import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.type_plan import TypePlan
from backend.Domain.models.enum.value_type import ValueType
sys.path = original_sys_path


class Plan(Base):
    __tablename__ = "plans"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    user_id = Column(UUID(as_uuid=True), ForeignKey('users.id'), default=uuid.uuid4, nullable=False)
    start = Column(Time, nullable=False)
    end = Column(Time, nullable=False)
    plan_type = Column(SQLEnum(TypePlan), nullable=True)
