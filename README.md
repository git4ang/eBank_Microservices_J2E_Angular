# eBanque Microservice fullstack project:
### 1- Java EE & Spring as backend
### 2- ***GraphQL & ELK (elasticsearch 7)*** as Databases
### 3- Thymeleaf or Angular 11 as frontend

***
***
Project developed in **Java EE** with **Spring 5 framework** (+ Spring Boot & Spring Cloud), (**Thymeleaf** or **Angular 11**, bootstrap 4) as frontend and (**elasticsearch 7** & **graphQL**) as databases.

- The goal of this project is to implement a Java EE web application on the backend side and Thymeleaf on the frontend side for managing bank accounts.
  This project concerns the implementation of JPA-2, JUnit-Mockito, Hibernate 6, Spring 5 (MVC, IOC, Spring security, Spring boot, Spring cloud ... etc),
  microservices (config-server, eureka-server, gateway-server, open feign, loadbalancer, Hateoas, actuator...etc), JWT, MongoDB, PostgreSQL, MySQL, H2, GIT, Maven, docker,
  Thymeleaf, Boostrap 4… etc.

***
## Backend side: Java EE & Spring 5 (Spring Boot 2.52 & Spring Cloud)

### Technologies:

|            1              |                 2                 |          3            |                   4                   |   
|           ---             |                ---                |         ---           |                  ---                  |
| Java EE 			        | Spring 5		                    | H2 Database			| Unit Test
| Declarative programming	| Spring Boot 2.52	Spring          | Data MongoDB	        | Maven
| Functional programming	| Spring Cloud 2020.0.3	            | PostgreSQL Driver		| Git
| Reactive programming 	    | Spring Reactive Web	            | MySQL DRIVER			| Lombok
| Java JDK-16               | Spring Web						| ELK 7(Elasticsearch, Logstash & Kibana)                      | Spring Boot DevTools
|                           | Spring Security					| GraphQL	            | java-jwt (Json Web Token)
|                           | Spring Data JPA					| 	                    | Spring Cloud Sleuth
|                           | Rest Repositories					|                       | Spring Cloud Sleuth Zipkin
|                           | Spring Data Rest					|                       | Spring Validation (V. Hibernate JPA)
|                           | Spring Cloud Gateway				| 	                    | Springdoc OpenApi 3.0
|                           | Spring Cloud Config client/server	| 		                | Spring Configuration Processor
|                           | Eureka Discovery client/server    |                       | Docker
|                           | Spring HATEOAS                    |                       | Docker-Compose
|                           | Spring Cloud LoadBalancer         |                       |
|                           | Spring Cloud Consul
|                           | Spring Boot Actuator
|                           | Spring Cloud OpenFeign


### Objective and detail of the project

- Each **Account entity** is defined by an accountNumber, a balance and a creation date.
- There are two types of accounts: current account and saving account.
- A ***Current Account entity*** is an account which also has an overdraft.
- A ***Savings Account entity*** is an account which also has an interest rate.
- Each account is owned by a customer and created by an employee.
- Each **Customer entity** is defined by his code, his name and his email.
- An **Employee entity** is also defined by his code and his name, department entity, group/role.
- Each account can have multiple transactions.
- There are two types of **operations**: deposit and withdrawal.
- Each transaction is performed by an employee.
- Each **Transaction entity** is defined by its number, amount, description and date of creation.
- There are three types of operations: Transfer, Payment and withdrawal.
- Each **User reactive entity** is defined by an id, username, Customer entity, email and a collection of roles.
- Each **Role reactive entity** is defined by an id, roleName, collection of users.

***
### The creation of the project is based on the following points that will be detailed below:

- [x] **M01-config-server** module creation.
    - Spring Cloud Config provides server-side and client-side support for externalized configuration in a distributed system.
      With the Config Server, you have a central place to manage external properties for applications across all environments.

***
- [x] **M02-eureka-server** module creation.
    - Eureka Server is an application that holds the information about all client-service applications.
      Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address.

***
- [x] **M03-gateway-server** module creation.
    - An API Gateway is a server that is the single entry point into the system. All requests from clients first go through the API Gateway.
      It then routes requests to the appropriate microservice.
***
- [x] **M04-admin-server** module creation.
    - codecentric’s Spring Boot Admin is a community project to manage and monitor your Spring Boot ® applications. The applications register with our Spring Boot Admin Client (via HTTP) or are discovered using Spring Cloud ® (e.g. Eureka, Consul).

***
- [ ] **M10-employee-microservice** module creation.
    - Set up the M10-employee-microservice module with the creation of the necessary packages such as: entities, repositories, restControllers,
      services, exceptions, proxies ... etc

***
- [ ] **M11-customer-microservice** module creation.
    - Set up the M11-customer-microservice module with the creation of the necessary packages such as: entities, repositories, restControllers,
      services, exceptions, proxies ... etc
  ***
