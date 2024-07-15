# TestContainers Integration Test Demo

This demo includes two types of integration test that are using [TestContainers](https://testcontainers.com/) under the hood.

The first one is performing an integration test with an upstream service dependency using a `GenericContainer`. See [UpstreamServiceIntegrationTest.java](./src/test/java/io/autumnkids/consumer_service/UpstreamServiceIntegrationTest.java) for more details.

The second one is performing an integration test with a DB dependency ([PostgreSQL](https://www.postgresql.org/)) using a `PostgreSQLContainer`. See [PostgresIntegrationTest.java](./src/test/java/io/autumnkids/consumer_service/PostgresIntegrationTest.java)

Both tests are functional API testing that leverage `MockMvc` to make direct requests to the service APIs.

The second test also leverages the [TestNG](https://testng.org/) framework along with the [Junit](https://junit.org/junit5/docs/current/user-guide/) framework to perform a test with priorities.

_Note: While the docker images used in the tests are pulled from Docker hub, which is the default behavior, this behavior can definitely be customized to pull the image from a specified registry instead. See this [documentation](https://java.testcontainers.org/features/image_name_substitution/) for more details. This will be helpful when configuring it in CI/CD pipeline. More specifically:_

> You can then configure Testcontainers to apply the prefix registry.mycompany.com/mirror/ to every image that it tries to pull from Docker Hub. This can be done in one of two ways:
> * Setting environment variables `TESTCONTAINERS_HUB_IMAGE_NAME_PREFIX=registry.mycompany.com/mirror/`
> * Via config file, setting hub.image.name.prefix in either:
>   * the ~/.testcontainers.properties file in your user home directory, or
>   * a file named testcontainers.properties on the classpath
> Testcontainers will automatically apply the prefix to every image that it pulls from Docker Hub - please verify that all the required images exist in your registry.

## Get Started

### Prerequisites

#### Set up ProgreSQL

Pull the docker image:

    docker pull postgres

Run postgres in docker:

    docker run --name postgres-local \
        -p 5432:5432 \
        -e POSTGRES_PASSWORD=postgres \
        -d postgres:latest

Install `psql` cli, Mac for example:

    brew update
    brew install libpq
    brew link --force libpq

Connect to local postgres container using default username and password:

    psql -h localhost -p 5432 -U postgres

Create user for this demo:

    CREATE USER demo WITH CREATEDB NOCREATEUSER PASSWORD 'p@$$w0Rd';

Create DB for this demo:

    CREATE DATABASE test_container_demo WITH OWNER demo;

#### Build upstream dependency

Build the image for the upstream dependency service, which will be used in integration tests:

    cd ../upstream-rest-service
    docker compose build service

### Run tests

You can either run tests in IDE, or in the console via `mvn test`.
