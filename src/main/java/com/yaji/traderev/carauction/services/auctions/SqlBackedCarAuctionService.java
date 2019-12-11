package com.yaji.traderev.carauction.services.auctions;

import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.entity.ColumnNameConstants;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.enums.CarAuctionStatus;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.services.bids.ICarBidService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlBackedCarAuctionService
    extends AbstractResourceService<CarAuction, CarAuctionRequestDto>
    implements ICarAuctionService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;
  @Autowired private ICarBidService carBidService;

  @Transactional
  @Modifying
  @Override
  public CarAuction modifyResource(Integer auctionId, CarAuctionRequestDto dto)
      throws TradeRevInvalidInputException {
    CarAuction original =
        repository
            .findById(auctionId)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.AUCTION_RESOURCE_NOT_FOUND, auctionId));
    CarAuction merged = dtoToEntityMergingUtil.mergeCarAuctionWithDto(original, dto);
    CarAuction auction = repository.save(merged);
    return auction;
  }

  @Transactional
  @Modifying
  @Override
  public CarAuction createResource(CarAuctionRequestDto dto) {
    CarAuction auction = dtoToEntityMergingUtil.mergeCarAuctionWithDto(null, dto);
    CarAuction newAuction = repository.save(auction);
    return newAuction;
  }

  @Override
  public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ErrorCode getResourceNotFoundErrorCode() {
    return ErrorCode.AUCTION_RESOURCE_NOT_FOUND;
  }

  @Override
  public ErrorCode getResourcesNotFoundErrorCode() {
    return ErrorCode.AUCTIONS_NOT_FOUND;
  }

  @Override
  public CarBid getWinningBid(Integer auctionId)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException {
    CarAuction carAuction = getResource(auctionId);
    if (carAuction != null) {
      if (carAuction.isNotYetOpen()) {
        log.error("Auction not yet open for auctionId {}.", auctionId);
        throw new TradeRevInvalidInputException(ErrorCode.AUCTION_NOT_OPEN_YET, auctionId);
      }
      List<CarBid> carBids =
          carBidService.getCarBids(
              auctionId,
              0,
              1,
              ColumnNameConstants.CarBidTable.CAR_BID_AMOUNT_COLUMN_NAME,
              ResourceSortingOrder.DESCENDING);
      if (carBids == null || carBids.isEmpty()) {
        return null;
      } else if (carBids != null && carBids.size() == 1) {
        return carBids.get(0);
      } else {
        log.error(
            "More than 1 winning bid found for auctionId {}. Winning Bids: {}", auctionId, carBids);
        throw new TradeRevIllegalStateException(
            ErrorCode.INTERNAL_STATE_ERROR, MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME));
      }
    }
    log.error("Auction not found for auctionId {}.", auctionId);
    throw new TradeRevResourceNotFoundException(ErrorCode.AUCTION_RESOURCE_NOT_FOUND, auctionId);
  }

  @Override
  @Transactional
  @Modifying
  public CarAuction closeAuction(Integer auctionId) throws TradeRevInvalidInputException {
    CarAuction carAuction = getResource(auctionId);
    if (carAuction != null) {
      if (carAuction.isClosed()) {
        log.error("Auction [{}] already closed. CarAuction : {}", auctionId, carAuction);
        throw new TradeRevInvalidInputException(ErrorCode.AUCTION_CLOSED, auctionId);
      }
      if (carAuction.isNotYetOpen()) {
        carAuction.setStatus(CarAuctionStatus.UNSOLD_WITHOUT_AUCTIONING);
      } else {
        CarBid winningBid = null;
        try {
          winningBid = getWinningBid(auctionId);
        } catch (TradeRevResourceNotFoundException e) {
          log.warn(
              "Winning Bid not found for auctionId {} while closing the auction. Marking this as UNSOLD.");
        }
        if (winningBid != null) {
          carAuction.setSellingBidId(winningBid.getId());
          carAuction.setSellingPrice(winningBid.getBidAmount());
          carAuction.setStatus(CarAuctionStatus.SOLD);
          carAuction.setBuyer(winningBid.getBidBy());
        } else {
          carAuction.setStatus(CarAuctionStatus.UNSOLD);
        }
      }
      return carAuction;
    }
    log.error("Auction not found for auctionId {}.", auctionId);
    throw new TradeRevResourceNotFoundException(ErrorCode.AUCTION_RESOURCE_NOT_FOUND, auctionId);
  }

@Override
public List<CarBid> getHistory(Integer auctionId, Integer page, Integer size, ResourceSortingOrder order) throws TradeRevInvalidInputException, TradeRevResourceNotFoundException {
	List<CarBid> ret = carBidService.getCarBids(auctionId, page, size, ColumnNameConstants.CarBidTable.CAR_BID_TIME_OF_BID, order);
	log.info("Returning history of auction {} : {}", auctionId, ret);
	return ret;
}
  
  
}
