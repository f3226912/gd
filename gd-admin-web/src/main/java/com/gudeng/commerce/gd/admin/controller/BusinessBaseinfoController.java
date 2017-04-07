package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.BusinessSteelyardInputDTO;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessPosToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessSteelyardToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("business")
public class BusinessBaseinfoController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(BusinessBaseinfoController.class);

	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;

	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	private  MarketManageService marketManageService;

	@Autowired
	private ProCategoryService proCategoryService;

	@Autowired
	private ReBusinessCategoryToolService reBusinessCategoryToolService;

	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;

	@Autowired
	private ReBusinessPosToolService reBusinessPosToolService;

	@Autowired
	private ReBusinessSteelyardToolService reBusinessSteelyardToolService;

	/**
	 * businessBaseinfo
	 * @return
	 */
	@RequestMapping("")
	public String businessBaseinfo(HttpServletRequest request){
		return "business/businessBaseinfo_list";
	}
	/**
	 * 分页查询
	 * @param request
	 * @return
	 */

	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			//记录数
			map.put("total", businessBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<BusinessBaseinfoDTO> listReturn=new ArrayList<>(); 
			List<BusinessBaseinfoDTO> list = businessBaseinfoToolService.getBySearch(map);
			for (BusinessBaseinfoDTO businessBaseinfoDTO:list) {
				if(businessBaseinfoDTO.getLevel()!=null && businessBaseinfoDTO.getLevel().intValue()==4){
					businessBaseinfoDTO.setMarketName("");
					//					businessBaseinfoDTO.setCateNames("");
				}
				listReturn.add(businessBaseinfoDTO);
			}			
			map.put("rows", listReturn);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletebyid" )
	@ResponseBody
	public String deleteById(@RequestParam String id, HttpServletRequest request){      
		try {
			if(null==id || id.equals("")){
				return "faild";
			}
			int i=businessBaseinfoToolService.deleteById(id);
			String returnStr="success";
			return returnStr;
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 增加修改同一个页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("addBusinessBaseinfoDto")
	public String addDto(HttpServletRequest request){
		return "business/addDto";
	}



	/**
	 * 	  增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save" )
	@ResponseBody
	public String saveOrUpdate(BusinessBaseinfoDTO dto, HttpServletRequest request){      
		try {
			if(null == dto.getShopsName() || dto.getShopsName().equals("")){
				return "shopsName";
			}
			if(null == dto.getBusinessModel() || dto.getBusinessModel().equals("")  || dto.getBusinessModel().equals("0")){
				return "model";
			}
			String level=request.getParameter("level");
			String [] zycategoryIds =null;
			String [] jycategoryIds =null;
			String areaType=request.getParameter("areaType");
			dto.setBunkCode(request.getParameter("bunkCode"));
			String offlineStatus=request.getParameter("offlineStatus");
			if(areaType!=null && areaType.equals("2")){
				dto.setManagementType(0);
			}
			if(level!=null && level.equals("4") && areaType!=null && areaType.equals("1")){
				if(dto.getManagementType()==null||dto.getManagementType().equals("")){
					return "managementType";
				}
			}
			Map map =new HashMap();
			map.put("businessId", dto.getBusinessId());
			int count= businessBaseinfoToolService.getTotal(map);
			int i=0;
			Long businessId=0L;
			if(count>0){//根据id判断是否存在，存在即为更新，不存在即为增加
				dto.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));//设置updateTime
				i=businessBaseinfoToolService.updateBusinessBaseinfoDTO(dto);
			}else{
				BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();
				bb.setName(dto.getName());
				bb.setShopsName(dto.getShopsName());
				bb.setBunkCode(dto.getBunkCode());
				bb.setOfflineStatus(Integer.valueOf(offlineStatus));//线下认证
				bb.setCreateTime(new Date());
				businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
				dto.setBusinessId(businessId);
			}
			String marketId=request.getParameter("marketId");
			zycategoryIds= request.getParameterValues("zycategoryId");
			jycategoryIds= request.getParameterValues("jycategoryId");


			//			if(null == zycategoryIds|| zycategoryIds.length==0){
			//				return "zycategoryId";
			//			}
			//			if(null == jycategoryIds|| jycategoryIds.length==0){
			//				return "jycategoryId";
			//			}
			if(null != level && level.equals("4")){
				marketId="3";
			}

			if(i==0){//新增商铺并新增关联市场
				ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
				rbm.setBusinessId(businessId);
				rbm.setMarketId(Long.valueOf(marketId));
				reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
			}else{//修改商铺时，删除原来所属市场，再写入所属市场
				reBusinessMarketToolService.deleteByBusinessId(dto.getBusinessId());
				ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
				rbm.setBusinessId(dto.getBusinessId());

				rbm.setMarketId(Long.valueOf(marketId));
				reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
			}

			if(i==0){//新增商铺并新增关联市场
			}else{//修改商铺时，删除原来所属市场，再写入所属市场
				reBusinessCategoryToolService.deleteByBusinessId(dto.getBusinessId());
			}

			if(null!=zycategoryIds){
				for(String cateId:zycategoryIds){
					ReBusinessCategoryDTO rbc=new ReBusinessCategoryDTO();
					rbc.setBusinessId(dto.getBusinessId());
					rbc.setCategoryId(Long.valueOf(cateId));
					rbc.setBusinessType("0");
					reBusinessCategoryToolService.addReBusinessCategoryDTO(rbc);
				}
			}
			if(null!=jycategoryIds){
				for(String cateId:jycategoryIds){
					ReBusinessCategoryDTO rbc=new ReBusinessCategoryDTO();
					rbc.setBusinessId(dto.getBusinessId());
					rbc.setCategoryId(Long.valueOf(cateId));
					rbc.setBusinessType("1");
					reBusinessCategoryToolService.addReBusinessCategoryDTO(rbc);
				}
			}

			if(i>0 || businessId != 0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit" )
	@ResponseBody
	public String edit( HttpServletRequest request) {     
		try {
			String id=request.getParameter("id");
			BusinessBaseinfoDTO dto=businessBaseinfoToolService.getById(id);
			putModel("dto",dto);
			return "business/businessBaseinfo_edit";
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 打开编辑页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editbyid/{businessId}")
	public String editbyid(@PathVariable("businessId") String businessId, HttpServletRequest request){      
		try {
			BusinessBaseinfoDTO dto=businessBaseinfoToolService.getById(businessId);
			MemberBaseinfoDTO mbdto= memberBaseinfoToolService.getById(String.valueOf(dto.getUserId()));

			List<MarketDTO> markets=null;
			Map mapM=new HashMap();
			if(mbdto.getLevel()!=null &&mbdto.getLevel()==4){
				markets=marketManageService.getAllByType("0");
			}else{
				markets=marketManageService.getAllByType("2");
			}
			for(MarketDTO market:markets){
				if((null!=dto && null !=dto.getBusinessId()) && market.getId().toString().equals(dto.getMarketId())) continue;
				List<ProductCategoryDTO> lsit = proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(market.getId()));
				mapM.put(market.getId(), lsit);
			}

			putModel("mapM", mapM); 
			putModel("markets", markets);

			Map map=new HashMap();
			map.put("businessId", dto.getBusinessId());
			map.put("startRow", 0);
			map.put("endRow", 200);

			if(null !=dto.getLevel() && dto.getLevel()!=4){

				//获取店铺店铺的经营分类
				List<ReBusinessCategoryDTO> listRBC = reBusinessCategoryToolService.getBySearch(map);
				//获取店铺所在市场的所有分类
				List<ProductCategoryDTO> lsitAll =proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(dto.getMarketId()));

				putModel("listRBC",listRBC);
				putModel("lsitAll",lsitAll);
			}else{
				//获取店铺店铺的经营分类
				List<ReBusinessCategoryDTO> listRBC = reBusinessCategoryToolService.getBySearch(map);
				//获取店铺所在市场的所有分类
				List<ProductCategoryDTO> lsitAll =proCategoryService.listTopProductCategoryByMarketId(3L);

				putModel("listRBC",listRBC);
				putModel("lsitAll",lsitAll);
			}

			putModel("mbdto",mbdto);
			putModel("dto",dto);

			ReBusinessMarketDTO rbmDto=reBusinessMarketToolService.getByBusinessId(dto.getBusinessId());
			if(rbmDto!=null){
				MarketDTO marketDTO=marketManageService.getById(String.valueOf(rbmDto.getMarketId()));
				putModel("mdto",marketDTO); 
			}
			List<ReBusinessPosDTO> list=reBusinessPosToolService.getNewReBusinessPosByBusinessId(Long.valueOf(businessId));
			if(list!=null &&list.size()>0){
				putModel("posNos",list); 
				putModel("posNosJSON",JSONObject.toJSONString(list,SerializerFeature.WriteDateUseDateFormat)); 
				putModel("posCount",list.size()); 
			}else{
				putModel("posNos",null); 
				putModel("posCount",0); 
			}

			List<ReBusinessSteelyardDTO> reBusinessSteelyardList=reBusinessSteelyardToolService.getReBusinessSteelyardByBusinessId(Long.valueOf(businessId));
			if(reBusinessSteelyardList!=null &&reBusinessSteelyardList.size()>0){
				putModel("steelyardList",reBusinessSteelyardList); 
				putModel("steelyardJSON",JSONObject.toJSONString(reBusinessSteelyardList,SerializerFeature.WriteDateUseDateFormat)); 
				putModel("steelyardCount",reBusinessSteelyardList.size()); 
			}else{
				putModel("steelyardList",null); 
				putModel("steelyardCount",0); 
			}

			return "business/businessBaseinfo_edit";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开查看页
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showbyid/{businessId}")
	public String showbyid(@PathVariable("businessId") String businessId, HttpServletRequest request){      
		try {

			BusinessBaseinfoDTO dto=businessBaseinfoToolService.getById(businessId);
			MemberBaseinfoDTO mbdto= memberBaseinfoToolService.getById(String.valueOf(dto.getUserId()));

			List<MarketDTO> markets=marketManageService.getAllByType("2");
			Map mapM=new HashMap();
			for(MarketDTO market:markets){
				if((null!=dto && null !=dto.getBusinessId()) && market.getId().toString().equals(dto.getMarketId())) continue;
				List<ProductCategoryDTO> lsit = proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(market.getId()));
				mapM.put(market.getId(), lsit);
			}
			putModel("mapM", mapM); 
			putModel("markets", markets);


			Map map=new HashMap();
			map.put("businessId", dto.getBusinessId());
			map.put("startRow", 0);
			map.put("endRow", 200);

			if(null !=dto.getLevel() && dto.getLevel()!=4){

				//获取店铺店铺的经营分类
				List<ReBusinessCategoryDTO> listRBC = reBusinessCategoryToolService.getBySearch(map);
				//获取店铺所在市场的所有分类
				List<ProductCategoryDTO> lsitAll =proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(dto.getMarketId()));

				putModel("listRBC",listRBC);
				putModel("lsitAll",lsitAll);
			}else{
				//获取店铺店铺的经营分类
				List<ReBusinessCategoryDTO> listRBC = reBusinessCategoryToolService.getBySearch(map);
				//获取店铺所在市场的所有分类
				List<ProductCategoryDTO> lsitAll =proCategoryService.listTopProductCategoryByMarketId(3L);

				putModel("listRBC",listRBC);
				putModel("lsitAll",lsitAll);
			}


			putModel("mbdto",mbdto);
			putModel("dto",dto);

			ReBusinessMarketDTO rbmDto=reBusinessMarketToolService.getByBusinessId(dto.getBusinessId());
			if(rbmDto!=null){
				MarketDTO marketDTO=marketManageService.getById(String.valueOf(rbmDto.getMarketId()));
				putModel("mdto",marketDTO); 
			}
			List<ReBusinessPosDTO> list=reBusinessPosToolService.getNewReBusinessPosByBusinessId(Long.valueOf(businessId));
			if(list!=null &&list.size()>0){
				putModel("posNos",list); 
				putModel("posNosJSON",JSONObject.toJSONString(list,SerializerFeature.WriteDateUseDateFormat)); 
				putModel("posCount",list.size()); 
			}else{
				putModel("posNos",null); 
				putModel("posCount",0); 
			}

			List<ReBusinessSteelyardDTO> reBusinessSteelyardList=reBusinessSteelyardToolService.getReBusinessSteelyardByBusinessId(Long.valueOf(businessId));
			if(reBusinessSteelyardList!=null &&reBusinessSteelyardList.size()>0){
				putModel("steelyardList",reBusinessSteelyardList); 
				putModel("steelyardJSON",JSONObject.toJSONString(reBusinessSteelyardList,SerializerFeature.WriteDateUseDateFormat)); 
				putModel("steelyardCount",reBusinessSteelyardList.size()); 
			}else{
				putModel("steelyardList",null); 
				putModel("steelyardCount",0); 
			}

			return "business/businessBaseinfo_show";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="querybysearch" )
	@ResponseBody
	public String querybysearch(BusinessBaseinfoDTO dto, HttpServletRequest request){      
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", dto.getBusinessId());
			//			map.put("name", dto.getName());
			map.put("shopsName", dto.getShopsName());
			//			map.put("startDate", request.getParameter("startDate"));
			//			map.put("endDate", request.getParameter("endDate"));
			map.put("level", request.getParameter("level"));
			map.put("marketId", request.getParameter("marketId"));
			//			map.put("mainProduct", request.getParameter("mainProduct"));
			map.put("account", request.getParameter("account"));
			map.put("posNumber", request.getParameter("posNumber"));
			map.put("mobile", request.getParameter("mobile"));
			String offlineStatus=request.getParameter("offlineStatus");
			if(StringUtil.isNotEmpty(offlineStatus)){
				map.put("offlineStatus",offlineStatus );
			}
			map.put("categoryID", request.getParameter("categoryID"));
			map.put("termType", request.getParameter("termType"));
			map.put("mobile", request.getParameter("mobile"));

			if(request.getParameter("provinceId")!=null && !request.getParameter("provinceId").equals("0")){
				map.put("provinceId", request.getParameter("provinceId"));
			}
			if(request.getParameter("cityId")!=null && !request.getParameter("cityId").equals("0")){
				map.put("cityId", request.getParameter("cityId"));
			}
			if(request.getParameter("areaId")!=null && !request.getParameter("areaId").equals("0")){
				map.put("areaId", request.getParameter("areaId"));
			}
			if(request.getParameter("mainCategoryId")!=null && !request.getParameter("mainCategoryId").equals("-1"))
				map.put("mainCategoryId", request.getParameter("mainCategoryId"));

			map.put("total", businessBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<BusinessBaseinfoDTO> listReturn=new ArrayList<>(); 
			List<BusinessBaseinfoDTO> list = businessBaseinfoToolService.getBySearch(map);
			for (BusinessBaseinfoDTO businessBaseinfoDTO:list) {
				if(businessBaseinfoDTO.getLevel()!=null && businessBaseinfoDTO.getLevel().intValue()==4){
					businessBaseinfoDTO.setMarketName("");
					//					businessBaseinfoDTO.setCateNames("");
				}
				listReturn.add(businessBaseinfoDTO);
			}
			map.put("rows", listReturn);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {

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
	public String checkExportParams(BusinessBaseinfoDTO dto, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", dto.getBusinessId());
			//			map.put("name", dto.getName());
			map.put("shopsName", dto.getShopsName());
			//			map.put("startDate", request.getParameter("startDate"));
			//			map.put("endDate", request.getParameter("endDate"));
			map.put("level", request.getParameter("level"));
			map.put("marketId", request.getParameter("marketId"));
			//			map.put("mainProduct", request.getParameter("mainProduct"));
			map.put("account", request.getParameter("account"));
			map.put("posNumber", request.getParameter("posNumber"));
			map.put("categoryID", request.getParameter("categoryID"));
			String offlineStatus=request.getParameter("offlineStatus");
			if(StringUtil.isNotEmpty(offlineStatus)){
				map.put("offlineStatus",offlineStatus );
			}
			map.put("termType", request.getParameter("termType"));


			if(request.getParameter("provinceId")!=null && !request.getParameter("provinceId").equals("0")){
				map.put("provinceId", request.getParameter("provinceId"));
			}
			if(request.getParameter("cityId")!=null && !request.getParameter("cityId").equals("0")){
				map.put("cityId", request.getParameter("cityId"));
			}
			if(request.getParameter("areaId")!=null && !request.getParameter("areaId").equals("0")){
				map.put("areaId", request.getParameter("areaId"));
			}
			map.put("address", request.getParameter("address"));

			int total = businessBaseinfoToolService.getTotal(map);
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

	/**
	 * @导出数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportdata")
	public void export(BusinessBaseinfoDTO dto, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessId", dto.getBusinessId());
		//		map.put("name", dto.getName());
		map.put("shopsName", dto.getShopsName());
		//		map.put("startDate", request.getParameter("startDate"));
		//		map.put("endDate", request.getParameter("endDate"));
		map.put("level", request.getParameter("level"));
		map.put("marketId", request.getParameter("marketId"));
		//		map.put("mainProduct", request.getParameter("mainProduct"));
		map.put("account", request.getParameter("account"));
		map.put("posNumber", request.getParameter("posNumber"));
		String offlineStatus=request.getParameter("offlineStatus");
		if(StringUtil.isNotEmpty(offlineStatus)){
			map.put("offlineStatus",offlineStatus );
		}
		map.put("termType", request.getParameter("termType"));

		map.put("categoryID", request.getParameter("categoryID"));


		if(request.getParameter("provinceId")!=null && !request.getParameter("provinceId").equals("0")){
			map.put("provinceId", request.getParameter("provinceId"));
		}
		if(request.getParameter("cityId")!=null && !request.getParameter("cityId").equals("0")){
			map.put("cityId", request.getParameter("cityId"));
		}
		if(request.getParameter("areaId")!=null && !request.getParameter("areaId").equals("0")){
			map.put("areaId", request.getParameter("areaId"));
		}
		map.put("address", request.getParameter("address"));

		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			//设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("店铺列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");	
			ouputStream = response.getOutputStream();
			//查询数据
			List<BusinessBaseinfoDTO> list = businessBaseinfoToolService.getBySearch(map);

			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			WritableSheet sheet = wwb.createSheet("店铺数据", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页

			Label label0 = new Label(0, 0, "公司名称");// 填充第一行第一个单元格的内容
			Label label1 = new Label(1, 0, "店铺名称");// 填充第一行第二个单元格的内容
			Label label2 = new Label(2, 0, "会员名称");// 填充第一行第三个单元格的内容
			Label label3 = new Label(3, 0, "开通时间");// 填充第一行第三个单元格的内容
			Label label4 = new Label(4, 0, "商户类型");
			Label label5 = new Label(5, 0, "所属市场");
			Label label6 = new Label(6, 0, "服务项目");
			Label label7 = new Label(7, 0, "终端号");
			Label label8 = new Label(8, 0, "线下认证");
			Label label9 = new Label(9, 0, "商铺地址");
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


			if(list != null){
				SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0, len = list.size(); i < len; i++) {
					BusinessBaseinfoDTO businessBaseinfoDTO = list.get(i);
					label0 = new Label(0, i+1, businessBaseinfoDTO.getName());// 填充第一行第一个单元格的内容
					label1 = new Label(1, i+1, businessBaseinfoDTO.getShopsName());// 填充第一行第二个单元格的内容
					label2 = new Label(2, i+1, businessBaseinfoDTO.getAccount());// 填充第一行第三个单元格的内容
					label3 = new Label(3, i+1, businessBaseinfoDTO.getCreateTime()==null?"":time.format(businessBaseinfoDTO.getCreateTime()));
					label4 = new Label(4, i+1, businessBaseinfoDTO.getLevel()==4?"产地供应商":"农批商");
					label5 = new Label(5, i+1, businessBaseinfoDTO.getLevel()==4?"":businessBaseinfoDTO.getMarketName());
					label6 = new Label(6, i+1, businessBaseinfoDTO.getLevel()==4?"":businessBaseinfoDTO.getCateNames());
					label7 = new Label(7, i+1, businessBaseinfoDTO.getPosNumber());
					label8 = new Label(8, i+1, (businessBaseinfoDTO.getOfflineStatus()!=null && businessBaseinfoDTO.getOfflineStatus()==1)?"已认证":"未认证");
					label9 = new Label(9, i+1, businessBaseinfoDTO.getShopsUrl());


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
					;
				}
			}
			wwb.write();
			wwb.close();
		}catch (Exception e1) {
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
	 * @导出数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="ajaxGetCategory/{marketId}")
	@ResponseBody
	public String ajaxGetCategory(@PathVariable("marketId") String marketId, HttpServletRequest request,HttpServletResponse response){  
		try {
			if(StringUtil.isEmpty(marketId)){
				return "";
			}
			List<ProductCategoryDTO> listCategory = proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
			return JSONObject.toJSONString(listCategory,SerializerFeature.WriteDateUseDateFormat);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 	  增加修改同一个方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="savePos" )
	@ResponseBody
	public String savePos(String type,String posNumber,String businessNo,Long businessId,String [] hasClears,String []updateData, HttpServletRequest request){      
		try {
			if (updateData.length>0) {
				Map<String,String> mapPara=new HashMap<>();
				mapPara.put("businessId", businessId.toString());
				for (int i = 0; i < updateData.length; i++) {
					String[] posNumUnion=updateData[i].split("@");
					/*if (posNumUnion.length==2) {
						mapPara.put("oriPosNum", posNumUnion[0]);
						mapPara.put("newPosNum", posNumUnion[1]);
						reBusinessPosToolService.updateByOriNewPosNum(mapPara);
					}else if (posNumUnion.length==1) {
						mapPara.put("oriPosNum", posNumUnion[0]);
						mapPara.put("state", "0");
						reBusinessPosToolService.updateByOriNewPosNum(mapPara);
					}*/
                    //不管用户是修改原有的pos名字还是置空，统一操作都是将原有pos修改为不可用,不再修改原有pos终端的终端号
					if (posNumUnion.length>=1) {
						mapPara.put("posNumber", posNumUnion[0]);
						mapPara.put("state", "0");
						reBusinessPosToolService.updateByOriNewPosNum(mapPara);
					}

				}
			}

			if(!StringUtil.isEmpty(posNumber)){
				String []types=type.split(",");
				//String []hasClears= hasClears.split(",");
				String []posNumbers=posNumber.split(",");
				for(int i=0;posNumbers!=null&&i<posNumbers.length;i++){
					ReBusinessPosEntity reBusinessPosEntity=new ReBusinessPosEntity();
					reBusinessPosEntity.setBusinessId(businessId);
					reBusinessPosEntity.setType(Integer.valueOf(types[i]));
					reBusinessPosEntity.setPosNumber(posNumbers[i]);
					reBusinessPosEntity.setCreatUserId(getUser(request).getUserID());
					reBusinessPosEntity.setUpdateUserId(getUser(request).getUserID());
					reBusinessPosEntity.setHasClear(hasClears[i]);
					reBusinessPosEntity.setCreateTime(new Date());
					reBusinessPosEntity.setState("1");
					reBusinessPosToolService.addReBusinessPosEntity(reBusinessPosEntity);
				}

			}

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	@RequestMapping(value="checkPosNumber" )
	@ResponseBody
	public String checkPosNumber(String posNumber,Long businessId, HttpServletRequest request){      
		try {
			if(!StringUtil.isEmpty(posNumber)){
				posNumber=posNumber.toLowerCase();
				BusinessBaseinfoDTO business=reBusinessPosToolService.getByPosDevNo(posNumber);
				if(business!=null&& business.getBusinessId().longValue()!=businessId.longValue()){
					return "Exist";
				}else{
					return "notExist";
				}
			}else{
				return "notExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}		
	}


	@RequestMapping(value="checkBusinessNo" )
	@ResponseBody
	public String checkBusinessNo(String businessNo,Long businessId, HttpServletRequest request){      
		try {
			if(businessNo!=null && !businessNo.equals("")){
				BusinessBaseinfoDTO business=businessBaseinfoToolService.getByBusinessNo(businessNo);
				if(business!=null && business.getBusinessId().longValue()!=businessId.longValue()){
					return "Exist";
				}else{
					return "notExist";
				}
			}else{
				return "notExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Exist";

		}		
	}

	@RequestMapping(value="saveSteelyard" )
	@ResponseBody
	public String saveSteelyard(HttpServletRequest request, BusinessSteelyardInputDTO inputDTO){      
		try {
			Long businessId = inputDTO.getBusinessId();
			reBusinessSteelyardToolService.deleteByBusinessId(businessId);
			reBusinessSteelyardToolService.addEntities(inputDTO);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("[ERROR]Save steelyard exception: ", e);
			return "failed";
		}
	}

	@RequestMapping(value="checkSteelyard" )
	@ResponseBody
	public String checkSteelyard(BusinessSteelyardInputDTO inputDTO, HttpServletRequest request){      
		try {
			Long businessId = reBusinessSteelyardToolService.getByMacAddr(inputDTO);
			if(businessId != null && inputDTO.getBusinessId().longValue() != businessId.longValue()){
				return "Exist";
			}else{
				return "notExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}		
	}

	@RequestMapping(value="delSteelyard" )
	@ResponseBody
	public String deleteSteelyard(BusinessSteelyardInputDTO inputDTO, HttpServletRequest request){      
		try {
			reBusinessSteelyardToolService.deleteById(inputDTO.getSteelyardId());
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}		
	}
}




