package com.yaji.traderev.carauction.services;

import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractResourceService<RESOURCE, DTO>
    implements IResourceService<RESOURCE, DTO> {

  @Autowired protected JpaRepository<RESOURCE, Integer> repository;

  @Override
  public RESOURCE getResource(Integer id) throws TradeRevResourceNotFoundException {
    RESOURCE resource =
        repository
            .findById(id)
            .orElseThrow(
                () -> new TradeRevResourceNotFoundException(getResourceNotFoundErrorCode(), id));
    return resource;
  }

  @Override
  public List<RESOURCE> getResources(Integer page, Integer size, String sortBy)
      throws TradeRevResourceNotFoundException {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<RESOURCE> resourcesPage = repository.findAll(pageable);
    if (resourcesPage == null || resourcesPage.isEmpty()) {
      throw new TradeRevResourceNotFoundException(
          getResourcesNotFoundErrorCode(), page, size, sortBy);
    }
    List<RESOURCE> resources = resourcesPage.toList();
    return resources;
  }

  public Sort getSortingOrder(String sortByProperty, ResourceSortingOrder order) {
    Sort sort = Sort.by(sortByProperty);
    if (ResourceSortingOrder.DESCENDING.equals(order)) {
      sort = sort.descending();
    }
    return sort;
  }

  public abstract ErrorCode getResourceNotFoundErrorCode();

  public abstract ErrorCode getResourcesNotFoundErrorCode();
}
