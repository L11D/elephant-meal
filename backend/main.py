from fastapi import FastAPI
import uvicorn

from sqlalchemy import create_engine

username = 'postgres'
password = '1111'
host = 'database_host'  # или 'database_host', если запускаете из другого контейнера в одном Compose
port = '5432'
database = 'elephant-meal-db'

connection_string = f'postgresql://{username}:{password}@{host}:{port}/{database}'

engine = create_engine(connection_string)




app = FastAPI()


@app.get("/")
async def read_root():
    return {"message": "Hello, Hell Hell!"}



if __name__ == "__main__":
    with engine.connect() as connection:
        result = connection.execute("SELECT 1")
        print(result.fetchone())
    print('exit')
    uvicorn.run(app, host="0.0.0.0", port=8000)

    
