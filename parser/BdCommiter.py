import sys
import os

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
import pandas as pd
import re
import ast
import numpy as np

from backend.Domain.models.enum.value_type import ValueType
from backend.Domain.models.tables.ingredient import Ingredient
from backend.Domain.models.tables.ingredient_in_recipe import IngredientInRecipe
from backend.Domain.models.tables.recipe import Recipe

sys.path = original_sys_path

recipes_df_path = 'data//recipes//recipes.csv'
ingredients_df_path = 'data/recipes/ingredients_embedded.h5'
def commit_ingredients(db):
    ingredients_df = pd.read_hdf(ingredients_df_path, key='df')
    for index, data in ingredients_df.iterrows():
        ingredient = Ingredient(name=data['name'], embedding=data['embedding'])
        db.add(ingredient)
    db.commit()


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
        nums = [float(n) for n in nums]

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
    return None


def get_values(x, y):
    value = None
    value_type = None
    displayed_value = None
    if x != y:
        displayed_value = y

    value_type = get_value_type(y)

    if value_type != 'notmetric':
        value = get_value(y)
    if value_type == 'GLASS':
        value *= 200
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
        case 'notmetric':
            value_type = ValueType.NotMetric

    return value, value_type, displayed_value


def commit_recipes(db):
    recipes_df = pd.read_csv(recipes_df_path)
    recipes_df['ingredients'] = recipes_df['ingredients'].apply(ast.literal_eval)

    recipes = []
    for index, data in recipes_df.iterrows():
        recipes.append(
            Recipe(
                name=data['name'],
                instruction=data['instruction'],
                link=data['url']
            )
        )
    db.add_all(recipes)
    db.commit()

    commit_ing_in_recipes(db, recipes_df=recipes_df, recipes=recipes)

    # ingredients = db.query(Ingredient).all()
    #
    # for index, data in recipes_df.iterrows():
    #     recipes_id = recipes[index].id
    #     for x, y in data['ingredients'].items():
    #         ingredient = next((ing for ing in ingredients if ing.name == x), None)
    #         ing_id = ingredient.id
    #         value, value_type, displayed_value = get_values(x, y)
    #         db.add(
    #             IngredientInRecipe(
    #                 recipe_id=recipes_id,
    #                 ingredient_id=ing_id,
    #                 value=value,
    #                 value_type=value_type,
    #                 displayed_value=displayed_value
    #             )
    #         )
    #
    # db.commit()

def commit_ing_in_recipes(db, recipes_df=None, recipes=None):
    if recipes_df is None:
        recipes_df = pd.read_csv(recipes_df_path)
        recipes_df['ingredients'] = recipes_df['ingredients'].apply(ast.literal_eval)
        print('recipes_df loaded')
    recipes_from_db = False
    if recipes is None:
        recipes = db.query(Recipe.id, Recipe.link).all()
        recipes_from_db = True
        print('recipes fetched')

    ingredients = db.query(Ingredient).all()

    for index, data in recipes_df.iterrows():
        recipe_id = 1
        if recipes_from_db:
            recipe = next((rec for rec in recipes if rec[1] == data['url']), None)
            recipe_id = recipe[0]
        else:
            recipe_id = recipes[index].id
        for x, y in data['ingredients'].items():
            ingredient = next((ing for ing in ingredients if ing.name == x), None)
            ing_id = ingredient.id
            value, value_type, displayed_value = get_values(x, y)
            db.add(
                IngredientInRecipe(
                    recipe_id=recipe_id,
                    ingredient_id=ing_id,
                    value=value,
                    value_type=value_type,
                    displayed_value=displayed_value
                )
            )

    db.commit()

def commit():
    init_db()

    db_gen = get_db()
    db = next(db_gen)
    with db:
        # commit_ingredients(db)
        commit_recipes(db)
        # commit_ing_in_recipes(db)

        db.commit()
        # fetched_ing = db.query(Ingredient).filter_by(name=ing_test['name']).first()
        #
        # embedding_array = np.frombuffer(fetched_ing.embedding, dtype=ing_test['embedding'].dtype)


commit()
