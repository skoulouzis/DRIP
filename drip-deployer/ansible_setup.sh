#! /bin/bash
apt-get update
apt-get -y upgrade
apt-get -y install software-properties-common python openssh-server sudo
service ssh restart