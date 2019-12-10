package com.yaji.traderev.carauction.metrics.collector;

import com.yaji.traderev.carauction.metrics.model.RequestResponseData;

public interface IRequestResponseDataCollector {

  public void collect(RequestResponseData requestResponseData);
}
