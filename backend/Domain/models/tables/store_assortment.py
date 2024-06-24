import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class User(Base):
    __tablename__ = "users"

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
    brand = Column(String, nullable=True)
    brand = Column(String, nullable=True)


    patronymic = Column(String, nullable=True)
    weight = Column(Float, nullable=True)
    height = Column(Float, nullable=True)
    birth_date = Column(Time, nullable=True)
    registration_date = Column(Time, nullable=False)
    password = Column(String, nullable=False)
