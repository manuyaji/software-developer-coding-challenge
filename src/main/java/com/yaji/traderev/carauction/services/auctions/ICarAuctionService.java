package com.yaji.traderev.carauction.services.auctions;

import java.util.List;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;

public interface ICarAuctionService {
	
	public CarAuction modifyCarAuction(Integer auctionid, CarAuctionRequestDto dto) throws TradeRevIllegalStateException;
	public CarAuction createCarAuction(CarAuctionRequestDto dto);
	public CarAuction getCarAuction(Integer auctionId) throws TradeRevResourceNotFoundException;
	public List<CarAuction> getCarAuctions(Integer page, Integer size, String sortBy) throws TradeRevResourceNotFoundException;
	public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException;

}
