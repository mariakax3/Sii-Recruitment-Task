
# Conference Management Service

RESTful web service for IT conference management and customer service created with Spring Boot 3, H2 in-memory database and Hibernate. API documentation is generated using Swagger.



### Instructions

Project should be built and run using Java 8 or above.

1. Open the `application.properties` file and set your own configurations for the database connection.

2. Build with Maven ```mvn clean install```

3. Change directory to ```../Sii-Recruitment-Task/target/```

4. Execute in the terminal ```java -jar Sii-Recruitment-Task-1.1.0.jar ```

Alternatively run ```mvn spring-boot:run``` from command line or IDE or execute *conference_management.ConferenceManagementApplication.main()* from within IDE.

Once the service is up and running the documentation of the REST API can be accessed at:

* API documentation WEB UI [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/swagger-ui.html)



### Initial database content
At the time of service launch, the database contains 9 lectures and 6 users. There are 5 users registered for one of the lectures with ID 8. The purpose of the case is to test the behavior of the service when another person wants to sign up for a lecture for which there are no places.
The database also contains a junction table representing many-to-many relationships between users and lectures.


### Sample queries
GET queries are easily reachable from Swagger.
Below are the URLs for POST, PUT and DELETE requests. These requests can be made by Swagger after providing the necessary parameters in the Swagger UI.
* POST - Change e-mail:
  * invalid email provided: [http://localhost:8080/api/users/ada998%40email.com?newEmail=ada_456%40email.com](http://localhost:8080/api/users/ada998%40email.com?newEmail=ada_456%40email.com)
  * successful update: [http://localhost:8080/api/users/ada997%40email.com?newEmail=ada_456%40email.com](http://localhost:8080/api/users/ada997%40email.com?newEmail=ada_456%40email.com)
* PUT - Register for selected lecture:
  * no seats available: [http://localhost:8080/api/register/user?login=aneta_325&email=aneta%40email.com&pathNumber=3&lectureNumber=2](http://localhost:8080/api/register/user?login=aneta_325&email=aneta%40email.com&pathNumber=3&lectureNumber=2)
  * login already in use: [http://localhost:8080/api/register/user?login=98kamil&email=kamil555%40email.com&pathNumber=2&lectureNumber=1](http://localhost:8080/api/register/user?login=98kamil&email=kamil555%40email.com&pathNumber=2&lectureNumber=1)
  * lecture colliding with another thematic path: [http://localhost:8080/api/register/user?login=patrycja653&email=patrycja653%40email.com&pathNumber=2&lectureNumber=2](http://localhost:8080/api/register/user?login=patrycja653&email=patrycja653%40email.com&pathNumber=2&lectureNumber=2)
  * registration completed successfully: [http://localhost:8080/api/register/user?login=patrycja653&email=patrycja653%40email.com&pathNumber=2&lectureNumber=1](http://localhost:8080/api/register/user?login=patrycja653&email=patrycja653%40email.com&pathNumber=2&lectureNumber=1)
* DELETE - Cancel reservation for selected lecture:
  * invalid e-mail provided: [http://localhost:8080/api/lectures/cancel/kamil_988%40email.com?pathNumber=2&lectureNumber=2](http://localhost:8080/api/lectures/cancel/kamil_988%40email.com?pathNumber=2&lectureNumber=2)
  * user not registered for provided lecture: [http://localhost:8080/api/lectures/cancel/kamil_98%40email.com?pathNumber=1&lectureNumber=2](http://localhost:8080/api/lectures/cancel/kamil_98%40email.com?pathNumber=1&lectureNumber=2)
  * successful cancellation: [http://localhost:8080/api/lectures/cancel/kamil_98%40email.com?pathNumber=2&lectureNumber=2](http://localhost:8080/api/lectures/cancel/kamil_98%40email.com?pathNumber=2&lectureNumber=2)
