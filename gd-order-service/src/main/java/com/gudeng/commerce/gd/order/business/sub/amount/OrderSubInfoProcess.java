package com.gudeng.commerce.gd.order.business.sub.amount;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ESubType;

/**
 * @Description: TODO(订单补贴信息处理类)
 * @author mpan
 * @date 2015年12月15日 下午2:06:09
 */
public class OrderSubInfoProcess {
	
	/**
	 * @Title: setOrderSubStatus
	 * @Description: TODO(设置订单补贴信息)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型
	 * @param subStatus 补贴状态
	 */
	public static void setOrderSubInfo(OrderBaseinfoDTO orderBase, String memberType, String subStatus) {
		setOrderSubInfo(orderBase, memberType, subStatus, null);
	}
	
	/**
	 * @Title: setOrderSubStatus
	 * @Description: TODO(设置订单补贴信息)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型
	 * @param subStatus 补贴状态
	 * @param subAmount 补贴金额
	 */
	public static void setOrderSubInfo(OrderBaseinfoDTO orderBase, String memberType, String subStatus, Double subAmount) {
		boolean flag = false;
		if (subAmount != null) {
			flag = true;
		}
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			orderBase.setBuySubStatus(subStatus);
			if (flag) {
				orderBase.setSubAmount(subAmount);
			}
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			orderBase.setSellSubStatus(subStatus);
			if (flag) {
				orderBase.setSellSubAmount(subAmount);
			}
		} else if (EMemberType.SUPPLIER.getCode().equals(memberType)) {
			orderBase.setSuppSubStatus(subStatus);
			if (flag) {
				orderBase.setSuppSubAmount(subAmount);
			}
		}
	}
	
	/**
	 * @Title: setOrderGoodsSubInfo
	 * @Description: TODO(设置商品补贴额)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param goodsSubAmt 商品补贴额
	 */
	public static void setOrderGoodsSubInfo(OrderProductDetailDTO orderGoods, String memberType, Double goodsSubAmt, Integer ruleId, String subType, String subUnit, String subTipInfo) {
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			if (goodsSubAmt != null) {
				orderGoods.setSubAmount(goodsSubAmt);
			}
			if (ruleId != null) {
				orderGoods.setRuleId(ruleId);
			}
			if (StringUtils.isNotBlank(subType)) {
				orderGoods.setSubType(subType);
				orderGoods.setSubRule(ESubType.getDescByCode(subType));
			}
			if (StringUtils.isNotBlank(subUnit)) {
				orderGoods.setSubUnit(subUnit);
			}
			if (StringUtils.isNotBlank(subTipInfo)) {
				orderGoods.setSubTipInfo(subTipInfo);
			}
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			if (goodsSubAmt != null) {
				orderGoods.setSellSubAmount(goodsSubAmt);
			}
			if (ruleId != null) {
				orderGoods.setSellRuleId(ruleId);
			}
			if (StringUtils.isNotBlank(subType)) {
				orderGoods.setSellSubType(subType);
				orderGoods.setSellSubRule(ESubType.getDescByCode(subType));
			}
			if (StringUtils.isNotBlank(subUnit)) {
				orderGoods.setSellSubUnit(subUnit);
			}
			if (StringUtils.isNotBlank(subTipInfo)) {
				orderGoods.setSellSubTipInfo(subTipInfo);
			}
		} else if (EMemberType.SUPPLIER.getCode().equals(memberType)) {
			orderGoods.setSuppSubAmount(goodsSubAmt);
		}
	}
	
	/**
	 * @Title: setOrderGoodsSubInfo
	 * @Description: TODO(设置商品补贴额)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param goodsSubAmt 商品补贴额
	 */
	public static void setOrderGoodsSubInfo(OrderProductDetailDTO orderGoods, String memberType, Double goodsSubAmt) {
		setOrderGoodsSubInfo(orderGoods, memberType, goodsSubAmt, null, null, null, null);
	}
	
	/**
	 * @Title: setOrderGoodsSubInfo
	 * @Description: TODO(设置商品补贴信息)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param ruleId 补贴规则ID
	 * @param subType 补贴类型
	 */
	public static void setOrderGoodsSubInfo(OrderProductDetailDTO orderGoods, String memberType, Integer ruleId, String subType) {
		setOrderGoodsSubInfo(orderGoods, memberType, null, ruleId, subType, null, null);
	}
	
	/**
	 * @Title: setOrderGoodsSubInfo
	 * @Description: TODO(设置商品补贴信息)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param subUnit 补贴单位
	 * @param subTipInfo 补贴提示信息
	 */
	public static void setOrderGoodsSubInfo(OrderProductDetailDTO orderGoods, String memberType, String subUnit, String subTipInfo) {
		setOrderGoodsSubInfo(orderGoods, memberType, null, null, null, subUnit, subTipInfo);
	}
	
	/**
	 * @Title: setOrderGoodsSubTipInfo
	 * @Description: TODO(设置商品补贴信息)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param subTipInfo 补贴提示信息
	 */
	public static void setOrderGoodsSubUnit(OrderProductDetailDTO orderGoods, String memberType, String subUnit) {
		setOrderGoodsSubInfo(orderGoods, memberType, null, null, null, subUnit, null);
	}
	
	/**
	 * @Title: setOrderGoodsSubTipInfo
	 * @Description: TODO(设置商品补贴信息)
	 * @param orderGoods 商品信息
	 * @param memberType 会员类型
	 * @param subTipInfo 补贴提示信息
	 */
	public static void setOrderGoodsSubTipInfo(OrderProductDetailDTO orderGoods, String memberType, String subTipInfo) {
		setOrderGoodsSubInfo(orderGoods, memberType, null, null, null, null, subTipInfo);
	}
	
}
