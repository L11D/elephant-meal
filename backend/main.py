from fastapi import FastAPI
import uvicorn

from sqlalchemy import create_engine, text, Table, Column, Integer, String, MetaData

username = 'postgres'
password = '1111'
host = 'database_host'  # или 'database_host', если запускаете из другого контейнера в одном Compose
port = '5432'
database = 'elephant-meal-db'

connection_string = f'postgresql://{username}:{password}@{host}:{port}/{database}'

metadata = MetaData()

# Определение таблицы
test_table = Table('test_table', metadata,
    Column('id', Integer, primary_key=True),
    Column('name', String)
)

engine = create_engine(connection_string)




app = FastAPI()


@app.get("/")
async def read_root():
    return {"message": "Hello, Hell Hell!"}



if __name__ == "__main__":
    with engine.connect() as connection:
        metadata.create_all(engine)
        insert_statement = test_table.insert().values(name='Test Name')
        connection.execute(insert_statement)
        select_statement = test_table.select()
        result = connection.execute(select_statement)
        for row in result:
            print(row)
            
    print('exit')
    # uvicorn.run(app, host="0.0.0.0", port=8000)

    
