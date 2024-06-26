FROM python:3.10-slim
LABEL maintainer="Mr. Liid"

WORKDIR /app

COPY alembic.ini .
COPY alembic alimbic

WORKDIR /app/backend

COPY backend/requirements.txt .

RUN pip install --cache-dir=$PIP_CACHE_DIR -r requirements.txt

COPY backend .

ENTRYPOINT [ "python3", "main.py" ]