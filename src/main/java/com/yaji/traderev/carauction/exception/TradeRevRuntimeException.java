package com.yaji.traderev.carauction.exception;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import lombok.Getter;

@Getter
public class TradeRevRuntimeException extends RuntimeException {

  protected ErrorCode errorCode;
  protected Object[] errorCodeParams;
  protected ResponseMetadata responseMetadata;

  public TradeRevRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public TradeRevRuntimeException(String message) {
    super(message);
  }

  public TradeRevRuntimeException(Throwable cause) {
    super(cause);
  }

  public TradeRevRuntimeException(Throwable cause, ErrorCode errorCode, Object... errorCodeParams) {
    super(cause);
    this.errorCode = errorCode;
    this.errorCodeParams = errorCodeParams;
  }

  public TradeRevRuntimeException(ErrorCode errorCode, Object... errorCodeParams) {
    this.errorCode = errorCode;
    this.errorCodeParams = errorCodeParams;
  }

  public TradeRevRuntimeException(
      Throwable cause,
      ResponseMetadata responseMetadata,
      ErrorCode errorCode,
      Object... errorCodeParams) {
    super(cause);
    this.errorCode = errorCode;
    this.errorCodeParams = errorCodeParams;
  }

  public TradeRevRuntimeException(
      ResponseMetadata responseMetadata, ErrorCode errorCode, Object... errorCodeParams) {
    this.errorCode = errorCode;
    this.errorCodeParams = errorCodeParams;
  }
}
