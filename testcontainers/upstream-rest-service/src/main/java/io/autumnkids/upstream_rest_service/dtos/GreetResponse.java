package io.autumnkids.upstream_rest_service.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GreetResponse {

  @Getter
  private final String message;
}
