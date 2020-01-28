FROM openjdk:11
COPY target/drip-provisioner-3.0.0-jar-with-dependencies.jar drip-provisioner-3.0.0-jar-with-dependencies.jar
COPY etc/ etc

CMD jar -xf drip-provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    sed -ie "s/^message.broker.host=.*/message.broker.host=$RABBITMQ_HOST/" application.properties && \ 
    sed -ie "s/^sure_tosca.base.path=.*/sure-tosca.base.path=$SURE_TOSCA_BASE_PATH/" application.properties && \
    cat application.properties && \
    jar -uf drip-provisioner-3.0.0-jar-with-dependencies.jar application.properties && \
    java -jar drip-provisioner-3.0.0-jar-with-dependencies.jar
