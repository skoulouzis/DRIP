#!/bin/bash

cd ../
mvn test
status=$?
[ $status -eq 0 ] && echo "-------Java tests successful------" || exit -1

cd planner && venv/bin/python3 -m unittest discover
status=$?
[ $status -eq 0 ] && echo "-------Planner tests successful------" || exit -1

cd ../
cd sure_tosca-flask-server && venv/bin/python3 -m unittest discover
status=$?
[ $status -eq 0 ] && echo "-------sure_tosca-flask-server tests successful------"" || exit -1


