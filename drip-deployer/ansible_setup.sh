#! /bin/bash
apt-get update
apt-get upgrade
apt-get -y install software-properties-common python
service ssh restart