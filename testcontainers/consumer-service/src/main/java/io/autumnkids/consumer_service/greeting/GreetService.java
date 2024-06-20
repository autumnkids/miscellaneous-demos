package io.autumnkids.consumer_service.greeting;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.HelloWorldControllerApi;
import org.openapitools.client.model.GreetResponse;
import org.springframework.stereotype.Service;

@Service
public class GreetService {

  private static final HelloWorldControllerApi helloWorldApi = new HelloWorldControllerApi();

  public String greet(String name) throws ApiException {
    GreetResponse response = helloWorldApi.greet(name);
    return response.getMessage();
  }
}
