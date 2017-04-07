package com.gudeng.commerce.gd.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.impl.MemberToolServiceImple;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.exception.BusinessException;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 货源发布管理
 * 
 * @author yanghaoyu
 * 
 */
@Controller
@RequestMapping("carManager")
public class CarManagerApiController extends GDAPIBaseController {
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FocusCategoryController.class);
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public CarManagerApiService carManagerApiService;
	
	@Autowired
	public MemberToolServiceImple memberToolServiceImple;
	/**
	 * 各种用户增加车辆信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addCarMessage")
	public void addCarMessage(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			//车牌号码转码
			transcodeCarNumberIfNecessary(carsDTO);
			//校验数据
			if (!verifyBeforeAddOrUpdateCarMessage(carsDTO, result)) {
				renderJson(result, request, response);
				return;
			}
			//添加车辆信息
			if (!realAddCarMessage(carsDTO, result)) {
				renderJson(result, request, response);
				return;
			}
			//如果添加成功了,则给积分
			addActityAfterAddCarMessage(carsDTO.getUserId(), carsDTO.getCarNumber());
			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("添加车辆异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
	
	/**如有需要，对车牌号码进行转码
	 * @param carsDTO
	 * @throws UnsupportedEncodingException 
	 */
	private void transcodeCarNumberIfNecessary(CarsDTO carsDTO) throws UnsupportedEncodingException {
    	//转码处理
    	if(!StringUtils.isBlank(carsDTO.getApp())){
	    	if("IOS".equals(carsDTO.getApp())){
	    		String transedCarNum= new String(carsDTO.getCarNumber().getBytes("utf-8"),"utf-8");
	    		carsDTO.setCarNumber(transedCarNum);
	    	}
    	}
	}
	
	
	/** 在添加或更新车辆信息前的数据校验
	 * @param carsDTO
	 * @param result
	 * @return true表示校验通过，反之返回false。
	 */
	private boolean verifyBeforeAddOrUpdateCarMessage(CarsDTO carsDTO, ObjectResult result) {
		boolean verifyResult = true;
		if(carsDTO.getCarLength()!=null){
			verifyResult = verifyCarLenth(carsDTO.getCarLength(), result);
		}
		
		//如果前面校验不通过，则不再往后校验。
		if(!verifyResult) {
			return verifyResult;
		};
		
		//车牌号码的输入合法验证
	    if(!StringUtils.isBlank(carsDTO.getCarNumber())){
	    	verifyResult = verifyCarNumber(carsDTO.getCarNumber(), result);
	    }
	    
	    return verifyResult;
	}
	
	
	/** 校验车长
	 * @param carLength 车辆长度
	 * @param result
	 * @return true表示校验通过，反之false。
	 */
	private boolean verifyCarLenth(Double carLength, ObjectResult result) {
		if(carLength == 0){
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("车长不能为0" );
			return false;
		}
    	Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
    	Matcher m = p.matcher(carLength.toString());
    	if (!m.matches()) {
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("车长只能输入一位小数点的正数" );
			return false;
    	}
		return true;
	}
	
	/**校验车牌号码
	 * @param carNum 车牌号码
	 * @param result 
	 * @return true表示校验通过，反之false。
	 */
	private boolean verifyCarNumber(String carNum, ObjectResult result) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$");
    	Matcher matcher = pattern.matcher(carNum);
    	if (!matcher.matches()) {
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("车牌号码格式不正确" );
			return false;
    	}else{
    		String sheng=carNum.substring(0,1);
    	    String shengfen="京津冀晋辽吉黑沪苏浙皖闽赣鲁甘豫鄂湘川粤桂琼渝藏青宁新蒙贵云陕甘"; 
    	    
    	    if(!shengfen.contains(sheng)){
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("请填写正确的省份简称" );
				return false;
    	    }
    		
    	} 
    	
    	return true;
	}
	
	
	/** 添加车牌信息的实际处理方法
	 * @param carsDTO 
	 * @param result
	 * @return true表示添加成功，反之返回false。
	 * @throws Exception
	 */
	private boolean realAddCarMessage(CarsDTO carsDTO, ObjectResult result) throws Exception {
		String carNumber = carsDTO.getCarNumber();
		//同步锁，保证相同车牌的车辆信息同步性
		synchronized (carNumber.intern()) {
			//根据车牌号码查询
			List<CarsDTO> carsDTOList = carManagerApiService.queryByCarNumber(carNumber, false);
			//如果当前车牌号码已经存在，则判断车主是否当前用户
			if(carsDTOList.size() > 0){
				//当前用户是否已经登记过该车辆
				boolean curUserOwnCar = false;
				for (CarsDTO oldCar : carsDTOList) {
					if(carsDTO.getUserId().equals(oldCar.getUserId())) {
						curUserOwnCar = true;
						break;
					}
				}
				
				if(curUserOwnCar) {
					result.setMsg("当前用户已登记过该车牌号码,请勿重复登记" );
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					//更改返回的结果参数
					return  false;
				}
				
				//车辆最多被不同账号添加两次。
				if(carsDTOList.size() >= 2) {
					result.setMsg("车牌登记超2次,请联系客服" );
					result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
					//更改返回的结果参数
					return  false;
				}
				
				//同步车辆信息。carsDTOList车辆基本信息都是一样的，取集合的第一个元素即可。
				synchBasicCarMsg(carsDTOList.get(0), carsDTO);
				Map<String, Object> extraMap = new HashMap<String, Object>(1);
				//添加标识。1表示同步车辆信息
				extraMap.put("isSyncCarMsg", 1);
				result.setExtra(extraMap);
				//为了兼容前端APP，在Object中也添加标识
				result.setObject(extraMap);
				
			}
			
			//添加记录到数据库
            carManagerApiService.addCarMessage(carsDTO);
		}
		
		return true;
	}
	
	
	/** 同步车辆的基本信息
	 * @param srcCar 被参考的车辆对象
	 * @param desCar 需同步的车辆对象
	 */
	private void synchBasicCarMsg(CarsDTO srcCar, CarsDTO desCar) {
		desCar.setCarType(srcCar.getCarType());
		desCar.setMaxLoad(srcCar.getMaxLoad());
		desCar.setCarLength(srcCar.getCarLength());
		desCar.setTransportType(srcCar.getTransportType());
		desCar.setTransportCarType(srcCar.getTransportCarType());
	}
	
	
	/** 在添加车辆信息后，送积分
	 * @param userId 用户id
	 * @param carNumber 车牌号
	 * @throws Exception 
	 */
	private void addActityAfterAddCarMessage(Long userId, String carNumber) throws Exception {
		Map<String, Object> map =new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("carNumber",carNumber);
		carManagerApiService.addActity(map);
	}
	
	
	/**
	 * 修改车辆信息时候,获取当前修改车辆的信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarMessageById")
	public void getCarMessageById(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			CarsDTO cd = carManagerApiService.getCarMessageById(Long.toString(carsDTO.getId()));
			if(cd.getUserType()!=null){

				if(cd.getUserType()==2){
					MemberBaseinfoDTO memberBaseinfoDTO=memberToolServiceImple.getById(cd.getUserId()+"");
					  
					  if(memberBaseinfoDTO.getCompanyContact()!=null){
						 // cd.setContact(memberBaseinfoDTO.getCompanyContact().substring(0, 1)+"先生");
						  cd.setContact(memberBaseinfoDTO.getCompanyContact());
					  }
			
				}
			}
			result.setObject(cd);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (BusinessException e) {
			logger.info("获取车辆详情异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		} catch (Exception e) {
			logger.info("获取车辆详情异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 修改车辆信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateCars")
	public void updateCars(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			//车牌号码转码
			transcodeCarNumberIfNecessary(carsDTO);
			//校验数据
			if (!verifyBeforeAddOrUpdateCarMessage(carsDTO, result)) {
				renderJson(result, request, response);
				return;
			}
			//更新车辆信息
			realUpdateCarMessage(carsDTO, result);
			
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("更新车辆信息异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
	
	
	/** 更新车牌信息的实际处理方法
	 * @param carsDTO 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private void realUpdateCarMessage(CarsDTO carsDTO, ObjectResult result) throws Exception {
		//根据车牌号码查询，
		List<CarsDTO> carsDTOList = carManagerApiService.
				queryByCarNumber(carsDTO.getCarNumber(), false);
		
		//判断此车牌号码的首次创建人是不是当前用户，如果不是，则需要同步首次创建人的车牌信息
		if(!carsDTO.getUserId().equals(carsDTOList.get(0).getUserId())) {
			//同步车辆信息。
			synchBasicCarMsg(carsDTOList.get(0), carsDTO);
			Map<String, Object> extraMap = new HashMap<String, Object>(1);
			//添加标识。表示同步车辆信息
			extraMap.put("isSyncCarMsg", 1);
			result.setExtra(extraMap);
			//为了兼容前端APP接口，在Object中也添加标识
			result.setObject(extraMap);
		}	
		
		//更新车辆信息
		carManagerApiService.updateCars(carsDTO);
	}
	
	
	/**
	 * 删除车辆信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delCars")
	public void delCars(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			
			
			int j=carManagerApiService.delTotalCars(carsDTO.getId());
			
			if(j>0){
				result.setObject(null);
				result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
				result.setMsg("你的车辆已经发布过线路,请删除对应的线路后,再删除车辆.");
				renderJson(result, request, response);
				return;
			}
			int i = carManagerApiService.delCars(carsDTO.getId());
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("删除车辆异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 修改车辆信息时候,获取当前修改车辆的信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listCarByUserId")
	public void listCarByUserId(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			
			
			String userId=carsDTO.getUserId().toString();
			MemberBaseinfoDTO memberBaseinfoDTO=memberToolServiceImple.getById(userId);
			
			List<CarsDTO> list= carManagerApiService.listCarByUserId(carsDTO.getUserId());
			 if(memberBaseinfoDTO.getUserType()!=null &&memberBaseinfoDTO.getUserType()==2){
			 for (CarsDTO singleDto : list) {
				
				  if(memberBaseinfoDTO.getCompanyContact()!=null){
					 // singleDto.setContact(memberBaseinfoDTO.getCompanyContact().substring(0, 1)+"先生");
					  singleDto.setContact(memberBaseinfoDTO.getCompanyContact());
				  }
				 
			 }
			 }
			//String contacts=this.getContacts(Long.toString(carsDTO.getUserId()));
			//List<CarManagerAppDTO> cadlist= new ArrayList<CarManagerAppDTO>();
			//Member
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("获取车辆列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
	
	
	
	/**
	 * 农速宝APP,个人中心,当前用户在个人中心需要维护姓名,性别
	 * @param request
	 * @param response
	 * @param memberDtoInput
	 */
	@RequestMapping("/getContacts")
	public String getContacts(String id) {
		//ObjectResult result = new ObjectResult();
		//Long id = memberDtoInput.getMemberId();
		//if(id == null){
		//	setResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "用户名id不能为空", request, response);
		//	return;
		//}
		
		
		try {
			MemberBaseinfoDTO memberDTO = memberToolService.getById(id + "");
			//判断用户是否存在
			if (null == memberDTO){
				//setResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "用户不存在", request, response);
				return null;
			}
			
			
			//更改真名
			String realName = memberDTO.getRealName();
			//StringBuffer contactName=new StringBuffer();
			
			if(!"".equals(realName)&&realName!=null){
				//contactName.append(realName.substring(0, 1));
			
			//更改类型
//			Integer  sex = memberDTO.getSex();
//			if(sex==null||"".equals(sex)){
//				contactName.append("先生");
//			}else{
//				if(sex!=1){
//					contactName.append("先生");
//				}else{
//					contactName.append("女士");
//				}
//
//			}
			CarsDTO cad= new CarsDTO();
			cad.setContact(realName);
			return cad.getContact();
		}else{
			return null;
		}
				
		} catch (Exception e) {
			logger.warn("[ERROR]更新用户信息异常", e);
			return null;
		}
		//renderJson(result, request, response);
	}
	
	
	/**
	 * 获取用户车辆的车牌号list
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listCarNumber")
	public void getCarNumber(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			List<CarsDTO> list = carManagerApiService.getCarNumber(carsDTO);

			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("获取线路列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
    
	
	/**
	 * 获取用户车辆的车牌号list
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/carTotal")
	public void getCarTotal(HttpServletRequest request,
			HttpServletResponse response,CarsDTO carsDTO) {
		ObjectResult result = new ObjectResult();
		try {
			CarsDTO cd = carManagerApiService.getCarTotal(carsDTO);
            //carsDTO.setCarTotal(i);
			result.setObject(cd);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("获取线路列表异常", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("服务异常，请稍后再试！");
		}
		renderJson(result, request, response);
	}
}
