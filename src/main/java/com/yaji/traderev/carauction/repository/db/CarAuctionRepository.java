package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.CarAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAuctionRepository extends JpaRepository<CarAuction, Integer> {}
