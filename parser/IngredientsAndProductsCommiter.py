from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
import pandas as pd

from backend.Domain.models.enum.value_type import ValueType
from backend.Domain.models.tables.ingredient_in_recipe import IngredientInRecipe
from backend.Domain.models.tables.ingredients_and_products import IngerdientAndProduct

ingredients_and_products_df = pd.read_csv('data/products_and_ingredients.csv')
ingredients_and_products_df = ingredients_and_products_df.to_dict('records')

result = []
for i_a_p in ingredients_and_products_df:

    result.append(IngerdientAndProduct(
        ingredient_id=i_a_p['ingredient_id'],
        product_id=i_a_p['product_id'],
        chance=i_a_p['chance']
    ))


init_db()

db_gen = get_db()
db = next(db_gen)
with db:
    print('commiting')
    db.add_all(result)
    db.commit()