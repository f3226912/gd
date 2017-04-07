package com.gudeng.commerce.info.admin.report;

import java.math.BigDecimal;
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
 * 日交易用户数
 * @author wind
 *
 */
@Component
public class TransactionUserReportCommand extends ReportBaseCommand{

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
			List<Number> dataList = new ArrayList<Number>();
			for (String dateStr : dateStrList) {
				ProBszbankDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					dataList.add(0);
				} else {
					dataList.add(dto.getTransactionUsers());
				}
			}
			
			ReportDataDetailDTO dataDetailDTO = new ReportDataDetailDTO();
			dataDetailDTO.setData(dataList);
			dataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			dataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			dataDetailDTO.setColor(ReportsUtil.Color.PURPLE.value());
			dataDetailDTO.setParms("日交易用户数");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", dataDetailDTO);
			
			List<Map<String, ReportDataDetailDTO>> data = new ArrayList<Map<String, ReportDataDetailDTO>>();
			data.add(dataMap);
			
			//统计总数
			BigDecimal sum = new BigDecimal(0);
			for(Number num : dataList){
				sum = sum.add(new BigDecimal(num.doubleValue()));
			}
			
			ReportDataDTO reportDataDTO = new ReportDataDTO();
			reportDataDTO.setParms(dateStrList);
			reportDataDTO.setData(data);
			reportDataDTO.setSum(sum.doubleValue());
			
			return reportDataDTO;
		}
		catch(Exception e){
			return null;
		}
	}

}
