#!/bin/bash

kubectl create -f ../k8s/CONF/
sleep 5
kubectl get all
kubectl delete -f ../k8s/CONF/

