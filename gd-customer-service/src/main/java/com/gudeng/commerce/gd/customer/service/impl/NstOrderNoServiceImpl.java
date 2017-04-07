package com.gudeng.commerce.gd.customer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.service.NstOrderNoService;

public class NstOrderNoServiceImpl implements NstOrderNoService{

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public String getNstOrderNo(String provinceAreaId) {
		Integer sequence = baseDao.queryForObject("NstSequence.getNstOrderNo", null, Integer.class);
		if(sequence == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(provinceAreaId.substring(0, 2)); //省份编号
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		sb.append(sf.format(new Date())); //年月日
		
		//四位的自增序列
		String seq = String.valueOf(sequence);
		for(int i = 0; i < 4 - seq.length(); i++){
			sb.append("0");
		}
		sb.append(seq);
		return sb.toString();
	}

}
