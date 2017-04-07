<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%@ include file="../../pub/constants.inc"%>
<%   
  String uri=request.getRequestURI();   
  uri=uri.substring(uri.lastIndexOf("/")+1);
  request.setAttribute("uri", uri);
  %>
<%@ include file="/WEB-INF/jsp/pub/loginForm.jsp" %>
<div class="banner01">
	<c:if test="${empty busiInfoUrl }">
		<c:if test="${busiInfo.level!=4 }">
		<img src="${CONTEXT}v1.0/images/shop-images/banner01.jpg"
			width="1170px" height="160px">
		</c:if>
		<c:if test="${busiInfo.level==4 }">
		<img src="${CONTEXT}v1.0/images/shop-images/banner01.jpg"
			width="1170px" height="160px">
		</c:if>
	</c:if>
	<c:if test="${not empty busiInfoUrl }">
		<img src="${imgHostUrl}${busiInfoUrl }"
			width="1170px" height="160px">
	</c:if>
</div>
<div class="company-bname">
	<h1 class="company-name">${busiInfo.shopsName }</h1>
</div>
<div class="shop-nav">
	<ul class="nav-list">
		<li class="fl list <% if (uri.equals("storeIndex.jsp")){ %> bgc<%} %>" ><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid }.html">首页</a></li>
		<li class="fl list <% if (uri.equals("storeInfo.jsp")){ %> bgc<%} %>"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/intro.html">商铺介绍</a></li>
		<li id="nav-list-2" class="fl list <% if (uri.equals("storeProduct.jsp")){ %> bgc<%} %>"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/supply.html">供应产品</a></li>
		<li class="fl list <% if (uri.equals("storeContact.jsp")){ %> bgc<%} %>"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/contact.html">联系我们</a></li>
	</ul>
	<div class="cl"></div>
</div>
<div class="banner02" style="display:none;">
	<c:if test="${busiInfo.level!=4 }">
		<img src="${CONTEXT}v1.0/images/shop-images/banner02.jpg"
			width="1170px" height="310px">
	</c:if>
	<c:if test="${busiInfo.level==4 }">
		<img src="${CONTEXT}v1.0/images/shop-images/banner04.jpg"
			width="1170px" height="310px">
	</c:if>
</div>