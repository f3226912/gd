package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;

public interface MemberAddressApiService {

	/**
	 * 获得当前登录用户下的货源列表
	 * @param userId 
	 * 
	 * @return
	 */
	public List<MemberAddressDTO> getMemberAddressByUserId(MemberAddressDTO memberAddressDTO) throws Exception;
    
	/**
	 * 当前手机登录用户新增货源发布信息
	 * @输入参数 MemberAddressDTO
	 * @返回对象 Int
	 */
	public int addMemberAddress(MemberAddressDTO memberAddressDTO) throws Exception;

	public int updateMemberAddress(MemberAddressDTO memberAddressDTO)throws Exception;

	public int delMemberAddress(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 重新发布货源
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public int replayMemberAddress(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 货源列表,点击查询单条详情
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public MemberAddressDTO getMemberAddressById(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 不带分页的货源列表查询
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMemberAddressByCondition(
			MemberAddressDTO memberAddressDTO)throws Exception;
	/**
	 * 我的货源  高级搜索 分页查询总数
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberAddressByUserConditionCount(MemberAddressDTO memberAddressDTO) throws Exception;
   
	/**
	 * 分页查询,获取总数
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public int getCountByCondition(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 分页的货源列表查询
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMemberAddressByConditionNew(
			MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 根据传的Id获取当前区域的名称
	 * @param f_provinceId
	 * @return
	 * @throws Exception
	 */
	public String getAreaString(Long f_provinceId) throws Exception;
    
	
	/**
	 * 根据传的城市名称,获取城市的信息
	 * @param getmCity
	 * @return
	 * @throws Exception
	 */
	public AreaDTO getArea(String getmCity)throws Exception;
   
	/**
	 * 获取推送消息中的货源
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMebApiMessage(CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 根据当前登录的用户最后发布的货源Id 返回Id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Long getmemberAddressId(Long userId)throws Exception;
    
	/**
	 * 获取推送的线路信息
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> getCarlineApiMessage(
			MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 插入推送的线路信息
	 * @param memberAddressDTO
	 * @param list
	 * @throws Exception
	 */
	public void setCarLApiMessage(MemberAddressDTO memberAddressDTO,
			List<CarLineDTO> list)throws Exception;
    
    /**
     * 获取当前如果按照正常条件查询,有多少条记录,创建路线,推荐货源
     * @param carLineDTO
     * @return
     * @throws Exception
     */
	public int getMebApiMessageCount(CarLineDTO carLineDTO )throws Exception;
   
	/**
	 * 如果按照正常条件获取记录数 ,推送消息 ,创建路线,推送货源
	 * @param carLineDTO
	 * @param p2
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMebApiMessagetwo(CarLineDTO carLineDTO,
			Map<String, Object> p2)throws Exception;
	
	/**
	 * 更新货源信息  member_address 中 isDeleted为1 已删除
	 * @param memberAdressIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateMemberAdressStatusByid(String memberAdressIds) throws Exception;

   
	/**
	 * 获取推送货源消息的时候,重新推荐
	 * @param carLineDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMebApiMessageAagin(CarLineDTO carLineDTO)throws Exception;

	
	
	/**
	 * 通过Id查询推送的线路信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MemberAddressDTO getNstpushMessageById(Long id) throws Exception;
	
	/**
	 * 根据货源mbid 查询农速通推送消息子表的记录 cl_id集合
	 * @return
	 * @throws Exception
	 */
	public List<PushNstMessageInfoDTO> getCarLinesBymessageId(Long id) throws Exception;
	
	/**
	 * 农商通线路推送规则
	 * @param memberDTO
	 * @param memberAddressDTO
	 * @param memberAddressApiService
	 * @return
	 * @throws Exception
	 */
	public List<CarLineDTO> excutePush(MemberBaseinfoDTO memberDTO,MemberAddressDTO memberAddressDTO,MemberAddressApiService memberAddressApiService) throws Exception;

	public List<MemberAddressDTO> getMemberAddressByConditionNewNst2(
			MemberAddressDTO memberAddressDTO)throws Exception;
    /*8
     * 农速通,查询线路,下订单,货主,搜索满足订单条件的货源信息
     */
	public List<MemberAddressDTO> getMemberAddressByCarLine(
			CarLineDTO carLineDTO)throws Exception;
    
	/**
	 * 农速通2.0
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMemberAddressByIdNst2(
			MemberAddressDTO memberAddressDTO)throws Exception;
    /**
     * 货源发布的,分页算法统计总数
     * @param memberAddressDTO
     * @return
     * @throws Exception
     */
	public int getCountByConditionNst2(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 判断用户的GPS城市是否可以不选择公司发布货源
	 * @param mcity
	 * @return
	 * @throws Exception
	 */
	public int checkCity(String mcity)throws Exception;

	public List<MemberAddressDTO> getMemberAddressByUserCondition(
			MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 获取当前发货地的规则设置信息部,满足条件的,按照分配时间的最后顺序的来排序,
	 * @param memberAddressDTO
	 * @return
	 */
	public List<MemberAddressDTO> getCompanyToMb(
			MemberAddressDTO memberAddressDTO)throws Exception;
    /**
     * 获取当前信息部的日上限,月上限,和已经分配数日分配,月分配
     * @param memberAddressDTO
     * @return
     */
	public MemberAddressDTO getCompanyToMbgetL(
			MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 获取当前客户端的轮播规则是否打开
	 * @param clients
	 * @return
	 * @throws Exception
	 */
	public int getRule(String clients)throws Exception;
    
	/**
	 * 分配信息部给
	 * @param createUserId
	 * @param clients
	 * @param id 
	 * @throws Exception
	 */
	public void updateCreateUserId(String createUserId, String clients, Long id)throws Exception;

	public void updatenstRule(Long id)throws Exception;

	/**
	 * 新增货源信息
	 * 成功返回 OK#_#id
	 * @param inputDTO
	 * @param isUpdate 
	 * @return
	 * @throws Exception
	 */
	public StatusCodeEnumWithInfo addMemberAddress(MemberAddressInputDTO inputDTO, boolean isUpdate) throws Exception;
}
