import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 5 },
    { duration: '10s', target: 10 },
    { duration: '10s', target: 5 }
  ],
  thresholds: {
    http_req_duration: [
      'p(99) < 80',
      'p(50) < 20',
    ],
  }
};

export default () => {
  const response = http.get(`${__ENV.TEST_HOST}/api/v1/examples/helloworld`);
  check(response, {
    'status code is 200': r => r.status === 200,
    'body contains hello world': r => r.body.includes(`{"message":"Hello World!"}`),
  });
  sleep(1);
}
