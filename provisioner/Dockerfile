FROM openjdk:11
COPY target/provisioner-3.0.0-jar-with-dependencies.jar provisioner-3.0.0-jar-with-dependencies.jar
COPY etc/ etc

CMD jar -xf provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    echo "----------------------------" && \
    cat application.properties && \
    sed -ie "s#^message.broker.host=.*#message.broker.host=$RABBITMQ_HOST#" application.properties && \ 
    sed -ie "s#^sure-tosca.base.path=.*#sure-tosca.base.path=$SURE_TOSCA_BASE_PATH#" application.properties && \
    sed -ie "s#^cloud.storm.secret=.*#cloud.storm.secret=$CLOUD_STORM_SECRET#" application.properties && \
    sed -ie "s#^credential.secret=.*#credential.secret=$CREDENTIAL_SECRET#" application.properties && \
    sed -ie "s#^cloud.storm.db.path=.*#cloud.storm.db.path=/etc/UD#" application.properties && \
    echo "" >> application.properties && \
    echo "cloud.storm.db.path=/etc/UD" >> application.properties && \
    cat application.properties && \
    echo "----------------------------" && \
    jar -uf provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    sleep 5 && \
    java -jar provisioner-3.0.0-jar-with-dependencies.jar

    
