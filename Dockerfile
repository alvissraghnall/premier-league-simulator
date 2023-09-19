WORKDIR /total
COPY . .

# Build Tailwind stage
FROM node:14 AS build-tailwind
WORKDIR /total
COPY package*.json ./
RUN npm install

RUN npm run tw

# Build Java stage
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build-java
WORKDIR /total
COPY --from=build-tailwind /total/package-lock.json pckg.json
# Copy the Maven project file(s) and download dependencies
# Copy the application source code and build the JAR
RUN mvn clean package


# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine AS final-run

# Set the working directory in the container
WORKDIR /total

# Copy the application source code
COPY --from=build-java /total/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar","app.jar"]
