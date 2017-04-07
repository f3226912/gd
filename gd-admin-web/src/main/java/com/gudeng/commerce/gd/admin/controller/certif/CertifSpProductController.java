package com.gudeng.commerce.gd.admin.controller.certif;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.certif.CertifLogToolService;
import com.gudeng.commerce.gd.admin.service.certif.CertifSpProductToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifSpProductDTO;
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
public class CertifSpProductController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CertifSpProductController.class);

	@Autowired
	private CertifSpProductToolService certifSpProductToolService;

	@Autowired
	private CertifLogToolService certifLogToolService;

	/**
	 * 进入主页
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifSpProduct/main")
	public String list(HttpServletRequest request) {
		return "certifSpProduct/main";
	}

	/**
	 * 列表查询
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifSpProduct/query")
	@ResponseBody
	public String query(HttpServletRequest request, String startDate, String endDate, String account,
			String signs, String certifStatus,String mobile) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			// 记录数
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("mobile", mobile);
			map.put("signs", signs);
			map.put("certifStatus", certifStatus);
			map.put("total", certifSpProductToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CertifSpProductDTO> list = certifSpProductToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}



	/**
	 * 进入新增页面
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifSpProduct/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "certifSpProduct/save";
	}

	/**
	 * 根据id修改数据
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifSpProduct/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifSpProductDTO dto = certifSpProductToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("certifSpProduct/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifSpProduct/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifSpProductDTO dto = certifSpProductToolService.getById(id);
			//专用标志图片
			Set<String> pictureList = StringUtils.commaDelimitedListToSet(dto.getSpecialImg());
			dto.setCommitTimeString(DateUtil.sdfDateTime.format(dto.getCommitTime()));
			//查询审核记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("optionId", dto.getId());
			map.put("type", CertifTypeEnum.CERTIF_SP_PRODUCT.getKey());
			List<CertifLogDTO> certifLogList = certifLogToolService.getList(map);
			mv.addObject("certifLogList", certifLogList);
			mv.addObject("pictureList", pictureList);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("certifSpProduct/view");
		return mv;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@ResponseBody
	@RequestMapping(value = "certifSpProduct/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = certifSpProductToolService.getTotal(map);
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
	@RequestMapping(value = "certifSpProduct/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate, String account,
			String signs, String certifStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("account", account);
		map.put("signs", signs);
		map.put("certifStatus", certifStatus);
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<CertifSpProductDTO> list = certifSpProductToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, CertifSpProductDTO.class);
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

	@RequestMapping(value="certifSpProduct/updateStatus/{id}-{status}-{type}" )
	@ResponseBody
    public String pass(@PathVariable("id") String id,@PathVariable("status") String status,@PathVariable("type") String type, HttpServletRequest request){

		SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);

		try {
			CertifSpProductDTO dto = new CertifSpProductDTO();
			dto.setId(Integer.valueOf(id));
			dto.setStatus(status);
			dto.setOptionUser(regUser.getUserName());
			//更新认证信息表
			int i = certifSpProductToolService.update(dto);
			if(i>0){
				//更新审核记录表
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(Integer.valueOf(id));
				cDto.setType(CertifTypeEnum.CERTIF_SP_PRODUCT.getKey());
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

	@RequestMapping(value="certifSpProduct/unpassShow/{id}")
    public String unpassShow(@PathVariable("id") String id, HttpServletRequest request){
		try {
			CertifSpProductDTO dto = certifSpProductToolService.getById(id);
			putModel("dto",dto);
			return "certifPersonal/unpassShow";
		} catch (Exception e) {

		}
		return null;
	}

	@RequestMapping(value="certifSpProduct/auditUnpass" )
	@ResponseBody
    public String auditUnpass(CertifSpProductDTO certifSpProductDTO, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			String reason = request.getParameter("reason");

			CertifSpProductDTO dto = new CertifSpProductDTO();
			dto.setId(certifSpProductDTO.getId());
			dto.setStatus("2");
			dto.setOptionUser(regUser.getUserName());
			//更新认证信息表
			int i = certifSpProductToolService.update(dto);

			if(i>0){
				//审核记录表
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(certifSpProductDTO.getId());
				cDto.setType(CertifTypeEnum.CERTIF_SP_PRODUCT.getKey());
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
