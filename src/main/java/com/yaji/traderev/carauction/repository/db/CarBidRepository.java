package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.CarBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBidRepository extends JpaRepository<CarBid, Integer> {}
