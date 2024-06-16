package io.autumnkids.upstream_rest_service.controllers;

import io.autumnkids.upstream_rest_service.dtos.GreetResponse;
import io.autumnkids.upstream_rest_service.services.HelloWorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/helloworld")
@RequiredArgsConstructor
public class HelloWorldController {

  private final HelloWorldService service;

  @GetMapping("/greet")
  public ResponseEntity<GreetResponse> greet(@RequestParam String name) {
    GreetResponse response = new GreetResponse(service.greet(name));
    return ResponseEntity.ok().body(response);
  }

}
