# МТС.Фитнех Академия
## Финальный проект
### Loan Service
#### Запуск
1. Собрать .jar файл с помощью mvn package
2. В папке проекта запустить команду docker-compose up setup для инициализации паролей для пользователей ELK. Ожидаемый вывод:![image](https://github.com/na0mint/loan-service/assets/114018603/1231e9e9-d847-471a-8ca7-172f89d03770)
3. В папке проекта запустить команду docker-compose up
#### Использование 
##### API 
 В программе есть предустановленные пользователи: user и admin. user'у доступны все url, кроме /loan-service/order c GET запросом для демонстрации всех существующих заявок.
##### Grafana
 Обращаться необходимо по localhost:3030 с данными: admin-admin. Есть 2 предустановленных дашборда: для мониторинга JVM и url'ов с помощью Micrometer.
##### Kibana
Обращаться необходимо по localhost:5601 с данными: elastic-changeme
#### Использованные технологии:
1. JdbcTemplate
2. Liquibase
3. Spring Security
4. Spring Web
5. Mapstruct
6. PostgreSQL
7. H2
8. Resilience4j
9. Lombok
10. Mockito
11. Junit
12. Docker
13. Docker-compose
14. Grafana
15. Prometheus
16. Elasticsearch
17. Logstash
18. Kibana 
