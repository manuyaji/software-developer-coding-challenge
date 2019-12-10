package com.yaji.traderev.carauction.models.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequestDto {

  private boolean shouldOverwriteAllFields;
  private UserInfoRequestDtoInfo info;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserInfoRequestDtoInfo {
	  private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String alternateEmailAddress;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String address;
    private String zipcode;
    private String driversLicenseNum;
    private String driversLicenseImageInfo;
    
  }
}
