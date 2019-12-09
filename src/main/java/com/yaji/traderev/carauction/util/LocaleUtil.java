package com.yaji.traderev.carauction.util;

import com.yaji.traderev.carauction.controller.constants.SeparatorConstants;
import java.util.Locale;

public class LocaleUtil {

  public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  private static ThreadLocal<Locale> currentLocale = new ThreadLocal<>();

  public Locale createLocaleFromStr(String localeStr) {
    String[] parts = localeStr.split(SeparatorConstants.UNDERSCORE);
    if (parts.length == 3) {
      return new Locale(parts[0], parts[1], parts[2]);
    }
    if (parts.length == 2) {
      return new Locale(parts[0], parts[1]);
    }
    if (parts.length == 1) {
      return new Locale(parts[0]);
    }
    return LocaleUtil.DEFAULT_LOCALE;
  }

  public void setCurrentLocale(Locale locale) {
    currentLocale.set(locale);
  }

  public Locale getCurrentLocale() {
    return currentLocale.get();
  }
}
