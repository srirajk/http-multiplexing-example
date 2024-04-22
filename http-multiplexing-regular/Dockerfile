# Use the official maven/Java 17 image to create a build artifact.
FROM maven:3.8.1-openjdk-17 AS build

# Set the working directory in the image to /app
WORKDIR /app


# Copy the pom.xml file of the common project to our app directory
COPY ../common/pom.xml ./common/

# Copy the source code of the common project
COPY ../common/src ./common/src

COPY ../http-multiplexing-regular/src ./src

COPY ../http-multiplexing-regular/pom.xml .

# Build the common project
RUN mvn -f ./common/pom.xml install -DskipTests

## Package the application
RUN mvn clean install package -DskipTests


## Use OpenJDK 17 to run the application
FROM openjdk:17.0.2-jdk

## Copy the jar file from the build stage
COPY --from=build /app/target/*.jar /app.jar

## Set the startup command
CMD ["java", "-jar", "-Dserver.port=8080", "/app.jar"]