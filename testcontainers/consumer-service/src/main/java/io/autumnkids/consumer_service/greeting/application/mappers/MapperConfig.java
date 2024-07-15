package io.autumnkids.consumer_service.greeting.application.mappers;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  public GreetUserMapper getGreetUserMapper() {
    return Mappers.getMapper(GreetUserMapper.class);
  }
}
