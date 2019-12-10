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

  public int getStatusCode() {
    return statusCode;
  }

  public static CarAuctionStatus getCarAuctionStatusFromStatusCode(Integer statCode) {
    switch (statCode) {
      case 100:
        return UNVERIFIED;
      case 110:
        return VERIFICATION_IN_PROGRESS;
      case 200:
        return AUCTION_SCHEDULED;
      case 210:
        return READY_TO_AUCTION;
      case 300:
        return AUCTION_IN_PROGRESS;
      case 400:
        return SOLD;
      case 500:
        return UNSOLD;
      default:
        return null;
    }
  }
}
