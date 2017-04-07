package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.service.NstSameCityCarlineService;

/**
 * 同城发布线路
 * @author sunl
 *
 */
@Service
@SuppressWarnings("unchecked")
public class NstSameCityCarlineServiceImpl implements NstSameCityCarlineService {

	@Autowired
	private BaseDao<?>  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public NstSameCityCarlineEntityDTO getById(String id) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", id);
		return (NstSameCityCarlineEntityDTO)this.baseDao.
				queryForObject("NstSameCityCarline.getSameCityCarLine",p,NstSameCityCarlineEntityDTO.class);
	}
	
	/**
	 * 根据条件查询对象
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getByDto(NstSameCityCarlineEntityDTO dto)throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("id", dto.getId());
		p.put("s_lng", dto.getS_lng());
		p.put("s_lat", dto.getS_lat());
		return (NstSameCityCarlineEntityDTO)this.baseDao.
				queryForObject("NstSameCityCarline.getSameCityCarLine",p,NstSameCityCarlineEntityDTO.class);
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryCarsByUserId(String userId)throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		List<NstSameCityCarlineEntityDTO> list=this.baseDao.
			queryForList("NstSameCityCarline.getCarNumbers",p,NstSameCityCarlineEntityDTO.class);
		return list;
	}
	
	/**
	 * 新增
	 * @param neSameCityCarlineEntityDTO
	 * @return
	 * @throws Exception
	 */
	public int addNstSameCityCarline(NstSameCityCarlineEntityDTO dto)throws Exception{
		return (int) baseDao.execute("NstSameCityCarline.addNstSameCityCarLineDTO", dto);
	}
	
	/**
	 * 修改
	 * @param neSameCityCarlineEntityDTO
	 * @return
	 * @throws Exception
	 */
	public int updateNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)throws Exception{
		return (int) baseDao.execute("NstSameCityCarline.updateNstSameCityCarLineDTO", dto);
	}
	
	/**
	 * 删除
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int deleteNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)throws Exception{
		return (int) baseDao.execute("NstSameCityCarline.deleteNstSameCityCarLineDTO", dto);
	}
	
	/**
	 * 同城找车查询 以及线路详情 记录数
	 */
	@Override
	public int queryNstSameCityCarLineCount(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("s_provinceId", dto.getS_provinceId());
		p.put("s_cityId", dto.getS_cityId());
		p.put("s_areaId", dto.getS_areaId());
		p.put("e_provinceId", dto.getE_provinceId());
		p.put("e_cityId", dto.getE_cityId());
		p.put("e_areaId", dto.getE_areaId());
		p.put("queryFlag", dto.getQueryFlag());
		p.put("transportCarTypeId", dto.getTransportCarTypeId());
		p.put("s_lng", dto.getS_lng());
		p.put("s_lat", dto.getS_lat());
		p.put("minLoad", dto.getMinLoad());
		p.put("maxLoad", dto.getMaxLoad());
		return (int)baseDao.queryForObject("NstSameCityCarline.queryNstSameCityCarLineCount", p, Integer.class);
	}
	
	/**
	 * 同城找车查询 列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryNstSameCityCarLineList(
			    NstSameCityCarlineEntityDTO dto)throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("s_provinceId", dto.getS_provinceId());
		p.put("s_cityId", dto.getS_cityId());
		p.put("s_areaId", dto.getS_areaId());
		p.put("e_provinceId", dto.getE_provinceId());
		p.put("e_cityId", dto.getE_cityId());
		p.put("e_areaId", dto.getE_areaId());
		p.put("queryFlag", dto.getQueryFlag());
		p.put("transportCarTypeId", dto.getTransportCarTypeId());
		p.put("s_lng", dto.getS_lng());
		p.put("s_lat", dto.getS_lat());
		p.put("startRow", dto.getStartRow());
		p.put("endRow", dto.getEndRow());
		p.put("minLoad", dto.getMinLoad());
		p.put("maxLoad", dto.getMaxLoad());
		List<NstSameCityCarlineEntityDTO> list=this.baseDao.
		     queryForList("NstSameCityCarline.queryNstSameCityCarLineList",p,NstSameCityCarlineEntityDTO.class);
	    return list;
	}
	
	/**
	 * 根据中文城市查询 
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	public NstSameCityCarlineEntityDTO getCityId(String cityName)throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("cityName", cityName);
		return (NstSameCityCarlineEntityDTO)this.baseDao.
				 queryForObject("NstSameCityCarline.getCityId",p,NstSameCityCarlineEntityDTO.class);
	}
	
	/**
	 * 根据城市id查询 
	 * @param cityName
	 * @return
	 * @throws Exception
	 */
	@Override
	public NstSameCityCarlineEntityDTO getCityName(int cityId) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("cityId", cityId);
		return (NstSameCityCarlineEntityDTO)this.baseDao.
				 queryForObject("NstSameCityCarline.getCityName",p,NstSameCityCarlineEntityDTO.class);
	}
	

	/**
	 * 线路管理 我发的车  记录数
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int queryMyCitylineCount(NstSameCityCarlineEntityDTO dto) throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("memberId", dto.getMemberId());
		return (int)baseDao.queryForObject("NstSameCityCarline.queryMyCitylineCount", p, Integer.class);
	}
	
	/**
	 * 线路管理 我发的车
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityCarlineEntityDTO> queryMyCitylineList(NstSameCityCarlineEntityDTO dto) throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("memberId", dto.getMemberId());
		p.put("startRow", dto.getStartRow());
		p.put("endRow", dto.getEndRow());
		List<NstSameCityCarlineEntityDTO> list=this.baseDao.
		     queryForList("NstSameCityCarline.queryMyCitylineList",p,NstSameCityCarlineEntityDTO.class);
	    return list;
	}
	
	/**
	 * 同城发布线路 匹配货源 查询货物列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<NstSameCityAddressDTO> queryCityCarGoodslineList(NstSameCityCarlineEntityDTO dto) throws Exception{
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("s_lat", dto.getClat());
		p.put("s_lng", dto.getClng());
		p.put("s_provinceId", dto.getS_provinceId());
		p.put("s_cityId", dto.getS_cityId());
		p.put("s_areaId", dto.getS_areaId());
		p.put("f_provinceId", dto.getE_provinceId());
		p.put("f_cityId", dto.getE_cityId());
		p.put("f_areaId", dto.getE_areaId());
		p.put("needCarType", dto.getCarType());
		
		/**
		 * 给再次推送 过滤掉第一次线路集合
		 */
		if (dto.getCityAddressIds()!=null && dto.getCityAddressIds().size()!=0){
			p.put("carLineIds", dto.getCityAddressIds());
		}
		List<NstSameCityAddressDTO> list=this.baseDao.
			     queryForList("NstSameCityCarline.queryCityCarGoodslineList",p,NstSameCityAddressDTO.class);
		    return list;
		
	}

	/**
	 * 同城发布货源 匹配线路 查询线路列表
	 */
	@Override
	public List<NstSameCityCarlineEntityDTO> queryCityCarGoodsMatchList(
			NstSameCityAddressDTO dto) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("s_lat", dto.getClat());
		p.put("s_lng", dto.getClng());
		p.put("s_provinceId", dto.getS_provinceId());
		p.put("s_cityId", dto.getS_cityId());
		p.put("s_areaId", dto.getS_areaId());
		p.put("e_provinceId", dto.getF_provinceId());
		p.put("e_cityId", dto.getF_cityId());
		p.put("e_areaId", dto.getF_areaId());
		p.put("transportCarType", dto.getNeedCarType());
		
		/**
		 * 给再次推送 过滤掉第一次线路集合
		 */
		if (dto.getCarLineIds()!=null && dto.getCarLineIds().size()!=0){
			p.put("carLineIds", dto.getCarLineIds());
		}
		
		List<NstSameCityCarlineEntityDTO> list=this.baseDao.
			     queryForList("NstSameCityCarline.queryCityCarGoodsMatchList",p,NstSameCityCarlineEntityDTO.class);
		 return list;
	}

	/**
	 * 发布线路 推送货源信息
	 */
	@Override
	public void insertGoodSNstpushMessage(NstSameCityCarlineEntityDTO dto, List<NstSameCityAddressDTO> list)
			throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("clId", dto.getId());
		p.put("memberId", dto.getMemberId());
		p.put("type",2);
		p.put("createUserId", dto.getCreateUserId());
		p.put("updateUserId", dto.getCreateUserId());
		p.put("mCity", dto.getCityName());
		p.put("cityId", dto.getS_cityId());
		p.put("source_type", "1");
		baseDao.execute("MemberAddress.addnstpushlinemessage", p);
		Integer id=baseDao.queryForObject("MemberAddress.getnstpushlinemessage", p,  Integer.class);
		Map <String, Object> p2 = new HashMap<String, Object>();
		if(id != null){
			for (int i = 0; i < list.size(); i++) {
				p2.put("messageId", id);
				p2.put("mb_Id", list.get(i).getId());
				p2.put("type",1);
				p2.put("createUserId", list.get(i).getMemberId());
				p2.put("updateUserId", list.get(i).getMemberId());
				p2.put("carType",list.get(i).getNeedCarType());
				p2.put("distance", list.get(i).getDistance());
				p2.put("mCity", dto.getCityName());
				p2.put("carLength",dto.getCarLength());
				baseDao.execute("MemberAddress.addnstpushlinemessageinfo", p2);
		  }
		}
	}

	/**
	 * 发布货源 推送线路信息
	 */
	@Override
	public void insertLineNstpushMessage(NstSameCityAddressDTO dto,
			List<NstSameCityCarlineEntityDTO> list) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("mbId", dto.getId());
		p.put("memberId", dto.getMemberId());
		p.put("type",1);
		p.put("createUserId", dto.getCreateUserId());
		p.put("updateUserId", dto.getCreateUserId());
		p.put("mCity", dto.getMcity());
		p.put("cityId", dto.getS_cityId());
		p.put("source_type", "1");
		baseDao.execute("MemberAddress.addnstpushmessage", p);
		Integer id=baseDao.queryForObject("MemberAddress.getnstpushmessage", p,  Integer.class);
		Map <String, Object> p2 = new HashMap<String, Object>();
		if(id != null){
			for (int i = 0; i < list.size(); i++) {
				p2.put("messageId", id);
				p2.put("cl_Id", list.get(i).getId());
				p2.put("type",1);
				p2.put("createUserId", dto.getCreateUserId());
				p2.put("updateUserId", dto.getCreateUserId());
				p2.put("carType",list.get(i).getTransportCarType());
				p2.put("distance", list.get(i).getDistance());
				p2.put("mCity", dto.getMcity());
				p2.put("carLength",list.get(i).getCarLength());
				baseDao.execute("MemberAddress.addnstpushmessageinfo", p2);
		  }
		}
	}

	@Override
	public List<NstSameCityAddressDTO> queryCityGoodsListDetail(
			Map<String, Object> p) throws Exception {
		List<NstSameCityAddressDTO> list=this.baseDao.
			     queryForList("NstSameCityCarline.queryCityGoodsListDetail",p,NstSameCityAddressDTO.class);
		 return list;
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryCityLineListDetail(
			Map<String, Object> p) throws Exception {
		List<NstSameCityCarlineEntityDTO> list=this.baseDao.
			     queryForList("NstSameCityCarline.queryCityLineListDetail",p,NstSameCityCarlineEntityDTO.class);
		 return list;
	}

	@Override
	public int getTotalForConsole(Map<String, Object> map)
			throws Exception {
		return (int)baseDao.queryForObject("NstSameCityCarline.getTotalForConsole",map, Integer.class);
	
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryListForConsole(
			Map<String, Object> map) throws Exception {
		List<NstSameCityCarlineEntityDTO> list=baseDao.
			     queryForList("NstSameCityCarline.queryListForConsole",map,NstSameCityCarlineEntityDTO.class);
		 return list;
	}

	@Override
	public Long queryLastNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return (Long)baseDao.queryForObject("NstSameCityCarline.queryLastNstSameCityCarLineDTO", dto, Long.class);
	}

	@Override
	public NstSameCityCarlineEntityDTO queryNstpushMessageById(Long id) 
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("source_type", "1");
		return (NstSameCityCarlineEntityDTO)baseDao.queryForObject("MemberAddress.getNstpushMessageById", paramMap, NstSameCityCarlineEntityDTO.class);
	}

	@Override
	public List<PushNstMessageInfoDTO> getCarLinesByClId(Long id)
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("id", id);
		return baseDao.queryForList("MemberAddress.getCarLinesByClId", paramMap, PushNstMessageInfoDTO.class);
	}
	
}
