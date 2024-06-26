from pydantic import BaseModel, EmailStr, constr



from UserService import user_config


class UserLoginDTO(BaseModel):
    email: EmailStr
    password: constr(min_length=user_config.MIN_PASSWORD_LENGTH)
