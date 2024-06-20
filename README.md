This Spring Boot Project Enables us to Reserve 1 hour Appointments for Operators, Reschedule the Appointments, Cancel the Appointments, Has Ability to show all booked appointments of an operator. This should display all individual
appointments ie. 1-2, 2-3, 3-4 and   to show open slots of appointments for an operator. This should merge all consecutive
open slots and show as 4-14 instead of 4-5, 5-6 .. 13-14.

Running the Application:
  Start MongoDB:
   Ensure your MongoDB server is running.(Default my Mongo URI is hardcoded, change the spring.data.mongodb.uri in Applications.properties file to your Mongo Uri)
   
  Run the Spring Boot Application:
    Navigate to the project directory and run:
      mvn spring-boot:run

      
Install postman paste these curls to test the application:
  Curl to Book an Appointments: curl --location --request POST 'http://localhost:8080/scheduler/book?operatorName=ServiceOperator0&date=2024-07-19&startHour=22&endHour=23'
  Curl to Reschedule an Appointments: curl --location --request PUT 'http://localhost:8080/scheduler/reschedule/66732350cde7474723e7d62e?newStartHour=3&newEndHour=4'
  Curl to get all Reserved Slots: curl --location 'http://localhost:8080/scheduler/appointments/ServiceOperator0?date=2024-07-19'
  Curl to get all Open Slots: curl --location 'http://localhost:8080/scheduler/open-slots/ServiceOperator0?date=2024-07-19'
  Curl to Cancel an Appointments: curl --location --request DELETE 'http://localhost:8080/scheduler/cancel/6673bf79cb9e237c3eeda022'
