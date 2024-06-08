# k6 Demo

k6 is an open-source load testing tool developed by Grafana.

> k6 is free, developer-centric, and extensible.

In its own words - https://grafana.com/docs/k6/latest/#what-is-k6.

## Get Started

This demo includes two examples:

* A REST API service that exposes a GET and a POST endpoint.
* A GRPC service that simply returns a "Hello World" message. (Streaming is also supported in k6, but it's not included in this demo this time.)

### Why k6

* The APIs are simple to use. For example:
  * If you like the Ultimate Thread Group in JMeter where you have control on how the traffic is ramped up and down, it is as simple as define the `stages` in the `options` object: [[Documentation](https://grafana.com/docs/k6/latest/using-k6/k6-options/reference/#stages)]
  ```
  export const options = {
    stages: [
      { duration: '300s', target: 100 }, // First 5min with 100 virtual users.
      { duration: '600s', target: 500 }, // And then 500 virtual users at peak, lasts for 10min.
      { duration: '300s', target: 100 }, // And then ramp down the traffic back to 100 virtual users.
    ],
  };
  ```
  * It is straightforward to define difference test scenarios in one test: [[Documentation](https://grafana.com/docs/k6/latest/using-k6/scenarios/)]
  ```
  export const options = {
    scenarios: {
      spike_up: { // Scenario where traffic spikes up
        executor: 'ramping-vus',
        stages: [
          {duration: '300s', target: 10},
          {duration: '120s', target: 100},
          {duration: '300s', target: 10},
        ],
      },
      cliff: { // Scenario where traffic drops like a cliff
        executor: 'ramping-vus',
        stages: [
          {duration: '300s', target: 100},
          {duration: '120s', target: 1},
          {duration: '300s', target: 100},
        ],
        gracefulRampDown: '0s',
      },
    },
  };
  ```
  * Test can be configured to run against SLOs: [[Documentation](https://grafana.com/docs/k6/latest/using-k6/thresholds/)]
  ```
  export const options = {
    thresholds: {
      http_req_duration: ['p(99) < 80', 'p(50) < 20'],
    },
  };
  ```
* It supports a variety of [protocols](https://grafana.com/docs/k6/latest/using-k6/protocols/), and has a variety of [supports from the community](https://grafana.com/docs/k6/latest/extensions/explore/) that extends the ability to other protocols.
* It can be integrated in CI/CD pipeline, which I personally found would be plus. For example, it can be integrated in [GitHub Actions](https://github.com/grafana/k6-action).
* I personally found that it has thorough documentation with a decent amount of code samples. It comes with a lot of [guides](https://grafana.com/docs/k6/latest/testing-guides/) and [examples](https://grafana.com/docs/k6/latest/examples/).

### REST Service

This is a simple Spring Boot application that exposes a GET API and a POST API. The load tests come with this example include one [regular load test](./rest-service-example/load-tests/load-getHelloWorld.js) that ramps the traffic up and down gracefully, and one [load test with extreme scenarios](./rest-service-example/load-tests/spike-postCustomMessage.js) where traffic spikes in a sudden, and drops without graceful ramp down period.

### GRPC Service

This is an example that demostrate the ability of load testing a GRPC service. The [load test](./grpc-example/load-tests/smoke-greeting.js) attached is just a simple smoke test to ensure the service functions as expected.
