package com.yaji.traderev.carauction.exception;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;

public class TradeRevIllegalStateException extends TradeRevRuntimeException {

  public TradeRevIllegalStateException(ErrorCode errorCode, Object... errorCodeParams) {
    super(errorCode, errorCodeParams);
  }

  public TradeRevIllegalStateException(
      ResponseMetadata responseMetadata, ErrorCode errorCode, Object... errorCodeParams) {
    super(responseMetadata, errorCode, errorCodeParams);
  }

  public TradeRevIllegalStateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TradeRevIllegalStateException(String message) {
    super(message);
  }

  public TradeRevIllegalStateException(
      Throwable cause, ErrorCode errorCode, Object... errorCodeParams) {
    super(cause, errorCode, errorCodeParams);
  }

  public TradeRevIllegalStateException(
      Throwable cause,
      ResponseMetadata responseMetadata,
      ErrorCode errorCode,
      Object... errorCodeParams) {
    super(cause, responseMetadata, errorCode, errorCodeParams);
  }

  public TradeRevIllegalStateException(Throwable cause) {
    super(cause);
  }
}
