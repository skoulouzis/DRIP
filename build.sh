#!/bin/bash

mvn -Dmaven.test.skip=true install

cd drip-planner && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt
cd ../
cd sure_tosca-flask-server && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt
cd ../
