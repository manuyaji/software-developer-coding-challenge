package com.yaji.traderev.carauction.services.auctions;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.services.IResourceService;
import java.util.List;

public interface ICarAuctionService extends IResourceService<CarAuction, CarAuctionRequestDto> {

  public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException;

  public CarBid getWinningBid(Integer auctionId)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException;

  public CarAuction closeAuction(Integer auctionId) throws TradeRevInvalidInputException;
}
