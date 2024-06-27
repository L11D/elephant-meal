import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.value_type import ValueType


class IngredientInRecipe(Base):
    __tablename__ = "ingredients_in_recipes"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    recipe_id = Column(UUID(as_uuid=True), ForeignKey('recipes.id'), nullable=False)
    ingredient_id = Column(UUID(as_uuid=True), ForeignKey('ingredients.id'), nullable=False)
    value = Column(Float, nullable=False)
    value_type = Column(SQLEnum(ValueType), nullable=False)
    displayed_value = Column(String, nullable=False)
