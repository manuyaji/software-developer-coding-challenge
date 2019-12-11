package com.yaji.traderev.carauction.services.auctions;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.services.IResourceService;
import java.util.List;

public interface ICarAuctionService extends IResourceService<CarAuction, CarAuctionRequestDto> {

  public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException;
}
