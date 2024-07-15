package io.autumnkids.consumer_service.greeting.domain.entities;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GreetUser {

  private final UUID id;

  private final String name;
}
