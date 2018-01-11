Task #1

Create a Config Service REST api using Spring Boot that implements below apis to return and update JSON documents representing an application’s config properties.
The application needs to store the JSON documents in any SQL database using Spring Data JPA.
 
• GET /api/{appCode}/config/{version} – return JSON document for specified appCode and version  
• POST /api/{appCode}/config/{version} – add new or update existing JSON document for specified appCode and version. JSON document should be included in the request body  
• GET /api/{appCode}/config – return list of available versions in JSON sorted by last modified date in descending order  
 
 Task #2

Create unit tests for task #1 (controller, service and repository layers)


* DataBase : H2 In-memory database.
* Build Tool : gradle v3.4
* Spring boot : v1.5.9
* Spring Data jpa
* Goole guava v21.0
* Goolecode junit-toolbox v2.2
* lombok
* Spring devtools
* Apache common-lang3
