package io.autumnkids.upstream_rest_service.services;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

  public String greet(String name) {
    return String.format("Hello %s!", name);
  }
}
