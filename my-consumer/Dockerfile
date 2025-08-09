from gradle:jdk21 as builder
workdir /app

copy gradlew .
copy gradle gradle

copy build.gradle .
copy settings.gradle .

copy src src/

run --mount=type=cache,target=/home/gradle/.gradle ./gradlew build -x test

from eclipse-temurin:21-jdk as layerextractor
workdir /extracted
arg JAR_FILE

copy --from=builder /app/build/libs/${JAR_FILE} app.jar
run java -Djarmode=layertools -jar app.jar extract

from eclipse-temurin:21-jre
workdir /app

copy --from=layerextractor /extracted/dependencies/ ./
copy --from=layerextractor /extracted/spring-boot-loader/ ./
copy --from=layerextractor /extracted/snapshot-dependencies/ ./
copy --from=layerextractor /extracted/application/ ./

arg OTEL_AGENT_VERSION=2.18.0
add https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

expose 8081

env JAVA_TOOL_OPTIONS="-javaagent:./opentelemetry-javaagent.jar"
entrypoint ["java", "org.springframework.boot.loader.launch.JarLauncher"]
