package com.yaji.traderev.carauction.enums;

public enum CarAuctionStatus {
  UNVERIFIED(100),
  VERIFICATION_IN_PROGRESS(110),
  AUCTION_SCHEDULED(200),
  READY_TO_AUCTION(210),
  AUCTION_IN_PROGRESS(300),
  SOLD(400),
  UNSOLD(500);

  private int statusCode;

  CarAuctionStatus(int statusCode) {
    this.statusCode = statusCode;
  }
}
