package io.autumnkids.consumer_service.greeting.persistent.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "greet_user")
public class GreetUser {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  private String name;
}
