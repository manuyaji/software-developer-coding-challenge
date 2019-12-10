package com.yaji.traderev.carauction.enums;

public enum MemberType {
  PRIME(200),
  NORMAL(1);

  private int typeId;

  MemberType(int typeId) {
    this.typeId = typeId;
  }

  public int getTypeId() {
    return typeId;
  }

  public static MemberType getMemberTypeFromTypeId(Integer typeId) {
    switch (typeId) {
      case 1:
        return NORMAL;
      case 200:
        return PRIME;
      default:
        return null;
    }
  }
}
