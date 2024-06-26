FROM python:3.10-slim
LABEL maintainer="Mr. Liid"

WORKDIR /app

COPY backend/requirements.txt ./
RUN pip install -r requirements.txt

COPY backend backend

ENTRYPOINT [ "python3", "main.py" ]