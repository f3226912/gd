package com.gudeng.commerce.gd.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.api.service.CarLineApiService;
import com.gudeng.commerce.gd.api.service.CarManagerApiService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.framework.web.controller.BaseController;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

public class CarLineMessgaeThread extends Thread {

	private MemberAddressApiService memberAddressApiService;

	private CarLineApiService carLineApiService;

	private MemberToolService memberToolService;
    private CarLineDTO carLineDTO;
    private CarManagerApiService carManagerApiService;
	public CarLineMessgaeThread(CarLineDTO carLineDTO,MemberAddressApiService memberAddressApiService,CarLineApiService carLineApiService,MemberToolService memberToolService,CarManagerApiService carManagerApiService){

	this.memberAddressApiService=memberAddressApiService;
	this.carLineApiService=carLineApiService;
	this.memberToolService=memberToolService;
	this.carManagerApiService=carManagerApiService;
	this.carLineDTO=carLineDTO;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub\

    		try {
//    		
//    		
//			//int i=0;
//					//memberAddressApiService.getMebApiMessageCount(carLineDTO);
//			
//			//int j=0;
//    			//获取带车长,车型条件的总数量
//    	   // Long carId= carLineDTO.getCarId();
//    		
    	    CarsDTO cd = carManagerApiService.getCarMessageById(Long.toString(carLineDTO.getCarId()));
			 carLineDTO.setCarLength(cd.getCarLength());
		     carLineDTO.setCarType(cd.getCarType());
			List<MemberAddressDTO>	list  = memberAddressApiService.getMebApiMessage(carLineDTO);

            //如果全条件查询,等于0个的话,则再次用步带车长,车型条件的进行过滤
			if(list.size()==0){
				 carLineDTO.setCarLength(null);
			     carLineDTO.setCarType("");
			     list  = memberAddressApiService.getMebApiMessage(carLineDTO);
			}
			//如果全条件查询的,大于0个小于5个,则行进一次不要带车长,车型条件查询
			if(list.size()>0&&list.size()<5){
				 carLineDTO.setCarLength(null);
			     carLineDTO.setCarType(null);
			     
			     //过滤掉,已经查询出来的全条件查询的Id
			     Map <String, Object> p2 = new HashMap<String, Object>();
			     for (int k = 0; k < list.size(); k++) {
					p2.put("id"+k, list.get(k).getId());
				}
			     p2.put("size", list.size());
			     //过滤掉已经查询出来的全条件查询的Id后,只要查剩余几条的
			     p2.put("endRow", 5-list.size());
			     List<MemberAddressDTO>	list2  = memberAddressApiService.getMebApiMessagetwo(carLineDTO,p2);
			     if(list.size()>0){
			    	
			    	 list.addAll(list2);
			     }
                 
			}
//			if(mc<5){
//    			
//			    carLineDTO.setCarLength(null);
//				carLineDTO.setCarType(null);
//				list = memberAddressApiService.getMebApiMessage(carLineDTO);
//			}	
			if(list!=null && list.size()>0){
	    		carLineApiService.setMebApiMessage(carLineDTO,list);
	    		MemberBaseinfoDTO memberDTO = memberToolService.getById(carLineDTO.getMemberId()+"");
				UMengPushMessage pushMessage2 = new UMengPushMessage();
				GdMessageDTO gdMessage2 = new GdMessageDTO();
				gdMessage2.setSendApp("2");
				gdMessage2.setSendType("1");
				gdMessage2.setTicket("【农速通为您推送线路信息】");
				gdMessage2.setTitle("农速通为您推送线路信息");
				gdMessage2.setContent("根据你发布的货源信息,我们为你推荐了路线信息,请查收");
				gdMessage2.setAfter_open("go_app");
				//gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
				//ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
				//android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
				gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
				gdMessage2.setProduction_mode(true);
				pushMessage2.pushMessage(gdMessage2);
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}
