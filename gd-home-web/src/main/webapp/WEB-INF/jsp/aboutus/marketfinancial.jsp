<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>农产品供求信贷及保险服务_金融服务_谷登农批网</title>
	<meta name="keywords" content="农业金融,农业信贷,农业保险,融资,授信,贷款">
	<meta name="description" content="谷登农批网金融服务频道是面对谷登农批平台用户提供信贷、保险业务，包括：融资、授信、贷款、保险等各类金融业务，解决谷登农批平台用户资金困难、农产品安全风险。谷登农批网为您提供全方位的金融服务。">
	<%@ include file="../pub/constants.inc" %>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/index-shop.css">
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="nav_title"><span>谷登金融</span>
				<div class="nav_title_show"></div>
			</div>
			<div class="main_menu">
				<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
			<!-- 二级分类 -->
		</div>
	</div>
	<!--head end-->
	<!--slide start-->
	<div class="fin-banner"><img src="${CONTEXT }v1.0/images/shop-images/fin-banner.png"/></div>
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop clearfix">
			<div class="mark-company">
				<div class="mark-financial">
					<h2 class="mark-financial-bt">谷登金融简介</h2>
					<p class="mark-financial-nr">金融是谷登业务架构的重要板块。<br/>
目前农副产品批发行业从业人员普遍面临资金周转问题，而银行、民间借贷都存在门槛高、风险大等困难。谷登从农批从业人员最具
体的困难出发，在征信数据积累的基础上，联合建设银行、农业银行等多家银行推出针对农批从业人员专设的信贷产品，解决农产品
商户和涉农企业的资金周转问题，从而帮助商户抓住商机，将生意做大做强。</p>
				</div>

				<div class="mark-financial mark-financial-mt10">
					<h2 class="mark-financial-bt mark-fin-bor0">谷登金融服务包括</h2>
					<ul class="mark-fin-service">
						<li><i class="mark-fin-service1"></i><p class="mark-fin-btp">征信</p></li>
						<li><i class="mark-fin-service1 mark-fin-ioc2"></i><p class="mark-fin-btp">理财</p></li>
						<li><i class="mark-fin-service1 mark-fin-ioc3"></i><p class="mark-fin-btp">信贷</p></li>
						<li><i class="mark-fin-service1 mark-fin-ioc4"></i><p class="mark-fin-btp">保险</p></li>
						<li><i class="mark-fin-service1 mark-fin-ioc5"></i><p class="mark-fin-btp">保理</p></li>
						<li><i class="mark-fin-service1 mark-fin-ioc6"></i><p class="mark-fin-btp">其他</p></li>
					</ul>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>

	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
	
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>