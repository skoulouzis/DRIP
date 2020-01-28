#!/bin/bash

sudo docker stop $(sudo docker ps -q)

sudo docker start mongo-inst
sleep 2
sudo docker start some-rabbit
sleep 2
sudo docker run -d alogo53/sure-tosca:3.0.0
sleep 6

sudo docker run -e MONGO_HOST=172.17.0.2 -e RABBITMQ_HOST=172.17.0.3 -e SURE_TOSCA_BASE_PATH='http\:\/\/172.17.0.4\/8081\/tosca-sure\/1.0.0\/' -p 8085:8080 -d alogo53/drip-manager:3.0.0

sudo docker run -e RABBITMQ_HOST=172.17.0.3 -e SURE_TOSCA_BASE_PATH='http\:\/\/172.17.0.4\/8081\/tosca-sure\/1.0.0\/' -d alogo53/drip-planner:3.0.0

sudo docker run -e RABBITMQ_HOST=172.17.0.3 -e SURE_TOSCA_BASE_PATH='http\:\/\/172.17.0.4\/8081\/tosca-sure\/1.0.0\/' -d alogo53/drip-provisioner:3.0.0
