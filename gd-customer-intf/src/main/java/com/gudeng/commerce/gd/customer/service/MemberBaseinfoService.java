package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBrowseMarketHistoryDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBusinessInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberSinxinDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReMemForCustDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */
public interface MemberBaseinfoService {

	/**
	 * 通过MemberBaseinfo对象插入数据库
	 *
	 * @param MemberBaseinfoDTO
	 * @return int
	 *
	 */
	public int addMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception;

	/**
	 * 通过MemberBaseinfoEntity 对象插入数据库
	 *
	 * 返回当前记录的Id值
	 *
	 * @param MemberBaseinfoEntity
	 * @return Long
	 *
	 */
	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity me) throws Exception;

	/**
	 * 通过ID删除MemberBaseinfo对象
	 *
	 * @param id
	 * @return int
	 *
	 */
	public int deleteById(String id) throws Exception;

	/**
	 * 通过MemberBaseinfo对象更新数据库
	 *
	 * @param MemberBaseinfoDTO
	 * @return int
	 *
	 */
	public int updateMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception;

	/**
	 * 启用或者禁用用户
	 *
	 * @param MemberBaseinfoDTO
	 * @return int
	 *
	 */
	public int updateStatus(MemberBaseinfoDTO mb) throws Exception;

	/**
	 * 设置展示补贴，或者隐藏补贴
	 *
	 * @param MemberBaseinfoDTO
	 * @return int
	 *
	 */
	public int updateSubShow(MemberBaseinfoDTO mb) throws Exception;

	/**
	 * 根据ID查询MemberBaseinfo对象
	 *
	 * @param id
	 * @return MemberBaseinfoDTO
	 *
	 */
	public MemberBaseinfoDTO getById(String id) throws Exception;

	/**
	 * 根据 mobile 精确查询 MemberBaseinfo对象
	 *
	 * @param mobile
	 * @return MemberBaseinfoDTO
	 *
	 */
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception;

	/**
	 * 根据 nickName 查询MemberBaseinfo对象
	 *
	 * @param id
	 * @return list
	 *
	 */
	public List<MemberBaseinfoDTO> getByNickName(Map map) throws Exception;

	/**
	 * 根据map 集合，统计个数
	 *
	 *
	 * @return int
	 *
	 */
	public int getTotal(Map map) throws Exception;
	
	/**
	 * 根据map 集合，统计个数
	 *
	 *
	 * @return int
	 *
	 */
	public int getGoldSupplierTotal(Map map) throws Exception;

	/**
	 * 根据查询所有 MemberBaseinfoDTO
	 *
	 *
	 * @return list
	 *
	 */
	public List<MemberBaseinfoDTO> getAll(Map map) throws Exception;

	/**
	 * 根据多条件查询
	 *
	 * account like查询 nickName like查询 mobile like查询 telephone like查询 email like查询 qq like查询 weixin like查询 weibo like查询 address like查询
	 *
	 * provinceId 等于查询 level 等于查询 cityId 等于查询 areaId 等于查询 lon 等于查询 lat 等于查询 status 等于查询
	 *
	 * @return list
	 *
	 */
	public List<MemberBaseinfoDTO> getBySearch(Map map) throws Exception;
	
	/**
	 * 根据多条件查询
	 *
	 * @return list
	 *
	 */
	public List<MemberBaseinfoDTO> queryGoldSupplierbySearch(Map map) throws Exception;

	/**
	 * 根据 account 查询MemberBaseinfo对象
	 *
	 * @param account
	 * @return MemberBaseinfoDTO
	 *
	 */
	public MemberBaseinfoDTO getByAccount(String account) throws Exception;

	public MemberBaseinfoDTO getByAccountNoCer(String account) throws Exception;



	/**
	 * 根据 account like 查询MemberBaseinfo对象集合
	 *
	 * @param id
	 * @return list
	 *
	 */
	public List<MemberBaseinfoDTO> getByAccountLike(Map map) throws Exception;

	public int updateMemberApp(MemberBaseinfoDTO memberDTO) throws Exception;

	/**
	 * 检测登录项
	 *
	 * @param map
	 * @return MemberBaseinfoDTO
	 *
	 */
	public MemberBaseinfoDTO getLogin(Map<String, Object> map) throws Exception;

	/**
	 * 登录
	 *
	 * @param map
	 * @return MemberBaseinfoDTO
	 *
	 */
	public List<MemberBaseinfoDTO> commitLogin(Map<String, Object> map) throws Exception;

	/**
	 * 根据用户id查找用户浏览市场记录
	 *
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemberBrowseMarketHistoryDTO getBrowseHistoryByMemberId(Long memberId) throws Exception;

	/**
	 * 插入用户浏览记录
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int addBrowseHistory(MemberBrowseMarketHistoryDTO dto) throws Exception;

	/**
	 * 注册验证
	 *
	 * @param map
	 * @return
	 */
	public MemberBaseinfoDTO checkRegister(Map<String, Object> map);

	public MemberBaseinfoDTO getAppById(String id) throws Exception;

	/**
	 * 通过memberId 跟新userType
	 *
	 * @param Long
	 *            memberId,Integer userType
	 * @return int
	 *
	 */
	public int updateUserType(Long memberId, Integer userType) throws Exception;

	public MemberBaseinfoDTO getCompanyName(Long memberId) throws Exception;

	public MemberCertifiDTO getByUserId(Long memberId) throws Exception;
	/**
	 * 根据被推荐人memberId 查询推荐人
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO getMemberInfoByMemberedId(Long memberId) throws Exception;

	public List<MemberBaseinfoDTO> getCompany() throws Exception;

	/**
	 * @Description getCompanyByCondition 根据条件查找企业
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月11日 下午8:41:21
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public List<MemberBaseinfoDTO> getCompanyByCondition(Map<String, Object> map) throws Exception;
	/**
	 * @Description getCompanyByConditionTotal 根据条件获取记录数
	 * @param map
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月11日 下午8:46:49
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public int getCompanyByConditionTotal(Map<String, Object> map) throws Exception;

    	public Object getlink(Map<String, Object> map)throws Exception;

    	public int getRecordByMemberInfo(Map<String, Object> map)throws Exception;

	public int getActityNo(String actityNo)throws Exception;

	public int addIntss(Map<String, Object> map)throws Exception;

	public MemberBaseinfoDTO getContacts(String string)throws Exception;

	/**
	 * 获取门岗信息
	 * @param username 账号
	 * @param level 会员类别
	 * @return
	 */
	public MemberBaseinfoDTO getByMobileAndLevel(String username, Integer level) throws Exception;

	public List<MemberBaseinfoDTO> getCompanyByMcity(
			MemberAddressDTO memberAddressDTO)throws Exception;

	public List<PushNstMessageDTO> getPushMessage(
			MemberBaseinfoDTO memberDtoInput)throws Exception;

	public List<PushNstMessageInfoDTO> getPushMessageInfo(
			PushNstMessageDTO pushNstMessageDTO);

	public List<PushNstMessageInfoDTO> getPushMessageInfoCarLine(
			PushNstMessageDTO pushNstMessageDTO)throws Exception;

	public int getPushMessageCount(MemberBaseinfoDTO memberDtoInput)throws Exception;

	/**
	 * 修改农速通会员星级评分
	 * @param memberId
	 * @param nstEvaluate
	 * @return
	 */
	public int updateNstEvaluate(Long memberId, Integer nstEvaluate);
	/**
	 *
	 * @param memberId
	 * @param updateNsyUserType 农商友用户类型
	 * @return
	 */
	public int updateNsyUserType(Long memberId, Integer updateNsyUserType);
	/**
	 * @Title: queryMemberListByMap
	 * @Description: TODO(查询会员信息)
	 * @param memberDtoInput
	 * @return
	 * @throws Exception
	 */
	public List<MemberBaseinfoDTO> queryMemberListByMap(MemberBaseinfoDTO memberDtoInput) throws Exception;

	/**
	 * @Title: addMember
	 * @Description: TODO(注册客户信息)
	 * @param mb
	 * @return
	 * @throws Exception
	 */
	public Long addMember(MemberBaseinfoDTO mb) throws Exception;

	/**
	 * 添加信息到客户关联表
	 * @param reMemForCust
	 * @return
	 * @throws Exception
	 */
	public int addReMemForCust(ReMemForCustDTO reMemForCust) throws Exception;

	/**
	 * 更新信息到客户关联表
	 * @param reMemForCust
	 * @return
	 * @throws Exception
	 */
	public int updateReMemForCust(ReMemForCustDTO reMemForCust) throws Exception;


	/**
	 * 农速通用户列表查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstMemberBaseinfoDTO> getNstListBySearch(Map map) throws Exception;

	public int getNstTotalBySearch(Map map) throws Exception;

	/**
	 * 根据ID查询农速通对象
	 *
	 * @param id
	 * @return NstMemberBaseinfoDTO
	 *
	 */
	public NstMemberBaseinfoDTO getNstMemberById(String id) throws Exception;

	/**
	 * @Title: queryMemberForSinxin
	 * @Description: TODO(会员查询)
	 * @param queryDTO 查询
	 * @throws Exception
	 */
	public List<MemberSinxinDTO> queryMemberForSinxin(MemberSinxinDTO queryDTO) throws Exception;

	/**
	 * 获取会员商铺信息
	 * @param param
	 */
	public MemberBusinessInfoDTO getMemberBusinessInfo(Map<String, Object> param);

	/**
	 * 查询用户基本信息(微信活动专用)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public MemberBaseinfoDTO queryMemberInfoForPromotion(Map<String, Object> params) throws Exception;

	/***
	 * 微信活动选择用户分页
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<MemberBaseinfoDTO> queryMemberSelectPageByCondition(Map<String, Object> map);

	/***
	 * 微信活动选择用户总记录数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public int getMemberSelectTotalByCondition(Map<String, Object> map);
	
	
	/*----------2016年8月10日10:22:13 配合农速通上线新增接口 start ------*/
	/***
	 * 修改手机号
	 * @param memberId,mobile
	 * @return
	 * @throws Exception 
	 */
	public int updateMobile(Long memberId, String mobile) throws Exception;
	/***
	 * 修改姓名
	 * @param memberId,realName
	 * @return
	 * @throws Exception 
	 */
	public int updateRealName(Long memberId, String realName) throws Exception;
	/***
	 * 修改密码
	 * @param memberId,password
	 * @return
	 * @throws Exception 
	 */
	public int updatePassword(Long memberId, String password) throws Exception;
	
	/*----------2016年8月10日10:22:13 配合农速通上线新增接口 end ------*/

	/***
	 * 根据memberId查询出角色类型
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public Integer getAppTypeByMemberId(String memberId) throws Exception;
	
	public List<MemberBaseinfoDTO> getBuyerListForGdActivity(Map<String, Object> map) throws Exception ;

	public MemberBaseinfoDTO getbyMemberAddressId(String memberAddressId)throws Exception;
	public MemberBaseinfoDTO getMemberTonken(String memberId) throws Exception;
}