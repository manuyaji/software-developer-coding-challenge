package com.yaji.traderev.carauction.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class UpdatableTable {

  @Column(name = "created_ts")
  private ZonedDateTime createdTime;

  @Column(name = "updated_ts")
  private ZonedDateTime updatedTime;
}
