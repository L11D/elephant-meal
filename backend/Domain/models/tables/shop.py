import uuid

from sqlalchemy import Column, UUID, Integer, String

from Domain.db_config import Base


class Shop(Base):
    __tablename__ = "shops"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
