import argparse
import os

import bs4
from datetime import datetime
from functools import reduce
import math
from multiprocessing import Pool
import pandas as pd
from pathlib import Path
import requests
from tqdm import tqdm
import ast

from Embedder import embedding_ingredients

n_workers = 20
save_path = 'data//recipes//'


def get_soup(url):
    req = requests.get(url)
    return bs4.BeautifulSoup(req.text, 'lxml')


def get_recipe_urls_from_page(url):
    soup = get_soup(url)
    content_div = soup.find('div', 'content-md')
    pages_url = [recipe_preview.find('h2').find('a')['href']
                 for recipe_preview in content_div.find_all('article', 'item-bl')]
    return pages_url


def get_recipe_from_page(url):
    soup = get_soup(url)
    recipe_name = soup.find('h1').get_text().strip()

    if recipe_name == 'Страница не найдена':
        return [{'url': url, 'name': recipe_name}]

    # find ingredients
    ingredients_tags = soup.findAll('li', itemprop='recipeIngredient')
    ingredients_dict = {}
    for ingredient in ingredients_tags:
        name = ingredient.find('a').get_text().strip()
        amount = ingredient.find_all('span')[-1]
        if amount is not None:
            amount = amount.get_text().strip()
        ingredients_dict[name] = amount

    # find instructions
    instructions_block = soup.find('ul', itemprop='recipeInstructions')
    instructions_list = []
    if instructions_block is not None:
        instructions_tags = instructions_block.findAll('li')
        for instruction in instructions_tags:
            description = instruction.find('div').find('p').get_text().strip()
            instructions_list.append(description)
    else:
        divs = soup.find('article', 'item-bl').find('div').find_all('div', recursive=False)
        div_without_attrs = [div for div in divs if not div.attrs]
        instructions_list.append(div_without_attrs[0].get_text().strip())

    # find food value
    values_all = {'energy': None, 'protein': None, 'fat': None, 'carb': None}
    values_portion = {'energy': None, 'protein': None, 'fat': None, 'carb': None}
    values_100g = {'energy': None, 'protein': None, 'fat': None, 'carb': None}

    def find_energy_values(all_values_from_table):
        return {
            'energy': all_values_from_table[0].find('strong').get_text().strip(),
            'protein': all_values_from_table[1].find('strong').get_text().strip(),
            'fat': all_values_from_table[2].find('strong').get_text().strip(),
            'carb': all_values_from_table[3].find('strong').get_text().strip(),
        }

    def postprocess(value):
        if value is not None:
            if ' ' in value:
                value = value.split(' ')[0]
            try:
                value = int(value)
                if value == 0:
                    return None
            except ValueError:
                pass
            return str(value)
        else:
            return value

    headers = list(map(lambda x: x.get_text().strip(), soup.findAll('h2')))

    if 'Пищевая и энергетическая ценность:' in headers:
        food_value_table = soup.find('table')
        table_headers = list(map(lambda x: x.get_text().strip(), soup.findAll('strong')))

        if 'Готового блюда' in table_headers:
            value_all = food_value_table.findAll('tr')[1].findAll('td')
            values_all = find_energy_values(value_all)
        if 'Порции' in table_headers:
            value_all = food_value_table.findAll('tr')[3].findAll('td')
            values_portion = find_energy_values(value_all)
            if '100 г блюда' in table_headers:
                value_all = food_value_table.findAll('tr')[5].findAll('td')
                values_100g = find_energy_values(value_all)
        elif '100 г блюда' in table_headers:
            value_all = food_value_table.findAll('tr')[3].findAll('td')
            values_100g = find_energy_values(value_all)

        values_all = {key: postprocess(value) for key, value in values_all.items()}
        values_portion = {key: postprocess(value) for key, value in values_portion.items()}
        values_100g = {key: postprocess(value) for key, value in values_100g.items()}

    portion_count = None
    recipe_yield = soup.find('span', itemprop='recipeYield')
    if recipe_yield:
        try:
            portion_count = int(recipe_yield.get_text().strip())
        except TypeError:
            pass

    return [{'url': url,
             'name': recipe_name,
             'ingredients': ingredients_dict,

             'energy_value_all': values_all['energy'],
             'protein_value_all': values_all['protein'],
             'fat_value_all': values_all['fat'],
             'carb_value_all': values_all['carb'],

             'energy_value_portion': values_portion['energy'],
             'protein_value_portion': values_portion['protein'],
             'fat_value_portion': values_portion['fat'],
             'carb_value_portion': values_portion['carb'],

             'energy_value_100g': values_100g['energy'],
             'protein_value_100g': values_100g['protein'],
             'fat_value_100g': values_100g['fat'],
             'carb_value_100g': values_100g['carb'],

             'portion_count': portion_count,
             'instruction': instructions_list}]


