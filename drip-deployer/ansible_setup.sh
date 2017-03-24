#! /bin/bash
sudo apt-get update
sudo apt-get upgrade
sudo apt-get -y install software-properties-common openssh-server python
sudo service ssh restart