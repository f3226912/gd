<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="gd" uri="/WEB-INF/tags/gd.tld" %>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>
<div style="margin-left: 20px; font-size: 13px" >
	<br>
	订单号: ${orderDTO.orderNo}<br>
	起始地: ${orderDTO.f_address_detail}<br>
	目的地: ${orderDTO.s_address_detail}<br>
	订单类型: ${orderDTO.orderType}<br>
	货物类型：${gd:getGoodsTypeByCode(orderDTO.goodsType)}<br>
	重量: ${orderDTO.totalWeight } <c:if test="${orderDTO.hundredweight==0}">吨</c:if>
							 	  <c:if test="${orderDTO.hundredweight==1}">公斤</c:if><br>
	<c:if test="${orderDTO.totalSize >0}">体积: ${orderDTO.totalSize }立方米<br> </c:if>
	车牌号: ${orderDTO.carNumber }<br>
	订单状态: <c:choose>
				<c:when test="${orderDTO.orderStatus==1}">
					待成交
				</c:when>
				<c:when test="${orderDTO.orderStatus==2}">
					已成交
				</c:when>
				<c:when test="${orderDTO.orderStatus==3}">
					已完成
				</c:when>
				<c:when test="${orderDTO.orderStatus==4}">
					未完成
				</c:when>
				<c:otherwise>
					已取消
				</c:otherwise>
			</c:choose><br>
	接单时间: ${orderDTO.orderTime }<br>
	订单确认时间: ${orderDTO.order_confirmTime }<br>
	订单完成时间: ${orderDTO.order_completeTime }<br>
	发布者用户类型:  ${orderDTO.releaseUserType }<br>
	接单者姓名: ${orderDTO.receiveName }<br>
	接单者电话: ${orderDTO.receiveMobile }<br>
	接单者认证状态: ${orderDTO.receiveNstStatus }<br>
	发布者姓名: ${orderDTO.releaseName }<br>
	发布者电话: ${orderDTO.releaseMobile }<br>
	发布者认证状态: ${orderDTO.releaseNstStatus }<br>
	发布者推荐人姓名: ${orderDTO.releaseName_ed }<br>
	发布者推荐人电话: ${orderDTO.releaseMobile_ed }<br>
	发布者推荐人所属区域: ${orderDTO.releaseAreaName }<br>
	接单者推荐人: ${orderDTO.receiveName_ed }<br>
	接单者推荐人电话: ${orderDTO.receiveMobile_ed }<br>
	接单者推荐人所属区域: ${orderDTO.receiveAreaName }<br>
</div>
