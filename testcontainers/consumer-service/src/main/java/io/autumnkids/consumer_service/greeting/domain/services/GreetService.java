package io.autumnkids.consumer_service.greeting.domain.services;

import io.autumnkids.consumer_service.greeting.application.exceptions.UserNotFoundException;
import io.autumnkids.consumer_service.greeting.application.mappers.GreetUserMapper;
import io.autumnkids.consumer_service.greeting.domain.entities.GreetUser;
import io.autumnkids.consumer_service.greeting.persistent.repositories.GreetUserRepository;
import io.autumnkids.consumer_service.upstream_rest_service.UpstreamRestServiceProperties;
import java.util.Optional;
import java.util.UUID;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.HelloWorldControllerApi;
import org.openapitools.client.model.GreetResponse;
import org.springframework.stereotype.Service;

@Service
public class GreetService {

  private final ApiClient client;

  private final HelloWorldControllerApi helloWorldApi;

  private final GreetUserRepository repository;

  private final GreetUserMapper mapper;

  public GreetService(UpstreamRestServiceProperties properties, GreetUserRepository repository, GreetUserMapper mapper) {
    client = new ApiClient().setBasePath(String.format("http://%s:%d", properties.getHost(), properties.getPort()));
    helloWorldApi = new HelloWorldControllerApi(client);
    this.repository = repository;
    this.mapper = mapper;
  }

  public String greet(String name) throws ApiException {
    GreetResponse response = helloWorldApi.greet(name);
    return response.getMessage();
  }

  public GreetUser createUser(String name) {
    io.autumnkids.consumer_service.greeting.persistent.entities.GreetUser user = new io.autumnkids.consumer_service.greeting.persistent.entities.GreetUser();
    user.setName(name);
    return mapper.toDomainEntity(repository.save(user));
  }

  public GreetUser getUserById(UUID id) throws UserNotFoundException {
    Optional<io.autumnkids.consumer_service.greeting.persistent.entities.GreetUser> user = repository.findById(id);
    if (!user.isPresent()) {
      throw new UserNotFoundException(String.format("User with id %s can not be found.", id));
    }

    return mapper.toDomainEntity(user.get());
  }
}
