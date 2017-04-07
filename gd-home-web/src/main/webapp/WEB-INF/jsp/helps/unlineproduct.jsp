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
					<h5 class="helps-right-tit">未上架的产品</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">如何管理未上架中的产品？</p>
					<p class="helps-txt">已下架的产品中 为点击下架或者产品上架的时间到期后的产品会进入未上架的产品中，您可以进行修改或者，，上架，删除操作</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic12.png">
					<p class="helps-txt">审核中的产品是您发布的新商品还在谷登农批网客服审核的过程中，请您耐心等待，无特殊情况，审核会在工作日24小时内完成，您可以进行修改或删除</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic13.png">
					<p class="helps-txt">审核不通过的产品 是您的产品与类目不相符，产品名称与图片不相符，产品图片不符合规格等原因，被驳回的状态，请您修改后重新审核通过后，才能上架</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic14.png">
					
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