package com.gudeng.commerce.gd.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.InvoiceInfoDTO;
import com.gudeng.commerce.gd.order.service.InvoiceInfoService;
@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 查询对象
	 * 
	 * @return InvoiceInfoDTO
	 * 
	 */
	public InvoiceInfoDTO getBySearch(Map map) throws Exception{
		List<InvoiceInfoDTO> invoiceInfoList = baseDao.queryForList("InvoiceInfo.getBySearch", map ,InvoiceInfoDTO.class);
		if(invoiceInfoList != null && invoiceInfoList.size() > 0){
			return invoiceInfoList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 插入
	 * @return
	 * @throws Exception
	 */
	public int insertInvoiceInfo(Map map) throws Exception{
		return (int) baseDao.execute("InvoiceInfo.insert", map);
	}

}
