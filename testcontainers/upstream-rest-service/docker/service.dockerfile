FROM maven:3.9.6-amazoncorretto-21-debian AS prepare

WORKDIR /app/build
COPY . .
RUN mvn clean package

FROM amazoncorretto:21-al2023-jdk AS execution

ARG user=autumnkids
ARG group=Development

RUN yum install -y shadow-utils && yum clean all
RUN groupadd ${group}
RUN useradd ${user}

WORKDIR /app
COPY --from=prepare --chown=${user}:${group} /app/build/target/upstream-rest-service.jar ./upstream-rest-service.jar
COPY --from=prepare --chown=${user}:${group} /app/build/docker/entrypoint.sh ./entrypoint.sh
EXPOSE 8080
USER ${user}
ENTRYPOINT [ "/bin/sh", "/app/entrypoint.sh" ]
