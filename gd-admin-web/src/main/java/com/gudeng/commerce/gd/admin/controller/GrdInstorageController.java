package com.gudeng.commerce.gd.admin.controller;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.service.GrdInstorageToolService;
import com.gudeng.commerce.gd.admin.util.JSONUtils;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.GrdInstorageDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdInstoragedetailDTO;
import com.gudeng.commerce.gd.promotion.exception.GrdInstorageException;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.dba.exception.DalException;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class GrdInstorageController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdInstorageController.class);

	@Autowired
	private  GrdInstorageToolService grdInstorageToolService;
	
	@RequestMapping(value = "grdInstorage/save")
	@ResponseBody
	public String save(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String effectRow = request.getParameter("effectRow");
			String purchaseData = request.getParameter("purchaseData");
			//生成流水号
			String inStorageId = getinStorageId();
			GrdInstorageDTO dto = JSONUtils.parseObject(purchaseData, GrdInstorageDTO.class);
			SysRegisterUser userInfo = getUser(request);
			dto.setCreateUser(userInfo.getUserID());
			dto.setCreateUserName(userInfo.getUserName());
			dto.setUpdateUser(userInfo.getUserID());
			dto.setUpdateUserName(userInfo.getUserName());
			dto.setInStorageId(inStorageId);
			List<GrdInstoragedetailDTO> batchDetailDTO = JSONUtils.parseArray(effectRow, GrdInstoragedetailDTO.class); 
			grdInstorageToolService.insert(dto,batchDetailDTO);
			map.put("msg", "success");
		}catch (DalException e) {
			if(e.getCause() instanceof GrdInstorageException){
				GrdInstorageException ge=(GrdInstorageException) e.getCause();
				map.put("msg", ge.getMessage());
				logger.info(ge.getMessage());
				return  JSONObject.toJSONString(map);
			}
			map.put("msg", "保存失败!");
			logger.trace("保存失败", e);
		}catch (Exception e) {
			map.put("msg", "保存失败!");
			logger.trace("保存失败", e);
		}
		return  JSONObject.toJSONString(map);
	}
	/**
	 * 查询当前日期的条数生成流水号
	 * @return
	 */
	public String getinStorageId() throws Exception{
		Map<String, Object> map = new HashMap<>();
		int count = grdInstorageToolService.getTodayTotal(map);
		count = count+1;
		String storageId = new DecimalFormat("0000").format(count);
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
		storageId = format.format(new Date()) + storageId;
		return storageId;
	}
	
	
}
