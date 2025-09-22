FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Build the specific module and its dependencies
ARG MODULE
RUN mvn clean package -pl ${MODULE} -am -DskipTests

FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR from the build stage
ARG MODULE
COPY --from=build /app/${MODULE}/target/${MODULE}-1.0.0.jar app.jar

# Expose the port (assuming default Spring Boot port 8080, adjust if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
