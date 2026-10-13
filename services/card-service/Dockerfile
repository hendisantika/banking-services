FROM maven:3.9-eclipse-temurin-26 AS build
LABEL authors="hendisantika"

WORKDIR /app

COPY ./pom.xml .

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine AS final

WORKDIR /app

COPY --from=build /app/target/*.jar cards.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "cards.jar"]