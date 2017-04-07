package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.CommonUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.JavaMd5;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBrowseMarketHistoryDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBusinessInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

@Service
public class MemberToolServiceImple implements MemberToolService {
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(MemberToolServiceImple.class);

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	private static MemberBaseinfoService memberBaseinfoService;
	
	protected MemberBaseinfoService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getMemberUrl();
		if(memberBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberBaseinfoService = (MemberBaseinfoService) factory.create(MemberBaseinfoService.class, url);
		}
		return memberBaseinfoService;
	}
	
	@Override
	public MemberBaseinfoDTO getByAccount(String account) throws Exception {
		return getHessianMemberService().getByAccount(account);
	}

	@Override
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception {
		return getHessianMemberService().getByMobile(mobile);
	}

	@Override
	public int addMember(MemberBaseinfoDTO mb) throws Exception {
		return getHessianMemberService().addMemberBaseinfoDTO(mb);
	}

	@Override
	public int updateMember(MemberBaseinfoDTO mb) throws Exception {
		return getHessianMemberService().updateMemberBaseinfoDTO(mb);
	}

	@Override
	public MemberBaseinfoDTO getById(String id) throws Exception {
		return getHessianMemberService().getById(id);
	}

	@Override
	public int updateMemberApp(MemberBaseinfoDTO memberDTO) throws Exception {
		return getHessianMemberService().updateMemberApp(memberDTO);
	}

	@Override
	public MemberBrowseMarketHistoryDTO getBrowseHistoryByMemberId(Long memberId) throws Exception {
		return getHessianMemberService().getBrowseHistoryByMemberId(memberId);
	}

	@Override
	public int addBrowseHistory(MemberBrowseMarketHistoryDTO dto) throws Exception {
		return getHessianMemberService().addBrowseHistory(dto);
	}

	@Override
	public boolean isAccExisted(String account) throws Exception {
		//手机账号是否已存在
		MemberBaseinfoDTO dto = getByMobile(account);
		if(dto != null){
			return true;
		}
		//检查账号是否存在
		dto = getByAccount(account);
		if(dto != null){
			return true;
		}
		return false;
	}
	
	@Override
	public MemberBaseinfoDTO checkAccExisted(String account) throws Exception {
		//手机账号是否已存在
		MemberBaseinfoDTO dto = getByMobile(account);
		if(dto != null){
			return dto;
		}
		//检查账号是否存在
		dto = getByAccount(account);
		if(dto != null){
			return dto;
		}
		return null;
	}

	@Override
	public MemberBaseinfoDTO getAppById(String id) throws Exception {
		return getHessianMemberService().getAppById(id);
	}

	@Override
	public MemberCertifiDTO getByUserId(Long memberId) throws Exception {
		return getHessianMemberService().getByUserId(memberId);
	}

	@Override
	public List<MemberBaseinfoDTO> getCompany() throws Exception {
		return getHessianMemberService().getCompany();
	}
    
		@Override
	public void getlink(Map<String, Object> map) throws Exception {
		 getHessianMemberService().getlink(map);
		
	}

	@Override
	public int getActityNo(String actityNo) throws Exception {
		return getHessianMemberService().getActityNo(actityNo);
	}

	@Override
	public int addInt(Map<String, Object> map) throws Exception {
		return getHessianMemberService().addIntss(map);
	}
	
	@Override
	public int getRecordByMemberInfo(Map<String, Object> map) throws Exception {
		return getHessianMemberService().getRecordByMemberInfo(map);
	}

	@Override
	public MemberBaseinfoDTO getContacts(String string) throws Exception {
		return getHessianMemberService().getContacts( string);
	}
	
	@Override
	public MemberBaseinfoDTO getByMobileAndLevel(String username, Integer level) throws Exception{
		return getHessianMemberService().getByMobileAndLevel(username,level);
	}

	@Override
	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity memberEntity) throws Exception {
		return getHessianMemberService().addMemberBaseinfoEnt(memberEntity);
	}
	
	@Override
	public Long addMemberEnt(MemberBaseinfoDTO memberDto) throws Exception {
		return getHessianMemberService().addMember(memberDto);
	}

	@Override
	public List<MemberBaseinfoDTO> getCompanyByMcity(
			MemberAddressDTO memberAddressDTO) throws Exception {
		return getHessianMemberService().getCompanyByMcity(memberAddressDTO);
	}

	@Override
	public List<PushNstMessageInfoDTO> getPushMessageInfo(
			PushNstMessageDTO pushNstMessageDTO) throws Exception {
		return getHessianMemberService().getPushMessageInfo(pushNstMessageDTO);
	}

	@Override
	public List<PushNstMessageDTO> getPushMessage(
			MemberBaseinfoDTO memberDtoInput) throws Exception {
		return getHessianMemberService().getPushMessage(memberDtoInput);
	}
	
	@Override
	public List<PushNstMessageInfoDTO> getPushMessageInfoCarLine(
			PushNstMessageDTO pushNstMessageDTO) throws Exception {
		return getHessianMemberService().getPushMessageInfoCarLine(pushNstMessageDTO);
	}

	@Override
	public int getPushMessageCount(MemberBaseinfoDTO memberDtoInput)
			throws Exception {
		return getHessianMemberService().getPushMessageCount(memberDtoInput);
	}

	@Override
	public int updateNstEvaluate(Long memberId, Integer nstEvaluate) throws Exception {
		return getHessianMemberService().updateNstEvaluate(memberId, nstEvaluate);
	}
	@Override
	public int updateNsyUserType(Long memberId, Integer nsyUserType) throws Exception {
		return getHessianMemberService().updateNsyUserType(memberId, nsyUserType);
	}
	@Override
	public List<MemberBaseinfoDTO> queryMemberListByMap(MemberBaseinfoDTO memberDtoInput) throws Exception {
		return getHessianMemberService().queryMemberListByMap(memberDtoInput);
	}

	@Override
	public int addReMemCust(ReMemForCustDTO reMemForCust) throws Exception {
		return getHessianMemberService().addReMemForCust(reMemForCust);
	}

	@Override
	public MemberBusinessInfoDTO getMemberBusinessInfo(Map<String, Object> param) throws MalformedURLException {
		return getHessianMemberService().getMemberBusinessInfo(param);
	}
	
	@Override
	public int updateReMemCust(ReMemForCustDTO reMemForCust) throws Exception {
		return getHessianMemberService().updateReMemForCust(reMemForCust);
	}

	@Override
	public Long autoRegister(String mobile, String realName, String resigerType)
			throws Exception {
		MemberBaseinfoEntity memberEntity = new MemberBaseinfoEntity();
		memberEntity.setMobile(mobile);
		memberEntity.setAccount(mobile);
		memberEntity.setRealName(realName);
		memberEntity.setLevel(3);//农商友
		memberEntity.setStatus("1");
		memberEntity.setCreateTime(new Date());
		memberEntity.setRegetype(resigerType);
		//随机生成8位默认密码
		String password = CommonUtils.getEightCode();
		memberEntity.setPassword(JavaMd5.getMD5(password+UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key")).toUpperCase());
		Long mId = addMemberBaseinfoEnt(memberEntity);
		
		//取redis缓存,获取通道号
		String channel = "";
		try{
			Object obj = redisClient.get("GDSMS_CHANNEL");
			channel = obj==null?"":obj.toString();
		}catch(Exception e){
			logger.info("获取redis消息通道出错!");
		}
		String content=null;
		if(Constant.Alidayu.REDISTYPE.equals(channel)){
			content=CommonUtils.alidayuUtil(Constant.Alidayu.MESSAGETYPE4,password);
			
		}else{
			String appDownloadUrl = UserSettingPropertyUtils.getEncrytphrase("gd.app.downloadUrl");
			content = String.format(MessageTemplateEnum.AUTO_REGIST.getTemplate(), password, appDownloadUrl);
		}
		logger.info("##############content"+content);
		CommonUtils.sendMsg(channel, content, mobile); 
		return mId;
	}

	@Override
	public int updateMobile(Long memberId, String mobile) throws Exception {
		return getHessianMemberService().updateMobile(memberId,mobile);
	}

	@Override
	public int updateRealName(Long memberId, String realName) throws Exception {
		return getHessianMemberService().updateRealName(memberId,realName);
	}

	@Override
	public int updatePassword(Long memberId, String password) throws Exception {
		return getHessianMemberService().updatePassword(memberId,password);
	}

	@Override
	public int updateStatus(Long memberId, String status) throws Exception {
		MemberBaseinfoDTO mb=new MemberBaseinfoDTO();
		mb.setMemberId(memberId);
		mb.setStatus(status);
		return getHessianMemberService().updateStatus(mb);
	}
	@Override
	public Integer getAppTypeByMemberId(String memberId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianMemberService().getAppTypeByMemberId(memberId);
	}

	@Override
	public MemberBaseinfoDTO getbyMemberAddressId(String memberAddressId) throws Exception {
		return getHessianMemberService().getbyMemberAddressId(memberAddressId);
	}
}
