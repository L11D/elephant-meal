from pydantic import BaseModel, EmailStr
from uuid import UUID

from backend.Domain.models.enum.sex import Sex
from datetime import date


class UserProfileDTO(BaseModel):
    id: UUID
    surname: str
    name: str
    patronymic: str
    email: EmailStr
    sex: Sex
    weight: float
    height: float
    birthdate: date
    registration_date: date
