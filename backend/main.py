from fastapi import FastAPI
import uvicorn

app = FastAPI()


@app.get("/")
async def read_root():
    return {"message": "Hello, Hell Hell!"}



if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)