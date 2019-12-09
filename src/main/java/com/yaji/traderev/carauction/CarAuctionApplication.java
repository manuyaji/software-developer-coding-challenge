package com.yaji.traderev.carauction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CarAuctionApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarAuctionApplication.class, args);
    log.info("Starting CarAuctionApplication..");
  }
}
