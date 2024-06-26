import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from Domain.db_config import Base
from Domain.models.enum.sex import Sex
from Domain.models.enum.value_type import ValueType


class DishInPlan(Base):
    __tablename__ = "dishes_in_plan"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    dish_id = Column(UUID(as_uuid=True), ForeignKey('dishes.id'), default=uuid.uuid4, nullable=False)
    plan_id = Column(UUID(as_uuid=True), ForeignKey('plans.id'), default=uuid.uuid4, nullable=False)
    part_of_dish = Column(Float, nullable=True)
    meal_time = Column(Time, nullable=True)
