<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }css/style.css">
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	
</head>

<body>	

<!--main_visual end-->
    
	<div class="main-container" id="mainH">
		<!--banner图-->
		<div class="swiper-container">
		  <div class="swiper-wrapper">
		  <c:forEach items="${productDto.pictures}" var="picture" varStatus="s">
		   <div class="swiper-slide"><span class="swiper-img"><img src="${imgHostUrl }${picture.urlOrg }"/></span></div>
			</c:forEach>
		  </div>
		  <div class="swiper-pagination swiper-pagination-cnet"></div>
		</div>
        <div class="mian-introduction">
        	<div class="box-introduction clearfix">
	        	<p class="introduction-p" id="adaptive">${productDto.productName}</p>
	        	<c:if test="${not empty productDto.managementType }">
				<span class="cooperative" id="swhide">
				<c:if test="${productDto.managementType=='1' }">种养大户</c:if>
				<c:if test="${productDto.managementType=='2' }">合作社</c:if>
				<c:if test="${productDto.managementType=='3' }">基地</c:if>
				</span>
				</c:if>
			</div>
			<div class="xq-price clearfix">
				<div class="ul-price clearfix">
					<div class="ul-price-dj">
						<span class="dj-price">单价：</span>
						<span class="nub-price">${productDto.formattedPrice} <c:if test="${productDto.formattedPrice ne '面议' }"><span class="nub-price-1">元/${productDto.unitName}</span></c:if></span>
					</div>
					<span class="price-date"><fmt:formatDate value="${productDto.createTime}" pattern="MM-dd"/></span>
				</div>
				<div class="stock-sj">
					<div class="stock-dv"><span class="stock-zl">库存:</span><span class="stock-zl stock-mt"> ${productDto.stockCount}${productDto.unitName}</span></div>
				</div>
				<div class="product-name">
					<div class="product-name-dp"><c:if test="${not empty productDto.shopsName }"><i class="i-icon"></i></c:if><span class="product-name-sp">${productDto.shopsName}</span></div>
					<div class="product-name-map"><c:if test="${not empty productDto.originProvinceName }"><i class="i-icon i-icon-t"></i></c:if><span class="product-name-sp">${productDto.originProvinceName}${productDto.originCityName}${productDto.originAreaName}</span></div>
				</div>
			</div>
        </div>
		<div class="commodity-about" id="commodity-bt">
			<h2 class="commodity-about-green">商品介绍：</h2>
			<p class="commodity-content">${productDto.description}</p>
		</div>
		
		<!-- <div class="footer">
			<footer>
				<ul class="footer-ul">
					<li class="footer-list">
						<div class="footer-a1"><i class="i-icon i-icon-sp"></i><a class="footer-a" href="#">进入商铺</a></div>
					</li>
					<li class="footer-list">
						<div class="footer-a1"><i class="i-icon i-icon-phone"></i><a class="footer-a" href="#">拨打电话</a></div>
					</li>
				</ul>
			</footer>
		</div> -->
	</div>

<script type="text/javascript" src="${CONTEXT }js/swiper.min.js"></script>
<script type="text/javascript">
	var swiperNum = document.getElementsByClassName('swiper-slide').length;
	var swiperNo = document.getElementsByClassName('swiper-pagination')[0];
	if(swiperNum==1){
		swiperNo.style.display="none";
	};
	//轮播图
	var mySwiper = new Swiper('.swiper-container', {
		loop : true,	
		pagination: '.swiper-pagination',
		autoplay: 3000,//可选选项，自动滑动
		autoplayDisableOnInteraction:false,
	})
	
	function $(id){
		return typeof id==="string" ? document.getElementById(id):id;
	}
	var wH = document.body.scrollHeight;
	var cY = $('commodity-bt');
	var mH = $('mainH');
	var swiper = mH.offsetHeight;
	if(wH > swiper) {
		cY.style.marginTop = wH - swiper+'px';
	}
	var atv = $('adaptive');
	var atvS = $('swhide');
	//判断有没有【合作社】这几个字。
	if(atvS) {
		atv.style.width='100%';
		atvS.style.display='none';
		cY.style.paddingBottom = '.5rem';
	}
	
	
</script>
</body>
</html>
