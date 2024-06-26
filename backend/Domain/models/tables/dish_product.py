import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from Domain.db_config import Base
from Domain.models.enum.sex import Sex
from Domain.models.enum.value_type import ValueType


class DishProduct(Base):
    __tablename__ = "dish_products"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    product_id = Column(UUID(as_uuid=True), ForeignKey('store_assortment.id'), default=uuid.uuid4, nullable=False)
    dish_id = Column(UUID(as_uuid=True), ForeignKey('dishes.id'), default=uuid.uuid4, nullable=False)