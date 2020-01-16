# News-Portal-REST

### Test task

Test task from Smartym.pro .
Description of tasks can be found [here](https://github.com/LordDetson/News-Portal-REST/blob/master/TestTaskSmartym.docx).

### Application containerization

To start the application you need to:
1. Install (Maven)[https://maven.apache.org/] and (Docker)[https://www.docker.com/].
2. Build image applications by running the command __mvnw install -DskipTests__ in the project directory.
3. Run __docker-compose up__ command

### Documentation API

After starting the application, you can see the documentation at the link http://localhost:8080/swagger-ui.html .
The documentation is incomplete - this is explained in the _Problems_ section.

Requests to the server:
1. User registration
POST /registration HTTP/1.1
Body:
{
	"username": "user123456",
	"password": "123456",
	"active": true,
	"accountNonLocked": true,
	"roles": ["USER"]
}

2. Authentication
POST /auth/signin HTTP/1.1
Body:
{
	"username": "admin",
	"password": "admin"
}
Response:
{
    "username": "admin",
    "token": "_some-token_"
}
This token is saved and used in the header of each request

3. Current user
GET /me HTTP/1.1
Header:
  Authorization: Bearer some-token
Response:
{
    "roles": [
        "ROLE_ADMINISTRATOR",
        "ROLE_USER"
    ],
    "username": "admin"
}

4. Another Requests
The project uses Spring Data REST to access news, comments, and user data. Therefore, standard requests for Spring.
REST API can be obtained by sending a request
GET /api/ HTTP/1.1
Header:
  Authorization: Bearer some-token
Response:
{
  "_links": {
    "comments": {
      "href": "http://localhost:8080/api/comments{?page,size,sort}",
      "templated": true
    },
    "users": {
      "href": "http://localhost:8080/api/users"
    },
    "news": {
      "href": "http://localhost:8080/api/news{?page,size,sort}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8080/api/profile"
    }
  }
}

Users only have access to http GET methods for all repositories except CommentItemRepository. User has access to GET, POST, PUT, DELETE for comments.

Administrator has access to all the methods and requests of the platform.

### Problems

Only one version of __Springfox 3.0.0-SNAPSHOT__ now works with __Spring Data REST (springfox-data-rest)__ with __Spring Boot 2.2.2__ (the latest version at the moment). 
But there is a problem that I can’t solve in any way and there are no solutions on the Internet:

__java.lang.IllegalStateException: Ambiguous models equality when conditions is empty.__

You can see this problem in branch problem-springfox-3.0.0-SNAPSHOT.

For this reason, I decided to roll back the Spring Boot version to 2.1.0 with the stable version of SpringFox 2.8.0 without using springfox-data-rest, as a more serious error occurs:

***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call the method org.springframework.data.repository.support.Repositories.getRepositoryInformationFor(Ljava/lang/Class;)Lorg/springframework/data/repository/core/RepositoryInformation; but it does not exist. Its class, org.springframework.data.repository.support.Repositories, is available from the following locations:

    jar:file:/C:/Users/%d0%94%d0%bc%d0%b8%d1%82%d1%80%d0%b8%d0%b9/.m2/repository/org/springframework/data/spring-data-commons/2.1.2.RELEASE/spring-data-commons-2.1.2.RELEASE.jar!/org/springframework/data/repository/support/Repositories.class

It was loaded from the following location:

    file:/C:/Users/%d0%94%d0%bc%d0%b8%d1%82%d1%80%d0%b8%d0%b9/.m2/repository/org/springframework/data/spring-data-commons/2.1.2.RELEASE/spring-data-commons-2.1.2.RELEASE.jar


Action:

Correct the classpath of your application so that it contains a single, compatible version of org.springframework.data.repository.support.Repositories
