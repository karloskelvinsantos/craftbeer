# Alpine Linux com OpenJDK 11 JRE
FROM azul/zulu-openjdk-alpine:11

# Copiar pacoce .jar e renomear para craftbeer.war
COPY craftbeer-0.0.1-SNAPSHOT.jar /craftbeer.war

# Executar a aplicação
CMD ["/usr/bin/java", "-jar", "/craftbeer.war"]