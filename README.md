# Ultra CarService 

## Contents
- [Overview](#Overview)
- [Requirements](#Requirements)
- [Installation](#Installation)
- [Documentation](#Documentation)
- [Testing](#Testing)
- [Usage](#Usage)

## TODO
- Swagger
- Docs
- Test

## Overview
A CRUD based REST API, that allows you to manage an Inventory of cars, with options to list, create, update and delete entrys. 
The Project utilizes the Spring Boot framework in order to manage it's API. **Unlike many similar APIs, it retains it's entrys even after a restart!**

## Requirements
- JVM and JRE running on Java 11 (or higher)
- Maven

## Installation
- After cloning the git repo into your local file system first use Maven to download all the nessicary dependencys and build the project
    - On Windows:   ``mvnw package``
    - On Unix:  ``mvn package`` 
- The API should now be running on ``http://localhost:8080/ultra-api/``

## Documentation
- This project is compatible with java docs
- if you wish to generate the documentation, run the following command: ``mvn javadoc:javadoc``
- The files can then be found under ``./target/site/apidocs`` in your project directory
- Furthermore, precise documentation for using the API can be found under ``http://localhost:8080/swagger-ui/`` at runtime

## Testing
- The project has two types of test already setup: 
    - Integration tests using Newman (/Postman)
    - Unit tests using Junit
- The Newman tests can be found under ``./src/test/newman/test_ulta_carservice.postman_collection.json``
- The integration tests can be run using ``newman run ./src/test/newman/test_ulta_carservice.postman_collection.json`` from the project root
- Unit Tests can be found under ``./src/test/java``
- Running all Unit tests at once can be achieved by running the Test Suite ``TestAll.java``

## Usage
- General Overview:
    - **C**reate entries: POST-Request to ``http://localhost:8080/ultra-api/create-car`` and a JSON Object in the body containing the following items:
        - name
        - model
    - **R**ead entries
        - to read all entries: GET-Request to ``http://localhost:8080/ultra-api/read-all``
        - to read out single entries: GET-Request to ``http://localhost:8080/ultra-api/read-single?id&19`` (here the id 19 is being checked)
    - **U**pdate entries: PUT-Request to ``http://localhost:8080/ultra-api/update-car?id=19``
        - with the id number (here 19) displaying the id of what car has to be updated 
        - as well as a JSON Object in the body containing the same items as the create method
    - **D**elete entries: DELETE-Request to ``http://localhost:8080/ultra-api/delete-car?id=19`` (here the entry with id number 19 is being deleted)
- For precise Usage info, please visit ``http://localhost:8080/swagger-ui/``
