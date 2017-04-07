package com.gudeng.commerce.gd.admin.util;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;
import com.gudeng.commerce.gd.customer.service.PushRecordService;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.service.ProductService;

/**
 * 消息推送aop拦截
 * 
 * @author lf
 * 
 */
@Component
@Aspect
public class MessageAopUtil {

	@Autowired
	public GdProperties gdProperties;

	private static PushRecordService pushRecordService;
	
	private static ProductService productService;
	
	private static MemberCertifiService memberBaseinfoService;
	
	private static BusinessBaseinfoService businessBaseinfoService;

	/**
	 * 功能描述: businessBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected BusinessBaseinfoService getHessianBusinessBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getBusinessBaseinfoUrl();
		if(businessBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}

	
	protected PushRecordService getPushRecToolService() throws MalformedURLException {
		String url = gdProperties.getPushRecUrl();

		if (pushRecordService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushRecordService = (PushRecordService) factory.create(PushRecordService.class, url);
		}
		return pushRecordService;
	}

	/**
	 * 功能描述:产品接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ProductService getHessianProductService()
			throws MalformedURLException {
		String url = gdProperties.getProductUrl();
		if (productService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productService = (ProductService) factory.create(
					ProductService.class, url);
		}
		return productService;
	}
	
	/**
	 * 功能描述: memberBaseinfoService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected MemberCertifiService getHessianMemberCertifiService() throws MalformedURLException {
		String url = gdProperties.getMemberCertifiUrl();
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberCertifiService) factory.create(MemberCertifiService.class, url);
		}
		return memberBaseinfoService;
	}

	//审核信息通知
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.AuditInfoToolServiceImpl.addAuditInfoDTO(..))")
	public void sendMessagePayFail(JoinPoint jp) {
		try {
			Object[] objects = jp.getArgs();
			if (objects.length > 0) {
				AuditInfoDTO auditDTO = (AuditInfoDTO) objects[0];
				String memberId = "";
				PushRecordEntity prEntity = new PushRecordEntity();
				if("1".equals(auditDTO.getType())){
					if("0".equals(auditDTO.getStatus())){
						ProductDto dto = getHessianProductService().getById(String.valueOf(auditDTO.getMainId()));
						if(null != dto){
							BusinessBaseinfoDTO binfoDTO = getHessianBusinessBaseinfoService().getById(String.valueOf(dto.getBusinessId()));
							if(null != binfoDTO){
								memberId = binfoDTO.getUserId().toString();
							}
						}
						//审核商品不通过
						prEntity.setTitle("产品发布审核未通过");
						prEntity.setContent("尊敬的用户：<br /> 您发布的供应产品由于“"+ auditDTO.getReason()+ "  " + auditDTO.getOtherReason() +"” 以致审核未通过，请重新发布。查看详情请点击未上架的产品。");
						prEntity.setType("1");
						prEntity.setReceiveType("1");
						if(!"".equals(memberId)){
							prEntity.setMemberId(Long.valueOf(memberId));
						}
						prEntity.setState("0");//新增均为未读
						prEntity.setCreateUserId(auditDTO.getCreateUserId());
						prEntity.setCreateTime(new Date());
						prEntity.setOrigin(1);//管理后台发布，写死
						getPushRecToolService().add(prEntity);
					}
				}else if("2".equals(auditDTO.getType())){
					MemberCertifiDTO dto = getHessianMemberCertifiService().getById(String.valueOf(auditDTO.getMainId()));
					if(null != dto){
						memberId = dto.getMemberId().toString();
					}
					//审核店铺
					if("2".equals(auditDTO.getStatus())){
						//如果审核不通过,发送站内信
						prEntity.setTitle("实名认证审核未通过");
						prEntity.setContent("尊敬的用户：<br/>您提交的实名认证由于“身份证与姓名不符”以致审核未通过，请重新提交。");
						prEntity.setType("1");
						prEntity.setReceiveType("1");
						if(!"".equals(memberId)){
							prEntity.setMemberId(Long.valueOf(memberId));
						}
						prEntity.setState("0");//新增均为未读
						prEntity.setCreateUserId(auditDTO.getCreateUserId());
						prEntity.setCreateTime(new Date());
						prEntity.setOrigin(1);//管理后台发布，写死
						getPushRecToolService().add(prEntity);
					}else if("1".equals(auditDTO.getStatus())){
						//如果审核通过,发送站内信
						prEntity.setTitle("实名认证审核通过");
						prEntity.setContent("尊敬的用户：<br/>您提交的实名认证已经通过，恭喜您成为我们的实名认证用户，您的信息将得到更多人信任，感谢您的支持！");
						prEntity.setType("1");
						prEntity.setReceiveType("1");
						if(!"".equals(memberId)){
							prEntity.setMemberId(Long.valueOf(memberId));
						}
						prEntity.setState("0");//新增均为未读
						prEntity.setCreateUserId(auditDTO.getCreateUserId());
						prEntity.setCreateTime(new Date());
						prEntity.setOrigin(1);//管理后台发布，写死
						getPushRecToolService().add(prEntity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.ProductToolServiceImpl.updateProduct(java.util.Map))")
	public void sendMessageProductOffLine(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		Map map = (Map) objects[0];
		//下架通知
		if (map != null && map.get("state") != null && "4".equalsIgnoreCase(String.valueOf(map.get("state")))){
			String productId = String.valueOf(map.get("productId"));
			String memberId = "";
			try {
				ProductDto dto = getHessianProductService().getById(productId);
				if(null != dto){
					BusinessBaseinfoDTO binfoDTO = getHessianBusinessBaseinfoService().getById(String.valueOf(dto.getBusinessId()));
					if(null != binfoDTO){
						memberId = binfoDTO.getUserId().toString();
					}
				}
				PushRecordEntity prEntity = new PushRecordEntity();
				prEntity.setTitle("商品下架通知");
				prEntity.setContent("尊敬的用户：<br/>您发布的"+ dto.getProductName() + "产品已到期下架，您可以进行上架处理。查看详情请点击未上架的产品。");
				prEntity.setType("1");
				prEntity.setReceiveType("1");
				if(!"".equals(memberId)){
					prEntity.setMemberId(Long.valueOf(memberId));
				}
				prEntity.setState("0");//新增均为未读
				prEntity.setCreateUserId(String.valueOf(map.get("updateUserId")));
				prEntity.setCreateTime(new Date());
				prEntity.setOrigin(1);//管理后台发布，写死
				getPushRecToolService().add(prEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
	
}
