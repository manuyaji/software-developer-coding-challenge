package com.yaji.traderev.carauction.exception;

import com.yaji.traderev.carauction.enums.ErrorCode;

public class TradeRevResourceNotFoundException extends TradeRevRuntimeException {

  public TradeRevResourceNotFoundException(ErrorCode errorCode, Object... errorCodeParams) {
    super(errorCode, errorCodeParams);
  }

  public TradeRevResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TradeRevResourceNotFoundException(String message) {
    super(message);
  }

  public TradeRevResourceNotFoundException(
      Throwable cause, ErrorCode errorCode, Object... errorCodeParams) {
    super(cause, errorCode, errorCodeParams);
  }

  public TradeRevResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
