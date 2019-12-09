package com.yaji.traderev.carauction.config;

import com.yaji.traderev.carauction.controller.constants.SeparatorConstants;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "locale")
public class LocaleConfig {

  private String supportedLocales;

  public String getSupportedLocales() {
    return this.supportedLocales;
  }

  public List<String> getListOfSupportedLocales() {
    return Arrays.asList(supportedLocales.split(SeparatorConstants.COMMA));
  }

  public void setSupportedLocales(String supportedLocales) {
    this.supportedLocales = supportedLocales;
  }
}
