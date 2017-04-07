package com.gudeng.paltform.sms.montnets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.gudeng.paltform.sms.montnets.common.ISms;
import com.gudeng.paltform.sms.montnets.common.MO_PACK;
import com.gudeng.paltform.sms.montnets.common.MULTIX_MT;
import com.gudeng.paltform.sms.montnets.common.RPT_PACK;
import com.gudeng.paltform.sms.montnets.common.SmsTool;
import com.gudeng.paltform.sms.montnets.common.StaticValue;
import com.gudeng.paltform.sms.montnets.common.ValidateParamTool;

public class MontnetsSMS {

	private static String strUserId = "J20124";

	private static String strPwd = "125263";

	private static String ip = "61.145.229.29";

	private static String port = "7903";

	private static boolean bKeepAlive = false;

	private static HttpClient httpClient = null;

	private static String strSpNumber = "*"; // 请输入通道号，不需要请输入*
	private static String strUserMsgId = "0"; // 不带请输入0（流水号范围-（2^63）……2^63-1）

	// private static Call call = null;

	public static void main(String arg[]) throws IOException {
		String strMessage = "同事您好，感谢您对此次测试的配合。[123456]";
		String[] mobiles = {"15112347541", "18681558780"};
		//StaticValue.ip = ip;
		//StaticValue.port = port;
		String str = "1";
		httpClient = new DefaultHttpClient();
		if (str.equals("1")) {
			// 短信息发送接口（相同内容群发，可自定义流水号）
			sendSms(strMessage, mobiles);
		} else if (str.equals("2")) {
			// 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
			sendMultixSms(mobiles, strMessage);
		} else if (str.equals("3")) {
			// 查询余额接口
			QueryBalance();
		} else if (str.equals("4")) {
			// 获取上行接口
			GetMo();
		} else if (str.equals("5")) {
			// 获取状态报告接口
			GetRpt();
		}
		// 关闭连接
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
		System.out.print("程序已退出！");
	}                            

