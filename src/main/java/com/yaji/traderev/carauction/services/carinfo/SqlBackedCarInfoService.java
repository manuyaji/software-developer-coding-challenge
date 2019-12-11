package com.yaji.traderev.carauction.services.carinfo;

import com.yaji.traderev.carauction.entity.CarInfo;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;

public class SqlBackedCarInfoService extends AbstractResourceService<CarInfo, CarInfoRequestDto>
    implements ICarInfoService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @Override
  @Transactional
  @Modifying
  public CarInfo createResource(CarInfoRequestDto dto) {
    CarInfo carInfo = dtoToEntityMergingUtil.mergeCarInfoWithDto(null, dto);
    CarInfo newCarInfo = repository.save(carInfo);
    return newCarInfo;
  }

  @Override
  @Transactional
  @Modifying
  public CarInfo modifyResource(Integer id, CarInfoRequestDto dto) {
    CarInfo original =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.CAR_INFO_RESOURCE_NOT_FOUND, id));
    CarInfo merged = dtoToEntityMergingUtil.mergeCarInfoWithDto(original, dto);
    CarInfo carInfo = repository.save(merged);
    return carInfo;
  }

  @Override
  public ErrorCode getResourceNotFoundErrorCode() {
    return ErrorCode.CAR_INFO_RESOURCE_NOT_FOUND;
  }

  @Override
  public ErrorCode getResourcesNotFoundErrorCode() {
    return ErrorCode.CAR_INFOS_NOT_FOUND;
  }
}
