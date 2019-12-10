package com.yaji.traderev.carauction.services.bids;

import java.util.List;

import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;

public interface ICarBidService {
	
	public CarBid modifyCarBid(Integer auctionid, Integer carBidId, CarBidRequestDto dto) throws TradeRevIllegalStateException;
	public CarBid createCarBid(Integer auctionid, CarBidRequestDto dto) throws TradeRevIllegalStateException;
	public CarBid getCarBid(Integer auctionId, Integer bidId) throws TradeRevResourceNotFoundException;
	public List<CarBid> getCarBids(Integer auctionId, Integer page, Integer size, String sortBy) throws TradeRevResourceNotFoundException;
	public CarBid getWinningBid(Integer auctionId) throws TradeRevIllegalStateException;

}
