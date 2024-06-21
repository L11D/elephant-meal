from utils.YandexDiskDownloader import download_file_from_yandex_disk
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd
import ast
import os.path
import numpy as np

recipes_dataset_url = 'https://disk.yandex.ru/d/zQ4nGknPq4ydPw'
recipes_dataset_path = 'data/recipes/recipes.csv'
ingredients_dataset_path = 'data/recipes/ingredients_embedded.h5'
products_dataset_path = 'data/products/products_embedded.h5'


def upload_recipes():
    if not os.path.exists(recipes_dataset_path):
        download_file_from_yandex_disk(recipes_dataset_url, recipes_dataset_path)
    recipe_df = pd.read_csv(recipes_dataset_path)
    recipe_df['ingredients'] = recipe_df['ingredients'].apply(ast.literal_eval)
    recipe_df['instruction'] = recipe_df['instruction'].apply(ast.literal_eval)
    return recipe_df


def upload_ingredients():
    if not os.path.exists(ingredients_dataset_path):
        print('Ingredients not found')
        exit()
    ingredients_df = pd.read_hdf(ingredients_dataset_path, key='df')
    return ingredients_df


def upload_products():
    if not os.path.exists(products_dataset_path):
        print('Products not found')
        exit()
    products_df = pd.read_hdf(products_dataset_path, key='df')
    return products_df


class RecipeToProductsProcessor:
    def __init__(self):
        self.recipe_df = upload_recipes()
        self.ingredients_df = upload_ingredients()
        self.products_df = upload_products()

    def get_ingredient_embedding(self, ingredient):
        ingredient_embedding = self.ingredients_df.loc[self.ingredients_df['name'] == ingredient, 'embedding']
        ingredient_embedding = np.vstack(ingredient_embedding)
        return ingredient_embedding

    def process(self, recipe_url):
        top_k = 5
        threshold = 0.8

        recipe = self.recipe_df.loc[self.recipe_df['url'] == recipe_url].to_dict(orient='records')[0]
        matches = {}
        for ingredient in recipe['ingredients'].keys():
            ingredient_embedding = self.get_ingredient_embedding(ingredient)
            similarity_scores = cosine_similarity(ingredient_embedding,
                                                  np.vstack(self.products_df['embedding'].values)).flatten()

            valid_indices = np.where(similarity_scores >= threshold)[0]
            valid_scores = similarity_scores[valid_indices]

            top_k_filtered = min(top_k, len(valid_scores))

            indices = valid_indices[np.argsort(valid_scores)[-top_k_filtered:][::-1]]
            scores = valid_scores[np.argsort(valid_scores)[-top_k_filtered:][::-1]]

            # print("\nIngredient:", ingredient)
            # print("Top 5 most similar sentences in corpus:")
            products_list = []
            for score, idx in zip(scores, indices):
                # print(self.products_df.loc[idx.item()]['name'], "(Score: {:.4f})".format(score))
                products_list.append({
                    'name': self.products_df.loc[idx.item()]['name'],
                    'url': self.products_df.loc[idx.item()]['url']
                })
            matches[ingredient] = products_list
            if len(products_list) == 0:
                matches[ingredient] = None
        return matches


if __name__ == '__main__':
    processor = RecipeToProductsProcessor()
    mathess = processor.process('https://www.povarenok.ru/recipes/show/20116/')
    print(mathess)
