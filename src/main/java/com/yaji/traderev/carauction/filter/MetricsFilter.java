package com.yaji.traderev.carauction.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yaji.traderev.carauction.constants.HeaderConstants;

@Order(40)
@Component
public class MetricsFilter extends HttpFilter {

  @Override
  protected void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    super.doFilter(request, response, chain);
    long endTime = System.currentTimeMillis();
    long appResponseTime = endTime - startTime;
    response.addHeader(HeaderConstants.RESPONSE_TIME_HEADERNAME, String.valueOf(appResponseTime));
  }
}
