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
  @OneToOne(fetch = FetchType.EAGER)
  private CarInfo carInfo;

  @JoinColumn(name = "seller_id")
  @OneToOne(fetch = FetchType.EAGER)
  private User seller;

  @JoinColumn(name = "buyer_id")
  @OneToOne(fetch = FetchType.EAGER)
  private User buyer;

  @Column(name = "auction_date")
  private ZonedDateTime auctionDate;

  @Column(name = "selling_bid_id")
  private Integer sellingBidId;

  public boolean isAlive() {
    return CarAuctionStatus.AUCTION_IN_PROGRESS.equals(status);
  }

  public boolean isClosed() {
    return CarAuctionStatus.SOLD.equals(status) || CarAuctionStatus.UNSOLD.equals(status);
  }

  public boolean isNotYetOpen() {
    return !isAlive() && !isClosed();
  }

  public static CarAuction createNew() {
    CarAuction ret = new CarAuction();
    User seller = User.createNew();
    CarInfo carInfo = CarInfo.createNew();
    ret.setSeller(seller);
    ret.setBuyer(null);
    ret.setCarInfo(carInfo);
    ret.setSellingBidId(null);
    return ret;
  }
}
