#!/bin/bash

cd ../
mvn test
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
cd drip-planner && venv/bin/python3 -m unittest discover
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
cd ../
cd sure_tosca-flask-server && venv/bin/python3 -m unittest discover
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1


