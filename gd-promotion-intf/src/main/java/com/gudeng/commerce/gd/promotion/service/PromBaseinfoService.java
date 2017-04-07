package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.entity.PromBaseinfoEntity;

/**
 * 活动基础服务
 * @author sss
 *
 */
public interface PromBaseinfoService {
	
	
	
	 /**
	  * 获取促销活动列表总数
	  * @param map
	  * @return
	  */
	 int getTotal(Map<String, Object> map);
	 /**
	  * 获取单个促销活动图片
	  * @param promBaseinfoId
	  * @return
	  */
	 List<PictureRefDTO> getPictures(int promBaseinfoId);
	 /**
	  * 获取单个促销活动详细信息
	  * @param map
	  * @return
	  */
	 PromBaseinfoDTO getDetail(Map<String, Object> map);

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<PromBaseinfoDTO> queryPageByCondition(Map<String, Object> map);
	
	/**
	 * 获取总条数
	 * @param map
	 * @return
	 */
	Integer getTotalCountByCondition(Map<String, Object> map);
	
	/**
	 * 更新活动基本信息
	 */
	void updatePromBaseinfo(PromBaseinfoEntity entity);
	
	/**
	 * 查询活动所属市场
	 * @param actId
	 * @return
	 */
	List<PromMarketDTO> queryPromMarketByActId(String actId);
	
	/**
	 * 保存活动基础信息,返回主键
	 * @param dto
	 */
	Integer savePromBaseinfo(PromBaseinfoDTO dto);
	
	
	/**
	 * 促销活动首页活动查询或供应商已参加的活动
	 * @param map
	 * @return
	 */
	List<PromBaseinfoDTO> queryPromoteActivitys(Map<String, Object> map);
	
}
