package com.gudeng.commerce.info.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;

public interface ReportsToolService {


    /**
     * @Description 根据用户ID查出该用户所拥有的所有报表权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    public List<ReportsDTO> getListByUserId(Map<String, Object> map) throws Exception;
	/**
	 * 获取当前用户可查看的图表信息
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public List<ReportsDTO>  getReportsList(Map<String, Object> parm)  throws Exception;
	
	/**
	 * 获取交易流水图表详细内容
	 * @param parm
	 * @return
	 */
	public List<ProBszbankDTO> getProBszbankList(Map<String, Object> parm)  throws Exception;
	
	/**
	 * 获取百度统计图表详细内容
	 * @param parm
	 * @return
	 */
	public List<ProBaiduEntityDTO> getbaiduList(Map<String, Object> parm)  throws Exception;

	/**
	 * 获取运营图表详细内容
	 * @param parm
	 * @return
	 */
	public List<ProOperateDTO> getOperateList(Map<String, Object> parm)  throws Exception;
}
