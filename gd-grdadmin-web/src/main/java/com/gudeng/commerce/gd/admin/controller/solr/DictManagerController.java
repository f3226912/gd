package com.gudeng.commerce.gd.admin.controller.solr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.active.PromRuleFeeToolService;
import com.gudeng.commerce.gd.admin.util.FileUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("solr")
public class DictManagerController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(DictManagerController.class);
	
	@RequestMapping("manager")
	public String list(){
		return "solr/manager";
	}
	
	@RequestMapping("updateDict")
	@ResponseBody
	public String updateDict(HttpServletRequest request,String content){
//		FileUtil.contentToTxt("D:/ext.dic", content);
		FileUtil.contentToTxt("/usr/local/tomcat-solr/webapps/solr/WEB-INF/classes/ext.dic", content);
		return "success";
	}
	
	
	
//	
//	/**
//	 * 保存活动规则与费用信息
//	 * @param dto
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("addPromRuleFee")
//	@ResponseBody
//	public Map<String,Object> addPromRuleFee(PromBaseinfoDTO dto, HttpServletRequest request){
//		Map<String,Object> res = new  HashMap<>();
//		try {
//			logger.info("保存活动规则与费用信息入参:"+JSONObject.toJSONString(dto));
//			SysRegisterUser user = this.getUser(request);
//			dto.setCreateUserId(user.getUserID());
//			dto.setUpdateUserId(user.getUserID());
//			dto.setCreateTime(new Date());
//			dto.setUpdateTime(new Date());
//			promRuleFeeToolService.savePromRuleFee(dto);
//			res.put("status", 1);//1成功
//		} catch (Exception e) {
//			res.put("status", 2);//2失败
//			res.put("msg", e.getMessage());
//			logger.info(e.getMessage(), e);
//		}
//		return res;
//	}
//	
//	@RequestMapping("queryPromRuleFeeById")
//	@ResponseBody
//	public String queryPromRuleFeeById(HttpServletRequest request){
//		try{
//			String actId = request.getParameter("actId");
//			if(StringUtils.isEmpty(actId)){
//				return "{}";
//			}
//			PromBaseinfoDTO dto = promRuleFeeToolService.queryPromRuleFeeByActId(Integer.parseInt(actId));
//			return JSONObject.toJSONString(dto, SerializerFeature.WriteDateUseDateFormat);
//		}catch(Exception e){
//			logger.info("查询活动规则与费用异常",e);
//			Map<String, Object> res = new HashMap<>();
//			res.put("msg", e.getMessage());
//			return JSONObject.toJSONString(res, SerializerFeature.WriteDateUseDateFormat);
//		}
//	}
}
