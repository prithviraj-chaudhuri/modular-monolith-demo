FROM maven:3.9.4-openjdk-17 AS build

WORKDIR /app

# Copy pom files for dependency resolution
COPY pom.xml .
COPY common/pom.xml common/
COPY gateway/pom.xml gateway/
COPY inventory/pom.xml inventory/
COPY orders/pom.xml orders/
COPY frontend/pom.xml frontend/

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY . .

# Build argument for module selection
ARG MODULE

# Build the specific module
RUN mvn package -pl $MODULE -am -DskipTests

FROM openjdk:17-jdk-slim

# Build argument for module selection
ARG MODULE

WORKDIR /app

# Copy the built JAR from the build stage
RUN find /app -name "*.jar" -path "*/$MODULE/target/*" -exec cp {} /app/app.jar \; || echo "JAR not found"

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
