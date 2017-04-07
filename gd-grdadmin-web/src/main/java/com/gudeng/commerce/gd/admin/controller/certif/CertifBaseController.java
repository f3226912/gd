package com.gudeng.commerce.gd.admin.controller.certif;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.certif.CertifBaseToolService;
import com.gudeng.commerce.gd.admin.service.certif.CertifLogToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifBaseDTO;
import com.gudeng.commerce.gd.customer.dto.certif.CertifLogDTO;
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
public class CertifBaseController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CertifBaseController.class);

	@Autowired
	private CertifBaseToolService certifBaseToolService;

	@Autowired
	private CertifLogToolService certifLogToolService;

	@Autowired
	public MarketManageService marketManageService;

	/**
	 * 进入主页
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("certifBase/main")
	public String list(HttpServletRequest request) {
		return "certifBase/main";
	}

	/**
	 * 列表查询
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping("certifBase/query")
	@ResponseBody
	public String query(HttpServletRequest request, String startDate, String endDate, String account,
			String baseName, String certifStatus) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("baseName", baseName);
			map.put("certifStatus", certifStatus);
			// 记录数
			map.put("total", certifBaseToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<CertifBaseDTO> list = certifBaseToolService.getListPage(map);
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace("列表查询错误", e);
		}
		return null;
	}


	/**
	 * 根据id修改数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("certifBase/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifBaseDTO dto = certifBaseToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("certifBase/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("certifBase/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			CertifBaseDTO dto = certifBaseToolService.getById(id);
			Set<String> marketIds = StringUtils.commaDelimitedListToSet(dto.getMarkets());
			StringBuffer sb = new StringBuffer();
			for(String marketId : marketIds){
				MarketDTO market = marketManageService.getById(marketId);
				sb.append(",").append(market.getMarketName());
			}
			if(sb.toString().length()>0){
				dto.setMarketNames(sb.toString().substring(1));
			}else{
				dto.setMarketNames("");
			}
			Set<String> pictureList = StringUtils.commaDelimitedListToSet(dto.getBzlPhotoUrl());
			dto.setCommitTimeString(DateUtil.sdfDateTime.format(dto.getCommitTime()));
			//查询审核记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("optionId", dto.getId());
			map.put("type", CertifTypeEnum.CERTIF_BASE.getKey());
			List<CertifLogDTO> certifLogList = certifLogToolService.getList(map);
			mv.addObject("certifLogList", certifLogList);
			mv.addObject("pictureList", pictureList);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("certifBase/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 *
	 * @param request
	 * @return
	 * @author lidong
	 */
	@RequestMapping(value = "certifBase/delete")
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		    if (CommonUtil.isEmpty(ids)) {
                map.put("msg", "未选中删除数据");
            } else {
	            List<String> list = Arrays.asList(ids.split(","));
				int i = certifBaseToolService.deleteBatch(list);
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
	@RequestMapping(value = "certifBase/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, String startDate, String endDate, String account,
			String baseName, String certifStatus) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("baseName", baseName);
			map.put("certifStatus", certifStatus);
			int total = certifBaseToolService.getTotal(map);
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
	@RequestMapping(value = "certifBase/exportData")
	public String exportData(HttpServletRequest request, String startDate, String endDate, String account,
			String baseName, String certifStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("account", account);
			map.put("baseName", baseName);
			map.put("certifStatus", certifStatus);
			List<CertifBaseDTO> list = certifBaseToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, CertifBaseDTO.class);
			workBook.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
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

	@RequestMapping(value="certifBase/updateStatus/{id}-{status}-{type}" )
	@ResponseBody
    public String pass(@PathVariable("id") String id,@PathVariable("status") String status,@PathVariable("type") String type, HttpServletRequest request){

		SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);

		try {
			CertifBaseDTO dto = new CertifBaseDTO();
			dto.setId(Integer.valueOf(id));
			dto.setStatus(status);
			dto.setOptionUser(regUser.getUserName());
			//更新认证信息表
			int i = certifBaseToolService.update(dto);
			if(i>0){
				//更新审核记录表
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(Integer.valueOf(id));
				cDto.setType(CertifTypeEnum.CERTIF_BASE.getKey());
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

	@RequestMapping(value="certifBase/unpassShow/{id}")
    public String unpassShow(@PathVariable("id") String id, HttpServletRequest request){
		try {
			CertifBaseDTO dto = certifBaseToolService.getById(id);
			putModel("dto",dto);
			return "certifPersonal/unpassShow";
		} catch (Exception e) {

		}
		return null;
	}

	@RequestMapping(value="certifBase/auditUnpass" )
	@ResponseBody
    public String auditUnpass(CertifBaseDTO certifBaseDTO, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			String reason = request.getParameter("reason");

			CertifBaseDTO dto = new CertifBaseDTO();
			dto.setId(certifBaseDTO.getId());
			dto.setStatus("2");
			dto.setOptionUser(regUser.getUserName());
			//更新认证信息表
			int i = certifBaseToolService.update(dto);

			if(i>0){
				//审核记录表
				CertifLogEntity cDto = new CertifLogEntity();
				Date date=new Date();
				cDto.setOptionId(certifBaseDTO.getId());
				cDto.setType(CertifTypeEnum.CERTIF_BASE.getKey());
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
