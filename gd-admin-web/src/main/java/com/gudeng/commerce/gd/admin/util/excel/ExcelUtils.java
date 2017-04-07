package com.gudeng.commerce.gd.admin.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gudeng.commerce.gd.admin.util.excel.cb.BeanToSheetCallBack;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *	使用示例可参照  ProductController#exportData
 */
public class ExcelUtils {
	
	public static WritableSheet newLine(WritableSheet sheet, String[] elements, int lineNum) throws RowsExceededException, WriteException{
		int len = elements.length;
		if (len <= 0){ return sheet; }
		for (int columnNum = 0; columnNum < len; columnNum++){
			// 第一个参数表示列, 第二个参数表示行
			Label label = new Label(columnNum, lineNum, elements[columnNum]);
			sheet.addCell(label);
		}
		return sheet;
	}
	
	public static void preProcessResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException{
		// 设置输出响应头信息，
		response.setContentType("application/vnd.ms-excel");
		// 文件名
		/*String convertedFileName  = new String(fileName.getBytes(), "ISO8859-1");*/
		String convertedFileName = java.net.URLEncoder.encode(fileName, "UTF-8") ;
		// 
		response.setHeader("Content-disposition", "attachment;filename=" + convertedFileName + ".xls");
	}
	
	public static <T> void export(String sheetName, OutputStream ouputStream, List<T> beanList, BeanToSheetCallBack<T> callback) throws IOException, RowsExceededException, WriteException{
		// 从输出流中创建一个新的写入工作簿
		WritableWorkbook wwb = Workbook.createWorkbook(ouputStream);
		if (wwb != null) {
			// 创建一个工作页, 第一个参数的页名, 第二个参数表示该工作页在excel中处于哪一页
			WritableSheet sheet = wwb.createSheet(sheetName, 0);
			// 填充数据
			callback.mapper(beanList, sheet);
			// 将数据写入工作簿
			wwb.write();
		}
		// 关闭工作簿
		wwb.close();
		// 发送缓冲区数据
		ouputStream.flush();
	}

}
