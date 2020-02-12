FROM ubuntu:18.04

RUN apt-get update && apt-get install -y openssh-server ansible openjdk-11-jre-headless python3.8  python3-venv maven
RUN mkdir /var/run/sshd
# RUN echo 'root:XXX' | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile
RUN mkdir -p ~/.ssh

EXPOSE 22

CMD echo $PUBLIC_KEY >> ~/.ssh/authorized_keys &&\
    /usr/sbin/sshd -D
