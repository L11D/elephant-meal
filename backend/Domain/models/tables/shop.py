import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
import uuid

from sqlalchemy import Column, UUID, Integer, String

from backend.Domain.db_config import Base
sys.path = original_sys_path


class Shop(Base):
    __tablename__ = "shops"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
