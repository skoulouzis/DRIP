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
cd sure_tosca-flask-server && docker build -t sure-tosca .
[ $status -eq 0 ] && echo "build successful" || exit -1
echo "----------Building planner Docker--------------"
cd ../
cd planner && docker build -t planner .
[ $status -eq 0 ] && echo "build successful" || exit -1
echo "----------Building deployer Docker--------------"
cd ../
cd deployer && docker build -t deployer .
[ $status -eq 0 ] && echo "build successful" || exit -1



docker tag sure-tosca qcdis/sure-tosca
docker tag planner qcdis/planner
docker tag deployer qcdis/deployer
docker tag manager:3.0.0 qcdis/manager
docker tag provisioner:3.0.0 qcdis/provisioner
