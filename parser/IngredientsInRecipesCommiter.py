from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
import pandas as pd

from backend.Domain.models.enum.value_type import ValueType
from backend.Domain.models.tables.ingredient_in_recipe import IngredientInRecipe

ingredients_in_recipes_df = pd.read_csv('data/recipes/ingredient_recipes_commit.csv')
ingredients_in_recipes = ingredients_in_recipes_df.to_dict('records')

result = []
for recipe in ingredients_in_recipes:
    value_type = None
    match recipe['value_type']:
        case 'ValueType.G':
            value_type = ValueType.G
        case 'ValueType.Kg':
            value_type = ValueType.Kg
        case 'ValueType.L':
            value_type = ValueType.L
        case 'ValueType.Ml':
            value_type = ValueType.Ml
        case 'ValueType.Piece':
            value_type = ValueType.Piece
        case 'ValueType.NotMetric':
            value_type = ValueType.NotMetric
        case _:
            value_type = ValueType.NotMetric

    result.append(IngredientInRecipe(
        recipe_id = recipe['recipe_id'],
        ingredient_id= recipe['ingredient_id'],
        value= recipe['value'],
        value_type= value_type,
        displayed_value= recipe['displayed_value']
    ))

# print(result)

init_db()

db_gen = get_db()
db = next(db_gen)
with db:
    print('commiting')
    db.add_all(result)
    db.commit()