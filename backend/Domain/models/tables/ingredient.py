import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
sys.path = original_sys_path


class Ingredient(Base):
    __tablename__ = "ingredients"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
    embedding = Column(LargeBinary, nullable=False)
