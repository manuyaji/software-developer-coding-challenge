package com.yaji.traderev.carauction.controller;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import org.slf4j.MDC;

public abstract class BaseController {

  public <T> ResponseDto buildResponseDto(T body, String selfLink) {
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    return ResponseDto.builder()
        .self(selfLink)
        .metadata(
            ResponseMetadata.builder().correlationId(correlationId).requestId(requestId).build())
        .body(body)
        .build();
  }

  public <T> ResponseDto buildResponseDto(
      T body, String selfLink, Integer page, Integer size, String sortBy) {
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    return ResponseDto.builder()
        .self(selfLink)
        .metadata(
            ResponseMetadata.builder()
                .correlationId(correlationId)
                .requestId(requestId)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build())
        .body(body)
        .build();
  }
}
