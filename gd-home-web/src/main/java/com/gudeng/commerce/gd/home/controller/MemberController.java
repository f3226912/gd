package com.gudeng.commerce.gd.home.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.home.service.MemberCerifiToolService;
import com.gudeng.commerce.gd.home.service.ProductToolService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.util.BusinessUtil;
import com.gudeng.commerce.gd.home.util.StringUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("userCenter/member")
public class MemberController  extends HomeBaseController{

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(StoreController.class);
	
	
	@Autowired
	public ProductToolService productToolService;
	
	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public QueryAreaToolService queryAreaToolService;
	
	
	@Autowired
	public MemberCerifiToolService cerifiToolService;
	
	@RequestMapping("")
	public String memberShow(HttpServletRequest request){
		try { 
			MemberBaseinfoEntity mbe=getUser(request);
			MemberBaseinfoDTO mbd=memberBaseinfoToolService.getById(mbe.getMemberId().toString());
//			AreaDTO area = queryAreaToolService.getArea(String.valueOf(mbd.getProvinceId()));
//			AreaDTO area1 = queryAreaToolService.getArea(String.valueOf(mbd.getCityId()));
//			AreaDTO area2 = queryAreaToolService.getArea(String.valueOf(mbd.getAreaId()));
//			if(null !=area){
//				String provinceName=area.getArea();
//				String areaName=area2.getArea();
//				String cityName=area1.getArea();
//				
//				putModel("provinceName",provinceName);
//				putModel("cityName",cityName);
//				putModel("areaName",areaName);
//			}	
//	
//			List<AreaDTO> listP = queryAreaToolService.findProvince();
//			List<AreaDTO> listC =new ArrayList();
//			List<AreaDTO> listA=new ArrayList();
//			
//			if(null!=area){
//				  listC = queryAreaToolService.findCity(area.getAreaID());
//			}else{
//				  listC = queryAreaToolService.findCity(listP.get(0).getAreaID());
//			}
//			
//			
//			if(null!=area1){
//				listA = queryAreaToolService.findCounty(area1.getAreaID());
//			}else{
//				listA = queryAreaToolService.findCity(listC.get(0).getAreaID());
//			}
//			String marketId=this.getCookieByMarketId(request);
//			BusinessBaseinfoDTO bdto=BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);
			
//			BusinessBaseinfoDTO bdto1= businessBaseinfoToolService.getByUserId(String.valueOf(mbe.getMemberId()));

//			putModel("bdto",bdto);
//			putModel("provinces",listP);
//			putModel("citys",listC);
//			putModel("areas",listA);
//		
			
			putModel("dto",mbd);
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		
		return "usercenter/member/member_edit";
	}
		
	@RequestMapping("contact")
	public String contact(HttpServletRequest request){
		try { 
			MemberBaseinfoEntity mbe=getUser(request);
			MemberBaseinfoDTO mbd=memberBaseinfoToolService.getById(mbe.getMemberId().toString());
			AreaDTO area = queryAreaToolService.getArea(String.valueOf(mbd.getProvinceId()));
			String provinceName=area.getArea();
			
			List listP = queryAreaToolService.findProvince();
			
			AreaDTO area1 = queryAreaToolService.getArea(String.valueOf(mbd.getCityId()));
			String cityName=area1.getArea();
			List listC = queryAreaToolService.findCity(area.getAreaID());
			
			
			AreaDTO area2 = queryAreaToolService.getArea(String.valueOf(mbd.getAreaId()));
			String areaName=area2.getArea();
			List listA = queryAreaToolService.findCounty(area1.getAreaID());
			
			
			String marketId=this.getCookieByMarketId(request);
			BusinessBaseinfoDTO bdto=BusinessUtil.getBusinessBaseinfo(request, businessBaseinfoToolService);
			
			
//			BusinessBaseinfoDTO bdto= businessBaseinfoToolService.getByUserId(String.valueOf(mbe.getMemberId()));
			
			putModel("bdto",bdto);
			putModel("provinces",listP);
			putModel("citys",listC);
			putModel("areas",listA);
			putModel("provinceName",provinceName);
			putModel("cityName",cityName);
			putModel("areaName",areaName);
			putModel("dto",mbd);
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		
		
		return "usercenter/member/member_contact";
	}
	
	
	
		
		
		
	@ResponseBody
	@RequestMapping("queryProvince")
	public String queryProvince() {
		List<AreaDTO> list = null;
		try {
			list = queryAreaToolService.findProvince();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("queryCity/{provinceId}")
	public String queryCity(@PathVariable("provinceId") String provinceId) {
		List<AreaDTO> list = null;
		try {
			list = queryAreaToolService.findCity(provinceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}

	@ResponseBody
	@RequestMapping("queryArea/{cityId}")
	public String queryArea(@PathVariable("cityId") String cityId) {
		List<AreaDTO> list = null;
		try {
			list = queryAreaToolService.findCounty(cityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.toJSONString(list);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String saveOrUpdate(MemberBaseinfoDTO mbdto,HttpServletRequest request){
		try { 
			
			
			

			if(StringUtil.isEmpty(mbdto.getRealName())||mbdto.getRealName().trim().equals("")){
				return "namenotnull";
			}
			String mobile=request.getParameter("mobile");
			if(null == mobile || StringUtil.isEmpty(mobile)){
				return "mobile";//手机号为空
			}
			Pattern pattern = Pattern.compile("^[1][34587][0-9]{9}$");
			Matcher matcher = pattern.matcher(mobile);
			
			if(!matcher.matches()){//手机不符合规则
				return "mobileE";
			}
//			mbdto.setProvinceId(Long.valueOf(0));
			if(null !=mbdto.getProvinceId() && null != mbdto.getAreaId() && null != mbdto.getCityId()){
				if(Long.valueOf(mbdto.getProvinceId())==0 ||Long.valueOf(mbdto.getAreaId())==0 ||Long.valueOf(mbdto.getCityId())==0){
					return "areaEmpty";
				}
			}
			
			
			MemberBaseinfoDTO exist1 =memberBaseinfoToolService.getByAccount(mobile);
			MemberBaseinfoDTO exist2 =memberBaseinfoToolService.getByMobile(mobile);
			
			MemberBaseinfoEntity mbe=getUser(request);
			//得到修改信息之前的member
		    MemberBaseinfoDTO member= memberBaseinfoToolService.getById(String.valueOf(mbe.getMemberId()));

			if(null != exist1  && Long.valueOf(mbdto.getMemberId()).longValue()!=exist1.getMemberId().longValue()  || null !=exist2 && mbe.getMemberId().longValue()!=exist2.getMemberId().longValue() ){
				return "existM";
			}
		    int i=memberBaseinfoToolService.updateMemberBaseinfoDTO(mbdto);
		    //农速通用户为企业时，修改手机号码的时候，同时更新到certifi（企业信息）表中
		    if(member.getLevel()!=null && member.getLevel()==2 
		    		&& member.getUserType()!=null && member.getUserType()==2
		    		&& member.getUserType()!=null && member.getUserType()==2
		    		&& mobile!=null && !mobile.equals(member.getMobile()))
		    {
		    	MemberCertifiDTO memberCertifiDTO=	cerifiToolService.getByUserId(String.valueOf(mbdto.getMemberId()));
		    	MemberCertifiDTO newDTO=new MemberCertifiDTO();
		    	newDTO.setCertifiId(memberCertifiDTO.getCertifiId());
		    	newDTO.setMobile(mobile);
		    	newDTO.setLinkMan(mbdto.getRealName());
		    	cerifiToolService.updateMemberCertifiDTO(newDTO);
		    }
		    
			if(i>0){
				return "success";
			}else{
				return "faild";
			}
			
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "faild";

		
		
	}
	
	
	
	
	
	
	
	@RequestMapping("password")
	public String password(MemberBaseinfoDTO mbdto,HttpServletRequest request){
		MemberBaseinfoEntity mbe=getUser(request);
		MemberBaseinfoDTO mbd;
		try {
			mbd = memberBaseinfoToolService.getById(mbe.getMemberId().toString());
			putModel("dto", mbd);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usercenter/member/password";
	}

	@RequestMapping("updatePassword")
	@ResponseBody
	public String updatePassword(HttpServletRequest request){
		try { 
			
			String memberId=request.getParameter("memberId");
			String password=request.getParameter("password");
			String password1=request.getParameter("password1");
			String password2=request.getParameter("password2");
			String spass=com.gudeng.commerce.gd.home.util.JavaMd5.getMD5(password+"gudeng2015@*&^-").toUpperCase();
			
			
			
			if(StringUtil.isEmpty(password)||StringUtil.isEmpty(password1)||StringUtil.isEmpty(password2)){
				return "notnull";
			}
			
			MemberBaseinfoDTO mbd=memberBaseinfoToolService.getById(memberId);
			if(!mbd.getPassword().equals(spass)){
				return "notCorrect";
			}
			//增加旧密码与新密码不能一样的的判断
			if (password.equals(password1)) {
				return "checkSame";
			}
			if(!password1.equals(password2)){
				return "notSame";
			}
			MemberBaseinfoDTO mbdnew =new MemberBaseinfoDTO();
			mbdnew.setMemberId(Long.valueOf(memberId));
			mbdnew.setPassword(com.gudeng.commerce.gd.home.util.JavaMd5.getMD5(password2+"gudeng2015@*&^-").toUpperCase());
		    int i=memberBaseinfoToolService.updateMemberBaseinfoDTO(mbdnew);
			
			if(i>0){
				return "success";
			}else{
				return "faild";
			}
			
			
			
		} catch (Exception e) {
			logger.trace(e.getMessage());
		}
		return "faild";

		
		
	}
	
}
