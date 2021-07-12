#!/bin/bash

echo "Running upload project"
./gradlew clean build

docker build -t upload-app .
docker run -p8888:8888 upload-app:latest

#java -jar build/libs/upload-0.0.1-SNAPSHOT.jar
