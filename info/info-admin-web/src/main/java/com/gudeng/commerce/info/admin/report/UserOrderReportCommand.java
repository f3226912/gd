package com.gudeng.commerce.info.admin.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDetailDTO;
import com.gudeng.commerce.info.customer.util.ReportsUtil;

/**
 * 订单交易用户成交量走势
 * @author wind
 *
 */
@Component
public class UserOrderReportCommand extends ReportBaseCommand{

	@Override
	public ReportDataDTO generateReportData(Map<String, Object> paramMap) {
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String startDateStr = (String) paramMap.get(PARAM_QUERY_START_DATE);
			String endDateStr = (String) paramMap.get(PARAM_QUERY_END_DATE);
			Date startDate = sf.parse(startDateStr);
			Date endDate = sf.parse(endDateStr);

			String format = "MMdd";
			// 时间参数范围内的时间字符串列表
			List<String> dateStrList = getDateStrList(startDate, endDate,format);
			
			// 预报表数据列表
			Long marketId = (Long) paramMap.get(PARAM_MARKET_ID);
			List<ProBszbankDTO> proBszbankDTOList = new ArrayList<ProBszbankDTO>();
			if(marketId == null || "1".equals(marketId.toString())){
				proBszbankDTOList = proBszbankToolService.sumReport(paramMap);
			}
			
			// 将预报表数据列表保存map中，key为时间，value为dto
			Map<String, ProBszbankDTO> dateDtoMap = new HashMap<String, ProBszbankDTO>();
			sf = new SimpleDateFormat(format);
			for(ProBszbankDTO dto : proBszbankDTOList){
				dateDtoMap.put(sf.format(dto.getDatatimes()), dto);
			}
			
			// 循环参数时间字符串list，如果map中没有则数据设置默认值为0，否则获取dto对应的数据
			List<Number> orderNumDataList = new ArrayList<Number>();
			List<Number> userNumDataList = new ArrayList<Number>();
			List<Number> avgNumDataList = new ArrayList<Number>();
			for (String dateStr : dateStrList) {
				ProBszbankDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					orderNumDataList.add(0);
					userNumDataList.add(0);
					avgNumDataList.add(0);
				} else {
					orderNumDataList.add(dto.getOrderNumbers());
					userNumDataList.add(dto.getTransactionUsers());
					avgNumDataList.add(dto.getUserTradeAvg());
				}
			}
			
			//当日订单数
			ReportDataDetailDTO orderNumDataDetailDTO = new ReportDataDetailDTO();
			orderNumDataDetailDTO.setData(orderNumDataList);
			orderNumDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			orderNumDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			orderNumDataDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			orderNumDataDetailDTO.setParms("当日订单数");
			
			//当日交易用户数量
			ReportDataDetailDTO userNumDataDetailDTO = new ReportDataDetailDTO();
			userNumDataDetailDTO.setData(userNumDataList);
			userNumDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			userNumDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			userNumDataDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			userNumDataDetailDTO.setParms("当日交易用户数量");
			
			//当日用户平均成交量
			ReportDataDetailDTO avgNumDataDetailDTO = new ReportDataDetailDTO();
			avgNumDataDetailDTO.setData(avgNumDataList);
			avgNumDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			avgNumDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			avgNumDataDetailDTO.setColor(ReportsUtil.Color.GREEN.value());
			avgNumDataDetailDTO.setParms("当日用户平均成交量");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", orderNumDataDetailDTO);
			dataMap.put("data2", userNumDataDetailDTO);
			dataMap.put("data3", avgNumDataDetailDTO);
			
			List<Map<String, ReportDataDetailDTO>> data = new ArrayList<Map<String, ReportDataDetailDTO>>();
			data.add(dataMap);
			
			ReportDataDTO reportDataDTO = new ReportDataDTO();
			reportDataDTO.setParms(dateStrList);
			reportDataDTO.setData(data);
			return reportDataDTO;
		}
		catch(Exception e){
			return null;
		}
	}

}
