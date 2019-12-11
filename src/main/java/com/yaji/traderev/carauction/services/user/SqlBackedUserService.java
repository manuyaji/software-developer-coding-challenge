/*package com.yaji.traderev.carauction.services.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.entity.UserInfo;
import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.models.requestdto.UserRequestDto;
import com.yaji.traderev.carauction.services.AbstractResourceService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;

public class SqlBackedUserService extends AbstractResourceService<User, UserRequestDto> implements IUserService
{
	@Autowired
	private DtoToEntityMergingUtil dtoToEntityMergingUtil;

	@Override
	public User createResource(UserRequestDto dto) throws TradeRevIllegalStateException {
		UserInfo userInfo = dtoToEntityMergingUtil.mergeUserInfoWithDto(null, dto);
		UserInfo newUserInfo = repository.save(userInfo);
	    return newUserInfo;
	}

	@Override
	public User modifyResource(Integer id, UserRequestDto dto) throws TradeRevIllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

}
*/
