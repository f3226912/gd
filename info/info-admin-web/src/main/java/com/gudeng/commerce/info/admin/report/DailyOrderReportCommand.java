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

/***
 * 每日订单交易情况
 * @author wind
 *
 */
@Component
public class DailyOrderReportCommand extends ReportBaseCommand{

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
			
			//当日交易总金额
			List<Number> totalAmountDataList = new ArrayList<Number>();
			//平均单价
			List<Number> avgAmountDataList = new ArrayList<Number>();
			//当日总订单数
			List<Number> totalNumDataList = new ArrayList<Number>();
			
			// 将预报表数据列表保存map中，key为时间，value为dto
			Map<String, ProBszbankDTO> dateDtoMap = new HashMap<String, ProBszbankDTO>();
			sf = new SimpleDateFormat(format);
			for(ProBszbankDTO dto : proBszbankDTOList){
				dateDtoMap.put(sf.format(dto.getDatatimes()), dto);
			}
			
			// 循环参数时间字符串list，如果map中没有则数据设置默认值为0，否则获取dto对应的数据
			for(String dateStr : dateStrList){
				ProBszbankDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					totalAmountDataList.add(0);
					avgAmountDataList.add(0);
					totalNumDataList.add(0);
				}else{
					totalAmountDataList.add(dto.getTransactionAmount());
					avgAmountDataList.add(dto.getOrderAmount());
					totalNumDataList.add(dto.getOrderNumbers());
				}
			}
			
			//当日交易总金额
			ReportDataDetailDTO totalAmountDataDetailDTO = new ReportDataDetailDTO();
			totalAmountDataDetailDTO.setData(totalAmountDataList);
			totalAmountDataDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			totalAmountDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_MONEY.value());
			totalAmountDataDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			totalAmountDataDetailDTO.setParms("当日交易总金额（元）");
			
			//平均单价
			ReportDataDetailDTO avgAmountDataDetailDTO = new ReportDataDetailDTO();
			avgAmountDataDetailDTO.setData(avgAmountDataList);
			avgAmountDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			avgAmountDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_MONEY.value());
			avgAmountDataDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			avgAmountDataDetailDTO.setParms("平均单价（元/单）");
			
			//当日总订单数
			ReportDataDetailDTO totalNumDataDetailDTO = new ReportDataDetailDTO();
			totalNumDataDetailDTO.setData(totalNumDataList);
			totalNumDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			totalNumDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			totalNumDataDetailDTO.setColor(ReportsUtil.Color.GREEN.value());
			totalNumDataDetailDTO.setParms("当日总订单数（单）");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", totalAmountDataDetailDTO);
			dataMap.put("data2", avgAmountDataDetailDTO);
			dataMap.put("data3", totalNumDataDetailDTO);
			
			// 报表数据需要以数组的格式返回:"data":[{"data1":{"color":"#123123","data":[0,200000,0,200000,200000],"type":"1","typeY":"1","where":"1"}}]
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
