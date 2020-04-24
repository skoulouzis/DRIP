FROM python:3.8-buster

RUN apt update -y && apt upgrade -y 

RUN mkdir -p /usr/src/semaphore-python-client-generated
COPY ../semaphore-python-client-generated /usr/src/semaphore-python-client-generated
WORKDIR /usr/src/semaphore-python-client-generated
RUN python setup.py install


RUN mkdir -p /usr/src/sure_tosca-client_python_stubs
COPY ../sure_tosca-client_python_stubs /usr/src/sure_tosca-client_python_stubs
WORKDIR /usr/src/sure_tosca-client_python_stubs
RUN python setup.py install

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/


RUN pip3 install --no-cache-dir -r requirements.txt

COPY . /usr/src/app

CMD python3 __main__.py $RABBITMQ_HOST deployer
