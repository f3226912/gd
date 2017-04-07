package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.FollowProductDTO;
import com.gudeng.commerce.gd.customer.dto.PushFilterDateDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.entity.PushProductContentEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.PushRecordService;
import com.gudeng.commerce.gd.customer.service.PushTaskService;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;


public class PushTaskServiceImpl implements PushTaskService {
	
	private static Logger logger = LoggerFactory.getLogger(PushTaskServiceImpl.class);

	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private PushRecordService pushRecordService;
	
	private UMengPushMessage uMengPushMessage= new UMengPushMessage();

	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public int addPushProductContent() {
		return (int) baseDao.execute("PushProductContent.addPushProductContent", Integer.class);
	}

	@Override
	public int deletePushProductContentByTime() {
		return (int) baseDao.execute("PushProductContent.deletePushProductContentByTime", null);
	}

	@Override
	public boolean isPushTime() {
		List<PushFilterDateDTO> list = baseDao.queryForList("PushFilterDate.getList",null, PushFilterDateDTO.class);
		if(null!=list&&list.size()>0)return false;
		return true;
	}

	@Override
	public void PushUsers(int daycn) {
		try {
			List<FollowProductDTO> list = baseDao.queryForList(
					"PushProductContent.getFollowUserList", null,
					FollowProductDTO.class);

			if (null == list || list.size() == 0)
				return;

			Map<String, String> extraMap = new HashMap<String, String>();
			GdMessageDTO gdMessage = new GdMessageDTO();
			gdMessage.setSendApp("1");
			gdMessage.setSendType("1");
			gdMessage.setTicket("【农商友告诉您市场价格行情动态】");
			gdMessage.setTitle("【农商友告诉您市场价格行情动态】");
			gdMessage.setProduction_mode(true);
			gdMessage.setContent("您关注的商品价格波动了，马上了解详情");
			gdMessage.setAfter_open("go_activity");
			gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
			extraMap.put("openmenu", "NSY_GZ");
			extraMap.put("sendType", "1");

			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("daycn", daycn);
			PushRecordEntity record = null;
			// TODO 此段业务代码可优化 semon 20151028
			for (FollowProductDTO fp : list) {
				//如果token为空的话不推送
				if(StringUtils.isBlank(fp.getDeviceTokens())){
					continue;
				}
				// 查询n天内是否推送过信息
				pmap.put("userId", fp.getUserId());
				List<PushProductDTO> plist = baseDao.queryForList(
						"Pushrecord.getListByTime", pmap, PushProductDTO.class);
				if (plist.size() > 0) {
					continue;
				}
				PushProductContentEntity ppc = new PushProductContentEntity();
				if(fp.getProductId()==0){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("cateId", fp.getCateId());
					ppc = baseDao.queryForObject("PushProductContent.getOneProduct", map, PushProductContentEntity.class);
					fp.setProductId(ppc.getProductId());
//					logger.debug("-----------------------------<<<>>>>>>"+fp.getProductId());
				}
				
				extraMap.put("productId", fp.getProductId() + "");
				gdMessage.setDevice_tokens(fp.getDeviceTokens());
				gdMessage.setExtraMap(extraMap);

				// 推送消息，记录推送消息
				record = new PushRecordEntity();
				record.setContent(gdMessage.getContent());
				record.setCreateTime(new Date());
				record.setCreateUserId("0");
				record.setMemberId(fp.getUserId());
				record.setReceiveType("2");
				record.setState("1");
				record.setTitle(gdMessage.getTitle());
				record.setType("1");
				record.setOrigin(0);
				record.setRedirectUrl(extraMap.get("openmenu"));
				pushRecordService.add(record);

				uMengPushMessage.pushMessage(gdMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("========================>定时推送消息失败");
		}

	}

	@Override
	public int[] addPushRecord(Map<String, Object>[] maps) {
		return baseDao.batchUpdate("Pushrecord.insertRecord", maps);
	}

	@Override
	public Map<String,Integer> processAd(int hour) {
		if(hour<=0)hour=3;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		//将启用广告更改为过期广告
		paramMap.put("hourcn", hour);
		int result1 = baseDao.execute("PushAdInfo.updataAdExpired", paramMap);
		//将停用广告更改为启用广告
		int result2 = baseDao.execute("PushAdInfo.updataAdStart", paramMap);
		resultMap.put("expiredAd", result1);
		resultMap.put("startAd", result2);
		return resultMap;
		
	}
	

	

}
