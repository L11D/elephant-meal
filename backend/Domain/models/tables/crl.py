import uuid

from sqlalchemy import Column, UUID, String

from Domain.db_config import Base
from Domain.models.enum.sex import Sex



class CRL(Base):
    __tablename__ = "crl"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    token = Column(String, index=True, nullable=False)
