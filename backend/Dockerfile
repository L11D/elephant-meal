FROM python:3.10-slim
LABEL maintainer="Mr. Liid"

WORKDIR /app

COPY requirements.txt ./
RUN pip install -r requirements.txt

COPY . .

ENTRYPOINT [ "python3", "main.py" ]