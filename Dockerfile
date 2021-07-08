#FROM        library/openjdk:latest AS builder
#
#ENV         GRADLE_USER_HOME /data/gradle_cache
#
#ARG         GROUP_NAME=chaosmin
#ARG         PROJECT_NAME=chaosmin-cdpt
#
#WORKDIR     /${GROUP_NAME}/${PROJECT_NAME}
#RUN         echo $GRADLE_USER_HOME
#COPY        / .
#RUN         ./gradlew clean
#RUN         ./gradlew bootJar --build-cache -x test

FROM        openjdk:8u222-jre

ARG         PROJECT_NAME=chaosmin-cdpt
ARG         PROJECT_VERSION=0.0.1.SNAPSHOT

WORKDIR     /root/

#COPY        --from=builder /${GROUP_NAME}/${PROJECT_NAME}/build/libs/${PROJECT_NAME}.jar .
COPY        build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar .

RUN         ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN         echo 'Asia/Shanghai' >/etc/timezone

RUN         echo "java -server -Xms518m -Xmx1024m -jar /root/${PROJECT_NAME}-${PROJECT_VERSION}.jar -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL" > ./run.sh
RUN         chmod +x ./run.sh

CMD         "./run.sh"