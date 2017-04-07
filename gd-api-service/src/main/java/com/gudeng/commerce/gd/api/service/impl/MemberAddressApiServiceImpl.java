package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.input.MemberAddressInputDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.GoodsTypeEnum;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.service.MemberAddressApiService;
import com.gudeng.commerce.gd.api.service.MemberCertifiApiService;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.thread.CountCarMileageThread;
import com.gudeng.commerce.gd.api.thread.RuleForMemberThread;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.EGoodsType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ParamsUtil;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.MemberAddressService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;

public class MemberAddressApiServiceImpl implements MemberAddressApiService {
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberAddressApiServiceImpl.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public MemberToolServiceImple memberToolServiceImple;
	@Autowired
	public MemberToolService memberToolService;
	@Autowired
	private AreaToolService areaToolService;
	
	private static MemberAddressService memberAddressService;
	@Autowired
	private MemberCertifiApiService memberCertifiApiService;
	
	private static AreaService areaService;
	
	/**
	 * 最大推送线路信息数
	 */
	private static final int MAX_MESSAGE = 5;
	
	/**
	 * 当前手机登录用户的货源发布信息列表获取
	 */
	@Override
	public List<MemberAddressDTO> getMemberAddressByUserId(MemberAddressDTO memberAddressDTO) throws Exception {
		return getMemberAddress(memberAddressDTO);
	}
    
	protected AreaService getHessianAreaService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.area.url");
		if(areaService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}
		return areaService;
	}
	private MemberAddressService hessianCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getMemberAddressUrl();
		if (memberAddressService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			memberAddressService = (MemberAddressService) factory.create(
					MemberAddressService.class, hessianUrl);
		}
		return memberAddressService;
	}
	/**
	 * 当前手机登录用户新增货源信息发布
	 * add by yanghaoyu
	 * 
	 */
	@Override
	public int addMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
		return hessianCategoryService().addMemberAddressDTO(memberAddressDTO);
	}

	/**
	 * 根据当前选择的ID行货源信息进行修改
	 * add by yanghaoyu
	 */
	@Override
	public int updateMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
		 return hessianCategoryService().updateMemberAddressDTO(memberAddressDTO);
	}
    
	
	/**
	 * 根据选择删除的货源信息ID,删除
	 * add by yanghaoyu
	 * 
	 */
	@Override
	public int delMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
		long id=memberAddressDTO.getId();
		String ids=Long.toString(id);
		return hessianCategoryService().deleteById(ids);
	}

	@Override
	public int replayMemberAddress(MemberAddressDTO memberAddressDTO)
			throws Exception {
		return hessianCategoryService().replayMemberAddress(memberAddressDTO);
	}
    
	@Override
	public MemberAddressDTO getMemberAddressById(
			MemberAddressDTO memberAddressDTO) throws Exception {
		String id=Long.toString(memberAddressDTO.getId());
		return hessianCategoryService().getById(id);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByCondition(
			MemberAddressDTO memberAddressDTO) throws Exception {
		
		
		return getMemberAddress(memberAddressDTO);
	}
	
	
	private List<MemberAddressDTO> getMemberAddress(
			MemberAddressDTO memberAddressDTO) throws MalformedURLException, Exception {
		
		List<MemberAddressDTO> retList=new ArrayList<>();
		
		//MemberBaseinfoDTO memberBaseinfoDTO=null;
		
		
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
	 		retList=hessianCategoryService().getMemberAddressByCondition(memberAddressDTO);
	 			//个人
			 for (MemberAddressDTO singleDto : retList) {
				//Long userId=singleDto.getUserId();
				//if(singleDto.getCreateUserId()!=null){
				//String creatUserId=singleDto.getCreateUserId();
				//if(creatUserId!=null||!"".equals(creatUserId)){
					//  memberBaseinfoDTO=memberToolServiceImple.getById(creatUserId);
					  if(singleDto.getCompanyContact()!=null){
						 // singleDto.setContact(singleDto.getCompanyContact().substring(0, 1)+"先生");
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  
					  singleDto.setCompanyName(singleDto.getCompanyName());
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
						if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
							singleDto.setS_provinceIdString("全国");
						}
						
//						if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//							if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//								singleDto.setS_cityIdString("");
//							}else{
//								singleDto.setS_provinceIdString("");
//							}
//							
//						}
						if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
							singleDto.setF_provinceIdString("全国");
						}
//						if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//							if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//								singleDto.setF_cityIdString("");
//							}else{
//								singleDto.setF_provinceIdString("");
//							}
//							
//						}
				//}
				//}
			}
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
			 		retList=hessianCategoryService().getMemberAddressByCondition(memberAddressDTO);
			//个人
			 for (MemberAddressDTO singleDto : retList) {
				//Long userId=singleDto.getUserId();
				// if(singleDto.getCreateUserId()!=null){
				//String creatUserId=singleDto.getCreateUserId();
				//if(creatUserId!=null||!"".equals(creatUserId)){
				//  memberBaseinfoDTO=memberToolServiceImple.getById(creatUserId);
				  
				  if(singleDto.getCompanyContact()!=null){
					  //singleDto.setContact(singleDto.getCompanyContact().substring(0, 1)+"先生");
					  singleDto.setContact(singleDto.getCompanyContact());
				  }
				  singleDto.setCompanyName(singleDto.getCompanyName());
				  singleDto.setUserMobile(singleDto.getCompanyMobile());
				//}
				// }
					if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
						singleDto.setS_provinceIdString("全国");
					}
					
//					if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//						if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//							singleDto.setS_cityIdString("");
//						}else{
//							singleDto.setS_provinceIdString("");
//						}
//						
//					}
					if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
						singleDto.setF_provinceIdString("全国");
					}
