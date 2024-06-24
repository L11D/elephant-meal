import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex


class IngredientInRecipe(Base):
    __tablename__ = "ingredient_in_recipe"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    recipe_id = Column(UUID(as_uuid=True), ForeignKey('recipes.id'), default=uuid.uuid4, nullable=False)
    ingredient_id = Column(UUID(as_uuid=True), ForeignKey('ingredients.id'), default=uuid.uuid4, nullable=False)
    value = Column(Float, nullable=False)
    value_type = Column(String, nullable=False)
