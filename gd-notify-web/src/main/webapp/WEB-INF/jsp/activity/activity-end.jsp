<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>活动结束</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT}/v1.0/css/gift.css"/>
</head>

<body style="background:#b4eef9;">
	<div class="main-wrap" style="margin-bottom: 0;">
			<div class="aty-end">
				<img src="${CONTEXT}v1.0/images/activity-end.png"/>
			</div>
	</div>

<script src="${CONTEXT}/v1.0/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	var CONTEXT = "${CONTEXT}";
	var activityId = '${activityId}';
	var unionid = '${unionid}';
	var openId = '${openId}';
	var userid = '${userid}';
	var itH = $(window).height();
	var wH = $('.main-wrap').height();
	if(wH<itH){
		$('.aty-end').css({"padding-top":itH-wH+"px"})
	}

</script>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${CONTEXT}js/weixin.js"></script>
<script src="${CONTEXT}v1.0/js/activity/weixin.js"></script>
</html>