//					if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//						if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//							singleDto.setF_cityIdString("");
//						}else{
//							singleDto.setF_provinceIdString("");
//						}
//						
//					}
					
			}
		}
		else  if (memberAddressDTO.getUserType()==2) {
			//MemberBaseinfoDTO memberBaseinfoDTO2 = new MemberBaseinfoDTO();
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			memberAddressDTO.setUserId(null);
			memberAddressDTO.setUserType(null);
			retList=hessianCategoryService().getMemberAddressByCondition(memberAddressDTO);
			 for (MemberAddressDTO singleDto : retList) {
					//Long userId=singleDto.getUserId();
					// String 
				// if(singleDto.getCreateUserId()!=null){
				// String creatUserId=singleDto.getCreateUserId();
				// if(creatUserId!=null||!"".equals(creatUserId)){
					 //优化,去掉查询,
					// memberBaseinfoDTO2=memberToolServiceImple.getById(creatUserId);
				 
					if(singleDto.getUserType()==null||singleDto.getUserType()==2){
						//memberBaseinfoDTO=memberToolServiceImple.getById(userId.toString());
						//  if(memberBaseinfoDTO.getCompanyContact()!=null){
						//	  singleDto.setC	ontact(memberBaseinfoDTO.getCompanyContact()+"先生");
						//
						//	singleDto.setUserMobile(memberBaseinfoDTO.getMobile());
						  if(singleDto.getCompanyContact()!=null){
							 // singleDto.setContact(singleDto.getCompanyContact().substring(0, 1)+"先生");
							  singleDto.setContact(singleDto.getCompanyContact());
						  }
						//
							singleDto.setUserMobile(singleDto.getCompanyMobile());	
					    
							 singleDto.setCompanyName(singleDto.getCompanyName()); 
					//}
					
				 //}
					}
					if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
						singleDto.setS_provinceIdString("全国");
					}
					
//					if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//						if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//							singleDto.setS_cityIdString("");
//						}else{
//							singleDto.setS_provinceIdString("");
//						}
//						
//					}
					if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
						singleDto.setF_provinceIdString("全国");
					}
//					if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//						if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//							singleDto.setF_cityIdString("");
//						}else{
//							singleDto.setF_provinceIdString("");
//						}
//						
//					}
			 }

				}
			
		if (retList!=null && retList.size()>0) {
			for (MemberAddressDTO dto : retList) {
				dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
			}
		}
		 return retList;
		 
	}

	@Override
	public int getCountByCondition(MemberAddressDTO memberAddressDTO)
			throws Exception {
        List<MemberAddressDTO> retList = new ArrayList<>();
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
		}
		else  if (memberAddressDTO.getUserType()==2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			memberAddressDTO.setUserId(null);
			memberAddressDTO.setUserType(null);


				}
			
		 
		 return 	 hessianCategoryService().getCountByCondition(memberAddressDTO);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByConditionNew(
			MemberAddressDTO memberAddressDTO) throws Exception {
	List<MemberAddressDTO> retList=new ArrayList<>();
		
		
		
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
	 		retList=hessianCategoryService().getMemberAddressByConditionNew(memberAddressDTO);
	 			//个人
			 for (MemberAddressDTO singleDto : retList) {
					  if(singleDto.getCompanyContact()!=null){
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  
					  singleDto.setCompanyName(singleDto.getCompanyName());
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
						if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
							singleDto.setS_provinceIdString("全国");
							//增加距离显示后,如果起始地是全国,则距离是0
							singleDto.setDistance(0.0);
						}
//						if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//							if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//								singleDto.setS_cityIdString("");
//							}else{
//								singleDto.setS_provinceIdString("");
//							}
//							
//						}
						if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
							singleDto.setF_provinceIdString("全国");
						}
//						if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//							if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//								singleDto.setF_cityIdString("");
//							}else{
//								singleDto.setF_provinceIdString("");
//							}
//							
//						}
						//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
			}
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
			 		retList=hessianCategoryService().getMemberAddressByConditionNew(memberAddressDTO);
			//个人
			 for (MemberAddressDTO singleDto : retList) {
				  
				  if(singleDto.getCompanyContact()!=null){
					  singleDto.setContact(singleDto.getCompanyContact());
				  }
				  singleDto.setCompanyName(singleDto.getCompanyName());
				  singleDto.setUserMobile(singleDto.getCompanyMobile());
					if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
						singleDto.setS_provinceIdString("全国");
						singleDto.setDistance(0.0);
					}
					
//					if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//						if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//							singleDto.setS_cityIdString("");
//						}else{
//							singleDto.setS_provinceIdString("");
//						}
//						
//					}
					if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
						singleDto.setF_provinceIdString("全国");
					}
