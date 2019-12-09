package com.yaji.traderev.carauction.controller;

import com.yaji.traderev.carauction.constants.HeaderConstants;
import com.yaji.traderev.carauction.controller.constants.ControllerConstants;
import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.enums.ErrorCode;
import com.yaji.traderev.carauction.exception.TradeRevResourceNotFoundException;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseMetadata;
import com.yaji.traderev.carauction.repository.db.CarAuctionRepository;
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

@RestController
@RequestMapping(path = CarAuctionController.BASE_PATH)
@Slf4j
public class CarAuctionController {

  private static final String SINGLE_RESOURCE = "Auction";
  private static final String RESOURCE_COLLECTION = "Auctions";
  public static final String BASE_PATH = "/auctions";

  @Autowired private CarAuctionRepository repository;
  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> getCarAuction(@PathVariable("id") Integer id) {
    log.info("Getting CarAuction ID[{}]", id);
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);

    CarAuction auction =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, SINGLE_RESOURCE, id));
    log.info("Returning CarAuction {}", auction);
    ResponseDto ret =
        ResponseDto.builder()
            .body(auction)
            .metadata(
                ResponseMetadata.builder()
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .self(BASE_PATH + "/" + id)
            .build();
    return new ResponseEntity<ResponseDto<CarAuction>>(ret, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<List<CarAuction>>> getCarAuctions(
      @RequestParam(required = false, name = "page") Integer page,
      @RequestParam(required = false, name = "size") Integer size,
      @RequestParam(required = false, name = "sortBy") String sortBy) {
    page = (page == null) ? ControllerConstants.DEFAULT_PAGE : page;
    size = (size == null) ? ControllerConstants.DEFAULT_SIZE : size;
    sortBy = (sortBy == null) ? ControllerConstants.DEFAULT_SORTBY : sortBy;
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    log.info("Getting CarAuctions Page[{}] ; Size[{}] ; SortBy[{}]", page, size, sortBy);

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<CarAuction> auctionsPage = repository.findAll(pageable);
    if (auctionsPage == null || auctionsPage.isEmpty()) {
      throw new TradeRevResourceNotFoundException(
          ErrorCode.RESOURCE_NOT_FOUND, RESOURCE_COLLECTION, page, size, sortBy);
    }
    List<CarAuction> auctions = auctionsPage.toList();
    log.info("Returning CarAuctions {}", auctions);
    ResponseDto ret =
        ResponseDto.builder()
            .body(auctions)
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
  public ResponseEntity<ResponseDto<CarAuction>> createCarAuction(
      @RequestBody CarAuctionRequestDto carAuctionReqDto) {
    CarAuction auction = dtoToEntityMergingUtil.mergeCarAuctionWithDto(null, carAuctionReqDto);
    CarAuction newAuction = repository.save(auction);
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);
    ResponseDto ret =
        ResponseDto.builder()
            .body(newAuction)
            .self(BASE_PATH + "/" + newAuction.getId())
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
      @PathVariable("id") Integer id, @RequestBody CarAuctionRequestDto carAuctionReqDto) {
    String correlationId = MDC.get(HeaderConstants.CORRELATION_ID_HEADERNAME);
    String requestId = MDC.get(HeaderConstants.REQUEST_ID_HEADERNAME);

    CarAuction original =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new TradeRevResourceNotFoundException(
                        ErrorCode.RESOURCE_NOT_FOUND, SINGLE_RESOURCE, id));
    CarAuction merged = dtoToEntityMergingUtil.mergeCarAuctionWithDto(original, carAuctionReqDto);
    CarAuction auction = repository.save(merged);

    ResponseDto ret =
        ResponseDto.builder()
            .body(auction)
            .self(BASE_PATH + "/" + auction.getId())
            .metadata(
                ResponseMetadata.builder()
                    .correlationId(correlationId)
                    .requestId(requestId)
                    .build())
            .build();
    return new ResponseEntity(ret, HttpStatus.CREATED);
  }
}