def get_pages_range():
    main_url = 'https://www.povarenok.ru/recipes/~1/'
    soup = get_soup(main_url)

    recipe_count_div = soup.find('div', 'bl-right')
    recipe_count = int(recipe_count_div.find('strong').get_text())
    recipe_per_page_count = 15
    pages_count = math.ceil(recipe_count / recipe_per_page_count)

    pages_range = [f'https://www.povarenok.ru/recipes/~{i}/' for i in range(1, pages_count + 1)]
    return pages_range


def save_ingredients(recipe_data):
    ingredients = set()
    for ingredients_dict in recipe_data['ingredients']:
        # ingredients_dict = ast.literal_eval(ingredient_str)
        for key, value in ingredients_dict.items():
            ingredients.add(key)
    ingredients = list(ingredients)
    ingredients_data = pd.DataFrame({'name': list(ingredients)})

    current_datetime = datetime.today().strftime('%Y_%m_%d')
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    ingredients_data.to_csv(f'{save_path}povarenok_ingredients_{current_datetime}.csv', index=False)


def save_ingredients_calculate_embeddings(recipe_data):
    ingredients = set()
    for ingredients_dict in recipe_data['ingredients']:
        # ingredients_dict = ast.literal_eval(ingredient_str)
        for key, value in ingredients_dict.items():
            ingredients.add(key)
    ingredients = list(ingredients)
    embedding = embedding_ingredients(ingredients)
    ingredients_data = pd.DataFrame({'name': ingredients, 'embedding': embedding})

    current_datetime = datetime.today().strftime('%Y_%m_%d')
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    ingredients_data.to_csv(f'{save_path}povarenok_ingredients_{current_datetime}.csv', index=False)


if __name__ == '__main__':
    # get_recipe_from_page('https://www.povarenok.ru/recipes/show/181238/')
    # get_recipe_from_page('https://www.povarenok.ru/recipes/show/181220/')
    # get_recipe_from_page('https://www.povarenok.ru/recipes/show/181337/')
    # get_recipe_from_page('https://www.povarenok.ru/recipes/show/181288/')

    pages_range = get_pages_range()
    print("Let's find all recipe urls")

    with Pool(n_workers) as p:
        p = Pool(n_workers)
        maped_recipe_urls = tqdm(p.imap_unordered(get_recipe_urls_from_page, pages_range[0:10]),
                                 total=len(pages_range[0:10]))
        print(maped_recipe_urls)
        recipe_urls = reduce(lambda x, y: x + y, maped_recipe_urls)
        recipe_urls = set(recipe_urls)
    print(recipe_urls)

    # for recipe_url in recipe_urls:
    #     get_recipe_from_page(recipe_url)

    print("Let's parse all recipe urls")
    with Pool(n_workers) as p:
        maped_recipes = tqdm(p.imap_unordered(get_recipe_from_page, recipe_urls), total=len(recipe_urls))
        recipes_data = pd.DataFrame(reduce(lambda x, y: x + y, maped_recipes))

    current_datetime = datetime.today().strftime('%Y_%m_%d')
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    recipes_data.to_csv(f'{save_path}povarenok_recipes_{current_datetime}.csv', index=False)
    save_ingredients(recipes_data)
    print("Well done")
