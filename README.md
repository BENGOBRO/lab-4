# Лабораторная работа №4

## Предисловие
Сервис GitLab требует верификации аккаунта для запуска CI/CD пайплайнов, а верификация заключается в предоставлении данных зарубежных банковской карты и номера телефона.
В связи с невозможностью верифицировать аккаунт был выбран сервис GitHub и Actions для настройки пайплайна, так как в нем не требуется верифицировать аккаунт таким образом.

## Проект
Для выполнения работы был использован старый pet-проект, в котором ранее настраивался пайплайн.

## Стек технологий
- Java 17
- Spring Boot 3
- Gradle - система сборки проекта
- Lombok

## Выполнение работы
### Задачи в пайплайне
В пайплайне будут выполняться следующие задачи: сборка проекта, упаковка в docker-образ, отправка образа в Docker Hub.
Так как у меня нет сервера, этап деплоя сервиса из Docker Hub на сервер производиться не будет.

### Dockerfile
В проекте описан самый простой Dockerfile. Из него будет собираться нужный образ с .jar файлом

*Его также можно улучшить, облегчив сам образ*

### Работа с Actions
Для работы GitHub Actions необходим файл .github/workflow/<имя>.yml. 
Тогда Actions автоматически будет читать этот файл.
Actions использует специфический синтаксис для описания .yml файлов, поэтому, для начала работы необходимо изучить официальную документацию.
В .yml файле необходимо задать имя процесса (name).
С помощью строки on мы задаем события, при которых будет запускаться пайплайн.
В нашем случае пайплайн будет запускаться при push и pull request с веткой main.
Задачи, которые будут выполняться пайплайном, указываются через строку jobs.

*Все дальнейшие шаги, связанные с настройкой окружения, описаны в .yml.*

Также можно использовать заготовленные скрипты из Actions Marketplace для ускорения процесса написания пайплайна.
В этой работе будут использованы скрипты из наборов actions/checkout@v3, actions/setup-java@v3, gradle/gradle-build-action@v2, docker/login-action@v3

actions/checkout@v3 - набор скриптов для настройки Git репозитория внутри виртуальной машины
![image](https://github.com/user-attachments/assets/2419a7e7-ea94-4824-a01d-23f0f18b615d)

actions/setup-java@v3 - набор скриптов для настройки окружения java
![image](https://github.com/user-attachments/assets/22cf259a-77a4-46ce-a3bb-966ee10561b7)

actions/gradle-build-action@v2 - набор скриптов для настройки Gradle
![image](https://github.com/user-attachments/assets/878a795f-2a91-41fe-b6b9-b03a9ba1c8cd)

docker/login-action@v3 - набор скриптов для аутентификации и авторизации в сервисе Docker Hub
![image](https://github.com/user-attachments/assets/1c9e9441-42c3-4ac0-8214-7bf8422fae54)

Перед выполнением этих шагов неявно отрабатывает шаг настройки виртуальной машины
![image](https://github.com/user-attachments/assets/b030d398-0cd7-446e-ac5a-28b93cf8cccb)

После выполнения всех шагов неявным образов выполняются шаги по остановке всех запущенных утилит в шагах, а также выхода из Docker Hub.
Помимо этого завершается работа виртуальной машины.
![image](https://github.com/user-attachments/assets/64eb71db-b2f4-4c21-ac4a-aa381fe8e8da)

После завершения пайплайна мы можем заново запустить пайплайн, а в случае падения одного из шагов, можем перезапустить пайплайн с конкретным шагом выполнения.

### Выполнение пайплайнов
В проекте настроены два пайплана bad-pipeline.yml и good-pipeline.yml, содержащие в себе разные практики CI/CD.
Оба пайплайна запускаются автоматически при push и pull_request в ветку main.
![image](https://github.com/user-attachments/assets/2997b7dd-a6a3-4af6-9d58-2970973a027a)

*Для удобства bad_pipeline.yml запускается при push ветки feature/update-readme, а good_pipeline.yml при push ветки main.*

### bad-pipeline.yml
Для удобства этот пайплайн не запускается при push

Основой пайплайна является его структура, и плохая практика - смешивание задач.
В этом файле смешаны задача сборки и тестирования проекта с задачей сборки образа docker и его отправки в Docker Hub.
Также имеются неправильные названия шагов задачи: Run build with Gradle Wrapper не отражает реальных действий в шаге, так как на этом шаге также прогоняются тесты проекта.
![image](https://github.com/user-attachments/assets/f8ae8e13-3eec-49db-8305-8346ec625fe2)

На этапах сборки образа, создания нового тега и отправки образа названия тегов и репозитория захардкожены.
![image](https://github.com/user-attachments/assets/6122b78f-e54f-4804-8488-0ef55f04d9a9)

Также захардкожены версии Java и Gradle, не указывается версия образа (по умолчанию latest).
![image](https://github.com/user-attachments/assets/1afb69e4-78fd-44dc-a9a9-40e3719c016f)

Gradle в pipeline указан с версией 8.1.1, а в проекте используется версия 7.6.
В данном случае все собирается, но лучше синхронизировать версии используемых утилит.
![image](https://github.com/user-attachments/assets/79ad0792-f735-4dee-a697-cd79a4d8ccf9)
![image](https://github.com/user-attachments/assets/329634e3-8fb4-4aaf-b3fa-a4b91f3f5492)

Задачи выполняются параллельно - это означает, что в сервер может взять старый образ из Docker Hub.


После исполнения пайплайна вылезает warnings.
Их часто игнорируют, но в один момент могут упасть все пайплайны, и скорее всего это будет самый неподходящий момент.
В нашем случае нас предупреждают, некоторые actions используют старую версию ноды. 
![image](https://github.com/user-attachments/assets/39edd3c3-ae8e-4b5c-80d0-fd19740cb93f)


### good-pipeline.yml
В этом файле были разделены этапы тестирования приложения, отправки образа в Docker Hub и деплоя сервиса на удаленный сервер.
![image](https://github.com/user-attachments/assets/f7c5e911-492d-43b6-a07d-8f055dcc9786)

Так как bad-pipeline был написан правильно и собирался без ошибок, то итоговый результат не изменился.
Однако теперь контроль над версиями утилит происходит в репозитории, а не локально на ПК.

С повышением версии gradle/gradle-build-action теперь появляется другой warning.
Его пофиксить не удалось.
![image](https://github.com/user-attachments/assets/c3ecd8e3-a9b4-4258-95d4-b3bad18fffc4)
