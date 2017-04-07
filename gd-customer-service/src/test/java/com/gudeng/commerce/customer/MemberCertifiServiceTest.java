package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.entity.MemberCertifiEntity;
import com.gudeng.commerce.gd.customer.service.MemberCertifiService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class MemberCertifiServiceTest extends TestCase{
	private static MemberCertifiService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/memberCertifiService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (MemberCertifiService) factory.create(MemberCertifiService.class, url);				
	}
	
//	@SuppressWarnings("static-access")
//	public void testGetDic()throws Exception {
//		MemberCertifiService service=this.getService();
//		MemberCertifiDTO dto = service.getById("1");
//		if(null != dto){
//			System.out.println(dto.getCertifiId()+":"+dto.getStatus());
//		}
//	}
	
//	public void testGetByBirthday() throws Exception{
//		DemoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		String startdate="2000-03-01 00:00:00";
//		
//		Date st=DateUtil.formateDate(startdate,DateUtil.DATE_FORMAT_DATETIME);
//		
//		String enddate="2015-03-02 00:00:00";
//		
//		Date ed=DateUtil.formateDate(enddate,DateUtil.DATE_FORMAT_DATETIME);
//
//		
//		map.put("startdate",st);
//		map.put("enddate",ed);
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<DictDTO> dicList = service.getByBirthday(map);
//		
//		if(null != dicList){
//			for(DictDTO dto : dicList){
//				System.out.println(dto.getId() + " : " + dto.getName()+" : "+dto.getBirthday());
//			}
//		}
//	}
//	public void testGetByCondition() throws Exception{
//		MemberCertifiService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		MemberCertifiDTO dto = service.getById("4");
//		System.out.println(dto.getMemberId() + " : " + dto.getNickName()+" : "+dto.getBirthday());
//	}
//	
//	public void testGetByMobile() throws Exception{
//		MemberCertifiService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		MemberCertifiDTO dto = service.getByMobile("13430135673");
//		System.out.println(dto.getMemberId() + " : " + dto.getNickName()+" : "+dto.getBirthday());
//	}
	
	
	
//	public void testGetBySearch() throws Exception{
//		MemberCertifiService service=this.getService();
//		Map map =new HashMap();
//		map.put("nickName", "test");
//		map.put("mobile", "13430135673");
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<MemberCertifiDTO> list=service.getBySearch(map);
//
//		for(MemberCertifiDTO dto:list){
//			System.out.println(dto.getMemberId() + " : " + dto.getNickName()+" : "+dto.getBirthday());
//		}	
//		}
	
//	public void testGetByName() throws Exception{
//		MemberCertifiService service=this.getService();
//		Map map =new HashMap();
//		map.put("nickName", "test");
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<MemberCertifiDTO> list=service.getByNickName(map);
//
//		for(MemberCertifiDTO dto:list){
//			System.out.println(dto.getMemberId() + " : " + dto.getNickName()+" : "+dto.getBirthday());
//		}	
//		}
//	
//	public void testGetAll() throws Exception{
//		MemberCertifiService service=this.getService();
//		List<MemberCertifiDTO> list=service.getAll();
//		for(MemberCertifiDTO dto:list){
//			System.out.println(dto.getMemberId() + " : " + dto.getNickName()+" : "+dto.getBirthday());
//		}
//	}
	
//	
//	public void testGetTotal() throws Exception{
//		MemberCertifiService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("id", "1");
//		System.out.println("total:" + service.getTotal(map));
//		
//	}
//	
	
//	public void testDelete()throws Exception {
//	MemberCertifiService service=this.getService();
//		 int i= service.deleteById("1");
//		if(i==1){
//			System.out.println("删除id为"+1+"的记录成功");
//		}
//	}
//	
//	public void testAddByDto()throws Exception {
//		MemberCertifiService service=this.getService();
//		MemberCertifiDTO mb = new MemberCertifiDTO();
//		mb.setAccount("testAccount2");
//		mb.setBirthday_string("2000-03-01 00:00:00"); 
//		mb.setCreateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//		mb.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//		mb.setPassword("123456");
//		mb.setCreateUserId(1L); 
//		int i=service.addMemberCertifiDTO(mb) ;
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
//	
	
	
	
	public void testAddByEnt()throws Exception {
		MemberCertifiService service=this.getService();
		MemberCertifiDTO mb = new MemberCertifiDTO();
		mb.setMemberId(18L);
		mb.setType("1");
		mb.setCompanyName("点点电商公司");
		mb.setLinkMan("黄生");
		mb.setMobile("13230234567");
		mb.setIsmobile("1");
		mb.setInitialCapital(100.00);
		mb.setIdCard("222123199010101234");
		mb.setCardPhotoUrl("/image/idcard.img");
		mb.setNgReason("图片不清晰");
		mb.setStatus("1");
		mb.setUrl("http://in.com");
		mb.setBzl("Gd-123");
		mb.setBzlPhotoUrl("/img/bzl.img");
		mb.setOrgCode("粤批0029");
		mb.setOrgCodePhotoUrl("/img/code.img");
		mb.setCommitTime_string(DateUtil.getSysDateString());
		mb.setIsbzl("1");
		mb.setIsIdCard("1");
		mb.setIsBrand("1");
		mb.setCertificationTime_string(DateUtil.getSysDateString());
		mb.setCertificationUserId(2L); 
		mb.setCreateTime_string(DateUtil.getSysDateString()); 
		mb.setUpdateTime_string(DateUtil.getSysDateString()); 
		mb.setCreateUserId("1");
		mb.setCreateUserId("1");
		int i=service.addMemberCertifiDTO(mb);
	 
			System.out.println("。。。。。。。。。"+ i);
	 
	}

	
//	public void testupdate()throws Exception {
//		MemberCertifiService service=this.getService();
//		MemberCertifiDTO mb = new MemberCertifiDTO();
//		mb.setMemberId(2L);
//		mb.setAccount("testAccount");
//		mb.setBirthday_string("2001-03-01 00:00:00"); 
//		mb.setCreateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//		mb.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//		mb.setPassword("1234567");
//		mb.setNickName("test123");
//		mb.setMobile("13430135673");
//		mb.setLevel(2);
//		mb.setCreateUserId(1L); 
//		int i=service.updateMemberCertifiDTO(mb) ;
//		if(i==1){
//			System.out.println("修改dto成功");
//		}
//	}
	
}
