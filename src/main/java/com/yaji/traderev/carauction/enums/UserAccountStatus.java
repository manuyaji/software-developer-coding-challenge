package com.yaji.traderev.carauction.enums;

public enum UserAccountStatus {
  ACTIVE(1),
  UNVERIFIED(100),
  INCOMPLETE_DETAILS(200),
  SUSPENDED(300),
  DELETED(400),
  INACTIVE(500),
  DECEASED(600);

  private int status;

  UserAccountStatus(int status) {
    this.status = status;
  }
}
