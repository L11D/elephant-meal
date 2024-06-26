import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType
sys.path = original_sys_path


class IngredientInRecipe(Base):
    __tablename__ = "ingredients_in_recipes"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    recipe_id = Column(UUID(as_uuid=True), ForeignKey('recipes.id'), default=uuid.uuid4, nullable=False)
    ingredient_id = Column(UUID(as_uuid=True), ForeignKey('ingredients.id'), default=uuid.uuid4, nullable=False)
    value = Column(Float, nullable=False)
    value_type = Column(SQLEnum(ValueType), nullable=False)
    displayed_value = Column(String, nullable=False)
