package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBidRepository extends JpaRepository<CarBid, Integer> {

  public Page<CarBid> findByCarAuction(CarAuction carAuction, Pageable pageable);
}
