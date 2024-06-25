from pydantic import BaseModel, EmailStr, constr

import config

from backend.UserService import user_config


class UserLoginDTO(BaseModel):
    email: EmailStr
    password: constr(min_length=user_config.MIN_PASSWORD_LENGTH)
