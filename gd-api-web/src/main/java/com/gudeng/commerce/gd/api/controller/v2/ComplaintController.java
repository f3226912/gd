package com.gudeng.commerce.gd.api.controller.v2;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.ComplaintToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.entity.ComplaintEntity;

/** 
* @author  bdhuang 
* @date 创建时间：2016年2月22日 上午10:48:08 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("v2/complaint")
public class ComplaintController extends GDAPIBaseController {
	/** 记录日志 */
	private static Logger logger = LoggerFactory
			.getLogger(ComplaintController.class);
	
	@Autowired
	private ComplaintToolService complaintToolService;
	
	@RequestMapping("/addComplaint")
	public void addComplaint(HttpServletRequest request,HttpServletResponse response, ComplaintEntity complaintEntity) throws UnsupportedEncodingException {
		ObjectResult result = new ObjectResult();
//		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getTitle())){
//			setResult(result, ErrorCodeEnum.FAIL.getValue(), "标题不能为空",
//					request, response);
//			return;
//		}
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getContent())){
			setResult(result, ErrorCodeEnum.COMPLAINT_CONTENT_IS_NULL, request, response);
			return;
		}
		if(complaintEntity.getContent().length()>500){
			setResult(result, ErrorCodeEnum.COMPLAINT_CONTENT_OVER_LENGTH, request, response);
			return;
		}
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getMember())){
			setResult(result, ErrorCodeEnum.COMPLAINT_MEMBER_IS_NULL, request, response);
			return;
		}
//		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getContact())){
//			setResult(result, ErrorCodeEnum.FAIL.getValue(), "投诉人手机号不能为空",
//					request, response);
//			return;
//		}
		if(com.gudeng.commerce.gd.api.util.StringUtils.isEmpty(complaintEntity.getSource())){
			setResult(result, ErrorCodeEnum.COMPLAINT_SOURCE_IS_NULL, request, response);
			return;
		}
		try {
			complaintEntity.setCreateTime(new Date());
			complaintEntity.setState("0");
			Long l = complaintToolService.addComplaint(complaintEntity);
			result.setObject(l);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.warn("[ERROR]添加投诉建议异常：", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}
