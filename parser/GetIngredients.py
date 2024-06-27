from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
from backend.Domain.models.tables.ingredient import Ingredient
import pandas as pd

init_db()

db_gen = get_db()
db = next(db_gen)
ingredients = None
with db:
    ingredients = db.query(Ingredient.id, Ingredient.name).all()

ingredients_dict = []
for ingredient in ingredients:
    ingredients_dict.append({
        "id": ingredient[0],
        'name': ingredient[1]
    })
ingredients_df = pd.DataFrame(ingredients_dict)
ingredients_df.to_csv('data/recipes/ingredients_from_db.csv', index=False)