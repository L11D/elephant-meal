import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from pydantic import BaseModel, EmailStr, constr, field_validator
from datetime import date, timedelta
from backend.Domain.models.enum.sex import Sex
from backend.UserService import user_config
from uuid import UUID
sys.path = original_sys_path


class UserUpdateDTO(BaseModel):
    surname: str = None
    name: str = None
    patronymic: str = None
    email: EmailStr = None
    sex: Sex = None
    weight: float = None
    height: float = None
    birth_date: date = None

    @field_validator('birth_date')
    def check_birthdate(cls, value):
        today = date.today()
        sixteen_years_ago = today - timedelta(days=16 * 365.25)
        one_hundred_twenty_years_ago = today - timedelta(days=120 * 365.25)

        if not one_hundred_twenty_years_ago <= value <= sixteen_years_ago:
            raise ValueError('Age must be between 16 and 120 years old')
        return value
