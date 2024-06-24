import logging

from fastapi import FastAPI, APIRouter
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

from sqlalchemy import create_engine, text, Table, Column, Integer, String, MetaData

from backend.Domain.domain_init import init_db
from backend.UserService.routers.user_router import user_router
from backend.UserService.services.email_service import EmailService

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

router = APIRouter(prefix="/api/v1")

router.include_router(user_router)

app = FastAPI()
app.include_router(router)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
async def read_root():
    return {"message": "Hello, Hell Hell!"}



if __name__ == "__main__":
    email_service = EmailService()
    email_service.test_send()

    init_db()

    print('exit')
    uvicorn.run(app, host="0.0.0.0", port=8000)

    
