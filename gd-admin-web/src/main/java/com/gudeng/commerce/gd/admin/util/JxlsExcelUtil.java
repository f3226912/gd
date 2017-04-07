/*
 * 文 件 名:  ExcelUtil.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  panmin
 * 修改时间:  2015-1-19
 * 跟踪单号:  <需求跟踪单号>
 * 修改单号:  <需求修改单号>
 * 修改内容:  <修改内容>
 */
package com.gudeng.commerce.gd.admin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * jxls工具类
 * 
 * @author  panmin
 */
public class JxlsExcelUtil {
	
	/** 
	 * 导出excel报表到目标位置
	 * @param srcFilePath 模板文件绝对路径
	 * @param beans 模板数据
	 * @param destFilePath 目标文件绝对路径
	 * @throws Exception 
	 */
	public static void exportReportFromAbsolutePath(String srcFilePath, Map<String, Object> beans, String destFilePath) throws Exception {
		try {
			createParentDir(destFilePath);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(srcFilePath, beans, destFilePath);
		} catch (Exception e) {
			throw new Exception("导出报表错误", e);
		}
	}
	
	/** 
	 * 导出excel报表到目标位置
	 * @param srcRelativePath 模板文件相对路径
	 * @param beans 模板数据
	 * @param destFilePath 目标文件绝对路径
	 * @throws Exception 
	 */
//	public static void exportReportFromRelativePath(String srcRelativePath, Map<String, Object> beans, String destFilePath) throws Exception {
//		try {
//			createParentDir(destFilePath);
//			String srcFilePath = new UrlResource(parseRelativePath(srcRelativePath)).getFile().getPath();
//			exportReportFromAbsolutePath(srcFilePath, beans, destFilePath);
//		} catch (Exception e) {
//			throw new Exception("导出报表错误", e);
//		}
//	}
	
	/** 
	 * 导出excel报表到输出流
	 * @param srcFilePath 模板文件绝对路径
	 * @param beans 模板数据
	 * @param out 输出流
	 * @throws Exception 
	 */
	public static void exportReportFromAbsolutePath(String srcFilePath, Map<String, Object> beans, OutputStream out) throws Exception {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(srcFilePath));
			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(in, beans);
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			throw new Exception("导出报表错误", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/** 
	 * 导出excel报表到输出流
	 * @param srcRelativePath 模板文件相对路径
	 * @param beans 模板数据
	 * @param out 输出流
	 * @throws Exception 
	 */
//	public static void exportReportFromRelativePath(String srcRelativePath, Map<String, Object> beans, OutputStream out) throws Exception {
//		try {
//			String srcFilePath = new UrlResource(parseRelativePath(srcRelativePath)).getFile().getPath();
//			exportReportFromAbsolutePath(srcFilePath, beans, out);
//		} catch (Exception e) {
//			throw new Exception("导出报表错误", e);
//		}
//	}
	
	/** 
	 * 创建目录
	 * @param filePath
	 */
	private static void createParentDir(String filePath) {
		File parentFile = new File(filePath).getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
	}
	
	/** 
	 * 解析相对路径
	 */
//	private static String parseRelativePath(String srcRelativePath) {
//		List<String> args = new ArrayList<String>();
//		args.add(SystemInfo.getServerName());
//		return MessageFormat.format(srcRelativePath, args.toArray());
//	}

}
