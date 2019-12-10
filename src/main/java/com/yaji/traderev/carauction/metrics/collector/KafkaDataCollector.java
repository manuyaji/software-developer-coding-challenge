package com.yaji.traderev.carauction.metrics.collector;

import org.springframework.stereotype.Component;

import com.yaji.traderev.carauction.metrics.model.RequestResponseData;

@Component
public class KafkaDataCollector implements IRequestResponseDataCollector{

	@Override
	public void collect(RequestResponseData requestResponseData) {
		// TODO 
		// push the data to Kafka Queue for any downstream process to consume and analyze that data if necessary  
	}

}
