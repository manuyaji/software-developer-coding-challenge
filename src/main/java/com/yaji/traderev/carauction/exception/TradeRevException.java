package com.yaji.traderev.carauction.exception;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;

import lombok.Getter;

@Getter
public class TradeRevException extends Exception {
	
	protected ErrorCode errorCode;
	protected Object[] errorCodeParams;
	protected ResponseMetadata responseMetadata;

  public TradeRevException(String message, Throwable cause) {
    super(message, cause);
  }

  public TradeRevException(String message) {
    super(message);
  }

  public TradeRevException(Throwable cause) {
    super(cause);
  }

  public TradeRevException(Throwable cause, ErrorCode errorCode, Object... errorCodeParams) {
	  super(cause);
	    this.errorCode = errorCode;
	    this.errorCodeParams = errorCodeParams;
  }

  public TradeRevException(ErrorCode errorCode, Object... errorCodeParams) {
	  this.errorCode = errorCode;
	    this.errorCodeParams = errorCodeParams;
  }
}
