package com.yaji.traderev.carauction.services.carinfo;

import com.yaji.traderev.carauction.entity.CarInfo;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto;
import com.yaji.traderev.carauction.repository.db.CarInfoRepository;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SqlBackedCarInfoService implements ICarInfoService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;
  @Autowired private CarInfoRepository repository;

  @Override
  public CarInfo getCarInfo(Integer id) throws TradeRevResourceNotFoundException {
    CarInfo carInfo =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.CAR_INFO_RESOURCE_NOT_FOUND, id));
    return carInfo;
  }

  @Override
  public List<CarInfo> getCarInfos(Integer page, Integer size, String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<CarInfo> carInfosPage = repository.findAll(pageable);
    if (carInfosPage == null || carInfosPage.isEmpty()) {
      throw new TradeRevResourceNotFoundException(
          ErrorCode.CAR_INFOS_NOT_FOUND, page, size, sortBy);
    }
    List<CarInfo> carInfos = carInfosPage.toList();
    return carInfos;
  }

  @Override
  public CarInfo createCarInfo(CarInfoRequestDto dto) {
    CarInfo carInfo = dtoToEntityMergingUtil.mergeCarInfoWithDto(null, dto);
    CarInfo newCarInfo = repository.save(carInfo);
    return newCarInfo;
  }

  @Override
  public CarInfo modifyCarInfo(Integer id, CarInfoRequestDto dto) {
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
}
