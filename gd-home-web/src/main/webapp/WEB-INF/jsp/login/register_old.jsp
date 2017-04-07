<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css"/>
		<title>注册</title>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">欢迎注册</div>
		</div>
	</div>
	<!--head end-->

	<div class="login-wrap wrap-1170 clearfix">
		<div class="reg-box  i-box-shadow clearfix">
			<div class="reg-l-left">
				<form name="register" action="${CONTEXT }login/register" method="post" data-url="${CONTEXT }login/register" >
					<ul class="reg-list">
						<li class="clearfix">
							<div class="inp-tit">用户名：</div>
							<div class="fl">
								<span class="bor">
									<input type="text" name="username" class="input-text input-reg" placeholder="请输入用户名" maxlength="20" data-err="请输入用户名由6-16个字母、数字、下划线组合" data-po="请输入用户名由6-16个字母、数字、下划线组合">
									<input type="hidden" id="BackUrl" name="BackUrl" value="${BackUrl }">	
								</span>
								<span class="info mt3"></span>
							</div>
						</li>						
						<li class="clearfix">
							<div class="inp-tit">手机号码：</div>
							<div class="fl">
								<span class="bor">
									<input type="text" name="mobile" class="input-text input-reg" placeholder="请输入手机号码" maxlength="11" data-err="请输入正确的手机号码" data-po="请输入手机号码"/>	
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-reg" type="password" placeholder="请输入登录密码" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="password" maxlength="20" data-po="6~20位字符，至少包含数字、大写字母、小写字母、符号" data-err="请输入6~20个字符的密码">
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">确认密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-reg" type="password" placeholder="请再次输入登录密码" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="confirmPassword" maxlength="20" data-po="请再次输入密码" data-err="请输入6~20个字符的密码">
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						
						<li class="clearfix">
							<div class="inp-tit">手机验证码：</div>
							<div class="fl">
								<span class="bor mr01">
									<input type="text" name="mobileVerifyCode" class="input-text mobileVerifyCode-input" placeholder="请输入短信验证码" maxlength="20" data-err="请输入正确的短信验证码" data-po="请输入短信验证码"/>	
								</span>

								<a href="javascript:;" id="yumobiles" class="mcodebtn" data-url="${CONTEXT }login/sendCode" data-po="短信验证码已发送到您的手机">获取验证码</a>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix agree-li">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<span> <label class="leb-txt2"><input class="account_agreeProtocol" value="1" checked="checked" type="checkbox" name="agre">我已阅读并同意《<a href="javascript:;" class="f-c-12adff js-showAgreement">谷登农批服务条款</a>》</label></span>
							</div>
						</li>
						
						<li class="clearfix">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<a href="javascript:;" class="btn-red-dia btn-reg">免费注册</a>
							</div>
						</li>
						
					</ul>
				</form>	
			</div>
			
			<div  class="reg-l-right">
				<p class="reg-l-p">我已经有账号，<a href="${CONTEXT}login/initLogin" title="点此登录">点此登录</a></p>
			</div>
		</div>
	</div>
	<!--注册成功-->

	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${CONTEXT}v1.0/js/gdui.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/login/register.js"></script>
</html>