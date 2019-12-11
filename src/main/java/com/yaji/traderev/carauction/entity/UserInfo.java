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
@Table(name = "user_info")
public class UserInfo extends IntegerIdUpdatableTable {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "email_addess")
  private String emailAddress;

  @Column(name = "alternate_email_addess")
  private String alternateEmailAddress;

  @Column(name = "phone_num")
  private String phoneNumber;

  @Column(name = "alternate_phone_num")
  private String alternatePhoneNumber;

  @Column(name = "primary_address")
  private String primaryAddress;

  @Column(name = "primary_zipcode")
  private String zipcode;

  @JoinColumn(name = "user_id")
  @OneToOne(fetch = FetchType.LAZY)
  private User user;

  @Column(name = "drivers_license_number")
  private String driversLicenseNum;

  @Column(name = "drivers_license_image_info")
  private String driversLicenseImageInfo;

  public static UserInfo createNew(User user) {
    UserInfo userInfo = new UserInfo();
    userInfo.setUser(user);
    return userInfo;
  }

  public static UserInfo createNew(Integer userId) {
    User user = new User();
    user.setId(userId);
    UserInfo userInfo = new UserInfo();
    userInfo.setUser(user);
    return userInfo;
  }
}
