package com.gudeng.commerce.gd.admin.controller.active;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.active.PromBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.Constant;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 活动（新） 菜单：产销对接活动管理
 * @author sss
 *
 */
@Controller
@RequestMapping("active")
public class PromBaseinfoController extends AdminBaseController{

	private final GdLogger logger = GdLoggerFactory.getLogger(PromBaseinfoController.class);
	
	@Resource
	private PromBaseinfoToolService activeBaseinfoToolService;
	
	@Resource
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("active/list");
		//是否有修改权限
		Map<String,String> map = (Map<String, String>) request.getSession().getAttribute(Constant.SYSTEM_BUTTONCODE);
		if(map.get("BTNHDCXDJ02") != null){//产销对接活动修改权限 
			mv.addObject("canEdit", 1);
		} else {
			mv.addObject("canEdit", 2);//不能修改
		}
		return mv;
	}
	
	@RequestMapping("toPromBaseinfo")
	public String toPromBaseinfo(){
		return "active/activeBaseinfo";
	}
	
	@RequestMapping("toPromBaseDetail")
	public ModelAndView toPromBaseDetail(@RequestParam(required=false) String actId){
		ModelAndView mv= new ModelAndView();
		mv.addObject("actId", actId);
		if(StringUtils.isEmpty(actId)){
			mv.addObject("flag", 1);//新增
		}else {
 			mv.addObject("flag", 2);//修改
		}
		mv.setViewName("active/activeDetail");
		return mv;
	}
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getSearchMapParams(request);
			Integer total = activeBaseinfoToolService.getTotalCountByCondition(map);
			resultMap.put("total", total);
			
			setCommParameters(request, map);
			List<PromBaseinfoDTO> list = activeBaseinfoToolService.queryPageByCondition(map);

			for(PromBaseinfoDTO dto : list){
				SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
				dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
			}
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.info("查询活动列表异常",e);
		}
		return JSONObject.toJSONString(resultMap, SerializerFeature.WriteDateUseDateFormat);
	}

	@RequestMapping("exportActive")
	public String exportActive(HttpServletRequest request, HttpServletResponse response){
		OutputStream ouputStream = null;
		try {
			Map<String, Object> map = getSearchMapParams(request);
				map.put(START_ROW, 0);
				map.put(END_ROW, 9999999);//百万级
				List<PromBaseinfoDTO> list = activeBaseinfoToolService.queryPageByCondition(map);

				for(PromBaseinfoDTO dto : list){
					SysRegisterUser sysRegisterUser = sysRegisterUserAdminService.get(dto.getCreateUserId());
					dto.setCreateUserName(sysRegisterUser == null ? null : sysRegisterUser.getUserName());
					//处理活动日期  暂时使用url字段来存储
					dto.setUrl(DateUtil.getDate(dto.getStartTime(), "yyyy-MM-dd")+" ~ "+DateUtil.getDate(dto.getEndTime(), "yyyy-MM-dd"));
				}
				String[][] headss ={{"活动名称","name","java.lang.String"}
									,{"活动日期","url","java.lang.String"}
									,{"活动创建人","createUserName","java.lang.String"}
									,{"活动创建日期","createTime","java.util.Date"}
									,{"所属市场","marketNames","java.lang.String"}
									};
				List<Map<String, Object>> ms = ExcelUtil.convertExportHeader(headss);
				
				// 设置输出响应头信息，
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				String fileName = null;
				String agent = request.getHeader("USER-AGENT");
	            if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
	                fileName = URLEncoder.encode("活动列表", "UTF-8");
	            } else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
	            	fileName = new String("活动列表".getBytes("UTF-8"), "ISO-8859-1");
	            }
	            String suffix = DateUtil.getDate(new Date(), "yyyy-MM-dd");
	            response.setHeader("Content-disposition", "attachment;filename=" + fileName +suffix + ".xlsx");
				ouputStream = response.getOutputStream();
				XSSFWorkbook book = ExcelUtil.buildXSLXExcel(ms, list);

				book.write(ouputStream);
			
		} catch (Exception e) {

			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("queryBaseInfoById")
	@ResponseBody
	public String queryBaseInfoById(HttpServletRequest request){
		try{
			String actId = request.getParameter("actId");
			if(StringUtils.isEmpty(actId)){
				return "{}";
			}
			PromBaseinfoDTO dto = activeBaseinfoToolService.queryPromBaseinfoById(Integer.parseInt(actId));
			return JSONObject.toJSONString(dto, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.info("查询活动列表异常",e);
			Map<String, Object> res = new HashMap<>();
			res.put("msg", e.getMessage());
			return JSONObject.toJSONString(res, SerializerFeature.WriteDateUseDateFormat);
		}
	}
	
	/**
	 * 保存活动基本信息
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("addPromBaseinfo")
	@ResponseBody
	public String add(PromBaseinfoDTO dto, HttpServletRequest request){
		Map<String,Object> res = new  HashMap<>();
		try {
			logger.info("保存活动基本信息入参:"+JSONObject.toJSONString(dto));
			SysRegisterUser user = this.getUser(request);
			dto.setType("1");//产销
			dto.setStatus("1");//1 正常 2取消
			dto.setVersion(1);
			dto.setIsLastest("1");//1 新 0 旧
			dto.setCreateUserId(user.getUserID());
			dto.setUpdateUserId(user.getUserID());
			dto.setCreateTime(new Date());
			dto.setUpdateTime(new Date());
			handlePicture(dto); 
			int actId = activeBaseinfoToolService.savePromBaseinfo(dto);
			res.put("status", 1);//1成功
			res.put("actId", actId);
		} catch (Exception e) {
			res.put("status", 2);//2失败
			res.put("msg", e.getMessage());
			logger.info(e.getMessage(), e);
		}
		return JSONObject.toJSONString(res);
	}
	
	/**
	 * 查询列表参数
	 * @param request
	 * @return
	 */
	private Map<String, Object> getSearchMapParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", request.getParameter("name"));
		map.put("marketName", request.getParameter("marketName"));
		map.put("actId", request.getParameter("actId"));
		map.put("marketId", request.getParameter("queryMarketId"));
		String actStartDate = request.getParameter("actStartDate");
		String actEndDate = request.getParameter("actEndDate");
		if(StringUtils.isNotBlank(actStartDate)){
			map.put("actStartDate", CommonUtil.getStartOfDay(DateUtil.formateDate(actStartDate)));
		}
		if(StringUtils.isNotBlank(actEndDate)){
			map.put("actEndDate", CommonUtil.getEndOfDay(DateUtil.formateDate(actEndDate)));
		}
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
		}
		return map;
	}

	
	private void handlePicture(PromBaseinfoDTO dto){
		//处理图片
		List<PictureRefDTO> plist = dto.getPictureRefList();
		Iterator<PictureRefDTO> it = plist.iterator();
		while(it.hasNext()){
			PictureRefDTO pr = it.next();
			if(StringUtils.isEmpty(pr.getUrlOrg())){
				it.remove();
			} else {
				pr.setCreateTime(new Date());
				pr.setUpdateTime(new Date());
				pr.setCreateUserId(dto.getCreateUserId());
				pr.setUpdateUserId(dto.getUpdateUserId());
				pr.setType((byte)1);//产销图片
				String picturePath = pr.getUrlOrg();
				pr.setUrl650(CommonUtil.generatePictureName(picturePath, 650));//650
				pr.setUrl400(CommonUtil.generatePictureName(picturePath, 370));//400
				pr.setUrl160(CommonUtil.generatePictureName(picturePath, 200));//170
				pr.setUrl120(CommonUtil.generatePictureName(picturePath, 160));//120
				pr.setUrl60(CommonUtil.generatePictureName(picturePath, 150));//60
			}
		}
	}
}
