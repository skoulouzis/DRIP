#!/bin/bash

sudo docker stop $(sudo docker ps -q)

docker start mongo-inst
sleep 1
docker start some-rabbit
sleep 1
docker run -d alogo53/sure-tosca:3.0.0
sleep 5
docker run -it -e MONOG_HOST=172.17.0.2 -e RABBITMQ_HOST=172.17.0.3 -e SURE_TOSCA_BASE_PATH=http\:\/\/172.17.0.4\/8081\/tosca-sure\/1.0.0/ -p 8080:8080 alogo53/drip-manager:3.0.0
