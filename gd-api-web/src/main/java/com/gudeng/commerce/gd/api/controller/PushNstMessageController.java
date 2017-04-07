package com.gudeng.commerce.gd.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.PushNstMessageApiService;
import com.gudeng.commerce.gd.api.service.impl.CarLineApiServiceImpl;
import com.gudeng.commerce.gd.api.service.impl.MemberToolServiceImple;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;

@Controller
@RequestMapping("pushNstMessage")
public class PushNstMessageController extends GDAPIBaseController {
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
	@Autowired
	public PushNstMessageApiService pushNstMessageService;
	@Autowired
	public MemberAddressApiService memberAddressApiService;
	@Autowired
	public CarLineApiService carLineApiService;
	@Autowired
	public CarLineApiServiceImpl carLineApiServiceImpl;
	
	@Autowired
	private AreaToolService areaToolService;
	/**
	 * 更新推送信息已读状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateRead")
	public void updateRead(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			int i=pushNstMessageService.setReadStatus(pushNstMessageDTO);
            //carsDTO.setCarTotal(i);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("修改推送信息已读状态变失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("修改推送信息已读状态变失败: " + e.getMessage());
		}
		renderJson(result, request, response);
	}
    
	
	/**
	 * 获取当前用户未读消息数量
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getNewCount")
	public void getNewCount(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			int i=pushNstMessageService.getNewCount(pushNstMessageDTO);
            //carsDTO.setCarTotal(i);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("newCount", i);
			result.setObject(map);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("修改推送信息已读状态变失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("修改推送信息已读状态变失败: " + e.getMessage());
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 获取当前用户未读消息数量
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCarToMemberAddress")
	public void getCarToMemberAddress(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			CarLineDTO carLineDTO = new CarLineDTO();
			// 通过APP传过来的线路Id获取线路信息
			carLineDTO = carLineApiServiceImpl.getCarLIneById(pushNstMessageDTO.getClId());
			// 获取当前线路的车辆的信息
			// CarsDTO cd =
			// carManagerApiService.getCarMessageById(Long.toString(pushNstMessageDTO.getClId()));
			// carLineDTO.setCarLength(cd.getCarLength());
			// carLineDTO.setCarType(cd.getCarType());
			AreaDTO a = new AreaDTO();
			if (pushNstMessageDTO.getCityId() != null
					&& !"".equals(pushNstMessageDTO.getCityId())) {
				a = areaToolService.getLngAndLatByCityId(pushNstMessageDTO
						.getCityId() + "");
				carLineDTO.setMlng(a.getLng());
				carLineDTO.setMlat(a.getLat());
				 carLineDTO.setApp(a.getAreaID());

			} else {
				carLineDTO.setMlng(Double.valueOf(0));
				carLineDTO.setMlat(Double.valueOf(0));
			}
			AreaDTO bj = memberAddressApiService.getArea("北京市");
			AreaDTO sh = memberAddressApiService.getArea("上海市");
			AreaDTO cq = memberAddressApiService.getArea("重庆市");
			AreaDTO tj = memberAddressApiService.getArea("天津市");

			carLineDTO.setBjlng(bj.getLng());
			carLineDTO.setBjlat(bj.getLat());

			carLineDTO.setShlng(sh.getLng());
			carLineDTO.setShlat(sh.getLat());

			carLineDTO.setTjlng(tj.getLng());
			carLineDTO.setTjlat(tj.getLat());

			carLineDTO.setCqlng(cq.getLng());
			carLineDTO.setCqlat(cq.getLat());
			carLineDTO.setDistance(Double.valueOf(100));
			

			List<MemberAddressDTO> list = memberAddressApiService.getMebApiMessageAagin(carLineDTO);
			if (list != null && list.size() == 5) {
				carLineApiService.setMebApiMessage(carLineDTO, list);
				result.setMsg("您好,成功匹配新的货源消息给你.");
			}
			// 如果全条件查询,等于0个的话,则再次用步带车长,车型条件的进行过滤
			if (list.size() == 0) {
				carLineDTO.setCarLength(null);
				carLineDTO.setCarType("");
				List<MemberAddressDTO> listtemp = memberAddressApiService
						.getMebApiMessageAagin(carLineDTO);
				if (listtemp != null && listtemp.size() > 0) {
					carLineApiService.setMebApiMessage(carLineDTO, listtemp);
					result.setMsg("您好,成功匹配新的货源消息给你.");
				}else{
					result.setMsg("暂无新消息推荐！");
				}
			}
			// 如果全条件查询的,大于0个小于5个,则行进一次不要带车长,车型条件查询
			if (list.size() > 0 && list.size() < 5) {
//				carLineDTO.setCarLength(null);
//				carLineDTO.setCarType(null);
//
//				// 过滤掉,已经查询出来的全条件查询的Id
//				Map<String, Object> p2 = new HashMap<String, Object>();
//				for (int k = 0; k < list.size(); k++) {
//					p2.put("id" + k, list.get(k).getId());
//				}
//				p2.put("size", list.size());
//				// 过滤掉已经查询出来的全条件查询的Id后,只要查剩余几条的
//				p2.put("endRow", 5 - list.size());
//				List<MemberAddressDTO> list2 = memberAddressApiService
//						.getMebApiMessagetwo(carLineDTO, p2);
//				if (list.size() > 0) {
//
//					list.addAll(list2);
//				}
				carLineDTO.setCarType(null);
				carLineDTO.setCarLength(null);
				//第二次根据条件查询
				List<MemberAddressDTO> list2 = memberAddressApiService
						.getMebApiMessageAagin(carLineDTO);
				
				List<MemberAddressDTO> list3 = new ArrayList<>();
				//首先将第一次查询list的放入list3中
				list3.addAll(list);

				boolean flag = false;
				for (MemberAddressDTO memberAddressDTO : list2) {
					if (flag) {
						break;
					}
					for (MemberAddressDTO cld : list) {
						// 第二次推送线路的信息不能与第一次相同
						if (memberAddressDTO.getId().equals(cld.getId())) {
							continue;
						}
						//循环加入第二次查询
						list3.add(memberAddressDTO);
						// 当推荐线路信息==MAX_MESSAGE 停止循环
						if (list3.size() == 5) {
							flag = true;
							break;
						}
					}
				}
				if (list3 != null && list3.size() > 0) {
					carLineApiService.setMebApiMessage(carLineDTO, list3);
					result.setMsg("您好,成功匹配新的货源消息给你.");
				}else{
					result.setMsg("暂无新消息推荐！");
				}
			//	memberAddressApiService.setCarLApiMessage(memberAddressDTO,
			//			list3);
				//加入返回集合中
			//	returnList.addAll(list3);

			}
			// if(mc<5){
			//
			// carLineDTO.setCarLength(null);
			// carLineDTO.setCarType(null);
			// list = memberAddressApiService.getMebApiMessage(carLineDTO);
			// }

			// carsDTO.setCarTotal(i);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			
		}catch (Exception e) {
			logger.info("修改推送信息已读状态变失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("修改推送信息已读状态变失败: ");
		}
		renderJson(result, request, response);
	}
	
	
	/**
	 * 更新推送信息已读状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delNstPush")
	public void delNstPush(HttpServletRequest request,
			HttpServletResponse response,PushNstMessageDTO pushNstMessageDTO) {
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isEmpty(pushNstMessageDTO.getMessageIdString())) {
				logger.debug("批量删除orderNo不能为空");
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			// app端传入orderNoString,多个订单号以逗号隔开
			List<String> orderNoList = Arrays.asList(pushNstMessageDTO
					.getMessageIdString().split(","));
			map.put("orderNoList", orderNoList);
			
			Integer status = pushNstMessageService.delNstPush(map);
			if (status <= 0) {
				setResult(result, ErrorCodeEnum.FAIL, request, response);
				return;
			}
			//int i=pushNstMessageService.delNstPush(pushNstMessageDTO);
            //carsDTO.setCarTotal(i);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("修改推送信息已读状态变失败", e);
			result.setObject(null);
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg("修改推送信息已读状态变失败: " + e.getMessage());
		}
		renderJson(result, request, response);
	}
}
