package com.gudeng.commerce.gd.customer.service.impl.statis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.dto.MemberMessageDTO;
import com.gudeng.commerce.gd.customer.enums.MessageTemplateEnum;
import com.gudeng.commerce.gd.customer.service.statis.MemberMessageService;
import com.gudeng.commerce.gd.customer.service.statis.PvStatisticBusinessService;
import com.gudeng.commerce.gd.customer.util.CommonUtils;
import com.gudeng.commerce.gd.customer.util.Constant;
import com.gudeng.commerce.gd.customer.util.Constant.Alidayu;
import com.gudeng.framework.dba.util.DalUtils;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 商铺浏览量业务
 * 
 * @author Ailen
 *
 */
public class PvStatisticBusinessServiceImpl implements PvStatisticBusinessService {

	@Autowired
	private BaseDao<?> baseDao;

	@Autowired
	private MemberMessageService memberMessageService;

	@Autowired
	private IGDBinaryRedisClient redisClient;

	@Override
	public void addPv(BusinessPvStatisDTO businessPvStatisDTO) {

		/*
		 * 根据页面 和 商铺ID 获得对应的统计数据
		 */
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromPage", businessPvStatisDTO.getFromPage());
		paramMap.put("businessId", businessPvStatisDTO.getBusinessId());
		paramMap.put("addPv", businessPvStatisDTO.getAddPv());

		// 读取数据
		BusinessPvStatisDTO result = baseDao.queryForObject("PvStatisticBusinessService.getPvStatisticBusiness",
				paramMap, BusinessPvStatisDTO.class);

		if(!"2".equals(businessPvStatisDTO.getFromPage())) {
			baseDao.execute("PvStatisticBusinessService.addPvForBusiness", paramMap);
		}
		
		/*
		 * 判断是否存在对应的统计数据
		 */
		if (result == null) { // 不存在 则添加

			paramMap = DalUtils.convertToMap(businessPvStatisDTO);

			baseDao.execute("PvStatisticBusinessService.addPvStatisticBusiness", paramMap);
		} else { // 存在 则修改
			paramMap.clear();

			/*
			 * 判断是否达到发短信的阶段 金牌会员拥有
			 */
			if (result.getMemberGrade() != null && result.getMemberGrade() == 1) {
				paramMap.clear();
				paramMap.put("businessId", result.getBusinessId());

				/*
				 * 获得点击次数 阶梯数 读取数据库,获得当前店铺所有页面的总浏览量
				 */
				Long visitCount = baseDao.queryForObject("PvStatisticBusinessService.getPvStatisticBusinessCount",
						paramMap, Long.class);

				int count = switchCount(visitCount, businessPvStatisDTO.getAddPv() + visitCount);
				/*
				 * 点击量不为0 发送短信
				 */
				if (count != 0) {
					Map<String, Object> params = new HashMap<>();
					params.put(Alidayu.ACCOUNT,
							result.getRealName() == null ? result.getMobile() : result.getRealName());
					params.put(Alidayu.COUNT, "" +count);

					/*
					 * 发送短信
					 */
					sendMsg(result.getMemberId(), result.getMobile(), Constant.Alidayu.MESSAGETYPE3,
							MessageTemplateEnum.PVSTATISTOP, params);

				}

			}

			paramMap.clear();
			
			paramMap.put("id", result.getId());
			paramMap.put("addPv", businessPvStatisDTO.getAddPv()); // 添加的浏览量

			baseDao.execute("PvStatisticBusinessService.updatePvStatisticBusiness", paramMap);

		}
	}

	@Override
	public void sendMsg(Long memberId, String mobile, Integer alidauCode, MessageTemplateEnum template,
			Map<String, Object> params) {
		/*
		 * 发送短信
		 */
		Object obj = redisClient.get("GDSMS_CHANNEL"); // 获取通道
		System.out.println("redis channel:###############" + obj);
		String channel = obj == null ? Constant.Alidayu.DEFAULTNO : obj.toString();

		String content = null;

		/*
		 * 拼接对应通道的内容信息
		 */
		if (Constant.Alidayu.REDISTYPE.equals(channel)) {

			// alipay短信
			try {
				content = CommonUtils.alidayuUtil(alidauCode, params); //获取阿里模板
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else { // 其他格式
			content = template.getTemplate(); //获取基本格式模板

			/*
			 * 拼接短信参数
			 */
			Set<String> keys = params.keySet();
			Iterator<String> iterator = keys.iterator();
			
			/*
			 * 遍历参数，替换到内容中
			 */
			while (iterator.hasNext()) {
				String key = iterator.next();

				content = content.replace(key, params.get(key).toString());

			}
		}

		/*
		 * 发送短信
		 */
		CommonUtils.sendMsg(channel, content, mobile);

		/*
		 * 添加数据库记录
		 */
		MemberMessageDTO memberMessageDTO = new MemberMessageDTO();
		memberMessageDTO.setMemberId(memberId);
		memberMessageDTO.setContent(content);
		memberMessageDTO.setCreateUserId("SYS");
		memberMessageDTO.setType(alidauCode);
		memberMessageDTO.setUpdateUserId("SYS");

		memberMessageService.add(memberMessageDTO);
	}

	private int switchCount(Long count, Long oldCount) {
		if (oldCount >= 3800 && count < 3800) {
			return 3800;
		}
		if (oldCount >= 3000 && count < 3000) {
			return 3000;
		}
		if (oldCount >= 2300 && count < 2300) {
			return 2300;
		}
		if (oldCount >= 1700 && count < 1700) {
			return 1700;
		}
		if (oldCount >= 1200 && count < 1200) {
			return 1200;
		}
		if (oldCount >= 800 && count < 800) {
			return 800;
		}
		if (oldCount >= 500 && count < 500) {
			return 500;
		}
		if (oldCount >= 200 && count < 200) {
			return 200;
		}
		return 0;
	}

	@Override
	public void addPvForProduct(Long productId, Integer addPv) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", productId);
		paramMap.put("addPv", addPv);

		baseDao.execute("PvStatisticBusinessService.addPvForProduct", paramMap);
		
		/*
		 * 添加对应的商铺浏览量记录
		 */
		//获取商铺ID
		Long businessId = baseDao.queryForObject("PvStatisticBusinessService.searchBusinessId", paramMap, Long.class);
		
		/*
		 * 设置添加数据
		 */
		BusinessPvStatisDTO dto = new BusinessPvStatisDTO();
		dto.setBusinessId(businessId);
		dto.setAddPv(addPv);
		
		dto.setFromPage("2"); //商品详情页面
		
		//添加商铺浏览量
		/*
		 * 同时判断发送短信
		 */
		addPv(dto);
		
	}
	
	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("PvStatisticBusinessService.getTotal", map, Integer.class);
	}
	
	@Override
	public List<BusinessPvStatisDTO> getBySearch(Map map) throws Exception {
		return  baseDao.queryForList("PvStatisticBusinessService.getBySearch", map, BusinessPvStatisDTO.class);
	}
	
	@Override
	public int getAmountTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("PvStatisticBusinessService.getAmountTotal", map, Integer.class);
	}
	
	@Override
	public List<BusinessPvStatisDTO> getAmountBySearch(Map map) throws Exception {
		return  baseDao.queryForList("PvStatisticBusinessService.getAmountBySearch", map, BusinessPvStatisDTO.class);
	}
	
	@Override
	public void updatePv(Map map) throws Exception {
		baseDao.execute("PvStatisticBusinessService.updatePv", map);
	}

}
