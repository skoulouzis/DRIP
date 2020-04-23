#!/bin/bash
cd ../
echo "----------Building manager Docker--------------"
cd manager && mvn -Dmaven.test.skip=true dockerfile:build
status=$?
[ $status -eq 0 ] && echo "build successful" || exit -1
cd ../
echo "----------Building provisioner Docker--------------"
cd provisioner && mvn -Dmaven.test.skip=true dockerfile:build
[ $status -eq 0 ] && echo "build successful" || exit -1
cd ../
echo "----------Building sure_tosca-flask-server Docker--------------"
cd sure_tosca-flask-server && docker build -t sure-tosca:3.0.0 .
[ $status -eq 0 ] && echo "build successful" || exit -1
echo "----------Building planner Docker--------------"
cd ../
cd planner && docker build -t planner:3.0.0 .
[ $status -eq 0 ] && echo "build successful" || exit -1
echo "----------Building deployer Docker--------------"
cd ../
cd deployer && docker build -t deployer:3.0.0 .
[ $status -eq 0 ] && echo "build successful" || exit -1
