<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%@ include file="constants.inc" %>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<!--head star-->
<div class="head-wrap">
	<div class="wrap-1170 header clearfix">
		<div class="head-l">
			<div class="sigout-box">
				<span class="mr5">您好，欢迎来到谷登农批！</span>
				<a href="${CONTEXT}login/initLogin" class="head-link">登录</a>
				<span class="head-spilt1">|</span>
				<a href="${CONTEXT}login/initRegister" class="head-link">注册</a>	
			</div>
			<div class="sigin-box" style="display:none;">
				您好，<a href="${CONTEXT}userCenter" class="sigin-name sigin-mobile">${sessionScope.loginMember.account}</a><a onclick="Login.loginout()" href="javascript:;"class="ml10">退出</a>
			</div>
		</div>
		<div class="head-r clearfix">
			<a href="${CONTEXT}${gd:formatMarket(marketId) }.html" class="head-link2">首页</a>
			<span class="head-spilt2"></span>
			<a href="${CONTEXT}about/app" class="head-link2 head-linkico">移动客户端</a>
			<span class="head-spilt2"></span>
			<a href="${CONTEXT}userCenter" class="head-link2">我的谷登</a>
			<span class="head-spilt2"></span>
			<a href="${CONTEXT}service.html" class="head-link2">客服中心</a>
			<span class="head-spilt2"></span>
			<a href="${CONTEXT}help.html" class="head-link2">帮助中心</a>	
		</div>
	</div>
</div>