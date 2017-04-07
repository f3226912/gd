package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBrowseMarketHistoryDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBusinessInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

public interface MemberToolService {
	
	public boolean isAccExisted(String account) throws Exception;
	
	public MemberBaseinfoDTO checkAccExisted(String account) throws Exception;

	/**
	 * 根据账号查找用户信息
	 * @param account 账号
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getByAccount(String account) throws Exception;
	
	/**
	 * 根据手机号查找用户信息
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception;

	/**
	 * 增加会员
	 * @param username 账号
	 * @throws Exception
	 */
	public int addMember(MemberBaseinfoDTO mb) throws Exception;
	

	/**
	 *修改会员
	 * @param username 账号
	 * @param password 密码
	 * @throws Exception
	 */
	public int updateMember(MemberBaseinfoDTO mb) throws Exception;
	
	/**
	 * 根据id查找会员信息
	 * @param id 会员id
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getById(String id)throws Exception;
	
    /**
     * add by yanghaoyu ,2015-10-21 个人呢中心更新真名和性别
     * @param memberDTO
     * @return
     * @throws Exception
     */
	public int updateMemberApp(MemberBaseinfoDTO memberDTO) throws Exception;
	
	/**
	 * 根据用户id查找用户浏览市场记录
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemberBrowseMarketHistoryDTO getBrowseHistoryByMemberId(Long memberId) throws Exception;
	
	/**
	 * 插入用户浏览记录
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int addBrowseHistory(MemberBrowseMarketHistoryDTO dto) throws Exception;

	public MemberBaseinfoDTO getAppById(String id) throws Exception;

	//public MemberBaseinfoDTO getCompanyName(Long memberId)  throws Exception;
	public MemberCertifiDTO getByUserId(Long memberId)  throws Exception;

	public List<MemberBaseinfoDTO> getCompany()throws Exception;
    
	public void getlink(Map<String, Object> map)throws Exception;
	
	public int getRecordByMemberInfo(Map<String, Object> map)throws Exception;

	public int getActityNo(String actityNo)throws Exception;

	public int addInt(Map<String, Object> map)throws Exception;

	public MemberBaseinfoDTO getContacts(String string)throws Exception;
	
	/**
	 * 查询门岗权限
	 * @param username
	 * @param level
	 * @return
	 */
	public MemberBaseinfoDTO getByMobileAndLevel(String username, Integer level) throws Exception;

	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity memberEntity) throws Exception;
	
	public Long addMemberEnt(MemberBaseinfoDTO memberDto) throws Exception;
    
	/**
	 * 农速通,获取当前定位城市的物流公司列表, 在发布货源的时候的下拉框
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberBaseinfoDTO> getCompanyByMcity(
			MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 农速通,个人中,获取推送信息,第一页的列表
	 * @param memberDtoInput
	 * @return
	 * @throws Exception
	 */
	public List<PushNstMessageDTO> getPushMessage(
			MemberBaseinfoDTO memberDtoInput)throws Exception;
    
	/**
	 * 农速通,个人中心,获取推送嘻嘻,第二页 ,货源列表
	 * @param pushNstMessageDTO
	 * @return
	 * @throws Exception
	 */
	public List<PushNstMessageInfoDTO> getPushMessageInfo(
			PushNstMessageDTO pushNstMessageDTO)throws Exception;
	
	/**
	 * 农速通,获取推送信息,第二页的线路列表
	 * @param pushNstMessageDTO
	 * @return
	 * @throws Exception
	 */
	public List<PushNstMessageInfoDTO> getPushMessageInfoCarLine(
			PushNstMessageDTO pushNstMessageDTO)throws Exception;
    
	/**
	 * 获取当前推送消息的总数,分页使用
	 * @param memberDtoInput
	 * @return
	 * @throws Exception
	 */
	public int getPushMessageCount(MemberBaseinfoDTO memberDtoInput)throws Exception;
	
	/**
	 * @Title: queryMemberListByMap
	 * @Description: TODO(查询会员信息)
	 * @param memberDtoInput
	 * @return
	 * @throws Exception
	 */
	public List<MemberBaseinfoDTO> queryMemberListByMap(MemberBaseinfoDTO memberDtoInput) throws Exception;
	
	/**
	 * 增加客户关联信息
	 * @param reMemForCust
	 * @return
	 * @throws Exception
	 */
	public int addReMemCust(ReMemForCustDTO reMemForCust) throws Exception;

	/**
	 * 修改农速通会员星级评分
	 * @param memberId
	 * @param nstEvaluate
	 * @return
	 * @throws Exception 
	 */
	public int updateNstEvaluate(Long memberId, Integer nstEvaluate) throws Exception;
	/**
	 * 
	 * @param memberId
	 * @param nsyUserType
	 * @return
	 * @throws Exception
	 */
	public int updateNsyUserType(Long memberId, Integer nsyUserType) throws Exception;
	/**
	 * 
	 * @param param
	 * @return
	 */
	public MemberBusinessInfoDTO getMemberBusinessInfo(Map<String, Object> param)throws Exception;
	
	public int updateReMemCust(ReMemForCustDTO reMemForCust) throws Exception;

	/**
	 * 自动注册
	 * @param mobile 手机号
	 * @param realName 实名
	 * @param resigerType 注册来源
	 * @return memberId 会员id
	 * @throws Exception
	 */
	public Long autoRegister(String mobile, String realName, String resigerType) throws Exception;
	
	/***
	 * 修改手机号
	 * @param memberId,mobile
	 * @return int
	 * @throws Exception 
	 */
	public int updateMobile(Long memberId, String mobile) throws Exception;
	/***
	 * 修改姓名
	 * @param memberId,realName
	 * @return int
	 * @throws Exception 
	 */
	public int updateRealName(Long memberId, String realName) throws Exception;
	/***
	 * 修改密码
	 * @param memberId,password
	 * @return int
	 * @throws Exception 
	 */
	public int updatePassword(Long memberId, String password) throws Exception;
	/**
	 * 启用或者禁用用户
	 *
	 * @param memberId,status
	 * @return int
	 *
	 */
	public int updateStatus(Long memberId, String status) throws Exception;
	
	/***
	 * 根据memberId查询出角色类型
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public Integer getAppTypeByMemberId(String memberId) throws Exception;

	/**
	 * 根据货源ID获取买家用户信息
	 * @param memberAddressId
	 * @return
	 */
	public MemberBaseinfoDTO getbyMemberAddressId(String memberAddressId) throws Exception;
	
}
