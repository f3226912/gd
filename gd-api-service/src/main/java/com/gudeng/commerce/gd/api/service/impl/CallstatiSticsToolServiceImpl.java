package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.CustInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.CallstatiSticsService;

public class CallstatiSticsToolServiceImpl implements CallstatiSticsToolService {

	private static Logger logger = LoggerFactory.getLogger(CallstatiSticsToolServiceImpl.class);

	public enum SYS_CODE {
		NSY_BUYER("1"), NSY_SELLER("3"), NST("2");

		SYS_CODE(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	private static CallstatiSticsService callstatiSticsService;
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public BusinessBaseinfoToolServiceImpl businessBaseinfoToolServiceImpl;
	@Autowired
	public MemberToolServiceImple memberToolServiceImple;

	/**
	 * 农商友添加电话记录
	 */
	@Override
	public ErrorCodeEnum insert(CallstatiSticsDTO callstatiSticsDTO) throws Exception {
		Long businessId = callstatiSticsDTO.getBusinessId();
		Long memberId = callstatiSticsDTO.getMemberId();

		if(memberId == null || memberId.equals(0)){
			return ErrorCodeEnum.ACTIVE_CALL_MEMBERID_EMPTY;
		}
		
		//被叫号码
		String s_Mobile = "";
		//被叫人姓名
		String s_Name = "";
		//商铺名称
		String shopName = "";
		//被叫人Id
		Long b_meberId = callstatiSticsDTO.getB_memberId();
		//非农速通 查询被叫人信息
		if ((null != businessId || null != b_meberId) 
				&& !"2".equals(callstatiSticsDTO.getSysCode())) {
			BusinessBaseinfoDTO baseinfoDTO = businessBaseinfoToolServiceImpl.getById(businessId + "");
			
			if(baseinfoDTO == null && (callstatiSticsDTO.getSysCode().equals("3") || callstatiSticsDTO.getSysCode().equals("4"))
					&& null != callstatiSticsDTO.getB_memberId()){
				baseinfoDTO = businessBaseinfoToolServiceImpl.getByUserId(b_meberId+"");
			}else{
				if(callstatiSticsDTO.getB_memberId()==null){ //供应商，找买家相关列表，无 被叫人Id，根据商铺Id直接找到对应的农批商用户Id
					if(baseinfoDTO!= null && baseinfoDTO.getUserId() != null){
						callstatiSticsDTO.setB_memberId(baseinfoDTO.getUserId());
					}else{
						callstatiSticsDTO.setB_memberId(null);
					}
					
				}
			}
			if(baseinfoDTO != null){
				s_Mobile = baseinfoDTO.getMobile();
				//change by wangweimin for defect of getting wrong name
				s_Name = baseinfoDTO.getRealName();
				shopName = baseinfoDTO.getShopsName();
			}
		}
		
		//查询主叫人信息
		MemberBaseinfoDTO memberBaseinfoDTO = memberToolServiceImple.getById(memberId+"");
		String e_Mobile = memberBaseinfoDTO.getMobile();
		String e_Name = memberBaseinfoDTO.getRealName();
		
		// add by yanghaoyu,date 2105-10-31 农速通取车主或者货主的电话
		if (null != b_meberId) {
			MemberBaseinfoDTO memberBaseinfoDTO2 = memberToolServiceImple.getById(b_meberId+"");
			
			if(memberBaseinfoDTO2 != null){
				s_Mobile = memberBaseinfoDTO2.getMobile();
				if (memberBaseinfoDTO2.getUserType() != null
						&& memberBaseinfoDTO2.getUserType() == 1) {
					s_Name = memberBaseinfoDTO2.getRealName();
				}
				if (memberBaseinfoDTO2.getUserType() != null
						&& memberBaseinfoDTO2.getUserType() == 2) {
					s_Name = memberBaseinfoDTO2.getCompanyContact();
				}
			}
			//如果b_memberId为空
		}else{
			if(StringUtils.isNotBlank(callstatiSticsDTO.getS_Mobile())){
				MemberBaseinfoDTO memberBaseinfoDTO3 = memberToolServiceImple.getByMobile(callstatiSticsDTO.getS_Mobile());
				if(memberBaseinfoDTO3 != null){
					s_Mobile = memberBaseinfoDTO3.getMobile();
					//如果被叫人ID为空，根据电话号码查询被叫memberId等信息
					callstatiSticsDTO.setB_memberId(memberBaseinfoDTO3.getMemberId());
					if (memberBaseinfoDTO3.getUserType() != null
							&& memberBaseinfoDTO3.getUserType() == 1) {
						s_Name = memberBaseinfoDTO3.getRealName();
					}
					if (memberBaseinfoDTO3.getUserType() != null
							&& memberBaseinfoDTO3.getUserType() == 2) {
						s_Name = memberBaseinfoDTO3.getCompanyContact();
					}
				}
			}
		}
	
		callstatiSticsDTO.setE_Mobile(e_Mobile); //主叫号码
		callstatiSticsDTO.setE_Name(e_Name);     //主叫号姓名
		callstatiSticsDTO.setS_Mobile(s_Mobile); //被叫号码
		callstatiSticsDTO.setS_Name(s_Name);     //被叫人姓名
		callstatiSticsDTO.setShopName(shopName); //商铺名称
		getCallstatiSticsService().insert(callstatiSticsDTO);
		
		return ErrorCodeEnum.SUCCESS;
	}

	protected CallstatiSticsService getCallstatiSticsService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty(
				"gd.callstatiSticsService.url");
		if (callstatiSticsService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			callstatiSticsService = (CallstatiSticsService) factory.create(
					CallstatiSticsService.class, url);
		}
		return callstatiSticsService;
	}

