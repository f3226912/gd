<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=utf-8" />
<meta name="pragma" content="no-cache"/>
<meta name="cache-control" content="no-cache"/>
<meta name="expires" content="0"/>
<meta name="keywords" content="农产品交易平台登陆,农产品O2O,农产品网登陆,农批网登陆"/>
<meta name="description" content="登录谷登农批网，浏览最新的农产品、商户价格信息、库存信息、交易信息、物流信息等，为你提供一站式信息、交易、物流、金融服务—谷登农批网。"/>
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>欢迎登陆-谷登农批网</title>
<%@ include file="../pub/head.inc" %>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}${gd:formatMarket(marketId) }.html"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">欢迎登录</div>
		</div>
	</div>
	<div class="login-wrap clearfix">
		<div class="wrap-1170 login-bbox">
			<div class="login-act-box"><a href="#"><img src="${CONTEXT}v1.0/images/login-temp.jpg" alt="" width="1170" height="428" /></a></div>	
			<div class="login-box">
				<div class="page-login">
			        <h2 class="dla-login-t clearfix"><a class="dla-login-t-link" href="${CONTEXT}login/initRegister" style="display:none;">还没有账号，去注册</a><span class="dla-login-ttxt">登录谷登农批网</span></h2>
			        <form name="login" action="${CONTEXT }login/commitLogin" method="post">
			        
			        <ul class="login-list">
			            <li>
			                <div class="clearfix">
			                	<span class="p-login-t">手机号：</span>
			                    <span class="bor">
			                        <!--<input type="text" id="loginName" name="loginName" class="input-text input-text-login" placeholder="账户名/手机号码" maxlength="16" data-err="请输入6~16个字符的用户名（一个汉字占用2个字符）"> -->
			                        <input type="text" id="loginName" name="loginName" class="input-text input-text-login" placeholder="手机号码"  maxlength="11" data-err="手机号码格式不正确">
			                        <input type="hidden" id="BackUrl" name="BackUrl" value="${BackUrl }">  
			                    </span>
			                </div>
			            </li>
			            <li>
			                <div class="clearfix">  
			                	<span class="p-login-t">密&nbsp;&nbsp;&nbsp;码：</span> 
			                    <span class="bor mr01">
			                        <input type="password" id="loginPassword" name="loginPassword" class="input-text input-text-login" placeholder="密码">
			                    </span>
			                </div>
			            </li>
			            <li>
							<div class="l-inp-wrap clearfix">							
								<span class="bor mr01">
									<input type="text" id="randCode" name="randCode" class="input-text input-text-code"  placeholder="验证码" />
									<img src="${CONTEXT}login/initImg" data-img="${CONTEXT}login/initImg" width="75" height="33" id="code_img" onclick="changeAuthCode();">
									<span>看不清？<a href="javascript:changeAuthCode();" class="captcha-change">换一张</a></span>
								</span>
								<div class="login-tips"></div>
							</div>
						</li>
			            <li><input class="btn-red-dia btn-dia-login" style="cursor:pointer;" type="button" value="立即登录"></li>
			            <li class="clearfix dia-log-tool">
			                <label class="leb-txt">
			                    <input type="checkbox" value="" checked="checked" class="ck-temp">记住账号</label>
			                <a class="login-link fr" href="${CONTEXT}login/initGetPassword1">忘记登录密码？</a>
			            </li>
			        </ul>
			        </form>
			    </div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script type="text/javascript" src="${CONTEXT}js/login/login.js"></script>
</html>