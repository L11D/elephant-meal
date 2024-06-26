import uuid

from sqlalchemy import Column, UUID, Boolean, Float, String, ForeignKey, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex
from backend.Domain.models.enum.utility import Utility


class Category(Base):
    __tablename__ = "categories"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    parent_id = Column(UUID(as_uuid=True), ForeignKey('categories.id'), default=uuid.uuid4, nullable=True)
    name = Column(String, nullable=False)
    utility = Column(SQLEnum(Utility), nullable=True)
    is_ready = Column(Boolean, nullable=True)
