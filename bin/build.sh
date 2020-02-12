#!/bin/bash

cd ../

mvn -Dmaven.test.skip=true install
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1

cd planner && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
cd ../
cd sure_tosca-flask-server && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
cd ../
cd deployer && rm -rf venv && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
