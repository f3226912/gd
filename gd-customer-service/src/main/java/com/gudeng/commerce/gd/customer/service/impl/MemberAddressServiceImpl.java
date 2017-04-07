package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstMemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.dto.RecommendedUserDTO;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.commerce.gd.customer.service.NstOrderBaseinfoService;


/**
 *功能描述：
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService{
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private NstOrderBaseinfoService nstOrderBaseinfoService;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public MemberAddressDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (MemberAddressDTO)this.baseDao.queryForObject("MemberAddress.getMemberAddress", p, MemberAddressDTO.class);
	}

	@Override
	public List<MemberAddressDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<MemberAddressDTO> list= baseDao.queryForList("MemberAddress.getByCondition", map, MemberAddressDTO.class);
		return list;
	}
	@Override
	public List<MemberAddressDTO> getGoodsListCompanyMobile(Map<String, Object> map)
			throws Exception {
		List<MemberAddressDTO> list= baseDao.queryForList("MemberAddress.getGoodsListCompanyMobile", map, MemberAddressDTO.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("MemberAddress.getTotal", map, Integer.class);
	}

	@Override
	@Deprecated
	public int deleteById(String id) throws Exception {
		int len = id.split(",").length;
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(id.split(",")[i]));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("MemberAddress.deleteMemberAddressDTO", batchValues).length;
	}
	
	

	@Override
	public int addMemberAddressDTO(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("MemberAddress.addMemberAddressDTO", map);
	}
	
	@Override
	public int addMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception {
		return (int) baseDao.execute("MemberAddress.addMemberAddressDTO", memberAddress);

	}

	@Override
	public int updateMemberAddressDTO(MemberAddressDTO memberAddress) throws Exception {
		return (int) baseDao.execute("MemberAddress.updateMemberAddressDTO", memberAddress);

	}

	@Override
	public List<MemberAddressDTO> getByName(Map<String, Object> map) {
		List<MemberAddressDTO> list= baseDao.queryForList("MemberAddress.getByName", map, MemberAddressDTO.class);
		return list;
	}
	
	/**
	 *根据用户ID获取货源列表
	 *add by yanghaoyu
	 *输入参数,手机登录用户ID
	 */
	@Override
	public List<MemberAddressDTO> listMemberAddressByUserId(MemberAddressDTO memberAddressDTO) throws Exception  {
		return this.baseDao.queryForList("MemberAddress.listMemberAddressByUserId",memberAddressDTO,MemberAddressDTO.class);
	}

	@Override
	public int replayMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
        
		return (int)baseDao.execute("MemberAddress.replayMemberAddressById",memberAddressDTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAddressDTO> getMemberAddressByCondition(
			MemberAddressDTO memberAddressDTO) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("carType", memberAddressDTO.getCarType());
		
		return this.baseDao.queryForList("MemberAddress.listMemberAddressByCondition",params,MemberAddressDTO.class);
	}

	@Override
	public int getTotalByName(Map<String, Object> map) throws Exception {
		
		return (int) baseDao.queryForObject("MemberAddress.getTotalByName", map, Integer.class);
	}
	
	
	@Override
	public List<MemberAddressDTO> getListByUserId(Map<String, Object> map)
			throws Exception {
		return this.baseDao.queryForList("MemberAddress.getListByUserId",map,MemberAddressDTO.class);
	}


	@Override
	public int getTotalByUserId(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("MemberAddress.getTotalByUserId", map, Integer.class);

	}

	@Override
	public int getCountByCondition(MemberAddressDTO memberAddressDTO)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("tjlat", memberAddressDTO.getTjlat());
		params.put("distance", memberAddressDTO.getDistance());
		return  (int) baseDao.queryForObject("MemberAddress.getCountByCondition", params, Integer.class);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByConditionNew(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("distance", memberAddressDTO.getDistance());
		params.put("tjlat", memberAddressDTO.getTjlat());
		return this.baseDao.queryForList("MemberAddress.listMemberAddressByConditionNew",params,MemberAddressDTO.class);

	}

	@Override
	public List<MemberAddressDTO> getMebApiMessage(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", carLineDTO.getS_provinceId());
		params.put("s_cityId", carLineDTO.getS_cityId());
		params.put("s_areaId", carLineDTO.getS_areaId());
		params.put("s_provinceId2", carLineDTO.getS_provinceId2());
		params.put("s_cityId2", carLineDTO.getS_cityId2());
		params.put("s_areaId2", carLineDTO.getS_areaId2());
		params.put("s_provinceId3", carLineDTO.getS_provinceId3());
		params.put("s_cityId3", carLineDTO.getS_cityId3());
		params.put("s_areaId3", carLineDTO.getS_areaId3());
		params.put("f_provinceId", carLineDTO.getE_provinceId());
		params.put("f_cityId", carLineDTO.getE_cityId());
		params.put("f_areaId", carLineDTO.getE_areaId());
		params.put("f_provinceId2", carLineDTO.getE_provinceId2());
		params.put("f_cityId2", carLineDTO.getE_cityId2());
		params.put("f_areaId2", carLineDTO.getE_areaId2());
		params.put("f_provinceId3", carLineDTO.getE_provinceId3());
		params.put("f_cityId3", carLineDTO.getE_cityId3());
		params.put("f_areaId3", carLineDTO.getE_areaId3());
		params.put("f_provinceId4", carLineDTO.getE_provinceId4());
		params.put("f_cityId4", carLineDTO.getE_cityId4());
		params.put("f_areaId4", carLineDTO.getE_areaId4());
		params.put("f_provinceId5", carLineDTO.getE_provinceId5());
		params.put("f_cityId5", carLineDTO.getE_cityId5());
		params.put("f_areaId5", carLineDTO.getE_areaId5());
		params.put("userType", carLineDTO.getUserType());
		params.put("userId", carLineDTO.getMemberId());
		params.put("createUserId", carLineDTO.getCreateUserId());
		params.put("startRow", carLineDTO.getStartRow());
		params.put("endRow", carLineDTO.getEndRow());
		params.put("carType", carLineDTO.getCarType());
		params.put("totalSize", carLineDTO.getCarLength());
		params.put("mlng", carLineDTO.getMlng());
		params.put("mlat", carLineDTO.getMlat());
		params.put("bjlng", carLineDTO.getBjlng());
		params.put("bjlat", carLineDTO.getBjlat());
		params.put("cqlng", carLineDTO.getCqlng());
		params.put("cqlat", carLineDTO.getCqlat());
		params.put("shlng", carLineDTO.getShlng());
		params.put("shlat", carLineDTO.getShlat());
		params.put("tjlng", carLineDTO.getTjlng());
		params.put("tjlat", carLineDTO.getTjlat());
		params.put("distance", carLineDTO.getDistance());
		return this.baseDao.queryForList("MemberAddress.getMebApiMessage",params,MemberAddressDTO.class);
	}
	
	@Override
	public int getMebApiMessageCount(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", carLineDTO.getS_provinceId());
		params.put("s_cityId", carLineDTO.getS_cityId());
		params.put("s_areaId", carLineDTO.getS_areaId());
		params.put("s_provinceId2", carLineDTO.getS_provinceId2());
		params.put("s_cityId2", carLineDTO.getS_cityId2());
		params.put("s_areaId2", carLineDTO.getS_areaId2());
		params.put("s_provinceId3", carLineDTO.getS_provinceId3());
		params.put("s_cityId3", carLineDTO.getS_cityId3());
		params.put("s_areaId3", carLineDTO.getS_areaId3());
		params.put("f_provinceId", carLineDTO.getE_provinceId());
		params.put("f_cityId", carLineDTO.getE_cityId());
		params.put("f_areaId", carLineDTO.getE_areaId());
		params.put("f_provinceId2", carLineDTO.getE_provinceId2());
		params.put("f_cityId2", carLineDTO.getE_cityId2());
		params.put("f_areaId2", carLineDTO.getE_areaId2());
		params.put("f_provinceId3", carLineDTO.getE_provinceId3());
		params.put("f_cityId3", carLineDTO.getE_cityId3());
		params.put("f_areaId3", carLineDTO.getE_areaId3());
		params.put("f_provinceId4", carLineDTO.getE_provinceId4());
		params.put("f_cityId4", carLineDTO.getE_cityId4());
		params.put("f_areaId4", carLineDTO.getE_areaId4());
		params.put("f_provinceId5", carLineDTO.getE_provinceId5());
		params.put("f_cityId5", carLineDTO.getE_cityId5());
		params.put("f_areaId5", carLineDTO.getE_areaId5());
		params.put("userType", carLineDTO.getUserType());
		params.put("userId", carLineDTO.getMemberId());
		params.put("createUserId", carLineDTO.getCreateUserId());
		params.put("startRow", carLineDTO.getStartRow());
		params.put("endRow", carLineDTO.getEndRow());
		params.put("carType", carLineDTO.getCarType());
		params.put("totalSize", carLineDTO.getCarLength());
		params.put("mlng", carLineDTO.getMlng());
		params.put("mlat", carLineDTO.getMlat());
		params.put("bjlng", carLineDTO.getBjlng());
		params.put("bjlat", carLineDTO.getBjlat());
		params.put("cqlng", carLineDTO.getCqlng());
		params.put("cqlat", carLineDTO.getCqlat());
		params.put("shlng", carLineDTO.getShlng());
		params.put("shlat", carLineDTO.getShlat());
		params.put("tjlng", carLineDTO.getTjlng());
		params.put("tjlat", carLineDTO.getTjlat());
		params.put("distance", carLineDTO.getDistance());
		return (Integer)this.baseDao.queryForObject("MemberAddress.getMebApiMessageCount",params,Integer.class);
	}

	@Override
	public Long getmemberAddressId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		return (Long)baseDao.queryForObject("MemberAddress.getmemberAddressId",params,Long.class);
	}

	@Override
	public List<CarLineDTO> getCarlineApiMessage(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("e_provinceId", memberAddressDTO.getF_provinceId());
		params.put("e_cityId", memberAddressDTO.getF_cityId());
		params.put("e_areaId", memberAddressDTO.getF_areaId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("carLength", memberAddressDTO.getCarLength());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("tjlat", memberAddressDTO.getTjlat());
		
		/**
		 * 给再次推送 过滤掉第一次线路集合
		 */
		if (memberAddressDTO.getCarLineIds()!=null && memberAddressDTO.getCarLineIds().size()!=0){
			params.put("carLineIds", memberAddressDTO.getCarLineIds());
		}
		
		return baseDao.queryForList("MemberAddress.getCarlineApiMessage",params,CarLineDTO.class);
	}

	@Override
	public List<MemberAddressDTO> getGoodsListByAreaId(Map<String, Object> map)
			throws Exception {
		List<MemberAddressDTO> list= baseDao.queryForList("MemberAddress.getListByAreaId", map, MemberAddressDTO.class);
		return list;
	}

	@Override
	public void getCarlineApiMessage(MemberAddressDTO memberAddressDTO,
			List<CarLineDTO> list) throws Exception {
		Map <String, Object> p = new HashMap<String, Object>();
		p.put("mbId", memberAddressDTO.getId());
		p.put("memberId", memberAddressDTO.getUserId());
		p.put("type",1);
		p.put("createUserId", memberAddressDTO.getUserId());
		p.put("updateUserId", memberAddressDTO.getUserId());
		p.put("mCity", memberAddressDTO.getmCity());
		p.put("cityId", memberAddressDTO.getCityId());
		p.put("source_type", "0");
		baseDao.execute("MemberAddress.addnstpushmessage", p);
		Integer id=baseDao.queryForObject("MemberAddress.getnstpushmessage", p,  Integer.class);
		Map <String, Object> p2 = new HashMap<String, Object>();
		if(id != null){
			for (int i = 0; i < list.size(); i++) {
				p2.put("messageId", id);
				p2.put("cl_Id", list.get(i).getId());
				p2.put("type",1);
				p2.put("createUserId", list.get(i).getMemberId());
				p2.put("updateUserId", list.get(i).getMemberId());
				p2.put("carType",list.get(i).getCarType());
				p2.put("carLength",list.get(i).getCarLength());
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
				
				p2.put("distance", df.format(list.get(i).getDistance()));
				p2.put("mCity", memberAddressDTO.getmCity());
				baseDao.execute("MemberAddress.addnstpushmessageinfo", p2);
			}
		}
		
	}

	@Override
	public Integer updateMemberAdressStatusByid(String memberAdressIds)
			throws Exception {
		
		Map<String, Object> paramMap=new HashMap<>();
		String[] str=memberAdressIds.split(",");
		List<String> ids=Arrays.asList(str);
		//其他app删除发布的货源  ，修改商品的转态
		if (ids!=null && ids.size()==1) {
			nstOrderBaseinfoService.updateDeliverStatusByMemberAddressId(Long.parseLong(ids.get(0)), 0);
		}
		paramMap.put("memberAdressIds", ids);
	
		return baseDao.execute("MemberAddress.updateMemberAdressStatusByid", paramMap);
	}

	@Override
	public List<MemberAddressDTO> getMebApiMessagetwo(CarLineDTO carLineDTO,
			Map<String, Object> p2) throws Exception {
			// TODO Auto-generated method stub
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("s_provinceId", carLineDTO.getS_provinceId());
			params.put("s_cityId", carLineDTO.getS_cityId());
			params.put("s_areaId", carLineDTO.getS_areaId());
			params.put("s_provinceId2", carLineDTO.getS_provinceId2());
			params.put("s_cityId2", carLineDTO.getS_cityId2());
			params.put("s_areaId2", carLineDTO.getS_areaId2());
			params.put("s_provinceId3", carLineDTO.getS_provinceId3());
			params.put("s_cityId3", carLineDTO.getS_cityId3());
			params.put("s_areaId3", carLineDTO.getS_areaId3());
			params.put("f_provinceId", carLineDTO.getE_provinceId());
			params.put("f_cityId", carLineDTO.getE_cityId());
			params.put("f_areaId", carLineDTO.getE_areaId());
			params.put("f_provinceId2", carLineDTO.getE_provinceId2());
			params.put("f_cityId2", carLineDTO.getE_cityId2());
			params.put("f_areaId2", carLineDTO.getE_areaId2());
			params.put("f_provinceId3", carLineDTO.getE_provinceId3());
			params.put("f_cityId3", carLineDTO.getE_cityId3());
			params.put("f_areaId3", carLineDTO.getE_areaId3());
			params.put("f_provinceId4", carLineDTO.getE_provinceId4());
			params.put("f_cityId4", carLineDTO.getE_cityId4());
			params.put("f_areaId4", carLineDTO.getE_areaId4());
			params.put("f_provinceId5", carLineDTO.getE_provinceId5());
			params.put("f_cityId5", carLineDTO.getE_cityId5());
			params.put("f_areaId5", carLineDTO.getE_areaId5());
			params.put("userType", carLineDTO.getUserType());
			params.put("userId", carLineDTO.getMemberId());
			params.put("createUserId", carLineDTO.getCreateUserId());
			params.put("startRow", 0);
			
			params.put("carType", carLineDTO.getCarType());
			params.put("totalSize", carLineDTO.getCarLength());
			params.put("mlng", carLineDTO.getMlng());
			params.put("mlat", carLineDTO.getMlat());
			params.put("bjlng", carLineDTO.getBjlng());
			params.put("bjlat", carLineDTO.getBjlat());
			params.put("cqlng", carLineDTO.getCqlng());
			params.put("cqlat", carLineDTO.getCqlat());
			params.put("shlng", carLineDTO.getShlng());
			params.put("shlat", carLineDTO.getShlat());
			params.put("tjlng", carLineDTO.getTjlng());
			params.put("tjlat", carLineDTO.getTjlat());
			params.put("distance", carLineDTO.getDistance());
			
			
			
			Iterator<Map.Entry<String, Object>> entries = p2.entrySet().iterator();  
			  
			while (entries.hasNext()) {  
			  
			    Map.Entry<String, Object> entry = entries.next();  
			  
			   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			    params.put(""+entry.getKey(), entry.getValue());
			  
			}  
			return this.baseDao.queryForList("MemberAddress.getMebApiMessage2",params,MemberAddressDTO.class);
		}

	@Override
	public List<MemberAddressDTO> getMebApiMessageAagin(CarLineDTO carLineDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", carLineDTO.getS_provinceId());
		params.put("s_cityId", carLineDTO.getS_cityId());
		params.put("s_areaId", carLineDTO.getS_areaId());
		params.put("s_provinceId2", carLineDTO.getS_provinceId2());
		params.put("s_cityId2", carLineDTO.getS_cityId2());
		params.put("s_areaId2", carLineDTO.getS_areaId2());
		params.put("s_provinceId3", carLineDTO.getS_provinceId3());
		params.put("s_cityId3", carLineDTO.getS_cityId3());
		params.put("s_areaId3", carLineDTO.getS_areaId3());
		params.put("f_provinceId", carLineDTO.getE_provinceId());
		params.put("f_cityId", carLineDTO.getE_cityId());
		params.put("f_areaId", carLineDTO.getE_areaId());
		params.put("f_provinceId2", carLineDTO.getE_provinceId2());
		params.put("f_cityId2", carLineDTO.getE_cityId2());
		params.put("f_areaId2", carLineDTO.getE_areaId2());
		params.put("f_provinceId3", carLineDTO.getE_provinceId3());
		params.put("f_cityId3", carLineDTO.getE_cityId3());
		params.put("f_areaId3", carLineDTO.getE_areaId3());
		params.put("f_provinceId4", carLineDTO.getE_provinceId4());
		params.put("f_cityId4", carLineDTO.getE_cityId4());
		params.put("f_areaId4", carLineDTO.getE_areaId4());
		params.put("f_provinceId5", carLineDTO.getE_provinceId5());
		params.put("f_cityId5", carLineDTO.getE_cityId5());
		params.put("f_areaId5", carLineDTO.getE_areaId5());
		params.put("userType", carLineDTO.getUserType());
		params.put("userId", carLineDTO.getMemberId());
		params.put("createUserId", carLineDTO.getCreateUserId());
		params.put("startRow", carLineDTO.getStartRow());
		params.put("endRow", carLineDTO.getEndRow());
		params.put("carType", carLineDTO.getCarType());
		params.put("totalSize", carLineDTO.getCarLength());
		params.put("mlng", carLineDTO.getMlng());
		params.put("mlat", carLineDTO.getMlat());
		params.put("bjlng", carLineDTO.getBjlng());
		params.put("bjlat", carLineDTO.getBjlat());
		params.put("cqlng", carLineDTO.getCqlng());
		params.put("cqlat", carLineDTO.getCqlat());
		params.put("shlng", carLineDTO.getShlng());
		params.put("shlat", carLineDTO.getShlat());
		params.put("tjlng", carLineDTO.getTjlng());
		params.put("tjlat", carLineDTO.getTjlat());
		params.put("distance", carLineDTO.getDistance());
		params.put("carLindId", carLineDTO.getId());
		return this.baseDao.queryForList("MemberAddress.getMebApiMessageAagin",params,MemberAddressDTO.class);
}
	@Override
	public MemberAddressDTO getNstpushMessageById(Long id)
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("source_type", "0");
		return (MemberAddressDTO) baseDao.queryForObject("MemberAddress.getNstpushMessageById", paramMap, MemberAddressDTO.class);
	}
	

	@Override
	public List<PushNstMessageInfoDTO> getCarLinesBymessageId(Long id)
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("id", id);
		// TODO Auto-generated method stub
		return baseDao.queryForList("MemberAddress.getCarLinesBymessageId", paramMap, PushNstMessageInfoDTO.class);
	}
	
	@Override
	public List<MemberAddressDTO> getMemberAddressByConditionNewNst2(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("distance", memberAddressDTO.getDistance());
		params.put("tjlat", memberAddressDTO.getTjlat());
		params.put("carLengthCondition1", memberAddressDTO.getCarLengthCondition1());
		params.put("carLengthCondition2", memberAddressDTO.getCarLengthCondition2());
		params.put("weightCondition1", memberAddressDTO.getWeightCondition1());
		params.put("weightCondition2", memberAddressDTO.getWeightCondition2());
		params.put("sizeCondition1", memberAddressDTO.getSizeCondition1());
		params.put("sizeCondition2", memberAddressDTO.getSizeCondition2());
		params.put("carLength", memberAddressDTO.getCarLength());
		int minMemberAddressId=baseDao.queryForObject("MemberAddress.getNotOverdueMinMemberAddressId",null,Integer.class);
		if (minMemberAddressId>0) {
			params.put("minMemberAddressId", minMemberAddressId);
		}
		return this.baseDao.queryForList("MemberAddress.listMemberAddressByConditionNewNst2",params,MemberAddressDTO.class);

	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByCarLine(
			CarLineDTO carLineDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", carLineDTO.getS_provinceId());
		params.put("s_cityId", carLineDTO.getS_cityId());
		params.put("s_areaId", carLineDTO.getS_areaId());
		params.put("s_provinceId2", carLineDTO.getS_provinceId2());
		params.put("s_cityId2", carLineDTO.getS_cityId2());
		params.put("s_areaId2", carLineDTO.getS_areaId2());
		params.put("s_provinceId3", carLineDTO.getS_provinceId3());
		params.put("s_cityId3", carLineDTO.getS_cityId3());
		params.put("s_areaId3", carLineDTO.getS_areaId3());
		params.put("f_provinceId", carLineDTO.getE_provinceId());
		params.put("f_cityId", carLineDTO.getE_cityId());
		params.put("f_areaId", carLineDTO.getE_areaId());
		params.put("f_provinceId2", carLineDTO.getE_provinceId2());
		params.put("f_cityId2", carLineDTO.getE_cityId2());
		params.put("f_areaId2", carLineDTO.getE_areaId2());
		params.put("f_provinceId3", carLineDTO.getE_provinceId3());
		params.put("f_cityId3", carLineDTO.getE_cityId3());
		params.put("f_areaId3", carLineDTO.getE_areaId3());
		params.put("f_provinceId4", carLineDTO.getE_provinceId4());
		params.put("f_cityId4", carLineDTO.getE_cityId4());
		params.put("f_areaId4", carLineDTO.getE_areaId4());
		params.put("f_provinceId5", carLineDTO.getE_provinceId5());
		params.put("f_cityId5", carLineDTO.getE_cityId5());
		params.put("f_areaId5", carLineDTO.getE_areaId5());
		params.put("userType", carLineDTO.getUserType());
		params.put("userId", carLineDTO.getMemberId());
		return this.baseDao.queryForList("MemberAddress.getMemberAddressByCarLine",params,MemberAddressDTO.class);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByIdNst2(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", memberAddressDTO.getId());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("tjlat", memberAddressDTO.getTjlat());
		return  this.baseDao.queryForList("MemberAddress.getMemberAddressByIdNst2", params, MemberAddressDTO.class);
	}

	@Override
	public int getCountByConditionNst2(MemberAddressDTO memberAddressDTO)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("distance", memberAddressDTO.getDistance());
		params.put("tjlat", memberAddressDTO.getTjlat());
		params.put("carLengthCondition1", memberAddressDTO.getCarLengthCondition1());
		params.put("carLengthCondition2", memberAddressDTO.getCarLengthCondition2());
		params.put("weightCondition1", memberAddressDTO.getWeightCondition1());
		params.put("weightCondition2", memberAddressDTO.getWeightCondition2());
		params.put("sizeCondition1", memberAddressDTO.getSizeCondition1());
		params.put("sizeCondition2", memberAddressDTO.getSizeCondition2());
		params.put("carLength", memberAddressDTO.getCarLength());
		int minMemberAddressId=baseDao.queryForObject("MemberAddress.getNotOverdueMinMemberAddressId",null,Integer.class);
		if (minMemberAddressId>0) {
			params.put("minMemberAddressId", minMemberAddressId);
		}
		return  (int) baseDao.queryForObject("MemberAddress.getCountByConditionNst2", params, Integer.class);
	}

	@Override
	public int checkCity(String mcity) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mcity", mcity);
		return  (int) baseDao.queryForObject("MemberAddress.checkCity", params, Integer.class);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByUserCondition(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("tjlat", memberAddressDTO.getTjlat());
		params.put("carLengthCondition1", memberAddressDTO.getCarLengthCondition1());
		params.put("carLengthCondition2", memberAddressDTO.getCarLengthCondition2());
		params.put("weightCondition1", memberAddressDTO.getWeightCondition1());
		params.put("weightCondition2", memberAddressDTO.getWeightCondition2());
		params.put("sizeCondition1", memberAddressDTO.getSizeCondition1());
		params.put("sizeCondition2", memberAddressDTO.getSizeCondition2());
		params.put("carLength", memberAddressDTO.getCarLength());
		params.put("beginTime", memberAddressDTO.getBeginTime());
		params.put("endTime", memberAddressDTO.getEndTime());
		params.put("goodsName", memberAddressDTO.getGoodsName());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		return this.baseDao.queryForList("MemberAddress.getMemberAddressByUserCondition",params,MemberAddressDTO.class);
	} 
	
	@Override
	public List<RecommendedUserDTO> getListByAddress(Map<String, Object> map)
			throws Exception {
		List<RecommendedUserDTO> list= baseDao.queryForList("MemberAddress.getListByAddress", map, RecommendedUserDTO.class);
		return list;
	}

	@Override
	public int getTotalByAddress(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("MemberAddress.getTotalByAddress", map, Integer.class);

	}


	@Override
	public List<MemberAddressDTO> getCompanyToMb(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		return this.baseDao.queryForList("MemberAddress.getCompanyToMb",params,MemberAddressDTO.class);

	}

	@Override
	public MemberAddressDTO getCompanyToMbgetL(
			MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		return (MemberAddressDTO)this.baseDao.queryForObject("MemberAddress.getCompanyToMbgetL",params,MemberAddressDTO.class);
	}

	@Override
	public int getRule(String clients) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clients", clients);
		Integer result =  (Integer) this.baseDao.queryForObject("MemberAddress.getRule",params,Integer.class);
		return result == null ? 0 : result;
	}

	@Override
	public Object updateCreateUserId(String createUserId, String clients,Long id) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clients", clients);
		params.put("createUserId", createUserId);
		params.put("id", id);
		return (int) baseDao.execute("MemberAddress.updateCreateUserId", params);
	}

	
	@Override
	public List<NstMemberAddressDTO> getDistributeAddressList(
			Map<String, Object> map) throws Exception {
		List<NstMemberAddressDTO> list= baseDao.queryForList("MemberAddress.getPublishMemberAddressList", map, NstMemberAddressDTO.class);
		return list;
	}

	@Override
	public int getDistributeAddressTotal(Map<String, Object> map)
			throws Exception {
		return (int) baseDao.queryForObject("MemberAddress.getPublishMemberAddressTotal", map, Integer.class);
	}

	@Override
	public void updatenstRule(Long id) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		 baseDao.execute("MemberAddress.updatenstRule", params);
		
	}

	@Override
	public MemberAddressDTO getByIdForOrder(String id, Integer type, Integer source) throws Exception {
		Map <String, String> p = new HashMap<String, String>();	
		p.put("source", source.toString());
		if(type==0) {
			p.put("id", id);
		} else {
			p.put("nobId", id);
		}
		return (MemberAddressDTO)this.baseDao.queryForObject("MemberAddress.getMemberAddressForOrder", p, MemberAddressDTO.class);
	}

	@Override
	public Long addMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
		Long memberAddressId = null;
		Lock lock = new ReentrantLock();
		lock.lock();
		try{
			int num = addMemberAddressDTO(memberAddressDTO);
			if(num > 0){
				memberAddressId = getmemberAddressId(memberAddressDTO.getUserId());
			}
		}catch(Exception ex){
		     throw new Exception("新增货源失败", ex);
		}finally{
		    lock.unlock();   //释放锁
		}
		return memberAddressId;
	}

	@Override
	public Integer getTotalForOrder(Map<String, Object> query) throws Exception {
		return  (int) baseDao.queryForObject("MemberAddress.getTotalForOrder", query, Integer.class);
	}

	@Override
	public List<MemberAddressDTO> getListForOrder(Map<String, Object> query) throws Exception {
		return  (List<MemberAddressDTO>) baseDao.queryForList("MemberAddress.getListForOrder", query, MemberAddressDTO.class);
	}
