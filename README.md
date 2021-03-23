## 💻 Como rodar este projeto


Informações API:
[Documentação API](https://documenter.getpostman.com/view/11094438/TVzYguUT)

---
### ⚙ Pré-requisitos

- **Docker**
- **Docker-Compose**
- **Java 11**
- **Postman** - *ou outra ferramente auxiliar para testes de api*

---

### 🧭 Iniciando o docker:

Rode este primeiro comando para iniciar a aplicação com todas funcionalidades ativas.  
- **OBS:** Faça as configurações necessárias no arquivo `docker-compose.yml` para o funcionamento correto da aplicação.
```bash
docker-compose up -d
```

---
##### Configurações extras

Para iniciar o projeto com configurações personalizadas edite o arquivo `docker-compose-test.yml` e rode o comando abaixo

```bash
docker-compose -f docker-compose-test.yml up -d
```
se por algum motivo o projeto não iniciar com as configurações mais recentes utilize o flag `--build`
```bash
docker-compose up -d --build
```

---
### 👥 Perfis
Atualmente há 2 perfis de execução do projeto

| PROFILE | PARA QUE SERVE                                                                    |
| ------- | --------------------------------------------------------------------------------- |
| dev     | Utiliza o banco de dados postgres e requer a conexão com um serviço de email real |
| test    | Utiliza o banco de dados em memoria (H2) e simula o envio de emails               |

A localização das configs de **perfis** e do **serviço de email** estão localizadas em:

```
src/main/resources/application.properties
```
___
### 📁 Config banco de dados:

As configurações do banco de dados foram divididos de acordo com os perfis disponíveis.

- `src/main/resource/application-dev.properties`
- `src/main/resource/application-test.properties`

---
### 🔧 Configurações de inicialização

Edite um dos arquivos `docker-compose.yml` ou crie `variaveis de ambiente` com o mesmo nome.

| NOME           | DESCRIÇÃO                                                                         |
| -------------- | --------------------------------------------------------------------------------- |
| DB_URL         | define a conexão padrão com o postgres                                            |
| DB_DATABASE    | define um nome personalizado para o banco de dados                                |
| DB_PASSWORD    | define a senha para se conectar ao banco de dados                                 |
| DB_USER        | define o nome de usuário do banco de dados                                        |
| PROFILE        | define o contexto de inicialização do aplicação                                   |
| EMAIL          | define o email padrão que será utilizado para enviar email                        |
| EMAIL_PASSWORD | define a senha do email que a aplicação irá utiliza para se conectar              |
| EMAIL_HOST     | define o link do serviço de email que a aplicação irá utilizar para enviar emails |
| JWT_SECRET     | define uma senha personalizada para assinar tokens JWT                            |


### 👤 Usuários

A aplicação está configurada para ter uma carga inicial de usuários para a execução de testes

```
email:      admin@desbravador.com
password:   123456
admin:      sim
```

```
email:      user@desbravador.com
password:   123456
admin:      não
```