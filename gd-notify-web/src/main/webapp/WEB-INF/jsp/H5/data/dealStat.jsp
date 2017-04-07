<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<!doctype html>
<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<meta http-equiv="Content-Type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/base.cy.min.css">
    <link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/index.min.css">
    <script src="${CONTEXT }/v1.0/js/jquery-1.8.3.min.js"></script>
    <script src="${CONTEXT }/v1.0/js/mustache.js"></script>
</head>

<body>

	<div id="stat">
		<!-- tab 切换 -->
		<div class="tab">
			<ul class="ui-box">
				<li class="ui-f1 active">今日</li>
				<li class="ui-f1">昨日</li>
				<li class="ui-f1">本月</li>
			</ul>
		</div>
		<!-- cont 内容 -->
		<div class="cont">
			
		</div>
	</div>
</body>
<script src="${CONTEXT }/v1.0/js/repeater-cy.js"></script>
<script src="${CONTEXT }/v1.0/js/common.js"></script>
<script src="${CONTEXT }/v1.0/js/data/statController.js"></script>

</html>
