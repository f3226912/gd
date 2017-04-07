package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;
import com.gudeng.commerce.gd.order.service.EnPostLogService;
@Service
public class EnPostLogServiceImpl implements EnPostLogService {
	
	@Autowired
	private BaseDao baseDao;
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("EnPostLog.getTotal", map, Integer.class);
	}
	@Override
	public List<EnPostLogEntity> getByCondition(Map<String, Object> map)
			throws Exception {
		List<EnPostLogEntity> list= baseDao.queryForList("EnPostLog.getByCondition", map, EnPostLogEntity.class);
		return list;
	}
	@Override
	public Long insertEnPostLog(Map<String, Object> map) throws Exception {
	   EnPostLogEntity enp=new EnPostLogEntity();
	   if(null!=map.get("version")&&!"".equals(String.valueOf(map.get("version")))){
	     enp.setVersion(String.valueOf(map.get("version")));
	   }
	   if(null!=map.get("state")&&!"".equals(String.valueOf(map.get("state")))){
         enp.setState(Integer.parseInt(String.valueOf(map.get("state"))));
       }
	   if(null!=map.get("charset")&&!"".equals(String.valueOf(map.get("charset")))){
	     enp.setCharset(String.valueOf(map.get("charset")));
       }
	   if(null!=map.get("machinenum")&&!"".equals(String.valueOf(map.get("machinenum")))){
	     enp.setMachinenum(String.valueOf(map.get("machinenum")));
       }
	   if(null!=map.get("merchantnum")&&!"".equals(String.valueOf(map.get("merchantnum")))){
	     enp.setMerchantnum(String.valueOf(map.get("merchantnum")));
       }
	   if(null!=map.get("transype")&&!"".equals(String.valueOf(map.get("transype")))){
	     enp.setTransype(String.valueOf(map.get("transype")));
       }
	   if(null!=map.get("orderno")&&!"".equals(String.valueOf(map.get("orderno")))){
	     enp.setOrderno(Long.parseLong(String.valueOf(map.get("orderno"))));
       }
	   if(null!=map.get("orderfee")&&!"".equals(String.valueOf(map.get("orderfee")))){
	     enp.setOrderfee(String.valueOf(map.get("orderfee")));
       }
	   if(null!=map.get("ratefee")&&!"".equals(String.valueOf(map.get("ratefee")))){
	     enp.setRatefee(String.valueOf(map.get("ratefee")));
       }
	   if(null!=map.get("payfee")&&!"".equals(String.valueOf(map.get("payfee")))){
	     enp.setPayfee(String.valueOf(map.get("payfee")));
       } 
	   if(null!=map.get("payresultcode")&&!"".equals(String.valueOf(map.get("payresultcode")))){
	     enp.setPayresultcode(String.valueOf(map.get("payresultcode")));
       } 
	   if(null!=map.get("payresultmsg")&&!"".equals(String.valueOf(map.get("payresultmsg")))){
	     enp.setPayresultmsg(String.valueOf(map.get("payresultmsg")));
       } 
	   if(null!=map.get("paycardno")&&!"".equals(String.valueOf(map.get("paycardno")))){
	     enp.setPaycardno(String.valueOf(map.get("paycardno")));
       } 
	   if(null!=map.get("transdate")&&!"".equals(String.valueOf(map.get("transdate")))){
	     enp.setTransdate(DateUtils.parseDate(String.valueOf(map.get("transdate")), 
	           "yyyyMMdd"));
       } 
	   if(null!=map.get("transdate")&&!"".equals(String.valueOf(map.get("transdate")))&&null!=map.get("transtime")&&!"".equals(String.valueOf(map.get("transtime")))){
	     enp.setTranstime( DateUtils.parseDate(String.valueOf(map.get("transdate"))+String.valueOf(map.get("transtime")), 
	           "yyyyMMddHHmmss"));
       } 
	  
	   if(null!=map.get("transseqno")&&!"".equals(String.valueOf(map.get("transseqno")))){
	     enp.setTransseqno(String.valueOf(map.get("transseqno")));
       } 
	   if(null!=map.get("reserved")&&!"".equals(String.valueOf(map.get("reserved")))){
	     enp.setReserved(String.valueOf(map.get("reserved")));
       } 
	   if(null!=map.get("bz")&&!"".equals(String.valueOf(map.get("bz")))){
         enp.setBz(String.valueOf(map.get("bz")));
       } 
		return  (Long)baseDao.persist(enp, Long.class);
		
	}
	@Override
	public int updateEnPostLog(Map<String, Object> map) throws Exception {
	  if(null!=map.get("transdate")&&!"".equals(String.valueOf(map.get("transdate")))&&null!=map.get("transtime")&&!"".equals(String.valueOf(map.get("transtime")))){
        String time=String.valueOf(map.get("transdate"))+String.valueOf(map.get("transtime"));
        map.put( "transtime",DateUtils.parseDate(time, "yyyyMMddHHmmss"));
      }else{
        if(map.containsKey("transtime")){
          map.remove( "transtime");
        }
      
      }
	  
	  if(null!=map.get("transdate")&&!"".equals(String.valueOf(map.get("transdate")))){
	    map.put("transdate",DateUtils.parseDate(String.valueOf(map.get("transdate")), 
              "yyyyMMdd"));
      }
		return (int) baseDao.execute("EnPostLog.updateEnPostLog", map);
	}
  @Override
  public EnPostLogEntity getById(Map<String, Object> map) throws Exception {
    
    return (EnPostLogEntity)baseDao.queryForObject("EnPostLog.getById", map, EnPostLogEntity.class);
  }

}
