from pydantic import BaseModel, EmailStr, constr, field_validator
from datetime import date, timedelta
from Domain.models.enum.sex import Sex
from UserService import user_config


class UserRegDTO(BaseModel):
    surname: str
    name: str
    patronymic: str = None
    email: EmailStr
    sex: Sex = None
    weight: float = None
    height: float = None
    birthdate: date = None
    password: constr(min_length=user_config.MIN_PASSWORD_LENGTH)

    @field_validator('birthdate')
    def check_birthdate(cls, value):
        today = date.today()
        sixteen_years_ago = today - timedelta(days=16 * 365.25)
        one_hundred_twenty_years_ago = today - timedelta(days=120 * 365.25)

        if not one_hundred_twenty_years_ago <= value <= sixteen_years_ago:
            raise ValueError('Age must be between 16 and 120 years old')
        return value
