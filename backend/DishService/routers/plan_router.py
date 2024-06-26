import logging

from fastapi import HTTPException, Depends, APIRouter, Query

import jwt
from uuid import UUID

from backend.DishService.models.dto.plan_dto import PlanDTO
from backend.DishService.services.plan_service import PlanService
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

plan_router = APIRouter(prefix="/plan")


@plan_router.post(
    "/crate/",
    tags=[user_config.SWAGGER_GROUPS["plan"]],
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
async def create_plan(
                plan_dto: PlanDTO,
                access_token: str = Depends(user_config.oauth2_scheme),
                db: Session = Depends(get_db),
                user_service: UserService = Depends(UserService),
                plan_service: PlanService = Depends(PlanService),
                auth_service: AuthService = Depends(AuthService)
                ):
    try:
        if await auth_service.check_revoked(db, access_token):
            logger.warning(f"(Get user profile) Token is revoked: {access_token}")
            raise HTTPException(status_code=403, detail="Token revoked")

        token_data = auth_service.get_data_from_access_token(access_token)

        user = await user_service.get_user_by_id(db, (await token_data)["sub"])

        final_user = await plan_service.get_physic_data(user)

        necessary_elements = await plan_service.get_necessary_elements(final_user, plan_dto)

        return MessageDTO(message="OK")
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"(Login) Error: {e}")
        raise HTTPException(status_code=500, detail="Internal server error")


