/**
 * @Title: AbstractCheck.java
 * @Package com.gudeng.commerce.gd.admin.service.impl.sub
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年12月2日 下午7:38:27
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.business.sub.limit;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;

/**
 * @Description: TODO(补贴校验规则抽象类)
 * @author mpan
 * @date 2015年12月2日 下午7:38:27
 */
public abstract class ACheckRule implements ICheckRule {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	/**
	 * 持有后继的校验对象
	 */
	protected ACheckRule checkRule;
	
	protected abstract String getLimitType();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<SubRangeLimitRuleDTO> getSubRangeLimitType(){
		
		String limitType = getLimitType();
		Map map = new HashMap();
		map.put("limitType", limitType);
		return baseDao.queryForList("SubLimitRule.getSubRangeLimitRuleByRuleId", map, SubRangeLimitRuleDTO.class);
	}
	
	/**
	 * 获取可用补贴限制规则
	 */
	@SuppressWarnings("unchecked")
	protected SubLimitRuleDTO getSubLimitRule(Integer marketId){
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("marketId", marketId);
		//map.put("persaleId", marketId);
		return baseDao.queryForObject("SubLimitRule.getSubLimitRuleList", map, SubLimitRuleDTO.class);
	}	
	
	protected abstract Map getOrderData(OrderBaseinfoDTO orderBase) throws ServiceException;

	/**
	 * @Title: dayRangeValid
	 * @Description: TODO(每天维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract String dayRangeValid(Map map, SubRangeLimitRuleDTO rultDTO) throws ServiceException;

	/**
	 * @Title: weekRangeValid
	 * @Description: TODO(每周维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract String weekRangeValid(Map map, SubRangeLimitRuleDTO rultDTO) throws ServiceException;

	/**
	 * @Title: monthRangeValid
	 * @Description: TODO(每周维度校验)
	 * @throws ServiceException
	 * @throws
	 */

	protected abstract String monthRangeValid(Map map, SubRangeLimitRuleDTO rultDTO) throws ServiceException;
	
     // 抽象方法，对5种类型，3个维度(天、周、月)进行判断，调用实例化方法分别进行判断。
	@SuppressWarnings("unchecked")
	public String validation(OrderBaseinfoDTO orderBase) throws ServiceException {
		List<SubRangeLimitRuleDTO> subRuleList = getSubRangeLimitType(); //获取服务类型；
		Map map = getOrderData(orderBase);	
//		String result = dayRangeValid(map, subRuleList.get(0));
//		result = weekRangeValid(map, subRuleList.get(1));
//		result = monthRangeValid(map, subRuleList.get(2));
//		Writesub(orderBase);
//		return result;
		
		String result = dayRangeValid(map, subRuleList.get(0));
		if(!"OK".equals(result)){
			Writesub(orderBase);
			return result;	
		}
		
		result = weekRangeValid(map, subRuleList.get(1));
		if(!"OK".equals(result)){
		Writesub(orderBase);
			return result;
		}
		
		result = monthRangeValid(map, subRuleList.get(2));
		if(!"OK".equals(result)){
			Writesub(orderBase);
			return result;
		} else { 		
		   Writesub(orderBase);	
	 }
		return result;
}

	@SuppressWarnings("unchecked")
	protected String Writesub(OrderBaseinfoDTO orderBase)throws ServiceException{
	
	List<SubRangeLimitRuleDTO> subRuleList = getSubRangeLimitType(); //获取服务类型；
	Map map1 = getOrderData(orderBase);
	long orderno = orderBase.getOrderNo();
	
	String result1 = dayRangeValid(map1, subRuleList.get(0));
	String result2 = weekRangeValid(map1, subRuleList.get(1));
	String result3 = monthRangeValid(map1, subRuleList.get(2));
	
     try{
		// 1.对于同一个类型的判断必须是每天、每周、每月都审批通过，才能写库
		if("ok".equals(result1)&"ok".equals(result2)&"ok".equals(result3)){
			//1.1 向order_baseinfo更新订单状态
		     @SuppressWarnings("rawtypes")
			 Map map = new HashMap();
		    map.put("orderno",orderno);//订单号
		  	map.put("buySubStatus", 21);// 买家补贴状态 0无补贴 11已计算补贴 10待出岗计算补贴 21验证通过 20验证不通过
		  	map.put("sellSubStatus", 21);//农批商补贴状态 0无补贴 11已计算补贴 10待出岗计算补贴 21验证通过 20验证不通过
		  	map.put("createTime", new Date());
		  	map.put("createUserId", "");
		  	 int tam = baseDao.execute("OrderBaseinfo.updateBuySubStatus", map);
		  	   
		    //1.2 向AuditLog 插入一条数据
		  	 @SuppressWarnings("rawtypes")
			Map map11 = new HashMap();
	    	 map11.put("orderno", orderno);//订单号
	  	     map11.put("type", 1);//类型 1补贴审核 2提现审核 3订单审核
	  	     map11.put("auditName","sys");//审核人姓名,sys
	  	     map11.put("createTime",new Date());//创建时间
	  	     int tam1 = baseDao.execute("AuditLog.addAuditLog", map11);  
	  	     
	  	   //1.3 向SubAudit插入一条数据
		  	 @SuppressWarnings("rawtypes")
			   Map map2 = new HashMap();
	    	 map2.put("orderNo",orderno);//订单号
		     map2.put("subStatus 补贴状态", 1);//补贴状态 1待补贴 2系统驳回 3已补贴 4不予补贴
		     map2.put("auditName", "sys");//创建人员
		     map2.put("createTime", new Date());//创建时间
		     map2.put("rejectReason", 1); //驳回原因	    
	  	   int tam2 = baseDao.execute("SubAudit.addAccTransInfo", map1);							
			}
		
		// 2.对于同一个类型的判断，如果每天、每周、每月每月全部通过，则审批不通过，也得写库
		
		else{
			 //向order_baseinfo更新订单状态
			 @SuppressWarnings("rawtypes")
			 Map map = new HashMap();
		     map.put("orderno",orderno);//订单号
		     map.put("buySubStatus", 20);// 买家补贴状态 0无补贴 11已计算补贴 10待出岗计算补贴 21验证通过 20验证不通过
		     map.put("sellSubStatus", 20);//农批商补贴状态 0无补贴 11已计算补贴 10待出岗计算补贴 21验证通过 20验证不通过
		     map.put("createTime",new Date());
		     map.put("createUserId", "");
		  	 int tam = baseDao.execute("OrderBaseinfo.updateBuySubStatus", map);
		
		  	   
		       //向AuditLog 插入一条数据		   
		  	 @SuppressWarnings("rawtypes")
			Map map2 = new HashMap();
		  	map2.put("type", 1);
		  	map2.put("orderNo", orderno);
	  	     int tam1 = baseDao.execute("AuditLog.addAuditLog", map2);  
	  	     
	  	   //向SubAudit插入一条数据
	  	    @SuppressWarnings("rawtypes")
			 Map map11 = new HashMap();
	  	     map11.put("orderNo",orderno);//订单号
		     map11.put("subStatus 补贴状态", 4);//补贴状态 1待补贴 2系统驳回 3已补贴 4不予补贴
		     map11.put("auditName", "sys");//创建人员
		     map11.put("createTime", new Date());//创建时间
		     map11.put("rejectReason", 1); //驳回原因	    
	  	   int tam2 = baseDao.execute("SubAudit.addAccTransInfo", map11);	 
		}
     } catch(Exception e){
    	 e.printStackTrace();
     }
		return null;
	}

	public void setCheckRule(ACheckRule checkRule) {
		this.checkRule = checkRule;
	}

	}
