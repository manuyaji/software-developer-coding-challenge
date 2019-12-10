package com.yaji.traderev.carauction.filter;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.metrics.collector.IRequestResponseDataCollector;
import com.yaji.traderev.carauction.metrics.model.RequestResponseData;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(40)
@Slf4j
@Component
public class MetricsFilter extends HttpFilter {

  @Autowired
  @Qualifier("metricsCollectorExecutorService")
  private ExecutorService metricsCollectorExecutorService;

  @Autowired private IRequestResponseDataCollector dataCollector;

  @Override
  protected void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    try {
      super.doFilter(request, response, chain);
    } finally {
      long endTime = System.currentTimeMillis();
      long appResponseTime = endTime - startTime;
      response.addHeader(HeaderConstants.RESPONSE_TIME_HEADERNAME, String.valueOf(appResponseTime));
      collectData(request, response, appResponseTime);
    }
  }

  private void collectData(
      HttpServletRequest request, HttpServletResponse response, long appResponseTime) {
    try {
      Integer responseSize = Integer.parseInt(response.getHeader("content-length"));
      RequestResponseData data =
          RequestResponseData.builder()
              .apiPath(request.getRequestURI())
              .clientIp(request.getRemoteAddr())
              .processingTimeMs(appResponseTime)
              .requestUrl(request.getRequestURL().toString())
              .responseCode(response.getStatus())
              .responseSize(responseSize)
              .serverIp(request.getLocalAddr())
              .serverName(request.getServerName())
              .requestSize(request.getContentLength())
              .build();
      metricsCollectorExecutorService.submit(() -> dataCollector.collect(data));
    } catch (Exception e) {
      log.error("Error while pushing request-response metrics.", e);
    }
  }
}
