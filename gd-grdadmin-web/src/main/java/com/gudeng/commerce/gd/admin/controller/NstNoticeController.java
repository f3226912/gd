package com.gudeng.commerce.gd.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.NstNoticeToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.web.controller.BaseController;
/**
 * 农速通controller
 * @author xiaojun
 */
@Controller
@RequestMapping("notice")
public class NstNoticeController extends AdminBaseController {
	  /** 记录日志 */
    @SuppressWarnings("unused")
    private static final GdLogger logger = GdLoggerFactory.getLogger(NstNoticeController.class);
    
    @Autowired
    private NstNoticeToolService nstNoticeToolService;
    
    @RequestMapping("index")
    public String index(Model model){
    	return "notice/nstNoticeList";
    }
    
    @RequestMapping("addNstNotice")
    public String addNstNotice(Model model){
    	return "notice/addNstNotice";
    }
    /**
     * 获取公告列表
     * @param request
     * @param nstDto
     * @return
     */
    @RequestMapping("getNstNoticeList")
    @ResponseBody
    public String getNstNoticeList(HttpServletRequest request,NstNoticeEntityDTO nstDto){
    	try {
			Map<String, Object> map = new HashMap<>();
			map.put("startBeginTime", nstDto.getStartBeginTime());
			map.put("startEndTime", nstDto.getStartEndTime());
			map.put("endBeginTime", nstDto.getEndBeginTime());
			map.put("endEndTime", nstDto.getEndEndTime());
			//设置总记录数
			map.put("total", nstNoticeToolService.queryNstNoticeListByPageCount(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<NstNoticeEntityDTO> list = nstNoticeToolService.queryNstNoticeListByPage(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 保存公告
     * @param request
     * @param nstDto
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(HttpServletRequest request,NstNoticeEntityDTO nstDto) {
    	Map<String, Object> map = new HashMap<>();
    	SysRegisterUser user = getUser(getRequest());
    	getMap(map, nstDto, user);
    	try {
			Integer status =nstNoticeToolService.insert(map);
			if (status>0) {
				return "success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
    /**
     * 拼装map
     * @param map
     */
	private void getMap(Map<String, Object> map,NstNoticeEntityDTO nstDto,SysRegisterUser user) {
		// TODO Auto-generated method stub
		map.put("startTime", nstDto.getStartTime());
		map.put("endTime", nstDto.getEndTime());
		map.put("notice", nstDto.getNotice());
		map.put("createuserId", user==null?"":user.getUserID());
		map.put("updateuserId", user==null?"":user.getUserID());
	}
}
