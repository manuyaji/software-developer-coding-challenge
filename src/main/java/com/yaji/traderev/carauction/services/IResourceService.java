package com.yaji.traderev.carauction.services;

import com.yaji.traderev.carauction.exception.TradeRevIllegalStateException;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import java.util.List;

public interface IResourceService<RESOURCE, DTO> {

  public RESOURCE getResource(Integer id) throws TradeRevResourceNotFoundException;

  public List<RESOURCE> getResources(Integer page, Integer size, String sortBy)
      throws TradeRevResourceNotFoundException;

  public RESOURCE createResource(DTO dto) throws TradeRevIllegalStateException;

  public RESOURCE modifyResource(Integer id, DTO dto) throws TradeRevIllegalStateException;
}
