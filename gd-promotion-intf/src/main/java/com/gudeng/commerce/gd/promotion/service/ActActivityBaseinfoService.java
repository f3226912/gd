package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActStatisticDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;

public interface ActActivityBaseinfoService {

    Long add(ActActivityBaseinfoEntity entity);

    List<ActActivityBaseinfoDTO> queryPageByCondition(Map<String, Object> map);

    Integer getTotalCountByCondition(Map<String, Object> map);

    ActActivityBaseinfoDTO getById(Integer id);

    Long addActivity(ActActivityBaseinfoDTO dto);

    int delete(ActActivityBaseinfoDTO dto);

    /**
     * 更新活动浏览次数
     * 
     * @param map
     * @return
     * @throws Exception
     * @author lidong
     */
    int updatePV(Map<String, Object> map) throws Exception;

	int updateActivity(ActActivityBaseinfoDTO dto);

	int updateLaunch(ActActivityBaseinfoDTO dto);

	List<ActActivityBaseinfoDTO> queryListByCondition(Map<String, Object> map);
	
	/**
	 * 活动统计分页
	 * @param map
	 * @return
	 */
	List<ActActivityBaseinfoDTO> queryActivityStatisticPage(Map<String, Object> map);

	/**
	 * h5金牌会员营销活动统计总记录数
	 * @param map
	 * @return
	 * @throws Exception 
	 * @date 2016年11月22日18:50:07
	 * @author gxz
	 */
	Integer getStatisticTotalCount(Map<String, Object> map);
	/**
	 * h5金牌会员营销活动活动统计分页
	 * @param map
	 * @return
	 * @throws Exception 
	 * @date 2016年11月22日18:50:07
	 * @author gxz
	 */
	List<ActStatisticDto> queryStatisticPage(Map<String, Object> map) ;
	
	/**
	 * 活动统计总记录数
	 * @param map
	 * @return
	 */
	Integer getActivityStatisticTotalCount(Map<String, Object> map);

	List<ActActivityBaseinfoDTO> queryActStatisticListByCondition(Map<String, Object> map);
	List<ActStatisticDto> queryExportDate(Map<String, Object> map);
}
