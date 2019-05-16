# post-code-lookup-api
API persist and retrieve the post code. 

Post Code API - Provides ability for an end-user to search Postal Code, Suburb associations.



####Get Suburb:

 REST endpoint retrieves the Suburb details for a Postal Code.
 Validation: Postal Code Should be valid Number, and between 0200 to 7999.

- Method: GET
- URI: /postcode/code/<postal-code>
- Authentication: Basic Auth (Please check application.yml for credentials)


**Sample Request:**

```$bash
    curl -u username http://localhost:8080/postcode/code/3000
```

####Get PostCode:

REST endpoint retrieves the Postal details for a Given Suburb.

- Method: GET
- URI: /postcode/suburb/<suburb-code>
- Authentication: Basic Auth (Please check application.yml for credentials)

**Request:**

```$bash
    curl -u username http://localhost:8080/postcode/code/3000
```


####Save PostCode Details

REST endpoint saves the Postal details.

- Method: POST
- URI: /postcode/saveSuburbDetails
- Authentication: Basic Auth (Please check application.yml for credentials)

**Request:**

```$bash
    curl -u username -d '{"postCode":"2000", "suburb":"Sydney Olampic Park"}' -H "Content-Type: application/json" -X POST http://localhost:8080/postcode/saveSuburbDetails
```

Sample Request:
```
{
	"postCode": 2000,
	"suburb" : "Sydney Olampic Park"
}
```



Built Tools:
- [Spring Boot](https://projects.spring.io/spring-boot/) and built
using [Gradle](https://gradle.org/).
- Redis/MongoDB for Persistence Layer.
- User Spring Security - Provides Basic Authentication.
- Lombok and MapStructs for Code Generation and Mapping.
- Prometheus for exposing custom metrics [http:// < ip > :< port >/metrics]
- CheckStyle Plugin
- JocoCo.
- RestDoc.






##Operation Support

[Prometheus](https://prometheus.io/) to provide the Operational Insights of the Application.


## Code quality

The projects include the following.

### API documentation
//TODO::
[Spring REST Docs](https://projects.spring.io/spring-restdocs/) is used to generate documentation
of the REST interface in [AsciiDoc](http://asciidoc.org/) format.

The generated documentation is added to each service’s Spring Boot JAR in the path
`BOOT-INF/classes/static/docs/api-guide.html`.

### CheckStyle

CheckStyle is run on the production Java code (not test code) and the build fails if the code does
conform to the CheckStyle rules in [checkstyle.xml](config/checkstyle/checkstyle.xml).

Generated reports can be read from `build/reports/checkstyle/main.html` for each service.

### Test coverage


[JaCoCo](http://www.jacoco.org/jacoco/) is used to report on code coverage by unit tests.

Generated reports can be read from `build/reports/jacoco/test/html/index.html` for each service.


*NB:* JaCoCo does not allow generated code (e.g. by [Lombok](http://projectlombok.org)) to
be excluded from code coverage. This means that classes may have low coverage reported because
they have generated `equals` and `hashCode` methods.

