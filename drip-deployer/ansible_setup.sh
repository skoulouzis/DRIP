#! /bin/bash
killall apt
rm /var/lib/dpkg/lock
dpkg --configure -a
apt-get update
apt-get -y upgrade
apt-get -y install software-properties-common python openssh-server sudo
service ssh restart