package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ProductParamsBean;
import com.gudeng.commerce.gd.admin.service.FarmersMarketToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.service.PushAdInfoToolService;
import com.gudeng.commerce.gd.admin.service.PushNoticeToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("push")
public class PushController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberBaseinfoController.class);
	
	@Autowired
	public PushAdInfoToolService pushAdInfoToolService;
	@Autowired
	public PushNoticeToolService pushNoticeToolService;
	@Autowired
	public ProductToolService productToolService;
	@Autowired
	public FarmersMarketToolService farmersMarketToolService;
	@Autowired
	public MarketManageService marketManageService;
	@Autowired
	public FileUploadToolService fileUploadToolService;
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	@Autowired
	public ProCategoryService proCategoryService;
	
	/**
	 * 广告轮播管理列表页面
	 * @return
	 */
	@RequestMapping("adInfoList")
	public String adInfoList(HttpServletRequest request,ModelMap map){
		List<MarketDTO> list = new ArrayList<MarketDTO>();
		try {
			list = marketManageService.getAllByType("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("marketList", list);
		return "push/adinfo_list";
	}
	
	/**
	 * 广告轮播管理列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("adInfoQuery")
	@ResponseBody
	public String adInfoQuery(HttpServletRequest request) {
		try {
			String adName = request.getParameter("adName");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String adCanal = request.getParameter("adCanal");
			String adType = request.getParameter("adType");
			String state = request.getParameter("state");
			String marketId = request.getParameter("marketId");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (startDate != null && !"".equals(startDate)){
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
			}
			if (adName != null && !"".equals(adName)){
				map.put("adName", adName);
			}
			if (adCanal != null && !"".equals(adCanal)){
				map.put("adCanal", adCanal);
			}
			if (adType != null && !"".equals(adType)){
				map.put("adType", adType);
			}
			if (state != null && !"".equals(state)){
				map.put("state", state);
			}
			if (marketId != null && !"".equals(marketId)){
				map.put("marketId", marketId);
			}
			
			//获取条件记录总数
			map.put("total", pushAdInfoToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<PushAdInfoDTO> list = pushAdInfoToolService.getListByConditionPage(map);
			if(null != list && list.size() > 0){
				for(PushAdInfoDTO paid : list){
					if(null != paid && null != paid.getMarketId()){
						MarketDTO md = marketManageService.getById(paid.getMarketId());
						if(null != md){
							paid.setMarketId(md.getMarketName());
						}
					}
				}
			}
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 初始化添加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("adInfoAdd")
	public String adInfoAdd(ModelMap map){
		List<MarketDTO> list = new ArrayList<MarketDTO>();
		try {
			list = marketManageService.getAllByType("2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("marketList", list);
		return "push/adinfo_add";
	}
	
	/**
	 * 初始化编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("adInfoEditById/{id}")
	public String adInfoEditById(@PathVariable("id") String id, ModelMap map){
		try {
			PushAdInfoDTO pushAdInfoDTO = pushAdInfoToolService.getById(Long.valueOf(id));
			pushAdInfoDTO.setStartTimeStr(DateUtil.toString(pushAdInfoDTO.getStartTime(), DateUtil.DATE_FORMAT_DATETIME));
			pushAdInfoDTO.setEndTimeStr(DateUtil.toString(pushAdInfoDTO.getEndTime(), DateUtil.DATE_FORMAT_DATETIME));
			if(null != pushAdInfoDTO.getCategoryId()){
				ProductCategoryDTO pcdto1 = proCategoryService.getProductCategoryById(pushAdInfoDTO.getCategoryId());
				if(null != pcdto1 && null != pcdto1.getCategoryId()){
					if(pcdto1.getParentId() > 0){
						ProductCategoryDTO pcdto2 = proCategoryService.getProductCategoryById(pcdto1.getParentId());
						if(null != pcdto2 && null != pcdto2.getCategoryId()){
							if(pcdto2.getParentId() > 0){
								ProductCategoryDTO pcdto3 = proCategoryService.getProductCategoryById(pcdto2.getParentId());
								if(null != pcdto3 && null != pcdto3.getCategoryId()){
									pushAdInfoDTO.setCategoryId1(pcdto3.getCategoryId());
									pushAdInfoDTO.setCategoryId2(pcdto2.getCategoryId());
									pushAdInfoDTO.setCategoryId3(pcdto1.getCategoryId());
								}
							}else{
								pushAdInfoDTO.setCategoryId1(pcdto2.getCategoryId());
								pushAdInfoDTO.setCategoryId2(pcdto1.getCategoryId());
							}
						}
					}else{
						pushAdInfoDTO.setCategoryId1(pcdto1.getCategoryId());
					}
				}
			}
			map.put("dto", pushAdInfoDTO);
			List<MarketDTO> list = marketManageService.getAllByType("2");
			map.put("marketList", list);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "push/adinfo_edit";
	}
	
	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("adInfoDetailById/{id}")
	public String adInfoDetailById(@PathVariable("id") String id, ModelMap map){
		try {
			PushAdInfoDTO pushAdInfoDTO = pushAdInfoToolService.getById(Long.valueOf(id));
			pushAdInfoDTO.setStartTimeStr(DateUtil.toString(pushAdInfoDTO.getStartTime(), DateUtil.DATE_FORMAT_DATETIME));
			pushAdInfoDTO.setEndTimeStr(DateUtil.toString(pushAdInfoDTO.getEndTime(), DateUtil.DATE_FORMAT_DATETIME));
			map.put("dto", pushAdInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "push/adinfo_detail";
	}
	
	/**
	 * 	  提交更新数据检查排序
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping(value="adInfoSaveCheck" )
	@ResponseBody
	public String adInfoSaveCheck(String id,String adCanal,String adType,String sort,String marketId, HttpServletRequest request) {
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			if (sort != null && !"".equals(sort)){
				map.put("sort", sort);
			}
			if (adCanal != null && !"".equals(adCanal)){
				map.put("adCanal", adCanal);
			}
			if (adType != null && !"".equals(adType)){
				map.put("adType", adType);
			}
			if (marketId != null && !"".equals(marketId)){
				map.put("marketId", marketId);
			}
			//获取条件记录总数
			List<PushAdInfoDTO> dtolist = pushAdInfoToolService.getListByCondition(map);
			String res = "";
			if (dtolist.size() > 0) {
				if(null != id && !"".equals(id)){
					PushAdInfoDTO dto = (PushAdInfoDTO)dtolist.get(0);
					if(null != dto && null != dto.getId()){
						if(Long.valueOf(id).equals(dto.getId())){
							res = "success";
						}else{
							res = "failed";
						}
					}
				}else{
					res = "failed";
				}
			} else {
				res = "success";
			}
			String returnStr=JSONObject.toJSONString(res,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 	  提交新增数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="adInfoSaveAdd" )
	@ResponseBody
	public String adInfoSaveAdd(PushAdInfoEntity dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			dto.setCreateTime(new Date());
			dto.setCreateUserId(user.getUserID());
			dto.setCreateUserName(user.getUserName());
			if(null == dto.getStartTime()){
				dto.setStartTime(new Date());
			}
			if(null == dto.getEndTime()){
				dto.setEndTime(DateUtil.formateDate("2099-12-31 23:59:59", DateUtil.DATE_FORMAT_DATETIME));
			}
			Long i = 0l;
			i = pushAdInfoToolService.insertEntity(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 	  提交更新数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="adInfoSaveEdit" )
	@ResponseBody
	public String adInfoSaveEdit(PushAdInfoDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			String id = request.getParameter("id");
			dto.setId(Long.valueOf(id));
			dto.setUpdateUserId(user.getUserID());
			//dto.setUpdateUserId("1");
			dto.setUpdateUserName(user.getUserName());
			//dto.setUpdateTimeStr(DateUtil.getSysDateString());
			int i = 0;
			i = pushAdInfoToolService.updateDTO(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="adInfoDeleteById" )
	@ResponseBody
	public String adInfoDeleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			int i = pushAdInfoToolService.deleteById(Long.valueOf(id));
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据多个ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="adInfoDeletesById" )
	@ResponseBody
	public String adInfoDeletesById(@RequestParam String id, HttpServletRequest request) {
		try {
			String[] ids = id.split(",");
			for(String ss : ids){
				pushAdInfoToolService.deleteById(Long.valueOf(ss));
			}
			return "success";
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 公告管理列表页面
	 * @return
	 */
	@RequestMapping("noticeList")
	public String noticeList(HttpServletRequest request){
		return "push/notice_list";
	}
	
	/**
	 * 公告管理列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("noticeQuery")
	@ResponseBody
	public String noticeQuery(HttpServletRequest request) {
		try {
			String title = request.getParameter("title");
			/*String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");*/
			String client = request.getParameter("client");
			String state = request.getParameter("state");
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			/*if (startDate != null && !"".equals(startDate)){
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
			}
			if (endDate != null && !"".equals(endDate)){
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
			}*/
			if (title != null && !"".equals(title)){
				map.put("title", title);
			}
			if (client != null && !"".equals(client)){
				map.put("client", client);
			}
			if(state !=null && !"".equals(state)){
				map.put("state", state);
			}
			
			//获取条件记录总数
			map.put("total", pushNoticeToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<PushNoticeDTO> list = pushNoticeToolService.getListByConditionPage(map);
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 初始化添加页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("noticeAdd")
	public String noticeAdd(ModelMap map){
		return "push/notice_add";
	}
	
	/**
	 * 初始化编辑页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("noticeEditById/{id}")
	public String noticeEditById(@PathVariable("id") String id, ModelMap map){
		try {
			PushNoticeDTO pushNoticeDTO = pushNoticeToolService.getById(Long.valueOf(id));
			map.put("dto", pushNoticeDTO);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "push/notice_edit";
	}
	
	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("noticeDetailById/{id}")
	public String noticeDetailById(@PathVariable("id") String id, ModelMap map){
		try {
			PushNoticeDTO pushNoticeDTO = pushNoticeToolService.getById(Long.valueOf(id));
			pushNoticeDTO.setCreateTimeStr(DateUtil.toString(pushNoticeDTO.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME));
			map.put("dto", pushNoticeDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "push/notice_detail";
	}
	
	/**
	 * 	  提交新增数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="noticeSaveAdd" )
	@ResponseBody
	public String noticeSaveAdd(PushNoticeEntity dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			dto.setCreateTime(new Date());
			dto.setCreateUserId(user.getUserID());
			dto.setCreateUserName(user.getUserName());
			Long i = 0l;
			i = pushNoticeToolService.insertEntity(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 	  发送操作
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="noticeSaveEdit" )
	@ResponseBody
	public String noticeSaveEdit(PushNoticeDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			String id = request.getParameter("id");
			String state = request.getParameter("state");
			dto.setId(Long.valueOf(id));
			dto.setUpdateUserId(user.getUserID());
			//dto.setUpdateUserId("1");
			dto.setState(state);
			dto.setUpdateUserName(user.getUserName());
			//dto.setUpdateTimeStr(DateUtil.getSysDateString());
			int i = 0;
			i = pushNoticeToolService.updateDTO(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 	  提交编辑数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateNoticeInfo" )
	@ResponseBody
	public String updateNoticeInfo(PushNoticeDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser user = this.getUser(request);
			String id = request.getParameter("id");
			dto.setId(Long.valueOf(id));
			dto.setUpdateUserId(user.getUserID());
			//dto.setUpdateUserId("1");
			dto.setUpdateUserName(user.getUserName());
			//dto.setUpdateTimeStr(DateUtil.getSysDateString());
			int i = 0;
			i = pushNoticeToolService.updateNoticeInfo(dto);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return "failed";
	}
	
	/**
	 * 根据ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="noticeDeleteById" )
	@ResponseBody
	public String noticeDeleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			int i = pushNoticeToolService.deleteById(Long.valueOf(id));
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据多个ID进行删除操作
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="noticeDeletesById" )
	@ResponseBody
	public String noticeDeletesById(@RequestParam String id, HttpServletRequest request) {
		try {
			String[] ids = id.split(",");
			for(String ss : ids){
				pushNoticeToolService.deleteById(Long.valueOf(ss));
			}
			return "success";
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("getMarketList")
	public String getMarketList(){
		List<MarketDTO> list = null;
		try {
			list = marketManageService.getAllByType("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	@RequestMapping("marketSelect")
	public String marketSelect(){
		return "push/marketSelectList";
	}
	
	/**
	 * 跳转商品列表页
	 * @return
	 */
	@RequestMapping("proInitList")
	public String proInitList(HttpServletRequest request,ModelMap map){
		String categoryId = request.getParameter("categoryId");
		map.put("categoryId", categoryId);
		String selectType = request.getParameter("selectType");
		map.put("selectType", selectType);
		return "push/productSelectList";
	}
	
	
	/**
	 *  分页查询产品列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getProductList")
	public String getProductList(ProductParamsBean params,HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(params.getStartDate())) {
				map.put("startDate", CommonUtil.getStartOfDay(DateUtil
						.formateDate(params.getStartDate())));
			}
			if (StringUtil.isNotEmpty(params.getEndDate())) {
				map.put("endDate", CommonUtil.getEndOfDay(DateUtil
						.formateDate(params.getEndDate())));
			}
			if (StringUtil.isNotEmpty(params.getProductName())) {
				map.put("productName", params.getProductName());
			}
			if (StringUtil.isNotEmpty(params.getCategoryId())) {
				map.put("categoryId", params.getCategoryId());
			}
			if (StringUtil.isNotEmpty(params.getAccount())) {
				map.put("account", params.getAccount());
			}
			map.put("state", "3");
			
			//记录数
			map.put("total", productToolService.getCount(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<ProductDto> list = productToolService.getProductList(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  上传图片
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadProductPic")
	public String uploadProductPic(HttpServletRequest request, @RequestParam(value = "productPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()){
				String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
			}else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e1) {
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (IOException e1) {
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}
		map.put("status", 1);
		map.put("message", masterPicPath);
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}
}
