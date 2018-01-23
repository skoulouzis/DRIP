#! /bin/bash
sudo apt-get update
sudo apt-get -y install linux-image-extra-$(uname -r) linux-image-extra-virtual
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get -y update
sudo apt-get -y install docker-ce
#sudo echo "{ \"insecure-registries\":[\"129.7.98.3:5000\"] }" > /etc/docker/daemon.json
#sudo service docker restart

sudo docker plugin install --grant-all-permissions weaveworks/net-plugin:latest_release
sudo docker plugin disable weaveworks/net-plugin:latest_release
sudo docker plugin set weaveworks/net-plugin:latest_release WEAVE_MULTICAST=1
sudo docker plugin enable weaveworks/net-plugin:latest_release

sudo wget -O /usr/local/bin/weave https://github.com/weaveworks/weave/releases/download/latest_release/weave
sudo chmod a+x /usr/local/bin/weave


sudo curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add
sudo add-apt-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main"
sudo apt-get update
sudo apt-get install -y kubelet kubeadm kubectl kubernetes-cni

curl -L https://github.com/kubernetes/kompose/releases/download/v1.7.0/kompose-linux-amd64 -o kompose
chmod +x kompose
sudo mv ./kompose /usr/local/bin/kompose
