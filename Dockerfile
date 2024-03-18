FROM openjdk:17
EXPOSE 8090
ADD target/bank-api.jar bank-api.jar
ENTRYPOINT ["java","-jar","/bank-api.jar"]