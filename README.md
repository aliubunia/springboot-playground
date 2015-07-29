Starting the application
------------------------

To run as a WAR application in embedded Tomcat:
```
mvn tomcat7:run -P war-packaging
```

To run as a standalone JAR application:
```
mvn spring-boot:run -P jar-packaging
```

The application will be available at [http://localhost:8080/](http://localhost:8080/)

REST API
--------

### Retrieving all the offices

```
GET /api/office/all.json
```

An array of JSON objects representing each office is returned as in the following example:

```
[
  {
    "id": 1,
    "city": "Berlin",
    "country": "DE",
    "openFrom": "09:00:00+02:00",
    "openUntil": "18:00:00+02:00",
    "openFromUTC": "07:00:00",
    "openUntilUTC": "16:00:00"
  },
  {
    "id": 2,
    "city": "São Paulo",
    "country": "BR",
    "openFrom": "09:00:00-03:00",
    "openUntil": "18:00:00-03:00",
    "openFromUTC": "12:00:00",
    "openUntilUTC": "21:00:00"
  },
  {
    "id": 3,
    "city": "Sydney",
    "country": "AU",
    "openFrom": "09:00:00+10:00",
    "openUntil": "18:00:00+10:00",
    "openFromUTC": "23:00:00",
    "openUntilUTC": "08:00:00"
  }
]
```

### Retrieving only offices open now

```
GET /api/office/openNow.json
```

The response has the same structure as the one retrieving all offices.
Only offices open at the time of query will be included.

### Retrieving office details by ID

```
GET /api/office/{id}.json
```

Returns a JSON document representing the requested office, or HTTP status `404` if there is no office with such ID.

For example,

```
GET /api/office/1.json
```

could return

```
{
  "id": 1,
  "city": "Berlin",
  "country": "DE",
  "openFrom": "09:00:00+02:00",
  "openUntil": "18:00:00+02:00",
  "openFromUTC": "07:00:00",
  "openUntilUTC": "16:00:00"
}
```

### Saving a new or existing office


```
POST /api/office/save.json
Content-Type: application/json

{
  "country": "NL",
  "city": "Den Haag",
  "openFrom": "10:00:00+02:00",
  "openUntil": "17:30:00+02:00"
}

```

On success the server returns HTTP status `201`, along with `Location` of the saved resource and JSON representation of
the office as perceived by the server:

```
Content-Type: application/json
Location: /api/office/50.json

{
  "id": 50,
  "country": "NL",
  "city": "Den Haag",
  "openFrom": "10:00:00+02:00",
  "openUntil": "17:30:00+02:00",
  "openFromUTC": "08:00:00",
  "openUntilUTC": "15:30:00"
}
```

