FROM python:3.7-alpine

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/

RUN pip install --no-cache-dir -r requirements.txt

COPY . /usr/src/app

EXPOSE 8081

ENTRYPOINT ["python __main__.py $RABBITMQ_HOST planner_queue"]
