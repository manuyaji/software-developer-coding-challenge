package com.yaji.traderev.carauction.models.responsedto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {

  private String self;
  private ResponseMetadata metadata;
  private List<ResponseError> errors;
  private T body;
  
}
