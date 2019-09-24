#! /bin/bash
sed -i -re 's/([a-z]{2}\.)?archive.ubuntu.com|security.ubuntu.com/old-releases.ubuntu.com/g' /etc/apt/sources.list
apt-get update &&  apt-get install -y apt-transport-https ca-certificates curl software-properties-common
add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg |  apt-key add -
apt-get update &&  apt-get install -y --allow-unauthenticated docker-ce=18.06.2~ce~3-0~ubuntu
echo -e "{\n \"exec-opts\": [\"native.cgroupdriver=systemd\"], \n \"log-driver\": \"json-file\", \n \"log-opts\": {\"max-size\": \"100m\"}, \n \"storage-driver\": \"overlay2\" \n}" > /etc/docker/daemon.json
mkdir -p /etc/systemd/system/docker.service.d
systemctl daemon-reload
systemctl restart docker

apt-get update &&  apt-get install -y apt-transport-https curl
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg |  apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" |  tee /etc/apt/sources.list.d/kubernetes.list
apt-get update &&   apt-get install -y kubelet kubeadm kubectl
apt-mark hold kubelet kubeadm kubectl
