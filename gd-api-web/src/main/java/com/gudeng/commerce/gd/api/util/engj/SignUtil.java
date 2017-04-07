package com.gudeng.commerce.gd.api.util.engj;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.api.dto.output.ENongResponseDTO;

/**
 * @Description: TODO(E农管家签名验签工具类)
 * @author mpan
 * @date 2016年3月10日 下午4:37:16
 */
public class SignUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * @Title: sign
	 * @Description: TODO(签名)
	 * @param response
	 * @return 返回签名
	 * @throws Exception
	 */
	public static String sign(ENongResponseDTO response) throws Exception {
		String certPath = getRealPath("/cert/szgd.pfx");
		LOGGER.info("E农管家私钥路径：" + certPath);
		String reqdata = JSON.toJSONString(response);
		LOGGER.info("待签名报文：" + reqdata);
		String signmsg = UtilRSA.fileSignMessage(UtilMD5.crypt(reqdata).toLowerCase(), certPath, "12345678");
		LOGGER.info("签名signmsg：" + signmsg);
		return signmsg;
	}
	
	/**
	 * @Title: signReJson
	 * @Description: TODO(签名)
	 * @param response
	 * @return 返回签名后的json报文
	 * @throws Exception
	 */
	public static String signReJson(ENongResponseDTO response) throws Exception {
		response.setSignmsg(sign(response));
		return JSON.toJSONString(response);
	}

	/**
	 * @Title: verify
	 * @Description: TODO(验签)
	 * @param reqdata 需验签的报文
	 * @param signmsg 签名
	 * @return true验签通过  false验签不通过
	 * @throws Exception
	 */
	public static boolean verify(String reqdata, String signmsg) throws Exception {
		String certPath = getRealPath("/cert/TrustEngj.crt");
		LOGGER.info("E农管家公钥路径=" + certPath);
		LOGGER.info("待验签报文：" + reqdata);
		boolean signFlag = UtilRSA.verifySign(UtilMD5.crypt(reqdata).toLowerCase(), signmsg, certPath);
		if (signFlag) {
			LOGGER.info("验签成功");
		} else {
			LOGGER.info("验签失败");
		}
		return signFlag;
	}

	/**
	 * @Title: getRealPath
	 * @Description: TODO(获取当前工程webapp绝对路径)
	 * @param relativePath
	 * @return 
	 */
	private static String getRealPath(String relativePath) {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		LOGGER.info("path=" + path);
		String tempPath = getValidContainerPath(path) + relativePath;
		LOGGER.info("containerPath=" + tempPath);
		if (new File(tempPath).exists()) {
			return tempPath;
		}
		tempPath = getValidLocalPath(path) + relativePath;
		LOGGER.info("localPath=" + tempPath);
		if (new File(tempPath).exists()) {
			return tempPath;
		}
		return null;
	}
	
	private static String getValidLocalPath(String path) {
		path = path.replace("/target/classes/", ""); // 去掉/target/class/
		path = path.replace("file:/", ""); // 去掉file:/
		if (path.substring(0, 1).indexOf("/") != -1) {
			path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
		}
		path += "/src/main/webapp/WEB-INF";
		return path;
	}
	
	private static String getValidContainerPath(String path) {
		path = path.replace("/classes/", ""); // 去掉class\
		return path;
	}
	
	public static void main(String[] args) throws Exception {
		ENongResponseDTO response = new ENongResponseDTO();
		response.setResultcode("123123");
		response.setResultmsg("123123");
		System.out.println(sign(response));
	}

}
