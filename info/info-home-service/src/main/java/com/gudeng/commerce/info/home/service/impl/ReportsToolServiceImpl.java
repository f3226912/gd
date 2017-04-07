package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;
import com.gudeng.commerce.info.customer.dto.ProBszbankDTO;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.dto.ReportsDTO;
import com.gudeng.commerce.info.customer.service.ReportsService;
import com.gudeng.commerce.info.home.service.ReportsToolService;
import com.gudeng.commerce.info.home.util.GdProperties;

public class ReportsToolServiceImpl implements ReportsToolService {

    @Autowired
    private GdProperties gdProperties;
	@Override
	public List<ProBszbankDTO> getProBszbankList(Map<String, Object> parm)  throws Exception{
		return getReportsHessianService().getProBszbankList(parm);
	}
	
	@Override
	public List<ProBaiduEntityDTO> getbaiduList(Map<String, Object> parm)  throws Exception{
		return getReportsHessianService().getbaiduList(parm);
	}

    private static ReportsService reportsService;

    protected ReportsService getReportsHessianService() throws MalformedURLException {
        if (reportsService == null) {
            String hessianUrl = gdProperties.getReportsServiceUrl();
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            reportsService = (ReportsService) factory.create(ReportsService.class, hessianUrl);
        }
        return reportsService;
    }

    @Override
    public List<ReportsDTO> getReportsList(Map<String, Object> parm) throws Exception {
        return getReportsHessianService().getReportsList(parm);
    }

    /**
     * @Description 根据用户ID查出该用户所拥有的所有报表权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    @Override
    public List<ReportsDTO> getListByUserId(Map<String, Object> map) throws Exception {
        return getReportsHessianService().getListByUserId(map);
    }

	@Override
	public List<ProOperateDTO> getOperateList(Map<String, Object> parm) throws Exception {
		// TODO Auto-generated method stub
		return getReportsHessianService().getOperateList(parm);
	}

}
