package com.gudeng.commerce.gd.api.service.impl;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.util.CopyOfUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.supplier.service.FileUploadService;
import com.innovane.win9008.exception.BusinessException;

@SuppressWarnings("restriction")
public class FileUploadToolServiceImpl {
	private static Logger logger = LoggerFactory
			.getLogger(FileUploadToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;

	public static FileUploadService fileUploadService;

	protected FileUploadService getFileUploadService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty(
				"gd.fileUploadService.url");
		if (fileUploadService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			fileUploadService = (FileUploadService) factory.create(
					FileUploadService.class, url);
		}
		return fileUploadService;
	}

	/**
	 * IOS上传图片
	 * 
	 * @param source
	 * @param userSessionVO
	 * @return
	 * @throws Exception
	 */
	public String IOSdataToPic(String source) throws Exception {
		String filePath = UserSettingPropertyUtils
				.getEncrytphrase("gd.Imgae.Path");
		String fileName = getImageName();

		CopyOfUtils.dataToPicture(source, filePath + fileName);
		return compressImage(filePath, fileName);
	}

	/**
	 * android用的上传图片方法
	 * 
	 * @param fileInputStream
	 * @param userSessionVO
	 * @return
	 * @throws Exception
	 * @throws MalformedURLException
	 */
	public String dataToPic(MultipartFile mfile) throws MalformedURLException,
			Exception {
		String filePath = UserSettingPropertyUtils
				.getEncrytphrase("gd.Imgae.Path");
		String fileName = getImageName();
		if (null == mfile) {
			throw new BusinessException("上传文件不能为空");
		}
		try {
			mfile.transferTo(new File(filePath + fileName));
		} finally {
		}

		return compressImage(filePath, fileName);
	}

	/**
	 * android用的上传图片方法
	 * 
	 * @param fileInputStream
	 * @param userSessionVO
	 * @return
	 * @throws Exception
	 */
	public String dataToPic(String imageStr) throws Exception {
		// 对字节数组字符串进行Base64解码并生成图片
		if (imageStr == null) // 图像数据为空
			return "";
		String fileName = getImageName();
		String filePath = UserSettingPropertyUtils
				.getEncrytphrase("gd.Imgae.Path");
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imageStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			out = new FileOutputStream(filePath + fileName);
			out.write(b);

		} finally {
			out.flush();
			out.close();
		}
		return compressImage(filePath, fileName);
	}

	private String compressImage(String filePath, String fileName)
			throws Exception {
		String retRul = "";
		InputStream inputStream = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		File file=new File(filePath + fileName);
		try {
			String imgSize = "150,150;160,160;200,200;370,370;650,650;";
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int n = 0;
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
			byte[] data = output.toByteArray();
			retRul = getFileUploadService().uploadImgfile(data, fileName,
					filePath, imgSize);
		} finally {
			inputStream.close();
			output.close();
			//完成之后，把原图删除
			file.delete();
		}
		return retRul;
	}

	private String getImageName() {
		return UUID.randomUUID().toString()+"_"+DateUtil.getSysDateString()+".jpg";
	}

	/**
	 * 返回图片名次
	 * 
	 * @return
	 */
	// private String dirExists(String fileName) {
	//
	// Calendar a = Calendar.getInstance();
	// String filePath = UserSettingPropertyUtils
	// .getEncrytphrase("gd.Imgae.Path");
	// String year = String.valueOf(a.get(Calendar.YEAR));
	// String month = String.valueOf(a.get(Calendar.MONTH) + 1);
	// String day = String.valueOf(a.get(Calendar.DATE));
	// String yearDir = filePath + year;
	// File yearFile = new File(yearDir);
	// if (yearFile.exists()) {
	// } else {
	// logger.info("文件夹不存在，现在创建   " + yearDir);
	// yearFile.mkdir();
	// }
	//
	// String monthDir = filePath + year + "/" + month;
	// File monthFile = new File(monthDir);
	// if (monthFile.exists()) {
	// } else {
	// logger.info("文件夹不存在，现在创建   " + monthFile);
	// monthFile.mkdir();
	// }
	//
	// String dayDir = filePath + year + "/" + month + "/" + day;
	// File dayFile = new File(dayDir);
	// if (dayFile.exists()) {
	// } else {
	// logger.info("文件夹不存在，现在创建   " + dayDir);
	// dayFile.mkdir();
	// }
	// return year + "/" + month + "/" + day + "/" + fileName;
	// }

	public static void main(String[] args) throws IOException {

		String imgFile = "d:\\test\\1449737844202.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

}
