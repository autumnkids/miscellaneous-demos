run-load-test:
	@k6 run -e TEST_HOST=http://localhost:8080 ./load-tests/load-getHelloWorld.js

run-spike-test:
	@k6 run -e TEST_HOST=http://localhost:8080 ./load-tests/spike-postCustomMessage.js
