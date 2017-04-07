package com.gudeng.commerce.gd.m.controller.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.NstSameCityAddressEntity;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.dto.PageDTO;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.m.service.MemberAddressToolService;
import com.gudeng.commerce.gd.m.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.m.util.ObjectResult;
import com.gudeng.commerce.gd.m.util.PageUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 物流页面的所有H5处理controller
 * 
 * @author Ailen
 *
 */
@Controller
@RequestMapping("/transfer")
public class TransferController extends MBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(TransferController.class);

	@Autowired
	private MemberAddressToolService memberaddressService;

	@Autowired
	private CallstatiSticsToolService callService;

	@Resource
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;

	@Autowired
	private NstSameCityAddressToolService nstSameCityAddressToolService;

	/**
	 * 显示已发布的所有单列表 分页数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sendList")
	public String showSendList(String memberId, String fromCode) {
		putModel("userId", memberId);
		putModel("fromCode", fromCode);

		return "/H5/transfer/sendList";
	}

	/**
	 * 获取已发布数据方法 JSON
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param fromCode
	 * @param pageDTO
	 */
	@RequestMapping("listSendDataCond/{userId}")
	@ResponseBody
	public void listSendData(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, String fromCode, PageDTO<String> pageDTO) {
		ObjectResult result = new ObjectResult();

		try {
			if (userId == null || "".equals(userId)) {
				logger.debug("传入的memberId不能为空");
				return;
			} else {
				Map<String, Object> query = new HashMap<>();

				/*
				 * 设置查询参数
				 */
				query.put("userId", userId);
				query.put("sendData", true);

				// 获取总数 通过query提供的参数 此处有service层提供方法
				int size = memberaddressService.getTotalForOrder(query);

				/*
				 * 默认使用创建pageDTO对象 页大小=5
				 */
				PageDTO<MemberAddressDTO> pageDto = PageUtil.create(MemberAddressDTO.class, 10);

				// 设置总数据
				pageDto.setTotalSize(size);

				// 设置查询分页基本参数 查询前初始化数据
				setCommParameters(pageDto, query);

				// 获取数据库数据 并设置到pageDTO中
				List<MemberAddressDTO> data = memberaddressService.getListForOrder(query);

				pageDto.setPageData(data);

				// 填充结果集
				result.setObject(pageDto);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");

			}
		} catch (Exception e) {
			logger.info("显示所有我已发布的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 显示单个订单的所有单列表 分页数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("showList")
	public String showList(String orderNo, String fromCode, String userId) {
		putModel("orderNo", orderNo);
		putModel("fromCode", fromCode);
		putModel("userId", userId);
		return "/H5/transfer/singleOrder";
	}

	/**
	 * 获取单个订单数据方法 JSON
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param fromCode
	 * @param pageDTO
	 */
	@RequestMapping("listDataCond/{orderNo}")
	@ResponseBody
	public void listData(HttpServletRequest request, HttpServletResponse response, @PathVariable String orderNo, String userId, String fromCode, PageDTO<String> pageDTO) {
		ObjectResult result = new ObjectResult();
		putModel("userId", userId);
		try {
			if (orderNo == null || "".equals(orderNo)) {
				logger.debug("传入的orderNo不能为空");
				return;
			} else {
				Map<String, Object> query = new HashMap<>();

				/*
				 * 设置查询参数
				 */
				query.put("orderNo", orderNo);
				query.put("userId", userId);

				// 获取总数 通过query提供的参数 此处有service层提供方法
				int size = memberaddressService.getTotalForOrder(query);

				/*
				 * 默认使用创建pageDTO对象 页大小=5
				 */
				PageDTO<MemberAddressDTO> pageDto = PageUtil.create(MemberAddressDTO.class, 10);

				// 设置总数据
				pageDto.setTotalSize(size);

				// 设置查询分页基本参数 查询前初始化数据
				setCommParameters(pageDto, query);

				// 获取数据库数据 并设置到pageDTO中
				List<MemberAddressDTO> data = memberaddressService.getListForOrder(query);

				pageDto.setPageData(data);

				// 填充结果集
				result.setObject(pageDto);
				result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
				result.setMsg("success");

			}
		} catch (Exception e) {
			logger.info("显示所有我已发布的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 获取待成交物流订单数据
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 * @param fromCode
	 */
	@RequestMapping("listWfDataCond")
	@ResponseBody
	public void listWfData(HttpServletRequest request, String userId, HttpServletResponse response, MemberAddressDTO memberAddressDTO, String fromCode) {
		ObjectResult result = new ObjectResult();
		putModel("userId", userId);
		try {
			memberAddressDTO.setOrderStatusInt(1);
			PageDTO<MemberAddressDTO> pageDto = getListByConditionPage(request, memberAddressDTO);
			// 填充结果集
			result.setObject(pageDto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("显示所有我待成交的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 获取已成交物流订单数据
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 * @param fromCode
	 */
	@RequestMapping("listFDataCond")
	@ResponseBody
	public void listFData(HttpServletRequest request, String userId, HttpServletResponse response, MemberAddressDTO memberAddressDTO, String fromCode) {
		ObjectResult result = new ObjectResult();
		putModel("userId", userId);
		try {
			memberAddressDTO.setOrderStatusInt(2);
			PageDTO<MemberAddressDTO> pageDto = getListByConditionPage(request, memberAddressDTO);
			// 填充结果集
			result.setObject(pageDto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("显示所有我已成交的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 获取已完成物流订单数据
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 * @param fromCode
	 */
	@RequestMapping("listFoDataCond")
	@ResponseBody
	public void listFoData(HttpServletRequest request, String userId, HttpServletResponse response, MemberAddressDTO memberAddressDTO, String fromCode) {
		ObjectResult result = new ObjectResult();
		putModel("userId", userId);
		try {
			memberAddressDTO.setOrderStatusInt(3);
			PageDTO<MemberAddressDTO> pageDto = getListByConditionPage(request, memberAddressDTO);
			// 填充结果集
			result.setObject(pageDto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("显示所有我已完成的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 获取未完成物流订单数据
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 * @param fromCode
	 */
	@RequestMapping("listuFDataCond")
	@ResponseBody
	public void listuFData(HttpServletRequest request, HttpServletResponse response, String userId, MemberAddressDTO memberAddressDTO, String fromCode) {
		ObjectResult result = new ObjectResult();
		putModel("userId", userId);
		try {
			memberAddressDTO.setShowRejectAndCal(true);
			PageDTO<MemberAddressDTO> pageDto = getListByConditionPage(request, memberAddressDTO);
			// 填充结果集
			result.setObject(pageDto);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("显示所有我未完成的单失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("editSend")
	public String toEditSend() {

		return "";
	}

	@RequestMapping("delSend")
	public String delSend() {

		return "";
	}

	/**
	 * 显示已发布详细物流信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("showSendTranDetail/{id}/{fromCode}")
	public String showSendTransferDetail(@PathVariable String id, String userId, @PathVariable String fromCode, String c, String orderNo, Integer source) {
		putModel("userId", userId);
		putModel("c", c);
		try {
			MemberAddressDTO memberAddressDTO = memberaddressService.getByIdForOrder(id, 0, source);
			putModel("mem", memberAddressDTO);
			putModel("fromCode", fromCode);
			putModel("orderNo", orderNo);

		} catch (Exception e) {
			logger.info("显示物流订单详细失败", e);
			e.printStackTrace();
		}
		return "/H5/transfer/sendDetail";
	}

	/**
	 * 显示待成交详细物流信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("showWfTranDetail/{id}/{fromCode}")
	public String showWfTransferDetail(@PathVariable String id, String userId, @PathVariable String fromCode, String c, String orderNo, Integer source) {
		putModel("userId", userId);
		try {
			MemberAddressDTO memberAddressDTO = memberaddressService.getByIdForOrder(id, 1, source);
			putModel("mem", memberAddressDTO);
			putModel("fromCode", fromCode);
			putModel("orderNo", orderNo);
			putModel("c", c);
		} catch (Exception e) {
			logger.info("显示物流订单详细失败", e);
			e.printStackTrace();
		}
		return "/H5/transfer/WfDetail";
	}

	/**
	 * 显示已成交详细物流信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("showFTranDetail/{id}/{fromCode}")
	public String showFTransferDetail(@PathVariable String id, String userId, @PathVariable String fromCode, String c, String orderNo, Integer source) {
		putModel("userId", userId);
		try {
			MemberAddressDTO memberAddressDTO = memberaddressService.getByIdForOrder(id, 2, source);
			putModel("mem", memberAddressDTO);
			putModel("fromCode", fromCode);
			putModel("orderNo", orderNo);
			putModel("c", c);
		} catch (Exception e) {
			logger.info("显示物流订单详细失败", e);
			e.printStackTrace();
		}
		return "/H5/transfer/FDetail";
	}

	/**
	 * 显示已完成详细物流信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("showFoTranDetail/{id}/{fromCode}")
	public String showFoTransferDetail(@PathVariable String id, String userId, @PathVariable String fromCode, String c, String orderNo, Integer source) {
		putModel("userId", userId);
		try {
			MemberAddressDTO memberAddressDTO = memberaddressService.getByIdForOrder(id, 3, source);
			putModel("mem", memberAddressDTO);
			putModel("fromCode", fromCode);
			putModel("orderNo", orderNo);
			putModel("c", c);
		} catch (Exception e) {
			logger.info("显示物流订单详细失败", e);
			e.printStackTrace();
		}
		return "/H5/transfer/FoDetail";
	}

	/**
	 * 显示未完成详细物流信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("showufTranDetail/{id}/{fromCode}")
	public String showuFTransferDetail(@PathVariable String id, String userId, @PathVariable String fromCode, String c, String orderNo, Integer source) {
		putModel("userId", userId);
		try {
			MemberAddressDTO memberAddressDTO = memberaddressService.getByIdForOrder(id, 4, source);
			putModel("mem", memberAddressDTO);
			putModel("fromCode", fromCode);
			putModel("orderNo", orderNo);
			putModel("c", c);
		} catch (Exception e) {
			logger.info("显示物流订单详细失败", e);
			e.printStackTrace();
		}
		return "/H5/transfer/uFDetail";
	}

	@RequestMapping("addCall")
	@ResponseBody
	public void addCall(HttpServletRequest request, HttpServletResponse response, CallstatiSticsDTO callstatiSticsDTO) {
		ObjectResult result = new ObjectResult();

		try {
			callstatiSticsDTO.setSource("WLXQ");
			
			callService.insert(callstatiSticsDTO);

			// 填充结果集
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");

		} catch (Exception e) {
			logger.info("添加电话记录失败", e);
			e.printStackTrace();
		}

		// 发送JSON数据
		renderJson(result, request, response);
	}

	/**
	 * 设置查询条件查询物流订单列表 分页
	 * 
	 * @param request
	 * @param nstOrderBaseinfoDTO
	 * @return
	 * @throws Exception
	 */
	private PageDTO<MemberAddressDTO> getListByConditionPage(HttpServletRequest request, MemberAddressDTO memberAddressDTO) throws Exception {

		/*
		 * 判断用户不为空
		 */
		if (memberAddressDTO.getUserId() == null || "".equals(memberAddressDTO.getUserId())) {
			logger.debug("传入的userId不能为空");
			throw new RuntimeException("传入的userId不能为空");
		} else {

			Map<String, Object> query = new HashMap<>();

			/*
			 * 设置查询参数
			 */
			query.put("memberId", memberAddressDTO.getUserId());

			if (memberAddressDTO.getShowRejectAndCal() != null && memberAddressDTO.getShowRejectAndCal()) {
				query.put("showRejectAndCal", true);
			}

			// set the order status
			if (memberAddressDTO.getOrderStatusInt() != null && !"".equals(memberAddressDTO.getOrderStatusInt())) {
				query.put("orderStatusInt", memberAddressDTO.getOrderStatusInt());
			}

			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size = memberaddressService.getTotalForOrder(query);

			/*
			 * 默认使用创建pageDTO对象 页大小=5
			 */
			PageDTO<MemberAddressDTO> pageDto = PageUtil.create(MemberAddressDTO.class, 10);

			// 设置总数据
			pageDto.setTotalSize(size);

			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, query);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(memberaddressService.getListForOrder(query));

			return pageDto;

		}

	}

	/**
	 * 接收订单（状态变已提交）
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping(value = "/acceptOrder")
	@ResponseBody
	public String acceptOrder(HttpServletRequest request, HttpServletResponse response, NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "接受订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			// 查出的订单状态为1是才可以被提交订单
			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			if (orderNoStatus != 1) {
				resultMap.put("status", -1);
				resultMap.put("msg", "订单已被取消");
				return JSONObject.toJSONString(resultMap);
			}
			Integer status = nstOrderBaseinfoToolService.acceptOrder(map);
			if (status <= 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "接受订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			logger.info("接收订单失败", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "接受订单失败");
		}
		return JSONObject.toJSONString(resultMap);
	}

	/**
	 * 拼装Map ，必须有订单号和登录人ID
	 * 
	 * @param result
	 * @param nstOrderBaseinfoDTO
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	private Map<String, Object> shouldOrderNoAndMemberId(ObjectResult result, NstOrderBaseinfoDTO nstOrderBaseinfoDTO, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(nstOrderBaseinfoDTO.getOrderNo())) {
			logger.debug("运单orderNo不能为空");
			return map;
		}
		if (nstOrderBaseinfoDTO.getMemberId() == null) {
			logger.debug("传入memberId不能为空");
			return map;
		}
		map.put("orderNo", nstOrderBaseinfoDTO.getOrderNo());
		map.put("memberId", nstOrderBaseinfoDTO.getMemberId());
		return map;
	}

	/**
	 * 拒绝订单
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/refuseOrder")
	@ResponseBody
	public String refuseOrder(HttpServletRequest request, HttpServletResponse response, NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "拒绝订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			// 查出的订单状态为1是才可以被拒绝订单
			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			if (orderNoStatus != 1) {
				resultMap.put("status", -1);
				resultMap.put("msg", "订单已被取消");
				return JSONObject.toJSONString(resultMap);
			}
			Integer status = nstOrderBaseinfoToolService.refuseOrder(map);
			if (status <= 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "拒绝订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			logger.info("拒绝订单失败", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "拒绝订单失败");
		}
		return JSONObject.toJSONString(resultMap);
	}

	/**
	 * 确认收货，状态设置为已完成
	 * 
	 * @param request
	 * @param response
	 * @param nstOrderBaseinfoDTO
	 */
	@RequestMapping("/confirmGoods")
	@ResponseBody
	public String confirmGoods(HttpServletRequest request, HttpServletResponse response, NstOrderBaseinfoDTO nstOrderBaseinfoDTO) {
		ObjectResult result = new ObjectResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map = shouldOrderNoAndMemberId(result, nstOrderBaseinfoDTO, map, request, response);
			// 如果map长度为0，则return
			if (map.size() == 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "确认订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			Integer status = nstOrderBaseinfoToolService.confirmGoods(map);
			if (status <= 0) {
				resultMap.put("status", -1);
				resultMap.put("msg", "确认订单失败");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			logger.info("确认收货失败", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "确认订单失败");
		}
		return JSONObject.toJSONString(resultMap);
	}

	@RequestMapping("delete/{memberAddressId}/{source}")
	@ResponseBody
	public String delete(@PathVariable("memberAddressId") String memberAdressId, @PathVariable String source) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			NstOrderBaseinfoDTO nstOrderBaseinfoDTO = nstOrderBaseinfoToolService.getByMemberAddressId(Long.parseLong(memberAdressId), source);

			if (nstOrderBaseinfoDTO != null) {
				resultMap.put("status", -1);
				resultMap.put("msg", "订单已被接单");
				return JSONObject.toJSONString(resultMap);
			}
			// int orderNoStatus = nstOrderBaseinfoToolService
			// .getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			// if (orderNoStatus == 1 || orderNoStatus == 2 || orderNoStatus ==
			// 3) {
			// resultMap.put("status", -1);
			// resultMap.put("msg", "订单已被接单");
			// return JSONObject.toJSONString(resultMap);
			// }

			if (StringUtils.isEmpty(source) || "0".equals(source)) {

				memberaddressService.updateMemberAdressStatusByid(memberAdressId);
			} else {
				NstSameCityAddressEntity entity = new NstSameCityAddressEntity();
				entity.setId(Long.parseLong(memberAdressId));
				Integer status=nstSameCityAddressToolService.delete(entity);
				if (status>0) {
					//传入的-1表示是同城这边的删除了货源，然后在服务端将-1修改为0 转态表示未出货
					nstOrderBaseinfoToolService.updateDeliverStatusByMemberAddressId(entity.getId(), -1);
				}
			}

			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("删除物流信息失败：" + e.getMessage());
			resultMap.put("status", -1);
			resultMap.put("msg", "删除物流信息失败");
		}
		return JSONObject.toJSONString(resultMap);
	}

	@RequestMapping("status/{memberAddressId}/{source}")
	@ResponseBody
	public String getStatus(@PathVariable("memberAddressId") String memberAdressId, @PathVariable String source) {
		try {
			NstOrderBaseinfoDTO nstOrderBaseinfoDTO = nstOrderBaseinfoToolService.getByMemberAddressId(Long.parseLong(memberAdressId), source);
			if (nstOrderBaseinfoDTO == null) {
				return "";
			}

			int orderNoStatus = nstOrderBaseinfoToolService.getStatusByOrderNo(nstOrderBaseinfoDTO.getOrderNo());
			return String.valueOf(orderNoStatus);
		} catch (Exception e) {
			logger.info("查询状态失败", e);
		}
		return "";
	}
}
