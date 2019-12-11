package com.yaji.traderev.carauction.services.auctions;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;

public class SqlBackedCarAuctionService
    extends AbstractResourceService<CarAuction, CarAuctionRequestDto>
    implements ICarAuctionService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @Transactional
  @Modifying
  @Override
  public CarAuction modifyResource(Integer auctionId, CarAuctionRequestDto dto)
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
}
