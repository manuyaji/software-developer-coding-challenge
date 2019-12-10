package com.yaji.traderev.carauction.metrics.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestResponseData {

  private String requestUrl;
  private String serverIp;
  private String serverName;
  private String apiPath;
  private Long processingTimeMs;
  private String clientIp;
  private int responseCode;
  private Integer responseSize;
  private Integer requestSize;
}
