package com.yaji.traderev.carauction.enums;

public enum Role {
  ADMIN(1),
  USER_NORMAL(100),
  USER_PRIME(101);

  private int roleId;

  Role(int roleId) {
    this.roleId = roleId;
  }

  public int getRoleId() {
    return roleId;
  }

  public static Role getRoleFromRoleId(Integer roleId) {
    switch (roleId) {
      case 1:
        return ADMIN;
      case 100:
        return USER_NORMAL;
      case 101:
        return USER_PRIME;
      default:
        return null;
    }
  }
}
