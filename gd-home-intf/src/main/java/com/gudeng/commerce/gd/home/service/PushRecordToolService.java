package com.gudeng.commerce.gd.home.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;

public interface PushRecordToolService {

	public void add(PushRecordEntity pushRecordEntity) throws MalformedURLException ;
	public void delete(Long id) throws MalformedURLException ;
	public List getList( ) throws MalformedURLException ;
	public List getList( Long memberId) throws MalformedURLException ;
	public List getList( Long memberId,Integer state) throws MalformedURLException ;
	public void batchDel(String ids) throws MalformedURLException ;
	public List<PushRecordDTO> getList(Map mapParam) throws MalformedURLException;
	public void update(PushRecordDTO pushRecordDTO) throws MalformedURLException ;

	
	public Integer getCount (Integer isRead) throws MalformedURLException;
	public Integer getCount (Integer isRead, Long memberId) throws MalformedURLException;

}
