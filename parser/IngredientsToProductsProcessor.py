import numpy as np
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

ingredients_df_path = 'data/recipes/ingredients_from_db_embedding.h5'
products_df_path = 'data/products/products_from_db_embedding.h5'


ingredients_df = pd.read_hdf(ingredients_df_path, key='df')
ingredients_values = ingredients_df.to_dict('records')

products_df = pd.read_hdf(products_df_path, key='df')
result = []

top_k = 5
threshold = 0.0

for ingredient in ingredients_values:
    similarity_scores = cosine_similarity(ingredient['embedding'].reshape(1, -1),
                                          np.vstack(products_df['embedding'].values)).flatten()

    valid_indices = np.where(similarity_scores >= threshold)[0]
    valid_scores = similarity_scores[valid_indices]

    top_k_filtered = min(top_k, len(valid_scores))

    indices = valid_indices[np.argsort(valid_scores)[-top_k_filtered:][::-1]]
    scores = valid_scores[np.argsort(valid_scores)[-top_k_filtered:][::-1]]

    # print('\n',ingredient['id'])
    for score, idx in zip(scores, indices):
        # print(products_df.loc[idx.item()]['id'], "(Score: {:.4f})".format(score))
        result.append(
            {
                'ingredient_id': ingredient['id'],
                'product_id': products_df.loc[idx.item()]['id'],
                'chance': score
            }
        )

result_df = pd.DataFrame(result)
result_df.to_csv('data/products_and_ingredients.csv', index=False)
