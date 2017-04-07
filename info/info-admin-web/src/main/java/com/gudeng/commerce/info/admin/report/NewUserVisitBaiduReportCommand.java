package com.gudeng.commerce.info.admin.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDTO;
import com.gudeng.commerce.info.customer.dto.ReportDataDetailDTO;
import com.gudeng.commerce.info.customer.util.ReportsUtil;

/**
 * 新增用户访问统计
 * @author wind
 *
 */
@Component
public class NewUserVisitBaiduReportCommand extends ReportBaseCommand{

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
			List<ProBaiduEntityDTO> proBaiduDTOList = proBaiduToolService.sumReport(paramMap);;
			
			// 将预报表数据列表保存map中，key为时间，value为dto
			Map<String, ProBaiduEntityDTO> dateDtoMap = new HashMap<String, ProBaiduEntityDTO>();
			sf = new SimpleDateFormat(format);
			for (ProBaiduEntityDTO dto : proBaiduDTOList) {
				dateDtoMap.put(sf.format(dto.getDatatimes()), dto);
			}
			// 循环参数时间字符串list，如果map中没有则数据设置默认值为0，否则获取dto对应的数据
			List<Number> uvDataList = new ArrayList<Number>();
			List<Number> newUserDataList = new ArrayList<Number>();
			for (String dateStr : dateStrList) {
				ProBaiduEntityDTO dto = dateDtoMap.get(dateStr);
				if (dto == null) {
					uvDataList.add(0);
					newUserDataList.add(0);
				} else {
					uvDataList.add(dto.getUVcount());
					newUserDataList.add(dto.getNewuser());
				}
			}

			ReportDataDetailDTO uvDetailDTO = new ReportDataDetailDTO();
			uvDetailDTO.setData(uvDataList);
			uvDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			uvDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			uvDetailDTO.setColor(ReportsUtil.Color.BLUE.value());
			uvDetailDTO.setParms("(UV)访客数");
			
			ReportDataDetailDTO newUserDetailDTO = new ReportDataDetailDTO();
			newUserDetailDTO.setData(newUserDataList);
			newUserDetailDTO.setType(ReportsUtil.Type.TYPE_2.value());
			newUserDetailDTO.setTypeY(ReportsUtil.TypeY.TYPEY_NUMBER.value());
			newUserDetailDTO.setColor(ReportsUtil.Color.GARNET.value());
			newUserDetailDTO.setParms("新增用户数");
			
			Map<String, ReportDataDetailDTO> dataMap = new HashMap<String, ReportDataDetailDTO>();
			dataMap.put("data1", uvDetailDTO);
			dataMap.put("data2", newUserDetailDTO);
			
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
