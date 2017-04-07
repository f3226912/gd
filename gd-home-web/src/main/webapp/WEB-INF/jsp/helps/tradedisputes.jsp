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
					<h5 class="helps-right-tit">注册新用户</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">会员账户注册不成功，原因可能是：</p>
					<p class="helps-txt">1、填写会员资料不正确，填写好相关内容后页面上会出现是否正确的提示，如果正确，会在该项内容后打勾；如果错误，右边会出现相关的错误提示，您可以根据提示内容修改，如图举例：您使用的浏览器不是ie的，请更换成ie浏览器；</p>
					<p class="helps-txt">2、如果您已经使用ie浏览器，您可以根据以下链接进行浏览器设置。</p>
					<p class="helps-txt">会员账户注册不成功，原因可能是：</p>
					<p class="helps-txt">1、填写会员资料不正确，填写好相关内容后页面上会出现是否正确的提示，如果正确，会在该项内容后打勾；如果错误，右边会出现相关的错误提示，您可以根据提示内容修改，如图举例：您使用的浏览器不是ie的，请更换成ie浏览器；</p>
					<p class="helps-txt">2、如果您已经使用ie浏览器，您可以根据以下链接进行浏览器设置。</p>	
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