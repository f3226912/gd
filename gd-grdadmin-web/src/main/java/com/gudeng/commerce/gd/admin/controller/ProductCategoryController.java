package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.RefCateSupNpsToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.RefCateSupNpsDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 功能描述：产品类别控制器
 */
@Controller
@RequestMapping("category")
public class ProductCategoryController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ProductCategoryController.class);

	public static final String PARAM_KEY_RETURN_MESSAGE = "returnMsg";

	@Autowired
	public ProCategoryService proCategoryService;
	
	@Autowired
	private FileUploadToolService fileUploadToolService;
	
	@Autowired
	private RefCateSupNpsToolService refCateService;
	
	@Autowired
	public MarketManageService marketManageService;
	
	@RequestMapping("list/{marketId}")
	public String list(@PathVariable String marketId) {
		try {
			if (null != marketId && !"".equals(marketId)) {
				MarketDTO dto = marketManageService.getById(marketId);
				putModel("marketDTO", dto);
				putModel("tree", getCategoryTreeStr(Long.valueOf(marketId)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "productCategory/categorylist";
	}

	/**
	 * 获取关联页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("refList")
	public String refList(HttpServletRequest request) {
		Long marketOfSupplier = 3L;
		try {
			String categoryId = request.getParameter("id");
			String marketId = request.getParameter("baseMarketId");

			
			MarketDTO dto = marketManageService.getById(marketId);
			putModel("marketDTO", dto);

			/*
			 * 获得对应农批商分类的供应商关联分类
			 */
			List<RefCateSupNpsDTO> refList = null;
			if (!StringUtil.isEmpty(categoryId)) {
				refList = refCateService.getRefCateSupNpsByNpsCateId(Long.parseLong(categoryId), "1");
			}

			/*
			 * 处理树结构 添加对应的农批商关联供应商分类， 用于初始化选择项
			 */
			putModel("tree", getCategoryTreeStrForChecked(marketOfSupplier, refList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "productCategory/refcate";
	}
	
	/**
	 * 获取基础分类一级
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("refBaseListlevel0")
	public String refBaseListlevel0(HttpServletRequest request) {
		Long marketOfBase = 0L;
		try {
			String categoryId = request.getParameter("id");
			String marketId = request.getParameter("baseMarketId");

			
			MarketDTO dto = marketManageService.getById(marketId);
			putModel("marketDTO", dto);
			ProductCategoryDTO pcDTO = proCategoryService.getProductCategoryById(Long.parseLong(categoryId));
			putModel("pcDTO", pcDTO);
			/*
			 * 获得对应农批商分类的供应商关联分类
			 */
			List<RefCateSupNpsDTO> refList = null;
			if (!StringUtil.isEmpty(categoryId)) {
				refList = refCateService.getRefCateSupNpsByNpsCateId(Long.parseLong(categoryId), "2");
			}

			/*
			 * 处理树结构 添加对应的农批商关联供应商分类， 用于初始化选择项
			 */
			putModel("tree", getCategoryTreeStrForCheckedLevel0(marketOfBase, refList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "productCategory/refbasecate-level0";
	}
	
	
	
	/**
	 * 获取基础分类关联页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("refBaseList")
	public String refBaseList(HttpServletRequest request) {
		Long marketOfBase = 0L;
		try {
			String categoryId = request.getParameter("id");
			String marketId = request.getParameter("baseMarketId");

			
			MarketDTO dto = marketManageService.getById(marketId);
			putModel("marketDTO", dto);
			
			ProductCategoryDTO pcDTO = proCategoryService.getProductCategoryById(Long.parseLong(categoryId));
			putModel("pcDTO", pcDTO);
			/*
			 * 获得对应农批商分类的供应商关联分类
			 */
			List<RefCateSupNpsDTO> refList = null;
			if (!StringUtil.isEmpty(categoryId)) {
				refList = refCateService.getRefCateSupNpsByNpsCateId(Long.parseLong(categoryId), "2");
				
				if(refList.size()>0) {
					putModel("refNodes", refList);
				}
			}

			/*
			 * 处理树结构 添加对应的农批商关联供应商分类， 用于初始化选择项
			 */
			putModel("tree", getBaseCategoryTreeStrForChecked(marketOfBase, refList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "productCategory/refbasecate";
	}

	/**
	 * 显示关联分类 功能描述：
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ref")
	public String refProductCategory(HttpServletRequest request) {
		try {
			/*
			 * 获取原始数据
			 */
			String npsCateid = request.getParameter("npsCateid"); // 农批商分类ID
			String marketId = request.getParameter("marketId"); // 市场ID
			String refCategoryIds = request.getParameter("refCategoryIds"); // 所有与之关联的供应商IDs
			String type = request.getParameter("type"); // 1 OR NULL: 农批商关联供应商，2: 农批商关联基础分类'

			String[] refIds = null;

			List<RefCateSupNpsDTO> refs = new ArrayList<>();
			SysRegisterUser user = this.getUser(request);
			
			if(!StringUtil.isEmpty(refCategoryIds)) {
				refIds = refCategoryIds.split(","); // 分解供应商IDs
			}
			else {
				refIds = new String[0];
				
				/*
				 * 添加单纯删除关联数据对象
				 * npsCategoryId 用于删除数据
				 * suppCategoryId=null 不添加任何关联
				 */
				RefCateSupNpsDTO ref = new RefCateSupNpsDTO();
				ref.setNpsCategoryId(Long.parseLong(npsCateid));
				ref.setType(type);
				refs.add(ref);
			}
			

			/*
			 * 遍历所有供应商ID， 组装后台service存储集合
			 */
			for (int i = 0; i < refIds.length; i++) {
				RefCateSupNpsDTO rcs = new RefCateSupNpsDTO();

				Long refId = Long.parseLong(refIds[i]);

				/*
				 * 组装数据
				 */
				rcs.setMarketId(Long.parseLong(marketId));
				rcs.setNpsCategoryId(Long.parseLong(npsCateid));
				rcs.setSuppCategoryId(refId);
				rcs.setCreateUserId(user.getUserID());
				rcs.setUpdateUserId(user.getUserID());
				rcs.setStatus("1");
				rcs.setType(type);
				rcs.setOrderNum(i);

				// 添加集合
				refs.add(rcs);
			}

			refCateService.updateRefCateSupNps(refs);

			return "true";
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "false";
		}

	}

	/**
	 * 显示新增分类表单 功能描述：
	 * 
	 * @param id
	 *            分类主键ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("edit")
	public String saveProductCategory(ProductCategoryEntity productCategoryEntity, HttpServletRequest request) {

		// 如果主键ID为空则新增数据,否则更新数据
		try {
			SysRegisterUser user = this.getUser(request);
			if (null == productCategoryEntity.getCategoryId()) {
				productCategoryEntity.setCreateUserId(user.getUserID());
				Long pid = proCategoryService.persistProductCategory(productCategoryEntity);
				return String.valueOf(pid);
			} else {

				productCategoryEntity.setUpdateUserId(user.getUserID());
				proCategoryService.updateProductCategory(productCategoryEntity);
				return "true";
			}

		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "false";
		}

	}

	@RequestMapping("showEdit/{categoryId}")
	public String showEdit(@PathVariable Long categoryId) {
		try {
			ProductCategoryDTO categoryDTO = null;
			if (categoryId == -1) {
				categoryDTO = new ProductCategoryDTO();
				categoryDTO.setMarketId(Long.parseLong(getRequest().getParameter("marketId")));
			} else {
				categoryDTO = proCategoryService.getProductCategoryById(categoryId);
			}
			putModel("category", categoryDTO);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "false";
		}

		return "productCategory/editcategory";
	}

	@ResponseBody
	@RequestMapping("delete")
	public String delProductCategory(ProductCategoryDTO productCategoryDTO) {

		try {

			String result = proCategoryService.deleteProductCategory(productCategoryDTO.getCategoryId());
			if (null == result) {
				return "true";
			} else {
				return StringUtil.changeCharset(result, "utf-8");
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "false";
		}
	}

	@ResponseBody
	@RequestMapping("queryOrderNum")
	public String queryOrderNum(HttpServletRequest request, HttpServletResponse response) {

		String categoryId = request.getParameter("categoryId");
		try {
			ProductCategoryDTO result = proCategoryService
					.getProductCategoryById(!StringUtil.isEmpty(categoryId) ? Long.parseLong(categoryId) : 0);
			if (null != result) {
				return String.valueOf(result.getOrderNum());
			} else {
				return "";
			}
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * @return
	 */
	private String getCategoryTreeStr(Long marketId) {
		// 所有分类树
		List<ProductCategoryDTO> categoryList = null;
		try {
			categoryList = proCategoryService.getProductCategoryByMarketId(marketId);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String result = "";
		
		if(marketId==0) {
			sb.append("[{id:\"-1\",pId:\"\",name:\"基础分类\",open:\"true\"},");
		} else {
			sb.append("[{id:\"-1\",pId:\"\",name:\"商品分类\",open:\"true\"},");
		}
		
		
		if (null != categoryList && categoryList.size() != 0) {
			for (ProductCategoryDTO d : categoryList) {
				sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
						+ d.getCateName() + "\"},");
			}
		}
		
		result = sb.toString();
		if (result.length() != 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result + "]";
	}

	/**
	 * 用于获取供应商一级列表，从redis中取出 农批商关联供应商所需接口，请勿修改 just for a only used
	 * 
	 * @return
	 */
	private String getCategoryTreeStrForChecked(Long marketId, List<RefCateSupNpsDTO> refList) {
		// 所有分类树
		List<ProductCategoryDTO> categoryList = null;
		try {
			categoryList = proCategoryService.listTopProductCategoryByMarketId(marketId);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String result = "";
		sb.append("[{id:\"-1\",pId:\"\",name:\"商品分类\",open:\"true\"},");
		if (null != categoryList && categoryList.size() != 0) {

			boolean isRef; // 标识符，是否已经被关联

			for (ProductCategoryDTO d : categoryList) {
				isRef = false;

				/*
				 * 循环遍历已经关联的供应商分类数据
				 */
				for (int i = 0; i < refList.size(); i++) {
					RefCateSupNpsDTO ref = refList.get(i);

					// ID相等 表示此供应商已被关联
					if (d.getCategoryId().equals(ref.getSuppCategoryId())) {
						isRef = true;
					}
				}

				/*
				 * 关联则添加数据时标识已被关联，复选框选择
				 */
				if (isRef)
					/*
					 * 设置对应的参数 cheked : true
					 */
					sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
							+ d.getCateName() + "\",open:\"false\",checked:true },");
				else
					sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
							+ d.getCateName() + "\",open:\"false\"},");
			}
		}
		result = sb.toString();
		if (result.length() != 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result + "]";
	}
	
	/**
	 * 用于获取基础分类一级列表，从redis中取出 农批商关联基础分类一级所需接口，请勿修改 just for a only used
	 * 
	 * @return
	 */
	private String getCategoryTreeStrForCheckedLevel0(Long marketId, List<RefCateSupNpsDTO> refList) {
		// 所有分类树
		List<ProductCategoryDTO> categoryList = null;
		try {
			categoryList = proCategoryService.listTopProductCategoryByMarketId(marketId);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String result = "";
		sb.append("[{id:\"-1\",pId:\"\",name:\"基础分类\",open:\"true\"},");
		if (null != categoryList && categoryList.size() != 0) {

			boolean isRef; // 标识符，是否已经被关联

			for (ProductCategoryDTO d : categoryList) {
				isRef = false;

				/*
				 * 循环遍历已经关联的供应商分类数据
				 */
				for (int i = 0; i < refList.size(); i++) {
					RefCateSupNpsDTO ref = refList.get(i);

					// ID相等 表示此供应商已被关联
					if (d.getCategoryId().equals(ref.getSuppCategoryId())) {
						isRef = true;
					}
				}

				/*
				 * 关联则添加数据时标识已被关联，复选框选择
				 */
				if (isRef)
					/*
					 * 设置对应的参数 cheked : true
					 */
					sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
							+ d.getCateName() + "\",open:\"false\",checked:true },");
				else
					sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
							+ d.getCateName() + "\",open:\"false\"},");
			}
		}
		result = sb.toString();
		if (result.length() != 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result + "]";
	}
	
	
	/**
	 * 用于获取基础分类所有列表，从redis中取出 农批商关联供应商所需接口，请勿修改 just for a only used
	 * 
	 * @return
	 */
	private String getBaseCategoryTreeStrForChecked(Long marketId, List<RefCateSupNpsDTO> refList) {
		// 所有分类树
		List<ProductCategoryDTO> categoryList = null;
		try {
			categoryList = proCategoryService.getProductCategoryByMarketId(marketId);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		String result = "";
		sb.append("[{id:\"-1\",pId:\"\",name:\"基础分类\",open:\"true\",nocheck:true},");
		if (null != categoryList && categoryList.size() != 0) {

			boolean isRef; // 标识符，是否已经被关联

			for (ProductCategoryDTO d : categoryList) {
				isRef = false;

				/*
				 * 循环遍历已经关联的基础分类数据
				 */
				for (int i = 0; i < refList.size(); i++) {
					RefCateSupNpsDTO ref = refList.get(i);

					// ID相等 表示此供应商已被关联
					if (d.getCategoryId().equals(ref.getSuppCategoryId())) {
						isRef = true;
					}
				}

				/*
				 * 关联则添加数据时标识已被关联，复选框选择
				 */
				if (isRef)
					/*
					 * 设置对应的参数 cheked : true
					 */
					sb.append("{tId:\"treeDemo_"+ d.getCategoryId() +"\",id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
							+ d.getCateName() + "\",isHidden:\"false\",checked:true },");
				else
					if(d.getCurLevel()==2)
						sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
								+ d.getCateName() + "\",open:\"false\"},");
					else
						sb.append("{id:\"" + d.getCategoryId() + "\"," + "pId:\"" + d.getParentId() + "\"," + "name:\""
								+ d.getCateName() + "\",open:\"false\",nocheck:true},");
			}
		}
		result = sb.toString();
		if (result.length() != 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result + "]";
	}

	@ResponseBody
	@RequestMapping("uploadCategoryPic")
	public String uploadProductPic(HttpServletRequest request,
			@RequestParam(value = "categoryPic", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
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
		return JSONObject.toJSONString(map);
	}

}
