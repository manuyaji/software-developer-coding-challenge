package com.yaji.traderev.carauction.services.carinfo;

import com.yaji.traderev.carauction.entity.CarInfo;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto;
import java.util.List;

public interface ICarInfoService {

  public CarInfo getCarInfo(Integer id) throws TradeRevResourceNotFoundException;

  public List<CarInfo> getCarInfos(Integer page, Integer size, String sortBy)
      throws TradeRevResourceNotFoundException;

  public CarInfo createCarInfo(CarInfoRequestDto dto) throws TradeRevIllegalStateException;

  public CarInfo modifyCarInfo(Integer id, CarInfoRequestDto dto)
      throws TradeRevIllegalStateException;
}
