package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.gudeng.commerce.gd.admin.service.MemberChangeLogToolService;
import com.gudeng.commerce.gd.admin.service.MemberMessageFlagToolService;
import com.gudeng.commerce.gd.admin.service.OrderBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.PosBankCardToolService;
import com.gudeng.commerce.gd.admin.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.admin.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.admin.service.sysmgr.SysRegisterUserAdminService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.JavaMd5;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.customer.dto.MemberChangeLogDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.AccBankCardInfoEntity;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.MemberMessageFlagEntity;
import com.gudeng.commerce.gd.customer.entity.PosBankCardEntity;
import com.gudeng.commerce.gd.customer.enums.MemberBaseinfoEnum;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("member")
public class MemberBaseinfoController extends AdminBaseController {
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
	private MarketManageService marketManageService;

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

	@Autowired
	public MemberChangeLogToolService memberChangeLogToolService;

	@Autowired
	public PvStatisticBusinessToolService pvStatisticBusinessToolService;
	
	@Autowired
	public MemberMessageFlagToolService memberMessageFlagToolService;

	/**
	 * demo
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String member(HttpServletRequest request) {
		// 市场列表
		try {
			List<MarketDTO> list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/memberBaseinfo_list";
	}

	/**
	 * demo
	 * 
	 * @return
	 */
	@RequestMapping("goldSupplier")
	public String goldSupplier(HttpServletRequest request) {
		// 市场列表
		try {
			List<MarketDTO> list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/goldSupplier_list";
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
	public String query(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");

			Map<String, Object> map = new HashMap<String, Object>();
			if (null == id || id.equals("")) {
			} else {
				map.put("id", id);
			}
			// 设置查询参数
			// 记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberBaseinfoDTO> list1 = memberBaseinfoToolService.getBySearch(map);
			List<MemberBaseinfoDTO> list = new ArrayList<MemberBaseinfoDTO>();
			for (MemberBaseinfoDTO mdt : list1) {
				if (null != mdt.getLevel() && mdt.getLevel() == 4) {
					mdt.setMarketName("");
				}
				String createUserId = mdt.getCreateUserId();
				if (StringUtils.isNotBlank(createUserId)) {
					SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
					if (createUser != null) {
						mdt.setCreateUserName(createUser.getUserName());
					}
				}
				list.add(mdt);
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据id查询
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "querybyid/{memberId}")
	@ResponseBody
	public String queryById(@PathVariable("memberId") String memberId, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (null == memberId || memberId.equals("")) {
			} else {
				map.put("memberId", memberId);
			}
			// 设置查询参数
			// 记录数
			map.put("total", memberBaseinfoToolService.getTotal(map));
			// 设定分页,排序
			setCommParameters(request, map);
			// list
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId);
			map.put("memberBaseinfoDTO", memberBaseinfoDTO);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
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
	@RequestMapping(value = "updatestatus/{memberId}-{status}")
	@ResponseBody
	public String updateStatus(@PathVariable("memberId") String memberId, @PathVariable("status") String status,
			HttpServletRequest request) {
		try {
			// MemberBaseinfoDTO
			// mb=memberBaseinfoToolService.getById(certifiId);
			// mb.setStatus(status);
			MemberBaseinfoDTO mb = new MemberBaseinfoDTO();
			mb.setStatus(status);
			mb.setMemberId(Long.valueOf(memberId));
			int i = memberBaseinfoToolService.updateStatus(mb);
			if (i > 0) {
				return "success";
			} else {
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
	@RequestMapping(value = "updateSubShow/{memberId}-{subShow}")
	@ResponseBody
	public String updateSubShow(@PathVariable("memberId") Long memberId, @PathVariable("subShow") Integer subShow,
			HttpServletRequest request) {
		try {
			MemberBaseinfoDTO mb = new MemberBaseinfoDTO();
			mb.setSubShow(subShow);
			mb.setMemberId(Long.valueOf(memberId));
			int i = memberBaseinfoToolService.updateSubShow(mb);
			if (i > 0) {
				return "success";
			} else {
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
	@RequestMapping(value = "deletebyid")
	@ResponseBody
	public String deleteById(@RequestParam String id, HttpServletRequest request) {
		try {
			if (null == id || id.equals("")) {
				return "faild";
			}
			int i = memberBaseinfoToolService.deleteById(id);
			if (i > 0) {
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 发送短信
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sendMessage")
	@ResponseBody
	public String sendMessage(@RequestParam String id, HttpServletRequest request) {
		try {
			if (null == id || id.equals("")) {
				return "faild";
			}
			HashMap<String, Object> params = new HashMap<String, Object>();
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(id);
			if (memberBaseinfoDTO == null) {
				return "failed";
			}
			String mobile = memberBaseinfoDTO.getMobile();
			if (StringUtils.isNotBlank(memberBaseinfoDTO.getRealName())) {
				params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT,
						memberBaseinfoDTO.getRealName());
			} else {
				params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT, mobile);
			}
			pvStatisticBusinessToolService.sendMsg(Long.valueOf(id), mobile,
					com.gudeng.commerce.gd.customer.util.Constant.Alidayu.MESSAGETYPE1,
					MessageTemplateEnum.ADVERTONLINE, params);
			
			//发送短信后，更新关联表信息为已发送
			SysRegisterUser regUser = (SysRegisterUser) request.getSession()
					.getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			
			MemberMessageFlagEntity entity  = new MemberMessageFlagEntity();
			entity.setMemberId(Long.valueOf(id));
			entity.setUpdateUserId(regUser.getUserID());
			entity.setSendFlag("1");
			int row = memberMessageFlagToolService.update(entity);
			if(row == 0)
			{
				entity.setCreateUserId(regUser.getUserID());
				memberMessageFlagToolService.insert(entity);
			}
			
		} catch (Exception e) {
			return "failed";
		}
		return "success";
	}

	/**
	 * 增加修改同一个页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request) {
		return "member/memberBaseinfo_edit";
	}

	/**
	 * 修改用户信息 增加修改同一个方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public String saveOrUpdate(MemberBaseinfoDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser regUser = (SysRegisterUser) request.getSession()
					.getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			dto.setIp(regUser.getUserIP());
			Map<String, String> map = new HashMap<String, String>();

			// String account=request.getParameter("account");
			String account = "gd" + UUID.randomUUID().toString().replace("-", "").toUpperCase(); // 由系统生成帐号：gd+UUID
			String mobile = request.getParameter("mobile");
			if (null == mobile || StringUtil.isEmpty(mobile)) {
				return "mobile";// 手机号为空
			}
			Pattern pattern = Pattern.compile("^[1][34587][0-9]{9}$");
			Matcher matcher = pattern.matcher(mobile);

			if (!matcher.matches()) {// 手机不符合规则
				return "mobileE";
			}

			String cityName = dto.getCityName();
			if (cityName != null && !cityName.trim().equals("")) {
				Area area = areaToolService.getByAreaName(cityName);
				if (area != null && area.getAreaID() != null && !area.getAreaID().equals("")) {
					dto.setCcityId(Long.valueOf(area.getAreaID()));
				} else {
					return "cCityName";
				}
			}
			if (dto.getUserType() != null && dto.getUserType() == 2
					&& (dto.getCompanyName() == null || dto.getCompanyName().trim().equals(""))) {
				return "companyName";
			}
			if (dto.getRealName().length() > 14) {
				return "toLong";
			}

			String memberId = request.getParameter("memberId");
			// 管理后台中新增用户的密码，在此加密后转大写，没有必要再jsp或者js中加密。
			String password = request.getParameter("password");
			if (password != null && !password.equals("")) {
				dto.setPassword(JavaMd5.getMD5(dto.getPassword() + "gudeng2015@*&^-").toUpperCase());
			}
			if (null != memberId && !memberId.equals("")) {
				map.put("memberId", memberId);
				dto.setMemberId(Long.valueOf(memberId));
				dto.setUpdateTime_string(DateUtil.getSysDateString());
				dto.setUpdateUserId(regUser.getUserID());// 用户ID，需要规划怎么填写
			} else {
				dto.setCreateTime_string(DateUtil.getSysDateString());
				dto.setCreateUserId(regUser.getUserID());// 用户ID，需要规划怎么填写
			}
			dto.setNickName(request.getParameter("nickName"));
			dto.setMobile(request.getParameter("mobile"));
			dto.setBirthday_string(request.getParameter("birthday"));
			String nsyUserType = request.getParameter("nsyUserType");

			if (StringUtil.isNotEmpty(nsyUserType)) {
				dto.setNsyUserType(Integer.valueOf(nsyUserType));
			}
			int i = 0;
			Long l = 0L;
			// 根据id判断是否存在，存在即为更新，不存在即为增加
			if (null != memberId && !memberId.equals("")) {
				//更新
				MemberBaseinfoDTO exist1 = memberBaseinfoToolService.getByAccount(mobile);
				MemberBaseinfoDTO exist2 = memberBaseinfoToolService.getByMobile(mobile);

				if (null != exist1 && Long.valueOf(memberId).longValue() != exist1.getMemberId().longValue()
						|| null != exist2 && Long.valueOf(memberId).longValue() != exist2.getMemberId().longValue()) {
					return "existM";
				}

				// 用户等级变更
				if (dto.getMemberGrade() != null && dto.getLevel() == 4
						&& !dto.getMemberGrade().equals(dto.getMemberGradeOld())) 
				{
					Map<String, Object> logMap = new HashMap<String, Object>();
					logMap.put("memberId", dto.getMemberId());
					logMap.put("description", dto.getChangeDescription());
					logMap.put("createUserId", getUser(request).getUserID());
					logMap.put("updateUserId", getUser(request).getUserID());

					// 普通会员变更为金牌供应商，
					if (dto.getMemberGrade() == 1) {
						// 记录用户日志类型
						logMap.put("type", "1");
						// 普通会员变更为金牌供应商时，金牌供应商发送短信通知
						Map<String, Object> params = new HashMap<>();
						if (StringUtils.isNotBlank(dto.getRealName())) {
							params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT,dto.getRealName());
						} else {
							params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT, dto.getMobile());
						}
						pvStatisticBusinessToolService.sendMsg(dto.getMemberId(), dto.getMobile(),
								com.gudeng.commerce.gd.customer.util.Constant.Alidayu.MESSAGETYPE2,
								MessageTemplateEnum.PAYSUCCESS, params);
						
						//从普通会员变更为金牌供应商时，状态改为未发送
						MemberMessageFlagEntity entity  = new MemberMessageFlagEntity();
						entity.setMemberId(dto.getMemberId());
						entity.setUpdateUserId(regUser.getUserID());
						entity.setUpdateTime(new Date());
						entity.setSendFlag("0");
						int row = memberMessageFlagToolService.update(entity);
						if(row == 0)
						{
							entity.setCreateUserId(regUser.getUserID());
							entity.setCreateTime(new Date());
							memberMessageFlagToolService.insert(entity);
						}
					} else {
						// 金牌供应商变为普通会员时，记录用户日志类型
						logMap.put("type", "2");
					}

					// 保存用户日志
					memberChangeLogToolService.addMemberChangeLog(logMap);
				} else {
					dto.setMemberGrade(null);
				}
				
				//保存操作
				i = memberBaseinfoToolService.updateMemberBaseinfoDTO(dto);

			} else {
				MemberBaseinfoDTO exist = memberBaseinfoToolService.getByAccount(account);
				MemberBaseinfoDTO exist1 = memberBaseinfoToolService.getByAccount(mobile);
				MemberBaseinfoDTO exist2 = memberBaseinfoToolService.getByMobile(mobile);
				// 新增时，手机和账号，mobile和account 都不能存在
				if (null != exist || null != exist1 || null != exist2) {
					return "exist";
				}

				MemberBaseinfoEntity me = new MemberBaseinfoEntity();
				// me.setAccount(dto.getAccount());
				me.setAccount(account); // 新增时账户为系统生成
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
				// 0表示管理后台创建的用户，1表示web前端注册的用户，2表示农速通注册的用户，3表示农商友-买家版注册的用户，4表示农商友-卖家版注册的用户，5表示门岗添加的用户
				me.setRegetype("0");
				if (dto.getMemberGrade() != null && dto.getMemberGrade() == 1) {
					me.setMemberGrade(1);
					me.setValidTime(new Date());
					me.setExpireTime(DateUtils.addDays(DateUtils.addYears(me.getValidTime(), 1), -1));
				}
				if (dto.getShopRecommend() != null) {
					me.setShopRecommend(dto.getShopRecommend());
				}
				l = memberBaseinfoToolService.addMemberBaseinfoEnt(me);
				// 金牌供应商发送短信通知
				if (dto.getMemberGrade() != null && dto.getMemberGrade() == 1) {
					Map<String, Object> params = new HashMap<>();
					if (StringUtils.isNotBlank(me.getRealName())) {
						params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT, me.getRealName());
					} else {
						params.put(com.gudeng.commerce.gd.customer.util.Constant.Alidayu.ACCOUNT, me.getMobile());
					}
					pvStatisticBusinessToolService.sendMsg(l, me.getMobile(),
							com.gudeng.commerce.gd.customer.util.Constant.Alidayu.MESSAGETYPE2,
							MessageTemplateEnum.PAYSUCCESS, params);
				}
				dto.setMemberId(l);
			}

			if (i > 0 || l > 0) {
				// if(null !=dto.getLevel() && dto.getLevel()==3){
				// 2015年10月16日
				// 管理后台增加小商户时，无需进行审核，直接通过认证，即往certifi表中，写入已经认证的记录，不必考虑认证资料
				MemberCertifiDTO mc = new MemberCertifiDTO();
				mc.setMemberId(i > 0 ? dto.getMemberId() : l);
				mc.setStatus(dto.getIsAuth());
				// if(dto.getUserType()!=null && dto.getLevel()==2 && i>0 ){
				// if(dto.getLevel()!=null && dto.getLevel()==2 &&
				// dto.getUserType()!=null && dto.getUserType()==2 && i>0 ){
				// 修改农速通用户的时候，修改企业的mobile,企业的名称，联系人姓名

				mc.setMobile(mobile);
				mc.setCompanyName(dto.getCompanyName());
				mc.setLinkMan(dto.getRealName());
				// }
				// Map<String, Number> q=new HashMap<String, Number>();
				// q.put("memberId", mc.getMemberId());
				// q.put("startRow",0);
				// q.put("endRow",10);
				// //判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
				// List<MemberCertifiDTO>
				// list=memberCertifiToolService.getBySearch(q);
				MemberCertifiDTO memberCertifiDTO = memberCertifiToolService.getByUserId(mc.getMemberId() + "");
				if (!"-2".equals(dto.getIsAuth())) {
					if (null != memberCertifiDTO) {
						mc.setCertifiId(memberCertifiDTO.getCertifiId());
						if ("-1".equals(dto.getIsAuth())) {
							memberCertifiToolService.deleteById(memberCertifiDTO.getCertifiId().toString());
						} else {
							memberCertifiToolService.updateMemberCertifiDTO(mc);
						}

					} else {
						String nowTimeString = DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME);
						mc.setType("1");
						mc.setCommitTime_string(nowTimeString);
						// mc.setCertificationTime_string(nowTimeString);
						mc.setCreateTime_string(nowTimeString);
						memberCertifiToolService.addMemberCertifiDTO(mc);
					}
				} else if (mc.getMobile() != null && !mc.getMobile().trim().equals("")) {
					// 农速通用户，不选择认证状态的时候,更新个人(member)的手机号码到企业（certifi)手机号中
					if (null != memberCertifiDTO) {
						mc.setCertifiId(memberCertifiDTO.getCertifiId());
						memberCertifiToolService.updateMemberCertifiDTO(mc);
					} else {
						MemberCertifiDTO mcd = new MemberCertifiDTO();
						mcd.setType(dto.getUserType() + "");
						mcd.setCompanyName(dto.getCompanyName());
						mcd.setLinkMan(dto.getRealName());
						mcd.setMobile(dto.getMobile());
						memberCertifiToolService.addMemberCertifiDTO(mc);
					}

				}
				String shopName = (String) request.getParameter("shopName");
				String marketId = (String) request.getParameter("marketId");
				if (null != dto.getLevel() && dto.getLevel() == 4) {// 产地供应商，默认写入市场为3
					marketId = "3";
				}
				if (shopName != null && !shopName.equals("") && marketId != null && !marketId.equals("")) {
					BusinessBaseinfoEntity bb = new BusinessBaseinfoEntity();

					bb.setUserId(Long.valueOf(dto.getMemberId()));
					bb.setShopsName(request.getParameter("shopName"));
					bb.setCreateTime(new Date());

					// 判断是否已经有商铺，没有则直接新增。仅仅设定一个店名，和当前用户ID。
					BusinessBaseinfoDTO bDto = businessBaseinfoToolService
							.getByUserId(String.valueOf(dto.getMemberId()));

					if (null != bDto) {
						reBusinessMarketToolService.deleteByBusinessId(bDto.getBusinessId());
						ReBusinessMarketDTO rbm = new ReBusinessMarketDTO();
						rbm.setBusinessId(bDto.getBusinessId());
						rbm.setMarketId(Long.valueOf(marketId));
						reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
					} else {
						Long lo = businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
						ReBusinessMarketDTO rbm = new ReBusinessMarketDTO();
						rbm.setBusinessId(lo);
						rbm.setMarketId(Long.valueOf(marketId));
						reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
					}
				}

				// }
				return "success";
			} else {
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
	@RequestMapping(value = "edit")
	@ResponseBody
	public String edit(HttpServletRequest request) {
		try {
			String memberId = request.getParameter("memberId");
			MemberBaseinfoDTO dto = memberBaseinfoToolService.getById(memberId);
			putModel("dto", dto);

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
	@RequestMapping(value = "editbyid/{memberId}")
	public String editbyid(@PathVariable("memberId") String memberId, HttpServletRequest request) {
		try {
			//查询用户信息
			MemberBaseinfoDTO dto = memberBaseinfoToolService.getById(memberId);
			if (dto.getCcityId() != null) {
				Area area = areaToolService.getArea(String.valueOf(dto.getCcityId()));
				dto.setCityName(area.getArea());
			}
			//查询商家信息
			BusinessBaseinfoDTO bdto = businessBaseinfoToolService.getByUserId(memberId);
			if (null != bdto && null != bdto.getMarketId()) {
				MarketDTO marketDTO = marketManageService.getById(bdto.getMarketId());
				putModel("mdto", marketDTO);
			}

			putModel("bdto", bdto);

			putModel("dto", dto);
			
			putModel("memberId", memberId);

			//获取钱包信息
			AccInfoDTO accInfoDTO = accInfoToolService.getWalletIndex(Long.valueOf(memberId));

			putModel("accInfoDTO", accInfoDTO);

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
	@RequestMapping(value = "showbyid/{memberId}")
	public String showbyid(@PathVariable("memberId") String memberId, HttpServletRequest request) {
		try {

			/** 获取会员信息 */
			MemberBaseinfoDTO dto = memberBaseinfoToolService.getById(memberId);

			BusinessBaseinfoDTO bdto = businessBaseinfoToolService.getByUserId(memberId);
			if (null != bdto && null != bdto.getMarketId()) {
				MarketDTO marketDTO = marketManageService.getById(bdto.getMarketId());
				putModel("mdto", marketDTO);
			}

			putModel("bdto", bdto);

			putModel("dto", dto);

			/** 获取钱包信息 */
			putModel("memberId", memberId);

			AccInfoDTO accInfoDTO = accInfoToolService.getWalletIndex(Long.valueOf(memberId));

			putModel("accInfoDTO", accInfoDTO);

			// 显示银行卡信息
			// List<AccBankCardInfoEntity> cardInfoList =
			// accBankCardInfoTooService.getCardInfoById(Long.parseLong(memberId));
			// putModel("cardInfoList",cardInfoList);
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
	@RequestMapping(value = "showMemberById/{memberId}")
	public String showMemberById(@PathVariable("memberId") String memberId, HttpServletRequest request) {
		try {

			/** 获取会员信息 */
			MemberBaseinfoDTO dto = memberBaseinfoToolService.getById(memberId);

			BusinessBaseinfoDTO bdto = businessBaseinfoToolService.getByUserId(memberId);
			if (null != bdto && null != bdto.getMarketId()) {
				MarketDTO marketDTO = marketManageService.getById(bdto.getMarketId());
				putModel("mdto", marketDTO);
			}

			putModel("bdto", bdto);

			putModel("dto", dto);

			/** 获取钱包信息 */
			// putModel("memberId", memberId);

			// AccInfoDTO
			// accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));
			//
			// putModel("accInfoDTO",accInfoDTO);

			return "member/memberBaseinfo_member_show";

		} catch (Exception e) {

		}
		return null;
	}

	@RequestMapping(value = "queryCarNo")
	@ResponseBody
	public String queryCarNo(HttpServletRequest request) {
		String memberId = request.getParameter("memberId");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(memberId)) {
			String regetype = request.getParameter("regetype");
			List<AccBankCardInfoEntity> cardInfoList = null;
			try {
				if (MemberBaseinfoEnum.REGETYPE_7.getKey().equals(regetype)) {
					// 来源是pos刷卡的用户只有一个付款卡号
					PosBankCardEntity posBankCardEntity = posBankCardToolService.getByMemberId(memberId);
					if (posBankCardEntity != null) {
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
		String json = JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
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
	@RequestMapping(value = "querybysearch")
	@ResponseBody
	public String querybysearch(MemberQueryBean mqb, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			// String level=request.getParameter("level");
			// String isAuth=request.getParameter("isAuth");

			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("level", mqb.getLevel());
			map.put("memberGrade", mqb.getMemberGrade());
			map.put("startdate", mqb.getStartDate());
			map.put("enddate", mqb.getEndDate());
			map.put("marketId", mqb.getMarketId());
			map.put("regetype", mqb.getRegetype());
			map.put("status", mqb.getStatus());
			map.put("realName", mqb.getRealName());
			map.put("shopsName", mqb.getShopsName());
			map.put("certstatus", mqb.getCertstatus());
			map.put("total", memberBaseinfoToolService.getTotal(map));

			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberBaseinfoDTO> list1 = memberBaseinfoToolService.getBySearch(map);
			List<MemberBaseinfoDTO> list = new ArrayList<>();
			for (MemberBaseinfoDTO mdt : list1) {
				if (null != mdt.getLevel() && mdt.getLevel() == 4) {
					mdt.setMarketName("");
				}

				String createUserId = mdt.getCreateUserId();
				if (StringUtils.isNotBlank(createUserId)) {
					SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
					if (createUser != null) {
						mdt.setCreateUserName(createUser.getUserName());
					}
				}
				list.add(mdt);
			}
			map.put("rows", list);// rows键 存放每页记录 list
			String returnStr = JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {

		}
		return null;
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
	@RequestMapping(value = "queryGoldSupplierbySearch")
	@ResponseBody
	public String queryGoldSupplierbySearch(MemberQueryBean mqb, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("sendFlag", mqb.getSendFlag());
			map.put("startValidDate", mqb.getStartValidDate());
			map.put("endValidDate", mqb.getEndValidDate());
			map.put("startExpireDate", mqb.getStartExpireDate());
			map.put("endExpireDate", mqb.getEndExpireDate());
			map.put("status", mqb.getStatus());
			map.put("total", memberBaseinfoToolService.getGoldSupplierTotal(map));

			// 设定分页,排序
			setCommParameters(request, map);
			// list
			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.queryGoldSupplierbySearch(map);
			for (MemberBaseinfoDTO memberBaseinfoDTO : list) {
				//如果发送状态为未发送，则发送时间为空
				if(memberBaseinfoDTO.getSendFlag() != null && memberBaseinfoDTO.getSendFlag() == 0)
				{
					memberBaseinfoDTO.setSendTime(null);
				}
			}
			map.put("rows", list);// rows键 存放每页记录 list
			return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, HttpServletResponse response, String account,
			String mobile, String level, String memberGrade, String marketId, String startDate, String endDate,
			String regetype) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("mobile", mobile);
			map.put("account", account);
			map.put("level", level);
			map.put("memberGrade", memberGrade);
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			map.put("marketId", marketId);
			map.put("regetype", regetype);
			int total = memberBaseinfoToolService.getTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("member checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExportGoldSupplierParams", produces = "application/json; charset=utf-8")
	public String checkExportGoldSupplierParams(MemberQueryBean mqb, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(mqb.getStartValidDate()) && StringUtils.isBlank(mqb.getEndValidDate())
					&& StringUtils.isBlank(mqb.getStartExpireDate()) && StringUtils.isBlank(mqb.getEndExpireDate())) {
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			if ((StringUtils.isNotBlank(mqb.getStartValidDate()) || StringUtils.isNotBlank(mqb.getEndValidDate()))
					&& DateUtil.isDateIntervalOverFlow(mqb.getStartValidDate(), mqb.getEndValidDate(), 31)) {
				result.put("status", 0);
				result.put("message", "请选择正确的生效日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			if ((StringUtils.isNotBlank(mqb.getStartExpireDate()) || StringUtils.isNotBlank(mqb.getEndExpireDate()))
					&& DateUtil.isDateIntervalOverFlow(mqb.getStartExpireDate(), mqb.getEndExpireDate(), 31)) {
				result.put("status", 0);
				result.put("message", "请选择正确的结束日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("sendFlag", mqb.getSendFlag());
			map.put("startValidDate", mqb.getStartValidDate());
			map.put("endValidDate", mqb.getEndValidDate());
			map.put("startExpireDate", mqb.getStartExpireDate());
			map.put("endExpireDate", mqb.getEndExpireDate());
			map.put("status", mqb.getStatus());
			int total = memberBaseinfoToolService.getGoldSupplierTotal(map);
			if (total > 10000) {
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		} catch (Exception e) {
			logger.info("member checkExportParams with ex : {} ", new Object[] { e });
		}
		return JSONObject.toJSONString(result);
	}

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account, String mobile,
			String level, String memberGrade, String marketId, String startDate, String endDate, String regetype) {
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String name = "用户表_" + DateFormatUtils.format(new Date(), "yyyyMMdd");
			String fileName = new String(name.getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("account", account);
			map.put("mobile", mobile);
			map.put("level", level);
			map.put("memberGrade", memberGrade);
			map.put("startdate", startDate);
			map.put("enddate", endDate);
			map.put("marketId", marketId);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			map.put("regetype", regetype);

			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.getBySearch(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet(name + "列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				String[] titles = { "用户类型", "注册来源", "用户账号", "手机号", "会员等级", "注册时间", "商铺所属市场", "账号状态", "录入人" };
				for (int i = 0; i < titles.length; i++) {
					// sheet.setColumnView(i, 18);
					sheet.addCell(new Label(i, 0, titles[i]));
				}
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberBaseinfoDTO item = list.get(i);
						String levelName = "";
						if (item.getLevel() == 1) {
							levelName = "谷登农批";
						} else if (item.getLevel() == 2) {
							levelName = "农速通";
						} else if (item.getLevel() == 3) {
							levelName = "农商友";
						} else if (item.getLevel() == 4) {
							levelName = "产地供应商";
						} else if (item.getLevel() == 5) {
							levelName = "农批友";
						}
						sheet.addCell(new Label(0, i + 1, levelName));
						String regeTypeName = "未知来源";
						if ("0".equals(item.getRegetype())) {
							levelName = "管理后台";
						} else if ("1".equals(item.getRegetype())) {
							levelName = "谷登农批网";
						} else if ("2".equals(item.getRegetype())) {
							levelName = "农速通";
						} else if ("3".equals(item.getRegetype())) {
							levelName = "农商友";
						} else if ("4".equals(item.getRegetype())) {
							levelName = "农商友-农批商";
						} else if ("5".equals(item.getRegetype())) {
							levelName = "农批友";
						} else if ("6".equals(item.getRegetype())) {
							levelName = "供应商";
						} else if ("7".equals(item.getRegetype())) {
							levelName = "POS刷卡";
						} else if ("8".equals(item.getRegetype())) {
							levelName = "微信注册用户";
						} else if ("9".equals(item.getRegetype())) {
							levelName = "农速通-货主";
						} else if ("10".equals(item.getRegetype())) {
							levelName = "农速通-司机";
						} else if ("11".equals(item.getRegetype())) {
							levelName = "农速通-物流公司";
						}
						sheet.addCell(new Label(1, i + 1, regeTypeName));
						sheet.addCell(new Label(2, i + 1, item.getAccount()));
						sheet.addCell(new Label(3, i + 1, item.getMobile()));
						String memberGradeName = "";
						if (item.getMemberGrade() != null) {
							if (item.getMemberGrade() == 0) {
								memberGradeName = "普通会员";
							} else if (item.getMemberGrade() == 1) {
								memberGradeName = "金牌供应商";
							}
						}
						sheet.addCell(new Label(4, i + 1, memberGradeName));
						sheet.addCell(new Label(5, i + 1, item.getCreateTime() == null ? ""
								: DateFormatUtils.format(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
						String marketName = item.getMarketName();
						if (null != item.getLevel() && item.getLevel() == 4) {
							marketName = "";
						}
						sheet.addCell(new Label(6, i + 1, marketName));
						String statusName = "";
						if ("0".equals(item.getStatus())) {
							statusName = "禁用";
						} else if ("1".equals(item.getStatus())) {
							statusName = "启用";
						}
						sheet.addCell(new Label(7, i + 1, statusName));
						String createUserId = item.getCreateUserId();
						if (StringUtils.isNotBlank(createUserId)) {
							SysRegisterUser createUser = sysRegisterUserAdminService.get(createUserId);
							if (createUser != null) {
								item.setCreateUserName(createUser.getUserName());
							}
						}
						sheet.addCell(new Label(8, i + 1, item.getCreateUserName()));
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "exportGoldSupplierData")
	public String exportGoldSupplierData(MemberQueryBean mqb, HttpServletRequest request,
			HttpServletResponse response) {
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String name = "金牌供应商表_" + DateFormatUtils.format(new Date(), "yyyyMMdd");
			String fileName = new String(name.getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", mqb.getMobile());
			map.put("account", mqb.getAccount());
			map.put("sendFlag", mqb.getSendFlag());
			map.put("startValidDate", mqb.getStartValidDate());
			map.put("endValidDate", mqb.getEndValidDate());
			map.put("startExpireDate", mqb.getStartExpireDate());
			map.put("endExpireDate", mqb.getEndExpireDate());
			map.put("status", mqb.getStatus());
			map.put("startRow", 0);
			map.put("endRow", 99999999);

			List<MemberBaseinfoDTO> list = memberBaseinfoToolService.queryGoldSupplierbySearch(map);
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet(name + "列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				String[] titles = { "用户账号", "手机号码", "会员服务生效时间", "会员服务结束时间", "注册时间", "帐号状态", "广告上架通知", "广告通知时间" };
				for (int i = 0; i < titles.length; i++) {
					// sheet.setColumnView(i, 18);
					sheet.addCell(new Label(i, 0, titles[i]));
				}
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberBaseinfoDTO item = list.get(i);
						sheet.addCell(new Label(0, i + 1, item.getAccount()));
						sheet.addCell(new Label(1, i + 1, item.getMobile()));
						sheet.addCell(new Label(2, i + 1, item.getValidTime() == null ? ""
								: DateFormatUtils.format(item.getValidTime(), "yyyy-MM-dd")));
						sheet.addCell(new Label(3, i + 1, item.getExpireTime() == null ? ""
								: DateFormatUtils.format(item.getExpireTime(), "yyyy-MM-dd")));
						sheet.addCell(new Label(4, i + 1, item.getCreateTime() == null ? ""
								: DateFormatUtils.format(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss")));
						String statusName = "";
						if ("0".equals(item.getStatus())) {
							statusName = "禁用";
						} else if ("1".equals(item.getStatus())) {
							statusName = "启用";
						}
						sheet.addCell(new Label(5, i + 1, statusName));
						String sendFlagName = "";
						if (item.getSendFlag() != null && item.getSendFlag() == 0) {
							sendFlagName = "未发送";
						} else if (item.getSendFlag() != null && item.getSendFlag() == 1) {
							sendFlagName = "已发送";
						}
						sheet.addCell(new Label(6, i + 1, sendFlagName));
						sheet.addCell(new Label(7, i + 1, item.getSendTime() == null ? ""
								: DateFormatUtils.format(item.getSendTime(), "yyyy-MM-dd HH:mm:ss")));
					}
				}
				wwb.write();// 将数据写入工作簿
			}
			wwb.close();// 关闭
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 跳转提现申请管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getAccountInfo")
	public String getAccountInfo(HttpServletRequest request) {

		return "member/memberBaseinfo_waletInfo";
	}

	/**
	 * 查询账号流水信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getAccountFlowList")
	@ResponseBody
	public String getAccountFlowList(@RequestParam String memberId, HttpServletRequest request) {

		try {
			Map<String, Object> map = new HashMap<>();

			if (StringUtil.isNotEmpty(memberId)) {
				// 查询账号（钱包）信息根据memberId

				// AccInfoDTO
				// accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));

				// memberId
				map.put("memberId", Long.valueOf(memberId));
				// 只查找已支付订单
				map.put("orderStatus", "3");
				// 设置总记录数
				map.put("total", orderBaseinfoToolService.getPageTotal(map));
				// 设定分页,排序
				setCommParameters(request, map);
				List<TransferListDTO> list = orderBaseinfoToolService.getListByPage(map);

				map.put("rows", list);// rows键 存放每页记录 list
				// map.put("accInfoDTO", accInfoDTO);
				return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询用户日志信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getChangeLogList")
	@ResponseBody
	public String getChangeLogList(@RequestParam String memberId, HttpServletRequest request) {

		try {
			Map<String, Object> map = new HashMap<>();

			if (StringUtil.isNotEmpty(memberId)) {
				// 查询账号（钱包）信息根据memberId

				// AccInfoDTO
				// accInfoDTO=accInfoToolService.getWalletIndex(Long.valueOf(memberId));

				// memberId
				map.put("memberId", Long.valueOf(memberId));
				// 设置总记录数
				map.put("total", memberChangeLogToolService.getTotal(map));
				// 设定分页,排序
				setCommParameters(request, map);
				List<MemberChangeLogDTO> list = memberChangeLogToolService.getBySearch(map);

				map.put("rows", list);// rows键 存放每页记录 list
				// map.put("accInfoDTO", accInfoDTO);
				return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加修改同一个方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveMore")
	@ResponseBody
	public String saveMore(MemberBaseinfoDTO dto, HttpServletRequest request) {
		try {
			SysRegisterUser regUser = (SysRegisterUser) request.getSession()
					.getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			dto.setIp(regUser.getUserIP());
			// Map map=new HashMap();

			String cityName = dto.getCityName();
			if (cityName != null && !cityName.trim().equals("")) {
				Area area = areaToolService.getByAreaName(cityName);
				if (area != null && area.getAreaID() != null && !area.getAreaID().equals("")) {
					dto.setCcityId(Long.valueOf(area.getAreaID()));
				} else {
					return "cCityName";
				}
			}
			if (dto.getUserType() != null && dto.getUserType() == 2
					&& (dto.getCompanyName() == null || dto.getCompanyName().trim().equals(""))) {
				return "companyName";
			}
			String memberId = request.getParameter("memberId");
			if (null != memberId && !memberId.equals("")) {
				dto.setMemberId(Long.valueOf(memberId));
				dto.setUpdateTime_string(DateUtil.getSysDateString());
				dto.setUpdateUserId(regUser.getUserID());// 用户ID，需要规划怎么填写
			} else {
				dto.setCreateTime_string(DateUtil.getSysDateString());
				dto.setCreateUserId(regUser.getUserID());// 用户ID，需要规划怎么填写
			}
			MemberBaseinfoDTO memberBaseinfoDTO = memberBaseinfoToolService.getById(memberId);
			int i = memberBaseinfoToolService.updateMemberBaseinfoDTO(dto);

			if (i > 0) {
				MemberCertifiDTO mc = new MemberCertifiDTO();
				mc.setMemberId(dto.getMemberId());
				mc.setStatus(dto.getIsAuth());
				// 修改农速通用户的时候，修改企业的mobile,企业的名称，联系人姓名
				mc.setMobile(memberBaseinfoDTO.getMobile());
				mc.setCompanyName(dto.getCompanyName());
				mc.setLinkMan(memberBaseinfoDTO.getRealName());

				// Map<String, Object> q=new HashMap<String, Object>();
				// q.put("memberId", mc.getMemberId());
				// q.put("startRow",0);
				// q.put("endRow",10);
				// //判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
				// List<MemberCertifiDTO>
				// list=memberCertifiToolService.getBySearch(q);
				// if(null!=list && list.size()>0){
				// MemberCertifiDTO mcdto=list.get(0);
				// mc.setCertifiId(mcdto.getCertifiId());
				// memberCertifiToolService.updateMemberCertifiDTO(mc);
				// }else{
				// MemberCertifiDTO mcd=new MemberCertifiDTO();
				// mcd.setType(dto.getUserType()+"");
				// mcd.setCompanyName(dto.getCompanyName());
				// mcd.setLinkMan(dto.getRealName());
				// mcd.setMobile(dto.getMobile());
				// memberCertifiToolService.addMemberCertifiDTO(mc);
				// }

				// 判断是否经过认证，没有则直接新增一条新的认证信息，有则更改状态
				MemberCertifiDTO memberCertifiDTO = memberCertifiToolService.getByUserId(mc.getMemberId() + "");
				if (null != memberCertifiDTO) {
					mc.setCertifiId(memberCertifiDTO.getCertifiId());
					memberCertifiToolService.updateMemberCertifiDTO(mc);
				} else {
					MemberCertifiDTO mcd = new MemberCertifiDTO();
					mcd.setType(dto.getUserType() + "");
					mcd.setCompanyName(dto.getCompanyName());
					mcd.setLinkMan(dto.getRealName());
					mcd.setMobile(dto.getMobile());
					memberCertifiToolService.addMemberCertifiDTO(mc);
				}

				String shopName = (String) request.getParameter("shopName");
				String marketId = (String) request.getParameter("marketId");
				if (null != dto.getLevel() && dto.getLevel() == 4) {// 产地供应商，默认写入市场为3
					marketId = "3";
				}
				if (shopName != null && !shopName.equals("") && marketId != null && !marketId.equals("")) {
					BusinessBaseinfoEntity bb = new BusinessBaseinfoEntity();
					bb.setUserId(Long.valueOf(dto.getMemberId()));
					bb.setShopsName(request.getParameter("shopName"));
					bb.setCreateTime(new Date());
					// 判断是否已经有商铺，没有则直接新增。仅仅设定一个店名，和当前用户ID。
					BusinessBaseinfoDTO bDto = businessBaseinfoToolService
							.getByUserId(String.valueOf(dto.getMemberId()));

					if (null != bDto) {
						reBusinessMarketToolService.deleteByBusinessId(bDto.getBusinessId());
						ReBusinessMarketDTO rbm = new ReBusinessMarketDTO();
						rbm.setBusinessId(bDto.getBusinessId());
						rbm.setMarketId(Long.valueOf(marketId));
						reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
					} else {
						Long lo = businessBaseinfoToolService.addBusinessBaseinfoEnt(bb);
						ReBusinessMarketDTO rbm = new ReBusinessMarketDTO();
						rbm.setBusinessId(lo);
						rbm.setMarketId(Long.valueOf(marketId));
						reBusinessMarketToolService.addReBusinessMarketDTO(rbm);
					}
				}
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}
