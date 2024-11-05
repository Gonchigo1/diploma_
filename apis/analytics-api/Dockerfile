FROM eclipse-temurin:17-jre-alpine AS builder

RUN mkdir -p /home/spring
WORKDIR /home/spring
COPY build/libs/analytics-api-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre-alpine

RUN mkdir -p /home/spring/logs
RUN chmod 777 /home/spring/logs
WORKDIR /home/spring
COPY --from=builder /home/spring/dependencies/ ./
COPY --from=builder /home/spring/spring-boot-loader/ ./
COPY --from=builder /home/spring/snapshot-dependencies/ ./
COPY --from=builder /home/spring/application/ ./

# EXPOSE 8880
ENTRYPOINT java $JAVA_OPTS -Dfile.encoding=UTF-8 org.springframework.boot.loader.launch.JarLauncher
