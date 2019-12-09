package com.yaji.traderev.carauction.repository.db;

import com.yaji.traderev.carauction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {}
