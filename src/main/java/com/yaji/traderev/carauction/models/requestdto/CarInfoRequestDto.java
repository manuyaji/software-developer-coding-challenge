package com.yaji.traderev.carauction.models.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoRequestDto {

  private boolean shouldOverwriteAllFields;
  private CarInfoRequestDtoInfo info;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CarInfoRequestDtoInfo {
    private Integer ownerId;
    private String manufacturer;
    private String model;
    private String year;
  }
}
