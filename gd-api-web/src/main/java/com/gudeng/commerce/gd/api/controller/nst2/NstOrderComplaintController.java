package com.gudeng.commerce.gd.api.controller.nst2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.NstOrderComplaintToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MailThread;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ThreadPoolSingleton;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst2/orderComplaint")
public class NstOrderComplaintController extends GDAPIBaseController{

	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderComplaintController.class);

	@Autowired
	private NstOrderComplaintToolService nstOrderComplaintToolService;
	
	@Autowired
	private GdProperties gdProperties;
	
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	
	@RequestMapping("save")
	public void add(HttpServletRequest request, HttpServletResponse response, NstOrderComplaintEntity nstOrderComplaintEntity){
		ObjectResult result = new ObjectResult();
		String orderNo = nstOrderComplaintEntity.getOrderNo();
		if(StringUtils.isBlank(orderNo)){
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}
		try{
			NstOrderBaseinfoDTO nstOrderBaseinfoDTO = nstOrderBaseinfoToolService.getByOrderNo(orderNo);
			if(nstOrderBaseinfoDTO == null){
				setResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
				return;
			}
			nstOrderComplaintEntity.setReplyStatus("0");
			nstOrderComplaintEntity.setCreateTime(new Date());
			nstOrderComplaintEntity.setUpdateUserId(nstOrderComplaintEntity.getCreateUserId());
			nstOrderComplaintEntity.setUpdateTime(new Date());
			Long id = nstOrderComplaintToolService.save(nstOrderComplaintEntity);
			
			//发送邮件
			NstOrderComplaintDTO nstOrderComplaintDTO = nstOrderComplaintToolService.getById(id);
			if(nstOrderComplaintDTO != null){
				comlainSendEmail(nstOrderBaseinfoDTO, nstOrderComplaintDTO);
			}
			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		}catch (Exception e) {
			logger.info("[Error]订单投诉异常：" + e.getMessage());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	protected void comlainSendEmail(NstOrderBaseinfoDTO nstOrderBaseinfoDTO, NstOrderComplaintDTO nstOrderComplaintDTO){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String subject = "农速通投诉"+sf.format(new Date());
		
		StringBuilder content = new StringBuilder();
		content.append("订单号："+nstOrderBaseinfoDTO.getOrderNo()+"<br>");
		
		String f_address_detail = nstOrderBaseinfoDTO.getF_address_detail() == null ? "" : nstOrderBaseinfoDTO.getF_address_detail();
		content.append("起运地：" + f_address_detail + "<br>");
		String s_address_detial = nstOrderBaseinfoDTO.getS_address_detail() == null ? "" : nstOrderBaseinfoDTO.getS_address_detail();
		content.append("目的地：" + s_address_detial + "<br>");
		String shipperName = nstOrderBaseinfoDTO.getShipperName() == null ? "" : nstOrderBaseinfoDTO.getShipperName();
		content.append("发货人：" + shipperName + "<br>");
		String shipperMobile = nstOrderBaseinfoDTO.getShipperMobile() == null ? "" : nstOrderBaseinfoDTO.getShipperMobile();
		content.append("发货人手机：" + shipperMobile + "<br>");
		String ownerName = nstOrderBaseinfoDTO.getOwnerName() == null ? "" : nstOrderBaseinfoDTO.getOwnerName();
		content.append("承运人：" + ownerName + "<br>");
		String ownerMobile = nstOrderBaseinfoDTO.getOwnerMobile() == null ? "" : nstOrderBaseinfoDTO.getOwnerMobile();
		content.append("承运人手机：" + ownerMobile + "<br>");
		String goodsName = nstOrderBaseinfoDTO.getGoodsName() == null ? "" : nstOrderBaseinfoDTO.getGoodsName();
		content.append("货物名称：" + goodsName + "<br>");
		Long totalWeight = nstOrderBaseinfoDTO.getTotalWeight() == null ? 0L : nstOrderBaseinfoDTO.getTotalWeight();
		content.append("货物重量："+ totalWeight + "<br>");
		String carNumber = nstOrderBaseinfoDTO.getCarNumber() == null ? "" : nstOrderBaseinfoDTO.getCarNumber();
		content.append("车牌号：" + carNumber + "<br>");
		String complaintDetail = nstOrderComplaintDTO.getComplaintsDetails() == null ? "" : nstOrderComplaintDTO.getComplaintsDetails();
		content.append("投诉内容：" + complaintDetail + "<br>");
		String complaintUserName = nstOrderComplaintDTO.getCreateUserName() == null ? "" : nstOrderComplaintDTO.getCreateUserName();
		content.append("投诉人：" + complaintUserName);
		List<String> toMail = new ArrayList<String>();
		toMail.add(gdProperties.getNst400MailAddr());
		
		//发送邮件
		MailThread mailThread=new MailThread(subject, content, toMail);
		ExecutorService service=ThreadPoolSingleton.getSingletonThreadPoolInstance();
		service.submit(mailThread);	
	}
	
}
