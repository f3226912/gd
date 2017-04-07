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
					<p class="helps-txt">如何注册谷登农批网的会员？</p>
					<p class="helps-txt">1.登录谷登农批网，点击注册按钮</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic1.png" alt="">
					<p class="helps-txt">2.根据您的经销类型特征进行选择：</p>
					<p class="helps-txt">采购商：从农批市场进货的买家 </p>
					<p class="helps-txt">农批商：农批市场有实体商铺的经营户 </p>
					<p class="helps-txt">供应商：为农批商提供产地货源的种养大户、合作社和贩销大户</p>

					<p class="helps-txt">3．按照流程及要求填写相关信息，进行注册。</p>
					<p class="helps-txt">注册完成默认采购商身份，可以查询批发商的产品信息，并关注批发商的产品和店铺。</p>
					<p class="helps-txt">如需发布供应信息，需注册成为农批商或产地供应商。</p>

					<p class="helps-txt">谷登农批网采购商、农批商、供应商功能有什么区别？</p>
					<p class="helps-txt">采购商：可以查看农批商发布的农产品价格等信息，也可以随时关注批发商的</p>
					<p class="helps-txt">产品和商铺，可发布物流需求，让司机主动联系您托运货物。</p>
					<p class="helps-txt">批发商：拥有自己的农批市场商铺，24套店招模板可以供您选择，并可以发布</p>
					<p class="helps-txt">批发产品，让更多才采购商找到您。可发布物流需求，让司机主动联系您托运货物。</p>
					<p class="helps-txt">供应商：拥有供应商商铺，24套店招模板可以供您选择，为农批商提供产地货源，您发布的产品在批</p>
					<p class="helps-txt">发商的会员中心我要进货中看到。可发布物流需求，让司机主动联系您托运货物。</p>

					<p class="helps-txt">采购商如何开通商铺？</p>
					<p class="helps-txt">第一步点击开通商铺</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic2.png" alt="">
					<p class="helps-txt">第二步点击根据提示马上完善</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic3.png" alt="">
					<p class="helps-txt">第三步完善商铺基本信息，可以选择农批商或者产地供应商，根据提示填写或勾选选项，保存后，店铺将开通，温馨提示：农批商与产地供应商不能互相转化，填写时请注意</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic4.png" alt="">
					<p class="helps-txt">第四步保存成功查看店铺</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic5.png" alt="">
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