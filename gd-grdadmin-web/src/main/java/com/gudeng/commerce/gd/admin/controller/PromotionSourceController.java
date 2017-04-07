package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ProductParamsBean;
import com.gudeng.commerce.gd.admin.entity.PromotionTypeEnum;
import com.gudeng.commerce.gd.admin.service.PromotionSourceToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.PromotionSourceDTO;
import com.gudeng.commerce.gd.customer.dto.PromotionStatisticsEntityDto;
import com.gudeng.commerce.gd.customer.dto.PromotionTypeDto;
import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionTypeEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionUrlEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("promotion")
public class PromotionSourceController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(PromotionSourceController.class);

	@Autowired
	public PromotionSourceToolService promotionSourceToolService;

	@RequestMapping("")
	public String promotion(HttpServletRequest request){
		return "promotion/promotion_list";
	}
	@RequestMapping("typeList")
	public String promotionTypeList(HttpServletRequest request){
		return "promotion/promotion_type_list";
	}
	@RequestMapping("typeAdd")
	public String promotionTypeAdd(HttpServletRequest request){
		return "promotion/promotion_type_add";
	}

	@RequestMapping("statisticsList")
	public String statisticsList(HttpServletRequest request){
		return "promotion/promotion_statistics";
	}

	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String name=request.getParameter("name");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			//记录数
			map.put("total", promotionSourceToolService.getTotal(name));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<PromotionSourceDTO> list = promotionSourceToolService.getByName(name);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("statistics")
	public String statistics(HttpServletRequest request, String name, String createTimeStart, String createTimeEnd){
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("createTimeStart", createTimeStart);
			map.put("createTimeEnd", createTimeEnd);
			//记录数
			map.put("total", promotionSourceToolService.getStatisticsTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<PromotionStatisticsEntityDto> list = promotionSourceToolService.statisticsGroupList(map);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletResponse response, HttpServletRequest request, String name, String createTimeStart, String createTimeEnd) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if (DateUtil.isDateIntervalOverFlow(createTimeStart, createTimeEnd, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			name = java.net.URLDecoder.decode(name, "UTF-8");
			map.put("name", name);
			map.put("createTimeStart", createTimeStart);
			map.put("createTimeEnd", createTimeEnd);
			int total =  promotionSourceToolService.getStatisticsAllCount(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}

	@RequestMapping("exportStatisticsList")
	public String exportStatisticsList(HttpServletResponse response, HttpServletRequest request, String name, String createTimeStart, String createTimeEnd){
		List<PromotionStatisticsEntityDto> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			name = java.net.URLDecoder.decode(name, "UTF-8");
			map.put("name", name);
			map.put("createTimeStart", createTimeStart);
			map.put("createTimeEnd", createTimeEnd);
			//记录数
			map.put("total", promotionSourceToolService.getStatisticsAllCount(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			list = promotionSourceToolService.statisticsListAll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("渠道统计明细列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("渠道统计明细列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "来源");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "类型");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "ip");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "创建时间");// 填充第一行第四个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						PromotionStatisticsEntityDto item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getName());
						Label label1 = new Label(1, i + 1, promotionSourceToolService.getTypeById(Long.valueOf(item.getType())).getName());
						Label label2 = new Label(2, i + 1, item.getIp());
						Label label3 = new Label(3, i + 1, item.getCreateTime()==null?"":time.format(item.getCreateTime()));

						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
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
	 * 增加修改同一个页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("addPromotion")
	public String addDto(HttpServletRequest request){
		return "promotion/promotion_edit";
	}

	@RequestMapping(value="save" )
	@ResponseBody
    public String saveOrUpdate(String name,Long id, HttpServletRequest request){
		try {
			if(null==id||id==0){
				PromotionSourceEntity promotionSourceEntity=new PromotionSourceEntity();
				promotionSourceEntity.setName(name);
				Long sourceId=promotionSourceToolService.addPromotionSourceEnt(promotionSourceEntity);
				if(null!=sourceId && sourceId>0){
					PromotionUrlEntity promotionUrlEntity = new PromotionUrlEntity();
					promotionUrlEntity.setSourceId(sourceId);
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("startRow", 0);
					params.put("endRow", Integer.MAX_VALUE);
					List<PromotionTypeEntity> list = promotionSourceToolService.getTypeInCondition(params);
					for (PromotionTypeEntity item : list){
						promotionUrlEntity.setType(item.getId().intValue());
						promotionUrlEntity.setUrl(item.getUrl() + "?sourceId="+sourceId+"&type="+ item.getId().intValue());
						promotionSourceToolService.addPromotionUrlEnt(promotionUrlEntity);
					}
//					promotionUrlEntity.setType(2);
//					promotionUrlEntity.setUrl("http://www.gdeng.cn/nsy/nsy.html?sourceId="+sourceId+"&type=2");
//					promotionSourceToolService.addPromotionUrlEnt(promotionUrlEntity);
				}
			}else{
				PromotionSourceDTO promotionSourceDTO=new PromotionSourceDTO();
				promotionSourceDTO.setId(id);
				promotionSourceDTO.setName(name);
				promotionSourceToolService.updatePromotionSourceDTO(promotionSourceDTO);
			}
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "faild";
	}


	@RequestMapping(value="deletebyid/{id}" )
	@ResponseBody
    public String deleteById(@PathVariable("id") Long id, HttpServletRequest request){
		try {
			if(null==id || id==0 ){
			return "faild";
			}
			int i=promotionSourceToolService.deleteById(id);
			int j=promotionSourceToolService.deletePromotionUrlBySourceId(id);
			if(i>0 && j>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {
			e.printStackTrace();
		}
		return "faild";
	}

	@RequestMapping(value="editbyid/{id}")
    public String editbyid(@PathVariable("id") Long id, HttpServletRequest request){
		try {
			PromotionSourceDTO dto=promotionSourceToolService.getById(id);


			putModel("dto",dto);
			return "promotion/promotion_edit";
		} catch (Exception e) {

		}
		return null;
	}


	@ResponseBody
	@RequestMapping("queryType")
	public String queryType(HttpServletRequest request){
		try {
			String name = request.getParameter("name");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			//记录数
			map.put("total", promotionSourceToolService.getTypeTotal(name));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<PromotionTypeEntity> list = promotionSourceToolService.getTypeInCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="editTypeById/{id}")
    public String editTypeById(@PathVariable("id") Long id, HttpServletRequest request){
		try {
			PromotionTypeEntity entity = promotionSourceToolService.getTypeById(id);
			putModel("entity",entity);
			return "promotion/promotion_type_edit";
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 新增类型信息
	 * @param id
	 * @param name
	 * @param url
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveTypeInfo")
    public String saveTypeInfo(Long id, String name, String url, HttpServletRequest request){
		try {
			SysRegisterUser user = getUser(request);
			PromotionTypeEntity entity = new PromotionTypeEntity();
			entity.setName(name);
			entity.setUrl(url);
			entity.setCreateTime(DateUtil.getNow());
			entity.setCreateUserId(user.getUserID());
			Long typeId = promotionSourceToolService.saveTypeInfo(entity);
			entity.setId(typeId);
			promotionSourceToolService.initialPromotionUrl(entity);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "faild";
	}
	/**
	 * 修改类型信息
	 * @param id
	 * @param name
	 * @param url
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveTypeModification")
    public String saveTypeModification(Long id, String name, String url, HttpServletRequest request){
		try {
			SysRegisterUser user = getUser(request);
			PromotionTypeDto dto = new PromotionTypeDto();
			dto.setId(id);
			dto.setName(name);
			dto.setUrl(url);
			dto.setUpdateTimeString(DateUtil.getSysDateTimeString());
			dto.setUpdateUserId(user.getUserID());
			promotionSourceToolService.saveTypeModification(dto);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "faild";
	}

	@ResponseBody
	@RequestMapping(value="deleteType/{id}")
    public String deleteType(@PathVariable("id") Long id, HttpServletRequest request){
		try {
			int result = promotionSourceToolService.deleteTypeById(id);
			Map map = new HashMap<>();
			map.put("status", result);
			return JSONObject.toJSONString(map);
		} catch (Exception e) {

		}
		return null;
	}
}
