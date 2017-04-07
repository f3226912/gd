<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/jsp/pub/helps/title.jsp"></jsp:include>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css"/>
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="main_menu check-nav">
			<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--head end-->
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
			<jsp:include page="/WEB-INF/jsp/pub/helps/leftnav.jsp"></jsp:include>
			<div class="fl">
				<div class="con-right products-con helps-cont">
					<h5 class="helps-right-tit">账户密码</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">注册时用户名和密码有什么要求？</p>
					<p class="helps-txt">密码由6~16个字符组成，可使用数字和字母，字母区分大小写，不可以使用特殊字符。</p>
					<p class="helps-txt">收取不到验证码怎么办？</p>
					<p class="helps-txt">首先请您检查手机号是否填写正确；同时，请您查看手机短信容量是否满或是否被拦截；也有可能是验证通道繁忙建议可以稍晚点再试。您也可以联系网站客服。</p>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>


	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>