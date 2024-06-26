package io.autumnkids.consumer_service.greeting;

import io.autumnkids.consumer_service.upstream_rest_service.UpstreamRestServiceProperties;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.HelloWorldControllerApi;
import org.openapitools.client.model.GreetResponse;
import org.springframework.stereotype.Service;

@Service
public class GreetService {

  private final ApiClient client;

  private final HelloWorldControllerApi helloWorldApi;

  public GreetService(UpstreamRestServiceProperties properties) {
    client = new ApiClient().setBasePath(String.format("http://%s:%d", properties.getHost(), properties.getPort()));
    helloWorldApi = new HelloWorldControllerApi(client);
  }

  public String greet(String name) throws ApiException {
    GreetResponse response = helloWorldApi.greet(name);
    return response.getMessage();
  }
}
