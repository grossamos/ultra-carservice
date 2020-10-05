# Ultra CarService 
## Contents
- [Overview](#Overview)
- [Requirements](#Requirements)
- [Installation](#Installation)
- [Usage](#usage)
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
## Usage
- **C**reate entrys: PUT-Request to ``http://localhost:8080/ultra-api/create-car`` and a JSON Object in the body containing the following items:
    - name
    - model
- **R**ead entrys
    - to read all entrys: GET-Request to ``http://localhost:8080/ultra-api/read-all``
    - to read out single entrys: GET-Request to ``http://localhost:8080/ultra-api/read-single?id&19`` (here the id 19 is being checked)
- **U**pdate entrys: POST-Request to ``http://localhost:8080/ultra-api/update-car?id=19``
    - with the id number (here 19) displaying the id of what car has to be updated 
    - as well as a JSON Object in the body containing the same items as the create method
- **D**elete entrys: DELETE-Request to ``http://localhost:8080/ultra-api/delete-car?id=19`` (here the entry with id number 19 is being deleted)