from fastapi import FastAPI
import uvicorn

from sqlalchemy import create_engine, text, Table, Column, Integer, String, MetaData

from backend.Domain.domain_init import init_db

app = FastAPI()


@app.get("/")
async def read_root():
    return {"message": "Hello, Hell Hell!"}



if __name__ == "__main__":
    init_db()
            
    print('exit')
    uvicorn.run(app, host="0.0.0.0", port=8000)

    
