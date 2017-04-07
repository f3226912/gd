package com.gudeng.commerce.gd.api.controller.v2;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.impl.FileUploadToolServiceImpl;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v2/fileUpload")
public class FileUploadController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	public FileUploadToolServiceImpl fileUploadService; 
	
		   
	@RequestMapping("/uploadImage")
	public void uploadImage( @RequestParam("file") MultipartFile mfile,HttpServletRequest request,
			 HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			result.setObject(fileUploadService.dataToPic(mfile));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("上传图片异常",e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	 }
	
	@RequestMapping("/uploadImageByStr")
	public void uploadImageByStr( @RequestParam("imageStr") String imageStr,HttpServletRequest request,
			 HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			result.setObject(fileUploadService.dataToPic(imageStr));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("上传图片异常",e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	 }
	
	@RequestMapping("/iosUploadImage")
	public void iosUploadImage(	HttpServletRequest request,
			 HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String byteArray=request.getParameter("byteArray");
			result.setObject(fileUploadService.IOSdataToPic(byteArray));
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
		} catch (Exception e) {
			logger.error("上传图片异常",e);
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	 }
}
