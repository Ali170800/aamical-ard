FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Ajout de l'option -e pour voir le détail de l'erreur Maven
RUN mvn -e clean package -DskipTests

FROM tomcat:10-jdk17-openjdk-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
