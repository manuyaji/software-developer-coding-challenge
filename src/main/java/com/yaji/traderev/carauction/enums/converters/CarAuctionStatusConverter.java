package com.yaji.traderev.carauction.enums.converters;

import com.yaji.traderev.carauction.enums.CarAuctionStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CarAuctionStatusConverter implements AttributeConverter<CarAuctionStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(CarAuctionStatus caStatus) {
    return caStatus.getStatusCode();
  }

  @Override
  public CarAuctionStatus convertToEntityAttribute(Integer statusCode) {
    return CarAuctionStatus.getCarAuctionStatusFromStatusCode(statusCode);
  }
}
