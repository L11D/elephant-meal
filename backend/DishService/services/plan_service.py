import logging
from datetime import date

from sqlalchemy.orm import Session

from backend.DishService.models.dto.necessary_elements_dto import NecessaryElementsDTO
from backend.DishService.models.dto.physic_data_dto import PhysicData
from backend.DishService.models.dto.plan_dto import PlanDTO
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.type_plan import TypePlan
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
