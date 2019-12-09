package com.yaji.traderev.carauction.controller;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.exception.translation.ErrorProperties;
import com.yaji.traderev.carauction.exception.translation.ErrorPropertiesReader;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseError;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import com.yaji.traderev.carauction.util.LocaleUtil;

/*
 * Reference:
 *     https://www.baeldung.com/exception-handling-for-rest-with-spring
 * Handle all these: 
 * 		https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ErrorPropertiesReader errorPropertiesReader;
	@Autowired
	private LocaleUtil localeUtil;
	
	@ExceptionHandler(value={TradeRevResourceNotFoundException.class})
	public ResponseEntity<ResponseDto> handleResourceNotFound(HttpServletRequest request, TradeRevResourceNotFoundException e){
		Locale locale = localeUtil.getCurrentLocale();
		ResponseMetadata metadata = buildResponseMetadata();
		String selfLink = request.getRequestURI();
		ResponseDto ret = null;
		if(locale == null){
			locale = LocaleUtil.DEFAULT_LOCALE;
		}
		ErrorProperties errorProperties = errorPropertiesReader.getErrorProperties(locale);
		if(errorProperties != null){
			ResponseError re = errorProperties.getResponseErrorDetails(e.getErrorCode(), e.getErrorCodeParams());
			if(re != null){
				ret = ResponseDto.builder().errors(re != null ? Arrays.asList(re) : null)
						.metadata(metadata)
						.self(selfLink)
						.build();
			}
		}
		if(ret == null){
			ret = ResponseDto.builder().metadata(metadata).self(selfLink).build();
		}
		return new ResponseEntity<ResponseDto>(ret, HttpStatus.NOT_FOUND);
	}
	
	private ResponseMetadata buildResponseMetadata(){
		String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
		String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
		ResponseMetadata metadata = ResponseMetadata.builder().correlationId(correlationId).requestId(requestId).build();
		return metadata;
	}
}
