package io.autumnkids.consumer_service.greeting;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.client.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api/v1/greet")
@RequiredArgsConstructor
public class GreetController {

  private final GreetService service;

  @GetMapping
  public ResponseEntity<GreetResponse> greet(@RequestParam String name) throws ApiException {
    GreetResponse response = new GreetResponse(service.greet(name));
    return ResponseEntity.ok().body(response);
  }

}
