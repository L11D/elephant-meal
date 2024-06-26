import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum, LargeBinary

from Domain.db_config import Base
from Domain.models.enum.sex import Sex


class Ingredient(Base):
    __tablename__ = "ingredients"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
    embedding = Column(LargeBinary, nullable=False)
