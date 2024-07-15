package io.autumnkids.consumer_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.autumnkids.consumer_service.greeting.application.CreateGreetUserRequest;
import io.autumnkids.consumer_service.greeting.domain.entities.GreetUser;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testng.annotations.Test;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PostgresIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
      .withExposedPorts(5432);

  private static GreetUser createdUser;

  @org.junit.jupiter.api.Test
  @Test
  void testGreetEndpointCreateUserRequest() throws Exception {
    String userName = "Test";
    CreateGreetUserRequest request = new CreateGreetUserRequest();
    request.setName(userName);
    String requestString = new ObjectMapper().writeValueAsString(request);
    MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/greet/user").content(requestString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is(userName)))
        .andReturn();
    String content = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(content);
    createdUser = new GreetUser(UUID.fromString(node.get("id").asText()), node.get("name").asText());
  }

  @org.junit.jupiter.api.Test
  @Test(dependsOnMethods = {"testGreetEndpointCreateUserRequest"})
  void testGreetEndpointGetUserRequest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/greet/user/{id}", createdUser.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(createdUser.getId().toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is(createdUser.getName())));

  }
}
