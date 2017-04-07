package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.GrdGdGiftToolService;
import com.gudeng.commerce.gd.admin.service.GrdGiftDetailToolService;
import com.gudeng.commerce.gd.admin.service.GrdPurchasegiftToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdGdGiftstoreDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class GrdGdGiftController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(GrdGdGiftController.class);

	@Autowired
	private GrdGdGiftToolService grdGdGiftToolService;

	@Autowired
	public FileUploadToolService fileUploadToolService;

	/**
	 * 礼品发放记录详细
	 */
	@Autowired
	public GrdGiftDetailToolService grdGiftDetailToolService;

	/**
	 * 礼品库存采购单记录
	 */
	@Autowired
	public GrdPurchasegiftToolService grdPurchasegiftToolService;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGift/list")
	public String list(HttpServletRequest request) {
		return "grdGdGift/list";
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGift/query")
	@ResponseBody
	public String query(HttpServletRequest request, GrdGdGiftDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("name", dto.getName());
			map.put("giftNo", dto.getGiftNo());
			// 记录数
			map.put("total", grdGdGiftToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<GrdGdGiftDTO> list = grdGdGiftToolService.getListPage(map);
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
	@RequestMapping(value = { "grdGdGift/save" })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(HttpServletRequest request, GrdGdGiftEntity entity, GrdGdGiftDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			Map<String, Object> existMap = new HashMap<String, Object>();
			existMap.put("name", entity.getName());
			List<GrdGdGiftDTO> grdGdGiftList = grdGdGiftToolService.getList(existMap);
			int size = grdGdGiftList.size();

			if (StringUtils.isNotEmpty(id)) {
				if (size > 0) {
					GrdGdGiftDTO grdGdGift = grdGdGiftList.get(0);
					String giftId = String.valueOf(grdGdGift.getId());
					if (!giftId.equals(id)) {
						map.put("msg", "当前礼品已存在，不允许添加重复礼品");
						return map;
					}
				}
				dto.setUpdateUser(user.getUserID());
				dto.setUpdateUserName(user.getUserName());
				i = grdGdGiftToolService.update(dto);
			} else {
				if (size > 0) {
					map.put("msg", "当前礼品已存在，不允许添加重复礼品");
					return map;
				}
				Integer maxId=grdGdGiftToolService.getMaxId();
				entity.setGiftNo(RandomIdGenerator.generatorGiftNumber(maxId));
				entity.setCreateUser(user.getUserID());
				entity.setCreateUserName(user.getUserName());
				entity.setUpdateUserName(user.getUserName());
				entity.setUpdateUser(user.getUserID());
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				i = grdGdGiftToolService.insert(entity);
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
	@RequestMapping("grdGdGift/beforeSave")
	public ModelAndView addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String maxId = request.getParameter("maxId");
		mv.addObject("maxId",maxId);
		mv.setViewName("grdGdGift/add");
		return mv;
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGift/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdGdGiftDTO dto = grdGdGiftToolService.getById(id);
			if (dto.getGiftImage() != null) {
				String[] imgStr = dto.getGiftImage().split(",");
				dto.setGiftImages(imgStr);
				mv.addObject("imgCount", imgStr.length);
			}

			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("grdGdGift/detail");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("grdGdGift/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			GrdGdGiftDTO dto = grdGdGiftToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("grdGdGift/view");
		return mv;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "grdGdGift/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids,String giftNos, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isEmpty(ids)) {
				map.put("msg", "未选中删除数据");
			} else {
				Map<String, Object> giftMap = new HashMap<String, Object>();
				giftMap.put("giftNos", Arrays.asList(giftNos.split(",")));
				int cnt = 0;
				cnt = grdGiftDetailToolService.getTotal(giftMap);
				if (cnt > 0) {
					map.put("msg", "礼品存在发放记录，不允许删除！");
					return map;
				}
				cnt = grdPurchasegiftToolService.getTotal(giftMap);
				if (cnt > 0) {
					map.put("msg", "礼品存在采购单，不允许删除！");
					return map;
				}
				List<String> list = Arrays.asList(ids.split(","));
				int i = grdGdGiftToolService.deleteBatch(list);
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
	@RequestMapping(value = "grdGdGift/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, GrdGdGiftDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("giftNo", dto.getGiftNo());
			map.put("name", dto.getName());
			int total = grdGdGiftToolService.getTotal(map);
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
	@RequestMapping(value = "grdGdGift/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, GrdGdGiftDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(dto.getGiftNo())) {
			map.put("giftNo", dto.getGiftNo());
		}
		if (StringUtil.isNotEmpty(dto.getName())) {
			map.put("name", dto.getName());
		}
		OutputStream ouputStream = null;
		WritableWorkbook wwb = null;

		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName = new String("礼品列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<GrdGdGiftDTO> list = grdGdGiftToolService.getList(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("礼品列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页

				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "礼品编号");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "礼品名称");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "单位");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "规格");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "起订量");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "供货周期");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "参考价");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "最新价格");// 填充第一行第八个单元格的内容
				

				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
			

				sheet.setColumnView(0, 20);
				sheet.setColumnView(1, 20);
				sheet.setColumnView(2, 15);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 15);
				sheet.setColumnView(5, 15);
				sheet.setColumnView(6, 15);
				sheet.setColumnView(7, 15);
				
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DecimalFormat df = new DecimalFormat("#.00");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						GrdGdGiftDTO item = list.get(i);
						Label label0 = new Label(0, i + 1, item.getGiftNo());
						Label label1 = new Label(1, i + 1, item.getName());
						Label label2 = new Label(2, i + 1, item.getUnit());
						Label label3 = new Label(3, i + 1, item.getSpec());		
						Label label4 = new Label(4, i + 1, item.getRiseCount()==null?"":String.valueOf(item.getRiseCount()));
						Label label5 = new Label(5, i + 1, item.getSupplyCycle());
						Label label6 = new Label(6, i + 1,
								item.getRePrice() == null ? "" : df.format(item.getRePrice()));
						Label label7 = new Label(7, i + 1,
								item.getNewPrice() == null ? "" : df.format(item.getNewPrice()));
						

						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						
					}
				}

				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
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
	@RequestMapping("grdGdGift/uploadProductPic")
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
}
