package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftstoreToolService;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftInStorageDataDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftOutStorageDataDTO;
import com.gudeng.commerce.gd.promotion.util.ExcelUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 
 * @author gcwu
 *
 */
@Controller
public class GrdGdGiftDataController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdGdGiftDataController.class);


	@Autowired
	private GrdGdGiftstoreToolService grdGdGiftstoreToolService;

	/**
	 * 进入入库统计列表	
	 * 
	 * @param request
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("giftInStorageData/main")
	public String list(HttpServletRequest request) {		
		return "grdPurchaseOrders/main";
	}
	
	/**
	 * 进入出库统计列表	
	 * 
	 * @param request
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("giftOutStorageData/main")
	public String giftOutStorageData(HttpServletRequest request) {		
		return "grdPurchaseOrders/outlist";
	}
	/**
	 * 进入各仓库礼品统计
	 * 
	 * @param request
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("grdPurchaseOrders/giftdatalist")
	public String GrdGdGiftData(HttpServletRequest request) {
		return "grdPurchaseOrders/giftdatalist";
	}
	
	
	/**
	 * 出库统计列表查询
	 * 
	 * @param marketId 所属市场
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("giftOutStorageData/query")
	@ResponseBody
	public String giftOutStorageData(HttpServletRequest request,Integer marketId,Integer giftstoreId,String giftNo,String giftName,String outStorageStartDate,String outStorageEndDate) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			 if(marketId != null){
					map.put("marketId", marketId);
				}
				if(giftstoreId != null){
					map.put("giftstoreId", giftstoreId);
				}
				if(giftNo != null){
					map.put("giftNo", giftNo);
				}
				if(giftName != null && giftName !=""){
					map.put("giftName", giftName);
				}
				if(outStorageStartDate != null&& outStorageStartDate !=""){
					map.put("outStorageStartDate", outStorageStartDate);
				}
				if(outStorageEndDate != null&& outStorageEndDate !=""){
					map.put("outStorageEndDate", outStorageEndDate);
				}
			// 设置查询参数
			// 设定分页,排序
			setCommParameters(request, map);
			// 记录数
			map.put("total", grdGdGiftstoreToolService.getGiftOutStorageDataCount(map));
			// list
			List<GrdGdGiftOutStorageDataDTO> list = grdGdGiftstoreToolService.getGiftOutStorageData(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	
	
	@RequestMapping("getGrdGdGiftDataSum/query")
	@ResponseBody
	public String getGrdGdGiftDataSum(HttpServletRequest request,Integer marketId,Integer giftstoreId,String giftNo,String giftName,String outStorageStartDate,String outStorageEndDate,String inStorageStartDate,String inStorageEndDate,String type) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
	
			 if(type != null && type !=""){
					map.put("type", type);
				}
			 if(marketId != null){
					map.put("marketId", marketId);
				}
				if(giftstoreId != null){
					map.put("giftstoreId", giftstoreId);
				}
				if(giftNo != null){
					map.put("giftNo", giftNo);
				}
				if(giftName != null&& giftName !=""){
					map.put("giftName", giftName);
				}
				if(outStorageStartDate != null&& outStorageStartDate !=""){
					map.put("outStorageStartDate", outStorageStartDate);
				}
				if(outStorageEndDate != null&& outStorageEndDate !=""){
					map.put("outStorageEndDate", outStorageEndDate);
				}
				if(inStorageStartDate != null&& inStorageStartDate !=""){
					map.put("inStorageStartDate", inStorageStartDate);
				}
				if(inStorageEndDate != null&& inStorageEndDate !=""){
					map.put("inStorageEndDate", inStorageEndDate);
				}
			//合计
			map.put("giftSum",grdGdGiftstoreToolService.getGrdGdGiftDataSum(map)); 
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	/**
	 * 导出出库Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author gcwu
	 */
	@RequestMapping(value = "giftOutStorageData/exportData")
	public String exportgiftOutStorageData(HttpServletRequest request,Integer marketId,Integer giftstoreId,String giftNo,String giftName,String outStorageStartDate,String outStorageEndDate) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(marketId != null){
				map.put("marketId", marketId);
			}
			if(giftstoreId != null){
				map.put("giftstoreId", giftstoreId);
			}
			if(giftNo != null){
				map.put("giftNo", giftNo);
			}
			if(giftName != null&& giftName !=""){
				map.put("giftName", giftName);
			}
			if(outStorageStartDate != null&& outStorageStartDate !=""){
				map.put("outStorageStartDate", outStorageStartDate);
			}
			if(outStorageEndDate != null&& outStorageEndDate !=""){
				map.put("outStorageEndDate", outStorageEndDate);
			}
	        OutputStream ouputStream = null;
	        try {
	            // 设置输出响应头信息，
	            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("各仓库礼品出库统计").getBytes(), "ISO-8859-1")+ RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xlsx");
	            ouputStream = response.getOutputStream();
	            // 查询数据
	        	List<GrdGdGiftOutStorageDataDTO> list = grdGdGiftstoreToolService.getGiftOutStorageData(map);
	            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, GrdGdGiftOutStorageDataDTO.class);
	            workBook.write(ouputStream);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            try {
	                ouputStream.flush();
	                ouputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	}
	/**
	 * 导出入库Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author gcwu
	 */
	@RequestMapping(value = "giftInStorageData/exportData")
	public String exportData(HttpServletRequest request, Integer marketId,Integer giftstoreId,String giftNo,String giftName,String inStorageStartDate,String inStorageEndDate) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(marketId != null){
				map.put("marketId", marketId);
			}
			if(giftstoreId != null){
				map.put("giftstoreId", giftstoreId);
			}
			if(giftNo != null){
				map.put("giftNo", giftNo);
			}
			if(giftName != null&& giftName !=""){
				map.put("giftName", giftName);
			}
			if(inStorageStartDate != null&& inStorageStartDate !=""){
				map.put("inStorageStartDate", inStorageStartDate);
			}
			if(inStorageEndDate != null&& inStorageEndDate !=""){
				map.put("inStorageEndDate", inStorageEndDate);
			}
	        OutputStream ouputStream = null;
	        try {
	            // 设置输出响应头信息，
	            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("各仓库礼品入库统计").getBytes(), "ISO-8859-1")+ RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xlsx");
	            ouputStream = response.getOutputStream();
	            // 查询数据
	        	List<GrdGdGiftInStorageDataDTO> list = grdGdGiftstoreToolService.getGiftInStorageData(map);
	            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, GrdGdGiftInStorageDataDTO.class);
	            workBook.write(ouputStream);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            try {
	                ouputStream.flush();
	                ouputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	}
	
	
	
	/**
	 * 入库统计列表查询
	 * 
	 * @param marketId 所属市场
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("giftInStorageData/query")
	@ResponseBody
	public String giftInStorageData(Integer marketId,Integer giftstoreId,String giftNo,String giftName,String inStorageStartDate,String inStorageEndDate) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(marketId != null){
				map.put("marketId", marketId);
			}
			if(giftstoreId != null){
				map.put("giftstoreId", giftstoreId);
			}
			if(giftNo != null){
				map.put("giftNo", giftNo);
			}
			if(giftName != null&& giftName !=""){
				map.put("giftName", giftName);
			}
			if(inStorageStartDate != null&& inStorageStartDate !=""){
				map.put("inStorageStartDate", inStorageStartDate);
			}
			if(inStorageEndDate != null&& inStorageEndDate !=""){
				map.put("inStorageEndDate", inStorageEndDate);
			}
			// 设置查询参数
			// 设定分页,排序
			setCommParameters(request, map);
			// 记录数
			map.put("total", grdGdGiftstoreToolService.getGiftInStorageDataCount(map));

			// list
			List<GrdGdGiftInStorageDataDTO> list = grdGdGiftstoreToolService.getGiftInStorageData(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	/**
	 * 各仓库礼品统计
	 * 
	 * @param marketId 所属市场
	 * @return
	 * @author gcwu
	 */
	@RequestMapping("grdGdGiftData/query")
	@ResponseBody
	public String grdGdGiftData(Integer marketId,Integer giftstoreId,String giftNo,String giftName) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(marketId != null){
				map.put("marketId", marketId);
			}
			if(giftstoreId != null){
				map.put("giftstoreId", giftstoreId);
			}
			if(giftNo != null){
				map.put("giftNo", giftNo);
			}
			if(giftName != null&& giftName !=""){
				map.put("giftName", giftName);
			}
			// 设置查询参数
			// 设定分页,排序
			setCommParameters(request, map);
			// 记录数
			//map.put("total", grdGdGiftstoreToolService.getGiftInStorageDataCount(map));
			map.put("total", grdGdGiftstoreToolService.getGrdGdGiftDataCount(map));
			// list
			List<GrdGdGiftDataDTO> list = grdGdGiftstoreToolService.getGrdGdGiftData(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	/**
	 * 导出仓库礼品Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author gcwu
	 */
	@RequestMapping(value = "grdGdGiftData/exportData")
	public String exportgrdGdGiftDataData(HttpServletRequest request, HttpServletResponse response,Integer marketId,Integer giftstoreId,
			String giftNo,String giftName) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(marketId != null){
				map.put("marketId", marketId);
			}
			if(giftstoreId != null){
				map.put("giftstoreId", giftstoreId);
			}
			if(giftNo != null){
				map.put("giftNo", giftNo);
			}
			if(giftName != null){
				map.put("giftName", giftName);
			}
			
			
	        OutputStream ouputStream = null;
	        try {
	            // 设置输出响应头信息，
	            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("各仓库礼品统计").getBytes(), "ISO-8859-1")+ RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xlsx");
	            ouputStream = response.getOutputStream();
	            // 查询数据
	            List<GrdGdGiftDataDTO> list = grdGdGiftstoreToolService.getGrdGdGiftData(map);
	            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, GrdGdGiftDataDTO.class);
	            workBook.write(ouputStream);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            try {
	                ouputStream.flush();
	                ouputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	}
	
}
