package com.gudeng.commerce.gd.admin.service.active.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.active.PromBaseinfoToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.service.PromBaseinfoService;

public class PromBaseinfoToolServiceImpl implements PromBaseinfoToolService{

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PromBaseinfoService promBaseinfoService;

	private PromBaseinfoService hessianPromBaseinfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getPromBaseinfoServiceUrl();
		if(promBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promBaseinfoService = (PromBaseinfoService) factory.create(PromBaseinfoService.class, hessianUrl);
		}
		return promBaseinfoService;
	}
	
	@Override
	public List<PromBaseinfoDTO> queryPageByCondition(Map<String, Object> params) throws Exception {
		List<PromBaseinfoDTO> dtoList = hessianPromBaseinfoService().queryPageByCondition(params);
		if(dtoList != null){
			for(PromBaseinfoDTO dto : dtoList){
				//设置所属市场
				List<PromMarketDTO> mlist = promBaseinfoService.queryPromMarketByActId(dto.getActId()+"");
				if(null != mlist){
					StringBuilder sb = new StringBuilder();
					for(PromMarketDTO pm : mlist){
						sb.append(pm.getMarketName());
						sb.append(",");
					}
					if(sb.length()>0){
						dto.setMarketNames(sb.substring(0, sb.length()-1));
					}

				}
			}
		}
		return dtoList;
	}

	@Override
	public Integer getTotalCountByCondition(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return hessianPromBaseinfoService().getTotalCountByCondition(map);
	}

	@Override
	public Integer savePromBaseinfo(PromBaseinfoDTO dto) throws Exception {
		if(null != dto.getActId()){
			if(validatePromStart(dto.getActId())){
				throw new RuntimeException("活动["+dto.getActId()+"]已开始，不能修改");
			}
		}
		if(dto.getMarketList() == null || dto.getMarketList().size() == 0){
			throw new RuntimeException("市场不能为空");
		}
		//校验该市场下，是否存在该活动名称
		Map<String,Object> map = new HashMap<>();
		map.put("name2", dto.getName());
		Integer marketId = dto.getMarketList().get(0).getMarketId();
		map.put("marketId", marketId);//目前只有一个市场
		map.put("notActId", dto.getActId());
		int count = hessianPromBaseinfoService().getTotalCountByCondition(map);
		//新增与修改需要区别对待 存在活动ID就是修改
		if(count > 0){
			throw new RuntimeException("所选市场已存在活动 ["+dto.getName()+"]");
		} 

		
		return hessianPromBaseinfoService().savePromBaseinfo(dto);
	}

	@Override
	public PromBaseinfoDTO queryPromBaseinfoById(Integer actId) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("actId", actId);
		params.put("startRow", 0);
		params.put("endRow", 1);
		List<PromBaseinfoDTO> dtoList = hessianPromBaseinfoService().queryPageByCondition(params);
		if(null == dtoList){
			throw new RuntimeException("活动Id["+actId+"]不存在");
		}
		PromBaseinfoDTO dto = dtoList.get(0);
		List<PromMarketDTO> mlist = promBaseinfoService.queryPromMarketByActId(actId+"");
		dto.setMarketList(mlist);
		//设置图片
		List<PictureRefDTO> plist =  promBaseinfoService.getPictures(actId);
		dto.setPictureRefList(plist);
		
		return dto;
	}

	@Override
	public boolean validatePromStart(Integer actId) throws Exception {
		PromBaseinfoDTO dto = queryPromBaseinfoById(actId);
		Date current = new Date();
		if(null == dto.getStartTime()){
			throw new RuntimeException("系统错误，活动["+actId+"]开始时间为空");
		}
		if(current.after(dto.getStartTime())){
			return true;
		}
		return false;
	}

}
