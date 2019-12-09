package com.yaji.traderev.carauction.util;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto.CarAuctionRequestDtoInfo;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto.CarBidRequestDtoInfo;

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
        ;
      }
    }
    return original;
  }
}
