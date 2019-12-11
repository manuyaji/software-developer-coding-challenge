package com.yaji.traderev.carauction.controller;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.exception.TradeRevException;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.exception.TradeRevRuntimeException;
import com.yaji.traderev.carauction.exception.translation.ErrorProperties;
import com.yaji.traderev.carauction.exception.translation.ErrorPropertiesReader;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseError;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import com.yaji.traderev.carauction.util.LocaleUtil;
import java.util.Arrays;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Reference:
 *     https://www.baeldung.com/exception-handling-for-rest-with-spring
 * Handle all these:
 * 		https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired private ErrorPropertiesReader errorPropertiesReader;
  @Autowired private LocaleUtil localeUtil;

  private ResponseError getResponseError(Exception e, Locale locale) {
    ResponseError ret = null;
    if (e instanceof TradeRevException || e instanceof TradeRevRuntimeException) {
      ErrorProperties errorProperties = errorPropertiesReader.getErrorProperties(locale);
      if (errorProperties != null) {
        if (e instanceof TradeRevException) {
          TradeRevException tre = (TradeRevException) e;
          ret =
              errorProperties.getResponseErrorDetails(tre.getErrorCode(), tre.getErrorCodeParams());
        } else if (e instanceof TradeRevRuntimeException) {
          TradeRevRuntimeException trre = (TradeRevRuntimeException) e;
          ret =
              errorProperties.getResponseErrorDetails(
                  trre.getErrorCode(), trre.getErrorCodeParams());
        }
      }
    }

    return ret;
  }

  private ResponseDto getResponseDto(HttpServletRequest request, Exception e) {
    Locale locale = localeUtil.getCurrentLocale();
    ResponseMetadata metadata = buildResponseMetadata();
    String selfLink = request.getRequestURI();
    ResponseDto ret = null;
    if (locale == null) {
      locale = LocaleUtil.DEFAULT_LOCALE;
    }
    ResponseError re = getResponseError(e, locale);

    if (re != null) {
      ret =
          ResponseDto.builder()
              .errors(re != null ? Arrays.asList(re) : null)
              .metadata(metadata)
              .self(selfLink)
              .build();
    }
    if (ret == null) {
      ret = ResponseDto.builder().metadata(metadata).self(selfLink).build();
    }
    return ret;
  }

  @ExceptionHandler(value = {TradeRevResourceNotFoundException.class})
  public ResponseEntity<ResponseDto> handleResourceNotFound(
      HttpServletRequest request, TradeRevResourceNotFoundException e) {
    log.info("ControllerAdvice handling TradeRevResourceNotFoundException e {} ", e.getMessage());
    ResponseDto ret = getResponseDto(request, e);
    return new ResponseEntity<ResponseDto>(ret, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {TradeRevInvalidInputException.class})
  public ResponseEntity<ResponseDto> handleInvalidInputException(
      HttpServletRequest request, TradeRevInvalidInputException e) {
    log.info("ControllerAdvice handling TradeRevInvalidInputException e {} ", e.getMessage());
    ResponseDto ret = getResponseDto(request, e);
    return new ResponseEntity<ResponseDto>(ret, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {TradeRevIllegalStateException.class})
  public ResponseEntity<ResponseDto> handleIllegalStateException(
      HttpServletRequest request, TradeRevIllegalStateException e) {
    log.info("ControllerAdvice handling TradeRevInvalidInputException e {} ", e.getMessage());
    ResponseDto ret = getResponseDto(request, e);
    return new ResponseEntity<ResponseDto>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseMetadata buildResponseMetadata() {
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    ResponseMetadata metadata =
        ResponseMetadata.builder().correlationId(correlationId).requestId(requestId).build();
    return metadata;
  }
}
