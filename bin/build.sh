#!/bin/bash

mvn -Dmaven.test.skip=true install

cd planner && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
cd ../
cd sure_tosca-flask-server && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt
cd ../
cd deployer && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
