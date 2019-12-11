package com.yaji.traderev.carauction.services.bids;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.repository.db.CarBidRepository;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.services.auctions.ICarAuctionService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

@Slf4j
public class SqlBackedCarBidService extends AbstractResourceService<CarBid, CarBidRequestDto>
    implements ICarBidService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;
  @Autowired private ICarAuctionService carAuctionService;

  @Override
  @Transactional
  @Modifying
  public CarBid modifyCarBid(
      @NotNull Integer auctionId, @NotNull Integer bidId, @NotNull CarBidRequestDto dto)
      throws TradeRevInvalidInputException {
    CarBid original =
        repository
            .findById(bidId)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.BID_RESOURCE_NOT_FOUND, auctionId, bidId));
    if (!original.getCarAuction().getId().equals(auctionId)) {
      log.error("Cannot modify Bid. BidId {} does not belong to Auction {}", bidId, auctionId);
      throw new TradeRevInvalidInputException(
          ErrorCode.WRONG_AUCTION_TO_BID_MAPPING, bidId, auctionId);
    }
    CarBid merged = dtoToEntityMergingUtil.mergeCarBidWithDto(original, dto);
    CarBid bid = repository.save(merged);
    return bid;
  }

  private boolean verifyAuctionable(CarAuction carAuction) {
    if (!carAuction.isAlive()) {
      if (carAuction.isClosed()) {
        log.error(
            "Could not create Bid as auction is closed for bidding. auctionId {} ",
            carAuction.getId());
        throw new TradeRevInvalidInputException(ErrorCode.AUCTION_CLOSED, carAuction.getId());
      } else if (carAuction.isNotYetOpen()) {
        log.error(
            "Could not create Bid as auction is not yet open for bidding. auctionId {} ",
            carAuction.getId());
        throw new TradeRevInvalidInputException(ErrorCode.AUCTION_NOT_OPEN_YET, carAuction.getId());
      } else {
        log.error(
            "Could not create Bid as auction is not open for bidding. auctionId {} ",
            carAuction.getId());
        throw new TradeRevInvalidInputException(ErrorCode.AUCTION_NOT_OPEN_YET, carAuction.getId());
      }
    }
    return true;
  }

  @Override
  @Transactional
  @Modifying
  public CarBid createCarBid(@NotNull Integer auctionId, @NotNull CarBidRequestDto dto)
      throws TradeRevInvalidInputException, TradeRevResourceNotFoundException {
    CarAuction carAuction = carAuctionService.getResource(auctionId);
    verifyAuctionable(carAuction);

    if (dto.getInfo() != null && auctionId.equals(dto.getInfo().getCarAuctionId())) {
      CarBid currentWinningBid = carAuctionService.getWinningBid(auctionId);
      CarBid bid = dtoToEntityMergingUtil.mergeCarBidWithDto(null, dto);
      double minAmount =
          (currentWinningBid != null)
              ? currentWinningBid.getBidAmount()
              : carAuction.getOpeningBid();
      if (bid.getBidAmount() <= minAmount) {
        log.error(
            "Could not create Bid due to amount being lesser than winning bid. auctionId {} ; DTO {} ; minAmount: {}",
            auctionId,
            dto,
            minAmount);
        throw new TradeRevInvalidInputException(
            ErrorCode.BID_LESSER_THANOR_EQUAL_TO_WINNING_BID,
            auctionId,
            bid.getBidAmount(),
            minAmount);
      }
      try {
        CarBid newBid = repository.save(bid);
        return newBid;
      } catch (Exception e) {
        log.error("Could not save bid : {} : because of exception : {}", bid, e.getMessage(), e);
        if (e instanceof SQLIntegrityConstraintViolationException) {
          throw new TradeRevInvalidInputException(
              ErrorCode.BID_LESSER_THANOR_EQUAL_TO_WINNING_BID,
              auctionId,
              bid.getBidAmount(),
              currentWinningBid.getBidAmount());
        }
        throw e;
      }
    }
    log.error(
        "Could not create Bid due to conflict/issue in auctionIds. auctionId {} ; DTO {} ",
        auctionId,
        dto);
    throw new TradeRevInvalidInputException(
        ErrorCode.CONFLICTING_AUCTION_IDS,
        auctionId,
        dto.getInfo() != null ? dto.getInfo().getCarAuctionId() : null);
  }

  @Override
  public CarBid getCarBid(Integer auctionId, Integer bidId)
      throws TradeRevResourceNotFoundException {
    CarBid bid =
        repository
            .findById(bidId)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.BID_RESOURCE_NOT_FOUND, auctionId, bidId));
    if (bid.getCarAuction().getId() != auctionId) {
      log.error("Cannot Get Bid. BidId {} does not belong to Auction {}", bidId, auctionId);
      throw new TradeRevInvalidInputException(
          ErrorCode.WRONG_AUCTION_TO_BID_MAPPING, bidId, auctionId);
    }
    return bid;
  }

  @Override
  public List<CarBid> getCarBids(
      Integer auctionId, Integer page, Integer size, String sortBy, ResourceSortingOrder order)
      throws TradeRevResourceNotFoundException {
    Pageable pageable = PageRequest.of(page, size, getSortingOrder(sortBy, order));
    CarBidRepository carBidRepository = (CarBidRepository) repository;
    CarAuction carAuction = CarAuction.createNew();
    carAuction.setId(auctionId);
    Page<CarBid> bidsPage = carBidRepository.findByCarAuction(carAuction, pageable);
    if (bidsPage == null || bidsPage.isEmpty()) {
      // return empty list
      log.info("No bids found for AuctionId {}", auctionId);
      return new ArrayList<>();
    }
    List<CarBid> bids = bidsPage.toList();
    return bids;
  }

  @Override
  @Deprecated
  public CarBid createResource(CarBidRequestDto dto) throws TradeRevInvalidInputException {
    throw new UnsupportedOperationException(
        "This operation - SqlBackedCarBidService.createResource(CarBidRequestDto dto) - is not supported.");
  }

  @Override
  @Deprecated
  public CarBid modifyResource(Integer id, CarBidRequestDto dto)
      throws TradeRevInvalidInputException {
    throw new UnsupportedOperationException(
        "This operation - SqlBackedCarBidService.modifyResource(Integer id, CarBidRequestDto dto) - is not supported.");
  }

  @Override
  public ErrorCode getResourceNotFoundErrorCode() {
    return ErrorCode.BID_RESOURCE_NOT_FOUND;
  }

  @Override
  public ErrorCode getResourcesNotFoundErrorCode() {
    return ErrorCode.BIDS_NOT_FOUND;
  }
}
