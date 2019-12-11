package com.yaji.traderev.carauction.entity;

import com.yaji.traderev.carauction.enums.MemberType;
import com.yaji.traderev.carauction.enums.Role;
import com.yaji.traderev.carauction.enums.UserAccountStatus;
import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends IntegerIdUpdatableTable {

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private Role role;

  @Column(name = "failed_attempts")
  private int failedAttempts;

  @Column(name = "last_login_attempt_ts")
  private ZonedDateTime lastLoginAttempt;

  @Column(name = "last_successful_login")
  private ZonedDateTime lastSuccessfulLogin;

  @Column(name = "member_type")
  private MemberType memberType;

  @Column(name = "account_status")
  private UserAccountStatus accountStatus;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  private UserInfo userInfo;

  public static User createNew() {
    User user = new User();
    return user;
  }
}
