FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install dos2unix
RUN dos2unix mvnw

RUN ["./mvnw", "clean", "install"]
CMD ["./mvnw", "spring-boot:run"]
