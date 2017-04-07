<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${productDto.productName}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="商品详情,${productDto.productName}" />
	<meta name="description" content="库存: ${dealStockCount} 单价: ${priceSign} 商品介绍: ${dealDes}" />
	<link rel="stylesheet" href="${CONTEXT }css/nps/style.css">
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/transport/global.css">
	<style>
	.down-wrap{vertical-align: middle;z-index:110;display: none;text-align: center;height: 100%;width: 100%; background: rgba(27,27,27,0.8); position: fixed;bottom: 0;}
	.btn-app{ display: block; width: 15rem; height: 3rem; border-radius: 1rem; font-size: 1.6rem; color: #000000; font-weight: bold; text-align: center; line-height: 3rem; left: 50%; position: absolute; bottom: 3rem; margin-left: -7rem;}
	.down-wrap .img{ margin:0 auto; height: 21.5rem; width: 30rem; top: 50%; left: 50%; margin-top: -10.5rem; margin-left: -15rem; position: absolute;}
	.down-wrap .wx-tip{position: absolute; right: 0; width: 60%; top:2rem;}
	.down-close{width: 3rem; height: 3rem; display: block; position: absolute; right: 0.5rem;top: 0.5rem; background-size:6rem auto ;}
	.sp-wholesale{ background:#fff; margin-top:.5rem; width:100%; padding:.8rem 0 .5rem 0;}
	/* .lact-img{ width:7.3rem; height:auto; display: block; float: left; padding:0; margin:0; } */
	.sp-left-icon{ width:25%; float:left; text-align:center;}
	.home-img{ width:5.75rem; height:5.75rem;}
	.sp-right-box{ width:75%; float:right;}
	.sp-right-box h3{ font-size:1.6rem; color:#666;}
	.label-area{ width:15.5rem; margin:.5rem 0; height:1.5rem; }
	.label-area li{ float:left; height:1.5rem; line-height:1.6rem;  font-size:1rem; color:#fff; border-radius:.2rem; background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#ff9242), to(#ff6f05)); padding:0 .5rem; margin-right:.5rem;}
	.num-sp{ width:100%; color:#999; font-size:1.2rem;}
	.gold-img{ width:5.7rem; height:1.3rem;}
	.stock-dv{float:left; width:50%;}
	.product-name-map{width: 50%; float: right;}
	.layermbtn span:last-child {color:#FF6600;}
	.lact-img-span{color: #000000;font-size:12rem; display:inline-block; background:url(${CONTEXT }v1.0/images/marketing/location.png) center center; margin-right:.3rem; background-size:7.3rem 1.6rem; width:7.3rem; height: 1.6rem;}
	.visitCount-img-span{display:inline-block; background:url(${CONTEXT }images/icons/icon_visistCount.png) center center no-repeat; margin-right:.3rem; width:2.3rem; height: 1.6rem;background-size:70%; }
	.cooperative {
		width: auto;
		padding: 0 .5rem;
	}
	.introduction-p{width: 65%;}
	</style>
	<script type="text/javascript">
    	function aonClick(urlType,params){
			switch(urlType){
			case 0:JsBridge.call(params);break;
			}
		}
  	</script>

</head>

<body>
<div id="prodDetailCoontent">
<!--main_visual end-->
	<div class="main-container art-wrap" id="mainH"  data-id='${productDto.businessId},${productDto.marketId},${productDto.productId}'>
		<!--主内容  -->
		<div style="margin-bottom:5rem;">
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
        	<div class="box-introduction clearfix" style="overflow:hidden;">
	        	<p class="introduction-p">
	        	<span class="lact-img-span" <c:if test="${productCertifStatus != 1 }">style='display:none;'</c:if> >
	        	</span>${productDto.productName}
		        <p style="color:#999999;font-size:12px;float:right;line-height:1.6rem;">
				<span class="visitCount-img-span" style="float:left;">
	        	</span>${productDto.visitCount}次浏览
 		        </p>
	        	</p>
	        	<!--
				<c:if test="${not empty productDto.managementType }">
				<span class="cooperative">
				<c:if test="${productDto.managementType=='1' }">种养大户</c:if>
				<c:if test="${productDto.managementType=='2' }">合作社</c:if>
				<c:if test="${productDto.managementType=='3' }">基地</c:if>
				</span>				
				</c:if>
				-->
	        	
			</div>
			<div class="xq-price clearfix">
				<div class="ul-price clearfix">
					<div class="ul-price-dj">
						<span class="dj-price">单价：</span>
						<c:choose>
						   <c:when test="${userGrade == 1}">  
							<span class="nub-price"><i>${productDto.formattedPrice}</i> <c:if test="${productDto.formattedPrice ne '面议' }"><span class="nub-price-1">元/${productDto.unitName}</span></c:if></span>     
						   </c:when>
						   <c:otherwise> 
							<span class="nub-price" id="vipVisible" style="font-size:1.4rem;"><i>会员可见</i></span>
						   </c:otherwise>
						</c:choose>
					</div>
					<span class="price-date"><fmt:formatDate value="${productDto.updateTime}" pattern="MM-dd"/></span>
				</div>
				<div class="stock-sj">
					<div class="stock-dv"><span class="stock-zl">库存:</span><span class="stock-zl stock-mt">${dealStockCount}</span></div>
					<div class="product-name-map"><c:if test="${not empty productDto.originProvinceName }"><i class="i-icon i-icon-t i-icon-ts"></i></c:if><span class="product-name-sp"><nobr>${productDto.originProvinceName}${productDto.originCityName}</nobr></span></div>
				</div>
			</div>
        </div>
		<div class="commodity-about" id="commodity-bt">
			<h2 class="commodity-about-green">商品介绍：</h2>
			<div class="commodity-content">${productDto.description}</div>
		</div>
		<!-- 新增水产批发商店 -->	
		<div class="sp-wholesale clearfix">
		    <div class="sp-left-icon"><img class="home-img" src="${CONTEXT }v1.0/images/marketing/sp-icon.png"/></div>
		    <div class="sp-right-box" id="goToShop">
		        <h3>${business.shopsName}</h3>
		        <ul class="label-area clearfix">
		            <c:if test="${business.memberGrade == 1}"><li style="background:none"><img class="gold-img" src="${CONTEXT }v1.0/images/marketing/gold_medal.png"/></li></c:if>
					<!--
		        	<c:if test="${business.comstatus == 1}"><li>企业</li></c:if>
					-->
			    	<c:if test="${business.cbstatus == 1}"><li>基地</li></c:if>
			        <c:if test="${business.ccstatus == 1}"><li>合作社</li></c:if>
		        </ul>
		        <p class="num-sp">共${productCount}件商品</p>
		    </div>
		</div>
		</div>
	</div>
	</div>
	<div class="down-wrap">
		<div class="img">
			<img src="images/app-down.png" id="levelTypeImg" alt=""/>
			<a href="javascript:;" class="btn-app"></a>
			<a href="javascript:;" class="down-close"></a>
		</div>
		<img src="images/app-wx-tip.png" alt="" class="wx-tip"/>
	</div>
	
<!--<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>-->
<script type="text/javascript" src="${CONTEXT }v1.0/js/gdui.m.js"></script>
<script type="text/javascript" src="${CONTEXT }v1.0/js/layer.m.js"></script>
<script type="text/javascript" src="${CONTEXT }v1.0/js/zepto.js"></script>
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
	
	//跳转到商铺列表页面
	function goToShopList(shopData){
		JsBridge.goToShopList(shopData);
	}
	
	//跳转到购买金牌会员页面
	function goToGoldeMedalPage(){
		JsBridge.goToGoldeMedalPage();
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
		
		//会员点击弹窗
		$('#vipVisible').on('click',function() {
			layer.open({
			    content: '该商品价格仅金牌供应商可见，立即加入金牌供应商，享受更多至尊特权',
			    btn: ['立即开通', '取消'],
			    shadeClose: false,
			    yes: function(){
			        goToGoldeMedalPage();
			    }
			});
		})
		
		
	});
	
</script>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>