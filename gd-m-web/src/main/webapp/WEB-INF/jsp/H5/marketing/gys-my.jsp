<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-Type" content="text/html">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	    <meta name="apple-touch-fullscreen" content="yes">
	    <meta name="apple-mobile-web-app-capable" content="yes"/>
		<title>我的产销汇</title>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }css/swiper.min.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }/v1.0/css/marketing/iconfont.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }/v1.0/css/marketing/gys-css.css"/>
		<script src="${CONTEXT }/v1.0/js/jquery-1.8.3.min.js"></script>
		
		<script type="text/javascript">
		var apiUrl='${apiUrl }';
		var CONTEXT = '${CONTEXT }';
		</script>
		
		
	</head>
	<body>
		<ul class="myChanxiao">
			<!--<li>
				<div class="iconfont icon-time header orage">距离结束时间:<span>28:23:23</span><span class="right">进行中</span></div>
				<div class="con">
					<div class="imgs">
						<div class="swiper">
							<div class="swiper-container">
						        <div class="swiper-wrapper">
						         	<div class="swiper-slide"><img src="${CONTEXT }/v1.0/images/temppic-01.jpg"/></div>
						         	<div class="swiper-slide"><img src="${CONTEXT }/v1.0/images/temppic-01.jpg"/></div>
						        </div>
						    </div>
						</div>
					</div>
					<ul>
						<li class="header">樱桃产销会</li>
						<li>开始时间:</li>
						<li>结束时间:</li>
						<li>活动规则:</li>
					</ul>
				</div>
				<div class="footer">查看详情</div>
			</li>
			<li>
				<div class="iconfont icon-time header green">距离结束时间:<span>28:23:23</span><span class="right">已报名</span></div>
				<div class="con">
					<div class="imgs"><img src="images/1.png"/></div>
					<ul>
						<li class="header">樱桃产销会</li>
						<li>开始时间:</li>
						<li>结束时间:</li>
						<li>活动规则:</li>
					</ul>
				</div>
				<div class="footer">查看详情</div>
			</li>
			<li>
				<div class="header"><span class="right">已取消</span></div>
				<div class="con">
					<div class="imgs"><img src="images/1.png"/></div>
					<ul>
						<li class="header">樱桃产销会</li>
						<li>开始时间:</li>
						<li>结束时间:</li>
						<li>活动规则:</li>
					</ul>
				</div>
				<div class="footer">查看详情</div>
			</li>-->
		</ul>
		<script src="${CONTEXT }js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${CONTEXT }/v1.0/js/marketing/global.js"></script>
		<script src="${CONTEXT }/v1.0/js/marketing/gys-my.js"></script>
		<script type="text/javascript">
			//图片轮播
		    var swiper = new Swiper('.swiper-container', {
		        pagination: '.swiper-pagination',
		        paginationClickable: true,
		        autoplay : 3000,
		        autoplayDisableOnInteraction : false
		    });
		</script>
	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>
