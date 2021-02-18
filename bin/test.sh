#!/bin/bash

cd ../
python3 -m venv venv
cd sure_tosca-flask-server && python3 -m venv venv && venv/bin/pip3 install -r test-requirements.txt && venv/bin/pip3 install -r requirements.txt && venv/bin/python3 -m unittest discover
if [ $? -eq 0 ]
then
  echo "------- sure_tosca-flask-server tests successful------"
  docker build -t sure-tosca:3.0.0 .
  docker tag sure-tosca:3.0.0 qcdis/sure-tosca:3.0.0
else
  echo "sure_tosca-flask-server tests Failed"
  exit 1
fi


docker stack deploy conf-test -c ../docker-compose/docker-compose-test.yml

sure_tosca_url=http://127.0.0.1:8081/tosca-sure/1.0.0/ui

echo "Waiting for $sure_tosca_url"
count=0 && until $(curl --output /dev/null --silent --head --fail "$sure_tosca_url"); do printf '.' && sleep 10 && count=$((count+1)) && if [ $count -gt 5 ]; then break; fi; done  


if curl --output /dev/null --silent --head --fail "$sure_tosca_url"; then
  printf '%s\n' "$sure_tosca_url exist"
else
  printf '%s\n' "$sure_tosca_url does not exist"
fi


cd ../
mvn test
if [ $? -eq 0 ]
then
  echo "------- Java tests successful------"
else
  echo "Java tests Failed"
  docker stack rm conf-test
  exit 1
fi

cd planner && python3 -m venv venv && venv/bin/pip3 install -r test-requirements.txt && venv/bin/pip3 install -r requirements.txt && venv/bin/python3 -m unittest discover
if [ $? -eq 0 ]
then
  echo "------- Planner tests successful------"
else
  echo "Planner tests Failed"
  docker stack rm conf-test
  exit 1
fi


cd ../
cd sure_tosca-client_python_stubs  && python3 -m venv venv && venv/bin/pip3 install -r test-requirements.txt && venv/bin/pip3 install -r requirements.txt && venv/bin/python3 -m unittest discover
if [ $? -eq 0 ]
then
  echo "------- sure_tosca-client_python_stubs tests successful------"
else
  echo "sure_tosca-client_python_stubs tests Failed"
  docker stack rm conf-test
  exit 1
fi

cd ../
cd semaphore-python-client-generated  && python3 -m venv venv && venv/bin/pip3 install -r test-requirements.txt && venv/bin/pip3 install -r requirements.txt && venv/bin/python3 -m unittest discover
if [ $? -eq 0 ]
then
  echo "------- semaphore-python-client-generated tests successful ------"
else
  echo "semaphore-python-client-generated tests Failed"
  docker stack rm conf-test
  exit 1
fi

#cd ../
#cd deployer  && python3 -m venv venv && venv/bin/pip3 install -U pip setuptools  && venv/bin/pip3 install -r test-requirements.txt && venv/bin/pip3 install -r requirements.txt && venv/bin/python3 -m unittest discover
#if [ $? -eq 0 ]
#then
#  echo "------- deployer tests successful ------"
#else
#  echo "deployer tests Failed"
#  docker stack rm conf-test
#  exit 1
#fi


docker stack rm conf-test
