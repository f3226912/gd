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
 * 新老用户交易数据对比
 * @author wind
 *
 */
@Component
public class UserDealReportCommand extends ReportBaseCommand{

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
			List<Number> newUserAvgUnitPriceDataList = new ArrayList<Number>();
			List<Number> oldUserAvgUnitPriceDataList = new ArrayList<Number>();
			for (String dateStr : dateStrList) {
				ProBszbankDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					newUserDataList.add(0);
					oldUserDataList.add(0);
					newUserAvgUnitPriceDataList.add(0);
					oldUserAvgUnitPriceDataList.add(0);
				} else {
					newUserDataList.add(dto.getNewUsers());
					oldUserDataList.add(dto.getOldUsers());
					newUserAvgUnitPriceDataList.add(dto.getNewUserAvgUnitPrice());
					oldUserAvgUnitPriceDataList.add(dto.getOldUserAvgUnitPrice());
				}
			}
			
			//当日新增用户平均单价
			ReportDataDetailDTO newUserAvgDetailDTO = new ReportDataDetailDTO();
			newUserAvgDetailDTO.setData(newUserAvgUnitPriceDataList);
			newUserAvgDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			newUserAvgDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_MONEY.value());
			newUserAvgDetailDTO.setColor(ReportsUtil.Color.GREEN.value());
			newUserAvgDetailDTO.setParms("当日新增用户平均单价");
			
			//当日老用户平均单价
			ReportDataDetailDTO oldUserAvgDetailDTO = new ReportDataDetailDTO();
			oldUserAvgDetailDTO.setData(oldUserAvgUnitPriceDataList);
			oldUserAvgDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			oldUserAvgDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_MONEY.value());
			oldUserAvgDetailDTO.setColor(ReportsUtil.Color.ORANGE.value());
			oldUserAvgDetailDTO.setParms("当日老用户平均单价");
			
			//当日新增交易用户数量
			ReportDataDetailDTO newUsersDetailDTO = new ReportDataDetailDTO();
			newUsersDetailDTO.setData(newUserDataList);
			newUsersDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			newUsersDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			newUsersDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			newUsersDetailDTO.setParms("当日新增交易用户数量");
			
			//当日老交易用户数量
			ReportDataDetailDTO oldUsersDetailDTO = new ReportDataDetailDTO();
			oldUsersDetailDTO.setData(oldUserDataList);
			oldUsersDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			oldUsersDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			oldUsersDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			oldUsersDetailDTO.setParms("当日老用户数量");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", newUserAvgDetailDTO);
			dataMap.put("data2", oldUserAvgDetailDTO);
			dataMap.put("data3", newUsersDetailDTO);
			dataMap.put("data4", oldUsersDetailDTO);
			
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
