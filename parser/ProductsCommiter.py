import ast

import pandas as pd

from backend.Domain.db_config import get_db
from backend.Domain.domain_init import init_db
from backend.Domain.models.enum.value_type import ValueType
from backend.Domain.models.tables.category import Category
from backend.Domain.models.tables.store_assortment import StoreAssortment

products_df_path = 'data/products/products_embedded.h5'
products_df = pd.read_hdf(products_df_path, key='df')
products_df['category_chain'] = products_df['category_chain'].apply(ast.literal_eval)

products_df['category'] = products_df.apply(lambda row: row['category_chain'][-1].lower(), axis=1)
products_dict = products_df.to_dict('records')

init_db()

db_gen = get_db()
db = next(db_gen)
with db:
    for product in products_dict:
        match product['value_type']:
            case 'g':
                product['value_type'] = ValueType.G
            case 'kg':
                product['value_type'] = ValueType.Kg
            case 'l':
                product['value_type'] = ValueType.L
            case 'ml':
                product['value_type'] = ValueType.Ml
            case 'peace':
                product['value_type'] = ValueType.Piece
            case 'notmetric':
                product['value_type'] = ValueType.NotMetric
            case _:
                product['value_type'] = ValueType.NotMetric

        category_id = None
        try:
            category_id = db.query(Category.id).filter_by(name=product['category']).first()[0]
        except Exception as e:
            print(product['category'])
            continue

        db.add(StoreAssortment(
            shop_id='329e063f-64f8-4f83-969d-30080df4c351',
            category_id=category_id,
            name=product['name'],
            embedding=product['embedding'],
            cost=product['cost'],
            value=product['value'],
            value_type=product['value_type'],
            calories=product['calories'],
            proteins=product['proteins'],
            carb=product['carb'],
            fats=product['fats'],
            brand=product['brand'],
            description=None,
            composition=product['composition'],
            image_id=product['image_id']
        ))
    print('commiting')
    db.commit()
