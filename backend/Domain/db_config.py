import os
import sys

original_sys_path = sys.path.copy()
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from backend.env_variables import DB_USER, DB_PASSWORD, DB_HOST, DB_PORT, DB_NAME
sys.path = original_sys_path


Base = declarative_base()
DATABASE_URL = f'postgresql://{DB_USER}:{DB_PASSWORD}@' \
               f'{DB_HOST}:{DB_PORT}/{DB_NAME}'
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
