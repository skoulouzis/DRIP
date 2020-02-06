FROM python:3.7-buster

RUN apt-get install ansible

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/

RUN pip3 install --no-cache-dir -r requirements.txt

COPY . /usr/src/app

EXPOSE 8081

ENTRYPOINT ["python3 __main__.py $RABBITMQ_HOST deployer_queue"]
