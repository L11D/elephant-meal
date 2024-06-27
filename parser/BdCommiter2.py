import sys
import os
import uuid
from concurrent.futures import ProcessPoolExecutor
from concurrent.futures import ThreadPoolExecutor
from functools import partial

from tqdm import tqdm

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pandas as pd
import re
import ast
import numpy as np

from backend.Domain.models.enum.value_type import ValueType
from backend.Domain.models.tables.ingredient import Ingredient
from backend.Domain.models.tables.ingredient_in_recipe import IngredientInRecipe
from backend.Domain.models.tables.recipe import Recipe

sys.path = original_sys_path


def get_value_type(value):
    a = ''.join(re.findall('[А-Яа-я]+', value))
    match a:
        case 'г':
            return 'g'
        case 'кг':
            return 'kg'
        case 'л':
            return 'l'
        case 'мл':
            return 'ml'
        case 'шт':
            return 'peace'
        case 'стак' | 'стакан':
            return 'GLASS'
        case _:
            return 'notmetric'


def get_value(value):
    m = re.match(r'(\d+[\s,/\.-]?)+', value)
    if m:
        s = m[0]
        s = s.strip()

        ans = 0

        nums = re.findall(r'\d+', s)
        try:
            nums = [float(n) for n in nums]
        except ValueError:
            return 0

        separators = re.findall(r'[^\d]', s)

        ans += nums[0]
        for i in range(1, len(nums)):
            match separators[i - 1]:
                case '.' | ',':
                    ans += nums[i] * 0.1
                case '-':
                    ans -= nums[i - 1]
                    ans += (nums[i - 1] + nums[i]) / 2
                case '/':
                    ans -= nums[i - 1]
                    ans += (nums[i - 1] / nums[i])
                case ' ':
                    ans += nums[i]

        return ans
    return 0


def get_values(x, y):
    value = 0
    value_type = ValueType.NotMetric
    displayed_value = 'error'
    if x != y:
        displayed_value = y

    value_type = get_value_type(y)

    if value_type != 'notmetric':
        value = get_value(y)
    if value_type == 'GLASS':
        try:
            value *= 200
        except Exception:
            pass
        value_type = 'ml'

    match value_type:
        case 'g':
            value_type = ValueType.G
        case 'kg':
            value_type = ValueType.Kg
        case 'l':
            value_type = ValueType.L
        case 'ml':
            value_type = ValueType.Ml
        case 'peace':
            value_type = ValueType.Piece
        case 'notmetric':
            value_type = ValueType.NotMetric
        case _:
            value_type = ValueType.NotMetric

    return value, value_type, displayed_value


def compute(recipe, recipes_from_db, ingredients_from_db):
    out = []
    recipe_id = recipes_from_db.loc[recipes_from_db['ulr'] == recipe['url'], 'id']
    if recipe_id.empty:
        return None
    else:
        recipe_id = recipe_id.iat[0]

    for x, y in recipe['ingredients'].items():
        ingredient_id = ingredients_from_db.loc[ingredients_from_db['name'] == x, 'id']
        if ingredient_id.empty:
            continue
        else:
            ingredient_id = ingredient_id.iat[0]

        value = 0
        value_type = ValueType.NotMetric
        displayed_value = 'error'
        try:
            value, value_type, displayed_value = get_values(x, y)
        except TypeError:
            print(recipe)
        out.append(
            {
                'recipe_id': recipe_id,
                'ingredient_id': ingredient_id,
                'value': value,
                'value_type': value_type,
                'displayed_value': displayed_value
            }
        )
    return out


ingredients_from_db_path = 'data/recipes/ingredients_from_db.csv'
recipes_from_db_path = 'data/recipes/recipes_from_db.csv'
recipes_df_path = 'data//recipes//recipes.csv'

ingredients_from_db = pd.read_csv(ingredients_from_db_path)
recipes_from_db = pd.read_csv(recipes_from_db_path)
recipes_df = pd.read_csv(recipes_df_path)
recipes_df['ingredients'] = recipes_df['ingredients'].apply(ast.literal_eval)

recipes_dict = recipes_df.to_dict('records')

result = []


def parallel_compute(recipe, recipes_from_db, ingredients_from_db):
    return compute(recipe, recipes_from_db, ingredients_from_db)

partial_parallel_compute = partial(parallel_compute, recipes_from_db=recipes_from_db, ingredients_from_db=ingredients_from_db)

max_workers = 12
with ThreadPoolExecutor(max_workers=max_workers) as executor:
    result = []
    progress_bar = tqdm(total=len(recipes_dict), desc="Processing recipes", unit="recipe")
    for sublist in executor.map(partial_parallel_compute, recipes_dict):
        if sublist is not None:
            result.extend(sublist)
        progress_bar.update(1)
    progress_bar.close()

out_df = pd.DataFrame(result)
out_df.to_csv('data/recipes/ingredient_recipes_commit.csv', index=False)


