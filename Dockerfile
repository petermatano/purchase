FROM openjdk:8-jdk-alpine

ARG baseDirectory="/usr/share/squaretrade"
ENV BASE_DIRECTORY=${baseDirectory}

ARG jarFile="purchase-0.0.1-SNAPSHOT.jar"
ENV JAR_FILE=${jarFile}

ARG buildDir="target"
ENV BUILD_DIR=${buildDir}

RUN apk update && apk upgrade

ADD $BUILD_DIR/$JAR_FILE $BASE_DIRECTORY/$JAR_FILE

EXPOSE 8080

ENTRYPOINT /usr/bin/java -jar $BASE_DIRECTORY/$JAR_FILE