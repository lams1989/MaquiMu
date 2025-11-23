FROM acrxmtransversal.azurecr.io/amazoncorretto:17.0.16-al2023-headless

ARG JAR_FILE=control-acceso-0.0.1-SNAPSHOT.jar
ARG TZ='America/Bogota '

RUN ln -sf /usr/share/zoneinfo/${TZ} /etc/localtime

COPY ${JAR_FILE} app.jar

USER nobody

ENTRYPOINT ["java", "-jar","/app.jar"]