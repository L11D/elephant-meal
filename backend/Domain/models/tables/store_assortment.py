import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class StoreAssortment(Base):
    __tablename__ = "store_assortment"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    shop_id = Column(UUID(as_uuid=True), ForeignKey('shops.id'), default=uuid.uuid4, nullable=False)
    category_id = Column(UUID(as_uuid=True), ForeignKey('categories.id'), default=uuid.uuid4, nullable=False)
    name = Column(String, nullable=False)
    embedding = Column(LargeBinary, nullable=False)
    cost = Column(Float, nullable=True)
    value_type = Column(SQLEnum(ValueType), nullable=True)
    calories = Column(Float, nullable=True)
    proteins = Column(Float, nullable=True)
    carb = Column(Float, nullable=True)
    fats = Column(Float, nullable=True)
    brand = Column(String, nullable=True)
    description = Column(String, nullable=True)
    composition = Column(String, nullable=True)
    image_id = Column(UUID(as_uuid=True), default=uuid.uuid4, nullable=True)
