package com.yaji.traderev.carauction.services.user;

import com.yaji.traderev.carauction.entity.UserInfo;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevInvalidInputException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.UserInfoRequestDto;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class SqlBackedUserInfoService extends AbstractResourceService<UserInfo, UserInfoRequestDto>
    implements IUserInfoService {

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @Override
  public UserInfo createResource(UserInfoRequestDto dto) throws TradeRevInvalidInputException {
    UserInfo userInfo = dtoToEntityMergingUtil.mergeUserInfoWithDto(null, dto);
    UserInfo newUserInfo = repository.save(userInfo);
    return newUserInfo;
  }

  @Override
  public UserInfo modifyResource(Integer id, UserInfoRequestDto dto)
      throws TradeRevInvalidInputException {
    UserInfo original =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.USER_INFO_RESOURCE_NOT_FOUND, id));
    UserInfo merged = dtoToEntityMergingUtil.mergeUserInfoWithDto(original, dto);
    UserInfo userInfo = repository.save(merged);
    return userInfo;
  }

  @Override
  public ErrorCode getResourceNotFoundErrorCode() {
    return ErrorCode.USER_INFO_RESOURCE_NOT_FOUND;
  }

  @Override
  public ErrorCode getResourcesNotFoundErrorCode() {
    return ErrorCode.USER_INFOS_NOT_FOUND;
  }
}
