package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.SubAuditQueryBean;
import com.gudeng.commerce.gd.admin.service.AuditLogToolService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.OrderProductDetailToolService;
import com.gudeng.commerce.gd.admin.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.admin.service.SubAuditToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.JxlsExcelUtil;
import com.gudeng.commerce.gd.admin.util.RandomIdGenerator;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketImageDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.entity.SubAuditEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;


@Controller
@RequestMapping("subaudit")
public class SubAuditController extends AdminBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(SubAuditController.class);

	@Autowired
	private SubAuditToolService auditToolService;
	@Autowired
	private AuditLogToolService auditLogToolService;

	@Autowired
	private OrderBaseinfoToolService orderBaseinfoToolService;
	@Autowired
	private OrderProductDetailToolService orderProductDetailToolService;
	@Autowired
	private PaySerialnumberToolService paySerialnumberToolService;

	@Autowired
	public GdProperties gdProperties;



	/**
	 *
	* @Title: subauditList
	* @Description: TODO(当用户从菜单点击
	* 					“待补贴验证订单”，“系统驳回订单”，”已补贴订单“，“不予补贴订单”，“全部补贴验证订单”
	* 					跳转的页面)
	* 					subStatus所对应的状态分别为：1: 待补贴验证订单;2: 系统驳回订单;3: 已补贴订单;4: 不予补贴订单到
	* @param request
	* @throws
	 */
	@RequestMapping("list/{subStatus}")
	public String subauditList(@PathVariable String subStatus, HttpServletRequest request){
		putModel("subStatus", subStatus);

		return "subaudit/subaudit_list";
	}


	/**
	 *
	 * @Description: TODO(返回验证列表数据 -- 根据条件查询）
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch", method=RequestMethod.POST)
    public @ResponseBody String queryBySearch(SubAuditQueryBean sqb, HttpServletRequest request){
		try {
			// 获取补贴类型
			String subStatus =  sqb.getSubStatus();
			putModel("subStatus", subStatus);

			// 获取参数
			Map<String, Object> map = auditToolService.getParamsMap(sqb);

			//设定分页,排序
			setCommParameters(request, map);

			//获取查询结果
			List<SubAuditDTO> list = auditToolService.getBySearch(map);
			int total = auditToolService.getTotal(map);

			map.put("total", total);
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			logger.trace("补贴查询出现异常:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开详细页, 查看订单详细信息和进行补贴审批操作
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="{auditId}/verify", method=RequestMethod.GET)
    public String editbyid(@PathVariable("auditId") Integer auditId, HttpServletRequest request){
		try {
			SubAuditEntity sae = auditToolService.getSubAuditById(auditId);

			// 获取订单详细信息
			OrderBaseinfoDTO orderBaseinfoDTO = orderBaseinfoToolService.getByOrderNo(sae.getOrderNo());
			orderBaseinfoDTO = auditToolService.convertorOfUI(orderBaseinfoDTO);
			Map<String, Object> detailmap = new HashMap<String, Object>();
			detailmap.put("orderNo", orderBaseinfoDTO.getOrderNo());
			orderBaseinfoDTO.setDetailList(orderProductDetailToolService.getListByOrderNo(detailmap));

			PaySerialnumberDTO paySerialnumberDTO = paySerialnumberToolService.getByOrderNo(orderBaseinfoDTO.getOrderNo());
			if(null != paySerialnumberDTO)orderBaseinfoDTO.setPaySerialnumberDTO(paySerialnumberDTO);

			orderBaseinfoDTO.setAuditLogList(auditLogToolService.getListByOrderNo(orderBaseinfoDTO.getOrderNo()));

			// 订单详细信息
			convertorOfUserSubStatusAsUIStr(orderBaseinfoDTO);		// 设置买家卖家供应商的(系统)补贴审核状态
			putModel("dto", orderBaseinfoDTO);

			// 车辆照片显示
			OrderOutmarketImageDTO outmarketImage = auditToolService.getOutmarketImage(sae.getOrderNo());

			putModel("auditId", sae.getAuditId());
			putModel("orderNo", sae.getOrderNo());
			//putModel("aLogs", aLogs);
			putModel("subStatus", sae.getSubStatus());
			putModel("subStatusStr", getOrderSubStatusAsUIStr(sae.getSubStatus()));
			putModel("subAmount", sae.getSubAmount());
			putModel("outmarketImage", outmarketImage);

			return "subaudit/subaudit_verify";
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return null;
	}

	/**
	 * 提交审核
	 * @param request
	 * @return
	 */
	@RequestMapping(value="verify/save", produces="text/html;charset=UTF-8", method=RequestMethod.POST)
	@ResponseBody
    public String verifySave(Integer auditId, String flag, String subComment, String subStatus, HttpServletRequest request){
		String result = "SUCCESS";
		try {
			// changeSubStatus
			SysRegisterUser user = getUser(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("auditId", auditId);// sub_audit.auditId
			map.put("flag", flag);
			map.put("subComment", subComment);
			//map.put("orderNo", orderNo);
			map.put("uiStauts", subStatus);
			map.put("updateUserId", user.getUserID());
			map.put("auditUserId", user.getUserID());
			map.put("auditUserName", user.getUserCode());
			String hMsg = auditToolService.updateSubStatusById(map);
			if(null != hMsg){
				result = hMsg;
			}
		} catch (Exception e) {
			logger.trace("verifySave error!" + e.getMessage());
			result = "操作失败：" + e.getMessage();
		}

		return result;
	}

	@RequestMapping(value="verifyBatch/save",produces="text/html;charset=UTF-8", method=RequestMethod.POST)
	public @ResponseBody String verifyBatchSave(HttpServletRequest request){
		String result = null;
		SysRegisterUser user = getUser(request);
		if(null == user){
			return "用户登录失效，请重新登录！";
		}

		String keys[] = request.getParameter("keys").split(",");

		List<Integer> ids = new ArrayList<Integer>();
		Map<String, Object> params = new HashMap<String, Object>();

		for(String id: keys){
			ids.add(Integer.valueOf(id));
		}
		params.put("updateUserId", user.getUserID());
		params.put("auditUserName", user.getUserCode());

		try {
			result = auditToolService.batchUpdateSubStatusAsPass(ids, params);
		} catch (Exception e) {
			result = "批量审批出现错误：" + e.getMessage();
			logger.trace(result);
		}

		return result;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(SubAuditQueryBean sqb, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String subStatus =  sqb.getSubStatus();
			// 空值或0表示查询“全部补贴验证订单”
			if(subStatus!=null && !"".equals(subStatus) && !"0".equals(subStatus)) {
				map.put("subStatus", subStatus);
			}

			map.put("orderNo", sqb.getOrderNo().trim());
			map.put("orderAmount", sqb.getOrderAmount());
			map.put("payType", sqb.getPayType().trim());
			map.put("buyerName", sqb.getBuyerName().trim());
			map.put("buyerShop", sqb.getBuyerShop().trim());
			map.put("orderLike", sqb.getOrderLike().trim());

			int total = auditToolService.getTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 导出数据到excel
	 * @param sqb
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="exportData")
	public String exportData(SubAuditQueryBean sqb, HttpServletRequest request, HttpServletResponse response){
		OutputStream out = null;
		try {

			Map<String, Object> map = auditToolService.getParamsMap(sqb);	// 设置查询参数
			List<SubAuditDTO> list = auditToolService.getBySearch(map);		//获取查询结果

			//获取模板文件路径
			String templatePath = gdProperties.getProperties().getProperty("SUBAUDIT_TEMPLETE");
			String srcFilePath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/" + templatePath);
			logger.info("template file path：" + srcFilePath);

			// 设置文件名和头信息
			String destFileName = RandomIdGenerator.random("yyyy-MM-dd-HH-") + ".xls";			//目标文件名
			response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);	// 设置响应
			response.setContentType("application/vnd.ms-excel");

			// 设置列表数据
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", list);

			//获取输出流
			out = response.getOutputStream();
			JxlsExcelUtil.exportReportFromAbsolutePath(srcFilePath, beans, out);
		} catch (Exception e) {
			logger.trace("导出功能(exportData)出现问题!" + e.getMessage());
			e.printStackTrace();
		} finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					logger.trace("关闭输出流错误!" + e.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 将OrderBaseinfoDTO对象中的卖家，卖家，供应商的补贴状态(subStatus)转换成文字
	 * @param ob
	 */
	private void convertorOfUserSubStatusAsUIStr(OrderBaseinfoDTO ob) {
		try {
			ob.setBuySubStatus(ESubStatus.getDescByCode(ob.getBuySubStatus()));
			ob.setSellSubStatus(ESubStatus.getDescByCode(ob.getSellSubStatus()));
			ob.setSuppSubStatus(ESubStatus.getDescByCode(ob.getSuppSubStatus()));
		} catch (Exception e) {
			String err = "订单号:"+ob.getOrderNo()+"买家/卖家/供应商的补贴状态出现异常！";
			logger.trace(err+ e.getMessage(), e.getStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * 返回订单当前的补贴状态文本(补贴状态 1待补贴 2系统驳回 3已补贴 4不予补贴)
	 * @param subStatus
	 */
	private String getOrderSubStatusAsUIStr(String subStatus){
		if("1".equals(subStatus)){
			return "待补贴 ";
		}else if("2".equals(subStatus)){
			return "系统驳回";
		}else if("3".equals(subStatus)){
			return "已补贴";
		}else if("4".equals(subStatus)){
			return "不予补贴";
		}
		return "";
	}

}
