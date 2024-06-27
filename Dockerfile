FROM python:3.10-slim
LABEL maintainer="Mr. Liid"

WORKDIR /app

COPY alembic.ini .
COPY alembic alembic

WORKDIR /app/backend

COPY backend/requirements.txt .

RUN pip install --cache-dir=$PIP_CACHE_DIR -r requirements.txt

COPY backend .

ENTRYPOINT [ "python", "main.py" ]