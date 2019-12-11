package com.yaji.traderev.carauction.controller;

import com.yaji.traderev.carauction.controller.constants.ControllerConstants;
import com.yaji.traderev.carauction.entity.CarAuction;
import com.yaji.traderev.carauction.entity.CarBid;
import com.yaji.traderev.carauction.enums.ResourceSortingOrder;
import com.yaji.traderev.carauction.models.requestdto.CarAuctionRequestDto;
import com.yaji.traderev.carauction.models.requestdto.CarBidRequestDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.services.auctions.ICarAuctionService;
import com.yaji.traderev.carauction.services.bids.ICarBidService;
import com.yaji.traderev.carauction.util.DtoToEntityMergingUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CarAuctionsBidsController.AUCTIONS_BASE_PATH)
@Slf4j
public class CarAuctionsBidsController extends BaseController {

  public static final String AUCTIONS_BASE_PATH = "/auctions";
  public static final String BIDS_BASE_PATH_FORMAT = AUCTIONS_BASE_PATH + "/%d/bids";

  @Autowired private DtoToEntityMergingUtil dtoToEntityMergingUtil;
  @Autowired private ICarBidService carBidService;
  @Autowired private ICarAuctionService carAuctionService;

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> getCarAuction(@PathVariable("id") Integer id) {
    log.info("Getting CarAuction ID[{}]", id);
    CarAuction auction = carAuctionService.getResource(id);
    log.info("Returning CarAuction {}", auction);
    ResponseDto ret = buildResponseDto(auction, AUCTIONS_BASE_PATH + "/" + id);
    return new ResponseEntity<ResponseDto<CarAuction>>(ret, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<ResponseDto<List<CarAuction>>> getCarAuctions(
      @RequestParam(
              required = false,
              name = "page",
              defaultValue = ControllerConstants.DEFAULT_PAGE_STR)
          Integer page,
      @RequestParam(
              required = false,
              name = "size",
              defaultValue = ControllerConstants.DEFAULT_SIZE_STR)
          Integer size,
      @RequestParam(
              required = false,
              name = "sortBy",
              defaultValue = ControllerConstants.DEFAULT_SORTBY)
          String sortBy) {
    log.info("Getting CarAuctions Page[{}] ; Size[{}] ; SortBy[{}]", page, size, sortBy);
    List<CarAuction> auctions = carAuctionService.getResources(page, size, sortBy);
    log.info("Returning CarAuctions {}", auctions);
    ResponseDto ret = buildResponseDto(auctions, AUCTIONS_BASE_PATH, page, size, sortBy);
    return new ResponseEntity(ret, HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> createCarAuction(
      @RequestBody CarAuctionRequestDto carAuctionReqDto) {
    CarAuction newAuction = carAuctionService.createResource(carAuctionReqDto);
    ResponseDto ret = buildResponseDto(newAuction, AUCTIONS_BASE_PATH + "/" + newAuction.getId());
    return new ResponseEntity(ret, HttpStatus.CREATED);
  }

  @RequestMapping(
      path = "/{id}",
      method = RequestMethod.PUT,
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> modifyCarAuction(
      @PathVariable("id") Integer id, @RequestBody CarAuctionRequestDto carAuctionReqDto) {

    CarAuction auction = carAuctionService.modifyResource(id, carAuctionReqDto);

    ResponseDto ret = buildResponseDto(auction, AUCTIONS_BASE_PATH + "/" + auction.getId());
    return new ResponseEntity(ret, HttpStatus.OK);
  }

  @RequestMapping(
      path = "/{auctionId}/closeAuction",
      method = RequestMethod.POST,
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarAuction>> closeAuction(
      @PathVariable("auctionId") Integer auctionId) {
    CarAuction auction = carAuctionService.closeAuction(auctionId);
    log.info("Returning Closing Auction {}", auction);
    ResponseDto ret =
        buildResponseDto(auction, AUCTIONS_BASE_PATH + "/" + auction.getId() + "/closeAuction");
    return new ResponseEntity<ResponseDto<CarAuction>>(ret, HttpStatus.OK);
  }

  /*
   * CAR AUCTION BIDS APIs
   */
  
  @RequestMapping(
	      path = "/{auctionId}/bids/history",
	      method = RequestMethod.GET,
	      produces = "application/json")
	  public ResponseEntity<ResponseDto<CarBid>> getHistoryOfAnAuction(
	      @PathVariable("auctionId") Integer auctionId, @RequestParam(
	              required = false,
	              name = "page",
	              defaultValue = ControllerConstants.DEFAULT_PAGE_STR)
	          Integer page,
	      @RequestParam(
	              required = false,
	              name = "size",
	              defaultValue = ControllerConstants.DEFAULT_SIZE_STR)
	          Integer size) {
	    List<CarBid> bids = carAuctionService.getHistory(auctionId, page, size, ResourceSortingOrder.ASCENDING);
	    log.info("Returning Winning Bid {}", bids);
	    ResponseDto ret =
	        buildResponseDto(bids, String.format(BIDS_BASE_PATH_FORMAT, auctionId) + "/winningBid");
	    return new ResponseEntity<ResponseDto<CarBid>>(ret, HttpStatus.OK);
	  }

  @RequestMapping(
      path = "/{auctionId}/bids/winningBid",
      method = RequestMethod.GET,
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarBid>> getWinningBid(
      @PathVariable("auctionId") Integer auctionId) {
    CarBid bid = carAuctionService.getWinningBid(auctionId);
    log.info("Returning Winning Bid {}", bid);
    ResponseDto ret =
        buildResponseDto(bid, String.format(BIDS_BASE_PATH_FORMAT, auctionId) + "/winningBid");
    return new ResponseEntity<ResponseDto<CarBid>>(ret, HttpStatus.OK);
  }

  @RequestMapping(
      path = "/{auctionId}/bids/{id}",
      method = RequestMethod.GET,
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarBid>> getCarBid(
      @PathVariable("auctionId") Integer auctionId, @PathVariable("id") Integer id) {
    log.info("Getting CarBidTable ID[{}]", id);
    CarBid bid = carBidService.getCarBid(auctionId, id);
    log.info("Returning CarBidTable {}", bid);
    ResponseDto ret =
        buildResponseDto(bid, String.format(BIDS_BASE_PATH_FORMAT, auctionId) + "/" + id);
    return new ResponseEntity<ResponseDto<CarBid>>(ret, HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      produces = "application/json",
      path = "/{auctionId}/bids")
  public ResponseEntity<ResponseDto<List<CarBid>>> getCarBids(
      @PathVariable("auctionId") Integer auctionId,
      @RequestParam(
              required = false,
              name = "page",
              defaultValue = ControllerConstants.DEFAULT_PAGE_STR)
          Integer page,
      @RequestParam(
              required = false,
              name = "size",
              defaultValue = ControllerConstants.DEFAULT_SIZE_STR)
          Integer size,
      @RequestParam(
              required = false,
              name = "sortBy",
              defaultValue = ControllerConstants.DEFAULT_SORTBY)
          String sortBy) {

    log.info(
        "Getting CarBids AuctionId [{}] Page[{}] ; Size[{}] ; SortBy[{}]",
        auctionId,
        page,
        size,
        sortBy);
    List<CarBid> bids =
        carBidService.getCarBids(auctionId, page, size, sortBy, ResourceSortingOrder.DESCENDING);
    log.info("For Auction [{}], returning CarBids {}", auctionId, bids);
    ResponseDto ret =
        buildResponseDto(bids, String.format(BIDS_BASE_PATH_FORMAT, auctionId), page, size, sortBy);
    return new ResponseEntity(ret, HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = "application/json",
      produces = "application/json",
      path = "/{auctionId}/bids")
  public ResponseEntity<ResponseDto<CarBid>> createCarBid(
      @PathVariable("auctionId") Integer auctionId, @RequestBody CarBidRequestDto carBidReqDto) {
    CarBid newBid = carBidService.createCarBid(auctionId, carBidReqDto);
    ResponseDto ret =
        buildResponseDto(
            newBid, String.format(BIDS_BASE_PATH_FORMAT, auctionId) + "/" + newBid.getId());
    return new ResponseEntity(ret, HttpStatus.CREATED);
  }

  @RequestMapping(
      path = "/{auctionId}/bids/{id}",
      method = RequestMethod.PUT,
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ResponseDto<CarBid>> modifyCarBid(
      @PathVariable("auctionId") Integer auctionId,
      @PathVariable("id") Integer id,
      @RequestBody CarBidRequestDto carBidReqDto) {
    CarBid bid = carBidService.modifyCarBid(auctionId, id, carBidReqDto);
    ResponseDto ret =
        buildResponseDto(bid, String.format(BIDS_BASE_PATH_FORMAT, auctionId) + "/" + id);
    return new ResponseEntity(ret, HttpStatus.OK);
  }
}
