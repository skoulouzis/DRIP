#! /bin/bash
killall apt
rm /var/lib/dpkg/lock
dpkg --configure -a
apt-get update
export DEBIAN_FRONTEND=noninteractive

apt-get -o Dpkg::Options::="--force-confold" upgrade -q -y --force-yes
apt-get -y install software-properties-common python openssh-server sudo
service ssh restart