package com.gudeng.commerce.gd.api.service.impl.V3;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.WalletToolService;
import com.gudeng.commerce.gd.api.service.V3.WalletToolServiceV3;
import com.gudeng.commerce.gd.api.service.impl.VerifyCodeServiceImpl;
import com.gudeng.commerce.gd.api.service.impl.VerifyCodeServiceImpl.VERIFY_CODE_TYPE;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.service.AccBankCardInfoService;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO.ACC_STATUS;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO.HAS_BANK_CARDS;
import com.gudeng.commerce.gd.order.service.AccInfoService;
import com.innovane.win9008.exception.BusinessException;

public class WalletToolServiceImpl_V3 implements WalletToolServiceV3{
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(WalletToolServiceImpl_V3.class);

	@Autowired
	private  GdProperties gdProperties;
	@Autowired
	private  VerifyCodeServiceImpl verifyCodeServiceImpl;
	private static AccBankCardInfoService accBankCardInfoService;
	
	private static AccInfoService accInfoService;
	
	protected AccInfoService getWalletService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.wallet.url");
		if(accInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accInfoService = (AccInfoService) factory.create(AccInfoService.class, url);
		}
		return  accInfoService;
	}
	
	protected AccBankCardInfoService getAccBankCardService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.accBankCardInfo.url");
		if(accBankCardInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accBankCardInfoService = (AccBankCardInfoService) factory.create(AccBankCardInfoService.class, url);
		}
		return  accBankCardInfoService;
	}
	@SuppressWarnings("rawtypes")
	public AccInfoDTO  getWalletIndex(Long memberId ) throws MalformedURLException{
		AccInfoDTO accInfoDTO= getWalletService().getWalletIndex(memberId);
		if (null==accInfoDTO) {
			accInfoDTO=new AccInfoDTO();
			accInfoDTO.setAccStatus(ACC_STATUS.VALIDATE.getValue());
			accInfoDTO.setBalAvailable(0.0);
			accInfoDTO.setBalblock(0.0);
			accInfoDTO.setBalTotal(0.0);
			accInfoDTO.setTransPwd("");
			accInfoDTO.setMemberId(memberId.intValue());
			getWalletService().addAccInfo(accInfoDTO);
		}
		
		if (null!=accInfoDTO) {
			if (StringUtils.isBlank(accInfoDTO.getTransPwd())) {
				accInfoDTO.setHasPwd(0);
			}
			else {
				accInfoDTO.setHasPwd(1);
			}
		}
		
		//判斷是否有綁定銀行卡
		List bankCardsLst=getAccBankCardService().getBankCards(memberId);
		if (null==bankCardsLst||bankCardsLst.size()==0) {
			accInfoDTO.setHasBankCards(HAS_BANK_CARDS.NOT_HAS.getValue());
		}
		else {
			accInfoDTO.setHasBankCards(HAS_BANK_CARDS.HAS.getValue());
		}
		accInfoDTO.setTransPwd(null);
		return accInfoDTO;
	}

	@Override
	public int addAccInfo(AccInfoDTO accInfoDTO,MemberBaseinfoDTO memberBaseinfoDTO) throws Exception {
		if (null==accInfoDTO.getMemberId()) {
			throw new BusinessException("memberId不能为空");
		}
		verifyCodeServiceImpl.verifyByMobile(VERIFY_CODE_TYPE.SZZFMM.getValue(),accInfoDTO.getVerifyCode(),memberBaseinfoDTO.getMobile());
		
		AccInfoDTO existAccInfoDTO= getWalletService().getWalletIndex(Long.valueOf(accInfoDTO.getMemberId()));
		if (null==existAccInfoDTO) {
			accInfoDTO.setAccStatus(ACC_STATUS.VALIDATE.getValue());
			return getWalletService().addAccInfo(accInfoDTO);
		}
		else if(StringUtils.isBlank(existAccInfoDTO.getTransPwd())){
			logger.error( "你已经有账户***************,但是交易密码为空"+accInfoDTO);
			return getWalletService().updateTransPwd(Long.valueOf(accInfoDTO.getMemberId()),accInfoDTO.getTransPwd());
		}
		return 0;
	}
 

	@Override
	public Integer validateTransPwd(Long memberId, String transPwd) throws Exception {
		AccInfoDTO accInfoDTO=new AccInfoDTO();
		accInfoDTO.setMemberId(memberId.intValue());
		accInfoDTO.setTransPwd(transPwd);
		AccInfoDTO accInfo = getWalletService().getTransPwd(accInfoDTO);
		//校验交易密码是否存在
		if(StringUtils.isBlank(accInfo.getTransPwd())){
			return -4;
		}
		//交易交易密码是否正确
		if(!accInfo.getTransPwd().equals(transPwd)){
			return -3 ;
		}
		return 0;
	}


	@Override
	public Integer updateTransPwd(AccInfoDTO accInfoDTO,MemberBaseinfoDTO memberBaseinfoDTO)
			throws Exception {
		
		verifyCodeServiceImpl.verifyByMobile(VERIFY_CODE_TYPE.XGZFMM.getValue(),accInfoDTO.getVerifyCode(),memberBaseinfoDTO.getMobile());
		return getWalletService().updateTransPwd(Long.valueOf(accInfoDTO.getMemberId()),accInfoDTO.getTransPwd());
	}
	
	@Override
	public int addAccInfo(AccInfoDTO accInfoDTO, Map map) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer updateTransPwd(AccInfoDTO accInfoDTO, Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
} 
