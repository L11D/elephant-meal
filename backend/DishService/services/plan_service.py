import logging
from datetime import date
import uuid

from sqlalchemy.orm import Session
from sqlalchemy import or_, and_


from backend.DishService.models.dto.necessary_elements_dto import NecessaryElementsDTO
from backend.DishService.models.dto.physic_data_dto import PhysicData
from backend.DishService.models.dto.plan_dto import PlanDTO
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.type_plan import TypePlan
from backend.Domain.models.tables.food_preference import FoodPreference
from backend.Domain.models.tables.store_assortment import StoreAssortment
from backend.Domain.models.tables.user import User
from backend.UserService.models.dto.user_reg_dto import UserRegDTO
from backend.UserService.models.dto.user_update_dto import UserUpdateDTO

class PlanService:
    def __init__(self):
        logging.basicConfig(level=logging.INFO)
        self.logger = logging.getLogger(__name__)


    async def get_physic_data(self, user: User) -> PhysicData:
        try:
            physic_data = PhysicData(id=user.id)

            physic_data.sex = Sex.Male if user.sex is not None else physic_data.sex = user.sex

            if user.height is not None:
                physic_data.height = user.height
                physic_data.weight = user.weight if user.weight is not None else physic_data.weight = 0.78*physic_data.height - 61.64
            else:
                if user.weight is not None:
                    physic_data.weight = user.weight
                    physic_data.height = 1.28*physic_data.weight + 78.1
                else:
                    physic_data.height = 178
                    physic_data.weight = 77.2


            today = date.today()
            physic_data.age = 20 if user.birth_date is None \
                else physic_data.age = int(today.year - user.birth_date.year - ((today.month, today.day) < (user.birth_date.month, user.birth_date.day)))

            return physic_data
        except Exception as e:
            self.logger.error(f"(Physic data getting) Error: {e}")
            raise


    async def get_necessary_elements(self, physic_data: PhysicData, plan_dto: PlanDTO) -> NecessaryElementsDTO:
        try:
            result = NecessaryElementsDTO()
            if physic_data.sex == Sex.Male:
                result.calories = 88.362 + (13.397*physic_data.weight) + (4.799*physic_data.height) - (5.677*physic_data.age)
            else:
                result.calories = 447.593 + (9.247 * physic_data.weight) + (3.098* physic_data.height) - (4.330 * physic_data.age)

            result.calories *= plan_dto.activity_type
            result.calories *= plan_dto.plann_type

            k_carb, k_protein, k_fat = 0

            if plan_dto.plann_type == TypePlan.Balanced:
                k_protein = 0.17708333333333331
                k_fat = 0.27083333333333337
                k_carb = 0.5520833333333333
            elif plan_dto.plann_type == TypePlan.Mass:
                k_protein = 0.27525252525252525
                k_fat = 0.22474747474747475
                k_carb = 0.5
            else:
                k_protein = 0.3286445012787724
                k_fat = 0.24808184143222506
                k_carb = 0.42327365728900257

            result.proteins = (result.calories * k_protein) / 4
            result.fats = (result.calories * k_fat) / 9
            result.carb = (result.calories * k_carb) / 4

            return result
        except Exception as e:
            self.logger.error(f"(Physic data getting) Error: {e}")
            raise

    async def make_plan(self, db: Session, physic_data: PhysicData, plan_dto: PlanDTO, elements: NecessaryElementsDTO):
        try:
            liked_products = (
                f"""
                SELECT
                 store_assortment.id AS id, store_assortment.name AS name,
                 store_assortment.category_id AS category_id,
                 store_assortment.cost  AS cost, store_assortment.calories AS calories, 
                 store_assortment.proteins AS proteins, store_assortment.carb AS carb, store_assortment.fats AS fats
                FROM store_assortment
                JOIN food_preferences ON ((food_preferences.product_id = store_assortment.id) OR (food_preferences.category_id = store_assortment.category_id))
                WHERE food_preferences.liked IS TRUE AND food_preferences.user_id = {physic_data.id}
                """
            )

            products_in_preferenses = (
                f"""
                SELECT id FROM food_preferences
                WHERE user_id = {physic_data.id}
                """
            )

            normal_products = (
                f"""
                SELECT
                 store_assortment.id AS id, store_assortment.name AS name,
                 store_assortment.category_id AS category_id,
                 store_assortment.cost  AS cost, store_assortment.calories AS calories, 
                 store_assortment.proteins AS proteins, store_assortment.carb AS carb, store_assortment.fats AS fats
                FROM store_assortment
                WHERE store_assortment.id NOT IN {products_in_preferenses}
                """
            )

            count_categories_liked_products = (
                f"""
                SELECT DISTINCT COUNT(store_assortment.category_id)
                FROM {liked_products}
                """
            )

            count_categories_normal_products = (
                f"""
                SELECT DISTINCT COUNT(store_assortment.category_id)
                FROM {normal_products}
                """
            )

            ingredients_liked1 = (
                f"""
                SELECT
                 LP.id AS id, LP.name AS name,
                 LP.category_id AS category_id,
                 LP.cost  AS cost, LP.calories AS calories, 
                 LP.proteins AS proteins, LP.carb AS carb, LP.fats AS fats
                FROM {liked_products} AS LP
                JOIN 
                """
            )

            ingredients_liked: list[uuid]
            ingredients_normal: list[uuid]

            recipes_liked = (
                f"""
                SELECT DISTINCT recipes.id, ingredients_in_recipes.ingredient_id 
                FROM recipes
                JOIN ingredients_in_recipes ON (ingredients_in_recipes.recipe_id = recipes.id)
                WHERE ingredients_in_recipes.ingredient_id IN {ingredients_liked}
                """
            )

            recipes_normal = (
                f"""
                SELECT DISTINCT recipes.id, ingredients_in_recipes.ingredient_id 
                FROM recipes
                JOIN ingredients_in_recipes ON (ingredients_in_recipes.recipe_id = recipes.id)
                WHERE ingredients_in_recipes.ingredient_id IN {ingredients_normal}
                """
            )
            good_categories = list["яйца, яичные продукты", "овощи", "зелень", ]
            """
            
            """
            calories_total = elements.calories * 30
            proteins_total = elements.proteins * 30
            fats_total = elements.fats * 30
            carb_total = elements.carb * 30

            #cheat_calories = итоговая калорийность чит мила * 4
            #ВЫЧИСЛЕНИЯ!!!!


            #calories_total -= cheat_calories
            #МИКРОЭЛЕМЕНТЫ ТОЖЕ МЕНЯЮТСЯ


            #calories_total -= calories_total * 0.1







        except Exception as e:
            self.logger.error(f"(Physic data getting) Error: {e}")
            raise
