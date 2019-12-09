package com.yaji.traderev.carauction.logs;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {

  @Override
  public String generateStringId() {
    return UUID.randomUUID().toString();
  }
}
