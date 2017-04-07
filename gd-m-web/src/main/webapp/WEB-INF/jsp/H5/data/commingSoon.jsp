<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title>即将上线</title>		
		<link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/reportData.css"/>
		<link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/swiper.min.css"> 
		<style type="text/css">	
			body{
				text-align: center;
				background-color: #474747;
				margin:0;
				padding: 0;
			}
			.outline{
				width: 100%;
				margin:0;
				padding: 0;
			}
			.innerImg{
				width: 85%;
				margin-left: auto;
				margin-right: auto;
				margin-top: 1.5rem;
			}
		</style>
	</head>
	<body>
		<div class="outline">
			<img class="innerImg" src="${CONTEXT }/v1.0/images/data/temp_coming.png" alt="">
		</div>		
		<script src="${CONTEXT }/v1.0/js/jquery-2.1.4.min.js"></script> 
		<script src="${CONTEXT }/v1.0/js/jquery.mobile.custom.min.js"></script>

	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>
