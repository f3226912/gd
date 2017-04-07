package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
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
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;


/**
 *功能描述：MemberBaseinfo增删改查实现类
 *
 */
@Service
public class MemberBaseinfoServiceImpl implements MemberBaseinfoService{

	@Autowired
	private BaseDao baseDao;
	@Autowired
	private CacheBo cacheBo;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public int addMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception {
		//调用sql实现insert
//		统一由前台进行md5加密
//		if(null != mb.getPassword() && !mb.getPassword().equals("")){
//			mb.setPassword(JavaMd5.getMD5(mb.getPassword()+"gudeng2015@*&^-"));
//		}
		return (int) baseDao.execute("MemberBaseinfo.addMemberBaseinfo", mb);
	}

	@Override
	public int deleteById(String memberId) throws Exception {
//		Map map=new HashMap();
//		map.put("memberId", memberId);
//		//删除缓存
//		MemberBaseinfoDTO dto = this.getById(memberId);
//		if(null != dto){
//			if(null != dto.getMobile()){
//				cacheBo.deleteMemberCacheById(dto.getMobile());
//			}
//			if(null != dto.getAccount()){
//				cacheBo.deleteMemberCacheById(dto.getAccount());
//			}
//		}
//		//最后删除id
//		cacheBo.deleteMemberCacheById(memberId);
//		return (int) baseDao.execute("MemberBaseinfo.deleteMemberBaseinfo", map);
		return 0;
	}

