# BankAPI
A backend section of the Bank Management project

## Set up

### Environment variables
- JAVA_HOME=C:\Program Files\Java\jdk-21
- M2_HOME=C:\apache-maven-3.8.8
- MAVEN_OPTS=-Xmx512m
- Project Structure:
  + Project SDK=21 
  + Modules -> Dependencies -> Module SDK=Project SDK 21
- PATH 
  + add a new entry of %M2_HOME%\bin

## Execution

```linux
mvn clean install -Dmaven.test.failure.ignore=false -Dmaven.test.error.ignore=false spring-boot:run
  or
mvn clean install
mvn clean package spring-boot:run
  or
mvn clean install spring-boot:run
```

## Documentations
- Spring Boot JWT Security Explained: Authorization, Authentication, and Token s(v6+)
  + https://www.youtube.com/watch?v=cbasQcUZ3cI