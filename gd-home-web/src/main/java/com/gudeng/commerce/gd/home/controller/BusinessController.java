package com.gudeng.commerce.gd.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.home.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.dto.ReCategoryBanelImgDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.FileUploadToolService;
import com.gudeng.commerce.gd.home.service.MarketToolService;
import com.gudeng.commerce.gd.home.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.ProCategoryToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.home.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.home.service.ReCategoryBanelImgToolService;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.home.util.CommonUtil;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.commerce.gd.home.util.StringUtil;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter/business")
public class BusinessController  extends HomeBaseController{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(StoreController.class);
	
	
	@Autowired
	public ProductToolService productToolService;
 
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public QueryAreaToolService queryAreaToolService;
	
	
	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;
	
	@Autowired
	public ReBusinessCategoryToolService reBusinessCategoryToolService;
	
	@Autowired
	public ProCategoryToolService proCategoryToolService;
	
	@Autowired
	public MarketToolService marketToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public ReCategoryBanelImgToolService reCategoryBanelImgToolService;

	@Autowired
	public FileUploadToolService fileUploadToolService;
	
	@RequestMapping("")
	public String businessShow(HttpServletRequest request){
		try { 
			
			//获取店铺实体对象
			BusinessBaseinfoDTO mbd=BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);
			
			MemberBaseinfoEntity mbe=getUser(request);
			MemberBaseinfoDTO member =memberBaseinfoToolService.getById(mbe.getMemberId().toString());
			this.putModel("level", member.getLevel());
//			BusinessBaseinfoDTO mbd= businessBaseinfoToolService.getByUserId(String.valueOf(mbe.getMemberId()));
			
			
			List<MarketDTO> markets=marketToolService.getAllByType("2");
			Map mapM=new HashMap();
			for(MarketDTO market:markets){
				if((null!=mbd && null !=mbd.getBusinessId()) && market.getId().toString().equals(mbd.getMarketId())) continue;
				List<ProductCategoryDTO> lsit = proCategoryToolService.listTopProductCategoryByMarketId(Long.valueOf(market.getId()));
				mapM.put(market.getId(), lsit);
			}
			
			putModel("mapM", mapM); 
			putModel("markets", markets);
			
			
			
			if(null==mbd || null==mbd.getBusinessId()){//未开通店铺
				putModel("market",markets.get(0));
			}else{
				Map map=new HashMap();
				map.put("businessId", mbd.getBusinessId());
				map.put("startRow", 0);
				map.put("endRow", 200);
				
				if(null !=mbe.getLevel()&& mbe.getLevel()!=4){
					//获取店铺店铺的经营分类
					List<ReBusinessCategoryDTO> listRBC = reBusinessCategoryToolService.getBySearch(map);
					//获取店铺所在市场的所有分类
					List<ProductCategoryDTO> lsitAll = proCategoryToolService.listTopProductCategoryByMarketId(Long.valueOf(mbd.getMarketId()));
					putModel("listRBC",listRBC);
					putModel("lsitAll",lsitAll);
				}
			

				putModel("dto",mbd);
				putModel("memberdto",member);
				
			}
			
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		
		return "usercenter/business/business_edit";
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
			
			if(StringUtil.isEmpty(dto.getShopsName())){
				return "shopsName";
			}
			if(StringUtil.isEmpty(String.valueOf(dto.getBusinessModel()))  || dto.getBusinessModel().equals("0")){
				return "model";
			}
			String level=request.getParameter("level");
			if(StringUtil.isEmpty(level)|| level.equals("0")){
				return "level";
			}
			
			String marketId=null;
			String [] categoryIds =null;
//			if(level.equals("1")){//谷登批发商
			if(!level.equals("4")){//谷登批发商
				marketId=request.getParameter("marketId");
				categoryIds= request.getParameterValues("categoryId");


				if(StringUtil.isEmpty(marketId)){
					return "marketId";
				}
				if(null == categoryIds|| categoryIds.length==0){
					return "categoryId";
				}
			}else{
				//产地供应商，直接设置marketId为3
				marketId="3";
			}
			
			
			
			MemberBaseinfoDTO mbe=getUser(request);
			MemberBaseinfoDTO mbd=new MemberBaseinfoDTO();
			mbd.setMemberId(mbe.getMemberId());
			mbd.setLevel(Integer.valueOf(level));
			Long businessId=0L;
			String bId=request.getParameter("businessId");
			
			int i=0;
			if(bId!=null && !bId.equals("")){//根据id判断是否存在，存在即为更新，不存在即为增加
				dto.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));//设置updateTime
				i=businessBaseinfoToolService.updateBusinessBaseinfoDTO(dto);
				businessId=Long.valueOf(bId);
			}else{
				if(BusinessUtil.isUserHaveBusiness(request, businessBaseinfoToolService)){
					//避免 ie 的后退，导致表单重复提交，产生多个店铺
					return "existBusiness";
				}
				BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();
				bb.setUserId(mbe.getMemberId());
				bb.setMainProduct(dto.getMainProduct());
				bb.setBusinessModel(dto.getBusinessModel());
				bb.setShopsDesc(dto.getShopsDesc());
				
				bb.setName(dto.getName());
				bb.setShopsName(dto.getShopsName());
				bb.setCreateTime(new Date());
				businessId=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
				
//				2015年11月30日16:49:05 经过讨论，不管开通店铺与否，角色俩来源不改变。
				
//				mbd.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//				memberBaseinfoToolService.updateMemberBaseinfoDTO(mbd);//新增时，需要考虑更改数据库中的用户的角色
//				
//				mbe.setLevel(Integer.valueOf(level));
//				request.getSession().setAttribute(Constant.SYSTEM_LOGINUSER, mbe);//新增时，需要考虑更session中的用户角色
				

				
			}
			
			if(i==0){//新增商铺并新增关联市场
			}else{//修改商铺时，删除原来所属市场，再写入所属市场
				reBusinessMarketToolService.deleteByBusinessId(dto.getBusinessId());
				reBusinessCategoryToolService.deleteByBusinessId(dto.getBusinessId());
			}
			
			if(!StringUtil.isEmpty(marketId)){
				ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
				rbm.setBusinessId(businessId);
				rbm.setMarketId(Long.valueOf(marketId));
				reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
			}
			
			if(null!=categoryIds){
				for(String cateId:categoryIds){
					ReBusinessCategoryDTO rbc=new ReBusinessCategoryDTO();
					rbc.setBusinessId(businessId);
					rbc.setCategoryId(Long.valueOf(cateId));
					reBusinessCategoryToolService.addReBusinessCategoryDTO(rbc);
				}
			}
			
			
			if(i>0 ){
				return "success";//修改店铺成功
			}if(businessId != 0){
				return "success1";//新增店铺成功
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	
	
	@RequestMapping(value="getCategorys/{marketId}") 
	@ResponseBody
  public String getCategorys(@PathVariable("marketId") String marketId, HttpServletRequest request){      
		try {
			List<ProductCategoryDTO> lsitAll = proCategoryToolService.listTopProductCategoryByMarketId(Long.valueOf(marketId));
			
			return JSONObject.toJSONString(lsitAll,SerializerFeature.WriteDateUseDateFormat);

		} catch (Exception e) {
			e.printStackTrace();
		}  

		
		return null;
	}
	
	/**
	 * 店铺装修
	 * 
	 * */
	@RequestMapping(value="decorate/{groupId}")
	public String decorate(@PathVariable("groupId") Long groupId, HttpServletRequest request){ 
		try { 
			//获取店铺实体对象
			BusinessBaseinfoDTO mbd=BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);
			
			
			
			
			
			List<Map<String, String>> appPicture = new ArrayList<Map<String, String>>();
			Map<String, String> pair = new HashMap<String, String>();
			
			
			Map<String,Object> mapb=new HashMap<>();
			mapb.put("businessId", mbd.getBusinessId());
			mapb.put("startRow", 0);
			mapb.put("endRow", 1);
			List<ReBusinessCategoryDTO> list=reBusinessCategoryToolService.getBySearch(mapb);
			if(null !=mbd.getBanelImgUrl() && !mbd.getBanelImgUrl().equals("")){
				pair.put("url", mbd.getBanelImgUrl());
			}else if(null !=list && list.size()>0){
				List<ReCategoryBanelImgDTO> rcbiDto=
						reCategoryBanelImgToolService.getByCategoryId(list.get(0).getCategoryId());
				if(rcbiDto!=null && rcbiDto.size()>0){
					pair.put("url", rcbiDto.get(0).getBanelImgUrl());
				}else {
					pair.put("url", "cate/templet/banner03.jpg");
				}
			}else {
				if(mbd.getLevel() !=null && mbd.getLevel()!=4) {
					pair.put("url", "cate/templet/banner01.jpg");
				} else {
					pair.put("url", "cate/templet/banner03.jpg");
				}
			}

			appPicture.add(pair);
			putModel("userImgUrl", JSONObject.toJSONString(appPicture)); 
			
			List<ReCategoryBanelImgDTO> groups=reCategoryBanelImgToolService.getAllGroups();
			
			Map map =new HashMap();
			if(groupId!=0 ){
				map.put("groupId",groupId);
				putModel("groupId",groupId);
			}
			putModel("groups",groups);
			
			
			// 获取总数 通过query提供的参数 此处有service层提供方法
			int size =reCategoryBanelImgToolService.getCount(map);

			/*
			 * 默认使用创建pageDTO对象 页大小=5
			 */
			PageDTO<ReCategoryBanelImgDTO> pageDto = PageUtil.create(ReCategoryBanelImgDTO.class, 6);

			// 设置总数据
			pageDto.setTotalSize(size);

			// 设置查询分页基本参数 查询前初始化数据
			setCommParameters(pageDto, map);

			// 获取数据库数据 并设置到pageDTO中
			pageDto.setPageData(reCategoryBanelImgToolService.getByAllByPage(map));

			// 数据添加到model 前台准备显示
			putPagedata(pageDto);
			/*
			 * pageDTO中数据
			 * getPageTotal 总页数 
			 * pageData 显示数据
			 */

		 
			
			
			
			return "usercenter/business/business_decorate";

		}catch (Exception e) {
			e.printStackTrace();
		}  
		return "usercenter/business/business_decorate";

	}
	

	@RequestMapping(value="getBanelImgByGroupId/{groupId}")
	@ResponseBody
    public String getBanelImgByGroupId(@PathVariable("groupId") Long groupId, HttpServletRequest request){      
		try {
			
		 
			List<ReCategoryBanelImgDTO> allBanel=reCategoryBanelImgToolService.getAllByGroupId(groupId);
			
			return JSONObject.toJSONString(allBanel);
			
		}catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
		
	}
	
	
	
	@RequestMapping(value="getBanelImgurl/{id}")
	@ResponseBody
    public String getBanelImgurl(@PathVariable("id") Integer id, HttpServletRequest request){      
		try { 
			ReCategoryBanelImgDTO reCategoryBanelImgDTO=reCategoryBanelImgToolService.getById(Long.valueOf(id));
			Long  businessId=BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
			
			BusinessBaseinfoDTO businessDto=new BusinessBaseinfoDTO();
			businessDto.setBusinessId(businessId);
			businessDto.setBanelImgUrl(reCategoryBanelImgDTO.getBanelImgUrl());
			
			int i=businessBaseinfoToolService.updateBusinessBaseinfoDTO(businessDto);
			if(i>0){
				Map map=new HashMap();
				map.put("url", reCategoryBanelImgDTO.getBanelImgUrl());
				return JSONObject.toJSONString(map);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
		
	}
	
	
	@ResponseBody
	@RequestMapping("uploadAttestPic")
	public String uploadAttestPic(
			HttpServletRequest request,
			@RequestParam(value = "attestPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file
						.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(
						file.getBytes(), fileName);
			} else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (IOException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}
		
		map.put("status", 1);
		map.put("message", "upload file success");
		map.put("url", masterPicPath);
		
		
		try {
			Long  businessId=BusinessUtil.getBusinessId(request, businessBaseinfoToolService);
			
			BusinessBaseinfoDTO businessDto=new BusinessBaseinfoDTO();
			businessDto.setBusinessId(businessId);
			businessDto.setBanelImgUrl(masterPicPath);
			int i=businessBaseinfoToolService.updateBusinessBaseinfoDTO(businessDto);
			
			String returnStr=JSONObject.toJSONString(map);
			if(i>0){
				return returnStr;
			}
		
			
		}catch (Exception e){
			e.printStackTrace();
		}
	return null;
	}
	
}
