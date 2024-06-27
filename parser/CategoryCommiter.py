import ast
import uuid

from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db

import pandas as pd

from backend.Domain.models.tables.category import Category


class CategoryStr(object):
    def __init__(self, name, parent):
        self.name = name
        self.parent = parent

    def __str__(self):
        return f"({self.name} __ {self.parent})\n"
    def __repr__(self):
        return self.__str__()


products_df_path = 'data/products/yeda_lenta_products2024_06_26_parsed.csv'
products_df = pd.read_csv(products_df_path)
products_df['category_chain'] = products_df['category_chain'].apply(lambda x: x.lower())
products_df['category_chain'] = products_df['category_chain'].apply(ast.literal_eval)
products_dict = products_df['category_chain'].tolist()

categories_out = []

for product_categories in products_dict:
    for idx, category in enumerate(product_categories):
        if any(c.name == category for c in categories_out):
            continue
        if idx == 0:
            categories_out.append(CategoryStr(category, None))
        else:
            categories_out.append(CategoryStr(category, product_categories[idx - 1]))

print(categories_out)

init_db()

db_gen = get_db()
db = next(db_gen)
with db:
    for category in categories_out:
        parent = None
        if category.parent:
            parent = db.query(Category.id).filter_by(name=category.parent).first()[0]
        db.add(Category(name=category.name, parent_id=parent))
        db.commit()
