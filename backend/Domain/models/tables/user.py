import uuid

from sqlalchemy import Column, UUID, Time, Float, String, Enum as SQLEnum

from backend.Domain.db_config import Base
from backend.Domain.models.enum.sex import Sex


class User(Base):
    __tablename__ = "users"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    surname = Column(String, nullable=False)
    name = Column(String, nullable=False)
    patronymic = Column(String, nullable=True)
    sex = Column(SQLEnum(Sex), nullable=True)
    weight = Column(Float, nullable=True)
    height = Column(Float, nullable=True)
    birth_date = Column(Time, nullable=True)
    registration_date = Column(Time, nullable=False)
    password = Column(String, nullable=False)
