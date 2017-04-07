package com.gudeng.commerce.info.admin.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDetailDTO;
import com.gudeng.commerce.info.customer.util.ReportsUtil;

/**
 * 农批网注册用户数据分析
 * @author wind
 *
 */
@Component
public class ComRegCountReportCommand extends ReportBaseCommand{

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
			List<ProOperateDTO> proOperateDtoList = proOperateToolService.sumReport(paramMap);

			// 将预报表数据列表保存map中，key为时间，value为dto
			Map<String, ProOperateDTO> dateDtoMap = new HashMap<String, ProOperateDTO>();
			sf = new SimpleDateFormat(format);
			for(ProOperateDTO dto : proOperateDtoList){
				dateDtoMap.put(sf.format(dto.getDatatimes()), dto);
			}
			
			List<Number> comRegCountDataList = new ArrayList<Number>();
			List<Number> growthRateDataList = new ArrayList<Number>();
			for(String dateStr : dateStrList){
				ProOperateDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					comRegCountDataList.add(0);
					growthRateDataList.add(0);
				}else{
					comRegCountDataList.add(dto.getComRegCount());
					growthRateDataList.add(dto.getGrowthRate());
				}
			}
			
			//当日网站注册用户数
			ReportDataDetailDTO comRegCountDataDetailDTO = new ReportDataDetailDTO();
			comRegCountDataDetailDTO.setData(comRegCountDataList);
			comRegCountDataDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			comRegCountDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			comRegCountDataDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			comRegCountDataDetailDTO.setParms("当日平台注册用户数");
			
			//环比增长率
			ReportDataDetailDTO growthRateDataDetailDTO = new ReportDataDetailDTO();
			growthRateDataDetailDTO.setData(growthRateDataList);
			growthRateDataDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			growthRateDataDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			growthRateDataDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			growthRateDataDetailDTO.setParms("环比增长率%");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", comRegCountDataDetailDTO);
			dataMap.put("data2", growthRateDataDetailDTO);
			
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
