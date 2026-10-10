# todo-app

Консольное приложение списка задач (todo list) на Java. Учебная практическая работа:
Java + Gradle + Git + GitHub Flow + CI/CD (GitHub Actions, автоматические релизы и
публикация Maven-пакета в GitHub Packages).

- Репозиторий: https://github.com/Tuxxxal/tuxxxal-todo-app
- Group ID: `org.example`
- Artifact ID: `todo-app`
- Текущая версия: `0.3.0` (задаётся в `gradle.properties`)

## Возможности

- `add <task>` — добавить задачу (пробелы по краям обрезаются, пустые строки игнорируются)
- `remove <index>` — удалить задачу по индексу
- `done <index>` — отметить задачу выполненной
- `search <text>` — найти задачи по подстроке без учёта регистра
- `clear` — удалить все задачи
- `list` — показать все задачи (`[x]` — выполнена, `[ ]` — активна)
- `help` — список команд
- `exit` — выход

Пример сессии:

```text
> add buy milk
Added: buy milk
> add write report
Added: write report
> done 0
Task #0 is done.
> list
0: [x] buy milk
1: [ ] write report
> search MILK
0: [x] buy milk
> clear
Removed 2 task(s).
> exit
Bye!
```

## Требования

- JDK 23 (Temurin)
- Gradle берётся из wrapper (`./gradlew`), системный Gradle не нужен

> Windows: если в пути к профилю пользователя есть кириллица, Gradle может падать на
> тестах с `Could not find or load main class
> worker.org.gradle.process.internal.worker.GradleWorkerMain`. Решение — задать
> `GRADLE_USER_HOME` в ASCII-путь, например `C:\Android\gradle-home`.

## Сборка и тесты

```bash
./gradlew clean build
```

Тесты (JUnit 5):

```bash
./gradlew test
```

Собирается «толстый» (fat) JAR с помощью плагина `com.gradleup.shadow`:
`build/libs/todo-app-<version>.jar`.

## Запуск

```bash
java -jar build/libs/todo-app-<version>.jar
```

Например:

```bash
java -jar build/libs/todo-app-0.3.0.jar
```

## Публикация Maven-пакета

Координаты: `org.example:todo-app:<version>`, репозиторий — GitHub Packages
(`https://maven.pkg.github.com/Tuxxxal/tuxxxal-todo-app`).

```bash
./gradlew publish
```

Учётные данные берутся из `gpr.user` / `gpr.key` в `gradle.properties` или из
переменных окружения `GITHUB_ACTOR` / `GITHUB_TOKEN`.

## GitHub Actions (CI/CD)

| Workflow | Файл | Триггер | Что делает |
| --- | --- | --- | --- |
| **CI / Build & Release** | `.github/workflows/ci.yml` | push в `main` | сборка и тесты (`./gradlew --no-daemon clean build`), затем GitHub Release с тегом `v<version>` и прикреплённым `todo-app-<version>.jar` |
| **Publish to GitHub Packages (Maven)** | `.github/workflows/publish-package.yml` | `workflow_run` после успешного `CI / Build & Release` | `./gradlew publish` — публикация fat-JAR в GitHub Packages |
| **Nightly Build** | `.github/workflows/nightly.yml` | `schedule` (`0 2 * * *`) и ручной `workflow_dispatch` | `./gradlew --no-daemon clean build`, без релиза и публикации |

Если тесты падают, job тоже падает — релиз и публикация не выполняются. Если тег `v<version>`
уже существует, workflow не падает: релиз обновляется.

Полезные ссылки:

- Actions: https://github.com/Tuxxxal/tuxxxal-todo-app/actions
- Releases: https://github.com/Tuxxxal/tuxxxal-todo-app/releases
- Packages: https://github.com/Tuxxxal/tuxxxal-todo-app/packages

## Процесс разработки (GitHub Flow)

1. Новая задача — новая ветка от `main` (`feat/...`, `fix/...`, `docs/...`).
2. Частые небольшие коммиты с осмысленными сообщениями (по-английски).
3. Pull Request в `main`.
4. Ревью и merge (`gh pr merge --merge`).
5. `git checkout main && git pull`.
6. После merge в `main` автоматически запускаются CI, релиз и публикация пакета.

Прямых коммитов в `main` нет, кроме начальной настройки проекта.

## Структура проекта

```text
src/main/java/org/example/
  Task.java      — задача: текст и статус выполнения
  TodoList.java  — хранилище задач (add/remove/done/clear/search)
  TodoApp.java   — консольная оболочка (Scanner)
src/test/java/org/example/
  TodoListTest.java              — базовые тесты
  TodoListExtraCommandsTest.java — тесты clear/done/search
```

## Стиль кода

Классы — `PascalCase`, методы и переменные — `camelCase`, константы — `UPPER_SNAKE_CASE`,
идентификаторы на английском без кириллицы и транслита.
