from pydantic import BaseModel, EmailStr, field_validator
from uuid import UUID

from backend.DishService.models.enum.week_days import DaysWeek
from backend.Domain.models.enum.activity_type import ActivityType
from backend.Domain.models.enum.sex import Sex
from datetime import date, datetime

from backend.Domain.models.enum.type_plan import TypePlan


class PlanDTO(BaseModel):
    like_products: list[UUID] = None
    dislike_products: list[UUID] = None
    days: list[DaysWeek]
    cheatmeal_calories: float
    plann_type: TypePlan
    activity_type: ActivityType

    @field_validator('days')
    def check_unique_days(cls, v):
        if len(v) != len(set(v)):
            raise ValueError('All days must be unique')
        return v
