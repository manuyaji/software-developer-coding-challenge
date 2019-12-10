package com.yaji.traderev.carauction.services.auctions;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.repository.db.CarAuctionRepository;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;

public class SqlBackedCarAuctionService implements ICarAuctionService {

  @Autowired private CarAuctionRepository repository;
  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @Transactional
  @Modifying
  @Override
  public CarAuction modifyCarAuction(Integer auctionId, CarAuctionRequestDto dto)
      throws TradeRevIllegalStateException {
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
  public CarAuction createCarAuction(CarAuctionRequestDto dto) {
    CarAuction auction = dtoToEntityMergingUtil.mergeCarAuctionWithDto(null, dto);
    CarAuction newAuction = repository.save(auction);
    return newAuction;
  }

  @Override
  public CarAuction getCarAuction(Integer auctionId) throws TradeRevResourceNotFoundException {
    CarAuction auction =
        repository
            .findById(auctionId)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.AUCTION_RESOURCE_NOT_FOUND, auctionId));
    return auction;
  }

  @Override
  public List<CarAuction> getCarAuctions(Integer page, Integer size, String sortBy)
      throws TradeRevResourceNotFoundException {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<CarAuction> auctionsPage = repository.findAll(pageable);
    if (auctionsPage == null || auctionsPage.isEmpty()) {
      throw new TradeRevResourceNotFoundException(ErrorCode.AUCTIONS_NOT_FOUND, page, size, sortBy);
    }
    List<CarAuction> auctions = auctionsPage.toList();
    return auctions;
  }

  @Override
  public List<CarAuction> getCarAuctions(User user) throws TradeRevResourceNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }
}
