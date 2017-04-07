package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromFeeDTO;
import com.gudeng.commerce.gd.promotion.dto.PromRuleDTO;
import com.gudeng.commerce.gd.promotion.entity.PromFeeEntity;
import com.gudeng.commerce.gd.promotion.entity.PromRuleEntity;
import com.gudeng.commerce.gd.promotion.service.PromRuleFeeService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

public class PromRuleFeeServiceImpl implements PromRuleFeeService{
	
	@Resource
	private BaseDao<?> baseDao;


	@Override
	@Transactional
	public void savePormRuleFee(PromBaseinfoDTO dto) {
		//查看是否存在规则
		int count = baseDao.queryForObject("PromRuleFee.getPromRuleTotalByActId", dto, Integer.class);
		if(count>0){
			//修改规则
			updatePromRule(dto);
		} else {
			//新增
			addPromRule(dto);
		}
		//保存手续费
		baseDao.execute("PromRuleFee.deletePromFee", dto);
		addPromFee(dto);
	}
	
	private Integer addPromRule(PromBaseinfoDTO dto){
		PromRuleEntity pre = new PromRuleEntity();
		PromRuleDTO prd = dto.getPromRule();
		prd.setActId(dto.getActId());
		prd.setCreateTime(dto.getCreateTime());
		prd.setCreateUserId(dto.getCreateUserId());
		prd.setUpdateTime(dto.getUpdateTime());
		prd.setUpdateUserId(dto.getUpdateUserId());
		BeanUtils.copyProperties(prd, pre);
		long ruleId = baseDao.persist(pre, Long.class);
		return (int)ruleId;
	}
	
	private void addPromFee(PromBaseinfoDTO dto){
		List<PromFeeDTO> list = dto.getPromFees();
		//feeSource 1 农批商 2供应商
		if(null != list){
			for(PromFeeDTO fee : list){
				fee.setActId(dto.getActId());
				fee.setCreateTime(dto.getCreateTime());
				fee.setCreateUserId(dto.getCreateUserId());
				fee.setUpdateTime(dto.getUpdateTime());
				fee.setUpdateUserId(dto.getUpdateUserId());
				PromFeeEntity pf = new PromFeeEntity();
				BeanUtils.copyProperties(fee, pf);
				baseDao.persist(fee, Long.class);
			}
		}
	}
	
	private void updatePromRule(PromBaseinfoDTO dto){
		PromRuleDTO prd = dto.getPromRule();
		Map<String,Object> map = DalUtils.convertToMap(prd);
		map.put("actId", dto.getActId());
		map.put("updateUserId",dto.getUpdateUserId());
		baseDao.execute("PromRuleFee.updatePromRule", map);
	}

	@Override
	public PromBaseinfoDTO queryPromRuleFee(Map<String, Object> map) {
		PromRuleDTO pr = baseDao.queryForObject("PromRuleFee.queryPromRule", map, PromRuleDTO.class);
		List<PromFeeDTO> plsit = baseDao.queryForList("PromRuleFee.queryPromFee", map, PromFeeDTO.class);
		PromBaseinfoDTO base = new PromBaseinfoDTO();
		base.setPromRule(pr);
		base.setPromFees(plsit);
		return base;
	}

}
