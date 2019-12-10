package com.yaji.traderev.carauction.enums;

public enum UserAccountStatus {
  ACTIVE(1),
  UNVERIFIED(100),
  INCOMPLETE_DETAILS(200),
  SUSPENDED(300),
  DELETED(400),
  INACTIVE(500),
  DECEASED(600);

  private int statusCode;

  UserAccountStatus(int status) {
    this.statusCode = status;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public static UserAccountStatus getUserAccountStatusFromStatusCode(Integer statusCode) {
    switch (statusCode) {
      case 1:
        return ACTIVE;
      case 100:
        return UNVERIFIED;
      case 200:
        return INCOMPLETE_DETAILS;
      case 300:
        return SUSPENDED;
      case 400:
        return DELETED;
      case 500:
        return INACTIVE;
      case 600:
        return DECEASED;
      default:
        return null;
    }
  }
}
