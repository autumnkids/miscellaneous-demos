package io.autumnkids.consumer_service.greeting.application.mappers;

import io.autumnkids.consumer_service.greeting.domain.entities.GreetUser;
import org.mapstruct.Mapper;

@Mapper
public interface GreetUserMapper {

  GreetUser toDomainEntity(io.autumnkids.consumer_service.greeting.persistent.entities.GreetUser user);
}
