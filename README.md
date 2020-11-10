# Ultra CarService 

## Contents
- [Overview](#Overview)
- [Requirements](#Requirements)
- [Installation](#Installation)
- [Documentation](#Documentation)
- [Testing](#Testing)
- [Frontend (Angular)](#Frontend (Angular))
- [Usage](#Usage)

## Overview
A web application based on a restful CRUD API, that allows you to manage an Inventory of cars, with options to list, create, update and delete entrys. 
The Project utilizes the Spring Boot framework in order to manage it's API, postgres to store the data and angular to display it in the front end. **And unlike many similar APIs, it is ULTRA cool**

## Requirements
- JVM and JRE running on Java 11 (or higher)
- Maven
- Docker
- NodeJs
- Angular
- Typescript
- Kubernetes
- Minikube

## Installation (Kubernetes)
- Step 1: Install all dependencies named above (example given using chocolaty)
    - ``choco install openjdk:11 maven docker nodejs minikube``
    - ``npm intsall -g typescript angular``
- Step 2: Add ``ultraservicespringboot`` to the host file and point it towards the minikube ip
    - Example: ``192.168.99.107    ultraservicespringboot``
- Step 3: Run the deployment script ``bash ./deploy.sh``
- Step 4: The webapp should now be running at ``http://minikube/list-all``
- Step 5: If failed run: ``eval "$(minikube docker-env --shell=bash)"`` and repeat steps 2-3

## Installation (Local)
- Step 1: Install all dependencies named above (example given using chocolaty)
    - ``choco install openjdk:11 maven docker nodejs minikube``
    - ``npm intsall -g typescript angular``
- Step 2: Add ``ultraservicespringboot`` to the host file and point it towards localhost
    - Example: ``localhost    ultraservicespringboot``
- Step 3: Pull up docker-compose file (to get postgres to run)
    - ``docker-compose up -d -f ./deploymen/docker-compose-local.yml``
- Step 4: Run Angular and Spring Boot
    - ``mvn package && java -jar ./target/car-service-0.0.1-SNAPSHOT.jar``
     - ``cd ./src/main/angular && ng serve --open``
- Step 5: Access the service over ``http://localhost:4200``

## Testing (Kubernetes)
- Run integration tests over Newman:
    - ``newman run ./src/test/newman/test_ulta_carservice.kubernetes.postman_collection.json``

## Testing (Local)
- The project has two types of test already setup: 
    - Integration tests using Newman (/Postman)
    - Unit tests using Junit
- Newman test:
    - Generate local newman tests with: ``cd ./deployments && bash ./generate_local_newman_test.sh``  
    - The Newman tests can then be found under ``./src/test/newman/test_ulta_carservice.postman_collection.json``
    - The integration tests can be run using ``newman run ./src/test/newman/test_ulta_carservice.postman_collection.json`` from the project root
- Unit Tests can be found under ``./src/test/java``
- Running all Unit tests at once can be achieved by running the Test Suite ``TestAll.java``

## Documentation
- The java portion of project is compatible with java docs
    - If you wish to generate that documentation, run the following command: ``mvn javadoc:javadoc`` in the project root
    - The files can then be found under ``./target/site/apidocs`` in your project directory
- Furthermore, precise documentation for using the API can be found under ``http://minikube/backend/swagger-ui/`` at runtime

## Usage of PostgreSQL in Docker
- Get PostgreSQL going: ``docker-compose up -d``
- Entering Postgres
    - Step 1: ``docker exec -it ultra-database bash``
    - Step 2: ``psql -U postgres``
    
## Usage Frontend (Angular)
- To access to front end (with Spring boot and Postgres running in the background):
    - navigate to ``./src/main/angular`` from the root directory
    - make sure angular & typescript are installed 
    - start the angular app using: ``ng serve --open``
    - (your browser should open the page automatically)
- Overview:
    - List of all cars: ``http://minikube/list-all`` or ``http://minikube/``
    - Search for individual cars: ``http://minikube/search``
    - Create car entries: ``http://minikube/create``
    - Edit existing car entries: ``http://minikube/edit``

## Usage Backend
- General Overview:
    - **C**reate entries: POST-Request to ``http://minikube/backend/ultra-api/create-car`` and a JSON Object in the body containing the following items:
        - name
        - model
    - **R**ead entries
        - to read all entries: GET-Request to ``http://minikube/backend/ultra-api/read-all``
        - to read out single entries: GET-Request to ``http://minikube/backend/ultra-api/read-single?id&19`` (here the id 19 is being checked)
    - **U**pdate entries: PUT-Request to ``http://minikube/backend/ultra-api/update-car?id=19``
        - with the id number (here 19) displaying the id of what car has to be updated 
        - as well as a JSON Object in the body containing the same items as the create method
    - **D**elete entries: DELETE-Request to ``http://minikube/backend/ultra-api/delete-car?id=19`` (here the entry with id number 19 is being deleted)
- For precise Usage info, please visit ``http://minikube/backend/swagger-ui/``
