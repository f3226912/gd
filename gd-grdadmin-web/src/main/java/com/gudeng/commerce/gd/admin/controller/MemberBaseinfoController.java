package com.gudeng.commerce.gd.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.MemberQueryBean;
import com.gudeng.commerce.gd.admin.dto.TransferListDTO;
import com.gudeng.commerce.gd.admin.service.AccBankCardInfoTooService;
import com.gudeng.commerce.gd.admin.service.AccInfoToolService;
import com.gudeng.commerce.gd.admin.service.AreaToolService;
import com.gudeng.commerce.gd.admin.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.PosBankCardToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JavaMd5;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.admin.util.ZipUtil;
import com.gudeng.commerce.gd.admin.enums.NsyUserTypeEnum;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("member")
public class MemberBaseinfoController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberBaseinfoController.class);

	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;

	@Autowired
	public MemberCertifiToolService memberCertifiToolService;

	@Autowired
	public BusinessBaseinfoToolService businessBaseinfoToolService;

	@Autowired
	public ReBusinessMarketToolService reBusinessMarketToolService;

	@Autowired
	private  MarketManageService marketManageService;

	@Autowired
	private SysRegisterUserAdminService sysRegisterUserAdminService;
	@Autowired
	private AccInfoToolService accInfoToolService;

	@Autowired
	private OrderBaseinfoToolService orderBaseinfoToolService;

	@Autowired
	private AccBankCardInfoTooService accBankCardInfoTooService;

	@Autowired
	public AreaToolService areaToolService;

	@Autowired
	private PosBankCardToolService posBankCardToolService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String member(HttpServletRequest request){
		//市场列表
		try{
			List<MarketDTO> list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "member/memberBaseinfo_list";
	}


	/**
	 * 默认查询和id条件查询结合
	 *
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			String id=request.getParameter("id");

			Map<String, Object> map = new HashMap<String, Object>();
			if(null==id || id.equals("")){
			}else{
				map.put("id", id);
			}
			// 设置查询参数
			//记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberBaseinfoDTO> list1 = memberBaseinfoToolService.getBySearch(map);
			List<MemberBaseinfoDTO> list = new ArrayList<MemberBaseinfoDTO>();
			for(MemberBaseinfoDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel()==4){
					mdt.setMarketName("");
				}
				String createUserId = mdt.getCreateUserId();
				if(StringUtils.isNotBlank(createUserId)){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
					if(createUser != null){
						mdt.setCreateUserName(createUser.getUserName());
					}
				}
				list.add(mdt);
			}
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//
//
//	/**
//	 * 根据id查询
//	 *
//	 * @param id
//	 * @param request
//	 * @return
//	 */


	@RequestMapping(value="querybyid/{memberId}")
	@ResponseBody
    public String queryById(@PathVariable("memberId") String memberId, HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==memberId || memberId.equals("")){
			}else{
				map.put("memberId", memberId);
			}
			// 设置查询参数
			//记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId);
			map.put("memberBaseinfoDTO", memberBaseinfoDTO);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 启用&&禁用
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updatestatus/{memberId}-{status}" )
	@ResponseBody
    public String updateStatus(@PathVariable("memberId") String memberId,@PathVariable("status") String status, HttpServletRequest request){
		try {
//			MemberBaseinfoDTO mb=memberBaseinfoToolService.getById(certifiId);
//			mb.setStatus(status);
			MemberBaseinfoDTO mb=new MemberBaseinfoDTO();
			mb.setStatus(status);
			mb.setMemberId(Long.valueOf(memberId));
			int i=memberBaseinfoToolService.updateStatus(mb);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 设置补贴是否显示
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateSubShow/{memberId}-{subShow}" )
	@ResponseBody
    public String updateSubShow(@PathVariable("memberId") Long memberId,@PathVariable("subShow") Integer subShow, HttpServletRequest request){
		try {
			MemberBaseinfoDTO mb=new MemberBaseinfoDTO();
			mb.setSubShow(subShow);
			mb.setMemberId(Long.valueOf(memberId));
			int i=memberBaseinfoToolService.updateSubShow(mb);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 根据ID进行删除操作
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletebyid" )
	@ResponseBody
    public String deleteById(@RequestParam String id, HttpServletRequest request){
		try {
			if(null==id || id.equals("")){
			return "faild";
			}
			int i=memberBaseinfoToolService.deleteById(id);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 增加修改同一个页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		return "member/memberBaseinfo_edit";
	}



	/**
	 * 	  增加修改同一个方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
    public String saveOrUpdate(MemberBaseinfoDTO dto, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			dto.setIp(regUser.getUserIP());
			Map<String, String> map=new HashMap<String, String>();

			String account=request.getParameter("account");
			String mobile=request.getParameter("mobile");
			if(null == mobile || StringUtil.isEmpty(mobile)){
				return "mobile";//手机号为空
			}
			Pattern pattern = Pattern.compile("^[1][34587][0-9]{9}$");
			Matcher matcher = pattern.matcher(mobile);

			if(!matcher.matches()){//手机不符合规则
				return "mobileE";
			}


			String cityName=dto.getCityName();
			if(cityName!=null && !cityName.trim().equals("")){
				Area area=areaToolService.getByAreaName(cityName);
				if(area!=null && area.getAreaID()!=null && !area.getAreaID().equals("")){
					dto.setCcityId(Long.valueOf(area.getAreaID()));
				}else{
					return "cCityName";
				}
			}
			if(dto.getUserType()!=null && dto.getUserType()==2 && (dto.getCompanyName()==null ||dto.getCompanyName().trim().equals(""))){
				return "companyName";
			}
			if(dto.getRealName()==null ||dto.getRealName().trim().equals("")){
				return "realName";
			}
			if(dto.getRealName().length()>14){
				return "toLong";
			}

			String memberId=request.getParameter("memberId");
//			管理后台中新增用户的密码，在此加密后转大写，没有必要再jsp或者js中加密。
			String password=request.getParameter("password");
			if(password!=null && !password.equals("")){
				dto.setPassword(JavaMd5.getMD5(dto.getPassword()+"gudeng2015@*&^-").toUpperCase());
			}
			if(null!=memberId && !memberId.equals("")){
				map.put("memberId", memberId);
				dto.setMemberId(Long.valueOf(memberId));
				dto.setUpdateTime_string(DateUtil.getSysDateString());
				dto.setUpdateUserId(regUser.getUserID());//用户ID，需要规划怎么填写
			}else{
				dto.setCreateTime_string(DateUtil.getSysDateString());
				dto.setCreateUserId(regUser.getUserID());//用户ID，需要规划怎么填写
			}
			dto.setNickName(request.getParameter("nickName"));
			dto.setMobile(request.getParameter("mobile"));
			dto.setBirthday_string(request.getParameter("birthday"));
			String nsyUserType =request.getParameter("nsyUserType");
			
			if(StringUtil.isNotEmpty(nsyUserType)){
				dto.setNsyUserType(Integer.valueOf(nsyUserType));
			}
			
			int i=0;
			Long l = 0L;
			if(null!=memberId && !memberId.equals("")){//根据id判断是否存在，存在即为更新，不存在即为增加
				MemberBaseinfoDTO exist1 =memberBaseinfoToolService.getByAccount(mobile);
				MemberBaseinfoDTO exist2 =memberBaseinfoToolService.getByMobile(mobile);

				System.out.println( ( null !=exist2 &&  Long.valueOf(memberId).longValue()!=exist2.getMemberId().longValue() )+"........................................");

				if(null != exist1 && Long.valueOf(memberId).longValue()!=exist1.getMemberId().longValue()  || null !=exist2 && Long.valueOf(memberId).longValue()!=exist2.getMemberId().longValue() ){
					return "existM";
				}
				i=memberBaseinfoToolService.updateMemberBaseinfoDTO(dto);

			}else{
				MemberBaseinfoDTO exist  =memberBaseinfoToolService.getByAccount(account);
				MemberBaseinfoDTO exist1 =memberBaseinfoToolService.getByAccount(mobile);
				MemberBaseinfoDTO exist2 =memberBaseinfoToolService.getByMobile(mobile);
				if(  null !=exist || null != exist1 || null != exist2  ){//新增时，手机和账号，在mobile 和account 都不能存在
					return "exist";
				}

				MemberBaseinfoEntity me=new MemberBaseinfoEntity();
				me.setAccount(dto.getAccount());
				me.setNsyUserType(dto.getNsyUserType());
				me.setMobile(dto.getMobile());
				me.setCreateTime(new Date());
				me.setRealName(dto.getRealName());
				me.setNickName(dto.getNickName());
				me.setPassword(dto.getPassword());
				me.setLevel(dto.getLevel());
				me.setStatus(dto.getStatus());
				me.setProvinceId(dto.getProvinceId());
				me.setCityId(dto.getCityId());
				me.setAreaId(dto.getAreaId());
				me.setAddress(dto.getAddress());
				me.setCreateUserId(getUser(request).getUserID());
//				0表示管理后台创建的用户，1表示web前端注册的用户，2表示农速通注册的用户，3表示农商友-买家版注册的用户，4表示农商友-卖家版注册的用户，5表示门岗添加的用户
				me.setRegetype("0");
				l=memberBaseinfoToolService.addMemberBaseinfoEnt(me);
				dto.setMemberId(l);
			}


			if(i>0 || l>0){
//				if(null !=dto.getLevel() && dto.getLevel()==3){
					//2015年10月16日 管理后台增加小商户时，无需进行审核，直接通过认证，即往certifi表中，写入已经认证的记录，不必考虑认证资料
					MemberCertifiDTO mc=new MemberCertifiDTO();
					mc.setMemberId(i>0?dto.getMemberId():l);
					mc.setStatus(dto.getIsAuth());
//					if(dto.getUserType()!=null && dto.getLevel()==2  && i>0 ){
//						if(dto.getLevel()!=null && dto.getLevel()==2 && dto.getUserType()!=null && dto.getUserType()==2 && i>0 ){
						//修改农速通用户的时候，修改企业的mobile,企业的名称，联系人姓名


						mc.setMobile(mobile);
						mc.setCompanyName(dto.getCompanyName());
						mc.setLinkMan(dto.getRealName());
//					}
//					Map<String, Number> q=new HashMap<String, Number>();
//					q.put("memberId", mc.getMemberId());
//					q.put("startRow",0);
//					q.put("endRow",10);
//					//判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
//					List<MemberCertifiDTO> list=memberCertifiToolService.getBySearch(q);
					MemberCertifiDTO memberCertifiDTO=memberCertifiToolService.getByUserId(mc.getMemberId()+"");
					if (!"-2".equals(dto.getIsAuth())) {
						if(null!=memberCertifiDTO ){
							mc.setCertifiId(memberCertifiDTO.getCertifiId());
							if ("-1".equals(dto.getIsAuth())) {
								memberCertifiToolService.deleteById(memberCertifiDTO.getCertifiId().toString());
							}else {
								memberCertifiToolService.updateMemberCertifiDTO(mc);
							}

						}else{
							String nowTimeString=DateUtil.toString(new Date(),DateUtil.DATE_FORMAT_DATETIME);
							mc.setType("1");
							mc.setCommitTime_string(nowTimeString);
//							mc.setCertificationTime_string(nowTimeString);
							mc.setCreateTime_string(nowTimeString);
							memberCertifiToolService.addMemberCertifiDTO(mc);
						}
					}else if(mc.getMobile()!=null && !mc.getMobile().trim().equals("")){
						//农速通用户，不选择认证状态的时候,更新个人(member)的手机号码到企业（certifi)手机号中
						if(null!=memberCertifiDTO){
							mc.setCertifiId(memberCertifiDTO.getCertifiId());
							memberCertifiToolService.updateMemberCertifiDTO(mc);
						}else{
							MemberCertifiDTO mcd=new MemberCertifiDTO();
							mcd.setType(dto.getUserType()+"");
							mcd.setCompanyName(dto.getCompanyName());
							mcd.setLinkMan(dto.getRealName());
							mcd.setMobile(dto.getMobile());
							memberCertifiToolService.addMemberCertifiDTO(mc);
						}

					}
					String shopName=(String)request.getParameter("shopName");
					String marketId=(String)request.getParameter("marketId");
					if(null !=dto.getLevel() && dto.getLevel()==4){//产地供应商，默认写入市场为3
						marketId="3";
					}
					if(shopName!=null && !shopName.equals("") && marketId!=null && !marketId.equals("")){
						BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();

						bb.setUserId(Long.valueOf(dto.getMemberId()));
						bb.setShopsName(request.getParameter("shopName"));
						bb.setCreateTime(new Date());

						//判断是否已经有商铺，没有则直接新增。仅仅设定一个店名，和当前用户ID。
						BusinessBaseinfoDTO bDto=businessBaseinfoToolService.getByUserId(String.valueOf(dto.getMemberId()));

						if(null!=bDto  ){
							reBusinessMarketToolService.deleteByBusinessId(bDto.getBusinessId());
							ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
							rbm.setBusinessId(bDto.getBusinessId());
							rbm.setMarketId(Long.valueOf(marketId));
							reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
						}else{
							Long lo=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
							ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
							rbm.setBusinessId(lo);
							rbm.setMarketId(Long.valueOf(marketId));
							reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
						}
					}



//				}
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 打开编辑页
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit" )
	@ResponseBody
    public String edit( HttpServletRequest request) {
		try {
			String memberId=request.getParameter("memberId");
			MemberBaseinfoDTO dto=memberBaseinfoToolService.getById(memberId);
			putModel("dto",dto);

			return "member/memberBaseinfo_edit";
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 打开编辑页
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editbyid/{memberId}")
    public String editbyid(@PathVariable("memberId") String memberId, HttpServletRequest request){
		try {
			MemberBaseinfoDTO dto=memberBaseinfoToolService.getById(memberId);
			if(dto.getCcityId()!=null){
				Area area=areaToolService.getArea(String.valueOf(dto.getCcityId()));
				dto.setCityName(area.getArea());
			}
			BusinessBaseinfoDTO bdto=businessBaseinfoToolService.getByUserId(memberId);
			if(null != bdto && null !=bdto.getMarketId()){
				MarketDTO marketDTO=marketManageService.getById(bdto.getMarketId());
				putModel("mdto",marketDTO);
			}

			putModel("bdto",bdto);

			putModel("dto",dto);

			/** 获取钱包信息*/
			putModel("memberId", memberId);

			AccInfoDTO accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));

			putModel("accInfoDTO",accInfoDTO);

			return "member/memberBaseinfo_edit";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 打开show页面
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showbyid/{memberId}")
    public String showbyid(@PathVariable("memberId") String memberId, HttpServletRequest request){
		try {

			/** 获取会员信息*/
			MemberBaseinfoDTO dto=memberBaseinfoToolService.getById(memberId);

			BusinessBaseinfoDTO bdto=businessBaseinfoToolService.getByUserId(memberId);
			if(null != bdto && null !=bdto.getMarketId()){
				MarketDTO marketDTO=marketManageService.getById(bdto.getMarketId());
				putModel("mdto",marketDTO);
			}

			putModel("bdto",bdto);

			putModel("dto",dto);

			/** 获取钱包信息*/
			putModel("memberId", memberId);

			AccInfoDTO accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));

			putModel("accInfoDTO",accInfoDTO);

			//显示银行卡信息
			//List<AccBankCardInfoEntity> cardInfoList = accBankCardInfoTooService.getCardInfoById(Long.parseLong(memberId));
			//putModel("cardInfoList",cardInfoList);
			return "member/memberBaseinfo_show";

		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 打开member_show页面
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showMemberById/{memberId}")
    public String showMemberById(@PathVariable("memberId") String memberId, HttpServletRequest request){
		try {

			/** 获取会员信息*/
			MemberBaseinfoDTO dto=memberBaseinfoToolService.getById(memberId);

			BusinessBaseinfoDTO bdto=businessBaseinfoToolService.getByUserId(memberId);
			if(null != bdto && null !=bdto.getMarketId()){
				MarketDTO marketDTO=marketManageService.getById(bdto.getMarketId());
				putModel("mdto",marketDTO);
			}

			putModel("bdto",bdto);

			putModel("dto",dto);

			/** 获取钱包信息*/
//			putModel("memberId", memberId);

//			AccInfoDTO accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));
//
//			putModel("accInfoDTO",accInfoDTO);

			return "member/memberBaseinfo_member_show";

		} catch (Exception e) {

		}
		return null;
	}
	@RequestMapping(value="queryCarNo" )
	@ResponseBody
	public String queryCarNo(HttpServletRequest request){
		String memberId =request.getParameter("memberId");
		Map<String, Object> map =new HashMap<String, Object>();
		if(StringUtils.isNotBlank(memberId)){
			String regetype =request.getParameter("regetype");
			List<AccBankCardInfoEntity> cardInfoList = null;
			try {
				if(MemberBaseinfoEnum.REGETYPE_7.getKey().equals(regetype)){
					//来源是pos刷卡的用户只有一个付款卡号
					PosBankCardEntity posBankCardEntity = posBankCardToolService.getByMemberId(memberId);
					if(posBankCardEntity != null){
						cardInfoList = new ArrayList<AccBankCardInfoEntity>();
						AccBankCardInfoEntity accBankCardInfo = new AccBankCardInfoEntity();
						accBankCardInfo.setBankCardNo(posBankCardEntity.getBankCardNo());
						cardInfoList.add(accBankCardInfo);
					}
				} else {
					cardInfoList = accBankCardInfoTooService.getCardInfoById(Long.parseLong(memberId));
				}
				map.put("rows", cardInfoList);
			} catch (Exception e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		String json =JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		return json;
	}



	/**
	 * 根据多个条件查询
	 *
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @return
	 */
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String querybysearch(MemberQueryBean mqb, HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();

//			String level=request.getParameter("level");
//			String isAuth=request.getParameter("isAuth");

			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("level", mqb.getLevel());
			map.put("isAuth", mqb.getIsAuth());
			map.put("startdate", mqb.getStartDate());
			map.put("enddate", mqb.getEndDate());
			map.put("marketId", mqb.getMarketId());
			map.put("regetype", mqb.getRegetype());
			map.put("status", mqb.getStatus());
			map.put("total", memberBaseinfoToolService.getTotal(map));

			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberBaseinfoDTO> list1 = memberBaseinfoToolService.getBySearch(map);
			List<MemberBaseinfoDTO> list = new ArrayList<>();
			for(MemberBaseinfoDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel()==4){
					mdt.setMarketName("");
				}

				String createUserId = mdt.getCreateUserId();
				if(StringUtils.isNotBlank(createUserId)){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
					if(createUser != null){
						mdt.setCreateUserName(createUser.getUserName());
					}
				}
				list.add(mdt);
			}
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, String account,
			String mobile,  String level,  String isAuth,String marketId,String startDate, String endDate,String regetype) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("mobile", mobile);
			map.put("account", account);
			map.put("level", level);
			map.put("isAuth", isAuth);
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			map.put("marketId", marketId);
			map.put("regetype", regetype);
			int total = memberBaseinfoToolService.getTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("member checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account, String mobile,  String level,  String isAuth,String marketId,String startDate, String endDate,String regetype) {
		List<MemberBaseinfoDTO> list = new ArrayList();
		File fileDir=new File(com.gudeng.commerce.gd.admin.util.Constant.TEMP_EXCEL_PATH);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		String excelPath=com.gudeng.commerce.gd.admin.util.Constant.TEMP_EXCEL_PATH+File.separator+"用户表_";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account",account);
			map.put("mobile", mobile);
			map.put("level", level);
			map.put("isAuth", isAuth);
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			map.put("marketId", marketId);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			map.put("regetype",regetype);

			List<MemberBaseinfoDTO> list1 = memberBaseinfoToolService.getBySearch(map);
			for(MemberBaseinfoDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel()==4){
					mdt.setMarketName("");
				}

				String createUserId = mdt.getCreateUserId();
				if(StringUtils.isNotBlank(createUserId)){
					SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
					if(createUser != null){
						mdt.setCreateUserName(createUser.getUserName());
					}
				}
				list.add(mdt);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
//统计需要多少个excel表
		Integer excelcount=0;//excel表的个数
		
		Integer excelNum=com.gudeng.commerce.gd.admin.util.Constant.EXCEL_SIZE;
		if(list.size()%excelNum>0){
			excelcount=list.size()/excelNum+1;
		}else{
			excelcount=list.size()/excelNum;
		}
		OutputStream ouputStream=null;		WritableWorkbook wwb = null;
		for (int excelIndex = 0;excelIndex < excelcount; excelIndex++) {
		try {
Integer excelIndexName=excelIndex+1;
		    ouputStream =new FileOutputStream(excelPath+excelIndexName+".xls");;
			// 查询数据
			
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("用户表_"+excelIndexName, 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "用户类型");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "手机号");// 填充第一行第三个单元格的内容
				Label label30 = new Label(3, 0, "昵称");// 填充第一行第四个单元格的内容
				Label label40 = new Label(4, 0, "注册时间");// 填充第一行第五个单元格的内容
				Label label50 = new Label(5, 0, "所属市场");// 填充第一行第七个单元格的内容
				Label label60 = new Label(6, 0, "认证状态");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "激活状态");// 填充第一行第七个单元格的内容
				Label label80 = new Label(8, 0, "录入人");// 填充第一行第八个单元格的内容
				Label label90 = new Label(9, 0, "个人/企业");// 填充第一行第9个单元格的内容
				Label label100 = new Label(10, 0, "企业名称");// 填充第一行第10个单元格的内容
				Label label110 = new Label(11, 0, "联系人");// 填充第一行第11个单元格的内容
				Label label120 = new Label(12, 0, "推荐人姓名");// 
        		Label label130 = new Label(13, 0, "推荐人手机号码");// 
        		Label label140 = new Label(14, 0, "农商友用户类型");// 

				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				sheet.addCell(label100);
				sheet.addCell(label110);
				sheet.addCell(label120);
				sheet.addCell(label130);	
				sheet.addCell(label140);	
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					Integer currentPageEnd=list.size()-(excelIndex+1)*excelNum;
					Integer lenght=list.size()-currentPageEnd;
					if(currentPageEnd<0){
						lenght=list.size();
					}
					for (int j = excelIndex*excelNum,i=0; i < lenght&&j<lenght; i++,j++) {
						MemberBaseinfoDTO memberDTO = list.get(j);
						Label label0 =null;
						if(memberDTO.getLevel()==null){
							label0 = new Label(0, i + 1, "");
						}else if(memberDTO.getLevel()==1){
							label0 = new Label(0, i + 1, "谷登农批");
						}else if(memberDTO.getLevel()==2){
							label0 = new Label(0, i + 1, "农速通");
						}else if(memberDTO.getLevel()==3){
							label0 = new Label(0, i + 1, "农商友");
						}else if(memberDTO.getLevel()==4){
							label0 = new Label(0, i + 1, "产地供应商");
						}else if(memberDTO.getLevel()==5){
							label0 = new Label(0, i + 1, "农批友");
						}
						Label label1 = new Label(1, i + 1, memberDTO.getAccount());
						Label label2 = new Label(2, i + 1, memberDTO.getMobile());
						Label label3 = new Label(3, i + 1, memberDTO.getNickName());
						Label label4 = new Label(4, i + 1, memberDTO.getCreateTime()==null?"":time.format(memberDTO.getCreateTime()));
						Label label5 = new Label(5, i + 1, memberDTO.getMarketName());
						Label label6 = null;
						if(memberDTO.getIsAuth()==null || "".equals(memberDTO.getIsAuth())||"-1".equals(memberDTO.getIsAuth())){
							label6=new Label(6, i + 1, "未提交认证");
						}else if(memberDTO.getIsAuth().equals("1")){
							label6 = new Label(6, i + 1, "已认证");
						}else if(memberDTO.getIsAuth().equals("2")){
							label6 = new Label(6, i + 1, "已驳回");
						}else if(memberDTO.getIsAuth().equals("0")){
							label6 = new Label(6, i + 1, "待认证");
						}else{
							label6=new Label(6, i + 1, "未提交认证");
						}

						Label label7 = null;
						if(memberDTO.getStatus()!=null && memberDTO.getStatus().equals("1")){
							label7=new Label(7, i + 1, "启用");
						}else{
							label7=new Label(7, i + 1, "禁用");
						}
						Label label8 = new Label(8, i + 1, memberDTO.getCreateUserName());
						//用户类型
						Label label9 = null;
						if(memberDTO.getUserType()!=null && memberDTO.getUserType()==2){
							label9=new Label(9, i + 1, "企业");
						}else{
							label9=new Label(9, i + 1, "个人");
						}
						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						if(memberDTO.getUserType()!=null && memberDTO.getUserType()==2){
							Label label10_ = new Label(10, i + 1, memberDTO.getCompanyName());
							Label label11 = new Label(11, i + 1, memberDTO.getCompanyContact());
							sheet.addCell(label10_);
							sheet.addCell(label11);
						}else{
							Label label10_ = new Label(10, i + 1, "");
							Label label11 = new Label(11, i + 1, memberDTO.getRealName());
							sheet.addCell(label10_);
							sheet.addCell(label11);
						}
						Label label12 = new Label(12, i + 1, memberDTO.getRefereeRealName());
						Label label13 = new Label(13, i + 1, memberDTO.getRefereeMobile());
						//农商友用户类型:1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它
						System.out.println(memberDTO.getNsyUserType() +"......."+NsyUserTypeEnum.getNsyUserTypeNameByNsyUserType(memberDTO.getNsyUserType()));
						Label label14 = new Label(14, i + 1, NsyUserTypeEnum.getNsyUserTypeNameByNsyUserType(memberDTO.getNsyUserType()));
						sheet.addCell(label12);
						sheet.addCell(label13);
						sheet.addCell(label14);					
					}
				}
				wwb.write();// 将数据写入工作簿
				ouputStream.flush();
				wwb.close();
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
		 	//输出提供下载
			List<File> srcfile=new ArrayList<File>();  
			for (int excelIndex = 0;excelIndex < excelcount; excelIndex++) {
				Integer excelIndexName=excelIndex+1;
			    srcfile.add(new File(excelPath+excelIndexName+".xls"));//添加需要打包的Excel文件路径
			}
			
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMdd");
			String zipTime=format.format(date); 
			
	        File zipfile = new File(excelPath+zipTime+".zip");//Zip输出的路径
	        ZipUtil.zipFiles(srcfile, zipfile);//进行打包
	        //提供下载
			response.setContentType("application/x-zip-compressed");
			String fileName;
			 InputStream in=null;
			OutputStream out=null;
			try {
				String zipName="用户表_"+zipTime;
				fileName = new String(zipName.getBytes(), "ISO8859-1");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName +".zip");			
		        in = new FileInputStream(new File(excelPath+zipTime+".zip"));  
		        out = response.getOutputStream();  
		        //写文件  
		        int b;  
		        while((b=in.read())!= -1) {  
		            out.write(b);  
		        }
		        in.close();  
		        out.flush();
		        out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//删除zip文件
				 File filezip =new File(excelPath+zipTime+".zip");
				 if(filezip.exists()){
					 filezip.delete();
				 }
				//删除excel文件
				for (int excelIndex = 1;excelIndex <= excelcount; excelIndex++) {
					 File file =new File(excelPath+excelIndex+".xls");
					 if(file.exists()){
						 file.delete();
					 }
				}
			}
		return null;
	}
	/**
	 * 跳转提现申请管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("getAccountInfo")
	public String getAccountInfo(HttpServletRequest request){

		return "member/memberBaseinfo_waletInfo";
	}


	/**
	 * 查询账号流水信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getAccountFlowList")
	@ResponseBody
	public String getAccountFlowList(@RequestParam String memberId,HttpServletRequest request) {

		try {
			Map<String, Object> map = new HashMap<>();

			if (StringUtil.isNotEmpty(memberId)) {
				//查询账号（钱包）信息根据memberId

				//AccInfoDTO accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));

				//memberId
				map.put("memberId", Long.valueOf(memberId));
				//只查找已支付订单
				map.put("orderStatus", "3");
				//设置总记录数
				map.put("total", orderBaseinfoToolService.getPageTotal(map));
				//设定分页,排序
				setCommParameters(request, map);
				List<TransferListDTO> list = orderBaseinfoToolService.getListByPage(map);

				map.put("rows", list);//rows键 存放每页记录 list
				//map.put("accInfoDTO", accInfoDTO);
				return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 	  增加修改同一个方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveMore")
	@ResponseBody
    public String saveMore(MemberBaseinfoDTO dto, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			dto.setIp(regUser.getUserIP());
//			Map map=new HashMap();


			String cityName=dto.getCityName();
			if(cityName!=null && !cityName.trim().equals("")){
				Area area=areaToolService.getByAreaName(cityName);
				if(area!=null && area.getAreaID()!=null && !area.getAreaID().equals("")){
					dto.setCcityId(Long.valueOf(area.getAreaID()));
				}else{
					return "cCityName";
				}
			}
			if(dto.getUserType()!=null && dto.getUserType()==2 && (dto.getCompanyName()==null ||dto.getCompanyName().trim().equals(""))){
				return "companyName";
			}
			String memberId=request.getParameter("memberId");
			if(null!=memberId && !memberId.equals("")){
				dto.setMemberId(Long.valueOf(memberId));
				dto.setUpdateTime_string(DateUtil.getSysDateString());
				dto.setUpdateUserId(regUser.getUserID());//用户ID，需要规划怎么填写
			}else{
				dto.setCreateTime_string(DateUtil.getSysDateString());
				dto.setCreateUserId(regUser.getUserID());//用户ID，需要规划怎么填写
			}
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId);
			int	i=memberBaseinfoToolService.updateMemberBaseinfoDTO(dto);



			if(i>0 ){
					MemberCertifiDTO mc=new MemberCertifiDTO();
					mc.setMemberId(dto.getMemberId());
					mc.setStatus(dto.getIsAuth());
					//修改农速通用户的时候，修改企业的mobile,企业的名称，联系人姓名
					mc.setMobile(memberBaseinfoDTO.getMobile());
					mc.setCompanyName(dto.getCompanyName());
					mc.setLinkMan(memberBaseinfoDTO.getRealName());
					
//					Map<String, Object> q=new HashMap<String, Object>();
//					q.put("memberId", mc.getMemberId());
//					q.put("startRow",0);
//					q.put("endRow",10);
//					//判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
//					List<MemberCertifiDTO> list=memberCertifiToolService.getBySearch(q);
//					if(null!=list && list.size()>0){
//						MemberCertifiDTO mcdto=list.get(0);
//						mc.setCertifiId(mcdto.getCertifiId());
//						memberCertifiToolService.updateMemberCertifiDTO(mc);
//					}else{
//						MemberCertifiDTO mcd=new MemberCertifiDTO();
//						mcd.setType(dto.getUserType()+"");
//						mcd.setCompanyName(dto.getCompanyName());
//						mcd.setLinkMan(dto.getRealName());
//						mcd.setMobile(dto.getMobile());
//						memberCertifiToolService.addMemberCertifiDTO(mc);
//					}
					
					//判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
					MemberCertifiDTO memberCertifiDTO=memberCertifiToolService.getByUserId(mc.getMemberId()+"");
					if(null!=memberCertifiDTO ){
						mc.setCertifiId(memberCertifiDTO.getCertifiId());
						memberCertifiToolService.updateMemberCertifiDTO(mc);
					}else{
						MemberCertifiDTO mcd=new MemberCertifiDTO();
						mcd.setType(dto.getUserType()+"");
						mcd.setCompanyName(dto.getCompanyName());
						mcd.setLinkMan(dto.getRealName());
						mcd.setMobile(dto.getMobile());
						memberCertifiToolService.addMemberCertifiDTO(mc);
					}



					String shopName=(String)request.getParameter("shopName");
					String marketId=(String)request.getParameter("marketId");
					if(null !=dto.getLevel() && dto.getLevel()==4){//产地供应商，默认写入市场为3
						marketId="3";
					}
					if(shopName!=null && !shopName.equals("") && marketId!=null && !marketId.equals("")){
						BusinessBaseinfoEntity bb=new BusinessBaseinfoEntity();
						bb.setUserId(Long.valueOf(dto.getMemberId()));
						bb.setShopsName(request.getParameter("shopName"));
						bb.setCreateTime(new Date());
						//判断是否已经有商铺，没有则直接新增。仅仅设定一个店名，和当前用户ID。
						BusinessBaseinfoDTO bDto=businessBaseinfoToolService.getByUserId(String.valueOf(dto.getMemberId()));

						if(null!=bDto  ){
							reBusinessMarketToolService.deleteByBusinessId(bDto.getBusinessId());
							ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
							rbm.setBusinessId(bDto.getBusinessId());
							rbm.setMarketId(Long.valueOf(marketId));
							reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
						}else{
							Long lo=businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
							ReBusinessMarketDTO rbm=new ReBusinessMarketDTO();
							rbm.setBusinessId(lo);
							rbm.setMarketId(Long.valueOf(marketId));
							reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
						}
					}
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}
