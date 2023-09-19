# Build Tailwind stage
#FROM node:18 AS build-tailwind
#WORKDIR /total
#COPY package*.json ./
#RUN npm install
#COPY . .
#RUN npm run tw
#COPY app/src/main/resources /total/app/src/main/resources
# RUN cat app/src/main/resources/static/css/main.css

# Build Java stage
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build-java
WORKDIR /total
#COPY --from=build-tailwind /total/package-lock.json pckg.json
COPY pom.xml .
# Copy the application source code and build the JAR
COPY app app
# Copy the Maven project file(s) and download dependencies
# Copy the application source code and build the JAR
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine AS final-run

# Set the working directory in the container
WORKDIR /total

# Copy the application source code
COPY --from=build-java /total/target/*.jar app.jar

#RUN cat /total/app/src/main/resources/static/css/main.css
EXPOSE 8080
ENTRYPOINT ["java", "-jar","app.jar"]
