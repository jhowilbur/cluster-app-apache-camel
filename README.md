# Wilbur Randoli

Project developed by Wilbur to Randoli.

In the steps below I will describe the project and its purpose.

### Project
A REST API using with Enterprise Integration Patterns with `Apache Camel` to perform CRUD operations to another 
application with `Spring Boot, Hibernate, PostgreSQL, Container and Cloud Concepts` to save Events.

You can find details how run application below, and how to use the API, inside project exists a file 
`Randoli.postman_collection.json` with more details about request.

### Architecture

The challange request a new REST endpoint that accepts a json payload and saves it in the
database. Each payload may contain one or more records. Each record contains one or more events.
To save this without change Client API I used the SPLIT pattern.
![image](https://user-images.githubusercontent.com/59379254/172300435-4e99202f-0cbc-4b13-a785-2940c6dd829e.png)
[apachecamel](https://camel.apache.org/components/3.17.x/eips/split-eip.html)

- Example of `Payload`:
```json
{
  "batchId":"0310abf6-d1f5-a1b3-8fb0-36fe934b1f28",
  "records":[
    {
      "transId":"0000abf8-d1f5-4536-8fb0-36fe934b1f28",
      "transTms":"20151022102011927EDT",
      "rcNum":"10002",
      "clientId":"RPS-00001",
      "event":[
        {
          "eventCnt":1,
          "locationCd":"DESTINATION",
          "locationId1":"T8C",
          "locationId2":"1J7",
          "addrNbr":"0000000001"
        },
        {
          "eventCnt":2,
          "locationCd":"CUSTOMER NUMBER",
          "locationId1":"0007316971"
        },
        {
          "eventCnt":3,
          "locationCd":"OUTLET ID",
          "locationId1":"I029"
        }
      ]
    }
    ...
]}
```

To access the application you need to use the following URL:
- [AWS/EC2 with K8S - http://camel.wilbur.cloud](http://camel.wilbur.cloud)


Actually exists two EC2 instances in the AWS with this application:

One master instance and one slave instance.

The master contains Rancher to manipulate others VMs.
- [RANCHER AWS/EC2 - https://rancher.wilbur.cloud](https://rancher.wilbur.cloud)

Example above, but with one master and 3 slaves (in our case we just have one slave to reduce cost)
![image](https://user-images.githubusercontent.com/59379254/172294538-3c54c247-a40f-4037-bb61-1d48a990dc60.png)

-----------------------------------------------------------------

# How to run the application

## To run application in development mode

- to run with mvn in development mode (with automatic H2 database)
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

- after start application to analyze REST API with swagger-ui
```
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs/
```

- to test application - unit tests
```
mvn clean package -Dtest=EventRespositoryTest
```
- to test application in DEV mode - integration tests
```
mvn -Dspring.profiles.active=dev test -Dtest=ResourcesIntegrationTest
```

-----------------------------------------------------------------

## To run application with containerized environment

### Docker
To run application with Docker, stay in the same directory with `Dockerfile` and run:

- package application
```
mvn clean package -Dtest=EventRespositoryTest
```
- build container
```
docker build --tag wilbur-randoli:1 .
```
- run app container in DEV mode
```
docker run -p 8080:8080 -e ENV_APP="dev" wilbur-randoli:1
```

- after start application to analyze REST API with swagger-ui
```
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs/
```

------------------

- run app container in PROD mode need run postgres container first
```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=admin postgres
```
- run app container in PROD mode
```
docker run -p 8080:8080 wilbur-randoli:1
```

-----------------------------------------------------------------

### Docker Compose
To run application with Docker Compose
```
docker-compose up
```

- after start application to analyze REST API with swagger-ui
```
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs/
```

To access pgAdmin to see database, after `docker-compose up` you can go to `http://localhost:16543` and insert details below:
- In Host name/address, inform the name of the container that corresponds to the PostgreSQL instance (postgres-compose);
- In Port set the value 5432 (default port for accessing the container and available from the postgres-compose-network);
- In the Username attribute, the PostgreSQL default user (postgres) will be informed, as well as the corresponding password in Password (admin).

-----------------------------------------------------------------

![image](https://user-images.githubusercontent.com/59379254/172307836-a7443eb8-e77c-46da-8aab-87dd483a9c17.png)
