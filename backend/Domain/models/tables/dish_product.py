import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class DishProduct(Base):
    __tablename__ = "dish_products"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    product_id = Column(UUID(as_uuid=True), ForeignKey('store_assortment.id'), nullable=False)
    dish_id = Column(UUID(as_uuid=True), ForeignKey('dishes.id'), nullable=False)