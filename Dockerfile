FROM adoptopenjdk/openjdk11:latest
MAINTAINER Nathan Devroy
VOLUME /main-app
ADD build/libs/upload-0.0.1-SNAPSHOT.jar app.jar
ARG PRIVATE_SSH_KEY
RUN  apt-get -yq update && \
     apt-get -yqq install ssh
RUN mkdir -p /root/.ssh && \
    chmod 0700 /root/.ssh && \
    ssh-keyscan 192.168.1.100 > /root/.ssh/known_hosts && \
    echo "$PRIVATE_SSH_KEY" > /root/.ssh/id_rsa && \
    chmod 600 /root/.ssh/id_rsa

EXPOSE 8888
CMD ["java","-jar","/app.jar"]