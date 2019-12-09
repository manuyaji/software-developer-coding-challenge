package com.yaji.traderev.carauction.models.responsedto;

import com.yaji.traderev.carauction.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseError {

  private int severity;
  private ErrorCode errorCode;
  private String shortMessage;
  private String description;
}
