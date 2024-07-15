package io.autumnkids.consumer_service.greeting.persistent.repositories;

import io.autumnkids.consumer_service.greeting.persistent.entities.GreetUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetUserRepository extends JpaRepository<GreetUser, UUID> {

}
