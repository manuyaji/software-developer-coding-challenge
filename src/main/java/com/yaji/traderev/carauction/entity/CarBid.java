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
  @OneToOne(fetch = FetchType.LAZY)
  private CarAuction carAuction;

  @Column(name = "time_of_bid")
  private ZonedDateTime timeOfBid;

  @Column(name = "bid_amount")
  private Double bidAmount;

  @JoinColumn(name = "bid_by")
  @OneToOne(fetch = FetchType.LAZY)
  private User bidBy;
}
