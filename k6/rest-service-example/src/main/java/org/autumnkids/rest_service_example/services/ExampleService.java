package org.autumnkids.rest_service_example.services;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

  public String getHelloWorldWithDelay(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
    }
    return "Hello World!";
  }

  public String getCustomMessage(String message) {
    return String.format("Custom message: %s", message);
  }
}
