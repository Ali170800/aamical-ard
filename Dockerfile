# Utilise l’image officielle Tomcat avec Java 11
FROM tomcat:9.0-jdk11

# Supprime les applications par défaut de Tomcat (optionnel mais recommandé)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copie ton fichier .war depuis le projet vers le dossier webapps de Tomcat
COPY amical-ard.war /usr/local/tomcat/webapps/ROOT.war

# Expose le port 8080
EXPOSE 8080

# Lance Tomcat quand le container démarre
CMD ["catalina.sh", "run"]