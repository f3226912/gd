<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>404</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="pub/constants.inc" %>
	<%@ include file="pub/head.inc" %>
	<%@ include file="pub/tags.inc" %>
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<div class="system404">
		<img src="${CONTEXT }v1.0/images/img404.jpg" alt="404">
		<p class="tips1">休息一下，喝口茶，我们马上就好！</p>
		<p class="tips2">如有疑问，请拨打全国服务热线：4007600800进行咨询。</p>
	</div>
	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>