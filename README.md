# nosql_template


## Предварительная проверка заданий

<a href=" ./../../../actions/workflows/1_helloworld.yml" >![1. Согласована и сформулирована тема курсовой]( ./../../actions/workflows/1_helloworld.yml/badge.svg)</a>

<a href=" ./../../../actions/workflows/2_usecase.yml" >![2. Usecase]( ./../../actions/workflows/2_usecase.yml/badge.svg)</a>

<a href=" ./../../../actions/workflows/3_data_model.yml" >![3. Модель данных]( ./../../actions/workflows/3_data_model.yml/badge.svg)</a>

<a href=" ./../../../actions/workflows/4_prototype_store_and_view.yml" >![4. Прототип хранение и представление]( ./../../actions/workflows/4_prototype_store_and_view.yml/badge.svg)</a>

<a href=" ./../../../actions/workflows/5_prototype_analysis.yml" >![5. Прототип анализ]( ./../../actions/workflows/5_prototype_analysis.yml/badge.svg)</a> 

<a href=" ./../../../actions/workflows/6_report.yml" >![6. Пояснительная записка]( ./../../actions/workflows/6_report.yml/badge.svg)</a>

<a href=" ./../../../actions/workflows/7_app_is_ready.yml" >![7. App is ready]( ./../../actions/workflows/7_app_is_ready.yml/badge.svg)</a>


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
