import numpy as np

from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
from backend.Domain.models.tables.ingredient import Ingredient
import pandas as pd

from backend.Domain.models.tables.store_assortment import StoreAssortment

init_db()

db_gen = get_db()
db = next(db_gen)
products = None
with db:
    products = db.query(StoreAssortment.id, StoreAssortment.embedding).all()

products_dict = []
for product in products:
    products_dict.append({
        "id": product[0],
        'embedding': np.frombuffer(product[1], dtype=np.float32)
    })
ingredients_df = pd.DataFrame(products_dict)
# ingredients_df.to_csv('data/recipes/ingredients_from_db.csv', index=False)

ingredients_df.to_hdf('data/products/products_from_db_embedding.h5', key='df', mode='w')