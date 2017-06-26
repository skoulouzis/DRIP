FROM agaveapi/myproxy:latest

RUN yum update -y
RUN yum install wget -y
RUN wget https://raw.githubusercontent.com/EGI-FCTF/fedcloud-userinterface/master/fedcloud-ui.sh

#Build: docker build -t drip-myproxy .
# Run: docker run --name drip-myproxy-inst -d drip-myproxy