//					if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//						if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//							singleDto.setF_cityIdString("");
//						}else{
//							singleDto.setF_provinceIdString("");
//						}
//						
//					}
					//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
					singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));

			}
		}
		else  if (memberAddressDTO.getUserType()==2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			//memberAddressDTO.setUserId(null);
			memberAddressDTO.setUserType(null);
			retList=hessianCategoryService().getMemberAddressByConditionNew(memberAddressDTO);
			 for (MemberAddressDTO singleDto : retList) {
				 //如果是企业,那么看情况,如果创建单据的人是个人,那么看到结果集的,就会,是个人创建的,联系人是 创建人,个人信息,如果是创建人事企业,则联系人企业联系人
				 
					if(singleDto.getUserType()==null||singleDto.getUserType()==2){
						  if(singleDto.getCompanyContact()!=null){
							  singleDto.setContact(singleDto.getCompanyContact());
						  }
							singleDto.setUserMobile(singleDto.getCompanyMobile());	
					    
							 singleDto.setCompanyName(singleDto.getCompanyName()); 
								if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
									singleDto.setS_provinceIdString("全国");
									singleDto.setDistance(0.0);
								}
								
//								if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//									if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//										singleDto.setS_cityIdString("");
//									}else{
//										singleDto.setS_provinceIdString("");
//									}
//									
//								}
								if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
									singleDto.setF_provinceIdString("全国");
								}
//								if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//									if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//										singleDto.setF_cityIdString("");
//									}else{
//										singleDto.setF_provinceIdString("");
//									}
//									
//								}
								//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
								singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
					}else{
						//增加一个分支,查询联系人的认证状态,个人联系人的认证状态
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getUserId())));
					}
			 }

				}
			
		if (retList!=null && retList.size()>0) {
			for (MemberAddressDTO dto : retList) {
				dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
			}
		}
		 return retList;
	}

	@Override
	public String getAreaString(Long f_provinceId) throws Exception {
		 AreaDTO dto = getHessianAreaService().getArea(f_provinceId.toString());
		 if(null != dto){
			 return dto.getArea();
		 }
		 return "";
	}

	@Override
	public AreaDTO getArea(String getmCity) throws Exception {
		 AreaDTO dto = getHessianAreaService().getAreaByName(getmCity);
		 if(null != dto){
			 return dto;
		 }
		 return null;
	}

	@Override
	public List<MemberAddressDTO> getMebApiMessage(CarLineDTO carLineDTO)
			throws Exception {
		return hessianCategoryService().getMebApiMessage(carLineDTO);
	}

	@Override
	public Long getmemberAddressId(Long userId) throws Exception {
		return hessianCategoryService().getmemberAddressId(userId);
	}

	@Override
	public List<CarLineDTO> getCarlineApiMessage(
			MemberAddressDTO memberAddressDTO) throws Exception {
		return hessianCategoryService().getCarlineApiMessage(memberAddressDTO);
	}

	@Override
	public void setCarLApiMessage(MemberAddressDTO memberAddressDTO,
			List<CarLineDTO> list) throws Exception {
		 hessianCategoryService().getCarlineApiMessage(memberAddressDTO,list);
		
	}

	@Override
	public int getMebApiMessageCount( CarLineDTO carLineDTO) throws Exception {
		return hessianCategoryService().getMebApiMessageCount(carLineDTO);
	}

	@Override
	public List<MemberAddressDTO> getMebApiMessagetwo(CarLineDTO carLineDTO,
			Map<String, Object> p2) throws Exception {
		return hessianCategoryService().getMebApiMessagetwo(carLineDTO,p2);
	}
	
	

	@Override
	public Integer updateMemberAdressStatusByid(String memberAdressIds)
			throws Exception {
		return hessianCategoryService().updateMemberAdressStatusByid(memberAdressIds);
	}


	@Override
	public List<MemberAddressDTO> getMebApiMessageAagin(CarLineDTO carLineDTO)
			throws Exception {
		return hessianCategoryService().getMebApiMessageAagin(carLineDTO);
	}


	@Override
	public MemberAddressDTO getNstpushMessageById(Long id) throws Exception {
		return hessianCategoryService().getNstpushMessageById(id);
	}

	@Override
	public List<PushNstMessageInfoDTO> getCarLinesBymessageId(Long id)
			throws Exception {
		return hessianCategoryService().getCarLinesBymessageId(id);
	}

	@Override
	public List<CarLineDTO> excutePush(MemberBaseinfoDTO memberDTO,
			MemberAddressDTO memberAddressDTO,
			MemberAddressApiService memberAddressApiService) throws Exception{
		
		//返回的returnList
		List<CarLineDTO> returnList=new ArrayList<>();
		
		//第一次查询的list
		List<CarLineDTO> list = memberAddressApiService
				.getCarlineApiMessage(memberAddressDTO);
		
		//第二次查询的list2
		List<CarLineDTO> list2 = new ArrayList<>();
		
		// 当第一次条件获取线路信息数为MAX_MESSAGE时直接插入推送信息
		if (list != null && list.size() == MAX_MESSAGE) {
			memberAddressApiService.setCarLApiMessage(memberAddressDTO,
					list);
			//加入返回集合中
			returnList.addAll(list);
		// 当第一次条件获取线路信息数<MAX_MESSAGE >0时,去除车类型和车长的筛选条件 第二次进行推送线路信息的查询
		} else if (list != null && list.size() < MAX_MESSAGE
				&& list.size() > 0) {
			memberAddressDTO.setCarType(null);
			memberAddressDTO.setCarLength(null);
			//第二次根据条件查询
			list2 = memberAddressApiService
					.getCarlineApiMessage(memberAddressDTO);
			
			List<CarLineDTO> list3 = new ArrayList<>();
			//首先将第一次查询list的放入list3中
			list3.addAll(list);

			boolean flag = false;
			for (CarLineDTO carLineDTO : list2) {
				if (flag) {
					break;
				}
				for (CarLineDTO cld : list) {
					// 第二次推送线路的信息不能与第一次相同
					if (carLineDTO.getId().equals(cld.getId())) {
						continue;
					}
					//循环加入第二次查询
					list3.add(carLineDTO);
					// 当推荐线路信息==MAX_MESSAGE 停止循环
					if (list3.size() == MAX_MESSAGE) {
						flag = true;
						break;
					}
				}
			}
			memberAddressApiService.setCarLApiMessage(memberAddressDTO,
					list3);
			//加入返回集合中
			returnList.addAll(list3);
			// 当第一次条件获取线路信息数为0时，直接将第二次查询的线路信息插入
		} else if (list != null && list.size() == 0) {
			memberAddressDTO.setCarType(null);
			memberAddressDTO.setCarLength(null);
			list2 = memberAddressApiService
					.getCarlineApiMessage(memberAddressDTO);
			list.addAll(list2);
			//当第二次查询的线路信息不为0 ，执行操作
			if (list.size() != 0) {
				memberAddressApiService.setCarLApiMessage(memberAddressDTO,
						list);
				//加入返回集合中
				returnList.addAll(list);
			}
			
		}
		if (returnList!=null && returnList.size()!=0) {
			UMengPushMessage pushMessage2 = new UMengPushMessage();
			GdMessageDTO gdMessage2 = new GdMessageDTO();
			gdMessage2.setSendApp("2");
			gdMessage2.setSendType("1");
			gdMessage2.setTicket("【农速通为您推送线路信息】");
			gdMessage2.setTitle("农速通为您推送线路信息");
			gdMessage2.setContent("根据你发布的货源信息,我们为你推荐了路线信息,请查收");
			gdMessage2.setAfter_open("go_app");
			// gdMessage.setActivity("com.gudeng.smallbusiness.activity.MainActivity");
			// ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
			// android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
			gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
			gdMessage2.setProduction_mode(true);
			pushMessage2.pushMessage(gdMessage2);
		}
		
		return returnList;
	}
     
	
	@Override
	public List<MemberAddressDTO> getMemberAddressByConditionNewNst2(
			MemberAddressDTO memberAddressDTO) throws Exception {
	List<MemberAddressDTO> retList=new ArrayList<>();
		
		
		
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
	 		retList=hessianCategoryService().getMemberAddressByConditionNewNst2(memberAddressDTO);
	 			//个人
			 for (MemberAddressDTO singleDto : retList) {
				  MemberBaseinfoDTO memberDTO = memberToolService
							.getById(singleDto.getCreateUserId() + "");
				  if(memberDTO!=null && memberDTO.getUserType()!=null && memberDTO.getUserType()==1 ){
					  singleDto.setContact(singleDto.getContact());
					  singleDto.setUserMobile(singleDto.getUserMobile());
					  if(singleDto.getUserId()==Long.parseLong(singleDto.getCreateUserId())){
						  singleDto.setCompanyName("");
					  }
				  }else{
					  if(singleDto.getCompanyContact()!=null){
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
				  }

					  
					  //singleDto.setCompanyName(singleDto.getCompanyName());
						if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
							singleDto.setS_provinceIdString("全国");
							//增加距离显示后,如果起始地是全国,则距离是0
							singleDto.setDistance(0.0);
						}
//						if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//							if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//								singleDto.setS_cityIdString("");
//							}else{
//								singleDto.setS_provinceIdString("");
//							}
//							
//						}
						if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
							singleDto.setF_provinceIdString("全国");
						}
//						if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//							if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//								singleDto.setF_cityIdString("");
//							}else{
//								singleDto.setF_provinceIdString("");
//							}
//							
//						}
						//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
						if(singleDto.getCreateUserId()!=null&&!"".equals(singleDto.getCreateUserId())){
							singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
						}
			}
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
			 		retList=hessianCategoryService().getMemberAddressByConditionNewNst2(memberAddressDTO);
			//个人
			 for (MemberAddressDTO singleDto : retList) {
				  
				  MemberBaseinfoDTO memberDTO = memberToolService
							.getById(singleDto.getCreateUserId() + "");
				  if(memberDTO!=null && memberDTO.getUserType()!=null && memberDTO.getUserType()==1 ){
					  singleDto.setContact(singleDto.getContact());
					  singleDto.setUserMobile(singleDto.getUserMobile());
					  if(singleDto.getUserId()==Long.parseLong(singleDto.getCreateUserId())){
						  singleDto.setCompanyName("");
					  }
				  }else{
					  if(singleDto.getCompanyContact()!=null){
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
				  }

					  
					  //singleDto.setCompanyName(singleDto.getCompanyName());
					if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
						singleDto.setS_provinceIdString("全国");
						singleDto.setDistance(0.0);
					}
					
//					if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//						if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//							singleDto.setS_cityIdString("");
//						}else{
//							singleDto.setS_provinceIdString("");
//						}
//						
//					}
					if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
						singleDto.setF_provinceIdString("全国");
					}
