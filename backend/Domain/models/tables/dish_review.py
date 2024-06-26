import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.mark import Mark
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class DishReview(Base):
    __tablename__ = "dish_reviews"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    dish_id = Column(UUID(as_uuid=True), ForeignKey('dishes.id'), default=uuid.uuid4, nullable=False)
    user_id = Column(UUID(as_uuid=True), ForeignKey('users.id'), default=uuid.uuid4, nullable=False)
    mark = Column(SQLEnum(Mark), nullable=True)
    discription = Column(String, nullable=True)
