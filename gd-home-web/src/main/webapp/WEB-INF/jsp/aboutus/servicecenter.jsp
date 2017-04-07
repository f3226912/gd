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
					<p>客服中心</p>
				</div>
			</div>
			
			<div class="serve-bg">
				<p class="serve-tel"><span class="serve-telico"></span>客服热线<span class="serve-telnum">4007-600-800</span></p>
				<div class="ser-c-time">
					<p style="line-height:30px;padding-top:10px;">客服邮箱：4007600800@gdeng.cn</p>
					人工服务时间：周一至周日：9:00-17:30<br/>					
					<p><span class="serbtn-qq-ico"></span><a class="btn-qq" href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODAzNjk2N18zNDA1NjlfNDAwNzYwMDgwMF8yXw" target="_blank">立即咨询</a></p>
					
				</div>
			</div>
		</div>
	</div>


	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
	
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>