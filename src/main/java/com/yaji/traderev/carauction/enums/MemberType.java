package com.yaji.traderev.carauction.enums;

public enum MemberType {
  PRIME(200),
  NORMAL(1);

  private int type;

  MemberType(int type) {
    this.type = type;
  }
}
