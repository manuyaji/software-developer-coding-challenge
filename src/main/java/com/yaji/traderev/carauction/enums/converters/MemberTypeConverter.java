package com.yaji.traderev.carauction.enums.converters;

import com.yaji.traderev.carauction.enums.MemberType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MemberTypeConverter implements AttributeConverter<MemberType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(MemberType type) {
    return type.getTypeId();
  }

  @Override
  public MemberType convertToEntityAttribute(Integer typeId) {
    return MemberType.getMemberTypeFromTypeId(typeId);
  }
}
