package io.autumnkids.consumer_service;

import io.autumnkids.consumer_service.greeting.GreetController;
import io.autumnkids.consumer_service.greeting.GreetService;
import io.autumnkids.consumer_service.upstream_rest_service.UpstreamRestServiceProperties;
import org.junit.jupiter.api.Test;
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
class GreetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Container
	static GenericContainer<?> upstreamService = new GenericContainer<>(DockerImageName.parse("upstream-rest-service-service:latest"))
			.withExposedPorts(9001);

	@Test
	void contextLoads() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/greet").param("name", "Test"))
				.andDo(MockMvcResultHandlers.print())
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
			return new GreetService(properties);
		}
	}

}
