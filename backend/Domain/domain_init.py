import logging

from backend.Domain.db_config import SessionLocal, Base, engine
from backend.Domain.models.tables.category import Category
from backend.Domain.models.tables.dish import Dish
from backend.Domain.models.tables.dish_in_plan import DishInPlan
from backend.Domain.models.tables.dish_product import DishProduct
from backend.Domain.models.tables.dish_review import DishReview
from backend.Domain.models.tables.ingredient import Ingredient
from backend.Domain.models.tables.ingredient_in_recipe import IngredientInRecipe
from backend.Domain.models.tables.plan import Plan
from backend.Domain.models.tables.recipe import Recipe

from backend.Domain.models.tables.shop import Shop
from backend.Domain.models.tables.store_assortment import StoreAssortment
from backend.Domain.models.tables.user import User

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def init_db():
    db = SessionLocal()

    try:
        Base.metadata.create_all(bind=engine, tables=[Shop.__table__,
                                                      Category.__table__,
                                                      User.__table__,
                                                      Recipe.__table__,
                                                      Ingredient.__table__,
                                                      IngredientInRecipe.__table__,
                                                      Plan.__table__,
                                                      StoreAssortment.__table__,
                                                      Dish.__table__,
                                                      DishInPlan.__table__,
                                                      DishProduct.__table__,
                                                      DishReview.__table__,
                                                      ]
                                 )
        db.commit()
        logger.info("(Init database) Database initialized successfully.")
    except Exception as e:
        db.rollback()
        logger.fatal(f"(Init database) initializing database: {e}")
        raise
    finally:
        db.close()
