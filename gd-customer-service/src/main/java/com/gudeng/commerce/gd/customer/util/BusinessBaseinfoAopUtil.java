package com.gudeng.commerce.gd.customer.util;

import java.net.MalformedURLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gudeng.commerce.gd.customer.util.GdProperties;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;

@Aspect
@Component
public class  BusinessBaseinfoAopUtil {

	@Autowired
	public GdProperties gdProperties;
	
	public String mBaseUrl;
	
	protected String getMBaseUrl() {
		if(mBaseUrl == null || mBaseUrl.equals("")){
			mBaseUrl = gdProperties.getMBaseUrl();
		}
		return mBaseUrl;
	}
	
	@Autowired
	private BaseDao<?> baseDao;

	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}


	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.customer.service.impl.BusinessBaseinfoServiceImpl.addBusinessBaseinfoEnt(..))",returning="id")
	public void updateBusinessBaseinfoDTO(JoinPoint jp,Object id) {
		Long businessId =(Long)id;
		BusinessBaseinfoDTO bDto=new BusinessBaseinfoDTO();
		bDto.setBusinessId(businessId);
		bDto.setShopsUrl(getMBaseUrl()+"shop"+businessId+".html");
		baseDao.execute("BusinessBaseinfo.updateBusinessBaseinfo", bDto);
	}
							
	

}
