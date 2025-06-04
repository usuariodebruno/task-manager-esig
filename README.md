# Task-Manager-Esig

## Sobre o projeto

Este projeto é um sistema de gestão de tarefas desenvolvido em Java utilizando JSF, JPA (Hibernate), CDI, PostgreSQL e WildFly 27. O objetivo é permitir o cadastro, edição, exclusão, conclusão e listagem de tarefas, com filtros, validações e interface responsiva seguindo a identidade visual da empresa.

### Funcionalidades implementadas

- **a)** Cadastro de tarefas com título, descrição, responsável, prioridade e deadline.
- **b)** Edição de tarefas já cadastradas.
- **c)** Exclusão de tarefas.
- **d)** Conclusão de tarefas (ao concluir, a tarefa sai da lista de "Em andamento").
- **e)** Listagem de tarefas com filtros por número, título/descrição, responsável e situação.
- **f)** Filtro inicial mostra apenas tarefas "Em andamento".
- **g)** Validação de campos obrigatórios.
- **h)** Interface responsiva e com identidade visual da empresa.
- **i)** Testes unitários para as principais classes de negócio.
- **j)** Estrutura de projeto organizada e pronta para deploy em ambiente cloud.

## Instruções para execução local

### 1. Pré-requisitos

- **Java 11 ou superior** instalado
- **Maven 3.8+** instalado (para build e gerenciamento de dependências)
- **Docker** instalado (para rodar o banco de dados)
- **WildFly 27** instalado e configurado (para rodar o arquivo `.war`)
- **Git** (opcional, para clonar o repositório)

### 2. Subindo o banco de dados com Docker

O projeto utiliza **PostgreSQL** como banco de dados, rodando em container Docker.  
Use o seguinte arquivo `docker-compose.yml`:

```yaml
version: '3'
services:
  db:
    image: postgres:14
    restart: always
    environment:
      POSTGRES_USER: esig
      POSTGRES_PASSWORD: esig123
      POSTGRES_DB: tarefas_db
    ports:
      - "5432:5432"
```

Para subir o banco, execute na pasta do projeto:

```sh
docker-compose up -d
```

### 2.1. Criação da base de dados e tabela

Após subir o container do PostgreSQL com Docker, você pode criar a tabela de tarefas utilizando uma ferramenta como o **DBeaver**:

1. Abra o DBeaver e crie uma nova conexão com o banco PostgreSQL usando:
   - Host: `localhost`
   - Porta: `5432`
   - Usuário: `esig`
   - Senha: `esig123`
   - Banco de dados: `tarefas_db`

2. Após conectar, execute o seguinte SQL para criar a tabela utilizada pelo sistema:

```sql
CREATE TABLE tarefas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    responsavel VARCHAR(100),
    prioridade VARCHAR(10) NOT NULL,  -- 'Alta', 'Média', 'Baixa'
    deadline DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'Em andamento'  -- 'Em andamento' ou 'Concluída'
);
```

### 3. Configuração do banco de dados no projeto

- O datasource deve estar configurado no WildFly como `java:/PostgreSQLDS`.
- O arquivo [`src/main/resources/META-INF/persistence.xml`](src/main/resources/META-INF/persistence.xml) já está preparado para usar esse datasource.
- Certifique-se de que o WildFly reconhece o driver do PostgreSQL (adicione o módulo se necessário).

### 4. Build do projeto

O projeto utiliza **Maven** para build e gerenciamento de dependências.  
Para compilar e empacotar o projeto, execute:

```sh
mvn clean install
```

O arquivo `.war` será gerado em `target/task-manager.war`.

### 5. Deploy no WildFly 27

Você pode fazer o deploy da aplicação de duas formas:

**Opção 1: Deploy manual**
- Copie o arquivo `.war` gerado para a pasta `standalone/deployments` do seu WildFly 27.
- Certifique-se de que o datasource `PostgreSQLDS` está configurado e ativo no WildFly.
- Acesse o sistema em:  
  [http://localhost:8080/task-manager](http://localhost:8080/task-manager)

**Opção 2: Deploy via Eclipse (Maneira que eu faço)**
- Baixe o WildFly 27 em https://www.wildfly.org/downloads/
- Extraia o WildFly em uma pasta de sua preferência.
- No Eclipse, vá em **Servers > New > Server** e escolha **WildFly 27+**.
- Aponte para a pasta onde você extraiu o WildFly.
- Adicione o projeto `.war` ao servidor pelo Eclipse (botão "Add and Remove...").
- Inicie o servidor pelo Eclipse e acesse o sistema normalmente.

Essas opções facilitam o deploy tanto para quem prefere linha de comando quanto para quem usa IDE.

### 6. Testes unitários
<!-- 
O projeto possui testes unitários utilizando **JUnit 5** e **Mockito**.  
Para rodar os testes:

```sh
mvn test
``` -->

### 7. Tecnologias utilizadas

- **Java 11+**
- **Jakarta EE 9+ (JSF, CDI, EJB)**
- **JPA 3.1 (Hibernate como implementação)**
- **PostgreSQL 14** (banco de dados relacional)
- **WildFly 27** (servidor de aplicação)
- **Maven** (build e dependências)
<!-- - **Docker** (ambiente do banco de dados)
- **JUnit 5 e Mockito** (testes unitários)
- **PrimeFaces** (componentes JSF adicionais) -->

### 8. Deploy em ambiente cloud

O projeto está estruturado para fácil deploy em ambientes cloud como Heroku, AWS ou Azure.  
Siga as instruções específicas de cada provedor para realizar o deploy da aplicação.

---

**Observações:**
- O projeto segue boas práticas de organização de código e uso de padrões Java EE.
- Para dúvidas ou sugestões, abra uma issue no repositório.
