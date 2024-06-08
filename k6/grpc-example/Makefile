run-server:
	@java -jar target/grpc-example-0.0.1.jar

run-client:
	@mvn validate exec:java

package:
	@mvn clean package

run-smoke-test:
	@k6 run -e TEST_HOST=localhost -e TEST_PORT=9001 ./load-tests/smoke-greeting.js
