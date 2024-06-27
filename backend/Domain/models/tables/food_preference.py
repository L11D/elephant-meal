import uuid

from sqlalchemy import Column, UUID, Boolean, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class FoodPreference(Base):
    __tablename__ = "food_preferences"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    user_id = Column(UUID(as_uuid=True), ForeignKey('users.id'), default=uuid.uuid4, nullable=False)
    category_id = Column(UUID(as_uuid=True), ForeignKey('categories.id'), nullable=True)
    product_id = Column(UUID(as_uuid=True), ForeignKey('store_assortment.id'), nullable=False)
    liked = Column(Boolean, nullable=False)
