package com.yaji.traderev.carauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaji.traderev.carauction.controller.constants.ControllerConstants;
import com.yaji.traderev.carauction.entity.CarInfo;
import com.yaji.traderev.carauction.models.requestdto.CarInfoRequestDto;
import com.yaji.traderev.carauction.models.responsedto.ResponseDto;
import com.yaji.traderev.carauction.services.carinfo.ICarInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = CarInfoController.BASE_PATH)
public class CarInfoController extends BaseController{
	
	@Autowired
	private ICarInfoService carInfoService;
	
	  public static final String BASE_PATH = "/carinfos";
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
	  public ResponseEntity<ResponseDto<CarInfo>> getCarInfo(@PathVariable("id") Integer id) {
	    log.info("Getting CarInfo ID[{}]", id);
	    CarInfo carInfo = carInfoService.getCarInfo(id);
	    log.info("Returning CarInfo {}", carInfo);
	    ResponseDto ret = buildResponseDto(carInfo, BASE_PATH + "/" + id);
	    return new ResponseEntity<ResponseDto<CarInfo>>(ret, HttpStatus.OK);
	  }

	  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
	  public ResponseEntity<ResponseDto<List<CarInfo>>> getCarInfos(
	      @RequestParam(required = false, name = "page", defaultValue=ControllerConstants.DEFAULT_PAGE_STR) Integer page,
	      @RequestParam(required = false, name = "size", defaultValue=ControllerConstants.DEFAULT_SIZE_STR) Integer size,
	      @RequestParam(required = false, name = "sortBy", defaultValue=ControllerConstants.DEFAULT_SORTBY) String sortBy) {
	    log.info("Getting CarInfos Page[{}] ; Size[{}] ; SortBy[{}]", page, size, sortBy);

	    List<CarInfo> carInfos = carInfoService.getCarInfos(page, size, sortBy);
	    log.info("Returning CarInfos {}", carInfos);
	    ResponseDto ret = buildResponseDto(carInfos, BASE_PATH, page, size, sortBy);
	    return new ResponseEntity(ret, HttpStatus.OK);
	  }

	  @RequestMapping(
	      method = RequestMethod.POST,
	      consumes = "application/json",
	      produces = "application/json")
	  public ResponseEntity<ResponseDto<CarInfo>> createCarInfo(
	      @RequestBody CarInfoRequestDto carInfoReqDto) {
		  CarInfo newCarInfo = carInfoService.createCarInfo(carInfoReqDto);
	    ResponseDto ret = buildResponseDto(newCarInfo, BASE_PATH + "/" + newCarInfo.getId());
	    return new ResponseEntity(ret, HttpStatus.CREATED);
	  }

	  @RequestMapping(
	      path = "/{id}",
	      method = RequestMethod.PUT,
	      consumes = "application/json",
	      produces = "application/json")
	  public ResponseEntity<ResponseDto<CarInfo>> modifyCarInfo(
	      @PathVariable("id") Integer id, @RequestBody CarInfoRequestDto carInfoReqDto) {
	    CarInfo carInfo = carInfoService.modifyCarInfo(id, carInfoReqDto);
	    ResponseDto ret = buildResponseDto(carInfo, BASE_PATH + "/" + carInfo.getId());
	    return new ResponseEntity(ret, HttpStatus.CREATED);
	  }
	
}
