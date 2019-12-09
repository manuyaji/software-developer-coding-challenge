package com.yaji.traderev.carauction.models.responsedto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMetadata {

  private Integer page;
  private Integer size;
  private String sortBy;
  private String correlationId;
  private String requestId;
}
