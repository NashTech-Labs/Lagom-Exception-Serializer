# lagom-exception-serializer-demo

> This is a simple lagom application which shows the usage of ExceptionSerializer.

Steps to follow to run this application:

- Open the terminal.
- Clone the project ```git clone git@github.com:DivyaDua/lagom-persistent-read-side.git```.
- ```cd lagom-persistent-read-side```.
- Execute ``mvn clean compile```.
- Run the application using ```mvn lagom:runAll```.

You can check which services are up by hitting ```http://localhost:8000/services```. There, you can see that along with cassandra and kafka, services named 'external-service' and 'demo' are also running.

You can now hit the service using postman -
- Hit the service with a GET call using URL - http://localhost:9000/user

