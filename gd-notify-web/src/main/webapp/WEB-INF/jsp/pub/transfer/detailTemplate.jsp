<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<div class="main-con main-bot-mar">
<div class="allDetails">
	<ul>
		<li class="frist">发货信息</li>
		<li>
			<div class="left">联系人:</div><div class="right">${mem.contactName }</div>
		</li>
		<li>
			<div class="left">发货地:</div><div class="right">${mem.s_provinceName }${mem.s_cityName }${mem.s_areaName }${mem.s_address_detail }</div>
		</li>
		<li>
			<div class="left">收货地:</div><div class="right">${mem.f_provinceName }${mem.f_cityName }${mem.f_areaName }${mem.f_address_detail }</div>
		</li>
	</ul>
	<ul>
		<li class="frist">货物信息</li>
		<li>
			<div class="left">货物分类</div><div class="right">${mem.goodsTypeString }</div>
		</li>
		<c:if test="${not empty mem.totalWeight && mem.totalWeight != 0 }">
		<li>
			<div class="left">总重量</div><div class="right">${mem.totalWeight }吨</div>
		</li>
		</c:if>
		<c:if test="${not empty mem.totalSize && mem.totalSize != 0 }">
		<li>
			<div class="left">总体积</div><div class="right">${mem.totalSize } <span class="weig weig2">&#109;<span class="lif">3</span></span></div>
		</li>
		</c:if>
	</ul>
	<ul>
		<li class="frist">用车信息</li>
		<li>
			<div class="left">装车时间:</div><div class="right">${mem.sendDateString }&nbsp;${mem.sendDateTypeString }</div>
		</li>
		<li>
			<div class="left">所需车型:</div><div class="right">${mem.carTypeString }</div>
		</li>
	</ul>
</div>
<div class="line"></div>
<div class="intentionalPrice">
	<div class="left">意向价格:</div><div class="right">
		<c:if test="${mem.price==0 }">
			${mem.priceString }
		</c:if>
		<c:if test="${mem.price!=0 }">
			${mem.priceString }${mem.priceUnitTypeString }
		</c:if>
	</div>
</div>
<div class="line"></div>
<div class="remarks">
	<div class="left">备注:</div><div class="right">${mem.remark }</div>
</div>
