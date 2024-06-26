from pydantic import BaseModel, EmailStr
from uuid import UUID

from Domain.models.enum.sex import Sex
from datetime import date, datetime


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
