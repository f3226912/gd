<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
	<base href="${CONTEXT}" >
		<meta charset="utf-8">
		<title>页面找不到</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	
		<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="v1.0/module/mui/examples/hello-mui/css/app.css"/>
	</head>

	<body class="bgcolor-ffffff mui-text-center">
		<h4 style="margin-top: 18%;"> 咦？页面怎么找不到了呢？</h4>
		<div>
			<img src="v1.0/module/mui/examples/hello-mui/images/unfoundPic300.png">
		</div>
		<a href="H5/workbench/main" target="_top"><button style="margin-top: 10%; width: 136px;" type="button" class="mui-btn mui-btn-warning bgcolor-ffc000">返回首页</button></a>
	</body>
	<script src="v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
</html>