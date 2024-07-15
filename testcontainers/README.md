# TestContainer Demo

This demo shows how integration tests can leverage [TestContainers](https://testcontainers.com/) to emulate the dependencies.

The tests are captured in the [consumer-service](./consumer-service/) folder - mainly two integration tests included at the moment. Please see the [README.md](./consumer-service/README.md) over there for more details. The tests are also written in a manner of functional API testing.

## Test frameworks included

* [MockMvc](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html)
* [TestContainers](https://testcontainers.com/)
* [TestNG](https://testng.org/)
