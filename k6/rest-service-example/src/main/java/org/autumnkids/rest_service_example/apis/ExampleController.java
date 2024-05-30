package org.autumnkids.rest_service_example.apis;

import lombok.RequiredArgsConstructor;
import org.autumnkids.rest_service_example.services.ExampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/examples")
@RequiredArgsConstructor
public class ExampleController {

  private final ExampleService service;

  @GetMapping(path = "/helloworld")
  public ResponseEntity<MessageResponse> getHelloWorldWithDelay(
      @RequestParam(required = false, defaultValue = "0") int delay
  ) {
    String message = service.getHelloWorldWithDelay(delay);
    MessageResponse response = new MessageResponse(message);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping(path = "/custom-message")
  public ResponseEntity<MessageResponse> getCustomerMessage(@RequestBody CustomMessageRequest payload) {
    String message = service.getCustomMessage(payload.getMessage());
    MessageResponse response = new MessageResponse(message);
    return ResponseEntity.ok().body(response);
  }
}
