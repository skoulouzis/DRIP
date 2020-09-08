FROM python:3.8-buster

RUN apt update -y && apt upgrade -y 

ARG git_url=https://github.com/skoulouzis/CONF.git

RUN git clone -b develop https://github.com/skoulouzis/CONF.git

RUN mkdir -p /usr/src/
RUN cp -r /CONF/semaphore-python-client-generated /usr/src/semaphore-python-client-generated
WORKDIR /usr/src/semaphore-python-client-generated
RUN python setup.py install


RUN cp -r /CONF/sure_tosca-client_python_stubs /usr/src/sure_tosca-client_python_stubs
WORKDIR /usr/src/sure_tosca-client_python_stubs
RUN python setup.py install

RUN rm -rf /CONF

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY requirements.txt /usr/src/app/


RUN pip3 install --no-cache-dir -r requirements.txt

COPY . /usr/src/app

CMD sed -i "s#http://127.0.0.1:8081/tosca-sure/1.0.0#$SURE_TOSCA_BASE_PATH#g" properties.ini && \
    sed -i "s#http://127.0.0.1:3000/api#$SEMAPHORE_BASE_PATH#g" properties.ini && \
    sed -i "s#host = 127.0.0.1#host = $RABBITMQ_HOST#g" properties.ini && \
    python3 __main__.py $RABBITMQ_HOST deployer
