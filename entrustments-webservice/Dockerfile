FROM openjdk:8-jre-alpine
COPY target/entrustments-webservice-*.jar /usr/src/myapp/entrustments-webservice.jar
WORKDIR /usr/src/myapp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "entrustments-webservice.jar"]