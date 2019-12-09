package com.yaji.traderev.carauction.controller;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.controller.constants.ControllerConstants;
import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import com.yaji.traderev.carauction.repository.db.CarBidRepository;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = CarBidController.BASE_PATH)
public class CarBidController {

  private static final String SINGLE_RESOURCE = "Bid";
  private static final String RESOURCE_COLLECTION = "Bids";
  public static final String BASE_PATH = "/bids";

  @Autowired private CarBidRepository repository;
  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<CarBid>> getCarAuction(@PathVariable("id") Integer id) {
    log.info("Getting CarBid ID[{}]", id);
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);

    CarBid bid =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, SINGLE_RESOURCE, id));
    log.info("Returning CarBid {}", bid);
    ResponseDto ret =
        ResponseDto.builder()
            .body(bid)
            .metadata(
                ResponseMetadata.builder()
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .self(BASE_PATH + "/" + id)
            .build();
    return new ResponseEntity<ResponseDto<CarBid>>(ret, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<List<CarBid>>> getCarAuctions(
      @RequestParam(required = false, name = "page") Integer page,
      @RequestParam(required = false, name = "size") Integer size,
      @RequestParam(required = false, name = "sortBy") String sortBy) {
    page = (page == null) ? ControllerConstants.DEFAULT_PAGE : page;
    size = (size == null) ? ControllerConstants.DEFAULT_SIZE : size;
    sortBy = (sortBy == null) ? ControllerConstants.DEFAULT_SORTBY : sortBy;
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    log.info("Getting CarBids Page[{}] ; Size[{}] ; SortBy[{}]", page, size, sortBy);

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<CarBid> bidsPage = repository.findAll(pageable);
    if (bidsPage == null || bidsPage.isEmpty()) {
      throw new TradeRevResourceNotFoundException(
          ErrorCode.RESOURCE_NOT_FOUND, RESOURCE_COLLECTION, page, size, sortBy);
    }
    List<CarBid> bids = bidsPage.toList();
    log.info("Returning CarAuctions {}", bids);
    ResponseDto ret =
        ResponseDto.builder()
            .body(bids)
            .self(BASE_PATH)
            .metadata(
                ResponseMetadata.builder()
                    .page(page)
                    .size(size)
                    .sortBy(sortBy)
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .build();
    return new ResponseEntity(ret, HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarBid>> createCarAuction(
      @RequestBody CarBidRequestDto carAuctionReqDto) {
    CarBid bid = dtoToEntityMergingUtil.mergeCarBidWithDto(null, carAuctionReqDto);
    CarBid newBid = repository.save(bid);
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    ResponseDto ret =
        ResponseDto.builder()
            .body(newBid)
            .self(BASE_PATH + "/" + newBid.getId())
            .metadata(
                ResponseMetadata.builder()
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .build();
    return new ResponseEntity(ret, HttpStatus.CREATED);
  }

  @RequestMapping(
      path = "/{id}",
      method = RequestMethod.PUT,
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> modifyCarAuction(
      @PathVariable("id") Integer id, @RequestBody CarBidRequestDto carAuctionReqDto) {
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);

    CarBid original =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, SINGLE_RESOURCE, id));
    CarBid merged = dtoToEntityMergingUtil.mergeCarBidWithDto(original, carAuctionReqDto);
    CarBid bid = repository.save(merged);

    ResponseDto ret =
        ResponseDto.builder()
            .body(bid)
            .self(BASE_PATH + "/" + bid.getId())
            .metadata(
                ResponseMetadata.builder()
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .build();
    return new ResponseEntity(ret, HttpStatus.CREATED);
  }
}
