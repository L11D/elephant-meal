import uuid

from sqlalchemy import Column, UUID, Time, Boolean, Float, String, Enum as SQLEnum, DateTime, Date

from backend.Domain.db_config import Base
from backend.Domain.models.enum.roles import Role
from backend.Domain.models.enum.sex import Sex


class User(Base):
    __tablename__ = "users"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    surname = Column(String, nullable=False)
    name = Column(String, nullable=False)
    patronymic = Column(String, nullable=True)
    email = Column(String, nullable=False)
    sex = Column(SQLEnum(Sex), nullable=True)
    weight = Column(Float, nullable=True)
    height = Column(Float, nullable=True)
    birth_date = Column(Date, nullable=True)
    registration_date = Column(DateTime, nullable=False)
    password = Column(String, nullable=False)
    role = Column(SQLEnum(Role), nullable=False)
    is_verified = Column(Boolean, nullable=True, default=False)
    secret_key = Column(String, nullable=True)
