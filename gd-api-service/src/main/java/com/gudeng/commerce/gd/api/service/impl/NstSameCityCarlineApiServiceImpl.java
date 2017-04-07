package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.NstSameCityCarlineApiService;
import com.gudeng.commerce.gd.api.util.DateTimeUtils;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.EGoodsType;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.commerce.gd.customer.dto.NstSameCityCarlineEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushNstMessageInfoDTO;
import com.gudeng.commerce.gd.customer.service.NstSameCityCarlineService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.UMengPushMessage;
/**
 * 同城发布线路
 * @author sunl
 *
 */
@Service
@SuppressWarnings("unchecked")
public class NstSameCityCarlineApiServiceImpl implements NstSameCityCarlineApiService {

	private static final GdLogger logger = GdLoggerFactory.getLogger(CarLineApiServiceImpl.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	private static NstSameCityCarlineService nstSameCityCarlineService;
	/**
	 * 最大推送线路信息数
	 */
	private static final int MAX_MESSAGE = 5;
	
	protected NstSameCityCarlineService getHessianMemberService() throws MalformedURLException {
		String url = gdProperties.getNstSameCityCarlineService();
		if(nstSameCityCarlineService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			nstSameCityCarlineService=(NstSameCityCarlineService)factory.create(NstSameCityCarlineService.class,url);
		}
		return nstSameCityCarlineService;
	}
	
	@Override
	public NstSameCityCarlineEntityDTO getById(String id) throws Exception {
		return getHessianMemberService().getById(id);
	}

	@Override
	public NstSameCityCarlineEntityDTO getByDto(NstSameCityCarlineEntityDTO dto)throws Exception{
		return getHessianMemberService().getByDto(dto);
	}
	
	
	@Override
	public List<NstSameCityCarlineEntityDTO> queryCarsByUserId(String userId) throws Exception {
		return getHessianMemberService().queryCarsByUserId(userId);
	}

	@Override
	public int addNstSameCityCarline(NstSameCityCarlineEntityDTO dto)throws Exception {
		return getHessianMemberService().addNstSameCityCarline(dto);
	}

	@Override
	public int updateNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return getHessianMemberService().updateNstSameCityCarLineDTO(dto);
	}
	
