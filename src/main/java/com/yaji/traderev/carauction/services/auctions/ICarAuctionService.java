package com.yaji.traderev.carauction.services.auctions;

import java.util.List;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.services.IResourceService;

public interface ICarAuctionService extends IResourceService<CarAuction, CarAuctionRequestDto> {

  public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException;

  public CarBid getWinningBid(Integer auctionId)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException;

  public CarAuction closeAuction(Integer auctionId) throws TradeRevInvalidInputException;
  
  public List<CarBid> getHistory(Integer auctionId, Integer page, Integer size, ResourceSortingOrder order) throws TradeRevInvalidInputException;
}
