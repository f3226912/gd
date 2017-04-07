package com.gudeng.commerce.gd.report.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.report.dto.DataDTO;
import com.gudeng.commerce.gd.report.dto.DataServiceQuery;
import com.gudeng.commerce.gd.report.dto.GoodsServiceQuery;
import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;
import com.gudeng.commerce.gd.report.dto.UserGoodsDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.util.MathUtil;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午3:59:57
 */
public class GoodsDataServiceImpl extends ADataService {

	@Override
	public DataDTO consolidateDataDTO(List<DataDTO> list,DataServiceQuery dataQuery) throws ServiceException {
		UserGoodsDataDTO dataDTO = new UserGoodsDataDTO();
		
		/*
		 * 循环整合数据
		 */
		for (int i = 0; i < list.size(); i++) {
			UserGoodsDataDTO tempData = (UserGoodsDataDTO)list.get(i);
			if(dataDTO.getGoodsTradeResultLists()==null) {
				dataDTO = tempData;
			} else {
				dataDTO.getGoodsTradeResultLists().addAll(tempData.getGoodsTradeResultLists());
			}
		}
		
		//内部数据重复项处理相加
		dataDTO.setGoodsTradeResultLists(consolidateList(dataDTO.getGoodsTradeResultLists()));

		List<GoodsTradeResult> goodsTradeResults = dataDTO.getGoodsTradeResultLists();
		
		//转换条件 为排序
		GoodsServiceQuery goodsServiceQuery = (GoodsServiceQuery)dataQuery;
		
		/*
		 * 排序
		 */
		
		if(goodsTradeResults.size()!=0) {
			if ("pv:DESC".equals(goodsServiceQuery.getSortCode())) {
				Collections.sort(goodsTradeResults, new GoodsPVComparetor());
			} else if ("tradeAmt:ASC".equals(goodsServiceQuery.getSortCode())) {
				Collections.sort(goodsTradeResults, new GoodsTradeAmtComparetor());
			} else if ("pv:ASC".equals(goodsServiceQuery.getSortCode())) {
				Collections.sort(goodsTradeResults, Collections.reverseOrder(new GoodsPVComparetor()));
			} else {
				Collections.sort(goodsTradeResults, Collections.reverseOrder(new GoodsTradeAmtComparetor()));
			}
		}
		
		List<GoodsTradeResult> newlist = new ArrayList<>();

		/*
		 * 取前十个数据
		 */
		if(goodsTradeResults.size()>10) {
			for(int i = 0; i < 10; i++) {
				newlist.add(goodsTradeResults.get(i));
			}
			
			dataDTO.setGoodsTradeResultLists(newlist);
		}
		
		
		return dataDTO;

	}
	
	/**
	 * 整合相加集合中所有的项
	 * @param list
	 * @return
	 */
	public List<GoodsTradeResult> consolidateList(List<GoodsTradeResult> list) {
		if(list.size()<2)
			return list;
		
		Map<Long, GoodsTradeResult> maps = new HashMap<>();
		
		for (int i = 0; i < list.size(); i++) {
			GoodsTradeResult gtr = list.get(i);
			GoodsTradeResult gtrMap = maps.get(gtr.getProductId());
			if(gtrMap==null) {
				maps.put(gtr.getProductId(), gtr);
			} else {
				gtrMap.setOrderNum(gtrMap.getOrderNum()+gtr.getOrderNum());
				// pv没有按时间统计，不能叠加
				if (gtrMap.getPv() >= gtr.getPv()) {
					gtrMap.setPv(gtrMap.getPv());
				} else {
					gtrMap.setPv(gtr.getPv());
				}
				gtrMap.setSales(MathUtil.add(gtrMap.getSales(), gtr.getSales()));
				gtrMap.setTradeAmt(MathUtil.add(gtrMap.getTradeAmt(), gtr.getTradeAmt()));
			}
		}
		
		return new ArrayList<>(maps.values());
		
	}

}
