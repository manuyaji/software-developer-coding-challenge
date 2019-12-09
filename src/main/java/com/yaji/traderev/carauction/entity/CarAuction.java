package com.yaji.traderev.carauction.entity;

import com.yaji.traderev.carauction.enums.CarAuctionStatus;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "car_auction")
public class CarAuction extends IntegerIdUpdatableTable {

  @Column(name = "status")
  private CarAuctionStatus status;

  @Column(name = "opening_bid")
  private Double openingBid;

  @Column(name = "selling_price")
  private Double sellingPrice;

  @JoinColumn(name = "car_info_id")
  @OneToOne(fetch = FetchType.LAZY)
  private CarInfo carInfo;

  @JoinColumn(name = "seller_id")
  @OneToOne(fetch = FetchType.LAZY)
  private User seller;

  @JoinColumn(name = "buyer_id")
  @OneToOne(fetch = FetchType.LAZY)
  private User buyer;

  @Column(name = "auction_date")
  private ZonedDateTime auctionDate;

  @JoinColumn(name = "selling_bid_id")
  @OneToOne(fetch = FetchType.LAZY)
  private CarBid sellingBid;
}
