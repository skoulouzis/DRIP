#!/bin/bash
echo "----------Building drip-manager Docker--------------"
cd drip-manager && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
echo "----------Building drip-provisioner Docker--------------"
cd drip-provisioner && mvn -Dmaven.test.skip=true dockerfile:build
cd ../
echo "----------Building sure_tosca-flask-server Docker--------------"
cd sure_tosca-flask-server && docker build -t sure-tosca:3.0.0 .
echo "----------Building drip-planner Docker--------------"
cd ../
cd drip-planner && docker build -t drip-planner:3.0.0 .
