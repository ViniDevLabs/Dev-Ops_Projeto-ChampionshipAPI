FROM maven:3.9.9-eclipse-temurin-21-alpine
RUN mkdir /root/api && mkdir /root/.m2
COPY . /root/api
WORKDIR /root/api
RUN mvn clean package -DskipTests
RUN mv target/*.jar target/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/app.jar", \
"--spring.profiles.active=prod", \
"--spring.jpa.hibernate.ddl-auto=create", \
"--spring.datasource.url=jdbc:postgresql://${POSTGRES_HOST}:5432/${POSTGRES_DB}", \
"--spring.datasource.username=${POSTGRES_USER}", \
"--spring.datasource.password=${POSTGRES_PASSWORD}", \
"--spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"]