# Используем официальный образ OpenJDK
FROM openjdk:17-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR файл в контейнер
COPY target/lb_mongodb-1.0-SNAPSHOT.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]