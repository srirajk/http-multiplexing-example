# Use the official maven/Java 17 image to create a build artifact.
FROM maven:3.9.6-amazoncorretto-21-al2023 AS build

# Set the working directory in the image to /app
WORKDIR /app


# Copy the pom.xml file of the common project to our app directory
COPY ../common/pom.xml ./common/

# Copy the source code of the common project
COPY ../common/src ./common/src

COPY ../http-multiplexing-virtual-threads/src ./src

COPY ../http-multiplexing-virtual-threads/pom.xml .

# Build the common project
RUN mvn -f ./common/pom.xml install -DskipTests

## Package the application
RUN mvn clean install package -DskipTests


## Use OpenJDK 17 to run the application
FROM amazoncorretto:21-alpine3.19

## Copy the jar file from the build stage
COPY --from=build /app/target/*.jar /app.jar

## Set the startup command
CMD ["java", "-jar", "-Dserver.port=8080", "/app.jar"]