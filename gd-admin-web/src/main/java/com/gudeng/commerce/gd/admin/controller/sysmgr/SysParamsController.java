package com.gudeng.commerce.gd.admin.controller.sysmgr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysParamsAdminService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysParams;

/**
 * 系统参数设定controller
 * @version 1.0
 */
@Controller
@RequestMapping("sysmgr")
public class SysParamsController extends AdminBaseController {

	
	/**系统参数设置SERVICE*/
	@Autowired
	public SysParamsAdminService sysParamsService;
	
	
	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2015-10-15
	 */
	@RequestMapping("/init")
	public String getList(HttpServletRequest request) throws Exception{
		
		/*try{
			Map<String, Object> map =new HashMap<String, Object>();
			
			//分页设置
			int recordCount = sysParamsService.getTotal();
			PageUtil page =PageUtil.turnPage(recordCount, map, request);
			web.setPage(page);
			//数据数目大于0，才查询数据
			if (recordCount > 0) {
				List<SysParams> sysParamsList = sysParamsService.getByCondition(map);
				web.setSysParamsList(sysParamsList);
			}
		}catch(Exception e){
			request.setAttribute(CommonConstant.COMMON_EXCEPTION_MESSAGE, e.getMessage());
		}*/
		return "";
	}
	
	/**
	 * 修改系统参数值
	 * @param request
	 * @return
	 * @throws CommException
	 */
	@RequestMapping("updateInit")
	public String get(HttpServletRequest request) throws Exception{
		
		/*try{
			SysmgrWebDTO web=new SysmgrWebDTO();
			//获取对象
			String sysParamsID=request.getParameter("sysParamsID");
			SysParams sysParams=sysParamsService.get(sysParamsID);
			//设置
			web.setSysParams(sysParams);
			request.setAttribute("web", web);
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		return  "";
	}

	/**
	 * 修改系统参数
	 * @param request
	 * @return "updateSuccess"  "udateFailure"
	 * @throws CommException
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpServletRequest request) throws Exception{
		
		//参数
		String sysParamsID=request.getParameter("sysParamsID");
		String paramsValue=request.getParameter("paramsValue");
		String paramsRemark=request.getParameter("paramsRemark");
		
		//设置
		SysParams sysParams=new SysParams();
		sysParams.setSysParamsID(sysParamsID);
		sysParams.setParamsValue(paramsValue);
		sysParams.setRemark(paramsRemark);
		String message=sysParamsService.update(sysParams);
		//返回
		return message;
	}
	
}

