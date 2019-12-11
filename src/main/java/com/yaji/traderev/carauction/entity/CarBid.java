package com.yaji.traderev.carauction.entity;

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
@Table(name = "car_bid")
public class CarBid extends IntegerIdUpdatableTable {

  @JoinColumn(name = "auction_id")
  @OneToOne(fetch = FetchType.EAGER)
  private CarAuction carAuction;

  @Column(name = "time_of_bid")
  private ZonedDateTime timeOfBid;

  @Column(name = "bid_amount")
  private Double bidAmount;

  @JoinColumn(name = "bid_by")
  @OneToOne(fetch = FetchType.EAGER)
  private User bidBy;

  public static CarBid createNew(CarAuction carAuction) {
    CarBid carBid = new CarBid();
    User bidBy = new User();
    carBid.setBidBy(bidBy);
    carBid.setCarAuction(carAuction);
    return carBid;
  }

  public static CarBid createNew(Integer carAuctionId) {
    CarAuction carAuction = new CarAuction();
    carAuction.setId(carAuctionId);
    CarBid carBid = new CarBid();
    User bidBy = new User();
    carBid.setBidBy(bidBy);
    carBid.setCarAuction(carAuction);
    return carBid;
  }
}
