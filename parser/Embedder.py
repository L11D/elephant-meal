from sentence_transformers import SentenceTransformer

model_name = 'LaBSE'


def embedding_ingredients(ingredients):
    embedder = SentenceTransformer(model_name)
    ingredients = [ingredient.lower() for ingredient in ingredients]
    ingredients_embeddings = embedder.encode(ingredients)
    return list(ingredients_embeddings)


def embedding_products(products):
    embedder = SentenceTransformer(model_name)
