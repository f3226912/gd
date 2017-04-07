<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="x-rim-auto-match" content="none">
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="cache-control" content="no-cache"> 
		<meta http-equiv="expires" content="0">
		<title>
			<c:choose>
				<c:when test="${empty adName }">广告丢失</c:when>
				<c:otherwise>${adName }</c:otherwise>
			</c:choose>
		</title>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }/v1.0/css/advert/global.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }/v1.0/css/advert/style.css"/>
	</head>
	<body class="wrapList">
		<c:choose>
			<c:when test="${empty adName }">
				没有有效的广告菜单
			</c:when>
			<c:otherwise>
				<c:forEach items="${adAdvertises }" var="adAdvertise">
					<a href="${adAdvertise.jumpUrl }"><img class="listImg" src="${imgRootUrl }${adAdvertise.adUrl }"/></a>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</body>
</html>
