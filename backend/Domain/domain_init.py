import logging

from backend.Domain.db_config import SessionLocal, Base, engine

from backend.Domain.models.tables.shop import Shop
from backend.Domain.models.tables.user import User

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def init_db():
    db = SessionLocal()

    try:
        Base.metadata.create_all(bind=engine, tables=[Shop.__table__,
                                                      User.__table__
                                                      ]
                                 )
        db.commit()
        logger.info("(Init database) Database initialized successfully.")
    except Exception as e:
        db.rollback()
        logger.fatal(f"(Init database) initializing database: {e}")
        raise
    finally:
        db.close()
