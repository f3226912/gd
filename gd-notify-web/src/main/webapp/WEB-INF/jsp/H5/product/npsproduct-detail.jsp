<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>农批商商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }css/nps/style.css">
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	<script type="text/javascript">
    	function aonClick(urlType,params){
			switch(urlType){
			case 0:JsBridge.call(params);break;
			}
		}
  	</script>
</head>

<body>	

<!--main_visual end-->
    
	<div class="main-container" id="mainH">
		<!--banner图-->
		<div class="swiper-container">
		  <div class="swiper-wrapper">
		   <c:forEach items="${productDto.pictures}" end="2" var="picture" varStatus="s">
		   <div class="swiper-slide"><span class="swiper-img"><img src="${imgHostUrl }${picture.urlOrg }"/></span></div>
			</c:forEach>
		  </div>
		  <div class="swiper-pagination swiper-pagination-cnet"></div>
		</div>
        <div class="mian-introduction">
        	<div class="box-introduction clearfix">
	        	<p class="introduction-p">${productDto.productName}</p>
	        	<c:if test="${not empty productDto.managementType }">
				<span class="cooperative">
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
					<div class="stock-dv"><span class="stock-zl">库存:</span><span class="stock-zl stock-mt">${productDto.stockCount}${productDto.unitName}</span></div>
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
			
		<div class="footer">
			<footer>
				<ul class="footer-ul">
					<li class="footer-list">
						<div class="footer-a1" id="goToShop"><i class="i-icon i-icon-sp"></i><a class="footer-a">进入商铺</a></div>
					</li>
					<li class="footer-list">
						<div class="footer-a1" id = "confirmBtn111"><i class="i-icon i-icon-phone"></i><a class="footer-a" >拨打电话</a></div>
					
					</li>
				</ul>
			</footer>
		</div>
	</div>

<!--<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>-->
<script type="text/javascript" src="${CONTEXT }js/swiper.min.js"></script>
<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
<script type="text/javascript">
	var swiperNum = document.getElementsByClassName('swiper-slide').length;
	var swiperNo = document.getElementsByClassName('swiper-pagination')[0];
	if(swiperNum==1){
		swiperNo.style.display="none";
	};
	var mySwiper = new Swiper('.swiper-container', {
		//loop : true,	
		pagination: '.swiper-pagination',
		autoplay: 3000,//可选选项，自动滑动
		autoplayDisableOnInteraction:false,
	})
	
	var wH = document.body.scrollHeight;
	var cY = document.getElementById('commodity-bt');
	var mH = document.getElementById('mainH');
	var swiper = mH.offsetHeight;
	if(wH > swiper) {
		cY.style.marginTop = wH - swiper+'px';

	}
	
	//跳转到商铺列表页面
	function goToShopList(shopData){
		JsBridge.goToShopList(shopData);
	}
	
	  $(document).ready(function(){
		//点击商户信息跳转
		$("#goToShop").click(function(){
			var shopData={
					"businessId":'${productDto.businessId }',
					"productId":'${productDto.productId}',
					"shopsName":'${productDto.shopsName }',
					"mainProduct":'${productDto.mainProduct }',
					"mobile":'${productDto.mobile }'
				};
	    	goToShopList(JSON.stringify(shopData));
	    	
		});
		
		  $("#confirmBtn111").on("tap",function(){
			aonClick(0, 'mobile=${productDto.mobile }' );
			//对于分享后点击链接进入的用户没有userId，因此不需要记录
			if("${productDto.memberId }"){
				//调用接口拨打电话
				$.ajax({
					url:"${CONTEXT }product/addCall",
					type:"post",
					data:{
						'sysCode':'${fromCode}',
						's_Mobile':'${productDto.mobile }',
						's_Name':'${productDto.realName}',
						//'e_Mobile':'${mem.contactMobile}',
						//'e_Name':'${mem.contactName}',
						'source':"SPXQ",
						'shopName':'${productDto.shopsName }',
						'fromCode':1,
						'b_memberId':'${productDto.businessUserId }',
						'memberId':'${productDto.memberId }'
					},
					dataType:"text",
					success:function(data){
							
					},
					error:function(){
					}
				});
			}
		});
});
</script>
</body>
</html>
