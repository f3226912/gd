package com.gudeng.commerce.gd.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.MyAddressToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.MyAddressDTO;
import com.gudeng.commerce.gd.customer.entity.MyAddress;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 我的====地址管理
 * 
 * @author lidong dli@gdeng.cn
 * @time 2016年8月23日 上午10:23:43
 */
@Controller
public class MyAddressController extends GDAPIBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MyAddressController.class);
	@Autowired
	private IGDBinaryRedisClient redisClient;
	@Autowired
	private MyAddressToolService myAddressToolService;

	private final static String TOKEN = "MyAddressController";
	private final static int EXPIRE = 60 * 10;// 600秒

	/**
	 * 进入添加/编辑 地址页面,产生随机token，防止重复提交数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "myAddress/token" }, method = { RequestMethod.POST })
	public void token(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			if (StringUtils.isEmpty(memberId)) {
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "用户不可为空", request, response);
				return;
			}
			String token = UUID.randomUUID().toString();
			redisClient.setex(TOKEN + memberId, EXPIRE, token);
			result.setObject(token);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取token错误，加载异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "加载异常", request, response);
		}
	}

	/**
	 * 地址列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "myAddress/query" })
	public void query(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			map.put("memberId", memberId);
			map.put("status", 1);
			List<MyAddressDTO> list = myAddressToolService.getList(map);
			result.setObject(list);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取地址列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "获取地址异常", request, response);
		}
	}

	/**
	 * 获取默认地址
	 * 
	 * @param request
	 * @param response
	 * @author lidong dli@gdeng.cn
	 * @time 2016年8月23日 下午3:44:59
	 */
	@RequestMapping(value = { "myAddress/preferAddr" })
	public void preferAddr(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			map.put("memberId", memberId);
			map.put("status", 1);
			map.put("prefer", 1);
			List<MyAddressDTO> list = myAddressToolService.getList(map);
			if (list != null && list.size() > 0) {
				result.setObject(list.get(0));
			} else {
				map.put("prefer", 0);
				list = myAddressToolService.getList(map);
				if (list != null && list.size() > 0) {
					result.setObject(list.get(0));
				}
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取地址列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "获取地址异常", request, response);
		}
	}

	/**
	 * 保存数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "myAddress/save" }, method = { RequestMethod.POST })
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String id = GSONUtils.getJsonValueStr(jsonStr, "id");
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			String token = GSONUtils.getJsonValueStr(jsonStr, "token");
			// 验证token，防止重复提交
			if (token == null) {
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "页面超时,请重新进入页面", request, response);
				return;
			} else if (!token.equals(redisClient.get(TOKEN + memberId))) {
				setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "页面超时,请重新进入页面", request, response);
				return;
			}
			redisClient.del(TOKEN + memberId);// 清除token，防止重复提交
			if (StringUtils.isNotEmpty(id)) {
				// 修改
				MyAddressDTO dto = (MyAddressDTO) GSONUtils.fromJsonToObject(jsonStr, MyAddressDTO.class);
				dto.setUpdateUser(Long.valueOf(memberId));
				myAddressToolService.update(dto);
				if ("1".equals(dto.getPrefer())) {// 若选择为默认地址，则其他地址清除默认地址设置
					Map<String, Object> map = new HashMap<>();
					map.put("id", id);
					map.put("memberId", memberId);
					myAddressToolService.prefer(map);
				}
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "修改地址成功", request, response);
			} else {
				// 新增
				MyAddress entity = (MyAddress) GSONUtils.fromJsonToObject(jsonStr, MyAddress.class);
				entity.setStatus("1");
				if (StringUtils.isEmpty(entity.getGender())) {
					entity.setGender("0");
				}
				if (StringUtils.isEmpty(entity.getPrefer())) {
					entity.setPrefer("0");
				}
				entity.setCreateUser(Long.valueOf(memberId));
				entity.setCreateTime(new Date());
				long resultId = myAddressToolService.insert(entity);
				if ("1".equals(entity.getPrefer())) {// 若选择为默认地址，则其他地址清除默认地址设置
					Map<String, Object> map = new HashMap<>();
					map.put("id", resultId);
					map.put("memberId", memberId);
					myAddressToolService.prefer(map);
				} else {
					// 如果是第一次添加地址
					Map<String, Object> map = new HashMap<>();
					map.put("memberId", memberId);
					map.put("status", 1);
					List<MyAddressDTO> list = myAddressToolService.getList(map);
					if (list.size() == 1) {
						map.put("id", resultId);
						map.put("memberId", memberId);
						myAddressToolService.prefer(map);// 第一次添加地址，则将该地址设为默认地址
					}
				}
				setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "添加地址成功", request, response);
			}
		} catch (Exception e) {
			logger.trace("保存数据异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "保存数据异常", request, response);
		}
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("myAddress/view")
	public void view(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String id = GSONUtils.getJsonValueStr(jsonStr, "id");
			MyAddressDTO dto = myAddressToolService.getById(id);
			result.setObject(dto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "", request, response);
		} catch (Exception e) {
			logger.trace("获取地址详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "获取地址详情异常", request, response);
		}
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "myAddress/delete" }, method = { RequestMethod.POST })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			MyAddressDTO dto = (MyAddressDTO) GSONUtils.fromJsonToObject(jsonStr, MyAddressDTO.class);
			dto.setUpdateUser(Long.valueOf(memberId));
			dto.setStatus("0");
			myAddressToolService.update(dto);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "删除收货地址成功", request, response);
		} catch (Exception e) {
			logger.trace("删除我的地址异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "服务器异常", request, response);
		}
	}

	/**
	 * 偏好设置，设置默认地址
	 * 
	 * @param request
	 * @param response
	 * @author lidong dli@gdeng.cn
	 * @time 2016年8月23日 下午12:24:02
	 */
	@RequestMapping(value = { "myAddress/prefer" }, method = { RequestMethod.POST })
	public void prefer(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			String memberId = GSONUtils.getJsonValueStr(jsonStr, "memberId");
			String id = GSONUtils.getJsonValueStr(jsonStr, "id");
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			map.put("memberId", memberId);
			myAddressToolService.prefer(map);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS.getStatusCode(), "设置默认地址成功", request, response);
		} catch (Exception e) {
			logger.trace("设置默认地址异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL.getStatusCode(), "设置默认地址异常", request, response);
		}
	}
}
