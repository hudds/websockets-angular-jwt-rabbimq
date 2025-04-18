


# Como rodar o projeto

Necessário ter apenas o docker installado

## Configurando o frontend

1. Acesse a pasta `frontend/src/environments`
2. Copie o arquivo `environment.docker.ts.example` para outro arquivo chamado `environment.docker.ts`: `cp environment.docker.ts.example environment.docker.ts`
3. Abra o arquivo `environment.docker.ts`
4. Substitua o IP 192.168.0.105 pelo IP da sua máquina na sua rede local 

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
