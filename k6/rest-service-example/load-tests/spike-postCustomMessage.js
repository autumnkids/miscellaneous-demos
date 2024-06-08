import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
  scenarios: {
    spike_up: {
      executor: 'ramping-vus',
      stages: [
        {duration: '10s', target: 1},
        {duration: '5s', target: 50},
        {duration: '10s', target: 1},
      ],
    },
    cliff: {
      executor: 'ramping-vus',
      stages: [
        {duration: '10s', target: 50},
        {duration: '5s', target: 1},
        {duration: '10s', target: 50},
      ],
      gracefulRampDown: '0s',
    },
  },
  thresholds: {
    http_req_duration: ['p(99) < 80', 'p(50) < 20'],
  },
};

export default () => {
  const url = `${__ENV.TEST_HOST}/api/v1/examples/custom-message`;
  const payload = JSON.stringify({
    message: 'Test Message',
  });
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  const response = http.post(url, payload, params);
  check(response, {
    'status code is 200': (r) => r.status === 200,
    'body contains custom message': (r) =>
      r.body.includes(`{"message":"Custom message: Test Message"}`),
  });
  sleep(1);
};
