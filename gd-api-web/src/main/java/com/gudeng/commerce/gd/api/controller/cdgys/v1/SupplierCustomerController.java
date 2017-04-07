package com.gudeng.commerce.gd.api.controller.cdgys.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ReMemForCustToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.supplier.dto.ReCustInfoDTO;
import com.gudeng.commerce.gd.supplier.dto.ReMemForCustDTO;

/**
 * 产地供应商 会员
 * @author Semon
 *
 */
@Controller
@RequestMapping("cdgys")
public class SupplierCustomerController extends GDAPIBaseController {
	
	/** 记录日志־ */
	private static Logger logger = LoggerFactory.getLogger(SupplierCustomerController.class);

	@Autowired
	public ReMemForCustToolService custToolService;
	
	/**
	 * 通过条件查询对应的数据
	 * @param request
	 * @param response
	 * @param searchReMemForCustDTO order 排序字段（倒序） searchName 搜索姓名
	 * 
	 */
	@RequestMapping("v1/getSupCustList")
	public void getList(HttpServletRequest request, HttpServletResponse response, ReMemForCustDTO searchReMemForCustDTO, String order, Long busiMemberId, Integer pageSize, Integer pageNum) {
		ObjectResult result = new ObjectResult();
		
		try {
			//写入日志 查询条件
			logger.debug("search customer of supplier in the search words: order-" + order + 
					", searchName: " + searchReMemForCustDTO.getSearchName());
			
			Map<String, Object> params = new HashMap<>();
			
			params.put("type", "1");
			/*
			 * 判断是否存在供应商ID
			 */
			if(busiMemberId==null || busiMemberId == 0L) {
				throw new Exception("不存在供应商ID");
			} else {
				//加入查询条件
				params.put("busiMemberId", busiMemberId);
			}
			
			/*
			 * 添加查询参数
			 */
			if(order!=null && !"".equals(order)) {
				params.put("order", order);
			}
			
			if(searchReMemForCustDTO!=null&&searchReMemForCustDTO.getSearchName()!=null&&!"".equals(searchReMemForCustDTO.getSearchName())) {
				params.put("searchName", searchReMemForCustDTO.getSearchName());
			}
			
			/*
			 * 分页参数设置
			 */
			CommonPageDTO pageDTO = getPageInfo(request, params);

			Integer total = custToolService.getTotal(params);
			//查询数据
			List<ReMemForCustDTO> resultList = custToolService.queryCustomer(params);

			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			
			//当前第几页
			String page=request.getParameter("pageNum");
			
			pageDTO.setCurrentPage(Integer.parseInt((page == null || page == "0") ? "1":page));
			pageDTO.setRecordList(resultList);
			
			
			/*
			 * 封装数据
			 */
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		} catch (Exception e) {
			logger.warn("[ERROR]查找供应商客户信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 查询指定客户信息
	 * @param request
	 * @param response
	 * @param Long id 
	 * 
	 */
	@RequestMapping("v1/getSupCustOne")
	public void getSupCustOne(HttpServletRequest request, HttpServletResponse response, Long id) {
		ObjectResult result = new ObjectResult();
		
		try {
			//写入日志 查询条件
			logger.debug("search customer of supplier by id: -" + id);
			
			ReMemForCustDTO reMemForCustDTO = custToolService.getCustomerById(id);
			
			/*
			 * 封装数据
			 */
			result.setObject(reMemForCustDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		} catch (Exception e) {
			logger.warn("[ERROR]查找供应商客户详细信息异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 添加电话和地址信息
	 * @param request
	 * @param response
	 * @param Long id 
	 * 
	 */
	@RequestMapping("v1/addMobileOrAddressForSupCust")
	public void addMobileOrAddressForSupCust(HttpServletRequest request, HttpServletResponse response, Long id, String name, String addresses, String mobiles) {
		ObjectResult result = new ObjectResult();
		
		try {
			//写入日志 查询条件
			logger.debug("add customer infomation of addresses and mobiles : id -" + id);
			
			/*
			 * 保证ID不为空
			 */
			if(id!=null && id != 0L) {
				
				/*
				 * 修改客户备注姓名
				 */
				ReMemForCustDTO custDTO = new ReMemForCustDTO();
				custDTO.setId(id);
				custDTO.setName(name);
				custToolService.updateReMemForCust(custDTO);
				
				//创建用户信息集合
				List<ReCustInfoDTO> custInfoDTOs = new ArrayList<>();
				
				//判断是否存在地址需要保存
				if(addresses!=null&&!"".equals(addresses)) {
					
					/*
					 * 提取所有地址信息
					 */
					String[] addreStr = addresses.split("--");
					
					String address = null;
					
					/*
					 * 判断是否超过限制
					 */
					if(addreStr.length>5) {
						throw new Exception("地址最多五条");
					}
					
					/*
					 * 封装对应的对象
					 */
					for (int i = 0; i < addreStr.length; i++) {
						address = addreStr[i];
						
						/*
						 * 封装数据
						 */
						ReCustInfoDTO custInfoDTO = new ReCustInfoDTO();
						custInfoDTO.setReCustId(id);	//对应关联客户表ID
						custInfoDTO.setAddress(address);	//对应地址数据
						custInfoDTO.setType("1");	//设置数据类型  1：地址
						
						custInfoDTOs.add(custInfoDTO);
					}
					
				}
				
				//判断是否存在电话需要保存
				if(mobiles!=null&&!"".equals(mobiles)) {
					
					/*
					 * 提取所有电话信息
					 */
					String[] mobilStr = mobiles.split("--");
					
					if(mobilStr.length>5) {
						throw new Exception("电话最多五条");
					}
					
					String mobile = null;
					
					/*
					 * 封装对应的对象
					 */
					for (int i = 0; i < mobilStr.length; i++) {
						mobile = mobilStr[i];
						
						/*
						 * 封装数据
						 */
						ReCustInfoDTO custInfoDTO = new ReCustInfoDTO();
						custInfoDTO.setReCustId(id);	//对应关联客户表ID
						custInfoDTO.setMobile(mobile);	//对应电话数据
						custInfoDTO.setType("2");	//设置数据类型  2：电话
						
						custInfoDTOs.add(custInfoDTO);
					}
					
				}
				
				custToolService.addMobileOrAddressForCustomerMor(custInfoDTOs);
			} else {
				throw new Exception("ID为空");
			}
			
			/*
			 * 封装结果
			 */
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			
		} catch (Exception e) {
			logger.warn("[ERROR]添加客户电话和地址异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	

}
