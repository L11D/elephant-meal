from pydantic import BaseModel, EmailStr, constr

import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from backend.UserService import user_config
sys.path = original_sys_path


class UserLoginDTO(BaseModel):
    email: EmailStr
    password: constr(min_length=user_config.MIN_PASSWORD_LENGTH)
