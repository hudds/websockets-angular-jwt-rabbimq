


# Como rodar o projeto

Necessário ter apenas o docker installado

## Build e Execução

### Sistemas LINUX

1. Rode o comando `sh build` na pasta raiz do projeto
2. Rode o comando `sh run`

Acesse http://localhost:4200/ para acessar a aplicação

### Windows

Ainda não fiz os scripts para build e execução para Windows. As imagens terão que ser buildadas e executadas manualmente.

Para fazer a build das imagens execute os comandos:

```
docker build -t hudsonprojects/wajr-backend ./backend
docker build -t hudsonprojects/wajr-api ./api
docker build -t hudsonprojects/wajr-backend-nginx ./docker/backend-nginx
docker build -t hudsonprojects/wajr-api-nginx ./docker/courses-api-nginx
docker build -t hudsonprojects/wajr-frontend ./frontend
```

Depois acesse a pasta docker e rode o comando `docker compose up`

## Acessando a aplicação
Acesse http://localhost:4200/ para acessar o frontend.
Acesse http://localhost:8080/swagger-ui/index.html para ver a documentação da api do frontend.
Acesse http://localhost:8081/swagger-ui/index.html para ver a documentação da api da API de Cursos.

O usuário e senha padrão da API de Cursos é definido no arquivo `docker/.env` nas propriedades `API_USER_DEFAULT_USERNAME` e `API_USER_DEFAULT_PASSWORD`

Para aparecer algum curso na dashboard é necessário cadastrar um curso diretamente na API de Cursos.