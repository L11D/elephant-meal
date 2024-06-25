from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
import pandas as pd
import re
import numpy as np

from backend.Domain.models.tables.ingredient import Ingredient
from backend.Domain.models.tables.recipe import Recipe


def commit_ingredients(db):
    ingredients_df = pd.read_hdf("data/recipes/ingredients_embedded.h5", key='df')
    for index, data in ingredients_df.iterrows():
        ingredient = Ingredient(name=data['name'], embedding=data['embedding'])
        db.add(ingredient)


def commit_recipes(db):
    recipes_df = pd.read_csv("data//recipes//recipes.csv")
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

    def get_values(ingredients):
        value = None
        value_type = None
        displayed_name = None
        for x, y in ingredients.items():
            if x != y:
                displayed_name = y

            value_type = get_value_type(y)

            if value_type != 'notmetric':
                value = get_value(y)
            if value_type == 'GLASS':
                value *= 200
                value_type = 'ml'

        return value, value_type, displayed_name

    for index, data in recipes_df.iterrows():
        recipe = Recipe(
            name=data['name'],
            instruction=data['instruction'],
            link=data['url']
        )
        db.add(recipe)
        value, value_type, displayed_name = get_values(data['ingredients'])



def commit():
    init_db()

    db_gen = get_db()
    db = next(db_gen)
    with db:
        recipe = Recipe(
            name='dffdf',
            instruction='dfdf',
            link='sss'
        )
        a = db.add(recipe)
        db.commit()

        print(a)
        print(recipe.id)
        # commit_ingredients(db)
        db.commit()
        # fetched_ing = db.query(Ingredient).filter_by(name=ing_test['name']).first()
        #
        # embedding_array = np.frombuffer(fetched_ing.embedding, dtype=ing_test['embedding'].dtype)


commit()
