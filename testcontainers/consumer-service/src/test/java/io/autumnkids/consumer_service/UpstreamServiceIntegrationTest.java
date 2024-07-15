package io.autumnkids.consumer_service;

import io.autumnkids.consumer_service.greeting.application.GreetController;
import io.autumnkids.consumer_service.greeting.application.mappers.GreetUserMapper;
import io.autumnkids.consumer_service.greeting.domain.services.GreetService;
import io.autumnkids.consumer_service.greeting.persistent.repositories.GreetUserRepository;
import io.autumnkids.consumer_service.upstream_rest_service.UpstreamRestServiceProperties;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@WebMvcTest(GreetController.class)
@Testcontainers
class UpstreamServiceIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Container
	static GenericContainer<?> upstreamService = new GenericContainer<>(DockerImageName.parse("upstream-rest-service-service:latest"))
			.withExposedPorts(9001);

	@Test
	void testGreetEndpointGetRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/greet").param("name", "Test"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Hello Test!\"}"));
	}

	@TestConfiguration
	static class GreetServiceITConfig {

		@Bean
		GreetService getRealGreetServiceImpl() {
			upstreamService.start();
			UpstreamRestServiceProperties properties = new UpstreamRestServiceProperties();
			properties.setHost(upstreamService.getHost());
			properties.setPort(upstreamService.getFirstMappedPort());
			return new GreetService(properties, Mockito.mock(GreetUserRepository.class), Mappers.getMapper(GreetUserMapper.class));
		}
	}

}
