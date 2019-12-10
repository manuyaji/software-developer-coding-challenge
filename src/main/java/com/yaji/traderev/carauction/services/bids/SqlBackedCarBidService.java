package com.yaji.traderev.carauction.services.bids;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;

import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.repository.db.CarBidRepository;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlBackedCarBidService implements ICarBidService{
	
	@Autowired
	private CarBidRepository repository;
	@Autowired
	private DtoToEntityMergingUtil dtoToEntityMergingUtil;

	@Override
	@Transactional
	@Modifying
	public CarBid modifyCarBid(@NotNull Integer auctionId, @NotNull Integer bidId, @NotNull CarBidRequestDto dto) throws TradeRevIllegalStateException{
		CarBid original =
		        repository
		            .findById(bidId)
		            .orElseThrow(
		                () ->
		                    new TradeRevResourceNotFoundException(
		                        ErrorCode.BID_RESOURCE_NOT_FOUND, auctionId, bidId));
		    if(!original.getCarAuction().getId().equals(auctionId)){
		    	throw new TradeRevIllegalStateException(ErrorCode.WRONG_AUCTION_TO_BID_MAPPING, auctionId, bidId);
		    }
		    CarBid merged = dtoToEntityMergingUtil.mergeCarBidWithDto(original, dto);
		    CarBid bid = repository.save(merged);
		    return bid;
	}

	@Override
	@Transactional
	@Modifying
	public CarBid createCarBid(@NotNull Integer auctionId, @NotNull CarBidRequestDto dto) throws TradeRevIllegalStateException{
		if(dto.getInfo() != null && auctionId.equals(dto.getInfo().getCarAuctionId())){
			CarBid bid = dtoToEntityMergingUtil.mergeCarBidWithDto(null, dto);
		    CarBid newBid = repository.save(bid);
		    return newBid;
		}
		log.error("Could not create Bid due to conflict/issue in auctionIds. auctionId {} ; DTO {} ", auctionId, dto);
		throw new TradeRevIllegalStateException(ErrorCode.CONFLICTING_AUCTION_IDS, auctionId, dto.getInfo() != null ? dto.getInfo().getCarAuctionId() : null);
	}

	@Override
	public CarBid getCarBid(Integer auctionId, Integer bidId) throws TradeRevResourceNotFoundException {
		CarBid bid =
		        repository
		            .findById(bidId)
		            .orElseThrow(
		                () ->
		                    new TradeRevResourceNotFoundException(
		                        ErrorCode.BID_RESOURCE_NOT_FOUND, auctionId, bidId));
		    if(bid.getCarAuction().getId() != auctionId){
		    	throw new TradeRevResourceNotFoundException(
		                ErrorCode.BID_RESOURCE_NOT_FOUND, auctionId, bidId);
		    }
		    return bid;
	}

	@Override
	public List<CarBid> getCarBids(Integer auctionId, Integer page, Integer size, String sortBy) throws TradeRevResourceNotFoundException {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<CarBid> bidsPage = repository.findAll(pageable);
	    if (bidsPage == null || bidsPage.isEmpty()) {
	      throw new TradeRevResourceNotFoundException(
	          ErrorCode.BIDS_NOT_FOUND, page, size, sortBy);
	    }
	    List<CarBid> bids = bidsPage.toList();
	    return bids;
	}

	@Override
	public CarBid getWinningBid(Integer auctionId) throws TradeRevIllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

}
