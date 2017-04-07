package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
/**
 * 同城发布线路
 * @author sunl
 *
 */
public interface NstSameCityCarlineApiService {

	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getById(String id)throws Exception;
	
	/**
	 * 根据条件查询对象
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getByDto(NstSameCityCarlineEntityDTO dto)throws Exception;
	
	/**
	 * 根据用户id查询其名下车辆
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryCarsByUserId(String userId)throws Exception;
	
	/**
	 * 新增
	 * @param neSameCityCarlineEntityDTO
	 * @return
	 * @throws Exception
	 */
	public int addNstSameCityCarline(NstSameCityCarlineEntityDTO dto)throws Exception;
	
	/**
	 * 修改
	 * @param neSameCityCarlineEntityDTO
	 * @return
	 * @throws Exception
	 */
	public int updateNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)throws Exception;
	
	/**
	 * 删除
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int deleteNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)throws Exception;
	
	/**
	 * 同城找车查询 列表 记录数
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int queryNstSameCityCarLineCount(NstSameCityCarlineEntityDTO dto) throws Exception;
	
	
	/**
	 * 同城找车查询 列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryNstSameCityCarLineList(NstSameCityCarlineEntityDTO dto)throws Exception;
	
	
	/**
	 * 根据中文城市查询 
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getCityId(String cityName)throws Exception;
	
	
	/**
	 * 根据城市id查询 
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getCityName(int cityId)throws Exception;
	
	
	/**
	 * 线路管理 我发的车  记录数
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int queryMyCitylineCount(NstSameCityCarlineEntityDTO dto) throws Exception;
	
	
	/**
	 * 线路管理 我发的车
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryMyCitylineList(NstSameCityCarlineEntityDTO dto) throws Exception;
	
	
	/**
	 * 同城发布线路 匹配货源 查询货物列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> queryCityCarGoodslineList(NstSameCityCarlineEntityDTO dto) throws Exception;
	
	/**
	 * 同城发布货源 匹配线路 查询线路列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryCityCarGoodsMatchList(NstSameCityAddressDTO dto) throws Exception;
	
	/**
	 * 同城发布线路 匹配货源 业务逻辑处理
	 * @param dto
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> excutePushCity(MemberBaseinfoDTO memberDTO,NstSameCityCarlineEntityDTO dto,NstSameCityCarlineApiService nstSameCityCarlineApiService) throws Exception;
	 
	/**
	 * 插入货源推送消息 
	 * @param dto
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void insertGoodSNstpushMessage(NstSameCityCarlineEntityDTO dto,List<NstSameCityAddressDTO> list) throws Exception;

	/**
	 * 插入线路推送消息 
	 * @param dto
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void insertLineNstpushMessage(NstSameCityAddressDTO dto,List<NstSameCityCarlineEntityDTO> list) throws Exception;

	/**
	 * 同城发布货源 匹配线路 业务逻辑处理
	 * @param dto
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> excutePushLine(MemberBaseinfoDTO memberDTO,NstSameCityAddressDTO dto,NstSameCityCarlineApiService nstSameCityCarlineApiService) throws Exception;
	 
	/**
	 * 发布线路 匹配货源后 推送消息 查看推送货源记录
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> queryCityGoodsListDetail(Map<String, Object> p) throws Exception;
	
	/**
	 * 发布货源 匹配线路后 推送消息 查看推送线路记录
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryCityLineListDetail(Map<String, Object> p) throws Exception;
	
	/**
	 * 查询id
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long queryLastNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto) throws Exception;
	
	
	/**
	 * 查询推送消息线路或货源Id
	 * @param id
	 * @return
	 */
	public NstSameCityCarlineEntityDTO queryNstpushMessageById(Long id) throws Exception;
	
	/**
	 * 根据推送主表id查询明细表所有的推送货源Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<PushNstMessageInfoDTO> getCarLinesByClId(Long id) throws Exception;
}
