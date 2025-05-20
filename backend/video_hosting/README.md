# VideoHosting


## Start with docker

```sh
docker compose build
docker compose up [-d]
```

### Connection to running container

> Connection to running container could be done with `docker exec -it <container_id> bash`

### Logs

```sh
docker compose logs [--follow]
```

### Stop

```sh
docker compose  down [--volumes]
```


## Start application without docker


```bash
java -jar backend\video_hosting\target\video_hosting-1.0-SNAPSHOT.jar
```

## Swagger:

*  [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)


## Test data

### Super user (ADMIN)

```bash
username: test
password: test
```