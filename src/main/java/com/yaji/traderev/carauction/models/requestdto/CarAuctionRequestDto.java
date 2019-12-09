package com.yaji.traderev.carauction.models.requestdto;

import com.yaji.traderev.carauction.enums.CarAuctionStatus;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarAuctionRequestDto {

  private boolean shouldOverwriteAllFields;
  private CarAuctionRequestDtoInfo info;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CarAuctionRequestDtoInfo {
    private CarAuctionStatus status;
    private Double openingBid;
    private Double sellingPrice;
    private Integer carInfoId;
    private Integer sellerId;
    private Integer buyerId;
    private ZonedDateTime auctionDate;
    private Integer sellingBidId;
  }
}
