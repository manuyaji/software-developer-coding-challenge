package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Integer> {}
