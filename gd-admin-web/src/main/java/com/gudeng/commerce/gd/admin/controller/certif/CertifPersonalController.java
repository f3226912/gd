package com.gudeng.commerce.gd.admin.controller.certif;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.certif.CertifLogToolService;
import com.gudeng.commerce.gd.admin.service.certif.CertifPersonalToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifPersonalDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifLogEntity;
import com.gudeng.commerce.gd.customer.entity.certif.CertifPersonalEntity;
import com.gudeng.commerce.gd.customer.enums.CertifTypeEnum;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
@Controller
public class CertifPersonalController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CertifPersonalController.class);

	@Autowired
	private CertifPersonalToolService certifPersonalToolService;
	
	@Autowired
	private CertifLogToolService certifLogToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifPersonal/main")
	public String list(HttpServletRequest request) {
		return "certifPersonal/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifPersonal/query")
	@ResponseBody
	public String query(HttpServletRequest request, String startDate, String endDate, String account,
			String realName, String status,String mobile) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("realName", realName);
			map.put("status", status);
			map.put("mobile", mobile);
			// 记录数
			map.put("total", certifPersonalToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CertifPersonalDTO> list = certifPersonalToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}

	/**
	 * 保存数据（新增、修改）
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = { "certifPersonal/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, CertifPersonalEntity entity, CertifPersonalDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = certifPersonalToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = certifPersonalToolService.insert(entity);
			}
			if (i > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "保存失败");
			}
		} catch (Exception e) {
			map.put("msg", "保存失败");
			logger.trace("新增保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}


	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifPersonal/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "certifPersonal/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifPersonal/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifPersonalDTO dto = certifPersonalToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("certifPersonal/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifPersonal/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifPersonalDTO dto = certifPersonalToolService.getById(id);
			mv.addObject("dto", dto);
			
			//查询审核记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("optionId", dto.getId());
			map.put("type", CertifTypeEnum.CERTIF_PERSONAL.getKey());
			List<CertifLogDTO> certifLogList = certifLogToolService.getList(map);
			mv.addObject("certifLogList", certifLogList);
			
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("certifPersonal/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "certifPersonal/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = certifPersonalToolService.deleteBatch(list);
				map.put("msg", "success");
            }
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "certifPersonal/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = certifPersonalToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出Excel文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "certifPersonal/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate, String account,
			String realName, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("account", account);
		map.put("realName", realName);
		map.put("status", status);
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<CertifPersonalDTO> list = certifPersonalToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, CertifPersonalDTO.class);
			workBook.write(ouputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value="certifPersonal/updateStatus/{id}-{status}-{type}" )
	@ResponseBody
    public String pass(@PathVariable("id") String id,@PathVariable("status") String status,@PathVariable("type") String type, HttpServletRequest request){
		
		SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);

		CertifPersonalDTO dto=new CertifPersonalDTO();
		dto.setId(Integer.valueOf(id));
		dto.setStatus(status);
		dto.setOptionUser(regUser.getUserName());
		try {
			int i = certifPersonalToolService.update(dto);
			if(i>0){
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(Integer.valueOf(id));
				cDto.setType(CertifTypeEnum.CERTIF_PERSONAL.getKey());
				cDto.setOptionUser(regUser.getUserName());
				cDto.setStatus("1");
				cDto.setReason("审核通过");
				cDto.setCreateTime(date);
				cDto.setUpdateTime(date);
				certifLogToolService.insert(cDto);
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "failed";
	}
	
	@RequestMapping(value="certifPersonal/unpassShow/{id}")
    public String unpassShow(@PathVariable("id") String id, HttpServletRequest request){
		try {
			CertifPersonalDTO dto = certifPersonalToolService.getById(id);
			putModel("dto",dto);
			return "certifPersonal/unpassShow";
		} catch (Exception e) {

		}
		return null;
	}
	
	@RequestMapping(value="certifPersonal/auditUnpass" )
	@ResponseBody
    public String auditUnpass(CertifPersonalDTO certifPersonalDTO, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			String reason=request.getParameter("reason");
			CertifPersonalDTO dto=new CertifPersonalDTO();
			dto.setId(certifPersonalDTO.getId());
			dto.setStatus("2");
			dto.setOptionUser(regUser.getUserName());
			int i = certifPersonalToolService.update(dto);
			
			if(i>0){
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(certifPersonalDTO.getId());
				cDto.setType(CertifTypeEnum.CERTIF_PERSONAL.getKey());
				cDto.setOptionUser(regUser.getUserName());
				cDto.setStatus("2");
				cDto.setReason(reason);
				cDto.setCreateTime(date);
				cDto.setUpdateTime(date);
				certifLogToolService.insert(cDto);
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
