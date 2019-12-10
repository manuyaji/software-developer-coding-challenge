package com.yaji.traderev.carauction.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yaji.traderev.carauction.exception.translation.ErrorPropertiesReader;
import com.yaji.traderev.carauction.logs.IdGenerator;
import com.yaji.traderev.carauction.logs.UUIDGenerator;
import com.yaji.traderev.carauction.metrics.collector.IRequestResponseDataCollector;
import com.yaji.traderev.carauction.metrics.collector.KafkaDataCollector;
import com.yaji.traderev.carauction.services.auctions.ICarAuctionService;
import com.yaji.traderev.carauction.services.auctions.SqlBackedCarAuctionService;
import com.yaji.traderev.carauction.services.bids.ICarBidService;
import com.yaji.traderev.carauction.services.bids.SqlBackedCarBidService;
import com.yaji.traderev.carauction.services.carinfo.ICarInfoService;
import com.yaji.traderev.carauction.services.carinfo.SqlBackedCarInfoService;
import com.yaji.traderev.carauction.threadpools.MDCRetainingExecutorService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import com.yaji.traderev.carauction.util.LocaleUtil;

@Configuration
public class BeanCreation {

  @Autowired private MetricsConfig metricsConfig;

  @Bean
  public IdGenerator idGenerator() {
    return new UUIDGenerator();
  }

  @Bean
  public DtoToEntityMergingUtil dtoToEntityMergingUtil() {
    return new DtoToEntityMergingUtil();
  }

  @Bean
  public ErrorPropertiesReader errorPropertiesReader() {
    return new ErrorPropertiesReader();
  }

  @Bean
  public LocaleUtil localeUtil() {
    return new LocaleUtil();
  }

  @Bean(name = "metricsCollectorExecutorService")
  public ExecutorService metricsCollectorExecutorService() {
    ExecutorService es = Executors.newFixedThreadPool(metricsConfig.getCollectorThreadpoolSize());
    return new MDCRetainingExecutorService(es);
  }
  
  @Bean
  public ICarBidService carBidService(){
	  return new SqlBackedCarBidService();
  }
  
  @Bean
  public ICarAuctionService carAuctionService(){
	  return new SqlBackedCarAuctionService();
  }
  
  @Bean
  public ICarInfoService carInfoService(){
	  return new SqlBackedCarInfoService();
  }
  
  @Bean IRequestResponseDataCollector metricsDataCollector(){
	  return new KafkaDataCollector();
  }
}