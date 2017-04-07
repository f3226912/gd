<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>跳转支付成功页面……</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	</head>
	<body>
		<script type="text/javascript">
			function aonClick(urlType,params){
				switch(urlType){
				case 0:JsBridge.success(params);break;
				}
			}
			
			try 
			{
				window.webkit.messageHandlers.NativeMethod.postMessage("finish");
			}
			catch(e){
			}
			
			aonClick(0,'code=${code}');
		</script>
	</body>
</html>