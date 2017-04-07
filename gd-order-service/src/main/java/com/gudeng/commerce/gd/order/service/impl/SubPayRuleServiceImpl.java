package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.CategoryTreeDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;
import com.gudeng.commerce.gd.order.dto.SubRangePayRuleDTO;
import com.gudeng.commerce.gd.order.entity.SubRangePayRuleEntity;
import com.gudeng.commerce.gd.order.service.SubPayRuleService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@SuppressWarnings({"rawtypes","unchecked"})
public class SubPayRuleServiceImpl implements SubPayRuleService {

	
	@Autowired
	private BaseDao  baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	@Transactional
	public long addRule(SubPayRuleParamDTO param) throws Exception {
//		try{
			long ruleId = (Long)baseDao.persist(param.getSubPayRule());
			//添加规则分类
			if(null!=param.getcList()){
			Map<String,Object>[] cRecords = new HashMap[param.getcList().size()];
			Set<CategoryTreeDTO> entrySet = param.getcList();
			int n=0;
			for (Iterator<CategoryTreeDTO> it = entrySet.iterator(); it.hasNext();) {
				CategoryTreeDTO node = (CategoryTreeDTO) it.next();
				Map<String,Object> cRecord = new HashMap<String,Object>();
				//ruleId,type,value,categoryCode,level
				cRecord.put("ruleId",ruleId );
				cRecord.put("type", node.getCurLevel());
				cRecord.put("value",node.getCategoryId());
				cRecords[n]=cRecord;
				n+=1;
			}
			baseDao.batchUpdate("subPayRule.addSubPayRuleCategory", cRecords);			
			}
			
			int type = Integer.parseInt(param.getSubPayRule().getSubType());
			if(type<3)return ruleId;
			
			switch(type){
			
			case 3:
				//按采购重量区间进行补贴
				//ruleId,lowerLimit,upperLimit,unit,subUnit,carType,truck,subAmount
				if(null==param.getzList())break;
				Map<String,Object>[] zRecords = new HashMap[param.getzList().size()];
				SubRangePayRuleEntity scz=null;
				for(int x=0;x<param.getzList().size();x++){
					Map<String,Object> zRecord = new HashMap<String,Object>();
					scz = param.getzList().get(x);
					//ruleId,type,value,categoryCode,level
					zRecord.put("ruleId",ruleId );
					zRecord.put("lowerLimit", scz.getLowerLimit());
					zRecord.put("upperLimit",scz.getUpperLimit() );
					zRecord.put("unit",scz.getUnit());
					zRecord.put("subUnit",scz.getSubUnit() );
					zRecord.put("carType",0);
					zRecord.put("truck","");
					zRecord.put("subAmount",scz.getSubAmount() );
					zRecords[x]=zRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", zRecords);

				break;
			case 4:
				//添加规则交易金额区间
				if(null==param.getrList())break;
				Map<String,Object>[] rRecords = new HashMap[param.getrList().size()];
				SubRangePayRuleEntity sr=null;
				for(int m=0;m<param.getrList().size();m++){
					Map<String,Object> rRecord = new HashMap<String,Object>();
					sr = param.getrList().get(m);
					rRecord.put("ruleId",ruleId );
					rRecord.put("lowerLimit", sr.getLowerLimit());
					rRecord.put("upperLimit",sr.getUpperLimit() );
					rRecord.put("unit",sr.getUnit());
					rRecord.put("subUnit",sr.getSubUnit());
					rRecord.put("carType",0);
					rRecord.put("truck","");
					rRecord.put("subAmount",sr.getSubAmount() );
					
					rRecords[m]=rRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", rRecords);
				break;
			case 5:
				//门岗目测审查
				if(null==param.getmList())break;
				Map<String,Object>[] mRecords = new HashMap[param.getmList().size()];
				SubRangePayRuleEntity srm=null;
				for(int m=0;m<param.getmList().size();m++){
					Map<String,Object> mRecord = new HashMap<String,Object>();
					srm = param.getmList().get(m);
					mRecord.put("ruleId",ruleId );
					mRecord.put("lowerLimit", "");
					mRecord.put("upperLimit","");
					mRecord.put("unit",srm.getUnit());
					mRecord.put("subUnit",srm.getSubUnit());
					mRecord.put("carType",srm.getCarType());
					mRecord.put("truck",srm.getTruck());
					mRecord.put("subAmount",srm.getSubAmount() );
					
					mRecords[m]=mRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", mRecords);
				break;
			default:;	
			
			}
			return ruleId;
			
//		}catch(Exception e){
//			e.printStackTrace();
//			return 0;
//		}

	}
	
	@Override
	@Transactional
	public long modifyRule(SubPayRuleParamDTO param) throws Exception {
//		try{
			int result = baseDao.dynamicMerge(param.getSubPayRule());
			long ruleId = param.getSubPayRule().getRuleId();
			//先删除分类和规则范围
			Map<String,Object> delmap = new HashMap<String,Object>();
			delmap.put("ruleId", ruleId);
			baseDao.execute("subPayRule.delCategroyByRuleId", delmap);
			baseDao.execute("subPayRule.delRangByRuleId", delmap);
			//添加规则分类
			if(null!=param.getcList()){
			Map<String,Object>[] cRecords = new HashMap[param.getcList().size()];
			Set<CategoryTreeDTO> entrySet = param.getcList();
			int n=0;
			for (Iterator<CategoryTreeDTO> it = entrySet.iterator(); it.hasNext();) {
				CategoryTreeDTO node = (CategoryTreeDTO) it.next();
				Map<String,Object> cRecord = new HashMap<String,Object>();
				//ruleId,type,value,categoryCode,level
				cRecord.put("ruleId",ruleId );
				cRecord.put("type", node.getCurLevel());
				cRecord.put("value",node.getCategoryId());
				cRecords[n]=cRecord;
				n+=1;
			}
			baseDao.batchUpdate("subPayRule.addSubPayRuleCategory", cRecords);			
			}
			
			int type = Integer.parseInt(param.getSubPayRule().getSubType());
			if(type<3)return ruleId;
			
			switch(type){
			
			case 3:
				//按采购重量区间进行补贴
				//ruleId,lowerLimit,upperLimit,unit,subUnit,carType,truck,subAmount
				if(null==param.getzList())break;
				Map<String,Object>[] zRecords = new HashMap[param.getzList().size()];
				SubRangePayRuleEntity scz=null;
				for(int x=0;x<param.getzList().size();x++){
					Map<String,Object> zRecord = new HashMap<String,Object>();
					scz = param.getzList().get(x);
					//ruleId,type,value,categoryCode,level
					zRecord.put("ruleId",ruleId );
					zRecord.put("lowerLimit", scz.getLowerLimit());
					zRecord.put("upperLimit",scz.getUpperLimit() );
					zRecord.put("unit",scz.getUnit());
					zRecord.put("subUnit",scz.getSubUnit() );
					zRecord.put("carType",0);
					zRecord.put("truck","");
					zRecord.put("subAmount",scz.getSubAmount() );
					zRecords[x]=zRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", zRecords);

				break;
			case 4:
				//添加规则交易金额区间
				if(null==param.getrList())break;
				Map<String,Object>[] rRecords = new HashMap[param.getrList().size()];
				SubRangePayRuleEntity sr=null;
				for(int m=0;m<param.getrList().size();m++){
					Map<String,Object> rRecord = new HashMap<String,Object>();
					sr = param.getrList().get(m);
					rRecord.put("ruleId",ruleId );
					rRecord.put("lowerLimit", sr.getLowerLimit());
					rRecord.put("upperLimit",sr.getUpperLimit() );
					rRecord.put("unit",sr.getUnit());
					rRecord.put("subUnit",sr.getSubUnit());
					rRecord.put("carType",0);
					rRecord.put("truck","");
					rRecord.put("subAmount",sr.getSubAmount() );
					
					rRecords[m]=rRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", rRecords);
				break;
			case 5:
				//门岗目测审查
				if(null==param.getmList())break;
				Map<String,Object>[] mRecords = new HashMap[param.getmList().size()];
				SubRangePayRuleEntity srm=null;
				for(int m=0;m<param.getmList().size();m++){
					Map<String,Object> mRecord = new HashMap<String,Object>();
					srm = param.getmList().get(m);
					mRecord.put("ruleId",ruleId );
					mRecord.put("lowerLimit", "");
					mRecord.put("upperLimit","");
					mRecord.put("unit",srm.getUnit());
					mRecord.put("subUnit",srm.getSubUnit());
					mRecord.put("carType",srm.getCarType());
					mRecord.put("truck",srm.getTruck());
					mRecord.put("subAmount",srm.getSubAmount() );
					
					mRecords[m]=mRecord;
				}
				baseDao.batchUpdate("subPayRule.addSubPayRuleRange", mRecords);
				break;
			default:;	
			
			}
			return result;
			
//		}catch(Exception e){
//			e.printStackTrace();
//			return 0;
//		}

	}	

	@Override
	public SubPayRuleDTO getRuleInfo(int ruleId)  throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ruleId", ruleId);
		SubPayRuleDTO dto = (SubPayRuleDTO)baseDao.queryForObject("subPayRule.queryRuleById", map, SubPayRuleDTO.class);
		int type = Integer.parseInt(dto.getSubType());
		if(type<3)return dto;
		List<SubRangePayRuleDTO> list = baseDao.queryForList("subPayRule.queryRangByRuleId", map, SubRangePayRuleDTO.class);
		dto.setRanges(list);
		return dto;
	}

	@Override
	public int getRuleTotal(Map<String,Object> map)   throws Exception{
		if(StringUtils.isNotBlank(map.get("search")==null?"":map.get("search").toString())){
			return (Integer)baseDao.queryForObject("subPayRule.getSearchTotal", map, Integer.class);
		}else{
			return (Integer)baseDao.queryForObject("subPayRule.getTotal", map, Integer.class);
		}
	}

	@Override
	public List<SubPayRuleDTO> getRuleList(Map<String, Object> map)   throws Exception{
		String categroy=map.get("categroy")==null?"":map.get("categroy").toString();
		String search = map.get("search")==null?"":map.get("search").toString();
		List<SubPayRuleDTO> list = null;
		if(StringUtils.isNotBlank(categroy)&&StringUtils.isNotBlank(search)){
			list = baseDao.queryForList("subPayRule.getSearchRuleList", map, SubPayRuleDTO.class);
		}else{
			list = baseDao.queryForList("subPayRule.getRuleList", map, SubPayRuleDTO.class);
		}
		if(list==null){
			return null;
		}
		for(SubPayRuleDTO dto : list){
				if(Integer.parseInt(dto.getSubType())>2)
				{
					map.put("ruleId", dto.getRuleId());
					List<SubRangePayRuleDTO> templist = baseDao.queryForList("subPayRule.queryRangByRuleId", map, SubRangePayRuleDTO.class);
					dto.setRanges(templist);
				}
			}
		return list;
	}

	@Override
	public int updateRuleStatus(Map<String, Object> map)  throws Exception {
//		map.put("ruleId", ruleId);
//		map.put("status", status);
		int ruleId = map.get("ruleId")==null?0:Integer.parseInt(map.get("ruleId").toString());
		int status = map.get("status")==null?0:Integer.parseInt(map.get("status").toString());
		if(ruleId==0)
			return 0;
		
		if(status==1){//如果要启用一条规则时，先判断当前是否到了他的活动开始时间，如果没有就设为排队状态
			SubPayRuleDTO dto = (SubPayRuleDTO)baseDao.queryForObject("subPayRule.queryRuleById", map, SubPayRuleDTO.class);
			if(dto.getTimeStart().getTime()>System.currentTimeMillis()){//活动时间大于当前时间
				map.put("status", 3);
			}
			//如果要启用一条规则时，先判断结束时间是否小于当前时间，如果是就设置为过期
			if(dto.getTimeEnd().getTime()<System.currentTimeMillis()){//活动时间大于当前时间
				map.put("status", 2);
			}
		}
		
		return baseDao.execute("subPayRule.updateRuleStatus", map);
	}

	@Override
	public CategoryTreeDTO validateCate(Set<CategoryTreeDTO> list,Map<String,Object> map)
			throws Exception {
		CategoryTreeDTO tempDto = null;
		if(null==list){
			return tempDto;
		}
		List<CategoryTreeDTO> tlist = null;
		Iterator<CategoryTreeDTO> it = list.iterator();
		while(it.hasNext()){
			CategoryTreeDTO dto =	it.next();
			if(dto.getCurLevel()<2)continue;
			map.put("cateId", dto.getCategoryId());
			tlist = baseDao.queryForList("subPayRule.vailDateCate", map,CategoryTreeDTO.class);
			if(tlist!=null&&tlist.size()>0){
				tempDto = tlist.get(0);
				break;
			}
		}
		return tempDto;
	}

	@Override
	public SubPayRuleDTO getRuleInfo(Map<String, Object> map) throws Exception {
		SubPayRuleDTO dto = (SubPayRuleDTO)baseDao.queryForObject("subPayRule.queryRuleInfo", map, SubPayRuleDTO.class);
		return dto;
	}

	@Override
	public SubPayRuleDTO querySubPayRuleByGoods(SubPayRuleDTO subPayRule) throws ServiceException {
		return (SubPayRuleDTO) baseDao.queryForObject("subPayRule.querySubPayRuleByGoods", subPayRule, SubPayRuleDTO.class);
	}

	@Override
	public List<SubPayRuleDTO> queryGoodsSubPayRuleByMap(Map<String, Object> map) throws ServiceException {
		return (List<SubPayRuleDTO>) baseDao.queryForList("subPayRule.queryGoodsSubPayRuleByMap", map, SubPayRuleDTO.class);
	}

	@Override
	public int startRule() throws Exception{
		baseDao.execute("subPayRule.endRule", null);
		return baseDao.execute("subPayRule.startRule", null);
	}

}
