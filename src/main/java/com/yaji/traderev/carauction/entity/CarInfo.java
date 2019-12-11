package com.yaji.traderev.carauction.entity;

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
@Table(name = "car_info")
public class CarInfo extends IntegerIdUpdatableTable {

  @JoinColumn(name = "owner")
  @OneToOne(fetch = FetchType.EAGER)
  private User owner;

  @Column(name = "manufacturer")
  private String manufacturer;

  @Column(name = "model")
  private String model;

  @Column(name = "year")
  private String year;

  public static CarInfo createNew() {
    CarInfo carInfo = new CarInfo();
    User owner = User.createNew();
    carInfo.setOwner(owner);
    return carInfo;
  }
}