	@Override
	public int deleteNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return getHessianMemberService().deleteNstSameCityCarLineDTO(dto);
	}
	
	@Override
	public int queryNstSameCityCarLineCount(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return getHessianMemberService().queryNstSameCityCarLineCount(dto);
	}
	
	
	@Override
	public List<NstSameCityCarlineEntityDTO> queryNstSameCityCarLineList(
			NstSameCityCarlineEntityDTO dto) throws Exception {
		List<NstSameCityCarlineEntityDTO> listByPage=getHessianMemberService().queryNstSameCityCarLineList(dto);
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");//头像地址
		//时间差的显示
		if (listByPage.size() > 0) {
			for (NstSameCityCarlineEntityDTO nstSameCityCarlineEntityDTO : listByPage) {
				if(nstSameCityCarlineEntityDTO.getUpdateTime() !=null){
					nstSameCityCarlineEntityDTO.setDayNum(DateTimeUtils.getTimeBetween(nstSameCityCarlineEntityDTO.getUpdateTime()));
				}
				nstSameCityCarlineEntityDTO.setAndupurl(imageHost+nstSameCityCarlineEntityDTO.getAndupurl());
			}
		}
		return listByPage;
	}

	@Override
	public NstSameCityCarlineEntityDTO getCityId(String cityName)
			throws Exception {
		return getHessianMemberService().getCityId(cityName);
	}

	@Override
	public NstSameCityCarlineEntityDTO getCityName(int cityId) 
			throws Exception {
		return getHessianMemberService().getCityName(cityId);
	}
	
	@Override
	public int queryMyCitylineCount(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return getHessianMemberService().queryMyCitylineCount(dto);
	}

	
	@Override
	public List<NstSameCityCarlineEntityDTO> queryMyCitylineList(
			NstSameCityCarlineEntityDTO dto) throws Exception {
		return getHessianMemberService().queryMyCitylineList(dto);
	}


	@Override
	public List<NstSameCityAddressDTO> queryCityCarGoodslineList(
			NstSameCityCarlineEntityDTO dto) throws Exception {
		return getHessianMemberService().queryCityCarGoodslineList(dto);
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryCityCarGoodsMatchList(
			NstSameCityAddressDTO dto) throws Exception {
		return getHessianMemberService().queryCityCarGoodsMatchList(dto);
	}

	@Override
	public List<NstSameCityAddressDTO> excutePushCity(
			MemberBaseinfoDTO memberDTO,
			NstSameCityCarlineEntityDTO dto,
			NstSameCityCarlineApiService nstSameCityCarlineApiService) throws Exception {
		
		//返回list
		List<NstSameCityAddressDTO> returnList=new ArrayList<>();
		//第一次查询线路信息 list
		List<NstSameCityAddressDTO> list=nstSameCityCarlineApiService.queryCityCarGoodslineList(dto);
		//第二次查询的list2
		List<NstSameCityAddressDTO> list2=new ArrayList<>();
		// 当第一次条件获取线路信息数为MAX_MESSAGE时直接插入推送信息
		if (list != null && list.size() == MAX_MESSAGE) {
			nstSameCityCarlineApiService.insertGoodSNstpushMessage(dto, list);
			//加入返回集合中
			returnList.addAll(list);
			
			// 当第一次条件获取线路信息数<MAX_MESSAGE >0时,去除车类型筛选条件 第二次进行推送线路信息的查询
		}else if (list != null && list.size() < MAX_MESSAGE && list.size() > 0) {
			dto.setCarType(null);
			//第二次根据条件查询
			list2 = nstSameCityCarlineApiService.queryCityCarGoodslineList(dto);
			List<NstSameCityAddressDTO> list3 = new ArrayList<>();
			//首先将第一次查询list的放入list3中
			list3.addAll(list);
			boolean flag = false;
			//遍历第二次查询
			for (NstSameCityAddressDTO nstSameCityAddressDTO : list2){
				if (flag) {
					break;
				}
				//遍历第一次查询
				for (NstSameCityAddressDTO cityAddressDTO : list){
					//两次线路相比较 相同就跳出
					if (nstSameCityAddressDTO.getId().equals(cityAddressDTO.getId())){
						break;
					}
					
					//循环加入第二次查询
					list3.add(nstSameCityAddressDTO);
					// 当推荐线路信息==MAX_MESSAGE 停止循环
					if (list3.size() == MAX_MESSAGE) {
						flag = true;
						break;
					}
					
				}
			}
			
		  //插入推送数据到表里
			nstSameCityCarlineApiService.insertGoodSNstpushMessage(dto, list3);
		  //加入返回集合中
		   returnList.addAll(list3);
		  // 当第一次条件获取线路信息数为0时，直接将第二次查询的线路信息插入
		} else if (list != null && list.size() == 0) {
			dto.setCarType(null);
			list2 = nstSameCityCarlineApiService.queryCityCarGoodslineList(dto);
			list.addAll(list2);
			//当第二次查询的线路信息不为0 ，执行操作
			if (list.size() != 0) {
				nstSameCityCarlineApiService.insertGoodSNstpushMessage(dto, list);
				//加入返回集合中
				returnList.addAll(list);
			}
			
		}
		
		if (returnList!=null && returnList.size()!=0) {
			UMengPushMessage pushMessage2 = new UMengPushMessage();
			GdMessageDTO gdMessage2 = new GdMessageDTO();
			gdMessage2.setSendApp("2");
			gdMessage2.setSendType("1");
			gdMessage2.setTicket("【农速通为您推送货源信息】");
			gdMessage2.setTitle("农速通为您推货源信息");
			gdMessage2.setContent("根据你发布的线路信息,我们为你推荐了货源信息,请查收");
			gdMessage2.setAfter_open("go_app");
			gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
			gdMessage2.setProduction_mode(true);
			pushMessage2.pushMessage(gdMessage2);
		}
		return returnList;
	}

	@Override
	public void insertGoodSNstpushMessage(NstSameCityCarlineEntityDTO dto,
			List<NstSameCityAddressDTO> list) throws Exception {
		 getHessianMemberService().insertGoodSNstpushMessage(dto, list);
	}

	@Override
	public void insertLineNstpushMessage(NstSameCityAddressDTO dto,
			List<NstSameCityCarlineEntityDTO> list) throws Exception {
		getHessianMemberService().insertLineNstpushMessage(dto, list);
		
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> excutePushLine(
			MemberBaseinfoDTO memberDTO, NstSameCityAddressDTO dto,
			NstSameCityCarlineApiService nstSameCityCarlineApiService)
			throws Exception {
		
		        //返回list
				List<NstSameCityCarlineEntityDTO> returnList=new ArrayList<>();
				//第一次查询线路信息 list
				List<NstSameCityCarlineEntityDTO> list=nstSameCityCarlineApiService.queryCityCarGoodsMatchList(dto);
				//第二次查询的list2
				List<NstSameCityCarlineEntityDTO> list2=new ArrayList<>();
				// 当第一次条件获取线路信息数为MAX_MESSAGE时直接插入推送信息
				if (list != null && list.size() == MAX_MESSAGE) {
					nstSameCityCarlineApiService.insertLineNstpushMessage(dto, list);
					//加入返回集合中
					returnList.addAll(list);
					
					// 当第一次条件获取线路信息数<MAX_MESSAGE >0时,去除车类型筛选条件 第二次进行推送线路信息的查询
				}else if (list != null && list.size() < MAX_MESSAGE && list.size() > 0) {
					dto.setNeedCarType(null);
					//第二次根据条件查询
					list2 = nstSameCityCarlineApiService.queryCityCarGoodsMatchList(dto);
					List<NstSameCityCarlineEntityDTO> list3 = new ArrayList<>();
					//首先将第一次查询list的放入list3中
					list3.addAll(list);
					boolean flag = false;
					//遍历第二次查询
					for (NstSameCityCarlineEntityDTO nstSameCityAddressDTO : list2){
						if (flag) {
							break;
						}
						//遍历第一次查询
						for (NstSameCityCarlineEntityDTO cityAddressDTO : list){
							//两次线路相比较 相同就跳出
							if (nstSameCityAddressDTO.getId().equals(cityAddressDTO.getId())){
								break;
							}
							
							//循环加入第二次查询
							list3.add(nstSameCityAddressDTO);
							// 当推荐线路信息==MAX_MESSAGE 停止循环
							if (list3.size() == MAX_MESSAGE) {
								flag = true;
								break;
							}
							
						}
					}
					
				  //插入推送数据到表里
				  nstSameCityCarlineApiService.insertLineNstpushMessage(dto, list3);
				  //加入返回集合中
				   returnList.addAll(list3);
				  // 当第一次条件获取线路信息数为0时，直接将第二次查询的线路信息插入
				} else if (list != null && list.size() == 0) {
					dto.setNeedCarType(null);
					list2 = nstSameCityCarlineApiService.queryCityCarGoodsMatchList(dto);
					list.addAll(list2);
					//当第二次查询的线路信息不为0 ，执行操作
					if (list.size() != 0) {
						nstSameCityCarlineApiService.insertLineNstpushMessage(dto, list);
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
					gdMessage2.setTitle("农速通为您推线路信息");
					gdMessage2.setContent("根据你发布的货源信息,我们为你推荐了线路信息,请查收");
					gdMessage2.setAfter_open("go_app");
					gdMessage2.setDevice_tokens(memberDTO.getDevice_tokens());
					gdMessage2.setProduction_mode(true);
					pushMessage2.pushMessage(gdMessage2);
				}
				return returnList;
	}

	@Override
	public List<NstSameCityAddressDTO> queryCityGoodsListDetail(
			Map<String, Object> p) throws Exception {
		List<NstSameCityAddressDTO> listByPage = getHessianMemberService().queryCityGoodsListDetail(p);
		//时间差的显示
		if (listByPage.size() != 0) {
			for (NstSameCityAddressDTO dto : listByPage) {
				if (dto.getReleaseTime() != null) {
					dto.setTimeDiffString(DateTimeUtils.getTimeBetween(dto.getReleaseTime()));
				}
				if (dto.getGoodsType()!=null) {
					dto.setGoodsTypeString(EGoodsType.getValueByCode(Integer.parseInt(dto.getGoodsType()+"")));
				}
				//前台表示已经过期
				if (DateUtil.daysBetween(dto.getCreateTime(),new Date())>=6) {
					dto.setIsDeleted((byte)1);
				}
			}
	    }
		return listByPage;
	}

	@Override
	public List<NstSameCityCarlineEntityDTO> queryCityLineListDetail(
			Map<String, Object> p) throws Exception {
		List<NstSameCityCarlineEntityDTO> listByPage =getHessianMemberService().queryCityLineListDetail(p);
		//时间差的显示
		if (listByPage.size() > 0) {
			for (NstSameCityCarlineEntityDTO nstSameCityCarlineEntityDTO : listByPage) {
				if(nstSameCityCarlineEntityDTO.getUpdateTime() !=null){
					nstSameCityCarlineEntityDTO.setDayNum(DateTimeUtils.getTimeBetween(nstSameCityCarlineEntityDTO.getUpdateTime()));
				}
			}
		}
		return listByPage;
	}

	@Override
	public Long queryLastNstSameCityCarLineDTO(NstSameCityCarlineEntityDTO dto)
			throws Exception {
		return getHessianMemberService().queryLastNstSameCityCarLineDTO(dto);
	}

	@Override
	public NstSameCityCarlineEntityDTO queryNstpushMessageById(Long id) 
			throws Exception {
		return getHessianMemberService().queryNstpushMessageById(id);
	}

	@Override
	public List<PushNstMessageInfoDTO> getCarLinesByClId(Long id)
			throws Exception {
		return getHessianMemberService().getCarLinesByClId(id);
	}
}
