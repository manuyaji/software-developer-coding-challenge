package com.yaji.traderev.carauction.filter;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.logs.IdGenerator;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(30)
@Slf4j
@Component
public class LoggingInterceptorFilter extends HttpFilter {

  @Autowired private IdGenerator idGenerator;

  @Override
  protected void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String correlationId = request.getHeader(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = request.getHeader(HeaderConstants.REQUEST_ID_HEADERNAME);

    if (correlationId == null || correlationId.isEmpty()) {
      correlationId = idGenerator.generateStringId();
    }

    if (requestId == null || requestId.isEmpty()) {
      requestId = idGenerator.generateStringId();
    }

    log.info("correlationId: {} ; requestId : {}");
    MDC.put(HeaderConstants.CORRELATION_ID_HEADERNAME, correlationId);
    MDC.put(HeaderConstants.REQUEST_ID_HEADERNAME, requestId);
    super.doFilter(request, response, chain);
  }
}
