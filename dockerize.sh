#!/bin/bash
echo "----------Building manager Docker--------------"
cd manager && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
echo "----------Building provisioner Docker--------------"
cd provisioner && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
echo "----------Building sure_tosca-flask-server Docker--------------"
cd sure_tosca-flask-server && docker build -t alogo53/sure-tosca:3.0.0 .
echo "----------Building planner Docker--------------"
cd ../
cd planner && docker build -t alogo53/planner:3.0.0 .
echo "----------Building deployer Docker--------------"
cd ../
cd planner && docker build -t alogo53/deployer:3.0.0 .
