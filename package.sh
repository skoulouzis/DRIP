#!/bin/bash

cd drip-manager && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
cd drip-planner && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
cd sure_tosca-flask-server && docker build -t sure-tosca:3.0.0 .

cd ../
cd drip-planner && docker build -t drip-planner:3.0.0 .
