package com.yaji.traderev.carauction.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "metrics")
@Data
public class MetricsConfig {

  private Integer collectorThreadpoolSize = 10;
}
