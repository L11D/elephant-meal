import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex


class Recipe(Base):
    __tablename__ = "recipes"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
    instruction = Column(String, nullable=False)
    link = Column(String, nullable=True)
