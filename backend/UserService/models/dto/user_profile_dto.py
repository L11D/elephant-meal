from pydantic import BaseModel, EmailStr
from uuid import UUID
import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from backend.Domain.models.enum.sex import Sex
from datetime import date, datetime
sys.path = original_sys_path


class UserProfileDTO(BaseModel):
    id: UUID
    surname: str
    name: str
    patronymic: str = None
    email: EmailStr
    sex: Sex = None
    weight: float = None
    height: float = None
    birthdate: date = None
    registration_date: datetime
