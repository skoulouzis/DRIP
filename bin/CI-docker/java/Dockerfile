FROM maven:3.6.3-jdk-11

MAINTAINER S. Koulouzis

COPY clone_git.sh clone_git.sh
COPY build.sh build.sh
COPY send_to_sonarqube.sh send_to_sonarqube.sh 

CMD ./clone_git.sh $BRANCH $GIT_URL $FOLDER_NAME && \ 
    ./build.sh $FOLDER_NAME && \
    ./send_to_sonarqube.sh $FOLDER_NAME $SONAR_HOST $SONAR_LOGIN
    
    
    
# docker build -t java_ci .
# docker run -it -e BRANCH=develop -e GIT_URL=https://gitlab.articonf.eu:4430/uva/conf.git -e FOLDER_NAME=conf java_ci -e SONAR_HOST=host -e SONAR_LOGIN=secret
