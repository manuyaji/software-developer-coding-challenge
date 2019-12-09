package com.yaji.traderev.carauction.filter;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.util.LocaleUtil;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(45)
@Slf4j
@Component
public class LocaleSettingFilter extends HttpFilter {
  @Autowired private LocaleUtil localeUtil;

  @Override
  protected void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      Locale locale = LocaleUtil.DEFAULT_LOCALE;
      String localeStr = request.getHeader(HeaderConstants.ACCEPT_LANGUAGE_HEADERNAME);
      if (localeStr != null && !localeStr.isEmpty()) {
        try {
          locale = localeUtil.createLocaleFromStr(localeStr);
        } catch (Exception e) {
          log.warn(
              "Locale {} passed in {} cannot be identified",
              localeStr,
              HeaderConstants.ACCEPT_LANGUAGE_HEADERNAME);
          // DO NOTHING
        }
      }
      localeUtil.setCurrentLocale(locale);
      super.doFilter(request, response, chain);
    } finally {
      localeUtil.setCurrentLocale(LocaleUtil.DEFAULT_LOCALE);
    }
  }
}