	@Override
	public int updateMemberBaseinfoDTO(MemberBaseinfoDTO mb) throws Exception {
//		统一由前台进行md5加密
//		if(null != mb.getPassword() && !mb.getPassword().equals("")){
//			mb.setPassword(JavaMd5.getMD5(mb.getPassword()+"gudeng2015@*&^-"));
//		}
		//删除缓存

		MemberBaseinfoDTO dto = this.getById(String.valueOf(mb.getMemberId()));
		if(null != dto){
			if(null != dto.getMobile()){
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if(null != dto.getAccount()){
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		//最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(mb.getMemberId()));
		return (int) baseDao.execute("MemberBaseinfo.updateMemberBaseinfo", mb);
	}

	@Override
	public int updateStatus(MemberBaseinfoDTO mb) throws Exception {
		MemberBaseinfoDTO dto = this.getById(String.valueOf(mb.getMemberId()));
		if(null != dto){
			if(null != dto.getMobile()){
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if(null != dto.getAccount()){
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		//最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(mb.getMemberId()));
		return (int) baseDao.execute("MemberBaseinfo.updateStatus", mb);
	}
	@Override
	public int updateSubShow(MemberBaseinfoDTO mb) throws Exception {
		MemberBaseinfoDTO dto = this.getById(String.valueOf(mb.getMemberId()));
		if(null != dto){
			if(null != dto.getMobile()){
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if(null != dto.getAccount()){
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		//最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(mb.getMemberId()));
		return (int) baseDao.execute("MemberBaseinfo.updateSubShow", mb);
	}
	@Override
	public MemberBaseinfoDTO getById(String memberId) throws Exception {
		//Map map=new HashMap();
		//map.put("memberId", memberId);
		//return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getByMemberId", map, MemberBaseinfoDTO.class);
	return (MemberBaseinfoDTO)cacheBo.getMemberById(memberId, baseDao);

	}

	@Override
	public MemberBaseinfoDTO getByMobile(String mobile) throws Exception {
		/*Map map=new HashMap();
		map.put("mobile", mobile);
		return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getByMobile", map, MemberBaseinfoDTO.class);*/
		return cacheBo.getMemberByMobile(mobile, baseDao);
	}

	@Override
	public 	List<MemberBaseinfoDTO> getByNickName(Map map) throws Exception {
		return baseDao.queryForList("MemberBaseinfo.getByNickName", map, MemberBaseinfoDTO.class);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("MemberBaseinfo.getTotal", map, Integer.class);
	}
	
	@Override
	public int getGoldSupplierTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("MemberBaseinfo.getGoldSupplierTotal", map, Integer.class);
	}

	@Override
	public List<MemberBaseinfoDTO> getAll(Map map) throws Exception {
		return baseDao.queryForList("MemberBaseinfo.getAll", map, MemberBaseinfoDTO.class);
	}
	@Override
	public List<MemberBaseinfoDTO> getBySearch(Map map) throws Exception {
		//return  baseDao.queryForList("MemberBaseinfo.getBySearch", map, MemberBaseinfoDTO.class);
		return  baseDao.queryForList("MemberBaseinfo.getBySearch", map, MemberBaseinfoDTO.class);
	}
	
	@Override
	public List<MemberBaseinfoDTO> queryGoldSupplierbySearch(Map map) throws Exception {
		//return  baseDao.queryForList("MemberBaseinfo.getBySearch", map, MemberBaseinfoDTO.class);
		return  baseDao.queryForList("MemberBaseinfo.getGoldSupplierbySearch", map, MemberBaseinfoDTO.class);
	}

	@Override
	public Long addMemberBaseinfoEnt(MemberBaseinfoEntity mb) throws Exception {
		// 通过对象保存，返回当前ID。
//		统一由前台进行md5加密
//		if(null != mb.getPassword() && !mb.getPassword().equals("")){
//			mb.setPassword(JavaMd5.getMD5(mb.getPassword()+"gudeng2015@*&^-"));
//		}
		//农商友用户类型，用persist时，要默认设置个0，不然插入数据库中，为null，无法统计使用
		mb.setNsyUserType(mb.getNsyUserType()==null?0:mb.getNsyUserType());
		mb.setMemberGrade(mb.getMemberGrade()==null?0:mb.getMemberGrade());
		mb.setShopRecommend(mb.getShopRecommend()==null?1:mb.getShopRecommend());
		mb.setNsyUserType(mb.getNsyUserType()==null?0:mb.getNsyUserType());
		mb.setCreateTime(mb.getCreateTime()==null?new Date():mb.getCreateTime());
		return (Long)baseDao.persist(mb, Long.class);
	}

	@Override
	public MemberBaseinfoDTO getByAccount(String account) throws Exception {
		/*Map map=new HashMap();
		map.put("account", account);
		return  (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getByAccount", map, MemberBaseinfoDTO.class);*/
		return cacheBo.getMemberByAccount(account, baseDao);
	}

	@Override
	public List<MemberBaseinfoDTO> getByAccountLike(Map map) throws Exception {
		return  baseDao.queryForList("MemberBaseinfo.getByAccountLike", map, MemberBaseinfoDTO.class);
	}

	@Override
	public int updateMemberApp(MemberBaseinfoDTO memberDTO) throws Exception {
		cacheBo.deleteMemberCacheById(memberDTO.getMemberId()+"");
		return (int) baseDao.execute("MemberBaseinfo.updateMemberBaseinfoApp", memberDTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MemberBaseinfoDTO getLogin(Map<String, Object> map) throws Exception {
		return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getLogin", map, MemberBaseinfoDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBaseinfoDTO> commitLogin(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MemberBaseinfo.commitLogin", map, MemberBaseinfoDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MemberBrowseMarketHistoryDTO getBrowseHistoryByMemberId(Long memberId)
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		return (MemberBrowseMarketHistoryDTO) baseDao.queryForObject("MemberBaseinfo.getBrowseHistoryByMemberId", map, MemberBrowseMarketHistoryDTO.class);
	}

	@Override
	public int addBrowseHistory(MemberBrowseMarketHistoryDTO dto) throws Exception {
		return baseDao.execute("MemberBaseinfo.addBrowseHistory", dto);
	}

	@Override
	public MemberBaseinfoDTO checkRegister(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.checkRegister", map, MemberBaseinfoDTO.class);
	}

	@Override
	public MemberBaseinfoDTO getAppById(String id) throws Exception {
		// TODO Auto-generated method stub
		String memberId=id;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		return (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getByMemberId", map, MemberBaseinfoDTO.class);
	}

	@Override
	public int updateUserType(Long memberId, Integer userType) throws Exception {

		MemberBaseinfoDTO dto = this.getById(String.valueOf(memberId));
		if(null != dto){
			if(null != dto.getMobile()){
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if(null != dto.getAccount()){
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		//最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(memberId));

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("userType", userType);
		return (int) baseDao.execute("MemberBaseinfo.updateUserType", map);
	}


		@Override
	public MemberCertifiDTO getByUserId(Long memberId) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		return (MemberCertifiDTO)baseDao.queryForObject("MemberBaseinfo.getByMemberId", map, MemberCertifiDTO.class);

	}

		@Override
	public MemberBaseinfoDTO getCompanyName(Long memberId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		return (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getCompanyName", map, MemberBaseinfoDTO.class);
	}

		@Override
		public List<MemberBaseinfoDTO> getCompany() throws Exception {
			// TODO Auto-generated method stub
			return  baseDao.queryForList("MemberBaseinfo.getCompany", MemberBaseinfoDTO.class);
		}

		@Override
		public List<MemberBaseinfoDTO> getCompanyByCondition(Map<String, Object> map) throws Exception {
			return  baseDao.queryForList("MemberBaseinfo.getCompanyByCondition",map, MemberBaseinfoDTO.class);
		}

		@Override
		public int getCompanyByConditionTotal(Map<String, Object> map) throws Exception {
			return (int) baseDao.queryForObject("MemberBaseinfo.getCompanyByConditionTotal", map, Integer.class);
		}

				@Override
		public Object getlink(Map<String, Object> map) throws Exception {
			// TODO Auto-generated method stub
			return (int) baseDao.execute("MemberBaseinfo.getlink", map);
		}

		@Override
		//
		public int getActityNo(String actityNo) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("actityNo", actityNo);
			return (int)baseDao.queryForObject("MemberBaseinfo.getActityNo", map, Integer.class);

		}

		@Override
		public int addIntss(Map<String, Object> map) throws Exception {
			// TODO Auto-generated method stub

			//return (int)baseDao.queryForObject("MemberBaseinfo.getCompanyByConditionTotal", map, Integer.class);
			return (int)baseDao.queryForObject("MemberBaseinfo.addInt", map, Integer.class);

		}

		@Override
		public int getRecordByMemberInfo(Map<String, Object> map) throws Exception {
			// TODO Auto-generated method stub
			return (int)baseDao.queryForObject("MemberBaseinfo.getRecordByMemberInfo", map, Integer.class);

		}

		@Override
		public MemberBaseinfoDTO getContacts(String string) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("memberId", string);
			return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getContacts", map, MemberBaseinfoDTO.class);
		}

		@Override
		public MemberBaseinfoDTO getByMobileAndLevel(String username,
				Integer level) throws Exception {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("mobile", username);
			map.put("account", username);
			map.put("level", level);
			return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getByMobileAndLevel", map, MemberBaseinfoDTO.class);
		}

		@Override
		public List<MemberBaseinfoDTO> getCompanyByMcity(
				MemberAddressDTO memberAddressDTO) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("mCity", memberAddressDTO.getmCity());
			map.put("memberId", memberAddressDTO.getUserId());
			map.put("userType", memberAddressDTO.getUserType());
			map.put("ccityId", memberAddressDTO.getCcityId());
			return  baseDao.queryForList("MemberBaseinfo.getCompanyByMcity",map, MemberBaseinfoDTO.class);
		}

		@Override
		public List<PushNstMessageDTO> getPushMessage(
				MemberBaseinfoDTO memberDtoInput) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("memberId", memberDtoInput.getMemberId());
			map.put("startRow", memberDtoInput.getStartRow());
			map.put("endRow", memberDtoInput.getEndRow());
			return  baseDao.queryForList("MemberBaseinfo.getPushMessage",map, PushNstMessageDTO.class);
		}

		@Override
		public List<PushNstMessageInfoDTO> getPushMessageInfo(
				PushNstMessageDTO pushNstMessageDTO) {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", pushNstMessageDTO.getMessageId());
			return  baseDao.queryForList("MemberBaseinfo.getPushMessageInfo",map, PushNstMessageInfoDTO.class);
		}

		@Override
		public MemberBaseinfoDTO getByAccountNoCer(String account)
				throws Exception {
			Map map=new HashMap();
			map.put("account", account);
			return  (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getByAccountNoCer", map, MemberBaseinfoDTO.class);
		}

		@Override
		public List<PushNstMessageInfoDTO> getPushMessageInfoCarLine(
				PushNstMessageDTO pushNstMessageDTO) throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", pushNstMessageDTO.getMessageId());
			return  baseDao.queryForList("MemberBaseinfo.getPushMessageInfoCarLine",map, PushNstMessageInfoDTO.class);
		}

		@Override
		public int getPushMessageCount(MemberBaseinfoDTO memberDtoInput)
				throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("memberId", memberDtoInput.getMemberId());

			return  (int)baseDao.queryForObject("MemberBaseinfo.getPushMessageCount",map, Integer.class);

		}

		@Override
		public int updateNstEvaluate(Long memberId, Integer nstEvaluate) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("nstEvaluate", nstEvaluate);
			return baseDao.execute("MemberBaseinfo.updateNstEvaluate", paramMap);
		}

		@Override
		public int updateNsyUserType(Long memberId, Integer nsyUserType) {
			
			try {
				MemberBaseinfoDTO dto = this.getById(String.valueOf(memberId));
				if(null != dto){
					if(null != dto.getMobile()){
						cacheBo.deleteMemberCacheById(dto.getMobile());
					}
					if(null != dto.getAccount()){
						cacheBo.deleteMemberCacheById(dto.getAccount());
					}
				}
				//最后删除id
				cacheBo.deleteMemberCacheById(String.valueOf(memberId));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("nsyUserType", nsyUserType);
			return baseDao.execute("MemberBaseinfo.updateNsyUserType", paramMap);
		}

		@Override
		public MemberBaseinfoDTO getMemberInfoByMemberedId(Long memberId)
				throws Exception {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			return (MemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getMemberInfoByMemberedId", paramMap, MemberBaseinfoDTO.class);

		}

		@Override
		public List<MemberBaseinfoDTO> queryMemberListByMap(MemberBaseinfoDTO memberDtoInput) throws Exception {
			return baseDao.queryForList("MemberBaseinfo.queryMemberListByMap", memberDtoInput);
		}

		@Override
		@Transactional
		public Long addMember(MemberBaseinfoDTO mb) throws Exception {
			MemberBaseinfoEntity mbEntity = new MemberBaseinfoEntity();
			mbEntity.setAccount(mb.getAccount());
			mbEntity.setPassword(mb.getPassword());
			mbEntity.setMobile(mb.getMobile());
			mbEntity.setRealName(mb.getRealName());
			mbEntity.setLevel(mb.getLevel());//农速通
			mbEntity.setStatus(mb.getStatus());
			mbEntity.setCreateTime(mb.getCreateTime());
			mbEntity.setRegetype(mb.getRegetype());//pos注册用户
			Long memberId = addMemberBaseinfoEnt(mbEntity);
			ReMemForCustDTO reMemForCust = new ReMemForCustDTO();
			reMemForCust.setBusiMemberId(mb.getBusiMemberId()); // 商家ID（供应商或批发商）
			reMemForCust.setCustMemberId(memberId); // 买家会员ID
			reMemForCust.setType("2");
			addReMemForCust(reMemForCust);
			return memberId;
		}

		@Override
		public int addReMemForCust(ReMemForCustDTO reMemForCust){
			return baseDao.execute("MemberBaseinfo.addReMemForCust", reMemForCust);
		}

		@Override
		public int updateReMemForCust(ReMemForCustDTO reMemForCust){
			return baseDao.execute("MemberBaseinfo.updateReMemForCust", reMemForCust);
		}

		@Override
		public List<NstMemberBaseinfoDTO> getNstListBySearch(Map map)
				throws Exception {
			return  baseDao.queryForList("MemberBaseinfo.queryByParameters", map, NstMemberBaseinfoDTO.class);

		}

		@Override
		public int getNstTotalBySearch(Map map) throws Exception {
			return (int) baseDao.queryForObject("MemberBaseinfo.queryTotalByParameters", map, Integer.class);

		}

		@Override
		public NstMemberBaseinfoDTO getNstMemberById(String id)
				throws Exception {
			Map map=new HashMap();
			map.put("memberId", id);
			return (NstMemberBaseinfoDTO) baseDao.queryForObject("MemberBaseinfo.getNstMemberById", map, NstMemberBaseinfoDTO.class);

		}

		@SuppressWarnings("unchecked")
		@Override
		public List<MemberSinxinDTO> queryMemberForSinxin(MemberSinxinDTO queryDTO) throws Exception {
			return baseDao.queryForList("MemberBaseinfo.queryMemberForSinxin", queryDTO, MemberSinxinDTO.class);
		}

		@Override
		public MemberBusinessInfoDTO getMemberBusinessInfo(Map<String, Object> param) {
			return (MemberBusinessInfoDTO) baseDao.queryForObject("MemberBaseinfo.getMemberBusinessInfo", param, MemberBusinessInfoDTO.class);

		}

		@Override
		public MemberBaseinfoDTO queryMemberInfoForPromotion(Map<String, Object> params) throws Exception {
			return (MemberBaseinfoDTO) baseDao.queryForObject("MemberForPromotion.queryMemberInfoForPromotion", params, MemberBaseinfoDTO.class);
		}

		@Override
		public List<MemberBaseinfoDTO> queryMemberSelectPageByCondition(Map<String, Object> map) {
			return baseDao.queryForList("MemberForPromotion.queryMemberSelectPageByCondition", map, MemberBaseinfoDTO.class);
		}

		@Override
		public int getMemberSelectTotalByCondition(Map<String, Object> map) {
			return (int) baseDao.queryForObject("MemberForPromotion.getMemberSelectTotalByCondition", map, Integer.class);
		}

	@Override
	public int updateMobile(Long memberId, String mobile) throws Exception {
		MemberBaseinfoDTO dto = this.getById(String.valueOf(memberId));
		if (null != dto) {
			if (null != dto.getMobile()) {
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if (null != dto.getAccount()) {
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		// 最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(memberId));
		Map map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("mobile", mobile);
		return (int) baseDao.execute("MemberBaseinfo.updateMobile", map);
	}

	@Override
	public int updateRealName(Long memberId, String realName) throws Exception {
		MemberBaseinfoDTO dto = this.getById(String.valueOf(memberId));
		if (null != dto) {
			if (null != dto.getMobile()) {
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if (null != dto.getAccount()) {
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		// 最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(memberId));
		Map map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("realName", realName);
		return (int) baseDao.execute("MemberBaseinfo.updateRealName", map);
	}

	@Override
	public int updatePassword(Long memberId, String password) throws Exception {
		MemberBaseinfoDTO dto = this.getById(String.valueOf(memberId));
		if (null != dto) {
			if (null != dto.getMobile()) {
				cacheBo.deleteMemberCacheById(dto.getMobile());
			}
			if (null != dto.getAccount()) {
				cacheBo.deleteMemberCacheById(dto.getAccount());
			}
		}
		// 最后删除id
		cacheBo.deleteMemberCacheById(String.valueOf(memberId));
		Map map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("password", password);
		return (int) baseDao.execute("MemberBaseinfo.updatePassword", map);
	}
	@Override
	public Integer getAppTypeByMemberId(String memberId) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("memberId", memberId);
		return (Integer) baseDao.queryForObject("MemberBaseinfo.getAppTypeByMemberId", paramMap, Integer.class);
	}

	@Override
	public List<MemberBaseinfoDTO> getBuyerListForGdActivity(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("MemberBaseinfo.getBuyerListForGdActivity", map, MemberBaseinfoDTO.class);
	}

	@Override
	public MemberBaseinfoDTO getbyMemberAddressId(String memberAddressId) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberAddressId", memberAddressId);
		return  (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getbyMemberAddressId", map, MemberBaseinfoDTO.class);	
	}
	@Override
	public MemberBaseinfoDTO getMemberTonken(String memberId) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId", memberId);
		return  (MemberBaseinfoDTO)baseDao.queryForObject("MemberBaseinfo.getbyMemberAddressId", map, MemberBaseinfoDTO.class);	
	}
}
