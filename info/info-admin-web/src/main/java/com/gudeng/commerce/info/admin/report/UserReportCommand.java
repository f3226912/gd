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

@Component
public class UserReportCommand extends ReportBaseCommand{

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
			List<Number> newUserDataList = new ArrayList<Number>();
			List<Number> oldUserDataList = new ArrayList<Number>();
			for (String dateStr : dateStrList) {
				ProBszbankDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					newUserDataList.add(0);
					oldUserDataList.add(0);
				} else {
					newUserDataList.add(dto.getNewUsers());
					oldUserDataList.add(dto.getOldUsers());
				}
			}
			
			//新用户
			ReportDataDetailDTO newUserDataDetailDTO = new ReportDataDetailDTO();
			newUserDataDetailDTO.setData(newUserDataList);
			newUserDataDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			newUserDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			newUserDataDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			newUserDataDetailDTO.setParms("新用户");
			
			//老用户
			ReportDataDetailDTO oldUserDataDetailDTO = new ReportDataDetailDTO();
			oldUserDataDetailDTO.setData(oldUserDataList);
			oldUserDataDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			oldUserDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			oldUserDataDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			oldUserDataDetailDTO.setParms("老用户");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", newUserDataDetailDTO);
			dataMap.put("data2", oldUserDataDetailDTO);
			
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
