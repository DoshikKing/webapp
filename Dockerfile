FROM maven:3.8.6-eclipse-temurin-19-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true
RUN mvn -f /home/app/pom.xml install -Dmaven.test.skip=true
ONBUILD RUN echo "Сборка и запуск произведены. Автор: Магин К. А."

FROM openjdk:19-jdk-alpine3.16
LABEL maintainer="Magin.K.A.IKBO-10-19"
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
ONBUILD RUN echo "Сборка и запуск произведены. Автор: Магин К. А."