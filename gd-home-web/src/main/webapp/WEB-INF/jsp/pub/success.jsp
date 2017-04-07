<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>		
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="农产品,农业产品,农产品批发,农产品交易网,农产品网上交易,谷登农批网"/>
		<meta name="description" content="谷登农批网是专业农产品网上交易平台，提供全面农产品批发市场信息，及时发布最新农业产品市场供需资讯，具有高效农产品物流体系，能够安全解决农产品交易中资金问题，作为第一O2O农批平台，谷登农批网为农产品行业人士推出一站式服务。"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>谷登农批网-专注农产品批发及农业产品网上交易平台-成功</title>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css"/>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">申请成功</div>
		</div>
	</div>
	<!--head end-->

	<div class="wrap-1170 login-wrap i-box-shadow clearfix getpwd-wrapa">
		<div class="getpwd-box">
			
			<div class="tab-cntent">
				<div class="get-tips">申请成功啦！等待审核！</div>
				<div class="get-tips-pce clearfix">
					<ul class="step-bar stepnum4 current-step4 ">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">申请</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">审核</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">审核通过</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item4"><span class="step-num">4</span><span class="step-txt">显示友情链接</span></li>
			        </ul>
	       		</div>
				<div class="reg-result-box">
					<h3 class="com-result-t"><span class="result-ico-succ"></span>申请成功！</h3>
					<p>申请成功了您现在可以<a href="${CONTEXT}login/initLogin" class="reg-r-link">登录</a>或进入<a href="${CONTEXT}index" class="reg-r-link">谷登农批网首页</a></p>
				</div>
			</div>
			
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<script type="text/javascript">
document.onkeydown = function () {
    if (window.event && window.event.keyCode == 8) {
        window.event.returnValue = false;
    }
}
</script>
</html>