//					if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//						if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//							singleDto.setF_cityIdString("");
//						}else{
//							singleDto.setF_provinceIdString("");
//						}
//						
//					}
					//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
					if(singleDto.getCreateUserId()!=null&&!"".equals(singleDto.getCreateUserId())){
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
					}

			}
		}
		else  if (memberAddressDTO.getUserType()==2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			//memberAddressDTO.set
			memberAddressDTO.setUserId(Long.parseLong("-1"));
			//memberAddressDTO.setUserType(null);
			retList=hessianCategoryService().getMemberAddressByConditionNewNst2(memberAddressDTO);
			 for (MemberAddressDTO singleDto : retList) {
				 //如果是企业,那么看情况,如果创建单据的人是个人,那么看到结果集的,就会,是个人创建的,联系人是 创建人,个人信息,如果是创建人事企业,则联系人企业联系人
				 
					if(singleDto.getUserType()==null||singleDto.getUserType()==2){
						  if(singleDto.getCompanyContact()!=null){
							  singleDto.setContact(singleDto.getCompanyContact());
						  }
							singleDto.setUserMobile(singleDto.getCompanyMobile());	
					    
							 singleDto.setCompanyName(singleDto.getCompanyName()); 
								if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
									singleDto.setS_provinceIdString("全国");
									singleDto.setDistance(0.0);
								}
								
//								if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//									if ("市辖区".equals(singleDto.get S_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//										singleDto.setS_cityIdString("");
//									}else{
//										singleDto.setS_provinceIdString("");
//									}
//									
//								}
								if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
									singleDto.setF_provinceIdString("全国");
								}
//								if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//									if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//										singleDto.setF_cityIdString("");
//									}else{
//										singleDto.setF_provinceIdString("");
//									}
//									
//								}
								//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
								if (singleDto.getCreateUserId()!=null) {
									if(singleDto.getCreateUserId()!=null&&!"".equals(singleDto.getCreateUserId())){
										singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
									}
								}
								
					}else{
						//增加一个分支,查询联系人的认证状态,个人联系人的认证状态
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getUserId())));
					}
			 }

				}
		if (retList!=null && retList.size()>0) {
			for (MemberAddressDTO dto : retList) {
				dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
			}
		}	
		 
		 return retList;
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByCarLine(
			CarLineDTO carLineDTO)  throws Exception {
		return hessianCategoryService().getMemberAddressByCarLine(carLineDTO);
	}

	@Override
	public List<MemberAddressDTO> getMemberAddressByIdNst2(
			MemberAddressDTO memberAddressDTO) throws Exception {
		List<MemberAddressDTO> retList=new ArrayList<>();
		
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
	 		retList=hessianCategoryService().getMemberAddressByIdNst2(memberAddressDTO);
	 			//个人
			 for (MemberAddressDTO singleDto : retList) {
				  MemberBaseinfoDTO memberDTO = memberToolService
							.getById(singleDto.getCreateUserId() + "");
				  if(memberDTO==null){
					  singleDto.setIsDeleted("1");
					  continue;
				  }
				  if(memberDTO.getUserType()!=null && memberDTO.getUserType()==1 ){
					  singleDto.setContact(singleDto.getContact());
					  singleDto.setUserMobile(singleDto.getUserMobile());
					  if(singleDto.getUserId()==Long.parseLong(singleDto.getCreateUserId())){
						  singleDto.setCompanyName("");
					  }
				  }else{
					  if(singleDto.getCompanyContact()!=null){
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
				  }

					  
					  //singleDto.setCompanyName(singleDto.getCompanyName());
						if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
							singleDto.setS_provinceIdString("全国");
							//增加距离显示后,如果起始地是全国,则距离是0
							singleDto.setDistance(0.0);
						}
//						if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//							if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//								singleDto.setS_cityIdString("");
//							}else{
//								singleDto.setS_provinceIdString("");
//							}
//							
//						}
						if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
							singleDto.setF_provinceIdString("全国");
						}
//						if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//							if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//								singleDto.setF_cityIdString("");
//							}else{
//								singleDto.setF_provinceIdString("");
//							}
//							
//						}
						//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
			}
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
			 		retList=hessianCategoryService().getMemberAddressByIdNst2(memberAddressDTO);
			//个人
			 for (MemberAddressDTO singleDto : retList) {
				  
				  MemberBaseinfoDTO memberDTO = memberToolService
							.getById(singleDto.getCreateUserId() + "");
				  if(memberDTO.getUserType()!=null && memberDTO.getUserType()==1 ){
					  singleDto.setContact(singleDto.getContact());
					  singleDto.setUserMobile(singleDto.getUserMobile());
					  if(singleDto.getUserId()==Long.parseLong(singleDto.getCreateUserId())){
						  singleDto.setCompanyName("");
					  }
				  }else{
					  if(singleDto.getCompanyContact()!=null){
						  singleDto.setContact(singleDto.getCompanyContact());
					  }
					  singleDto.setUserMobile(singleDto.getCompanyMobile());
				  }

					  
					 // singleDto.setCompanyName(singleDto.getCompanyName());
					if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
						singleDto.setS_provinceIdString("全国");
						singleDto.setDistance(0.0);
					}
					