	/**
	 * 短信息发送接口（相同内容群发，可自定义流水号）
	 * 
	 * @param mobiles
	 *            多个号码用英文逗号分隔，最多100个号码
	 * @param content
	 */
	public static boolean sendSms(String content, String...phones) {
		StaticValue.ip = ip;
		StaticValue.port = port;
		String phone = null;
		try {
			boolean falgp = false;
			do {
				boolean flagg = true;
				if (phones.length > 0 && phones.length <= 100) {
					for (int i = 0; i < phones.length; i++) {
						// 如果输入的对象号码不合法则要重新输入
						if ("".equals(phones[i]) || phones[i].length() != 11 || !SmsTool.isUnSignDigit(phones[i])) {
							flagg = false;
							break;
						}else{
							if(i == 0){
								phone = phones[i];
							}else if(i == phones.length-1){
								phone += ","+phones[i];
							}else{
								phone += ","+phones[i];
							}
						}
					}
					if (flagg) {
						falgp = true;
					} else {
						/*System.out.println("手机号码输入不合法，请重新输入");
						System.out.println("手机号码：");*/
						return false;
					}
				} else {
					/*System.out.println("手机号码个数超过100个，请重新输入");
					System.out.println("手机号码：");*/
					return false;
				}
			} while (!falgp);

			boolean flag3 = false;
			String strMessage;
			do {
				strMessage = content;
				if (ValidateParamTool.validateMessage(strMessage)) {
					flag3 = true;
				} else if (strMessage == null || "".equals(strMessage)) {
					/*System.out.println("短信长度为0，请重新输入");
					System.out.print("短信内容：");*/
					return false;
				} else {
					/*System.out.println("短信长度超过350个汉字，请重新输入");
					System.out.print("短信内容：");*/
					return false;
				}
			} while (!flag3);

			boolean flag4 = false;
			String strSubPort = null;
			do {
				strSubPort = strSpNumber;
				if (ValidateParamTool.validateSubPort(strSubPort)) {
					flag4 = true;
				} else {
					/*System.out.println("扩展子号输入不合法，请重新输入");
					System.out.print("扩展子号：");*/
					return false;
				}
			} while (!flag4);

			boolean flag5 = false;
			do {
				if (ValidateParamTool.validateUserMsgId(strUserMsgId)) {
					flag5 = true;
				} else {
					/*System.out.println("流水号输入不合法，请重新输入");
					System.out.print("流水号：");*/
					return false;
				}
			} while (!flag5);

			ISms sms = new CHttpPost();
			StringBuffer strPtMsgId = new StringBuffer("");
			// 短信息发送接口（相同内容群发，可自定义流水号）POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
			int result = sms.SendSms(strPtMsgId, strUserId, strPwd, phone, strMessage, strSubPort, strUserMsgId,
					bKeepAlive, httpClient);
			if (result == 0) {
				System.out.println("请求成功：" + strPtMsgId.toString());
				/*//请求成功后 查询是否发送成功
				Thread.sleep(2000);
				System.out.println("休眠下下");
				List<RPT_PACK> rptPackList = sms.GetRpt(strUserId, strPwd, bKeepAlive, httpClient);
				new SmsTool().printRptPack(rptPackList);
				if(null != rptPackList && rptPackList.size() != 0){
					for (RPT_PACK rpt_PACK : rptPackList) {
						//说明发送失败
						if(rpt_PACK.getnStatus() != 0){
							System.out.println("请求成功,发送失败："+rpt_PACK.getStrErCode());
							return false;
						}
					}
				}else{
					System.out.println("无法获取状态码");
				}*/
				return true;
			} else {
				System.out.println("发送失败：" + strPtMsgId.toString());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
	 */
	public static boolean sendMultixSms(String[] mobiles, String content) {
		StaticValue.ip = ip;
		StaticValue.port = port;
		// 1: Get请求方式 2: Post请求方式 3: Soap请求方式
		String type = "2";
		String phone = "";
		for (int i = 0; i < mobiles.length; i++) {
			if(i == 0){
				phone = mobiles[i]+",";
			}else if(i == mobiles.length-1){
				phone += mobiles[i];
			}else{
				phone += mobiles[i]+",";
			}
		}
		try {
			
			List<MULTIX_MT> multixMts = new ArrayList<MULTIX_MT>();
			MULTIX_MT multixMt = new MULTIX_MT();
			// 手机号
			multixMt.setStrMobile(phone);
			// 信息内容由GBK编码再转换成base64编码
			multixMt.setStrBase64Msg(content);
			// 下行源地址 这写里可以填扩展号或*号
			multixMt.setStrSpNumber(strSpNumber);
			// 自定义流水号
			multixMt.setStrUserMsgId(strUserMsgId);
			multixMts.add(multixMt);

			if (type.equals("3")) {
				/*
				 * ISms sms = new CHttpSoap(); StringBuffer strPtMsgId = new
				 * StringBuffer(""); //
				 * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）SOAP请求,call为空，则是短连接,call不为空
				 * ，则是长连接。 int result = sms.SendMultixSms(strPtMsgId, strUserId,
				 * strPwd, multixMts, bKeepAlive, call);
				 * 
				 * if (result == 0) { System.out.println("发送成功：" +
				 * strPtMsgId.toString()); } else { System.out.println("发送失败：" +
				 * strPtMsgId.toString()); }
				 */
			} else if (type.equals("2")) {
				ISms sms = new CHttpPost();
				StringBuffer strPtMsgId = new StringBuffer("");
				// 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				int result = sms.SendMultixSms(strPtMsgId, strUserId, strPwd, multixMts, bKeepAlive, httpClient);
				if (result == 0) {
					System.out.println("发送成功：" + strPtMsgId.toString());
					return true;
				} else {
					System.out.println("发送失败：" + strPtMsgId.toString());
					return false;
				}
			} else if (type.equals("1")) {
				/*
				 * ISms sms = new CHttpGet(); StringBuffer strPtMsgId = new
				 * StringBuffer(""); //
				 * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）GET请求,httpClient为空，则是短连接,
				 * httpClient不为空，则是长连接。 int result =
				 * sms.SendMultixSms(strPtMsgId, strUserId, strPwd, multixMts,
				 * bKeepAlive, httpClient); if (result == 0) {
				 * System.out.println("发送成功：" + strPtMsgId.toString()); } else {
				 * System.out.println("发送失败：" + strPtMsgId.toString()); }
				 */
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 查询余额接口
	 */
	public static void QueryBalance() {
		try {
			System.out.println("请选择请求方式：");
			System.out.println("1: Get请求方式");
			System.out.println("2: Post请求方式");
			System.out.println("3: Soap请求方式");
			System.out.print("请求方式：");
			String type = "2";
			if (type.equals("3")) {
				/*
				 * ISms sms = new CHttpSoap(); StringBuffer nBalance = new
				 * StringBuffer(""); // 查询余额接口SOAP请求,call为空，则是短连接,call不为空，则是长连接。
				 * int result = sms.QueryBalance(nBalance, strUserId, strPwd,
				 * bKeepAlive, call); if (result == 0) {
				 * System.out.println("查询成功：" + nBalance.toString()); } else {
				 * System.out.println("查询失败：" + nBalance.toString()); }
				 */

			} else if (type.equals("2")) {
				// 查询余额接口POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				ISms sms = new CHttpPost();
				StringBuffer nBalance = new StringBuffer("");
				int result = sms.QueryBalance(nBalance, strUserId, strPwd, bKeepAlive, httpClient);
				if (result == 0) {
					System.out.println("查询成功：" + nBalance.toString());
				} else {
					System.out.println("查询失败：" + nBalance.toString());
				}
			} else if (type.equals("1")) {
				// 查询余额接口GET请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				/*
				 * ISms sms = new CHttpGet(); StringBuffer nBalance = new
				 * StringBuffer(""); int result = sms.QueryBalance(nBalance,
				 * strUserId, strPwd, bKeepAlive, httpClient); if (result == 0)
				 * { System.out.println("查询成功：" + nBalance.toString()); } else {
				 * System.out.println("查询失败：" + nBalance.toString()); }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取上行接口
	 */
	public static void GetMo() {
		try {
			System.out.println("请选择请求方式：");
			System.out.println("1: Get请求方式");
			System.out.println("2: Post请求方式");
			System.out.println("3: Soap请求方式");
			System.out.print("请求方式：");
			String type = "2";

			if (type.equals("3")) {
				/*
				 * // 获取上行接口SOAP请求,call为空，则是短连接,call不为空，则是长连接。 ISms sms = new
				 * CHttpSoap(); List<MO_PACK> moPackList = sms.GetMo(strUserId,
				 * strPwd, bKeepAlive, call); new
				 * SmsTool().printMoPack(moPackList);
				 */
			} else if (type.equals("2")) {
				ISms sms = new CHttpPost();
				// 获取上行接口POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				List<MO_PACK> moPackList = sms.GetMo(strUserId, strPwd, bKeepAlive, httpClient);
				new SmsTool().printMoPack(moPackList);

			} else if (type.equals("1")) {
				/*
				 * ISms sms = new CHttpGet(); //
				 * 获取上行接口GET请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				 * List<MO_PACK> moPackList = sms.GetMo(strUserId, strPwd,
				 * bKeepAlive, httpClient); new
				 * SmsTool().printMoPack(moPackList);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取状态报告接口
	 */
	public static void GetRpt() {
		try {
			System.out.println("请选择请求方式：");
			System.out.println("1: Get请求方式");
			System.out.println("2: Post请求方式");
			System.out.println("3: Soap请求方式");
			System.out.print("请求方式：");
			String type = "2";

			if (type.equals("3")) {
				/*
				 * ISms sms = new CHttpSoap(); //
				 * 获取状态报告接口SOAP请求,call为空，则是短连接,call不为空，则是长连接。 List<RPT_PACK>
				 * rptPackList = sms.GetRpt(strUserId, strPwd, bKeepAlive,
				 * call); new SmsTool().printRptPack(rptPackList);
				 */
			} else if (type.equals("2")) {
				ISms sms = new CHttpPost();
				// 获取状态报告接口POST请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				List<RPT_PACK> rptPackList = sms.GetRpt(strUserId, strPwd, bKeepAlive, httpClient);
				new SmsTool().printRptPack(rptPackList);
			} else if (type.equals("1")) {
				// 获取状态报告接口GET请求,httpClient为空，则是短连接,httpClient不为空，则是长连接。
				/*
				 * ISms sms = new CHttpGet(); List<RPT_PACK> rptPackList =
				 * sms.GetRpt(strUserId, strPwd, bKeepAlive, httpClient); new
				 * SmsTool().printRptPack(rptPackList);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
