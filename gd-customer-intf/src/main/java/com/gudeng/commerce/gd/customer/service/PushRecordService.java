package com.gudeng.commerce.gd.customer.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;

public interface PushRecordService {

//	public void add(PushRecordDTO pushRecordDTO) throws MalformedURLException ;
//	保存方法不能为void，要么为当前插入的id，要么为一个标识，才能判断新增是否成功
	public Long add(PushRecordEntity pushRecordEntity) throws MalformedURLException ;

	public int delete(Long id) throws MalformedURLException ;
	public List<PushRecordDTO> getList(Long memberId) throws MalformedURLException;
	public List<PushRecordDTO>  getList( ) throws MalformedURLException ;
	public List<PushRecordDTO>  getList(Integer state ) throws MalformedURLException ;
//	public Integer batchDel(String ids) ;
	public int update(PushRecordDTO pushRecordDTO) ;
	public Integer  getCount(Integer state) ;
	
	public int getTotal(Map map)throws Exception;
	public List<PushRecordDTO> getList(Map mapParam) throws MalformedURLException;
	public List<PushRecordDTO> getBySearch(Map map) throws Exception;
	public List<PushRecordDTO> getList(Long memberId,Integer state) throws MalformedURLException ;
	public Integer getCount(Integer state,Long memberId);
}
