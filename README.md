## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

# Список выполненных задач:

- 2 -> Удалены социальные сети: vk, yandex.
- 3 -> Секьюрная информация вынесена в отдельный файл свойств (application.properties).
- 4 ->  Переделаны тесты для использования базы данных в памяти (H2) вместо PostgreSQL во время тестирования. Определены два бина.
- 5 ->  Написаны тесты для всех публичных методов контроллера ProfileRestController.
- 6 ->  Рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload с использованием NIO.
- 9 ->  Написан Dockerfile для основного сервера.
- 10 ->  Написан файл docker-compose для запуска контейнера сервера вместе с db и nginx. Файл конфигурации (nginx.conf) был отредактирован