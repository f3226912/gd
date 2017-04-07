package com.gudeng.commerce.gd.order.business.sub.limit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.entity.SubRangeLimitRuleEntity;
import com.gudeng.commerce.gd.order.service.OrderSubService;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;
public class SubLimitCheckManagementImpl implements ICheckRule{

//	private AbstractCheckRule AbstractCheckRule;
	@Autowired
	private SubLimitRuleService subLimitRuleService;
	@Autowired
	protected ACheckRule tamountLimitCheck2; //市场补贴总额数限制
	@Autowired
	protected ACheckRule twoUtimesLimitCheck2; //用户间交易频率限制检查类
	@Autowired
	protected ACheckRule uamountLimitCheck2;   //补贴用户额度限制检查类
	@Autowired
	protected ACheckRule utimesLimitCheckRuleXtd2; //补贴用户交易频率限制检查类
	@Autowired
	protected ACheckRule vehLimitCheck2;  //车辆限制检查类
	
//开关判断，修改“validation”为“Switch”
	public String Switch(OrderBaseinfoDTO orderBase) throws ServiceException {
		String result = "OK";
		
		SubLimitRuleDTO activeRule = tamountLimitCheck2.getSubLimitRule(orderBase.getMarketId());
//		SubRangeLimitRuleEntity entity=null;
//		Map map = new HashMap();
//		map.put("limitType", entity.getLimitType());
//		List<SubLimitRuleDTO> dto = subLimitRuleService.getSubLimitRuleList(map);
		
	// (自己写的) SubLimitRuleDTO activeRule = uamountLimitCheck2.getSubLimitRule(orderBase.getMarketId());
		
		if(activeRule!=null){	
			if("1".equals(activeRule.getUamountLimit())){
				//ACheckRule = new UamountLimitCheck();
	 //1.补贴用户额度， 如果开关打开，执行validation方法(对3个维度和5种类型判断)，实现开关对补贴审核控制；
				result = uamountLimitCheck2.validation(orderBase); 
				if(!"OK".equals(result)){
					return result;
				}
			}
		}

		if(activeRule!=null){
		//2.private String vehLimit; // 车辆限制开关 1开 0关	
		if("1".equals(activeRule.getVehLimit())){
			//ACheckRule = new VehLimitCheck();
			// 如果开关打开，执行validation方法(对3个维度和5种类型判断)，实现开关与补贴审批的绑定；
			result = vehLimitCheck2.validation(orderBase);
			if(!"OK".equals(result)){
				return result;				
	    	}
		}
	}
 
		if(activeRule!=null){
		//	private String utimesLimit; // 补贴用户次数限制开关 1开 0关
        if("1".equals(activeRule.getUtimesLimit())){
			//ACheckRule = new TwoUtimesLimitCheck();
	//		result = twoUtimesLimitCheck2.validation(orderBase);
			result = utimesLimitCheckRuleXtd2.validation(orderBase);
			if(!"OK".equals(result)){
				return result;	   	
		}
       }
     }
		
	if(activeRule!=null){
		//	private String twoUtimesLimit; // 两用户间交易频率限制 1开 0关
		if("1".equals(activeRule.getTwoUtimesLimit())){
				//ACheckRule = new TwoUtimesLimitCheck();
				result = twoUtimesLimitCheck2.validation(orderBase);
				if(!"OK".equals(result)){
					return result;	   		
		   }
    	}
	}
	
	  if(activeRule!=null){
		// private String tamountLimit; // 补贴总额数限制开关 1开 0关
		if("1".equals(activeRule.getTamountLimit())){
				//ACheckRule = new TamountLimitCheck();
				// 如果开关打开，执行validation方法(对3个维度和5种类型判断)，实现开关与补贴审批的绑定；
				result = tamountLimitCheck2.validation(orderBase);
				if(!"OK".equals(result)){
					return result;	   				
		}
		return result;
	 }
	  }
		return result; 
   }
 
 
//      public void main(String string) throws ServiceException {
//    	  
//        int ordeBase = 12345678;
//        OrderBaseinfoDTO  o= new OrderBaseinfoDTO();
//        o.setOrderNo(Long.valueOf(ordeBase));;
//        System.out.println(Switch(o));
//     
////        }
	

         public static void main(String[] args) {
		
        	String url = "http://localhost:8080/gd-order/service/orderSubService.hs";
        //	String url = "http://localhost:8080/gd-order/service/orderSubService.hs";
     		HessianProxyFactory factory = new HessianProxyFactory();
     		try {
     		  long orderBase = 1449141678390l;
     		  OrderSubService orderSubService=(OrderSubService)factory.create(OrderSubService.class, url);
     	//    	OrderSubService orderSubService= new OrderSubServiceImpl();
//     		    orderSubService.checkOrderSubRule(orderBase);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
         } 
      }

      
     

