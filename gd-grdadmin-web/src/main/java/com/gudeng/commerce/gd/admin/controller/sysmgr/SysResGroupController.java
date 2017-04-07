package com.gudeng.commerce.gd.admin.controller.sysmgr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysResGroupAdminService;
import com.gudeng.commerce.gd.admin.util.LoginUserUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysResGroup;
import com.gudeng.commerce.gd.authority.sysmgr.util.CommonConstant;
import com.gudeng.commerce.gd.authority.sysmgr.util.IdCreater;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 资源组controller类
 * @author wwj
 *
 */
@Controller
@RequestMapping("sysmgr")
public class SysResGroupController extends AdminBaseController{

	// 日志
	private GdLogger logger = GdLoggerFactory.getLogger(SysResGroupController.class);

	/** 资源组管理 Service */
	@Autowired
	private SysResGroupAdminService sysResGroupService;
	
	/**
	 * 系统资源组列表
	 * @author wwj
	 * @date 创建时间：2015年7月25日 上午9:46:57
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("sysGroup/list")
	public String list(HttpServletRequest request){
		
		return "sysmgr/sysgroup/grouplist";
	}

	/**
	 * 分页查询;
	 * 
	 * @param request
	 * @return path
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("sysGroup/query")
	@ResponseBody
	public String query(HttpServletRequest request){

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 条件参数
			map.put("resGroupName", StringUtils.trimToNull(request.getParameter("resGroupName")));
			//记录数
			map.put("total", sysResGroupService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<SysResGroup> list = sysResGroupService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			return getExceptionMessage(e,logger);
		}
	}

	/**
	 * 新增修改资源组- 页面初始化;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sysGroup/addInit")
	public String addInit(HttpServletRequest request) {

		/*try {
			SysmgrWebDTO web = new SysmgrWebDTO();
			String resGroupID = request.getParameter("resGroupID");
			if (StringUtils.isEmpty(resGroupID)) {
				return Constant.SYSRESGROUP_ADD_JSP;
			} else {
				SysResGroup sysResGroup = sysResGroupService.get(resGroupID);
				web.setSysResGroup(sysResGroup);
				request.setAttribute("web", web);
				return Constant.SYSRESGROUP_UPDATE_JSP;
			}
		} catch (Exception e) {
			request.setAttribute(Constant.COMMON_EXCEPTION_MESSAGE, e.getMessage());
		}*/
		//return Constant.SYSROLE_ADD_JSP;
		request.setAttribute("actionUrl", "add");
		return "sysmgr/sysgroup/groupedit";
	}

	/**
	 * 新增资源组
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sysGroup/add")
	@ResponseBody
	public String add(HttpServletRequest request){

		String message = "";
		try {
			// 取得页面的参数
			String resGroupName = StringUtils.trimToNull(request.getParameter("resGroupName"));
			String remark = StringUtils.trimToNull(request.getParameter("remark"));

			// 构造对象
			SysResGroup sysResGroup = new SysResGroup();
			sysResGroup.setResGroupID(IdCreater.newId());
			sysResGroup.setResGroupName(resGroupName);
			sysResGroup.setRemark(remark);
			sysResGroup.setCreateUserID(LoginUserUtil.getLoginUserId(request));

			message = sysResGroupService.insert(sysResGroup);
		} catch (Exception e) {
			return getExceptionMessage(e,logger);
		}
		return message;
	}

	/**
	 * 修改页面初始化
	 * @author wwj
	 * @date 创建时间：2015年7月25日 上午10:09:18
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("sysGroup/updateInit")
	public String updateInit(HttpServletRequest request) {

		String resGroupID = request.getParameter("resGroupID");
		try {
			SysResGroup sysResGroup = sysResGroupService.get(resGroupID);
			request.setAttribute("dto", sysResGroup);
			request.setAttribute("actionUrl", "update");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sysmgr/sysgroup/groupedit";
	}
	
	/**
	 * 修改资源组
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sysGroup/update")
	@ResponseBody
	public String update(HttpServletRequest request) {

		String message = "";
		try {
			// 取得页面的参数
			String resGroupID = StringUtils.trimToNull(request.getParameter("resGroupID"));
			String resGroupName = StringUtils.trimToNull(request.getParameter("resGroupName"));
			String remark = StringUtils.trimToNull(request.getParameter("remark"));

			// 构造对象
			SysResGroup sysResGroup = new SysResGroup();
			sysResGroup.setResGroupID(resGroupID);
			sysResGroup.setResGroupName(resGroupName);
			sysResGroup.setRemark(remark);
			sysResGroup.setUpdateUserID(LoginUserUtil.getLoginUserId(request));
			// 修改
			message = sysResGroupService.update(sysResGroup);
		} catch (Exception e) {
			return getExceptionMessage(e,logger);
		}
		return message;
	}

	/**
	 * 删除资源组;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sysGroup/delete")
	@ResponseBody
	public String delete(HttpServletRequest request){

		try {
			// 获取页面id集合
			String groupID = StringUtils.trimToNull(request.getParameter("groupIDs"));
			if (null != groupID) {
				String[] groupIDs = groupID.split(",");
				// 循环删除记录
				for (int i = 0; i < groupIDs.length; i++) {
					// 删除组
					sysResGroupService.delete(groupIDs[i]);
				}
			}
			return CommonConstant.COMMON_AJAX_SUCCESS;
		} catch (Exception e) {
			return getExceptionMessage(e,logger);
		}
	}

}
