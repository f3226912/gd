package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftToolService;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftstoreToolService;
import com.gudeng.commerce.gd.admin.service.GrdPurchaseToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.promotion.dto.GiftInstoreInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchaseDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdPurchasegiftDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchaseEntity;
import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class GrdPurchaseController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdPurchaseController.class);

	@Autowired
	private GrdPurchaseToolService grdPurchaseToolService;
	
	@Autowired
	private GrdGdGiftToolService grdGdGiftToolService;
	
	@Autowired
	private GrdGdGiftstoreToolService grdGdGiftstoreToolService;
	
	@Autowired
	private MarketManageService marketManageService;
	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdPurchase/main")
	public String list(HttpServletRequest request) {
		return "grdPurchase/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param marketId 所属市场
	 * @param warehouseId 所属仓库
	 * @param status 状态
	 * @param purchaseNO 采购单编号
	 * @return
	 * @author humy
	 */
	@RequestMapping("grdPurchase/query")
	@ResponseBody
	public String query(Integer marketId, Integer warehouseId, String status, String purchaseNO) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(marketId != null){
				map.put("marketId", marketId);
			}
			if(warehouseId != null){
				map.put("warehouseId", warehouseId);
			}
			if(StringUtils.isNotBlank(status)){
				map.put("status", status);
			}
			if(StringUtils.isNotBlank(purchaseNO)){
				map.put("purchaseNO", purchaseNO);
			}
			// 设置查询参数
			// 设定分页,排序
			setCommParameters(request, map);
			// 记录数
			map.put("total", grdPurchaseToolService.getTotal(map));

			// list
			List<GrdPurchaseDTO> list = grdPurchaseToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 保存数据（新增）
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "grdPurchase/save" },produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request, GrdPurchaseEntity entity, GrdPurchaseDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			GrdPurchaseEntity purchase = new GrdPurchaseEntity();
			purchase.setPurchaseNO(DateUtil.getDate(new Date(), "YYYYMMDD"));
			purchase.setPurchaser(request.getParameter("save_purchaser"));
			purchase.setMarketId(Integer.parseInt(request.getParameter("save_marketId")));
			purchase.setRemark(request.getParameter("save_remark"));
			purchase.setWarehouseId(Integer.parseInt(request.getParameter("save_warehouseId")));
			purchase.setStatus("1");
			purchase.setCreateTime(new Date());
			purchase.setCreateUser(user.getUserID());
			purchase.setCreateUserName(user.getUserName());
			purchase.setUpdateTime(new Date());
			purchase.setUpdateUser(user.getUserID());
			purchase.setUpdateUserName(user.getUserName());
			
			String giftNos = request.getParameter("seledgiftId");
			if(StringUtils.isBlank(giftNos)){
				map.put("msg", "请选择礼品");
				return JSONObject.toJSONString(map);
			}
			String[] giftNosArr = giftNos.split(",");
			
			map.put("ids", giftNos);
	        map.put(START_ROW, 0);
			map.put(END_ROW, 100);
			List<GrdGdGiftDTO> gifts = grdGdGiftToolService.getListPageByPurchase(map);
			if(giftNosArr.length!=gifts.size()){
				map.put("msg", "你选择的礼品有被删除的");
				return JSONObject.toJSONString(map);
			}
			
			List<GrdPurchasegiftEntity> giftList = new ArrayList<GrdPurchasegiftEntity>();
			for(String giftNo:giftNosArr){
				GrdPurchasegiftEntity gift = new GrdPurchasegiftEntity();
				gift.setGiftNO(giftNo);
				gift.setStatus("0");
				gift.setAmount(Double.parseDouble(request.getParameter("amount"+giftNo)));
				gift.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice"+giftNo)));
				gift.setCount(Integer.parseInt(request.getParameter("count"+giftNo)));
				giftList.add(gift);
			}
			
			
			int result = grdPurchaseToolService.add(purchase, giftList);

			if (result > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}
	/**
	 * 保存数据（修改）
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "grdPurchase/saveEdit" },produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveEdit(HttpServletRequest request, GrdPurchaseEntity entity, GrdPurchaseDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			
			String purchaseNo = request.getParameter("edit_purchaseNO");
			GrdPurchaseEntity purchase = new GrdPurchaseEntity();
			purchase.setPurchaseNO(DateUtil.getDate(new Date(), "YYYYMMDD"));
			purchase.setPurchaser(request.getParameter("edit_purchaser"));
			purchase.setMarketId(Integer.parseInt(request.getParameter("edit_marketId")));
			purchase.setRemark(request.getParameter("edit_remark"));
			purchase.setWarehouseId(Integer.parseInt(request.getParameter("edit_warehouseId")));
			purchase.setStatus("1");
			purchase.setUpdateTime(new Date());
			purchase.setUpdateUser(user.getUserID());
			purchase.setUpdateUserName(user.getUserName());
			purchase.setPurchaseNO(purchaseNo);
			
			String giftNos = request.getParameter("seledgiftId");
			if(StringUtils.isBlank(giftNos)){
				map.put("msg", "请选择礼品");
				return JSONObject.toJSONString(map);
			}
			
			String[] giftNosArr = giftNos.split(",");
			
			map.put("ids", giftNos);
	        map.put(START_ROW, 0);
			map.put(END_ROW, 100);
			List<GrdGdGiftDTO> gifts = grdGdGiftToolService.getListPageByPurchase(map);
			if(giftNosArr.length!=gifts.size()){
				map.put("msg", "你选择的礼品有被删除的");
				return JSONObject.toJSONString(map);
			}
			
			List<GrdPurchasegiftDTO> giftList = new ArrayList<GrdPurchasegiftDTO>();
			for(String giftNo:giftNosArr){
				GrdPurchasegiftDTO gift = new GrdPurchasegiftDTO();
				gift.setGiftNO(giftNo);
				gift.setPurchaseNO(purchaseNo);
				gift.setStatus("0");
				gift.setAmount(Double.parseDouble(request.getParameter("amount"+giftNo)));
				gift.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice"+giftNo)));
				gift.setCount(Integer.parseInt(request.getParameter("count"+giftNo)));
				String prototypePrice = StringUtils.isBlank(request.getParameter("prototypePrice"+giftNo))?"0":request.getParameter("prototypePrice"+giftNo);
				gift.setPrototypePrice(Double.parseDouble(prototypePrice));
				giftList.add(gift);
			}
			
			
			int result = grdPurchaseToolService.edit(purchase, giftList);
			
			if (result > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}


	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdPurchase/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "grdPurchase/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdPurchase/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdPurchaseDTO dto = grdPurchaseToolService.getById(id);
			mv.addObject("dto", dto);
			Map<String,Object> map = new HashMap<String,Object>();
			List<MarketDTO> list = marketManageService.getAllByType("2");
			mv.addObject("marketList", list);
			
			map.put("marketId", dto.getMarketId());
			// list
			List<GrdGdGiftstoreDTO> storeList = grdGdGiftstoreToolService.getList(map);
			mv.addObject("storeList", storeList);
			
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdPurchase/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdPurchase/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdPurchaseDTO dto = grdPurchaseToolService.getById(id);
			mv.addObject("dto", dto);
			Map<String,Object> map = new HashMap<String,Object>();
			List<MarketDTO> list = marketManageService.getAllByType("2");
			mv.addObject("marketList", list);
			
			map.put("marketId", dto.getMarketId());
			// list
			List<GrdGdGiftstoreDTO> storeList = grdGdGiftstoreToolService.getList(map);
			mv.addObject("storeList", storeList);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdPurchase/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdPurchase/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = grdPurchaseToolService.deleteBatch(list);
				map.put("msg", "success");
            }
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 根据id关闭
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdPurchase/close")
	@ResponseBody
	public String close(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "请选择采购单");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = grdPurchaseToolService.closeBatch(list);
				map.put("msg", "success");
            }
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}	

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "grdPurchase/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = grdPurchaseToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param marketId 所属市场
	 * @param warehouseId 所属仓库
	 * @param status 状态
	 * @param purchaseNO 采购单编号
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdPurchase/exportData")
	public String exportData(Integer marketId, Integer warehouseId, String status, 
			String purchaseNO, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(marketId != null){
			map.put("marketId", marketId);
		}
		if(warehouseId != null){
			map.put("warehouseId", warehouseId);
		}
		if(StringUtils.isNotBlank(status)){
			map.put("status", status);
		}
		if(StringUtils.isNotBlank(purchaseNO)){
			map.put("purchaseNO", purchaseNO);
		}
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName  = new String("采购单列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdPurchaseDTO> list = grdPurchaseToolService.getList(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("采购单列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "采购单编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "所属市场");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "所属仓库");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "订单总金额");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "订单创建时间");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "状态");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "采购申请人");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "备注");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						GrdPurchaseDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getPurchaseNO());
						Label label1 = new Label(1, i + 1, item.getMarketName());
						Label label2 = new Label(2, i + 1, item.getWarehouseName());
						Label label3 = new Label(3, i + 1, String.valueOf(item.getOrderamount()));
						Label label4 = new Label(4, i + 1, item.getCreateTime()==null?"":time.format(item.getCreateTime()));
						Label label5 = new Label(5, i + 1, item.getStatus() == "1" ? "待入库" : item.getStatus() == "2"?"入库中":"已关闭");
						Label label6 = new Label(6, i + 1, item.getPurchaser());
						Label label7 = new Label(7, i + 1, item.getRemark());

						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
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
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdPurchase/getPurchaseByStatusTotal")
	@ResponseBody
	public String queryPurchaseInfo(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List <GrdPurchaseDTO> totalList= grdPurchaseToolService.getPurchaseByStatusTotal(map);
			map.put("rows", totalList);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("queryPurchaseInfo查询错误", e);
		}
		return null;
	}
	/**
	 * 批次价格查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdPurchase/getPurchaseBatch/{giftNo}/{marketId}/{giftstoreId}")
	@ResponseBody
	public String getPurchaseBatch(HttpServletRequest request, @PathVariable("giftNo") String giftNo,@PathVariable("marketId") String marketId,@PathVariable("giftstoreId") String giftstoreId) {
		try { 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("giftNO", giftNo);
			map.put("marketId", marketId);
			map.put("warehouseId", giftstoreId);
			// 记录数
			map.put("total", grdPurchaseToolService.getPurchaseBatchTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			List<GrdPurchaseDTO> list = grdPurchaseToolService.getPurchaseBatch(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace("批次列表查询出错", e);
		}
		return null;
	}
	/**
	 * 导出采购单
	 * 
	 * @param marketId 所属市场
	 * @param warehouseId 所属仓库
	 * @param status 状态
	 * @param purchaseNO 采购单编号
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdPurchase/exportSingleData")
	public String exportSingleData(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(id != null){
			map.put("id", id);
		}
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName  = new String("采购单礼品列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdPurchasegiftDTO> list = grdPurchaseToolService.getPurchaseGiftList(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("采购单礼品列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "采购单编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "所属市场");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "仓库");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "申请人");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "礼品编码");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "礼品名称");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "单位");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "单价");// 填充第一行第七个单元格的内容
				Label label80 = new Label(8, 0, "数量");// 填充第一行第八个单元格的内容
				Label label90 = new Label(9, 0, "金额");// 填充第一行第九个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						GrdPurchasegiftDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getPurchaseNO());
						Label label1 = new Label(1, i + 1, item.getMarketName());
						Label label2 = new Label(2, i + 1, item.getWarehouseName());
						Label label3 = new Label(3, i + 1, item.getPurchaser());
						Label label4 = new Label(4, i + 1, item.getGiftNO());
						Label label5 = new Label(5, i + 1, item.getName());
						Label label6 = new Label(6, i + 1, item.getUnit());
						Label label7 = new Label(7, i + 1, String.valueOf(item.getUnitPrice()));
						Label label8 = new Label(8, i + 1, String.valueOf(item.getCount()));
						Label label9 = new Label(9, i + 1, String.valueOf(item.getAmount()));

						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
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
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdPurchase/queryPurchaseSelect")
	@ResponseBody
	public String queryPurchaseSelect(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", request.getParameter("marketId"));
			map.put("warehouseId", request.getParameter("warehouseId"));
			// 设置查询参数
			// 记录数
			//map.put("total", grdPurchaseToolService.getTotal(map));
			// 设定分页,排序
			//setCommParameters(request, map);
			// list
			List<GrdPurchaseDTO> list = grdPurchaseToolService.queryPurchaseSelect(map);
			if(CollectionUtils.isNotEmpty(list)){
				for(GrdPurchaseDTO grdPurchaseDTO :list){
					if(grdPurchaseDTO != null && grdPurchaseDTO.getMarketId() != null){
						MarketDTO marketDTO = marketManageService.getById(grdPurchaseDTO.getMarketId().toString());
						grdPurchaseDTO.setMarketName(marketDTO.getMarketName());
					}
				}
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	

	/**
	 * 打开采购选择页面
	 *
	 * @param request
	 * @return
	 * @author liufan
	 * @time 2016年8月16日 下午5:02:22
	 */
	@RequestMapping("grdPurchase/initPurchaseSelect")
	public ModelAndView purchaseSelect(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("option", "purchaseSelect");
		mv.setViewName("grdgift/purchaseSelect");
		return mv;
	}
	
	/**
	 * 打开礼品入库页面
	 *
	 * @param request
	 * @return
	 * @author liufan
	 * @time 2016年8月16日 下午5:02:22
	 */
	@RequestMapping("grdPurchase/initInstock/{purchaseNO}")
	public ModelAndView initInstock(HttpServletRequest request, @PathVariable("purchaseNO") String purchaseNO) {
		ModelAndView mv = new ModelAndView();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseNO", purchaseNO);
			List<GrdPurchaseDTO> list = grdPurchaseToolService.queryPurchaseSelect(map);
			if(CollectionUtils.isNotEmpty(list)){
				GrdPurchaseDTO grdPurchaseDTO = list.get(0);
				if(grdPurchaseDTO != null && grdPurchaseDTO.getMarketId() != null){
					MarketDTO marketDTO = marketManageService.getById(grdPurchaseDTO.getMarketId().toString());
					grdPurchaseDTO.setMarketName(marketDTO.getMarketName());
				}
				mv.addObject("grdPurchaseDTO", grdPurchaseDTO);
			}
		}catch (Exception e) {
			logger.trace("初使化礼品入库页面改出错", e);
		}
		mv.addObject("option", "instock");
		mv.setViewName("grdgift/instock");
		return mv;
	}
	
	
	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdPurchase/selgift")
	public String selGift(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "grdPurchase/selgift";
	}	
	

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdGdGift/queryGift")
	@ResponseBody
	public String queryGift(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String name = request.getParameter("name");
			String giftNo = request.getParameter("giftNo");
			String ids = request.getParameter("ids");
			String selected = request.getParameter("selected");
			// 设置查询参数
			// 记录数
			List<GrdGdGiftDTO> list;
			if(StringUtils.isNotBlank(selected)&&StringUtils.isBlank(ids)){
				map.put("giftname", name);
				map.put("giftNo", giftNo);
				map.put("total", 0);
				// 设定分页,排序
				setCommParameters(request, map);
				// list
				list = new ArrayList<GrdGdGiftDTO>();;
				map.put("rows", list);// rows键 存放每页记录 list
				return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);				
				
			}else{
				map.put("giftname", name);
				map.put("giftNo", giftNo);
				if(StringUtils.isNotBlank(ids)){
					map.put("ids", ids);
				}
				map.put("total", grdGdGiftToolService.getTotalByPurchase(map));
				// 设定分页,排序
				setCommParameters(request, map);
				// list
				list = grdGdGiftToolService.getListPageByPurchase(map);
				map.put("rows", list);// rows键 存放每页记录 list
				return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			}
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("grdGdGift/queryGiftByPno")
	@ResponseBody
	public String queryGiftByPno(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String purchaseNo = request.getParameter("purchaseNo");
			String ids = request.getParameter("ids");
			setCommParameters(request, map);
			if(StringUtils.isNotBlank(ids)){
				map.put("ids", ids);
				if(ids.equals("noid")){
					map.put("total", 0);
					// 设定分页,排序
					setCommParameters(request, map);
					// list
					List<GrdGdGiftDTO> list = new ArrayList<GrdGdGiftDTO>();;
					map.put("rows", list);// rows键 存放每页记录 list
					return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);	
				}
			}
			if(StringUtils.isNotBlank(purchaseNo)){
				map.put("purchaseNo", purchaseNo);
			}
			

			
			List<GrdGdGiftDTO> list = grdPurchaseToolService.getGiftByPurchaseNoList(map);
			map.put("total",grdPurchaseToolService.getGiftByPurchaseNoCount(map));
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}
	
	/**
	 * 采购单礼品列表查询
	 * 
	 * @param request
	 * @return
	 * @author liufan
	 */
	@RequestMapping("grdPurchase/queryPurchasegiftList")
	@ResponseBody
	public String queryPurchasegiftList(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseNO", request.getParameter("purchaseNO"));
			
			// 设置查询参数
			// 记录数
			map.put("total", grdPurchaseToolService.queryPurchasegiftListTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			map.put("startRow", 0);
			map.put("endRow", 999999);
			// list
			List<GrdPurchasegiftDTO> list = grdPurchaseToolService.queryPurchasegiftList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author 
	 */
	@RequestMapping("grdPurchase/instoreQuery")
	@ResponseBody
	public String instoreQuery(HttpServletRequest request) {
		try {
			
			String purchaseNo = request.getParameter("purchaseNo");
			
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("purchaseNo", purchaseNo);
			
			// 记录数
			map.put("total", grdPurchaseToolService.findGiftInstoreInfoCount(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GiftInstoreInfoDTO> list = grdPurchaseToolService.findGiftInstoreInfoList(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}	
	
	
	/**
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "grdPurchase/instoreQueryExport")
	public void instoreQueryExport(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			String purchaseNo = request.getParameter("purchaseNo");
			// 设置查询参数
			map.put("purchaseNo", purchaseNo);
			List<GiftInstoreInfoDTO> list = grdPurchaseToolService.findGiftInstoreInfoList(map);
			
			
			WritableWorkbook wwb = null;
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName =purchaseNo+ new String("采购单入库单明细".getBytes(), "ISO8859-1") ;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("采购单入库单明细", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "入库单号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "入库时间");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "入库人");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "仓库 ");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "礼品编码");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "礼品名称");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "单位");// 填充第一行第四个单元格的内容
				Label label70 = new Label(7, 0, "数量");// 填充第一行第五个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);// 将单元格加入表格
				sheet.addCell(label60);
				sheet.addCell(label70);

				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						GiftInstoreInfoDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getInStorageId());
						Label label1 = new Label(1, i + 1, dto.getCreateTime());
						Label label2 = new Label(2, i + 1, dto.getCreateUserName());
						Label label3 = new Label(3, i + 1,dto.getStore());
						Label label4 = new Label(4, i + 1, dto.getGiftNO());
						Label label5 = new Label(5, i + 1, dto.getName());
						Label label6 = new Label(6, i + 1, dto.getUnit());
						Label label7 = new Label(7, i + 1, dto.getInCount()==null?"0":dto.getInCount()+"");

						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭

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
	}
	
	
}
