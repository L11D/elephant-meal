import logging

from fastapi import HTTPException, Depends, APIRouter, Query

import jwt
from uuid import UUID

from backend.Domain.db_config import get_db
from sqlalchemy.orm import Session
from backend.UserService import user_config
from backend.UserService.models.dto.user_access_token_dto import UserAccessTokenDTO
from backend.UserService.models.dto.user_login_dto import UserLoginDTO
from backend.UserService.models.dto.user_profile_dto import UserProfileDTO
from backend.UserService.models.dto.user_reg_dto import UserRegDTO
from backend.UserService.models.dto.user_update_dto import UserUpdateDTO
from backend.UserService.services.auth_service import AuthService
from backend.UserService.services.email_service import EmailService
from backend.UserService.services.user_service import UserService
from backend.UserService.user_config import oauth2_scheme
from backend.basic_models.dto.error_dto import ErrorDTO
from backend.basic_models.dto.message_dto import MessageDTO

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

user_router = APIRouter(prefix="/user")


@user_router.post(
    "/login/",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=UserAccessTokenDTO,
    responses={
        200: {
            "model": UserAccessTokenDTO

        },
        400: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def login(user_login_dto: UserLoginDTO,
                db: Session = Depends(get_db),
                user_service: UserService = Depends(UserService),
                auth_service: AuthService = Depends(AuthService)
                ):
    try:
        if not await user_service.verify_password(db, user_login_dto.email, user_login_dto.password):
            logger.warning(f"(Login) Failed login for user with email: {user_login_dto.email}")
            raise HTTPException(status_code=400, detail="Invalid credentials")

        user = await user_service.get_user_by_email(db, user_login_dto.email)

        access_token = await auth_service.create_access_token(
            data={"sub": str(user.id)}
        )

        logger.info(f"(Login) Login successful for user with ID: {user.id}")
        return UserAccessTokenDTO(access_token=access_token)
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Login) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


@user_router.put(
    "/update/",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=MessageDTO,
    responses={
        200: {
            "model": MessageDTO

        },
        400: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def update(
                    user_update_dto: UserUpdateDTO,
                    access_token: str = Depends(user_config.oauth2_scheme),
                    auth_service: AuthService = Depends(AuthService),
                    db: Session = Depends(get_db),
                    user_service: UserService = Depends(UserService),
                   ):
    try:
        print(f"OIGOIRJIGJERJPGOJ")
        logger.warning(f"OIGOIRJIGJERJPGOJ")
        if await auth_service.check_revoked(db, access_token):
            logger.warning(f"(Get user profile) Token is revoked: {access_token}")
            raise HTTPException(status_code=403, detail="Token revoked")

        token_data = auth_service.get_data_from_access_token(access_token)

        user = await user_service.get_user_by_id(db, (await token_data)["sub"])

        logger.info(f"(Get user profile) Successful get profile with id: {user.id}")

        user = await user_service.update_user(db, user_update_dto, user.id)

        logger.info(f"(Reg) User successful updated: {user.id}")
        return MessageDTO(message=str(user))
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Reg) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")

@user_router.post(
    "/register/",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=MessageDTO,
    responses={
        200: {
            "model": MessageDTO

        },
        400: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def register(user_reg_dto: UserRegDTO,
                   db: Session = Depends(get_db),
                   user_service: UserService = Depends(UserService),
                   email_service: EmailService = Depends(EmailService)
                   ):
    try:
        user = await user_service.get_user_by_email(db, user_reg_dto.email)

        if user:
            logger.warning(f"(Reg) User already registered: {user_reg_dto.email}")
            raise HTTPException(status_code=400, detail="User already exists")

        user = await user_service.create_user(db, user_reg_dto)

        email_service.send_link(user.secret_key, user.email)

        logger.info(f"(Reg) User successful registered: {user.id}")
        return MessageDTO(message=str(user.id))
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Reg) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


@user_router.get(
    "/verify/{key}",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=MessageDTO,
    responses={
        200: {
            "model": MessageDTO

        },
        404: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def verify(key: str,
                 db: Session = Depends(get_db),
                 user_service: UserService = Depends(UserService),
                 ):
    try:
        user = await user_service.get_user_by_secret_key(db, key)

        if not user or user.is_verified or await user_service.get_user_by_email(db, user.email):
            logger.warning(f"(Verify) User not exists or verified: {key}")
            raise HTTPException(status_code=404, detail="Not found")

        await user_service.verify_user(db, key)

        logger.info(f"(Verify) User verified registered: {user.id}")

        return MessageDTO(message='Account verified!')
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Verify) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


@user_router.get(
    "/",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=UserProfileDTO,
    responses={
        200: {
            "model": UserProfileDTO

        },
        401: {
            "model": ErrorDTO
        },
        403: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def get_profile(access_token: str = Depends(user_config.oauth2_scheme),
                      db: Session = Depends(get_db),
                      user_service: UserService = Depends(UserService),
                      auth_service: AuthService = Depends(AuthService)
                      ):
    try:
        print(f"OIGOIRJIGJERJPGOJ")
        logger.warning(f"OIGOIRJIGJERJPGOJ")
        if await auth_service.check_revoked(db, access_token):
            logger.warning(f"(Get user profile) Token is revoked: {access_token}")
            raise HTTPException(status_code=403, detail="Token revoked")

        token_data = auth_service.get_data_from_access_token(access_token)

        user = await user_service.get_user_by_id(db, (await token_data)["sub"])


        logger.warning(f"OIGOIRJIGJERJPGOJ  {user}")
        logger.warning(f"OIGOIRJIGJERJPGOJ  {user.birth_date}")
        logger.warning(f"OIGOIRJIGJERJPGOJ  {user.registration_date}")
        logger.warning(f"OIGOIRJIGJERJPGOJ  {user.weight}")
        logger.warning(f"OIGOIRJIGJERJPGOJ  {user.height}")
        logger.warning(f"                          ")

        birthdate = user.birth_date if user.birth_date else None
        registration_date = user.registration_date
        logger.info(f"(Get user profile) Successful get profile with id: {user.id}")
        print(f"DDDDDDDDDDDDDDDDDDDD    {user}")
        logger.info(f"DDDDDDDDDDDDDDDDDDDD    {user}")
        logger.warning(f"DDDDDDDDDDDDDDDDDDDD")

        returned_data = UserProfileDTO(
            id=user.id,
            email=user.email,
            name=user.name,
            surname=user.surname,
            registration_date=user.registration_date
        )

        returned_data.weight = user.weight if user.weight is not None else returned_data.weight
        returned_data.height = user.height if user.height is not None else returned_data.height
        returned_data.sex = user.sex if user.sex is not None else returned_data.sex
        returned_data.patronymic = user.patronymic if user.patronymic is not None else returned_data.patronymic
        returned_data.birthdate = user.birth_date if user.birth_date is not None else returned_data.birthdate

        return returned_data
    except jwt.PyJWTError as e:
        logger.warning(f"(Get user profile) Bad token: {e}")
        raise HTTPException(status_code=403, detail="Bad token")
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Get user profile) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


@user_router.post(
    "/logout/",
    tags=[user_config.SWAGGER_GROUPS["user"]],
    response_model=MessageDTO,
    responses={
        200: {
            "model": MessageDTO

        },
        401: {
            "model": ErrorDTO
        },
        403: {
            "model": ErrorDTO
        },
        500: {
            "model": ErrorDTO
        }
    }
)
async def logout(access_token: str = Depends(oauth2_scheme),
                 db: Session = Depends(get_db),
                 auth_service: AuthService = Depends(AuthService)
                 ):
    try:
        if await auth_service.check_revoked(db, access_token):
            logger.warning(f"(Logout) Token is revoked: {access_token}")
            raise HTTPException(status_code=403, detail="Token revoked")

        await auth_service.revoke_access_token(db, access_token)

        logger.info(f"(Logout) Token was revoked now: {access_token}")

        return MessageDTO(message="Token was successfully revoked")
    except jwt.PyJWTError as e:
        logger.warning(f"(Logout) Bad token: {e}")
        raise HTTPException(status_code=403, detail="Bad token")
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Login) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


# @user_router.get(
#     "/users",
#     tags=[user_config.SWAGGER_GROUPS["user"]],
#     response_model=list[UserProfileDTO],
#     responses={
#         200: {
#             "model": list[UserProfileDTO]
#
#         },
#         401: {
#             "model": ErrorDTO
#         },
#         403: {
#             "model": ErrorDTO
#         },
#         500: {
#             "model": ErrorDTO
#         }
#     }
# )
# async def get_users(
#                     access_token: str = Depends(user_config.oauth2_scheme),
#                     db: Session = Depends(get_db),
#                     user_service: UserService = Depends(UserService),
#                     auth_service: AuthService = Depends(AuthService)
#                     ):
#     try:
#         if await auth_service.check_revoked(db, access_token):
#             logger.warning(f"(Get user profile) Token is revoked: {access_token}")
#             raise HTTPException(status_code=403, detail="Token revoked")
#
#         users = await user_service.get_users(db)
#
#         users_dto = []
#
#         for user in users:
#             users_dto.append(
#                 UserProfileDTO(
#                     id=user.id,
#                     email=user.email,
#                     full_name=user.full_name,
#                     role=(await user_service.get_role_by_id(db, user.role_id)).name
#                 )
#             )
#
#         return users_dto
#     except jwt.PyJWTError as e:
#         logger.warning(f"(Get users profiles) Bad token: {e}")
#         raise HTTPException(status_code=403, detail="Bad token")
#     except HTTPException:
#         raise
#     except Exception as e:
#         logger.error(f"(Get user profile) Error: {e}")
#         raise HTTPException(status_code=500, detail="Internal server error")

