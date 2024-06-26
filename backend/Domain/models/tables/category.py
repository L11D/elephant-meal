import uuid

from sqlalchemy import Column, UUID, Time, Float, String, ForeignKey, LargeBinary

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex


class Category(Base):
    __tablename__ = "categories"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    parent_id = Column(UUID(as_uuid=True), ForeignKey('categories.id'), default=uuid.uuid4, nullable=False)
    name = Column(String, nullable=False)
