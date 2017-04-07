package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.ActGiftBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 活动礼品库
 * @author dengjianfeng
 *
 */
@Controller
@RequestMapping("actGift")
public class ActGiftBaseinfoController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(ActGiftBaseinfoController.class);
	
	@Resource
	private ActGiftBaseinfoToolService actGiftBaseinfoToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public String initList(){
		return "actGift/actGift_list";
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchMapParams(request);
			//总记录数
			Integer total = actGiftBaseinfoToolService.getTotalCountByCondition(map);
			resultMap.put("total", total);
			//查询列表数据
			setCommParameters(request, map);
			List<ActGiftBaseinfoDTO> list = actGiftBaseinfoToolService.queryPageByCondition(map);
			for(ActGiftBaseinfoDTO giftDto : list){
				SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(giftDto.getCreateUserId());
				giftDto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询活动礼品列表异常", e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}
	
	@RequestMapping("add")
	public String add(){
		return "actGift/actGift_add";
	}
	
	@RequestMapping("saveAdd")
	@ResponseBody
	public String saveAdd(ActGiftBaseinfoDTO dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//根据名称查询礼品是否存在
			boolean exist = actGiftBaseinfoToolService.exist(dto.getName());
			if(exist){
				resultMap.put("status", -1);
				resultMap.put("msg", "礼品名称不允许重复");
				return JSONObject.toJSONString(resultMap);
			}
			
			ActGiftBaseinfoEntity entity = new ActGiftBaseinfoEntity();
			entity.setName(dto.getName());
			entity.setStockTotal(dto.getStockTotal());
			entity.setStockAvailable(dto.getStockTotal());
			entity.setCreateUserId(getUser(request) == null ? null : getUser(request).getUserID());
			entity.setCreateTime(new Date());
			actGiftBaseinfoToolService.add(entity);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("新增活动礼品异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "新增活动礼品失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable("id") Integer id){
		try{
			ActGiftBaseinfoDTO dto = actGiftBaseinfoToolService.getById(id);
			putModel("dto", dto);
		}catch(Exception e){
			logger.info("编辑时查询活动礼品信息异常", e);
		}
		return "actGift/actGift_edit";
	}
	
	@RequestMapping("saveEdit")
	@ResponseBody
	public String saveEdit(ActGiftBaseinfoDTO dto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ActGiftBaseinfoDTO oldDTO = actGiftBaseinfoToolService.getById(dto.getId());
			if(oldDTO == null){
				resultMap.put("status", -1);
				resultMap.put("msg", "编辑礼品不存在");
				return JSONObject.toJSONString(resultMap);
			}
			//根据名称查询礼品是否存在
			if(!oldDTO.getName().equals(dto.getName())){
				boolean exist = actGiftBaseinfoToolService.exist(dto.getName());
				if(exist){
					resultMap.put("status", -1);
					resultMap.put("msg", "礼品名称不允许重复");
					return JSONObject.toJSONString(resultMap);
				}
			}
			
			//判断礼品库存
			if(dto.getStockTotal() < oldDTO.getStockTotal()){
				resultMap.put("status", -1);
				resultMap.put("msg", "修改后的当前库存不能小于修改前的当前库存");
				return JSONObject.toJSONString(resultMap);
			}
	
			ActGiftBaseinfoEntity entity = new ActGiftBaseinfoEntity();
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setStockTotal(dto.getStockTotal());
			//修改后的可用库存=原来可用库存+修改前后库存的相差值
			int differStock = dto.getStockTotal() - oldDTO.getStockTotal();
			int stockAvailable = oldDTO.getStockAvailable() + differStock;
			entity.setStockAvailable(stockAvailable);
			entity.setUpdateUserId(getUser(request) == null ? null : getUser(request).getUserID());
			actGiftBaseinfoToolService.update(entity);
			
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("修改活动礼品信息异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "修改活动礼品信息失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value="delete/{id}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String delete(@PathVariable("id")Integer id, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			int count = actGiftBaseinfoToolService.getActivityUseGiftCount(id);
			if(count > 0){
				resultMap.put("status", -1);
				resultMap.put("msg", "删除失败，存在还在使用该礼品的活动！");
				return JSONObject.toJSONString(resultMap);
			}
			
			ActGiftBaseinfoDTO dto = new ActGiftBaseinfoDTO();
			dto.setId(id);
			SysRegisterUser sysRegisterUser = getUser(request);
			dto.setUpdateUserId(sysRegisterUser == null ? null : sysRegisterUser.getUserID());
			actGiftBaseinfoToolService.delete(dto);
			resultMap.put("status", 0);
			resultMap.put("msg", "success");
		}catch(Exception e){
			logger.info("删除礼品异常", e);
			resultMap.put("status", -1);
			resultMap.put("msg", "删除礼品失败");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping("view/{id}")
	public String view(@PathVariable("id") Integer id){
		try{
			ActGiftBaseinfoDTO dto = actGiftBaseinfoToolService.getById(id);
			if(dto != null){
				SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
				dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
			}
			putModel("dto", dto);
		}catch(Exception e){
			logger.info("查询礼品信息异常", e);
		}
		return "actGift/actGift_view";
	}
	
	@RequestMapping(value="checkExportParams",produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkExportParams(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
			resultMap.put("status", 0);
			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
			return JSONObject.toJSONString(resultMap);
		}
		try{
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = actGiftBaseinfoToolService.getTotalCountByCondition(map);
			if (total != null && total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("礼品导出参数验证异常", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response){
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<ActGiftBaseinfoDTO> list = null;
		try{
			Map<String, Object> map = getSearchMapParams(request);
			list = actGiftBaseinfoToolService.getListByCondition(map);
			if(list != null){
				for(ActGiftBaseinfoDTO giftDto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(giftDto.getCreateUserId());
					giftDto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
				}
			}
		}catch(Exception e){
			logger.info("导出礼品数据异常", e);
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("活动礼品列表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("礼品列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "礼品名称");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "当前库存 ");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "可用库存 ");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "创建时间");// 填充第一行第五个单元格的内容
				Label label40 = new Label(4, 0, "添加人");// 填充第一行第七个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, len = list.size(); i < len; i++) {
						ActGiftBaseinfoDTO dto = list.get(i);
						Label label0 = new Label(0, i + 1, dto.getName());
						Label label1 = new Label(1, i + 1, dto.getStockTotal()+"");
						Label label2 = new Label(2, i + 1, dto.getStockAvailable()+"");
						Label label3 = new Label(3, i + 1, DateUtil.getDate(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label4 = new Label(4, i + 1, dto.getCreateUserName());
					
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
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
	
	/**
	 * 查询参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
		}
		return map;
	}
}
