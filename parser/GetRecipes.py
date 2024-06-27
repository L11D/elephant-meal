from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
from backend.Domain.models.tables.ingredient import Ingredient
import pandas as pd

from backend.Domain.models.tables.recipe import Recipe

init_db()

db_gen = get_db()
db = next(db_gen)
recipes = None
with db:
    recipes = db.query(Recipe.id, Recipe.link).all()

recipes_dict = []
for recipe in recipes:
    recipes_dict.append({
        "id": recipe[0],
        'ulr': recipe[1]
    })
recipes_df = pd.DataFrame(recipes_dict)
recipes_df.to_csv('data/recipes/recipes_from_db.csv', index=False)