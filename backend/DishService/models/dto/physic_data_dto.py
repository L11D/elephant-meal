from pydantic import BaseModel, EmailStr, field_validator
from uuid import UUID
from typing import Union
from backend.DishService.models.enum.week_days import DaysWeek
from backend.Domain.models.enum.sex import Sex
from datetime import date, datetime

from backend.Domain.models.enum.type_plan import TypePlan


class PhysicData(BaseModel):
    id: UUID
    height: float
    weight: float
    sex: Union[Sex, None]
    age: int

    class Config:
        arbitrary_types_allowed = True
