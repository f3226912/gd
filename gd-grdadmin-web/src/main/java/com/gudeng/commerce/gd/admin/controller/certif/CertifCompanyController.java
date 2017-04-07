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
import com.gudeng.commerce.gd.admin.service.certif.CertifCompanyToolService;
import com.gudeng.commerce.gd.admin.service.certif.CertifLogToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifCompanyDTO;
import com.gudeng.commerce.gd.customer.entity.certif.CertifCompanyEntity;
import com.gudeng.commerce.gd.customer.entity.certif.CertifLogEntity;
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
public class CertifCompanyController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CertifCompanyController.class);

	@Autowired
	private CertifCompanyToolService certifCompanyToolService;
	
	@Autowired
	private CertifLogToolService certifLogToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifCompany/main")
	public String list(HttpServletRequest request) {
		return "certifCompany/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifCompany/query")
	@ResponseBody
	public String query(HttpServletRequest request, String startDate, String endDate, String account,
			String companyName, String status) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("companyName", companyName);
			map.put("status", status);			
			// 记录数
			map.put("total", certifCompanyToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CertifCompanyDTO> list = certifCompanyToolService.getListPage(map);
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
	@RequestMapping(value = { "certifCompany/save" })
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, CertifCompanyEntity entity, CertifCompanyDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = certifCompanyToolService.update(dto);
			} else {
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = certifCompanyToolService.insert(entity);
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
	@RequestMapping("certifCompany/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "certifCompany/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifCompany/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifCompanyDTO dto = certifCompanyToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("certifCompany/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifCompany/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifCompanyDTO dto = certifCompanyToolService.getById(id);
			dto.setCommitTimeStr(DateUtil.toString(dto.getCommitTime(), DateUtil.DATE_FORMAT_DATETIME));
			mv.addObject("dto", dto);
			//查询审核记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("optionId", dto.getId());
			map.put("type", CertifTypeEnum.CERTIF_COMPANY.getKey());
			List<CertifLogDTO> certifLogList = certifLogToolService.getList(map);
			mv.addObject("certifLogList", certifLogList);
			
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("certifCompany/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "certifCompany/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (StringUtils.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = certifCompanyToolService.deleteBatch(list);
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
	@RequestMapping(value = "certifCompany/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = certifCompanyToolService.getTotal(map);
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
	@RequestMapping(value = "certifCompany/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate, String account,
			String companyName, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("account", account);
		map.put("companyName", companyName);
		map.put("status", status);			
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<CertifCompanyDTO> list = certifCompanyToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, CertifCompanyDTO.class);
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
	

	@RequestMapping(value="certifCompany/updateStatus/{id}-{status}-{type}" )
	@ResponseBody
    public String pass(@PathVariable("id") String id,@PathVariable("status") String status,@PathVariable("type") String type, HttpServletRequest request){
		
		SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);

		CertifCompanyDTO dto=new CertifCompanyDTO();
		dto.setId(Integer.valueOf(id));
		dto.setStatus(status);
		dto.setOptionUser(regUser.getUserName());
		try {
			int i = certifCompanyToolService.update(dto);
			if(i>0){
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(Integer.valueOf(id));
				cDto.setType(CertifTypeEnum.CERTIF_COMPANY.getKey());
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
	
	@RequestMapping(value="certifCompany/unpassShow/{id}")
    public String unpassShow(@PathVariable("id") String id, HttpServletRequest request){
		try {
			CertifCompanyDTO dto = certifCompanyToolService.getById(id);
			putModel("dto",dto);
			return "certifCompany/unpassShow";
		} catch (Exception e) {

		}
		return null;
	}
	
	@RequestMapping(value="certifCompany/auditUnpass" )
	@ResponseBody
    public String auditUnpass(CertifCompanyDTO certifCompanyDTO, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			String reason=request.getParameter("reason");
			CertifCompanyDTO dto=new CertifCompanyDTO();
			dto.setId(certifCompanyDTO.getId());
			dto.setStatus("2");
			dto.setOptionUser(regUser.getUserName());
			int i = certifCompanyToolService.update(dto);
			
			if(i>0){
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(certifCompanyDTO.getId());
				cDto.setType(CertifTypeEnum.CERTIF_COMPANY.getKey());
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
