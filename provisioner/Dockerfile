FROM openjdk:11
COPY target/provisioner-3.0.0-jar-with-dependencies.jar provisioner-3.0.0-jar-with-dependencies.jar
COPY etc/ etc
ENV ENCRYPTION_PASSWORD=123

CMD jar -xf provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    cat application.properties && \
    sed -ie "s#^message.broker.host=.*#message.broker.host=$RABBITMQ_HOST#" application.properties && \ 
    sed -ie "s#^sure-tosca.base.path=.*#sure-tosca.base.path=$SURE_TOSCA_BASE_PATH#" application.properties && \
    ENCRYPTION_PASSWORD=`date +%s | sha256sum | base64 | head -c 32 ; echo` && \
    sed -ie "s#^secret=.*#secret=$ENCRYPTION_PASSWORD#" application.properties && \
    echo "cloud.storm.db.path=/etc/UD" >> application.properties && \
    cat application.properties && \
    jar -uf provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    sleep 5 && \
    java -jar provisioner-3.0.0-jar-with-dependencies.jar
