import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class IngerdientAndProduct(Base):
    __tablename__ = "ingredients_and_products"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    ingredient_id = Column(UUID(as_uuid=True), ForeignKey('ingredients.id'), nullable=False)
    product_id = Column(UUID(as_uuid=True), ForeignKey('store_assortment.id'), nullable=False)
    chance = Column(Float, nullable=False)