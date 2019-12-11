package com.yaji.traderev.carauction.services.bids;

import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.services.IResourceService;
import java.util.List;

public interface ICarBidService extends IResourceService<CarBid, CarBidRequestDto> {

  public CarBid modifyCarBid(Integer auctionid, Integer carBidId, CarBidRequestDto dto)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException;

  public CarBid createCarBid(Integer auctionid, CarBidRequestDto dto)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException;

  public CarBid getCarBid(Integer auctionId, Integer bidId)
      throws TradeRevResourceNotFoundException;

  public List<CarBid> getCarBids(
      Integer auctionId, Integer page, Integer size, String sortBy, ResourceSortingOrder order)
      throws TradeRevResourceNotFoundException;
}
