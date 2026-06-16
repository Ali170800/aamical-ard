<<<<<<< HEAD
# Utilise l’image officielle Tomcat avec Java 11
FROM tomcat:9.0-jdk11

# Supprime les applications par défaut de Tomcat (optionnel mais recommandé)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copie ton fichier .war depuis le projet vers le dossier webapps de Tomcat
COPY amical-ard.war /usr/local/tomcat/webapps/ROOT.war

# Expose le port 8080
EXPOSE 8080

# Lance Tomcat quand le container démarre
=======
# Étape 1 : Compiler le projet avec Maven et Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Lancer Tomcat et y injecter le fichier .war généré
FROM tomcat:10-jdk17-openjdk-slim
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
CMD ["catalina.sh", "run"]