package com.gudeng.commerce.gd.report.data;

import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:37:47
 */
public interface DataBuilder {
	
	/**
	 * 具体数据拼接方法
	 * 用于将数据库数据组装成DataDTO
	 * @param dataQuery 查询时间范围
	 * @return
	 * @throws ServiceException
	 */
	public DataDTO getData(DataDBQuery dataQuery) throws ServiceException;

}
