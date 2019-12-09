package com.yaji.traderev.carauction.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class IntegerIdUpdatableTable extends UpdatableTable {

  @Id
  @Column(name = "id")
  @Access(AccessType.PROPERTY)
  private Integer id;
}
