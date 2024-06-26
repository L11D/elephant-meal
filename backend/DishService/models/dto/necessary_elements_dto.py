from pydantic import BaseModel, EmailStr, field_validator
from uuid import UUID

from backend.DishService.models.enum.week_days import DaysWeek
from backend.Domain.models.enum.sex import Sex
from datetime import date, datetime

from backend.Domain.models.enum.type_plan import TypePlan


class NecessaryElementsDTO(BaseModel):
    calories: float
    #grams
    proteins: float
    carb: float
    fats: float
