package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.NpsOfferPriceToolService;
import com.gudeng.commerce.gd.admin.service.NpsPurchaseToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 农批商询价信息管理
 * 
 * @author zlb
 * @date 2017年1月4日
 */
@Controller
public class NpsPurchaseController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(NpsPurchaseController.class);

	@Autowired
	private NpsPurchaseToolService npsPurchaseToolService;
	
	@Autowired
	private NpsOfferPriceToolService npsOfferPriceToolService;
	
	@Autowired
	private MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public MarketManageService marketManageService;
	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsPurchase/main")
	public String list(HttpServletRequest request) {
		return "npsPurchase/main";
	}

	/**
	 * 供应商报价列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsPurchase/offerList/{purchaseId}")
	public String offerList(HttpServletRequest request, @PathVariable("purchaseId") String purchaseId) {
		request.setAttribute("purchaseId", purchaseId);
		request.setAttribute("type", request.getParameter("type"));
		return "npsPurchase/offer_list";
	}
	/**
	 * 跳转选择市场页面
	 *
	 * @return
	 */
	@RequestMapping("npsPurchase/toMarketSelect")
	public String toMarketSelect(ModelMap map) {
		return "npsPurchase/marketSelect";
	}
	
	@ResponseBody
	@RequestMapping("npsPurchase/getListOfMarket")
	public String queryMarketIdByMemberId(HttpServletRequest request,String marketName) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<MarketDTO> marketList = null;
		try {
			// '0:产地供应商,1: 街市,2 :市场(农批中心),3:前台用户添加', 4:市场活动
			map.put("marketType", 2);
			// 0:启用, 1:停用, 2:已删除
			map.put("status", 0);
			// 市场名称
			map.put("marketName", marketName);
			// 记录数
			map.put("total", marketManageService.getMarketCountByCondition(map));
			// 设定分页,排序
			setCommParameters(request, map);
			marketList = marketManageService.getMarketListByCondition(map);
			map.put("rows", marketList);// rows键 存放每页记录 list
		} catch (Exception e) {
			logger.info("getListOfMarket With ex : {} ", e);
		}
	
		return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
	}
	/**
	 * 显示隐藏供应商报价信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "npsPurchase/updateOffer", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateOffer(HttpServletRequest request,NpsOfferPriceDTO dto) {
		Map<String, Object> map = new HashMap<>();
		long i = 0;
		try {
			i=npsOfferPriceToolService.update(dto);
			if (i > 0) {
				map.put("msg", "success");
			} else {
				map.put("msg", "更新失败");
			}
		} catch (Exception e) {
			map.put("msg", "更新失败");
			logger.trace("保存错误", e);
		}
		return JSONObject.toJSONString(map);
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "npsPurchase/query")
	@ResponseBody
	public String query(HttpServletRequest request, NpsPurchaseDTO dto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("id", dto.getId());
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("marketName", dto.getMarketName());
			map.put("status", dto.getStatus());
			map.put("releaseTimeStart", request.getParameter("releaseTimeStart"));
			map.put("releaseTimeEnd", request.getParameter("releaseTimeEnd"));
			map.put("createTimeStart", request.getParameter("createTimeStart"));
			map.put("createTimeEnd", request.getParameter("createTimeEnd"));
			// 记录数
			map.put("total", npsPurchaseToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<NpsPurchaseDTO> list = npsPurchaseToolService.getListPage(map);
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
	@RequestMapping(value = "npsPurchase/save", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveOrUpdate(HttpServletRequest request, NpsPurchaseEntity entity, NpsPurchaseDTO dto) {
		Map<String, Object> map = new HashMap<>();
		try {
			SysRegisterUser user = getUser(request);
			long i = 0;
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				dto.setAuthUserId(user.getUserCode());
				if (StringUtils.equals(dto.getStatus(), "2")) {
					Date date=new Date();
					//dto.setReleaseTimeStr(DateUtil.getDate(date, DateUtil.DATE_FORMAT_DATETIME));
					dto.setEndTimeStr(DateUtil.getDate(DateUtil.addDate(date, dto.getPurchaseCycle()==null?0:dto.getPurchaseCycle()),DateUtil.DATE_FORMAT_DATETIME));
				}
				i = npsPurchaseToolService.update(dto);
			}
			if (i > 0) {
				try {
					if (StringUtils.equals(dto.getStatus(), "2")) {
						NpsPurchaseDTO temp = npsPurchaseToolService.getById(String.valueOf(dto.getId()));
						//查询会员的token
						MemberBaseinfoDTO member = memberBaseinfoToolService.getMemberTonken(String.valueOf(temp.getMemberId()));
						if(null != member && null != member.getDevice_tokens() && !"".equals(member.getDevice_tokens())){
							UMengPushMessage pushMessage = new UMengPushMessage();
							GdMessageDTO gdMessage = new GdMessageDTO();
							Map<String,String> extraMap = new HashMap<String,String>();
							extraMap.put("openmenu", "NPS_PURCHASE_DETAIL");
							extraMap.put("purchaseId", ""+dto.getId());
							gdMessage.setExtraMap(extraMap);
							gdMessage.setSendApp("3");
							gdMessage.setSendType("1");
							gdMessage.setTicket("农批商");
							gdMessage.setTitle("农批商");
							gdMessage.setContent("你发布的（"+temp.getGoodsName()+"）采购需求已通过审核， 请点击查看！【谷登科技】");
							gdMessage.setDevice_tokens(member.getDevice_tokens());
							gdMessage.setProduction_mode(false);
							logger.info("友盟推送参数：" + gdMessage);
							pushMessage.pushMessage(gdMessage);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
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
	 */
	@RequestMapping("npsPurchase/beforeSave")
	public String addDto(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		return "npsPurchase/save";
	}

	/**
	 * 根据id修改数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsPurchase/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			NpsPurchaseDTO dto = npsPurchaseToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入编辑页面错误", e);
		}
		mv.setViewName("npsPurchase/edit");
		return mv;
	}

	/**
	 * 根据id查看数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("npsPurchase/view/{id}")
	public ModelAndView view(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		try {
			NpsPurchaseDTO dto = npsPurchaseToolService.getById(id);
			mv.addObject("dto", dto);
		} catch (Exception e) {
			logger.trace("进入查看页面错误", e);
		}
		mv.setViewName("npsPurchase/view");
		return mv;
	}


	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "npsPurchase/checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request,NpsPurchaseDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", dto.getId());
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("marketName", dto.getMarketName());
			map.put("status", dto.getStatus());
			String releaseStart= request.getParameter("releaseTimeStart");
			String releaseEnd=request.getParameter("releaseTimeEnd");
			map.put("releaseTimeStart", releaseStart);
			map.put("releaseTimeEnd", releaseEnd);
//			if (DateUtil.isDateIntervalOverFlow(releaseStart, releaseEnd, 31)) {
//				result.put("status", 0);
//				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
//				return JSONObject.toJSONString(result);
//			}
			
			String createStart=request.getParameter("createTimeStart");
			String createEnd=request.getParameter("createTimeEnd");
			map.put("createTimeStart", createStart);
			map.put("createTimeEnd", createEnd);
//			if (DateUtil.isDateIntervalOverFlow(createStart, createEnd, 31)) {
//				result.put("status", 0);
//				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
//				return JSONObject.toJSONString(result);
//			}
			int total = npsPurchaseToolService.getTotal(map);
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
	@RequestMapping(value = "npsPurchase/exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response,NpsPurchaseDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			map.put("id", dto.getId());
			map.put("mobilePhone", dto.getMobilePhone());
			map.put("goodsName", dto.getGoodsName());
			map.put("marketName", dto.getMarketName());
			map.put("status", dto.getStatus());
			map.put("releaseTimeStart", request.getParameter("releaseTimeStart"));
			map.put("releaseTimeEnd", request.getParameter("releaseTimeEnd"));
			map.put("createTimeStart", request.getParameter("createTimeStart"));
			map.put("createTimeEnd", request.getParameter("createTimeEnd"));
			map.put("startRow", 0);
			map.put("endRow", 10000);
			// 设置输出响应头信息，
			WritableWorkbook wwb = null;

			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("农批商询价列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				fileName = new String("农批商询价列表".getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName  + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<NpsPurchaseDTO> list = npsPurchaseToolService.getList(map);
			// 在输出流中创建一个新的写入工作簿
						wwb = Workbook.createWorkbook(ouputStream);
						if (wwb != null) {
							WritableSheet sheet = wwb.createSheet("询价列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
							// 第一个参数表示列，第二个参数表示行
							int col = 0;
							Label label0 = new Label(col++, 0, "编号");
							Label label1 = new Label(col++, 0, "手机号码");
							Label label10 = new Label(col++, 0, "姓名");
							Label label11 = new Label(col++, 0, "所属市场");
							Label label12 = new Label(col++, 0, "商铺名称");
							Label label2 = new Label(col++, 0, "商品名称");
							Label label3 = new Label(col++, 0, "提交时间");
							Label label4 = new Label(col++, 0, "发布时间");
							Label label5 = new Label(col++, 0, "状态");
							Label label6 = new Label(col++, 0, "结束时间");
							Label label7 = new Label(col++, 0, "累计被访问次数");
							Label label8 = new Label(col++, 0, "累计报价数");
							//Label label9 = new Label(col++, 0, "审核员");
							
							sheet.addCell(label0);
							sheet.addCell(label1);
							sheet.addCell(label10);
							sheet.addCell(label11);
							sheet.addCell(label12);
							sheet.addCell(label2);
							sheet.addCell(label3);
							sheet.addCell(label4);
							sheet.addCell(label5);
							sheet.addCell(label6);
							sheet.addCell(label7);
							sheet.addCell(label8);
							//sheet.addCell(label9);
							
							/*** 循环添加数据到工作簿 ***/
							if (list != null && list.size() > 0) {
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								for (int i = 0, lenght = list.size(); i < lenght; i++) {
									NpsPurchaseDTO npsPurchaseDTO = list.get(i);
									col = 0;
									Label labelA0 = new Label(col++, i + 1, npsPurchaseDTO.getId().toString());
									Label labelA1 = new Label(col++, i + 1, npsPurchaseDTO.getMobilePhone() );
									Label labelA10 = new Label(col++, i + 1, npsPurchaseDTO.getRealName());
									Label labelA11 = new Label(col++, i + 1, npsPurchaseDTO.getMarketName());
									Label labelA12 = new Label(col++, i + 1, npsPurchaseDTO.getShopsName());
									Label labelA2 = new Label(col++, i + 1, npsPurchaseDTO.getGoodsName());
									Label labelA3 = new Label(col++, i + 1, sdf.format(npsPurchaseDTO.getCreateTime()));
									Label labelA4 = new Label(col++, i + 1,npsPurchaseDTO.getReleaseTime()==null?"": sdf.format(npsPurchaseDTO.getReleaseTime()));
									Label labelA5 = new Label(col++, i + 1, setStatus(npsPurchaseDTO.getStatus()));
									Label labelA6 = new Label(col++, i + 1, npsPurchaseDTO.getEndTime()==null?"":sdf.format(npsPurchaseDTO.getEndTime()));
									Label labelA7 = new Label(col++, i + 1, npsPurchaseDTO.getVisitCount().toString());
									Label labelA8 = new Label(col++, i + 1, npsPurchaseDTO.getPriceCount().toString());
									//Label labelA9 = new Label(col++, i + 1, npsPurchaseDTO.getAuthUserId());
									
									sheet.addCell(labelA0);
									sheet.addCell(labelA1);
									sheet.addCell(labelA10);
									sheet.addCell(labelA11);
									sheet.addCell(labelA12);
									sheet.addCell(labelA2);
									sheet.addCell(labelA3);
									sheet.addCell(labelA4);
									sheet.addCell(labelA5);
									sheet.addCell(labelA6);
									sheet.addCell(labelA7);
									sheet.addCell(labelA8);
									//sheet.addCell(labelA9);
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
	
	/**
	 * 状态词
	 * @param status
	 * @return
	 */
	private String setStatus(String status){
		String statusStr="";
		switch(status){
		case "1":
			statusStr="待审核";
			break;
		case "2":
			statusStr="审核通过";
			break;
		case "3":
			statusStr="已驳回";
			break;
		case "4":
			statusStr="用户撤销";
			break;
		/*case "5":
			statusStr="已关闭";
			break;
			*/
		case "6":
			statusStr="已结束";
			break;
		}
		return statusStr;
	}
	
	
	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "npsPurchase/checkExportParams2", produces = "application/json; charset=utf-8")
	public String checkExportParams2(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseId", request.getParameter("purchaseId"));
			
			int total = npsOfferPriceToolService.getTotal(map);
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
	@RequestMapping(value = "npsPurchase/exportData2")
	public String exportData2(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream ouputStream = null;
		try {
			map.put("purchaseId", request.getParameter("purchaseId"));
			map.put("startRow", 0);
			map.put("endRow", 10000);
			// 设置输出响应头信息，
			WritableWorkbook wwb = null;

			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String fileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && -1 != agent.indexOf("MSIE") || -1 != agent.indexOf("Trident")) {// ie
				fileName = URLEncoder.encode("供应商报价列表", "UTF-8");
			} else if (agent != null && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				fileName = new String("供应商报价列表".getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName  + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			List<NpsOfferPriceDTO> list = npsOfferPriceToolService.getList(map);
			// 在输出流中创建一个新的写入工作簿
						wwb = Workbook.createWorkbook(ouputStream);
						if (wwb != null) {
							WritableSheet sheet = wwb.createSheet("报价列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
							// 第一个参数表示列，第二个参数表示行
							int col = 0;
							Label label0 = new Label(col++, 0, "手机号码");
							Label label1 = new Label(col++, 0, "询价编号");
							Label label2 = new Label(col++, 0, "上车价");
							Label label3 = new Label(col++, 0, "单位");
							Label label4 = new Label(col++, 0, "报价时间");
							Label label5 = new Label(col++, 0, "报价状态");
							sheet.addCell(label0);
							sheet.addCell(label1);
							sheet.addCell(label2);
							sheet.addCell(label3);
							sheet.addCell(label4);
							sheet.addCell(label5);
							/*** 循环添加数据到工作簿 ***/
							if (list != null && list.size() > 0) {
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								for (int i = 0, lenght = list.size(); i < lenght; i++) {
									NpsOfferPriceDTO npsOfferPriceDTO = list.get(i);
									col = 0;
									Label labelA0 = new Label(col++, i + 1, npsOfferPriceDTO.getMobilePhone());
									Label labelA1 = new Label(col++, i + 1, npsOfferPriceDTO.getPurchaseId().toString());
									Label labelA2 = new Label(col++, i + 1, npsOfferPriceDTO.getOfferPriceStr());
									Label labelA3 = new Label(col++, i + 1, npsOfferPriceDTO.getPurchaseUnit());
									Label labelA4 = new Label(col++, i + 1,npsOfferPriceDTO.getOfferTime()==null?"": sdf.format(npsOfferPriceDTO.getOfferTime()));
									Label labelA5 = new Label(col++, i + 1, npsOfferPriceDTO.getStatus()==null?"":setStatus2(npsOfferPriceDTO.getStatus()));
									
									sheet.addCell(labelA0);
									sheet.addCell(labelA1);
									sheet.addCell(labelA2);
									sheet.addCell(labelA3);
									sheet.addCell(labelA4);
									sheet.addCell(labelA5);
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
	
	/**
	 * 状态词
	 * @param status
	 * @return
	 */
	private String setStatus2(String status){
		String statusStr="";
		switch(status){
		case "1":
			statusStr="显示";
			break;
		case "2":
			statusStr="已删除";
			break;
		case "3":
			statusStr="隐藏";
			break;
		}
		return statusStr;
	}
	
}
