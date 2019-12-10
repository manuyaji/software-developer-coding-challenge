package com.yaji.traderev.carauction.models.requestdto;

import com.yaji.traderev.carauction.enums.MemberType;
import com.yaji.traderev.carauction.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

  private boolean shouldOverwriteAllFields;
  private UserRequestDtoInfo info;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserRequestDtoInfo {
    private Role role;
    private MemberType memberType;
  }
}
