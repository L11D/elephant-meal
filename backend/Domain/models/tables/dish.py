import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
sys.path = original_sys_path


class Dish(Base):
    __tablename__ = "dishes"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    recipe_id = Column(UUID(as_uuid=True), ForeignKey('recipes.id'), default=uuid.uuid4, nullable=False)
    name = Column(String, nullable=False)
