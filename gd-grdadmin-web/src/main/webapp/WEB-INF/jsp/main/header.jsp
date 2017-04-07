<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="logo-box">
	<img src="${CONTEXT}images/logo.png" alt="谷登">
	<h1 style="color: #3178E6;">${sysName }</h1>
</div>
<div class="u-box">
	<div class="loginWelcome">${systemUserCode }
		您好！| <a href="${CONTEXT}sys/loginOut" id="loginOut">退出</a> 
		<a style="margin-left: 10px;" href="javascript:editPassword()">修改密码</a>
	</div>
</div>
