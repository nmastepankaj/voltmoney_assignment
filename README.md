# Voltmoney Assignment
I read the problem statement carefully and try to build the application or API accordingly.

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
java -jar target/voltmoney-assignment-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/appointments/{operatorId}
    
    GET /api/get_open_appointments/{operatorId}
    
    POST /api/book_appointment
    
    PATCH /api/reschedule_appointment
    
    DELETE /api/cancel_appointment/{appointmentId}



## Screenshots

- Get all appointments
![Screenshot (970)](https://github.com/nmastepankaj/voltmoney_assignment/assets/68346633/db1708f5-574e-4f12-9dc3-87b8e02f2747)

- Get Open slots
![Screenshot (971)](https://github.com/nmastepankaj/voltmoney_assignment/assets/68346633/78c3c9b8-a60e-4359-9f2c-8b2a1d2335b2)

- Book Appointment
![Screenshot (972)](https://github.com/nmastepankaj/voltmoney_assignment/assets/68346633/03300c15-05ea-4dbc-a017-f09e3d361a41)

- reshedule appointment
![Screenshot (973)](https://github.com/nmastepankaj/voltmoney_assignment/assets/68346633/4d6314b4-c675-4bd8-b23d-2e376bff143b)

- Cancel Appointment
![Screenshot (974)](https://github.com/nmastepankaj/voltmoney_assignment/assets/68346633/1da66ab7-d26b-48f9-8770-8079a78e131f)