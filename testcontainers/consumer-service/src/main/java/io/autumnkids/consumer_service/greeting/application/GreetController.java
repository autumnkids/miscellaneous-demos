package io.autumnkids.consumer_service.greeting.application;

import io.autumnkids.consumer_service.greeting.application.exceptions.UserNotFoundException;
import io.autumnkids.consumer_service.greeting.domain.entities.GreetUser;
import io.autumnkids.consumer_service.greeting.domain.services.GreetService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.client.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

  @GetMapping("/user/{id}")
  public ResponseEntity<GreetUser> getMethodName(@PathVariable UUID id) throws UserNotFoundException {
    GreetUser user = service.getUserById(id);
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/user")
  public ResponseEntity<GreetUser> postMethodName(@RequestBody CreateGreetUserRequest request) {
    GreetUser user = service.createUser(request.getName());
    return ResponseEntity.ok().body(user);
  }

}
