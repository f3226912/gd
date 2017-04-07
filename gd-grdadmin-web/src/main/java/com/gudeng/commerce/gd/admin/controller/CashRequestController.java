package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.ProductParamsBean;
import com.gudeng.commerce.gd.admin.service.CashRequestToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.order.dto.CashRequestDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("cash")
public class CashRequestController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CashRequestController.class);

	@Autowired
	private CashRequestToolService cashRequestToolService;
	/**
	 * 跳转提现申请管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("cashRequest")
	public String cashRequest(HttpServletRequest request){
		request.setAttribute("status", 0);
		return "cash/cashRequestList";
	}
	/**
	 * 跳转已经提款页面
	 * @param request
	 * @return
	 */
	@RequestMapping("alreadyPay")
	public String alreadyPay(HttpServletRequest request){
		request.setAttribute("status", 1);
		return "cash/alreadyPayList";
	}

	/**
	 * 查询提现信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("cashList")
	@ResponseBody
	public String cashRequestList(HttpServletRequest request,CashRequestDTO cashRequestDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			map.put("status", cashRequestDTO.getStatus());
			map.put("realName", cashRequestDTO.getRealName());
			map.put("bankCardNo", cashRequestDTO.getBankCardNo());
			map.put("account", cashRequestDTO.getAccount());
			map.put("isABC", cashRequestDTO.getIsABC());
			map.put("applyBeginTime", cashRequestDTO.getApplyBeginTime());
			map.put("applyEndTime", cashRequestDTO.getApplyEndTime());
			map.put("payBeginTime", cashRequestDTO.getPayBeginTime());
			map.put("payEndTime", cashRequestDTO.getPayEndTime());
			//设置总记录数
			map.put("total", cashRequestToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			List<CashRequestDTO> list = cashRequestToolService.getCashRequestInfo(map);

			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转账号流水信息
	 * @param request
	 * @return
	 */
	@RequestMapping("accountFlow")
	public String accountFlow(HttpServletRequest request){
		String cashRequestId=request.getParameter("cashReqId");
		String status=request.getParameter("status");
		request.setAttribute("cashRequestId", cashRequestId);
		request.setAttribute("status", status);
		return "cash/accountFlowList";
	}

	/**
	 * 查询账号流水信息
	 * @param request
	 * @return
	 */
	@RequestMapping("cashAccountFlowList")
	@ResponseBody
	public String cashAccountFlowList(HttpServletRequest request,CashRequestDTO cashRequestDTO) {

		try {
			Map<String, Object> map = new HashMap<>();
			//获取提现申请的Id
			String cashRequestId=request.getParameter("cashRequestId");
			if (StringUtil.isNotEmpty(cashRequestId)) {
				//根据提现申请Id查询
				CashRequestDTO casDto=cashRequestToolService.getCashRequestByCashReqId(cashRequestId);
				//得到提现账号Id
				Integer reqUid=casDto.getReqUid();
				//账户
				map.put("reqUid", reqUid);
				//设置总记录数
				map.put("total", cashRequestToolService.getAccountFolwTotal(map));
				//设定分页,排序
				setCommParameters(request, map);
				List<CashRequestDTO> list = cashRequestToolService.getAccountFlowInfo(map);

				map.put("rows", list);//rows键 存放每页记录 list
				return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 进行提款操作
	 * @param request
	 * @param rMap
	 * @return
	 */
	@RequestMapping("pay")
	public String pay(HttpServletRequest request,ModelMap rMap){
		String cashRequestId=request.getParameter("cashReqId");
		request.setAttribute("cashRequestId", cashRequestId);
		return "cash/confirmPay";
	}


	/**
	 * 按键已提款操作
	 * @return
	 */
	@RequestMapping("alreadyPayOperation")
	@ResponseBody
	public String alreadyPayOperation(HttpServletRequest request,ModelMap rmap){
		//获取流水号 必须输入
		String accountflowId=request.getParameter("accountflowId");
		//获取提现申请的Id
		String cashRequestId=request.getParameter("cashRequestId");

		try {

			if (StringUtil.isEmpty(accountflowId) ||StringUtil.isEmpty(cashRequestId)) {
				rmap.put("isSuccess", "error");
				return JSON.toJSONString(rmap);
			}


			Boolean isExist=existStatementCode(accountflowId);
			if (isExist) {
				rmap.put("isSame", "same");
				return JSON.toJSONString(rmap);
			}

			CashRequestDTO cashRequestDTO=cashRequestToolService.getCashRequestByCashReqId(cashRequestId);

			Map<String, Object> map=new HashMap<>();

			if (cashRequestDTO!=null) {
				//账户id
				map.put("accId", cashRequestDTO.getReqUid());

				//会员id
				map.put("memberId", cashRequestDTO.getMemberId());

				//交易金额
				map.put("cashAmount", cashRequestDTO.getCashAmount());

				//开户账号、收款账号
				map.put("recipientAcc", cashRequestDTO.getBankCardNo());
			}

			//流水号
			map.put("accountflowId", accountflowId);

			//类型是3提现生成流水
			map.put("tradeType", "3");

			//类型是1表示是收入,2表示的是支出
			map.put("peType","2");


			//获取登录用户
			SysRegisterUser sysRegisterUser=getUser(request);

			//当前登录用户Id
			map.put("createUserId",sysRegisterUser.getUserID());

			// 插入提现申请Id
			map.put("cashRequestId", cashRequestId);

			//更新提现表的用户Id
			map.put("updateUserId", sysRegisterUser.getUserID());

			//执行流水插入和账号余额修改 成功返回'success',失败返回'error'
			String isSuccess= cashRequestToolService.flowDisposeAmt(map);

			rmap.put("isSuccess", isSuccess);

			return JSON.toJSONString(rmap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	public String checkExportParams(HttpServletRequest request,HttpServletResponse response,CashRequestDTO cashRequestDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = cashRequestDTO.getApplyBeginTime();
			String endDate = cashRequestDTO.getApplyEndTime();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的申请日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("status", cashRequestDTO.getStatus());
			map.put("realName", cashRequestDTO.getRealName());
			map.put("bankCardNo", cashRequestDTO.getBankCardNo());
			map.put("account", cashRequestDTO.getAccount());
			map.put("isABC", cashRequestDTO.getIsABC());
			map.put("applyBeginTime", cashRequestDTO.getApplyBeginTime());
			map.put("applyEndTime", cashRequestDTO.getApplyEndTime());
			map.put("payBeginTime", cashRequestDTO.getPayBeginTime());
			map.put("payEndTime", cashRequestDTO.getPayEndTime());

			int total = cashRequestToolService.getTotal(map);
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

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request,HttpServletResponse response,CashRequestDTO cashRequestDTO){
		Map<String, Object> map = new HashMap<>();
		map.put("status", cashRequestDTO.getStatus());
		map.put("account", cashRequestDTO.getAccount());
		map.put("realName", cashRequestDTO.getRealName());
		map.put("bankCardNo", cashRequestDTO.getBankCardNo());
		map.put("isABC", cashRequestDTO.getIsABC());
		map.put("applyBeginTime", cashRequestDTO.getApplyBeginTime());
		map.put("applyEndTime", cashRequestDTO.getApplyEndTime());
		map.put("payBeginTime", cashRequestDTO.getPayBeginTime());
		map.put("payEndTime", cashRequestDTO.getPayEndTime());
		map.put("startRow", 0);
		map.put("endRow", 99999999);
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
			ouputStream = response.getOutputStream();
			//查询数据
			List<CashRequestDTO> list = cashRequestToolService.getCashRequestInfo(map);
			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("0".equals(cashRequestDTO.getStatus())?"待提现列表":"已提款列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "提现金额");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "申请时间");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "姓名");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "提现会员账号");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "开户行");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "银行卡号 ");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "手机号码 ");// 填充第一行第八个单元格的内容
				Label label70 = new Label(7, 0, "提现状态 ");// 填充第一行第九个单元格的内容
				Label label80 = new Label(8, 0, "结款时间 ");// 填充第一行第十个单元格的内容
				sheet.addCell(label00);
				sheet.addCell(label10);
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						CashRequestDTO casDto = list.get(i);
						Label label0 = new Label(0, i + 1, casDto.getCashAmount().toString());
						Label label2 = new Label(1, i + 1, casDto.getReqTime()==null?"":time.format(casDto.getReqTime()));
						Label label3 = new Label(2, i + 1, casDto.getRealName());
						Label label5 = new Label(3, i + 1, casDto.getAccount());
						Label label1 = new Label(4, i + 1, casDto.getDepositBankName());
						Label label4 = new Label(5, i + 1, casDto.getBankCardNo());
						Label label6 = new Label(6, i + 1, casDto.getMobile());
						Label label7 = null;
						if ("0".equals(casDto.getStatus())) {
							label7 = new Label(7, i + 1, "待提现");
						} else if ("1".equals(casDto.getStatus())) {
							label7 = new Label(7, i + 1, "已结款");
						}
						Label label8 = new Label(8, i + 1, casDto.getPayingTime()==null?"":time.format(casDto.getPayingTime()));
						sheet.addCell(label0);
						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 判读是否存在流水号
	 * @param accountflowId
	 * @return
	 * @throws Exception
	 */
	private Boolean existStatementCode(String accountflowId) throws Exception{

		Map<String, Object> map =new HashMap<>();
		map.put("accountflowId",accountflowId );
		Long sflowId=cashRequestToolService.getStatementId(map);
		if (sflowId!=null) {
			return true;
		}
		return false;
	}
}
