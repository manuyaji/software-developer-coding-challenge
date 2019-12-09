package com.yaji.traderev.carauction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yaji.traderev.carauction.exception.translation.ErrorPropertiesReader;
import com.yaji.traderev.carauction.logs.IdGenerator;
import com.yaji.traderev.carauction.logs.UUIDGenerator;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import com.yaji.traderev.carauction.util.LocaleUtil;

@Configuration
public class BeanCreation {

  @Bean
  public IdGenerator idGenerator() {
    return new UUIDGenerator();
  }

  @Bean
  public DtoToEntityMergingUtil dtoToEntityMergingUtil() {
    return new DtoToEntityMergingUtil();
  }
  
  @Bean
  public ErrorPropertiesReader errorPropertiesReader(){
	  return new ErrorPropertiesReader();
  }
  
  @Bean
  public LocaleUtil localeUtil(){
	  return new LocaleUtil();
  }
  
}
