package com.yaji.traderev.carauction.exception.translation;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.models.responsedto.ResponseError;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProperties {

  private ResourceBundle resourceBundle;
  private Locale locale;

  private static final String SEVERITY_FORMAT = "%s.severity";
  private static final String SHORT_MESSAGE_FORMAT = "%s.shortMessage";
  private static final String DESCRIPTION_FORMAT = "%s.description";

  public ErrorProperties(Locale locale) {
    this.resourceBundle = ResourceBundle.getBundle("errormessages/errors", locale);
    this.locale = locale;
  }

  public ResponseError getResponseErrorDetails(ErrorCode errorCode, Object... params) {
    try {
      int severity =
          Integer.parseInt(resourceBundle.getString(String.format(SEVERITY_FORMAT, errorCode)));
      String shortMessage =
          resourceBundle.getString(String.format(SHORT_MESSAGE_FORMAT, errorCode));
      String description =
          buildDescription(
              resourceBundle.getString(String.format(DESCRIPTION_FORMAT, errorCode)), params);
      return ResponseError.builder()
          .errorCode(errorCode)
          .severity(severity)
          .shortMessage(shortMessage)
          .description(description)
          .build();
    } catch (MissingResourceException e) {
      log.error("Error Properties not found for ErrorCode:{}, Locale:{}", errorCode, locale, e);
      return null;
    }
  }

  private String buildDescription(String format, Object... params) {
    if (format != null && params != null) {
      return String.format(format, params);
    }
    return null;
  }
}
