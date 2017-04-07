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
 * 农商友注册用户数据分析
 * @author wind
 *
 */
@Component
public class NsyUserRegReportCommand extends ReportBaseCommand{

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
			
			//填充日期列表中每日数据
			List<Number> nsyRegCountDataList = new ArrayList<Number>();
			List<Number> growthRateDataList = new ArrayList<Number>();
			for(String dateStr : dateStrList){
				ProOperateDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					nsyRegCountDataList.add(0);
					growthRateDataList.add(0);
				}else{
					nsyRegCountDataList.add(dto.getNsyRegCount());
					growthRateDataList.add(dto.getGrowthRate());
				}
			}

			//农商友当日注册用户数
			ReportDataDetailDTO nsyRegCountDetailDTO = new ReportDataDetailDTO();
			nsyRegCountDetailDTO.setData(nsyRegCountDataList);
			nsyRegCountDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			nsyRegCountDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			nsyRegCountDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			nsyRegCountDetailDTO.setParms("农商友当日注册用户数");
			
			//环比增长率
			ReportDataDetailDTO growthRateDetailDTO = new ReportDataDetailDTO();
			growthRateDetailDTO.setData(growthRateDataList);
			growthRateDetailDTO.setType(ReportsUtil.Type.TYPE_1.value());
			growthRateDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			growthRateDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			growthRateDetailDTO.setParms("日注册数量环比增长率%");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", nsyRegCountDetailDTO);
			dataMap.put("data2", growthRateDetailDTO);
			
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