- [ ] **M12-account-microservice** module creation.
    - Set up the M12-account-microservice module with the creation of the necessary packages such as: entities, repositories, restControllers,
      services, exceptions, proxies ... etc

***
- [ ] **M13-operations-microservice** module creation.
    - Set up the M13-operations-microservice module with the creation of the necessary packages such as: entities, repositories, restControllers,
      services, exceptions, proxies ... etc

***
- [ ] **M99-user-microservice** module creation.
    - Set up the M99-user-microservice module with the creation of the necessary packages such as: entities, repositories, restControllers,
      services, exceptions, proxies ... etc

***   
- [ ] ELK 7.13.x - Elasticsearch, logstash & kibana.
    - "ELK" is the acronym for three open source projects: Elasticsearch, Logstash, and Kibana. Elasticsearch is a search and analytics engine. Logstash is a server‑side data processing pipeline that ingests data from multiple sources simultaneously, transforms it, and then sends it to a "stash" like Elasticsearch. Kibana lets users visualize data with charts and graphs in Elasticsearch.
        - [**ELK download**] => https://www.elastic.co/fr/downloads/
***

- [ ] GraphQL - A query language for your API.
    - GraphQL is a query language for APIs and a runtime for fulfilling those queries with your existing data. GraphQL provides a complete and understandable description of the data in your API, gives clients the power to ask for exactly what they need and nothing more, makes it easier to evolve APIs over time, and enables powerful developer tools.
***

- [ ] **Zipkin server** and **Sleuth** for all microservices modules.
    - [ ] **M10-employee-microservice**
    - [ ] **M11-customer-microservice**
    - [ ] **M12-account-microservice**
    - [ ] **M13-operations-microservice**
    - [ ] **M99-user-microservice**
        - **Docker:** _docker run -d -p 9411:9411 openzipkin/zipkin_
        - **Java:** _curl -sSL https://zipkin.io/quickstart.sh | bash -s java -jar zipkin.jar_

***
- [ ] OpenApi 3.0 config for all microservices modules.
    - [ ] **M10-employee-microservice**
    - [ ] **M11-customer-microservice**
    - [ ] **M12-account-microservice**
    - [ ] **M13-operations-microservice**
        - [**Swagger-url employee**] (http://localhost:8080/employee-microservice/swagger-ui.html/)

***
- [ ] Validations JPA for the module **M10-employee-microservice**.
- [ ] Validations JPA for the module **M11-customer-microservice**.
- [ ] Validations JPA for the module **M12-account-microservice**.
- [ ] Validations JPA for the module **M13-operations-microservice**.
- [ ] Validations JPA for the module **M99-user-microservice**.

***
- [ ] Spring Security for the **M01-config-server**
- [ ] Spring Security for the **M02-eureka-server**
- [ ] Spring Security for the **M03-gateway-server**
- [ ] Spring Security for the **M04-admin-server**
- [ ] Spring Security & JWT for the **M10-employee-microservice**
- [ ] Spring Security & JWT for the **M11-customer-microservice**
- [ ] Spring Security & JWT for the **M12-account-microservice**
- [ ] Spring Security & JWT for the **M13-operations-microservice**
- [ ] Spring Security & JWT for the **M99-user-microservice**

***
- [ ] **Keycloak server with OAuth 2.0 & OpenID Connect**
    - Keycloak is an open source Identity and Access Management solution aimed at modern applications and services. It makes it easy to secure applications and services with little to no code.

***
- [ ] Set up minimal security for elk (Elasticsearch)

***    
- [ ] Unit Tests for the module **M10-employee-microservice**.
- [ ] Unit Tests for the module **M11-customer-microservice**.
- [ ] Unit Tests for the module **M12-account-microservice**.
- [ ] Unit Tests for the module **M13-operations-microservice**.
- [ ] Unit Tests for the module **M99-user-microservice**.

***
## Frontend side: (Thymeleaf or Angular 11) & Boostrap 4
***
***

### General functionality:

- [ ] **CRUD M10-employee-microservice**:
    - Creation of component, service, module of Employee, Department, Group entities. (Creation, read, update & delete)

- [ ] **CRUD M11-customer-microservice**:
    - Creation of component, service, module of Customer entity. (Creation, read, update & delete)

- [ ] **CRUD M12-account-microservice**:
    - Creation of component, service, module of Account, Current Account & Saving Account entities. (Creation, read, update & delete)

- [ ] **CRUD M13-operations-microservice**:
    - Creation of component, service, module of Operation (transfer, payment & withdrawal) entity. (Creation, read, update & delete)

- [ ] **CRUD M99-user-microservice**:
    - Creation of component, service, module of Role & user entities. (Creation, read, update & delete)

- [ ] Authentication of users via **Keycloak server**.
