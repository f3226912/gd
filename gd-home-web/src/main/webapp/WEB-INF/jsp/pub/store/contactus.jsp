<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con-right">
	<div class="right-m">
		<p class="right-m-t">联系我们</p>
		<p class="right-m-p">
			联 系 人：${busiInfo.contact } <br>
			电 话：${busiInfo.telephone } <br>
			手机：
			<c:if test="${isLogin }">
				${busiInfo.mobile } 
			</c:if>
			<c:if test="${!isLogin }">
				<span class="mob-btn showLoginDialog">
				<span class="mob-mark"></span>登录后可见</span> 
			</c:if>
			<br>
			电子邮箱：${busiInfo.email } <br>
			地 址：${busiInfo.province } ${busiInfo.city } ${busiInfo.area } ${busiInfo.address } <br>
			邮 编：${busiInfo.zipCode } <br>
<%-- 			商铺网址：<a href="${busiInfo.shopsUrl }"><span class="bule">${busiInfo.shopsUrl }</span></a>
 --%>		</p>
	</div>
</div>