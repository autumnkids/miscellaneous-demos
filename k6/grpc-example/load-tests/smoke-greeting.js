import { Client, StatusOK } from 'k6/net/grpc';
import { check, sleep } from 'k6';

const client = new Client();
client.load(['../src/main/proto'], 'helloworld.proto');

export const options = {
  vus: 10,
  duration: '30s',
};

export default () => {
  client.connect(`${__ENV.TEST_HOST}:${__ENV.TEST_PORT}`, {
    plaintext: true
  });
  const request = {
    name: 'World',
  };
  const response = client.invoke('helloworld.Greeter/SayHello', request);

  check(response, {
    'status is OK': r => r && r.status === StatusOK,
    'response includes Hello World text': r => r && r.message && r.message.message.includes(`Hello ${request.name}`),
  });

  client.close();
  sleep(1);
}
