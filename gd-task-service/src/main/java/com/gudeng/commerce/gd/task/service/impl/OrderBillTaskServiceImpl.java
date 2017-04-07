package com.gudeng.commerce.gd.task.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.order.dto.OrderBillImportLlogDTO;
import com.gudeng.commerce.gd.order.service.OrderBillImportLogService;
import com.gudeng.commerce.gd.order.service.OrderBillService;
import com.gudeng.commerce.gd.task.service.OrderBillTaskService;
import com.gudeng.commerce.gd.task.util.DateUtil;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.HtmlUtil;
import com.gudeng.commerce.gd.task.util.MailUtil;
import com.gudeng.commerce.gd.task.util.NetAddrUtil;
import com.gudeng.commerce.gd.task.util.StringUtil;

/**
 * @Description 交易账单服务
 * @Project gd-admin-service
 * @ClassName OrderBillService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class OrderBillTaskServiceImpl implements OrderBillTaskService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	public HttpClient httpClient = null; // HttpClient实例
	public GetMethod getMethod = null; // GetMethod实例
	private OrderBillService orderBillService;
	private OrderBillImportLogService orderBillImportLogService;
	// 账单excel所在的文件夹路径
	private static String orderBilldir;
	// 通知邮箱
	private static List<String> mails = new ArrayList<>();
	// 当天存放文件的文件夹
	@SuppressWarnings("unused")
	private static String folder = null;
	private static String hostAddress = null;
	// 文件前缀
	private static String filePrex = "gdkj";
	// 文件前缀
	private static String wangPosFilePrex = "llkj"; // 旺pos对账文件
	// 文件后缀
	private static String fileSuffix = ".txt";

	/**
	 * 初始化前天的年月日及相对应需要导入的文件名
	 */
	static {
		hostAddress = NetAddrUtil.getLocalIP();
		folder = getSomeDay(new Date(), 0);
	}

	/**
	 * 判断昨日市场订单记录是否已经全部导入完成
	 * 
	 * @return false 未全部导入完成，true：已经全部导入完成
	 * @throws Exception
	 * @throws MalformedURLException
	 */
	private boolean doneOk() throws MalformedURLException, Exception {
		// 相对应需要导入的文件名
		String wuhanFileName = new StringBuilder().append(filePrex).append(getSomeDay(new Date(), -1)).append(fileSuffix).toString();// 武汉市场记录文件名
		String luoyangFileName = new StringBuilder().append(filePrex).append(getSomeDay(new Date(), -1)).append("_ly").append(fileSuffix).toString();// 洛阳市场记录文件名
		String enshiFileName = new StringBuilder().append(filePrex).append(getSomeDay(new Date(), -1)).append("_es").append(fileSuffix).toString();// 恩施市场记录文件名
		String nnccbFileName = new StringBuilder().append(filePrex).append(getSomeDay(new Date(), -1)).append("_NNCCB").append(fileSuffix).toString();// 南宁建行记录文件名
		String gxrcbFileName = new StringBuilder().append(filePrex).append(getSomeDay(new Date(), -1)).append("_GXRCB").append(fileSuffix).toString();// 广西农信社记录文件名
		String wangPosFileName = new StringBuilder().append(wangPosFilePrex).append(getSomeDay(new Date(), -1)).append(fileSuffix).toString();// 旺pos记录文件名
		// 查找今日导入记录，若今日文件已经成功导入，则不进行任务
		OrderBillImportLlogDTO wuhanBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(wuhanFileName);// 武汉导入记录
		OrderBillImportLlogDTO luoyangBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(luoyangFileName);// 洛阳导入记录
		OrderBillImportLlogDTO enshiBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(enshiFileName);// 恩施导入记录
		OrderBillImportLlogDTO nnccbBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(nnccbFileName);// 南宁建行导入记录
		OrderBillImportLlogDTO gxrcbBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(gxrcbFileName);// 广西农信社导入记录
		OrderBillImportLlogDTO wangPosBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(wangPosFileName);// 旺pos导入记录
		if (wuhanBill != null && luoyangBill != null &&  enshiBill != null && nnccbBill != null && gxrcbBill != null && wangPosBill != null) {
			return true;
		}
		return false;
	}

	/**
	 * @Description 导入账单文件数据到数据库中
	 * @return
	 * @CreationDate 2015年12月22日 下午2:46:06
	 * @Author lidong(dli@gdeng.cn)
	 */
	public boolean importData() {
		try {
			// 检测今天的任务是否已经完成,若已经完成，则直接返回true
//			if (doneOk()) {
//				return true;
//			}
			initBeforeImport();
			List<String> files = getFiles(orderBilldir);
			for (String file : files) {
				if (!((file.startsWith(filePrex) || file.startsWith(wangPosFilePrex)) && file.endsWith(fileSuffix))) {
					continue;
				}
				// 判断文件是否已经插入
				OrderBillImportLlogDTO temp = getHessianOrderBillImportLogService().getOrderBillLogByFileName(file);
				// 如果文件没有插入记录，则进行导入，并插入日志记录
				if (temp == null) {
					boolean success = save(orderBilldir + file, file);
					if (success) {
						temp = new OrderBillImportLlogDTO();
						temp.setFileName(file);
						temp.setFileUrl(orderBilldir + file);
						temp.setRemoteServer(orderBilldir);
						getHessianOrderBillImportLogService().addBillLogDTO(temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Description 读取文件流并持久化数据
	 * @param file
	 * @throws Exception
	 * @CreationDate 2015年12月22日 下午5:13:02
	 * @Author lidong(dli@gdeng.cn)
	 */
	private boolean save(String url, String fileName) {
		int statusCode = 0;
		InputStream input = null;
		BufferedReader reader = null;
		String exceptionStack = null;
		httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
		getMethod.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
		// 执行Get方法并取得返回状态码，200表示正常，其它代码为异常
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200) {
				// 导入失败发送邮件
				String subject = "Exception.定时导入交易订单数据错误";
				String content = hostAddress + "<br>时间:" + DateUtil.getNow() + "<br>:Exception.定时导入交易订单数据错误." + url + "<br>请检查文件是否存在！";
				MailUtil.sendMail(subject, content, mails, null, null, null, null);
				return false;
			}
			// 读取解析结果
			input = getMethod.getResponseBodyAsStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			// 导入失败发送邮件
			String subject = "Exception.定时导入交易订单数据错误";
			String content = hostAddress + "<br>时间:" + DateUtil.getNow() + "<br>:Exception.定时导入交易订单数据错误." + url + "<br>" + e1.getMessage();
			MailUtil.sendMail(subject, content, mails, null, null, null, null);
			return false;
		}
//		int rowNum = 0;// 记录当前读取的行数
		List<OrderBillDTO> list = new ArrayList<>();
		try {
			String charsetName;
			if (!fileName.startsWith(wangPosFilePrex)) {
				charsetName = "UTF-8";
			} else {
				charsetName = "GBK";
			}
			reader = new BufferedReader(new InputStreamReader(input, charsetName));
			String line = null;
			if (!fileName.startsWith(wangPosFilePrex)) {
				line = reader.readLine();
//				rowNum++;
			}
			do { // 如果 line 为空说明读完了
				// 解析字符串，
				OrderBillDTO dto = null;
				line = reader.readLine(); // 读取下一行 **
				dto = readLineToDTO(line, fileName);
//				rowNum++;
				if (dto != null) {
					list.add(dto);
				}
			} while (line != null);
			int row = batchAddDTO(list, url);
			if (row >= 0) {
				// 导入成功发送邮件
				String subject = hostAddress + " Success.定时导入交易订单成功";
				String content = hostAddress + "<br>时间:" + DateUtil.getNow() + "<br>Success.定时导入交易订单成功.导入记录：" + row + "," + url;
				MailUtil.sendMail(subject, content, mails, null, null, null, null);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			exceptionStack = e.getMessage();
			// 导入失败发送邮件
			String subject = hostAddress + " Exception.定时导入交易订单数据错误";
			String content = hostAddress + "<br>时间:" + DateUtil.getNow() + "<br>:Exception.定时导入交易订单数据错误." + url + "<br>" + exceptionStack;
			MailUtil.sendMail(subject, content, mails, null, null, null, null);
			return false;
		}
	}

	private int batchAddDTO(List<OrderBillDTO> list, String url) {
		int row = -1;
		if (list == null || list.size() == 0) {
			return 0;
		}
		try {
			row = getHessianOrderBillService().batchAddDTO(list);
		} catch (Exception e) {
			// 导入失败发送邮件
			String subject = hostAddress + " Exception.定时导入交易订单数据错误";
			String content = hostAddress + "<br>时间:" + DateUtil.getNow() + "<br>:Exception.定时导入交易订单数据错误." + url + "<br>报错：" + e.getMessage();
			MailUtil.sendMail(subject, content, mails, null, null, null, null);
		}
		return row;
	}

	/**
	 * @Description 解析字符串，将读取到的行流转换为DTO
	 * @param line
	 * @return
	 * @CreationDate 2015年12月22日 下午4:33:49
	 * @Author lidong(dli@gdeng.cn)
	 * @modify 新增银行流水表第13列：payChannelCode ，支付渠道编码 @Author dli@gdeng.cn 2016/11/16 16:18
	 */
	private OrderBillDTO readLineToDTO(String line, String fileName) {//2016年7月25日16:50:53 增加replaceAllEmptyStr 方法，处理掉txt中所有的 空格(" ")和制表符("	")
		OrderBillDTO dto = null;
		if (StringUtils.isNotEmpty(line)) {
			dto = new OrderBillDTO();
			String[] strs = line.split("\\|");
			
			String businessNo, businessName, tradeType, dayStr, timeStr, cardNo, clientNo, sysRefeNo, cardType, tradeDayStr, payChannelCode;
			Double tradeMoney, fee;
			Long marketId;
			/**
			 * 新增旺pos对账单入库方式
			 */
			if (!fileName.startsWith(wangPosFilePrex)) {
				businessNo = StringUtil.replaceAllEmptyStr(strs[0]);
				businessName = StringUtil.replaceAllEmptyStr(strs[1]);
				tradeType = StringUtil.replaceAllEmptyStr(strs[2]);
				dayStr = StringUtil.replaceAllEmptyStr(strs[3]);
				timeStr = StringUtil.replaceAllEmptyStr(strs[4]);
				cardNo = StringUtil.replaceAllEmptyStr(strs[5]);
				clientNo = StringUtil.replaceAllEmptyStr(strs[6]);
				tradeMoney = Double.parseDouble(StringUtil.replaceAllEmptyStr(strs[7]));
				sysRefeNo = StringUtil.replaceAllEmptyStr(strs[8]);
				cardType = StringUtil.replaceAllEmptyStr(strs[9]);
				tradeDayStr = StringUtil.replaceAllEmptyStr(strs[3]) + StringUtil.replaceAllEmptyStr(strs[4]);
				marketId = Long.valueOf(StringUtil.replaceAllEmptyStr(strs[10]));
				fee = Double.valueOf(StringUtil.replaceAllEmptyStr(strs[11]));
				if (strs.length >= 13) {
					payChannelCode = StringUtil.replaceAllEmptyStr(strs[12]); //支付渠道编码
				}else {
					payChannelCode = "enong";//支付渠道编码,银行流水文件如果没有支付渠道数据列，临时设值：E农，
				}
			} else {
				businessNo = StringUtil.replaceAllEmptyStr(strs[0]);
				businessName = null;
//				tradeType = StringUtil.replaceAllEmptyStr(strs[3]);
				tradeType = "订单支付"; // 对账文件的有问题，默认写死
				dayStr = StringUtil.replaceAllEmptyStr(strs[4]);
				timeStr = StringUtil.replaceAllEmptyStr(strs[5]);
				cardNo = StringUtil.replaceAllEmptyStr(strs[2]);
				clientNo = StringUtil.replaceAllEmptyStr(strs[1]);
				tradeMoney = Double.parseDouble(StringUtil.replaceAllEmptyStr(strs[7]));
				sysRefeNo = StringUtil.replaceAllEmptyStr(strs[11]);
				cardType = StringUtil.replaceAllEmptyStr(strs[10]);
				tradeDayStr = StringUtil.replaceAllEmptyStr(strs[4]) + StringUtil.replaceAllEmptyStr(strs[5]);
				marketId = 1L; // 默认白沙洲市场
				fee = Double.valueOf(StringUtil.replaceAllEmptyStr(strs[8]));
				payChannelCode = "WANGPOS"; // 默认旺POS
			}
			dto.setBusinessNo(businessNo);// 商户号
			dto.setBusinessName(businessName);// 商户名
			dto.setTradeType(tradeType);// 交易类型
			dto.setDayStr(dayStr);// 交易年月日
			dto.setTimeStr(timeStr);// 交易时分秒
			dto.setCardNo(cardNo);// 交易卡号
			dto.setClientNo(clientNo);// 终端号
			dto.setTradeMoney(tradeMoney);// 交易金额
			dto.setSysRefeNo(sysRefeNo);// 系统参考号
			dto.setCardType(cardType); // 银行卡类别
			dto.setTradeDayStr(tradeDayStr);// 交易时间
			dto.setMarketId(marketId);//市场id
			dto.setFee(fee);//手续费
			dto.setPayChannelCode(payChannelCode);//支付渠道编码
		}
		return dto;
	}

	/**
	 * @Description 获取相对某一时间多少天的时间
	 * @param date
	 * @param day
	 * @return
	 * @CreationDate 2015年12月22日 下午5:03:49
	 * @Author lidong(dli@gdeng.cn)
	 */
	private static String getSomeDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(date);
		return yesterday.replaceAll("-", "").trim();
	}

	private OrderBillService getHessianOrderBillService() throws MalformedURLException {
		if (orderBillService == null) {
			String url = gdProperties.getOrderBillUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBillService = (OrderBillService) factory.create(OrderBillService.class, url);
		}
		return orderBillService;
	}

	private OrderBillImportLogService getHessianOrderBillImportLogService() throws MalformedURLException {
		if (orderBillImportLogService == null) {
			String url = gdProperties.getOrderBillImportLogService();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			orderBillImportLogService = (OrderBillImportLogService) factory.create(OrderBillImportLogService.class, url);
		}
		return orderBillImportLogService;
	}

	/**
	 * @Description 在导入数据之前初始化路径、文件名、邮箱等数据
	 * @CreationDate 2015年12月23日 下午3:22:09
	 * @Author lidong(dli@gdeng.cn)
	 */
	private void initBeforeImport() {
		if (StringUtils.isEmpty(orderBilldir)) {
			orderBilldir = gdProperties.getOrderBillDir();
		}
		if (mails.size() == 0) {
			String mailsStr = gdProperties.getOrderBillTOMail();
			initMails(mailsStr);
		}
	}

	/**
	 * @Description 初始化邮箱
	 * @param mailsStr
	 * @CreationDate 2015年12月23日 下午3:37:27
	 * @Author lidong(dli@gdeng.cn)
	 */
	private static void initMails(String mailsStr) {
		String[] split = mailsStr.split(";");
		if (split != null && split.length > 0) {
			mails.clear();
			for (String mail : split) {
				if (StringUtils.isNotEmpty(mail)) {
					mails.add(mail);
				}
			}
		}
	}

	/**
	 * @Description 获取路径下的所有需要导入的文件
	 * @param url
	 * @return
	 * @CreationDate 2015年12月24日 下午3:26:14
	 * @Author lidong(dli@gdeng.cn)
	 */
	private List<String> getFiles(String url) {
		List<String> files = new ArrayList<>();
		String htmlContent = HtmlUtil.getHtmlContent(url);
		List<String> aTags = HtmlUtil.getATags(htmlContent);
		for (String tag : aTags) {
			if (StringUtils.isNotEmpty(tag) && (tag.contains(filePrex) || tag.contains(wangPosFilePrex)) && tag.contains(fileSuffix)) {
				files.add(HtmlUtil.getAHref(tag));
			}
		}
		return files;
	}

	/**
	 * @Description 人工导入文件
	 * @param fileName
	 * @return
	 * @CreationDate 2015年12月24日 下午5:46:05
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Override
	public String pushImportData(String file, OrderBillImportLlogDTO temp) {
		try {
			// 查找今日导入记录，若今日文件已经成功导入，则不进行任务
			OrderBillImportLlogDTO todayBill = getHessianOrderBillImportLogService().getOrderBillLogByFileName(file);
			if (todayBill != null) {
				return "304";// 已经存在
			}
			initBeforeImport();
			boolean success = save(orderBilldir + file.trim(), file.trim());
			if (success) {
				temp.setFileName(file);
				temp.setFileUrl(orderBilldir + file);
				temp.setRemoteServer(orderBilldir);
				int row = getHessianOrderBillImportLogService().addBillLogDTO(temp);
				if (row >= 0) {
					return "200";// 导入成功
				} else {
					return "500";// 导入失败
				}
			} else {
				return "500";// 导入失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "500";// 导入失败
		}
	}
}
