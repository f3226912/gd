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
					<h5 class="helps-right-tit">实名认证</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">什么是实名认证？</p>
					<p class="helps-txt">实名认证是个人或企业向我司提供证明自己真实身份的资料（如身份证、营业执照、组织机构代码证的扫描件）经我司人工审核通过后，在网页上显示实名认证标识。实名认证分为企业实名认证和个人实名认证,可以同时通过个人和企业实名认证。</p>
					<p class="helps-txt">为什么要做实名认证？不做有什么影响？</p>
					<p class="helps-txt">新注册的通过实名认证后可以获得更高的会员等级，并且在交易过程对双方更有保障。</p>
					<p class="helps-txt">实名认证有哪几种方式？</p>
					<p class="helps-txt">实名认证有个人实名认证和企业实名认证两种方式，可以同时通过个人和企业实名认证，这样能提升您在谷登农批网发布产品的排名，提升信誉。</p>	
					<p class="helps-txt">营业执照、组织机构代码证、身份证过期了能认证吗？</p>
					<p class="helps-txt">为了保障买卖双方的权益，请上传真实有效的证件。</p>
					<p class="helps-txt">如何实名认证？</p>
					<p class="helps-txt">企业实名认证：请提供真实有效的营业执照和组织机构代码证，或三证合一的的营业执照（营业执照、组织机构代码证、税务登记证合为一个证件）照片/扫描件；</p>
					<p class="helps-txt">个人实名认证：请提供真实有效的中华人民共和国二代身份证的正反两面照片/扫描件。</p>
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