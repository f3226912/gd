<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>谷登农批网-农速通</title>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/index-shop.css">
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!--head end-->
	
	<!-- 主体 -->
	<div class="banner-down">
		<div id="" class="ty_picScroll mgauto down-banner slide-down-banner">		
			<div class="ty_tabInfo slide-down-banner2">
				<ul>
					<li class="ty_pic slide-down-banner3">
						<a href="#" target="_blank">
							<img src="${CONTEXT }v1.0/images/shop-images/down-img02a.jpg">
						</a>
					</li>
					<li class="ty_pic slide-down-banner3">
						<a href="#" target="_blank">
							<img src="${CONTEXT }v1.0/images/shop-images/down-img01.jpg" >
						</a>
					</li>
					<li class="ty_pic slide-down-banner3">
						<a href="#" target="_blank">
							<img src="${CONTEXT }v1.0/images/shop-images/down-img03a.jpg">
						</a>
					</li>
				</ul>
			</div>
		</div>

	</div>
	<div class="cont02" style="height:280px;">
		<div class="cont-down">
			<div class="cont-down-img fl cont-down-img-left">
				<div class="cont-down-nsw">
					<strong class="cont-down-nsw1">农速通</strong>
					<span class="cont-down-nsw2">找车找货更轻松！</span>
				</div>
				<div class="adr-down-ios">
					<!-- <a href="http://www.gdeng.cn/nst/nst.apk"><img class="adr-down-img" src="${CONTEXT }v1.0/images/shop-images/android.png"></a>
					<a href="https://itunes.apple.com/cn/app/nong-su-tong/id1059305411?mt=8"><img class="adr-down-img" src="${CONTEXT }v1.0/images/shop-images/ipone_1.png"></a>
					 -->
				</div>

			</div>
			<div class="diwn-line-wt2"></div>
			<div class="dis-down-rt clearfix">
					<img class="dis-img-fl10" src="${CONTEXT }v1.0/images/shop-images/cap.png" />
					<span class="dis-down-sao">扫一扫  立即下载到手机</span>
			</div>
		</div>
	</div>
	<div class="cont01">
		<div class="cont-down">
			<div class="cont-down-tit fl">
				<h2><span class="down-ico01"></span>发货信息及时拥有</h2>
				<p>农速通提供及时的商户发货信息，让车主无需漫长等待。</p>
			</div>
			<div class="cont-down-img fr">
				<img src="${CONTEXT }v1.0/images/shop-images/a-1.png">
			</div>
		</div>
	</div>
	<div class="cont02">
		<div class="cont-down">
			<div class="cont-down-img fl cont-down-img-left">
				<img style="margin-top:40px; margin-left:100px;" src="${CONTEXT }v1.0/images/shop-images/a-2.png">
			</div>
			<div class="cont-down-tit fr cont-down-tit-right clearfix">
					<h2><span class="down-ico02"></span>随时随地发布线路信息</h2>
					<p>农速通让司机随时发布线路信息，上万货主方便快捷找到你。</p>
			</div>
		</div>
	</div>
	<div class="cont01">
		<div class="cont-down">
			<div class="cont-down-tit fl">
				<h2><span class="down-ico03"></span>专注全国农批市场</h2>
				<p>农速通专注国内知名一级批发市场商户，发货信息有保障。</p>
			</div>
			<div class="cont-down-img fr">
				<img src="${CONTEXT }v1.0/images/shop-images/img-map.png">
			</div>
		</div>
	</div>
	<div class="cont02">
		<div class="cont-down">
			<div class="cont-down-img fl cont-down-img-left">
				<img style="margin-left:60px;" src="${CONTEXT }v1.0/images/shop-images/a-3.png">
			</div>
			<div class="cont-down-tit fr cont-down-tit-right clearfix">
					<h2><span class="down-ico04"></span>货物不怕再延误，求车只需一小步</h2>
					<p>农速通为您迅速筛选实时线路，助您快速到达目的地。</p>
			</div>
		</div>
	</div>
	<div class="cont02" style="background:#fff;">
		<div class="down-bottom clearfix">
			<div class="down-bottom-left fl">
				<img src="${CONTEXT }v1.0/images/shop-images/cap.png" width="182" height="182">
			</div>
			<div class="down-bottom-right fr">
				<h3><span class="down-bottom-span">农速通</span>农批交易好帮手</h3>
				<h4><a href="http://www.gdeng.cn/nst/nst.apk">一键下载</a></h4>
			</div>
		</div>
	</div>

	<!-- foot page -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>	
	
	<script>
		$(".down-banner").gduiSlide({
			customeClass:"slide-down-banner",
			speed : 5000,
			auto : true,
			direction : true
		});	
	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>