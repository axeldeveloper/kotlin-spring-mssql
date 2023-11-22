# Start with a base image containing Java runtime
FROM amazoncorretto:17-alpine-jdk

ENV http_proxy='http://proxy.sgi.ms.gov.br:8081' \
    https_proxy='http://proxy.sgi.ms.gov.br:8081' \
    ENVIRONMENT=develop \
    LC_ALL=pt_BR.UTF-8 \
    LANG=pt_BR.UTF-8 \
    APP_URLS=http://*:8008

# Create a directory
WORKDIR /app

# Copy all the files from the current directory to the image
COPY . .

# build the project avoiding tests
RUN ./gradlew build
# RUN ./gradlew bootRun
# RUN ./gradlew clean build -x test

# Expose port 8080
EXPOSE 8004

# Run the jar file
CMD ["java", "-jar", "./build/libs/demo-0.0.1-SNAPSHOT.jar"]