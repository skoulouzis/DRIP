#!/bin/bash

mvn test

cd drip-planner && venv/bin/python3 -m unittest discover
cd ../
cd sure_tosca-flask-server && venv/bin/python3 -m unittest discover


