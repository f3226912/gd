package com.gudeng.commerce.gd.admin.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;

public interface PushRecToolService {


//	public void add(PushRecordDTO pushRecordDTO) throws MalformedURLException ;
//	保存方法不能为void，要么为当前插入的id，要么为一个标识，才能判断新增是否成功
	public Long add(PushRecordEntity pushRecordEntity) throws Exception ;

	public int delete(Long id) throws Exception ;
	public List<PushRecordDTO>  getList( ) throws Exception ;
	public List<PushRecordDTO>  getList(Integer state ) throws Exception ;
//	public Integer batchDel(String ids) ;
	public int update(PushRecordDTO pushRecordDTO) throws Exception ;
	public Integer  getCount(Integer state)  throws Exception ;
	
	public int getTotal(Map map)throws Exception;
	
	public List<PushRecordDTO> getBySearch(Map map) throws Exception;
	
	
}
