package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;


/**
 *功能描述：收发货地址管理
 */
public interface MemberAddressService{
	


	/**
	 * 根据ID查询对象
	 * 
	 * @param id
	 * @return MemberAddressDTO
	 * 
	 */
	public MemberAddressDTO getById(String id)throws Exception;
	/**
	 * 根据条件查询list(分页查询)
	 * 
	 * @param map
	 * @return List<MemberAddressDTO>
	 */
	public List<MemberAddressDTO> getByCondition(Map<String,Object> map)throws Exception;


	/**
	 * 根据城市ID查询List
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getGoodsListByAreaId(Map<String, Object> map)throws Exception;
	

	public List<MemberAddressDTO> getGoodsListCompanyMobile(Map<String,Object> map)throws Exception;

	/**
	 * 	  根据name查询对象集合
	 * 
	 *   拓展后，实现多个条件查询
	 *   
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getByName(Map<String, Object> map);

	
	/**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotal(Map<String,Object> map)throws Exception;
	
	
	
	
	/**
	 * 根据条件查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByName(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 通过ID删除对象
	 * 
	 * @param id
	 * @return void
	 * 
	 */
	public int deleteById(String id)throws Exception;
	
	/**
	 * 通过Map插入数据库
	 * 
	 * @param map
	 * @return int
	 * 
	 */
	public int addMemberAddressDTO(Map<String,Object> map)throws Exception;

	/**
	 * 通过对象插入数据库
	 * 
	 * @param MemberAddressDTO
	 * @return int
	 * 
	 */
	public int addMemberAddressDTO(MemberAddressDTO dto) throws Exception;
	
	/**
	 * 通过对象跟新数据库
	 * 
	 * @param MemberAddressDTO
	 * @return int
	 * 
	 */
	public int updateMemberAddressDTO(MemberAddressDTO dto) throws Exception;

	public List<MemberAddressDTO> listMemberAddressByUserId(MemberAddressDTO memberAddressDTO)throws Exception;

	public int replayMemberAddress(MemberAddressDTO memberAddressDTO)throws Exception;

	public List<MemberAddressDTO> getMemberAddressByCondition(
			MemberAddressDTO memberAddressDTO)throws Exception;
	
	
	/**
	 * 用户中心查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getListByUserId(Map<String, Object> map) throws Exception;


	public int getTotalByUserId(Map<String, Object> map) throws Exception;

	public int getCountByCondition(MemberAddressDTO memberAddressDTO)throws Exception;

	public List<MemberAddressDTO> getMemberAddressByConditionNew(
			MemberAddressDTO memberAddressDTO)throws Exception;

	public List<MemberAddressDTO> getMebApiMessage(CarLineDTO carLineDTO)throws Exception;

	public Long getmemberAddressId(Long userId)throws Exception;

	public List<CarLineDTO> getCarlineApiMessage(
			MemberAddressDTO memberAddressDTO)throws Exception;

	public void getCarlineApiMessage(MemberAddressDTO memberAddressDTO,
			List<CarLineDTO> list)throws Exception;


	public int getMebApiMessageCount(CarLineDTO carLineDTO)throws Exception;

	/**
	 * 更新货源信息  member_address 中 isDeleted为1 已删除
	 * @param memberAdressIds
	 * @return
	 * @throws Exception
	 */
	public Integer updateMemberAdressStatusByid(String memberAdressIds) throws Exception;

	public List<MemberAddressDTO> getMebApiMessagetwo(CarLineDTO carLineDTO,
			Map<String, Object> p2)throws Exception;


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

	public List<MemberAddressDTO> getMemberAddressByConditionNewNst2(
			MemberAddressDTO memberAddressDTO)throws Exception;
   /**
     * 根据线路下订单,选择自己的满足条件的货源信息
     * @param carLineDTO
     * @return
     * @throws Exception
     */
	public List<MemberAddressDTO> getMemberAddressByCarLine(
			CarLineDTO carLineDTO)throws Exception;

	public List<MemberAddressDTO> getMemberAddressByIdNst2(
			MemberAddressDTO memberAddressDTO)throws Exception;
    /**
     * 农速通,货源查询分页
     * @param memberAddressDTO
     * @return
     */
	public int getCountByConditionNst2(MemberAddressDTO memberAddressDTO)throws Exception;
    
	/**
	 * 农速通2.0 .发布货源,个人是否可以
	 * @param mcity
	 * @return
	 * @throws Exception
	 */
	public int checkCity(String mcity)throws Exception;
    
	/**
	 * 我的货源  高级搜索
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getMemberAddressByUserCondition(
			MemberAddressDTO memberAddressDTO)throws Exception;
	
	/**
	 * 我的货源  高级搜索 分页查询总数
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberAddressByUserConditionCount(MemberAddressDTO memberAddressDTO) throws Exception;

	/**
	 * 会员发布货源统计
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getListByAddress(Map<String, Object> map) throws Exception;
	
	/**
	 * 会员发布货源统计(同城)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RecommendedUserDTO> getListByAddressSameCity(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 查询记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByAddress(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询记录数(同城)
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getTotalByAddressSameCity(Map<String,Object> map)throws Exception;

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
	 * 查询当前客户端发布是否打开轮播机制
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
	public Object updateCreateUserId(String createUserId, String clients, Long id);



	/**
	 * 货源分配统计列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<NstMemberAddressDTO> getDistributeAddressList(Map<String, Object> map) throws Exception;
	
	
	
	/**
	 * 货源分配统计总记录数
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getDistributeAddressTotal(Map<String,Object> map)throws Exception;

	public void updatenstRule(Long id)throws Exception;

	/**
	 * 物流订单详细查询所用
	 * @param id
	 * @param type
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public MemberAddressDTO getByIdForOrder(String id, Integer type, Integer source)throws Exception;
	
	/**
	 * 查询物流订单总数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalForOrder(Map<String, Object> query) throws Exception;
	
	/**
	 * 查询物流订单,分页
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<MemberAddressDTO> getListForOrder(Map<String, Object> query) throws Exception;
	
	/**
	 * 新增货源订单并返回主键
	 * @param memberAddressDTO
	 * @return
	 * @throws Exception
	 */
	public Long addMemberAddress(MemberAddressDTO memberAddressDTO) throws Exception;
}
