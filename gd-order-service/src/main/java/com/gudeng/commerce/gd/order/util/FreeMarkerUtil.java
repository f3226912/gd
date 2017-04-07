package com.gudeng.commerce.gd.order.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Description: TODO(freemarker工具类)
 * @author mpan
 * @date 2015年12月24日 上午8:46:49
 */
public class FreeMarkerUtil {

	private static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);


	/**
	 * 生成模板内容
	 * @param param 填充的内容
	 * @param tempName 模板文件名
	 * @return 填充后的模板内容
	 */
	public static String genContent(Map<String, Object> param, String tempName) {
		try {
			Configuration cfg = ConfigurationFactory.getInstance();
			Template template = null;
			template = cfg.getTemplate(tempName);
			// 将模板和数据模型合并
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter bos = new BufferedWriter(
					new OutputStreamWriter(baos));
			template.process(param, bos);
			bos.flush();
			return baos.toString();
		} catch (TemplateException e) {
			logger.error("初始化freemarker模板路径出错", e);
		} catch (IOException e) {
			logger.error("IO异常", e);
		}
		return null;
	}

}
