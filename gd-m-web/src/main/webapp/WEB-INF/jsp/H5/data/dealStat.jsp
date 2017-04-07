<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<!doctype html>
<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>交易数据</title>
	<meta http-equiv="Content-Type" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/base.cy.min.css">
    <link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/index.min.css">
    <script src="${CONTEXT }/v1.0/js/jquery-1.8.3.min.js"></script>
    <script src="${CONTEXT }/v1.0/js/mustache.js"></script>
    <style type="text/css">
    	.tab ul{width:100%;overflow:hidden;}
    	.tab ul li {float:left;width:33.33%;}
    </style>
</head>

<body>

	<div id="stat">
		<!-- tab 切换 -->
		<div class="tab">
			<ul class="">
				<li class="active">今日</li>
				<li style="border-left:1px solid #ff6c00;border-right:1px solid #ff6c00">昨日</li>
				<li class="">本月</li>
			</ul>
		</div>
		<!-- cont 内容 -->
		<div class="cont">
			
		</div>
	</div>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script src="${CONTEXT }/v1.0/js/repeater-cy.js"></script>
<script src="${CONTEXT }/v1.0/js/common.js"></script>
<script src="${CONTEXT }/v1.0/js/data/statController.js"></script>

</html>
