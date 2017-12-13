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

# sudo apt-get -y install firewalld
# sudo systemctl start firewalld
# sudo systemctl enable firewalld

# sudo firewall-cmd --add-port=22/tcp --permanent
# sudo firewall-cmd --add-port=2376/tcp --permanent
# sudo firewall-cmd --add-port=2377/tcp --permanent
# sudo firewall-cmd --add-port=7946/tcp --permanent
# sudo firewall-cmd --add-port=7946/udp --permanent
# sudo firewall-cmd --add-port=4789/udp --permanent
# sudo firewall-cmd --reload
# sudo systemctl restart docker


sudo echo iptables-persistent iptables-persistent/autosave_v4 boolean true | sudo debconf-set-selections
sudo echo iptables-persistent iptables-persistent/autosave_v6 boolean true | sudo debconf-set-selections

sudo apt-get -y install iptables-persistent
sudo netfilter-persistent flush

sudo iptables -A INPUT -p tcp --dport 22 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 2376 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 2377 -j ACCEPT
sudo iptables -A INPUT -p tcp --dport 7946 -j ACCEPT
sudo iptables -A INPUT -p udp --dport 7946 -j ACCEPT
sudo iptables -A INPUT -p udp --dport 4789 -j ACCEPT

sudo netfilter-persistent save
sudo systemctl restart docker

    