	@Override
	public void insertNst(CallstatiSticsDTO callstatiSticsDTO) throws Exception {
		Long businessId = callstatiSticsDTO.getBusinessId();
		Long memberId = callstatiSticsDTO.getMemberId();

		String s_Mobile = "";
		String s_Name = "";
		String shopName = "";
		if (!SYS_CODE.NSY_SELLER.getValue().equals(
				callstatiSticsDTO.getSysCode())) {
			BusinessBaseinfoDTO baseinfoDTO = businessBaseinfoToolServiceImpl
					.getById(String.valueOf(businessId));

			s_Mobile = baseinfoDTO.getMobile();
			s_Name = baseinfoDTO.getName();
			shopName = baseinfoDTO.getShopsName();
		}
		MemberBaseinfoDTO memberBaseinfoDTO = memberToolServiceImple
				.getById(String.valueOf(memberId));

		String e_Mobile = "";
		String e_Name = "";

		if (memberBaseinfoDTO.getUserType() != null
				&& memberBaseinfoDTO.getUserType() == 1) {
			e_Name = memberBaseinfoDTO.getRealName();
			e_Mobile = memberBaseinfoDTO.getMobile();
		}
		if (memberBaseinfoDTO.getUserType() != null
				&& memberBaseinfoDTO.getUserType() == 2) {
			e_Name = memberBaseinfoDTO.getCompanyContact();
			e_Mobile = memberBaseinfoDTO.getCompanyMobile();
		}

		// add by yanghaoyu,date 2105-10-31 农速通取车主或者货主的电话
		if (SYS_CODE.NSY_SELLER.getValue().equals(
				callstatiSticsDTO.getSysCode())) {
			MemberBaseinfoDTO memberBaseinfoDTO2 = memberToolServiceImple
					.getById(String.valueOf(callstatiSticsDTO.getB_memberId()));

			if (memberBaseinfoDTO2.getUserType() != null
					&& memberBaseinfoDTO2.getUserType() == 1) {
				s_Name = memberBaseinfoDTO2.getRealName();
				s_Mobile = memberBaseinfoDTO2.getMobile();
			}
			if (memberBaseinfoDTO2.getUserType() != null
					&& memberBaseinfoDTO2.getUserType() == 2) {
				s_Name = memberBaseinfoDTO2.getCompanyContact();
				s_Mobile = memberBaseinfoDTO2.getCompanyMobile();
			}

		}
		callstatiSticsDTO.setE_Mobile(e_Mobile);
		callstatiSticsDTO.setE_Name(e_Name);
		callstatiSticsDTO.setS_Mobile(s_Mobile);
		callstatiSticsDTO.setS_Name(s_Name);
		callstatiSticsDTO.setShopName(shopName);
		getCallstatiSticsService().insert(callstatiSticsDTO);

	}

	@Override
	public List<CustInfoDTO> getCust(Map<String, Object> map) throws Exception {
		return getCallstatiSticsService().getCust(map);
	}

	@Override
	public List<CustInfoDTO> getLinkman(Map<String, Object> param)  throws Exception{
		return getCallstatiSticsService().getLinkman(param);
	}

	@Override
	public Long getLinkmanTotal(Map<String, Object> param) throws Exception{
		return getCallstatiSticsService().getLinkmanTotal(param);
	}

	@Override
	public Long getCustTotal(Map<String, Object> param)  throws Exception{
		return getCallstatiSticsService().getCustTotal(param);
	}

	
}
