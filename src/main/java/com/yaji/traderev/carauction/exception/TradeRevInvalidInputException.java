package com.yaji.traderev.carauction.exception;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;

public class TradeRevInvalidInputException extends TradeRevRuntimeException {

  public TradeRevInvalidInputException(ErrorCode errorCode, Object... errorCodeParams) {
    super(errorCode, errorCodeParams);
  }

  public TradeRevInvalidInputException(
      ResponseMetadata responseMetadata, ErrorCode errorCode, Object... errorCodeParams) {
    super(responseMetadata, errorCode, errorCodeParams);
  }

  public TradeRevInvalidInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public TradeRevInvalidInputException(String message) {
    super(message);
  }

  public TradeRevInvalidInputException(
      Throwable cause, ErrorCode errorCode, Object... errorCodeParams) {
    super(cause, errorCode, errorCodeParams);
  }

  public TradeRevInvalidInputException(
      Throwable cause,
      ResponseMetadata responseMetadata,
      ErrorCode errorCode,
      Object... errorCodeParams) {
    super(cause, responseMetadata, errorCode, errorCodeParams);
  }

  public TradeRevInvalidInputException(Throwable cause) {
    super(cause);
  }
}
