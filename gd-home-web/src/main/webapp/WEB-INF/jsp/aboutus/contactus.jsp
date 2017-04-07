<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	
	<meta charset="UTF-8">
	<title>联系我们</title>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
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
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
	
			<!--slide start-->
			<div class="con-left fl">
				<div class="con-left-tit check-img logi-right helps">
					<p>关于我们</p>
				</div>
				<div class="con-left-tit helps-left helps-left1 helpa-aboutus">
					<h2 class="shopname helps-left-tit helps-white"><a href="${CONTEXT }about/index">关于我们</a></h2>
					<h2 class="shopname helps-left-tit"><a href="${CONTEXT }about/contactus">联系我们<span class="helps-ico02"></span></a></h2>
				</div>
			</div>
			
			<div class="fl">
				<!-- 联系我们 -->
				<div class="con-right products-con helps-cont contact-us-con">
					<p class="helps-txt helps-public-con helps-cont-title">联系我们</p>
					<div class="baidu-map">
						<img src="${CONTEXT }v1.0/images/shop-images/baidu-map.png" width="530px" height="350px" alt="">
					</div>
					<p class="helps-txt helps-public-con"></p>
					<p class="helps-txt helps-public-con cont-us">地址：深圳市福田区福华一路98号卓越大厦5楼509</p>
					<p class="helps-txt helps-public-con cont-us">邮编：518048 </p>
					<p class="helps-txt helps-public-con cont-us">客服中心热线：4007-600-800</p>
					<p class="helps-txt helps-public-con cont-us">电话/Tel：（0755）2380 2308</p>
					<p class="helps-txt helps-public-con cont-us">FaX：（0755）2380 2318</p>
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