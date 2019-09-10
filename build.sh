#!/bin/bash

mkdir build

cd drip-api
mvn install 
tar -czvf drip-api-1.0-SNAPSHOT.tar.gz target/drip-api-1.0-SNAPSHOT
mv drip-api-1.0-SNAPSHOT.tar.gz ../build


cd ../

tar -czvf drip-deployer.tar.gz drip-deployer
mv drip-deployer.tar.gz build


tar -czvf drip-planner.tar.gz drip-planner
mv drip-planner.tar.gz build


cd drip-provisioner/
mvn install 
mv target/drip-provisioner-1.0-SNAPSHOT-jar-with-dependencies.jar ../build

cd ../ 
mv etc.tar.gz build
