package io.autumnkids.consumer_service.greeting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GreetResponse {

  @Getter
  private final String message;
}
