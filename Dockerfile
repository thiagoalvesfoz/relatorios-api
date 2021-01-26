########### COMPILER APPLICATION ##############
FROM maven:3.6-jdk-11-slim AS build

RUN mkdir -p /workspace
WORKDIR /workspace

COPY pom.xml /workspace
COPY src /workspace/src

RUN mvn clean package -DskipTests

########### RUN APPLICATION ##############
FROM openjdk:11

WORKDIR /app
COPY --from=build /workspace/target/servidor-relatorios.jar /app

ARG db_url
ARG db_database
ARG db_user
ARG db_password
ARG profile
ARG email
ARG email_password
ARG jwt_secret

ENV DB_URL=${db_url}
ENV DB_DATABASE=${db_database}
ENV DB_PASSWORD=${db_password}
ENV DB_USER=${db_user}
ENV PROFILE=${profile}
ENV EMAIL=${email}
ENV EMAIL_PASSWORD=${email_password}
ENV JWT_SECRET=${jwt_secret}

EXPOSE 8080

CMD [\
    "java", \
    "-Xms128m", \
    "-Xmx256m", \
    "-jar", \
    "servidor-relatorios.jar" \
]