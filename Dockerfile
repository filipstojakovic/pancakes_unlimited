FROM openjdk:11-jre-slim
WORKDIR /opt/pancakes_unlimited
COPY ./target/*.jar pancakes_unlimited.jar
ARG DEFAULT_PORT=8080
ENV PORT $DEFAULT_PORT
EXPOSE $PORT
ENTRYPOINT [ "java", "-jar", "pancakes_unlimited.jar" ]