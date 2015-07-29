To run as a WAR application in embedded Tomcat:
```
mvn tomcat7:run -P war-packaging
```

To run as a standalone JAR application:
```
mvn spring-boot:run -P jar-packaging
```

The application will be available at [http://localhost:8080/](http://localhost:8080/)
