package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.ActivityToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;
import com.gudeng.commerce.gd.promotion.entity.ActGiftExchangeApplyEntity;
import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;
import com.gudeng.commerce.gd.promotion.service.ActActivityBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.ActActivityGiftExchangService;
import com.gudeng.commerce.gd.promotion.service.ActActivityScoreRecordService;
import com.gudeng.commerce.gd.promotion.service.ActGiftBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.ActReUserActivityService;
import com.gudeng.commerce.gd.promotion.service.ReActivityGiftService;

public class ActivityToolServiceImpl implements ActivityToolService {

    @Autowired
    public GdProperties gdProperties;

    protected static ActReUserActivityService actReUserActivityService;

    protected static ActActivityBaseinfoService actActivityBaseinfoService;

    protected static ActGiftBaseinfoService actGiftBaseinfoService;

    protected static ActActivityScoreRecordService actActivityScoreRecordService;
    
    protected static ActActivityGiftExchangService actActivityGiftExchangService;

    protected static ReActivityGiftService reActivityGiftService;

    protected ActGiftBaseinfoService getHessianActGiftBaseinfoService() throws MalformedURLException {

        if (actGiftBaseinfoService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actGiftBaseinfoService = (ActGiftBaseinfoService) factory.create(ActGiftBaseinfoService.class, gdProperties.getProperties().getProperty("gd.giftBaseInfo.url"));
        }
        return actGiftBaseinfoService;
    }
    protected ActReUserActivityService getHessianActReUserActivityService() throws MalformedURLException {

        if (actReUserActivityService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actReUserActivityService = (ActReUserActivityService) factory.create(ActReUserActivityService.class, gdProperties.getReUserActivity());
        }
        return actReUserActivityService;
    }

    protected ActActivityBaseinfoService getHessianActActivityBaseinfoService() throws MalformedURLException {

        if (actActivityBaseinfoService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actActivityBaseinfoService = (ActActivityBaseinfoService) factory.create(ActActivityBaseinfoService.class, gdProperties.getActActivityBaseinfoServiceUrl());
        }
        return actActivityBaseinfoService;
    }

    protected ActActivityScoreRecordService getHessianActActivityScoreRecordService() throws MalformedURLException {

        if (actActivityScoreRecordService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            actActivityScoreRecordService = (ActActivityScoreRecordService) factory.create(ActActivityScoreRecordService.class, gdProperties.getProperties().getProperty("gd.activityScoreRecord.url"));
        }
        return actActivityScoreRecordService;
    }
    protected ActActivityGiftExchangService getHessianActActivityGiftExchangService() throws MalformedURLException {

		if (actActivityGiftExchangService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			actActivityGiftExchangService = (ActActivityGiftExchangService) factory.create(ActActivityGiftExchangService.class, gdProperties.getProperties().getProperty("gd.activityGiftExchange.url"));
		}
		return actActivityGiftExchangService;
	}
    protected ReActivityGiftService getHessianActReActivityGiftService() throws MalformedURLException {

		if (reActivityGiftService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reActivityGiftService = (ReActivityGiftService) factory.create(ReActivityGiftService.class, gdProperties.getReActivityGift());
		}
		return reActivityGiftService;
	}

    @Override
    public ActActivityBaseinfoDTO getActivityInfo(String activityId) throws Exception {
        return getHessianActActivityBaseinfoService().getById(Integer.valueOf(activityId));
    }

    @Override
    public ActReUserActivityDto getReUserActivityInfo(String activityId, String userid) throws Exception {
        return getHessianActReUserActivityService().getReUserActivityInfo(activityId, userid);
    }

    @Override
    public int updateReUserActivityInfo(ActReUserActivityDto actReUserActivityDto) throws Exception {
        return getHessianActReUserActivityService().updateReUserActivityInfo(actReUserActivityDto);
    }

    @Override
    public Long insertReUserActivityInfo(ActReUserActivityEntity actReUserActivityEntity) throws Exception {
        return getHessianActReUserActivityService().insertReUserActivityInfo(actReUserActivityEntity);
    }

    @Override
    public int updatePV(Map<String, Object> map) throws Exception {
        return getHessianActActivityBaseinfoService().updatePV(map);
    }

	@Override
	public Long insertReActivityScoreRecoreInfo(ActActivityScoreRecordEntity entity) throws Exception {
		return getHessianActActivityScoreRecordService().add(entity);
	}

    @Override
    public Integer getScoreRecordTotal(Map<String, Object> condition) throws Exception {
        return getHessianActActivityScoreRecordService().getTotal(condition);
    }

    @Override
    public Integer getScoreRecordTotal2(Map<String, Object> map) throws Exception {
        return getHessianActActivityScoreRecordService().getTotal2(map);
    }

    @Override
	public int updateGiftBaseInfo(Map<String, Object> params) throws Exception {
		return getHessianActGiftBaseinfoService().updateGiftBaseInfo(params);
	}
	@Override
	public ActGiftBaseinfoDTO getByGiftBaseinfoId(Integer id) throws Exception {
		return getHessianActGiftBaseinfoService().getById(id);
	}
	@Override
	public ActReUserActivityDto getUserActivityByUserid(
			Map<String, Object> params) throws Exception {
		return getHessianActReUserActivityService().getUserActivityByUserid(params);
	}
	@Override
	public List<ActReUserActivityDto> getWechatUserInfoByUserid(
			Map<String, Object> params) throws Exception {
		return getHessianActReUserActivityService().getWechatUserInfoByUserid(params);
	}
	@Override
    @Transactional
	public void updateAllActivityInfo(ActGiftExchangeApplyEntity actGiftExchangeApply,
			Map<String, Object> params1, Map<String, Object> params2)
			throws Exception {
		//this.updateReUserActivityInfo(userActivity);
		getHessianActActivityGiftExchangService().insertActivityExchangeRecord(actGiftExchangeApply);
		getHessianActReActivityGiftService().updateActivityGift(params1);
		this.updateGiftBaseInfo(params2);
	}

	@Override
	public Integer getUserScore(Integer userId, Integer actId) throws Exception {
		return getHessianActReUserActivityService().getUserScore(userId,actId);
	}

}
