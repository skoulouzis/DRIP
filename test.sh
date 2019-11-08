#!/bin/bash

mvn test

python3.6 --version

cd drip-planner && venv/bin/python3 -m unittest test/test_planner.py


cd sure_tosca-flask-server && venv/bin/python3 -m unittest sure_tosca/test/test_default_controller.py
