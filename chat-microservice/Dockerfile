# Use the official Maven/Java image for building
FROM maven:3.8.3-openjdk-17 AS builder

# Copy the entire project to the working directory
COPY . /app

# Set the working directory
WORKDIR /app

# Build the project
RUN mvn clean package -DskipTests

# Use a smaller JRE image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder image
COPY --from=builder /app/target/chat-microservice-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your application runs on (if applicable)
EXPOSE 8091

# Define the command to run your application
CMD ["java", "-jar", "/app/app.jar"]
