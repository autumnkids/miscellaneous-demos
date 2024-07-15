package io.autumnkids.consumer_service.upstream_rest_service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "upstream-rest-service")
@Getter
@Setter
public class UpstreamRestServiceProperties {

  private String host;

  private Integer port;
}
