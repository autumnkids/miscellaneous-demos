package io.autumnkids.consumer_service.greeting;

import org.springframework.stereotype.Service;

@Service
public class GreetService {

  public String greet(String name) {
    return name;
  }
}
