# Voltmoney Assignment
I read the problem statement carefully and try to build the application or API for this

### Assumptions

Before starting of the project I read the problem statement carefully and took some assumptions
- Only one user is booking to one or more Operator at the same time. So, that's why I didn't create User model for this. Assume that appointment is booking for single user.
- And I take one assumption that it is booking for the current date only (for Today's open slots) (Not considering the time factor)


## Functionality
- Build API to get the alredy booked slots
- Build API to get the slots remaining to book
- Build API to schedule the Appointment
- Build API to reschedule appointment
- Build API to cancel appointment


## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/nmastepankaj/voltmoney_assignment.git
```

**2. Create Mysql database**
```bash
create database demo_db;
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target\voltmoney-assignment-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/users
    
    POST /api/get_open_appointments/{operatorId}
    
    GET /api/users/{userId}
    
    PATCH /api/users/{userId}
    
    DELETE /api/users/{userId}

