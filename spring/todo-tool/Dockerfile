#FROM tomcat:9-jre11
#EXPOSE 8080
#RUN rm -rf /usr/local/tomcat/webapps/*
#COPY ./target/*.war /usr/local/tomcat/webapps/ROOT.war
#CMD ["catalina.sh","run"]
#ENTRYPOINT ["sh","-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/ROOT.war"]

#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.todotool.TodoToolApplication"]

#FROM adoptopenjdk/openjdk11:alpine-jre
#
#LABEL author czetsuya@gmail.com
#
 Refer to Maven build -> finalName
#ARG JAR_FILE=target/terawarehouse-config-server.jar
#
#RUN addgroup -S tomcat && adduser -S tomcat -G tomcat
#USER tomcat
#
# cd /opt/app
#WORKDIR /opt/app
#
# cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
# Use sh to support JAVA_OPTS
# Use ${@} to pass all command arguments
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]

#FROM openjdk:11-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
FROM alpine:edge
RUN apk add --no-cache openjdk11-jdk
RUN addgroup -S appuser && adduser -S appuser -G appuser
VOLUME /tmp
# Creating dirs
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
RUN chown -R appuser:appuser /app
USER appuser:appuser
# The application's jar file
ARG JAR_FILE=target/todo-tool.jar
ADD ${JAR_FILE} /app/app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]