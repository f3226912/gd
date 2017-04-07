package com.gudeng.commerce.gd.report.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;
import com.gudeng.commerce.gd.report.dto.UserGoodsDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.GoodsService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:39:56
 */
public class GoodsDataBuilder implements DataBuilder {

	@Autowired
	private GoodsService goodsService;

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	@Override
	public UserGoodsDataDTO getData(DataDBQuery dataQuery) throws ServiceException {

		// 转换查询参数 MAP
		Map<String, Object> params = new HashMap<>();
		UserGoodsDataDTO dataDTO = new UserGoodsDataDTO();

		params.put("startTime", dataQuery.getStartTime());
		params.put("endTime", dataQuery.getEndTime());
		params.put("memberId", dataQuery.getMemberId());

		// 获得基础数据
		List<GoodsTradeResult> goodsTradeResults = goodsService.getGoodsTradeResult(params);

		/*
		 * 封装前端需要数据结构
		 */
		dataDTO.setGoodsTradeResultLists(goodsTradeResults);

		return dataDTO;
	}

}
