package com.gudeng.commerce.gd.admin.controller.active;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.active.PromProdinfoToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.taobao.api.internal.util.StringUtils;

/**
 * 促销商品列表
 * @author gdeng
 *
 */
@Controller
@RequestMapping("active")
public class PromProdinfoController extends AdminBaseController{
	
	private final GdLogger logger = GdLoggerFactory.getLogger(PromProdinfoController.class);
	
	@Resource
	private PromProdinfoToolService promProdinfoToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;

	@RequestMapping("toPromSupplerinfo")
	public ModelAndView toPromBaseinfo(@RequestParam(required=false) String actId){
		ModelAndView mv = new ModelAndView();
		mv.addObject("actId", actId);
		mv.setViewName("active/supplyList");
		return mv;
	}
	
	@RequestMapping("toPromProduct")
	public String toPromProduct(){
		return "active/promProductList";
	}
	
	@RequestMapping("querySuppler")
	@ResponseBody
	public String querySuppler(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			List<PromProdInfoDTO> list = null;
			//活动Id为空不查询
			if(StringUtils.isEmpty(request.getParameter("actId"))){
				resultMap.put("total", 0);
			} else {
				Map<String, Object> map = getSearchMapParams(request);
				Integer total = promProdinfoToolService.getSupplerTotalCountByCondition(map);
				resultMap.put("total", total);
				
				setCommParameters(request, map);
				list = promProdinfoToolService.querySupplerPageByCondition(map);
			}
			
			resultMap.put("rows", list == null ? new ArrayList<>() : list);
		}catch(Exception e){
			logger.info("查询供应商列表异常",e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	@RequestMapping("queryProduct")
	@ResponseBody
	public String queryProduct(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			List<PromProdInfoDTO> list = null;
			//活动Id为空不查询
			if(StringUtils.isEmpty(request.getParameter("actId"))){
				resultMap.put("total", 0);
			} else {
				Map<String, Object> map = getSearchMapParams(request);
				Integer total = promProdinfoToolService.getProductTotalCountByCondition(map);
				resultMap.put("total", total);
				
				setCommParameters(request, map);
				list = promProdinfoToolService.queryProductPageByCondition(map);
				for(PromProdInfoDTO dto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getMemberId());
					dto.setMemberName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
			
			resultMap.put("rows", list == null ? new ArrayList<>() : list);
		}catch(Exception e){
			logger.info("查询活动商品列表异常",e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 保存活动商品信息
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("saveProduct")
	@ResponseBody
	public Map<String,Object> saveProduct(PromBaseinfoDTO dto, HttpServletRequest request){
		Map<String,Object> res = new  HashMap<>();
		try {
			logger.info("保存活动商品信息入参:"+JSONObject.toJSONString(dto));
			SysRegisterUser user = this.getUser(request);
			List<PromProdInfoDTO> list = dto.getPromProdInfoList();
			if(null != list){
				Iterator<PromProdInfoDTO> it = list.iterator();
				while(it.hasNext()){
					PromProdInfoDTO pp = it.next();
					//审核 
					if(pp.getAuditStatus() == null){
						pp.setAuditStatus(2);//不参加活动 直接跳过
					} else if(pp.getAuditStatus() == 3){
						it.remove();
						continue;
					}
					pp.setActId(dto.getActId());
					pp.setMemberId(user.getUserID());
					pp.setUpdateTime(new Date());
					pp.setUpdateUserId(user.getUserID());
				}
				if(list.size()>0){
					promProdinfoToolService.updatePromProdInfo(list);
				}
				
			}
			res.put("status", 1);//1成功
		} catch (Exception e) {
			res.put("status", 2);//2失败
			res.put("msg", e.getMessage());
			logger.info(e.getMessage(), e);
		}
		return res;
	}
	
	@RequestMapping("exportSupplier")
	public String exportSupplier(HttpServletRequest request, HttpServletResponse response){
		OutputStream ouputStream = null;
		try {
			//活动Id为空不查询
			if(StringUtils.isEmpty(request.getParameter("actId"))){
				return null;
			} else {
				Map<String, Object> map = getSearchMapParams(request);
				map.put(START_ROW, 0);
				map.put(END_ROW, 9999999);//百万级
				List<PromProdInfoDTO> list = promProdinfoToolService.querySupplerPageByCondition(map);
				String[][] headss ={{"手机","mobile","java.lang.String"}
									,{"姓名","supplierName","java.lang.String"}
									,{"用户类型","supplierTypeName","java.lang.String"}
									,{"报名时间","createTime","java.util.Date"}
									};
				List<Map<String, Object>> ms = ExcelUtil.convertExportHeader(headss);
				for(PromProdInfoDTO dto : list){
					if(dto.getSupplierType() == null ||  dto.getSupplierType() == 4){
						dto.setSupplierTypeName("产地供应商");
					} else {
						throw new RuntimeException("用户类型错误，只能是供应商");
					}
				}
				
				// 设置输出响应头信息，
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				String fileName = null;
				String agent = request.getHeader("USER-AGENT");
	            if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
	                fileName = URLEncoder.encode("供应商列表", "UTF-8");
	            } else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
	            	fileName = new String("供应商列表".getBytes("UTF-8"), "ISO-8859-1");
	            }
	            String suffix = DateUtil.getDate(new Date(), "yyyy-MM-dd");
	            response.setHeader("Content-disposition", "attachment;filename=" + fileName +suffix + ".xlsx");
				ouputStream = response.getOutputStream();
				XSSFWorkbook book = ExcelUtil.buildXSLXExcel(ms, list);

				book.write(ouputStream);
			}
		} catch (Exception e) {

			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("exportProduct")
	public String exportProduct(HttpServletRequest request, HttpServletResponse response){
		OutputStream ouputStream = null;
		try {
			//活动Id为空不查询
			if(StringUtils.isEmpty(request.getParameter("actId"))){
				return null;
			} else {
				Map<String, Object> map = getSearchMapParams(request);
				map.put(START_ROW, 0);
				map.put(END_ROW, 9999999);//百万级
				List<PromProdInfoDTO> list = promProdinfoToolService.queryProductPageByCondition(map);
				String[][] headss ={{"供应商名称","supplierName","java.lang.String"}
									,{"商品名称","prodName","java.lang.String"}
									,{"库存","stockCount","java.lang.Double"}
									,{"单位","unitName","java.lang.String"}
									,{"正常价格","price","java.lang.Double"}
									,{"活动价","actPrice","java.lang.Double"}
									,{"是否参加活","auditStatusName","java.lang.String"}
									,{"操作人","memberName","java.lang.String"}
									};
				List<Map<String, Object>> ms = ExcelUtil.convertExportHeader(headss);
				for(PromProdInfoDTO dto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getMemberId());
					dto.setMemberName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
					if(dto.getAuditStatus() == null || dto.getAuditStatus() ==0 ||
							dto.getAuditStatus() == 2){
						dto.setAuditStatusName("不参加");
					} 
					else if(dto.getAuditStatus() == 1){
						dto.setAuditStatusName("参加");
					}
					else {
						dto.setAuditStatusName("已取消");
					}
				}
				
				// 设置输出响应头信息，
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				String fileName = null;
				String agent = request.getHeader("USER-AGENT");
	            if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
	                fileName = URLEncoder.encode("商品信息列表", "UTF-8");
	            } else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
	            	fileName = new String("商品信息列表".getBytes("UTF-8"), "ISO-8859-1");
	            }
	            String suffix = DateUtil.getDate(new Date(), "yyyy-MM-dd");
	            response.setHeader("Content-disposition", "attachment;filename=" + fileName +suffix + ".xlsx");
				ouputStream = response.getOutputStream();
				XSSFWorkbook book = ExcelUtil.buildXSLXExcel(ms, list);

				book.write(ouputStream);
			}
		} catch (Exception e) {

			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 查询列表参数
	 * @param request
	 * @return 
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actId", request.getParameter("actId"));
		map.put("supplierName", request.getParameter("supplierName"));
		map.put("prodName", request.getParameter("prodName"));
		return map;
	}
}
