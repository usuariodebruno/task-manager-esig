# Use a imagem oficial do WildFly
FROM quay.io/wildfly/wildfly:27.0.1.Final

# Copie o WAR gerado para o diretório de deploy do WildFly
COPY target/task-manager.war /opt/jboss/wildfly/standalone/deployments/