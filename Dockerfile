# Build Tailwind stage
FROM node:14 AS build-tailwind
WORKDIR /total
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run tw

# Build Java stage
FROM maven:3.9.0-openjdk-17 AS build-java
WORKDIR /total
# Copy the Maven project file(s) and download dependencies
# Copy the application source code and build the JAR
RUN mvn clean package


# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine AS final-run

# Set the working directory in the container
WORKDIR /total

# Copy the application source code
COPY --from=build-java /total/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar","app.jar"]
