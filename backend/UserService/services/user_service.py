import logging
import random
import string
from datetime import date, time, datetime
from sqlalchemy.orm import Session

from Domain.models.enum.roles import Role
# from models.enum.applicationstatuses import ApplicationStatuses
#
# from models.tables.role import Role
#
# from models.enum.userroles import UserRoles

from Domain.models.tables.user import User
from UserService.models.dto.user_reg_dto import UserRegDTO
from UserService.models.dto.user_update_dto import UserUpdateDTO


class UserService:
    def __init__(self):
        logging.basicConfig(level=logging.INFO)
        self.logger = logging.getLogger(__name__)

    async def get_user_by_email(self, db: Session, email: str) -> User:
        try:
            user = db.query(User).filter((User.email == email) & User.is_verified).first()

            if user:
                self.logger.info(f"(Email user getting) Got user with ID: {user.id}")
            else:
                self.logger.warning(f"(Email user getting) No same user found: {email}")

            return user
        except Exception as e:
            self.logger.error(f"(Email user getting) Error: {e}")
            raise

    async def create_user(self, db: Session, user_data: UserRegDTO) -> User:
        try:
            user = User(
                surname=user_data.surname,
                name=user_data.name,
                patronymic=user_data.patronymic,
                email=user_data.email,
                sex=user_data.sex,
                weight=user_data.weight,
                height=user_data.height,
                birth_date=user_data.birthdate,
                registration_date=datetime.now(),
                password=user_data.password,
                role=Role.User,
                secret_key=''.join(''.join(random.choices(string.ascii_letters + string.digits, k=33)))
            )
            db.add(user)
            db.commit()

            self.logger.error(f"(Creating user) Success: {user}")

            return user
        except Exception as e:
            self.logger.error(f"(Creating user) Error: {e}")
            raise

    async def update_user(self, db: Session, user_data: UserUpdateDTO, user_id: str) -> User:
        try:
            user = db.query(User).filter(User.id == user_id).first()

            user.surname = user.surname if user_data.surname == None else user_data.surname
            user.name = user.name if user_data.name == None else user_data.name
            user.patronymic = user.patronymic if user_data.patronymic == None else user_data.patronymic
            user.email = user.email if user_data.email == None else user_data.email
            user.sex = user.sex if user_data.sex == None else user_data.sex
            user.weight = user.weight if user_data.weight == None else user_data.weight
            user.height = user.height if user_data.height == None else user_data.height
            user.birth_date = user.birth_date if user_data.birth_date == None else user_data.birth_date

            db.commit()

            self.logger.error(f"(Creating user) Success: {user}")

            return user
        except Exception as e:
            self.logger.error(f"(Creating user) Error: {e}")
            raise

    async def get_user_by_id(self, db: Session, _id: str) -> User:
        try:
            user = db.query(User).filter((User.id == _id) & User.is_verified).first()

            if user:
                self.logger.info(f"(User id getting) Got user with ID: {user.id}")
            else:
                self.logger.warning(f"(User id getting) No same user found: {_id}")

            return user
        except Exception as e:
            self.logger.error(f"(User id getting) Error: {e}")
            raise

    async def get_users(self, db: Session) -> list[User]:
        try:
            users = db \
                .query(User) \
                .filter(
                        User.is_verified == True
                        ) \
                .all()

            return users
        except Exception as e:
            self.logger.error(f"(Users is getting) Error: {e}")
            raise

    async def get_user_by_secret_key(self, db: Session, _key: str) -> User:
        try:
            user = db.query(User).filter(User.secret_key == _key).first()

            if user:
                self.logger.info(f"(User key getting) Got user with ID: {user.id}")
            else:
                self.logger.warning(f"(User key getting) No same user found: {_key}")

            return user
        except Exception as e:
            self.logger.error(f"(User key getting) Error: {e}")
            raise

    async def verify_user(self, db: Session, _key: str) -> User:
        try:
            user = db.query(User).filter(User.secret_key == _key).first()
            user.is_verified = True

            db.commit()

            self.logger.info(f"(User verify) Success: {_key}")
            return user
        except Exception as e:
            self.logger.error(f"(User verify) Error: {e}")
            raise

    async def verify_password(self, db: Session, email: str, password: str) -> bool:
        try:
            user = await self.get_user_by_email(db, email)

            if not user:
                self.logger.info(f"(Password verify) No same user found: {email}")

                return False

            if user.password == password and user.is_verified:
                self.logger.info(f"(Password verify) Success: {email}")

                return True
            else:
                self.logger.warning(f"(Password verify) Failure: {email}")

                return False
        except Exception as e:
            self.logger.error(f"(Password verify) Error: {e}")
            raise

    async def check_user_existence(self, db: Session, user_id: str) -> bool:
        try:
            user = db.query(User).filter((User.id == user_id) & User.is_verified).first()

            if user:
                self.logger.info(f"(Checking user existence) Got user with ID: {user.id}")
                return True
            else:
                self.logger.warning(f"(Checking user existence) No same user found: {user_id}")
                return False

        except Exception as e:
            self.logger.error(f"(Checking user existence) Error: {e}")
            raise

    async def change_user_role(self, db: Session, role: int, user_id: str) -> str:
        try:
            self.logger.info(f"МЫЫЫЫ ТУУУТ")
            user = db.query(User).filter(User.id == user_id).first()
            prev_role = user.role_id
            user.role_id = role
            self.logger.info(f"ААААААУУУУ {user.id} == {user_id}")
            db.commit()

            return str(f"change role on {role} user: {user.full_name} with previous role {prev_role}")
        except Exception as e:
            self.logger.error(f"(Role id changing) Error: {e}")
            raise