//					if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//						if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//							singleDto.setS_cityIdString("");
//						}else{
//							singleDto.setS_provinceIdString("");
//						}
//						
//					}
					if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
						singleDto.setF_provinceIdString("全国");
					}
//					if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//						if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//							singleDto.setF_cityIdString("");
//						}else{
//							singleDto.setF_provinceIdString("");
//						}
//						
//					}
					//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
					singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));

			}
		}
		else  if (memberAddressDTO.getUserType()==2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			//memberAddressDTO.setUserId(null);
			memberAddressDTO.setUserType(null);
			retList=hessianCategoryService().getMemberAddressByIdNst2(memberAddressDTO);
			 for (MemberAddressDTO singleDto : retList) {
				 //如果是企业,那么看情况,如果创建单据的人是个人,那么看到结果集的,就会,是个人创建的,联系人是 创建人,个人信息,如果是创建人事企业,则联系人企业联系人
				 
					if(singleDto.getUserType()==null||singleDto.getUserType()==2){
						  if(singleDto.getCompanyContact()!=null){
							  singleDto.setContact(singleDto.getCompanyContact());
						  }
							singleDto.setUserMobile(singleDto.getCompanyMobile());	
					    
							 singleDto.setCompanyName(singleDto.getCompanyName()); 
								if(singleDto.getS_areaId()!=null&&singleDto.getS_provinceId()!=null&&singleDto.getS_cityId()!=null&&singleDto.getS_areaId()==0&&singleDto.getS_provinceId()==0&&singleDto.getS_cityId()==0){
									singleDto.setS_provinceIdString("全国");
									singleDto.setDistance(0.0);
								}
								
//								if(singleDto.getS_cityId()!=null&&singleDto.getS_cityId()!=0){
//									if ("市辖区".equals(singleDto.getS_cityIdString()) || "县".equals(singleDto.getS_cityIdString())||"市".equals(singleDto.getS_cityIdString())){
//										singleDto.setS_cityIdString("");
//									}else{
//										singleDto.setS_provinceIdString("");
//									}
//									
//								}
								if(singleDto.getF_areaId()!=null&&singleDto.getF_provinceId()!=null&&singleDto.getF_cityId()!=null&&singleDto.getF_areaId()==0&&singleDto.getF_provinceId()==0&&singleDto.getF_cityId()==0){
									singleDto.setF_provinceIdString("全国");
								}
//								if(singleDto.getF_cityId()!=null&&singleDto.getF_cityId()!=0){
//									if ("市辖区".equals(singleDto.getF_cityIdString()) || "县".equals(singleDto.getF_cityIdString())||"市".equals(singleDto.getF_cityIdString())){
//										singleDto.setF_cityIdString("");
//									}else{
//										singleDto.setF_provinceIdString("");
//									}
//									
//								}
								//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
								singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
					}else{
						//增加一个分支,查询联系人的认证状态,个人联系人的认证状态
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getUserId())));
					}
			 }

				}
			
		if (retList!=null && retList.size()>0) {
			for (MemberAddressDTO dto : retList) {
				dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
			}
		}
		 return retList;
	}

	@Override
	public int getCountByConditionNst2(MemberAddressDTO memberAddressDTO)
			throws Exception {
        //List<MemberAddressDTO> retList=new ArrayList<>();
		if(memberAddressDTO.getUserType()==null){
	 		memberAddressDTO.setCreateUserId(null);
	 		memberAddressDTO.setUserId(null);
		}
		else if (memberAddressDTO.getUserType()==1) {
			 		memberAddressDTO.setCreateUserId(null);
		}
		else  if (memberAddressDTO.getUserType()==2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId())); 
			memberAddressDTO.setUserId(null);
			//memberAddressDTO.setUserType(null);


				}
			
		 
		 return 	 hessianCategoryService().getCountByConditionNst2(memberAddressDTO);
	}

	@Override
	public int checkCity(String mcity) throws Exception {
		return 	 hessianCategoryService().checkCity(mcity);
	}
	
	@Override
	public List<MemberAddressDTO> getMemberAddressByUserCondition(
			MemberAddressDTO memberAddressDTO) throws Exception {
		List<MemberAddressDTO> retList = new ArrayList<>();
		// 个人
		if (memberAddressDTO.getUserType() == 1) {
			retList = hessianCategoryService().getMemberAddressByUserCondition(memberAddressDTO);
			for (MemberAddressDTO singleDto : retList) {
				MemberBaseinfoDTO memberDTO = memberToolService.getById(singleDto.getCreateUserId() + "");
				if (memberDTO.getUserType() != null && memberDTO.getUserType() == 1
						&&singleDto.getUserId() == Long.parseLong(singleDto.getCreateUserId()) ) {
					//未被信息部分配，展示的货主的电话
					singleDto.setContact(singleDto.getContact());
					singleDto.setUserMobile(singleDto.getUserMobile());
				} else {
					//货源被分配后  货主看到的是信息部的电话和信息
					if (singleDto.getCompanyContact() != null) {
						singleDto.setContact(singleDto.getCompanyContact());
					}
					singleDto.setUserMobile(singleDto.getCompanyMobile());
				}
				if (singleDto.getS_areaId() != null && singleDto.getS_provinceId() != null
						&& singleDto.getS_cityId() != null && singleDto.getS_areaId() == 0
						&& singleDto.getS_provinceId() == 0 && singleDto.getS_cityId() == 0) {
					singleDto.setS_provinceIdString("全国");
					singleDto.setDistance(0.0);
				}
				if (singleDto.getF_areaId() != null && singleDto.getF_provinceId() != null
						&& singleDto.getF_cityId() != null && singleDto.getF_areaId() == 0
						&& singleDto.getF_provinceId() == 0 && singleDto.getF_cityId() == 0) {
					singleDto.setF_provinceIdString("全国");
				}
				//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
				if(singleDto.getCreateUserId()!=null&&!"".equals(singleDto.getCreateUserId())){
					singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
				}
			}
		}
		
		/* 如果是企业,那么看情况,如果创建单据的人是个人,那么看到结果集的,就会,是个人创建的,联系人是
		*  创建人,个人信息,如果是创建人事企业,则联系人企业联系人
		*/ 
		else if (memberAddressDTO.getUserType() == 2) {
			memberAddressDTO.setCreateUserId(String.valueOf(memberAddressDTO.getUserId()));
			retList = hessianCategoryService().getMemberAddressByUserCondition(memberAddressDTO);
			for (MemberAddressDTO singleDto : retList) {	
				if (singleDto.getUserType() == null || singleDto.getUserType() == 2) {	
					//货源被分配后  货主看到的是信息部的电话和信息
					if (singleDto.getCompanyContact() != null) {
						singleDto.setContact(singleDto.getCompanyContact());
					}
					singleDto.setUserMobile(singleDto.getCompanyMobile());
					if (singleDto.getS_areaId() != null && singleDto.getS_provinceId() != null
							&& singleDto.getS_cityId() != null && singleDto.getS_areaId() == 0
							&& singleDto.getS_provinceId() == 0 && singleDto.getS_cityId() == 0) {
						singleDto.setS_provinceIdString("全国");
						singleDto.setDistance(0.0);
					}

					if (singleDto.getF_areaId() != null && singleDto.getF_provinceId() != null
							&& singleDto.getF_cityId() != null && singleDto.getF_areaId() == 0
							&& singleDto.getF_provinceId() == 0 && singleDto.getF_cityId() == 0) {
						singleDto.setF_provinceIdString("全国");
					}
					//增加一个分支,查询联系人的认证状态,查询企业联系人的认证状态
					if(singleDto.getCreateUserId()!=null&&!"".equals(singleDto.getCreateUserId())){
						singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getCreateUserId())));
					}
				}else{
					//增加一个分支,查询联系人的认证状态,个人联系人的认证状态
					singleDto.setIsCertify(memberCertifiApiService.queryCertStatus(Long.valueOf(singleDto.getUserId())));
				}
			}
		}
		if (retList!=null && retList.size()>0) {
			for (MemberAddressDTO dto : retList) {
				dto.setGoodsTypeString(EGoodsType.getValueByCode(dto.getGoodsType()));
			}
		}
		return retList;
	}

	@Override
	public List<MemberAddressDTO> getCompanyToMb(
			MemberAddressDTO memberAddressDTO) throws Exception {
		return 	 hessianCategoryService().getCompanyToMb(memberAddressDTO);
	}

	@Override
	public MemberAddressDTO getCompanyToMbgetL(
			MemberAddressDTO memberAddressDTO) throws Exception {
		return hessianCategoryService().getCompanyToMbgetL(memberAddressDTO);
	}

	@Override
	public int getRule(String clients) throws Exception {
		return hessianCategoryService().getRule(clients);
	}

	@Override
	public void updateCreateUserId(String createUserId, String clients,Long id)
			throws Exception {
		 hessianCategoryService().updateCreateUserId(createUserId,clients,id);
		
	}

	@Override
	public void updatenstRule(Long id) throws Exception {
		 hessianCategoryService().updatenstRule(id);
		
	}

	@Override
	public Integer getMemberAddressByUserConditionCount(MemberAddressDTO memberAddressDTO) throws Exception {
		return hessianCategoryService().getMemberAddressByUserConditionCount(memberAddressDTO);
	}
	
	@Override
	public StatusCodeEnumWithInfo addMemberAddress(MemberAddressInputDTO inputDTO, boolean isUpdate)
			throws Exception {
		StatusCodeEnumWithInfo statusCode = new StatusCodeEnumWithInfo();
		
		if(null == inputDTO.getS_provinceId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.SEND_PROVINCE_IS_NULL);
			return statusCode;
		}
		
		if(null == inputDTO.getS_cityId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.SEND_CITY_IS_NULL);
			return statusCode;
		}
		
		if(null == inputDTO.getS_areaId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.SEND_AREA_IS_NULL);
			return statusCode;
		}
		
		if(null == inputDTO.getF_provinceId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.RECEIVE_PROVINCE_IS_NULL);
			return statusCode;
		}
		
		if(null == inputDTO.getF_cityId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.RECEIVE_CITY_IS_NULL);
			return statusCode;
		}
		
		if(null == inputDTO.getF_areaId()){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.RECEIVE_AREA_IS_NULL);
			return statusCode;
		}
		
		if(StringUtils.isBlank(inputDTO.getGoodsType())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.GOODS_TYPE_IS_NULL);
			return statusCode;
		}else{
			if(validateGoodsType(inputDTO.getGoodsType())){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.GOODS_TYPE_ERROR);
				return statusCode;
			}
		}
		
		if(StringUtils.isBlank(inputDTO.getTotalWeight())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.GOODS_WEIGHT_IS_NULL);
			return statusCode;
		}else{
			if(validateTotalWeight(inputDTO.getTotalWeight())){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.GOODS_WEIGHT_ERROR);
				return statusCode;
			}
		}
		
		if(StringUtils.isBlank(inputDTO.getContactName())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.CONTACT_NAME_IS_NULL);
			return statusCode;
		}
		
		if(inputDTO.getMemberId() == null){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_MEMBERID_IS_NULL);
			return statusCode;
		}
		
		if(StringUtils.isBlank(inputDTO.getClients())){
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ORDER_CLIENT_IS_NULL);
			return statusCode;
		}
		
		Long createUserId = inputDTO.getMemberId();
		MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
		MemberBaseinfoDTO memberDTO = memberToolService.getById(createUserId + "");
		// 判断用户是否存在
		if (null == memberDTO) {
			statusCode.setStatusCodeEnum(ErrorCodeEnum.ACCOUNT_NOT_EXISTED);
			return statusCode;
		} else {
			//更新用户userType为1
			memberDTO.setMemberId(createUserId);
			memberDTO.setUserType(1);
			memberToolService.updateMemberApp(memberDTO);
			// 存的当前的登录人的手机号码
			memberAddressDTO.setType(2);
			memberAddressDTO.setContactName(inputDTO.getContactName());
			memberAddressDTO.setTotalWeight(Double.parseDouble(inputDTO.getTotalWeight()));
			memberAddressDTO.setGoodsType(ParamsUtil.getIntFromString(inputDTO.getGoodsType(), null));
			memberAddressDTO.setF_areaId(inputDTO.getF_areaId());
			memberAddressDTO.setF_cityId(inputDTO.getF_cityId());
			memberAddressDTO.setF_provinceId(inputDTO.getF_provinceId());
			memberAddressDTO.setS_areaId(inputDTO.getS_areaId());
			memberAddressDTO.setS_cityId(inputDTO.getS_cityId());
			memberAddressDTO.setS_provinceId(inputDTO.getS_provinceId());
			memberAddressDTO.setCarType(ParamsUtil.getIntFromString(inputDTO.getCarType(), 9));
			memberAddressDTO.setRemark(inputDTO.getRemark());
			memberAddressDTO.setS_detailed_address(inputDTO.getS_detailed_address());
			memberAddressDTO.setF_detailed_address(inputDTO.getF_detailed_address());
			memberAddressDTO.setUserId(createUserId);
			memberAddressDTO.setUserType(1);
			memberAddressDTO.setCreateUserId(createUserId+"");
			memberAddressDTO.setClients(inputDTO.getClients()+"");
			memberAddressDTO.setUserMobile(memberDTO.getMobile());
			memberAddressDTO.setSendGoodsType(2);
			memberAddressDTO.setPriceUnitType(0);
			//校验体积
			if(validateTotalSize(inputDTO.getTotalSize())){
				memberAddressDTO.setTotalSize(ParamsUtil.getIntFromString(inputDTO.getTotalSize(),null));
			}else{
				statusCode.setStatusCodeEnum(ErrorCodeEnum.GOODS_SIZE_ERROR);
				return statusCode;
			}
			//校验装车时间
			ErrorCodeEnum validateResult = validateSendDate(inputDTO.getSendDate(), inputDTO.getSendDateType(), memberAddressDTO);
			if(ErrorCodeEnum.SUCCESS != validateResult){
				statusCode.setStatusCodeEnum(validateResult);
				return statusCode;
			}
			//校验价格
			validateResult = validatePrice(inputDTO.getPrice(), memberAddressDTO);
			if(ErrorCodeEnum.SUCCESS != validateResult){
				statusCode.setStatusCodeEnum(validateResult);
				return statusCode;
			}
		}
		Long memberAddressId = inputDTO.getMemberAddressId();
		if(isUpdate){
			memberAddressDTO.setId(inputDTO.getMemberAddressId());
			hessianCategoryService().updateMemberAddressDTO(memberAddressDTO);
		}else{
			memberAddressId = hessianCategoryService().addMemberAddress(memberAddressDTO);
			if(memberAddressId == null){
				statusCode.setStatusCodeEnum(ErrorCodeEnum.FAIL);
				return statusCode;
			}
			
			statusCode.setStatusCodeEnum(ErrorCodeEnum.SUCCESS);
			statusCode.setObj(memberAddressId);
		}
		
		inputDTO.setMemberAddressId(memberAddressId);
		//启动计算驾车里程线程
		new Thread(new CountCarMileageThread(inputDTO, areaToolService, hessianCategoryService())).start();
		//农速通个人分配规则
		memberAddressDTO.setId(memberAddressId);
		if(memberAddressDTO.getClients() != null
				&& !"".equals(memberAddressDTO.getClients())
				&& "2".equals(memberAddressDTO.getClients())
				&& memberAddressDTO.getUserType() == 1){
			if(getRule(memberAddressDTO.getClients()) == 1){
				new Thread(new RuleForMemberThread(memberAddressDTO, this)).start();
			}
			

		}
		//非农速通一手货源分配规则
		if(memberAddressDTO.getClients() !=null 
				&& !"".equals(memberAddressDTO.getClients())
				&& !"2".equals(memberAddressDTO.getClients())){
			if(getRule(memberAddressDTO.getClients()) == 1){
				new Thread(new RuleForMemberThread(memberAddressDTO, this)).start();
			}
		}
		return statusCode;
	}

	private boolean validateGoodsType(String goodsType) {
		Integer iGoodsType = ParamsUtil.getIntFromString(goodsType, null);
		if(iGoodsType == null){
			return true;
		}
		
		if(GoodsTypeEnum.getByKey(iGoodsType) == null){
			return true;
		}
		return false;
	}

	private boolean validateTotalSize(String totalSize) {
		if(StringUtils.isBlank(totalSize)){
			return true;
		}
		
		Pattern pattern = Pattern.compile("[0-9]+");   
		Matcher matcher = pattern.matcher(totalSize);   
		boolean result = matcher.matches(); 
		return result;
	}

	/**
	 * 校验重量
	 * 通过返回false
	 * @param totalWeight
	 * @return
	 */
	private boolean validateTotalWeight(String totalWeight) {
		Pattern p = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
		Matcher m = p.matcher(totalWeight);
		if (!m.matches()) {
			double weight = Double.parseDouble(totalWeight);
			return weight >= 0.1;
		}
		return false;
	}

	/**
	 * 校验价格
	 * 通过返回 "OK"
	 * @param price
	 * @param memberAddressDTO
	 * @return
	 */
	private ErrorCodeEnum validatePrice(String price,
			MemberAddressDTO memberAddressDTO) {
		if (StringUtils.isBlank(price)) {
			memberAddressDTO.setPrice(0.00);
		} else {
			Pattern p = Pattern.compile("^[0-9]+(.[0-9]{2})?$");
			Pattern p2 = Pattern.compile("^[1-9]\\d*$ ");
			Pattern p3 = Pattern.compile("^[0-9]+(.[0-9]{1})?$");
			Matcher m = p.matcher(price);
			Matcher m2 = p2.matcher(price);
			Matcher m3 = p3.matcher(price);
			if (!m.matches()) {
				if (!m3.matches()) {
					if (!m2.matches()) {
						return ErrorCodeEnum.GOODS_PRICE_ERROR;
					}
				}
			}

			Double dPrice = Double.parseDouble(price);
			if (dPrice.doubleValue() > 10000000) {
				return ErrorCodeEnum.GOODS_PRICE_OVER_MAX;
			}
			
			memberAddressDTO.setPrice(dPrice);
		}
		return ErrorCodeEnum.SUCCESS;
	}

	/**
	 * 检验装车时间
	 * 通过返回 "OK"
	 * @param sendDate
	 * @param sendDateType 
	 * @param memberAddressDTO
	 * @return
	 * @throws ParseException 
	 */
	private ErrorCodeEnum validateSendDate(String sendDate,
			String sSendDateType, MemberAddressDTO memberAddressDTO) throws ParseException {
		//如果为空则默认不限4
		Integer sendDateType = ParamsUtil.getIntFromString(sSendDateType, 4);
		memberAddressDTO.setSendDateType(sendDateType);
		
		if(StringUtils.isNotBlank(sendDate)){
			Date currentDate = new Date();
			String currentDateString = DateUtil.getDate(currentDate, DateUtil.DATE_FORMAT_DATEONLY);
			Date dSendDate = DateUtil.parseDate(sendDate, DateUtil.DATE_FORMAT_DATEONLY);
			currentDate = DateUtil.formateDate(currentDateString, DateUtil.DATE_FORMAT_DATEONLY);
			
			if(dSendDate.getTime() < currentDate.getTime()) {
				return ErrorCodeEnum.SEND_DATE_ERROR;
			}else{
				//发车时间类别(0:上午,1:中午,2:下午,3:晚上,4:无限)
				if (sendDateType != 4 && dSendDate.getTime() == currentDate.getTime()) {
					int hour = currentDate.getHours();
					if (sendDateType == 0) {
						if (hour > 12) {
							return ErrorCodeEnum.SEND_DATE_ERROR_MORNING;
						}

					}else if (sendDateType == 1) {
						if (hour > 14) {
							return ErrorCodeEnum.SEND_DATE_ERROR_NOON;
						}
					}else if (sendDateType == 2) {
						if (hour > 18) {
							return ErrorCodeEnum.SEND_DATE_ERROR_AFTERNOON;
						}
					}
				}
				memberAddressDTO.setSendDate(dSendDate);
				memberAddressDTO.setSendDateString(sendDate);
			}
		}
		return ErrorCodeEnum.SUCCESS;
	}
}
