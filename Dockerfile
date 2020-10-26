FROM openjdk:11

WORKDIR /app

COPY target/servidor-relatorios.jar /app

ARG db_url
ARG db_database
ARG db_user
ARG db_password


ENV DB_URL=${db_url}
ENV DB_DATABASE=${db_database}
ENV DB_PASSWORD=${db_password}
ENV DB_USER=${db_user}

EXPOSE 8080

CMD [\
    "java", \
    "-Xms128m", \
    "-Xmx256m", \
    "-jar", \
    "servidor-relatorios.jar" \
]