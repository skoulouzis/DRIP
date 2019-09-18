#! /bin/bash
sudo apt-get update && apt-get install -y apt-transport-https ca-certificates curl software-properties-common
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-get update && apt-get install -y --allow-unauthenticated docker-ce=18.06.2~ce~3-0~ubuntu
sudo echo -e "{\n \"exec-opts\": [\"native.cgroupdriver=systemd\"], \n \"log-driver\": \"json-file\", \n \"log-opts\": {\"max-size\": \"100m\"}, \n \"storage-driver\": \"overlay2\" \n}" > /etc/docker/daemon.json
sudo mkdir -p /etc/systemd/system/docker.service.d
sudo systemctl daemon-reload
sudo systemctl restart docker



sudo apt-get update && apt-get install -y apt-transport-https curl
sudo curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list
sudo apt-get update && apt-get install -y kubelet kubeadm kubectl
sudo apt-mark hold kubelet kubeadm kubectl
