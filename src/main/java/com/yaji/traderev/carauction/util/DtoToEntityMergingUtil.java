package com.yaji.traderev.carauction.util;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.entity.CarInfo;
import com.yaji.traderev.carauction.entity.User;
import com.yaji.traderev.carauction.entity.UserInfo;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto.CarAuctionRequestDtoInfo;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto.CarBidRequestDtoInfo;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto.CarInfoRequestDtoInfo;
import com.yaji.traderev.carauction.models.requestdto.UserInfoRequestDto;
import com.yaji.traderev.carauction.models.requestdto.UserInfoRequestDto.UserInfoRequestDtoInfo;
import com.yaji.traderev.carauction.models.requestdto.UserRequestDto;
import com.yaji.traderev.carauction.models.requestdto.UserRequestDto.UserRequestDtoInfo;

public class DtoToEntityMergingUtil {

  public CarAuction mergeCarAuctionWithDto(CarAuction original, CarAuctionRequestDto dto) {
    if (original == null) {
      original = new CarAuction();
    }
    if (dto != null) {
      CarAuctionRequestDtoInfo info = dto.getInfo();
      if (dto.isShouldOverwriteAllFields() || info.getAuctionDate() != null) {
        original.setAuctionDate(info.getAuctionDate());
      }
      if (dto.isShouldOverwriteAllFields() || info.getBuyerId() != null) {
        original.getBuyer().setId(info.getBuyerId());
      }
      if (dto.isShouldOverwriteAllFields() || info.getCarInfoId() != null) {
        original.getCarInfo().setId(info.getCarInfoId());
      }
      if (dto.isShouldOverwriteAllFields() || info.getOpeningBid() != null) {
        original.setOpeningBid(info.getOpeningBid());
      }
      if (dto.isShouldOverwriteAllFields() || info.getSellerId() != null) {
        original.getSeller().setId(info.getSellerId());
      }
      if (dto.isShouldOverwriteAllFields() || info.getSellingBidId() != null) {
        original.getSellingBid().setId(info.getSellingBidId());
      }
      if (dto.isShouldOverwriteAllFields() || info.getSellingPrice() != null) {
        original.setSellingPrice(info.getSellingPrice());
      }
      if (dto.isShouldOverwriteAllFields() || info.getStatus() != null) {
        original.setStatus(info.getStatus());
      }
    }
    return original;
  }

  public CarBid mergeCarBidWithDto(CarBid original, CarBidRequestDto dto) {
    if (original == null) {
      original = new CarBid();
    }
    if (dto != null) {
      CarBidRequestDtoInfo info = dto.getInfo();
      if (dto.isShouldOverwriteAllFields() || info.getBidAmount() != null) {
        original.setBidAmount(info.getBidAmount());
      }
      if (dto.isShouldOverwriteAllFields() || info.getBidBy() != null) {
        original.getBidBy().setId(info.getBidBy());
      }
      if (dto.isShouldOverwriteAllFields() || info.getCarAuctionId() != null) {
        original.getCarAuction().setId(info.getCarAuctionId());
      }
      if (dto.isShouldOverwriteAllFields() || info.getTimeOfBid() != null) {
        original.setTimeOfBid(info.getTimeOfBid());
      }
    }
    return original;
  }
  
  public CarInfo mergeCarInfoWithDto(CarInfo original, CarInfoRequestDto dto) {
	    if (original == null) {
	      original = new CarInfo();
	    }
	    if (dto != null) {
	      CarInfoRequestDtoInfo info = dto.getInfo();
	      if (dto.isShouldOverwriteAllFields() || info.getManufacturer() != null) {
	        original.setManufacturer(info.getManufacturer());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getModel() != null) {
	        original.setModel(info.getModel());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getOwnerId() != null) {
	        original.getOwner().setId(info.getOwnerId());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getYear() != null) {
	        original.setYear(info.getYear());
	      }
	    }
	    return original;
	  }
  
  public UserInfo mergeUserInfoWithDto(UserInfo original, UserInfoRequestDto dto) {
	    if (original == null) {
	      original = new UserInfo();
	    }
	    if (dto != null) {
	    	UserInfoRequestDtoInfo info = dto.getInfo();
	      if (dto.isShouldOverwriteAllFields() || info.getAddress() != null) {
	        original.setPrimaryAddress(info.getAddress());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getAlternateEmailAddress() != null) {
	        original.setAlternateEmailAddress(info.getAlternateEmailAddress());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getAlternatePhoneNumber() != null) {
	        original.setAlternatePhoneNumber(info.getAlternatePhoneNumber());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getDriversLicenseImageInfo() != null) {
	        original.setDriversLicenseImageInfo(info.getDriversLicenseImageInfo());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getDriversLicenseNum() != null) {
		        original.setDriversLicenseNum(info.getDriversLicenseNum());
		      }
	      if (dto.isShouldOverwriteAllFields() || info.getEmailAddress() != null) {
	    	  original.setEmailAddress(info.getEmailAddress());
		      }
	      
	      
	      if (dto.isShouldOverwriteAllFields() || info.getFirstName() != null) {
		       original.setFirstName(info.getFirstName()); 
		      }
	      if (dto.isShouldOverwriteAllFields() || info.getLastName() != null) {
		        original.setLastName(info.getLastName());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getMiddleName() != null) {
		        original.setMiddleName(info.getMiddleName());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getPhoneNumber() != null) {
		      original.setPhoneNumber(info.getPhoneNumber());  
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getUserId() != null) {
		     original.getUser().setId(info.getUserId());   
	      }
	      
	      if (dto.isShouldOverwriteAllFields() || info.getZipcode() != null) {
		        original.setZipcode(info.getZipcode());
	      }
	      
	    }
	    return original;
	  }
  
  public User mergeCarInfoWithDto(User original, UserRequestDto dto) {
	    if (original == null) {
	      original = new User();
	    }
	    if (dto != null) {
	    	UserRequestDtoInfo info = dto.getInfo();
	      if (dto.isShouldOverwriteAllFields() || info.getMemberType() != null) {
	        original.setMemberType(info.getMemberType());
	      }
	      if (dto.isShouldOverwriteAllFields() || info.getRole() != null) {
	        original.setRole(info.getRole());
	      }
	    }
	    return original;
	  }
}
