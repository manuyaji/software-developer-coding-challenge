package com.yaji.traderev.carauction.enums.converters;

import com.yaji.traderev.carauction.enums.UserAccountStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserAccountStatusConverter implements AttributeConverter<UserAccountStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(UserAccountStatus userStatus) {
    return userStatus.getStatusCode();
  }

  @Override
  public UserAccountStatus convertToEntityAttribute(Integer statusCode) {
    return UserAccountStatus.getUserAccountStatusFromStatusCode(statusCode);
  }
}
