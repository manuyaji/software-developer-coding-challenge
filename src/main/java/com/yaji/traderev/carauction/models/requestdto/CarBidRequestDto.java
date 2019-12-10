package com.yaji.traderev.carauction.models.requestdto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarBidRequestDto {

  private boolean shouldOverwriteAllFields;
  private CarBidRequestDtoInfo info;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CarBidRequestDtoInfo {
    private Integer carAuctionId;
    private ZonedDateTime timeOfBid;
    private Double bidAmount;
    private Integer bidBy;
  }
}
