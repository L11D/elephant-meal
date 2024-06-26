FROM python:3.10-slim
LABEL maintainer="Mr. Liid"

COPY alembic.ini .
COPY alembic .

WORKDIR /backend

COPY backend/requirements.txt .
RUN pip install -r requirements.txt

COPY backend .


ENTRYPOINT [ "python3", "main.py" ]