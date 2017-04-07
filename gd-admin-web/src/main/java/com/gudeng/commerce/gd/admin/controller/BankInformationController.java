package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.BankInformationToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.FileUtils;
import com.gudeng.commerce.gd.admin.util.ImportExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.order.dto.BankInformationDTO;
import com.gudeng.commerce.gd.order.entity.BankInformationEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class BankInformationController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(BankInformationController.class);

	@Autowired
	private BankInformationToolService bankInformationToolService;

	@Autowired
	public FileUploadToolService fileUploadToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("bankInformation/main")
	public String list(HttpServletRequest request) {
		return "bankInformation/main";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("bankInformation/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("bankName", request.getParameter("bankName"));
			map.put("status", request.getParameter("status"));
			// 记录数
			map.put("total", bankInformationToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<BankInformationDTO> list = bankInformationToolService.getListPage(map);
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
	 */
	@RequestMapping(value = { "bankInformation/save" })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, BankInformationEntity entity,
			BankInformationDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setUpdateUserId(user.getUserID());
				dto.setUpdateTime(new Date());
				i = bankInformationToolService.update(dto);
			} else {
				entity.setStatus("1");
				entity.setCreateUserId(user.getUserID());
				entity.setCreateTime(new Date());
				i = bankInformationToolService.insert(entity);
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
		return map;
	}

	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("bankInformation/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "bankInformation/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("bankInformation/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			BankInformationDTO dto = bankInformationToolService.getById(id);
			if(dto.getBankSignLength()==null){
				dto.setBankSignLength(dto.getBankSignId()==null?0:dto.getBankSignId().length());
			}
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("bankInformation/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("bankInformation/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			BankInformationDTO dto = bankInformationToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("bankInformation/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bankInformation/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				int i = bankInformationToolService.deleteBatch(list);
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return map;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bankInformation/updateStatus/{status}")
	@ResponseBody
	public Map<String, Object> updateStatus(String ids, @PathVariable("status") String status,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中修改数据");
			} else {
				List<String> list = Arrays.asList(ids.split(","));
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("idsList", list);
				param.put("status", status);
				int i = bankInformationToolService.updateBatch(param);
				map.put("msg", "success");
			}
		} catch (Exception e) {
			map.put("msg", "服务器出错");
		}
		return map;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "bankInformation/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int total = bankInformationToolService.getTotal(map);
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
	 */
	@RequestMapping(value = "bankInformation/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("template.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();
			// 查询数据
			List<BankInformationDTO> list = bankInformationToolService.getList(map);
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, BankInformationDTO.class);
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

	@ResponseBody
	@RequestMapping("bankInformation/uploadProductPic")
	public String uploadProductPic(HttpServletRequest request,
			@RequestParam(value = "giftPicture", required = false) MultipartFile file) {
		String masterPicPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
				masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
			} else {
				map.put("status", 0);
				map.put("message", "upload file failed!!");
				return JSONObject.toJSONString(map);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", "upload file failed!!");
			return JSONObject.toJSONString(map);
		}

		map.put("status", 1);
		map.put("message", "upload file success");
		map.put("url", masterPicPath);
		return JSONObject.toJSONString(map);
	}

	@ResponseBody
	@RequestMapping(value = "bankInformation/checkImportParams", produces = "application/json; charset=utf-8")
	public String checkImportParams(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String fileName = request.getParameter("fileName");
			String extensionName = FileUtils.getExtensionName(fileName);
			if (StringUtils.containsIgnoreCase(extensionName, "xls")) {
				result.put("status", 1);
				result.put("message", "参数检测通过");
			} else {
				result.put("status", 0);
				result.put("message", "文件格式错误,请上传excel格式文件！！");
			}
		} catch (Exception e) {
			logger.info("product checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	@RequestMapping("bankInformation/uploadBankExcel")
	public ModelAndView uploadBankExcel(HttpServletRequest request,
			@RequestParam(value = "cardFile", required = false) MultipartFile file) {
		ModelAndView mv = new ModelAndView();
		String msg = "";
		try {
			InputStream is = null;
			List<List<Object>> listObj = null;
			// 判断文件是否为空
			if (file == null)
				throw new Exception("文件不存在！");
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
			long size = file.getSize();
			if (fileName == null || ("").equals(fileName) && size == 0)
				throw new Exception("文件不存在！");

			is = file.getInputStream();

			listObj = new ImportExcelUtil().getBankListByExcel(is, fileName);
			is.close();

			List<BankInformationEntity> entityList=new ArrayList<BankInformationEntity>();
			for (int i = 0; i < listObj.size(); i++) {
				List<Object> obj = listObj.get(i);
				BankInformationEntity entity = new BankInformationEntity();
				entity.setStatus("1");
				entity.setCreateTime(new Date());
				entity.setCreateUserId("");
				entity.setBankCode((String) obj.get(0));
				entity.setBankName((String) obj.get(1));
				entity.setBankShortName((String) obj.get(2));
				entity.setBankEShortName((String) obj.get(3));
				entity.setCardName((String) obj.get(4));
				if(obj.get(5)!=null&&obj.get(5)!=""){
					entity.setCardLength(Integer.parseInt((String) obj.get(5)));
				}
				entity.setBankSignId((String) obj.get(6));
				if(obj.get(7)!=null&&obj.get(7)!=""){
					entity.setBankSignLength(Integer.parseInt((String) obj.get(7)));
				}
				entity.setCardType((String) obj.get(8));
				entityList.add(entity);
				
			}
			bankInformationToolService.batchInsert(entityList);
			msg = "本次成功导入" + listObj.size() + "条数据";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "批量导入文件失败";
			logger.info("批量导入文件失败", e);
		}
		mv.addObject("msg", msg);
		mv.setViewName("bankInformation/main");
		return mv;
	}

}
