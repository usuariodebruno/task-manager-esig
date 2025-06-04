# Use a imagem oficial do WildFly 27.0.1.Final com JDK 11
FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk11

# Copie o WAR gerado para o diretório de deploy do WildFly
COPY target/task-manager.war /opt/jboss/wildfly/standalone/deployments/