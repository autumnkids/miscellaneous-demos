package org.autumnkids.rest_service_example.apis;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageResponse {

  @NonNull
  private final String message;
}
