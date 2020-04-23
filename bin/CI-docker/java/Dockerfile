FROM maven:3.6.3-jdk-11

MAINTAINER S. Koulouzis

ARG SONAR_PROPERTIES_FILE=uva-conf.properties

# Install SonarQube Dependency
RUN wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.2.0.1873-linux.zip && \
    unzip sonar-scanner-cli-4.2.0.1873-linux.zip && \
    mv sonar-scanner-4.2.0.1873-linux /opt/sonar-scanner

COPY clone_git.sh clone_git.sh
COPY build.sh build.sh
COPY send_to_sonarqube.sh send_to_sonarqube.sh 
COPY ${SONAR_PROPERTIES_FILE} ${SONAR_PROPERTIES_FILE}

CMD ./clone_git.sh $BRANCH $GIT_URL $FOLDER_NAME && \ 
    ./build.sh $FOLDER_NAME && \
    ./send_to_sonarqube.sh $FOLDER_NAME $SONAR_HOST $SONAR_LOGIN $SONAR_PROPERTIES_FILE
    
    
    
# docker build -t java_ci -build-arg SONAR_PROPERTIES_FILE=uva-conf.properties .
# docker run -it -e BRANCH=develop -e GIT_URL=https://gitlab.articonf.eu:4430/uva/conf.git -e FOLDER_NAME=conf -e SONAR_HOST=https://sonar.articonf.eu/ -e SONAR_LOGIN= -e SONAR_PROPERTIES_FILE=uva-conf.properties -e FOLDER_NAME=conf java_ci
