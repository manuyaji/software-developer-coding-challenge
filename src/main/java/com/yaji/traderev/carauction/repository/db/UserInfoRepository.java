package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {}
