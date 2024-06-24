from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
import pandas as pd
import numpy as np

from backend.Domain.models.tables.ingredient import Ingredient

ingredients_df = pd.read_hdf("data/recipes/ingredients_embedded.h5", key='df')


def commit_ingredients(db):
    for index, data in ingredients_df.iterrows():
        ingredient = Ingredient(name=data['name'], embedding=data['embedding'])
        db.add(ingredient)


init_db()

db_gen = get_db()
db = next(db_gen)
with db:
    commit_ingredients(db)
    # db.add(ing)
    db.commit()
    # fetched_ing = db.query(Ingredient).filter_by(name=ing_test['name']).first()
    #
    # embedding_array = np.frombuffer(fetched_ing.embedding, dtype=ing_test['embedding'].dtype)
