#!/bin/bash

mvn clean install

cd drip-planner && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
cd ../
cd sure_tosca-flask-server && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
venv/bin/pip3 install -r test-requirements.txt
cd ../
