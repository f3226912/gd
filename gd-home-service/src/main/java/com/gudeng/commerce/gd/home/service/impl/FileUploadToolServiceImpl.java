package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.home.service.FileUploadToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.supplier.service.FileUploadService;


/**
 * 图片上传接口实现
 */
@Service
public class FileUploadToolServiceImpl implements FileUploadToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static FileUploadService fileUploadService;

	protected FileUploadService getHessianFileUploadService()
			throws MalformedURLException {
		String url = gdProperties.getFileUploadServiceUrl();
		if (fileUploadService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			fileUploadService = (FileUploadService) factory.create(
					FileUploadService.class, url);
		}
		return fileUploadService;
	}

	/**
	 * 上传图片，同时根据上传的图片生成多个不同尺寸的图片，并返回上传后的图片的路径.目前提供的尺寸包括650*650,400*400,160*160,
	 * 120*120,60*60,见配置文件upload.properties
	 * 
	 * @param data
	 *            图片二进制流
	 * @param fileName
	 *            图片名称
	 * @return 
	 *         返回图片上传后的地址：http://wwww.domain.com/img/xxx.jpg,注：其他尺寸的图片地址为http://wwww
	 *         .domain.com/img/xxx650_650.jpg,http://wwww
	 *         .domain.com/img/xxx400_400.jpg等
	 * @throws Exception
	 * @author 李冬
	 * @time 2015年10月16日 上午8:55:40
	 */
	public String uploadImgfile(byte[] data, String fileName)
			throws Exception {
		// 读取配置中文件上传路径
		String fileUploadUrl = gdProperties.getFileUploadUrl();
		// 读取配置中图片尺寸列表
		String imgSize = gdProperties.getImgUploadSize();
		return getHessianFileUploadService().uploadImgfile(data,
				fileName, fileUploadUrl, imgSize);
	}
}
