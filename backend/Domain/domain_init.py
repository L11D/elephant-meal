import logging
import os

from Domain.db_config import SessionLocal, Base, engine

import logging
from alembic import command
from alembic.config import Config

from Domain.models.tables.category import Category
from Domain.models.tables.crl import CRL
from Domain.models.tables.dish import Dish
from Domain.models.tables.dish_in_plan import DishInPlan
from Domain.models.tables.dish_product import DishProduct
from Domain.models.tables.dish_review import DishReview
from Domain.models.tables.ingredient import Ingredient
from Domain.models.tables.ingredient_in_recipe import IngredientInRecipe
from Domain.models.tables.plan import Plan
from Domain.models.tables.recipe import Recipe
from Domain.models.tables.shop import Shop
from Domain.models.tables.store_assortment import StoreAssortment
from Domain.models.tables.user import User


logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def init_db():
    from Domain.db_config import Base, engine

    # Automatically generate migrations
    # Абсолютный путь к alembic.ini
    alembic_ini_path = os.path.join(os.path.dirname(__file__), "..", "..", "alembic.ini")

    # Загружаем конфигурацию Alembic
    # Загружаем конфигурацию Alembic
    alembic_cfg = Config(alembic_ini_path)
    command.revision(alembic_cfg, autogenerate=True, message="Automatic migration")

    # Apply migrations
    command.upgrade(alembic_cfg, "head")

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
                                                      CRL.__table__,
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