@Override
	public Integer getMemberAddressByUserConditionCount(MemberAddressDTO memberAddressDTO) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("s_provinceId", memberAddressDTO.getS_provinceId());
		params.put("s_cityId", memberAddressDTO.getS_cityId());
		params.put("s_areaId", memberAddressDTO.getS_areaId());
		params.put("f_provinceId", memberAddressDTO.getF_provinceId());
		params.put("f_cityId", memberAddressDTO.getF_cityId());
		params.put("f_areaId", memberAddressDTO.getF_areaId());
		params.put("totalWeight", memberAddressDTO.getTotalWeight());
		params.put("hundredweight", memberAddressDTO.getHundredweight());
		params.put("totalSize", memberAddressDTO.getTotalSize());
		params.put("goodsType", memberAddressDTO.getGoodsType());
		params.put("sendDate", memberAddressDTO.getSendDate());
		params.put("sendDateType", memberAddressDTO.getSendDateType());
		params.put("id", memberAddressDTO.getId());
		params.put("userType", memberAddressDTO.getUserType());
		params.put("userId", memberAddressDTO.getUserId());
		params.put("createUserId", memberAddressDTO.getCreateUserId());
		params.put("startRow", memberAddressDTO.getStartRow());
		params.put("endRow", memberAddressDTO.getEndRow());
		params.put("carType", memberAddressDTO.getCarType());
		params.put("mlng", memberAddressDTO.getMlng());
		params.put("mlat", memberAddressDTO.getMlat());
		params.put("bjlng", memberAddressDTO.getBjlng());
		params.put("bjlat", memberAddressDTO.getBjlat());
		params.put("cqlng", memberAddressDTO.getCqlng());
		params.put("cqlat", memberAddressDTO.getCqlat());
		params.put("shlng", memberAddressDTO.getShlng());
		params.put("shlat", memberAddressDTO.getShlat());
		params.put("tjlng", memberAddressDTO.getTjlng());
		params.put("tjlat", memberAddressDTO.getTjlat());
		params.put("carLengthCondition1", memberAddressDTO.getCarLengthCondition1());
		params.put("carLengthCondition2", memberAddressDTO.getCarLengthCondition2());
		params.put("weightCondition1", memberAddressDTO.getWeightCondition1());
		params.put("weightCondition2", memberAddressDTO.getWeightCondition2());
		params.put("sizeCondition1", memberAddressDTO.getSizeCondition1());
		params.put("sizeCondition2", memberAddressDTO.getSizeCondition2());
		params.put("carLength", memberAddressDTO.getCarLength());
		params.put("beginTime", memberAddressDTO.getBeginTime());
		params.put("endTime", memberAddressDTO.getEndTime());
		params.put("goodsName", memberAddressDTO.getGoodsName());
		return (Integer) baseDao.queryForObject("MemberAddress.getMemberAddressByUserConditionCount", params, Integer.class);
	}

@Override
public List<RecommendedUserDTO> getListByAddressSameCity(Map<String, Object> map)
		throws Exception {
	// TODO Auto-generated method stub
	List<RecommendedUserDTO> list= baseDao.queryForList("NstSameCityAddress.getListByAddressSameCity", map, RecommendedUserDTO.class);
	return list;
}

@Override
public int getTotalByAddressSameCity(Map<String, Object> map) throws Exception {
	// TODO Auto-generated method stub
	return (int) baseDao.queryForObject("NstSameCityAddress.getTotalByAddressSameCity", map, Integer.class);
}
}

	
