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
	<link rel="stylesheet" href="${CONTEXT }css/style.css">
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	<style>
	.down-wrap{vertical-align: middle;z-index:110;display: none;text-align: center;height: 100%;width: 100%; background: rgba(27,27,27,0.8); position: fixed;bottom: 0;}
	.btn-app{ display: block; width: 15rem; height: 3rem; border-radius: 1rem; font-size: 1.6rem; color: #000000; font-weight: bold; text-align: center; line-height: 3rem; left: 50%; position: absolute; bottom: 3rem; margin-left: -7rem;}
	.down-wrap .img{ margin:0 auto; height: 21.5rem; width: 30rem; top: 50%; left: 50%; margin-top: -10.5rem; margin-left: -15rem; position: absolute;}
	.down-wrap .wx-tip{position: absolute; right: 0; width: 60%; top:2rem;}
	.down-close{width: 3rem; height: 3rem; display: block; position: absolute; right: 0.5rem;top: 0.5rem; background-size:6rem auto ;}
	.lact-img-span{color: #000000;font-size:12rem; display:inline-block; background:url(${CONTEXT }v1.0/images/marketing/location.png) center center; margin-right:.3rem; background-size:7.3rem 1.6rem; width:7.3rem; height: 1.6rem;}
	.introduction-p{width: 65%;}
	.visitCount-img-span{display:inline-block; background:url(${CONTEXT }images/icons/icon_visistCount.png) center center no-repeat; margin-right:.3rem; width:2.3rem; height: 1.6rem;background-size:70%; }
	
	</style>
</head>

<body>	
<div id="prodDetailCoontent">
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
						<span class="nub-price">${productDto.formattedPrice} <c:if test="${productDto.formattedPrice ne '面议' }"><span class="nub-price-1">元/${productDto.unitName}</span></c:if></span>
					</div>
					<span class="price-date"><fmt:formatDate value="${productDto.updateTime}" pattern="MM-dd"/></span>
				</div>
				<div class="stock-sj">
					<div class="stock-dv"><span class="stock-zl">库存:</span><span class="stock-zl stock-mt"> ${dealStockCount}</span></div>
				</div>
				<div class="product-name">
					<div class="product-name-dp"><c:if test="${not empty productDto.shopsName }"><i class="i-icon i-icon-ts"></i></c:if><span class="product-name-sp"><nobr>${productDto.shopsName}</nobr></span></div>
					<div class="product-name-map"><c:if test="${not empty productDto.originProvinceName }"><i class="i-icon i-icon-t i-icon-ts"></i></c:if><span class="product-name-sp"><nobr>${productDto.originProvinceName}${productDto.originCityName}${productDto.originAreaName}</nobr></span></div>
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
	</div>
<div class="down-wrap">
		<div class="img">
			<img src="images/app-down.png" id="levelTypeImg" alt=""/>
			<a href="javascript:;" class="btn-app"></a>
			<a href="javascript:;" class="down-close"></a>
		</div>
		<img src="images/app-wx-tip.png" alt="" class="wx-tip"/>
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
		pagination: '.swiper-pagination',
		autoplay: 3000,//可选选项，自动滑动
		autoplayDisableOnInteraction:false,
	})
	//页面居低
	/*
	var wH = document.body.scrollHeight;
	var cY = document.getElementById('commodity-bt');
	var mH = document.getElementById('mainH');
	var swiper = mH.offsetHeight;
	if(wH > swiper) {
		cY.style.marginTop = wH - swiper+'px';
	}*/
	
	if("${flag}"=='1'){
		function getWeiXin(){
		    var ua = window.navigator.userAgent.toLowerCase();
		    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		        return true;
		    }else{
		        return false;
		    }
		}
		var Terminal = {
	        // 辨别移动终端类型
	        platform : function(){
	            var u = window.navigator.userAgent.toLowerCase(), app = navigator.appVersion;
	            return {
	            	//Windows
	            	isWin: (navigator.platform == "Win32") || (navigator.platform == "Windows"),
	            	//MAC
	            	isMac: (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel"),
	                // android终端或者uc浏览器
	                android: (u.indexOf('android') > -1 || u.indexOf('linux') > -1) && u.indexOf('micromessenger')<0,
	                // 是否为iPhone或者QQHD浏览器
	                iPhone: u.indexOf('iphone') > -1 ,
	                // 是否iPad
	                iPad: u.indexOf('ipad') > -1,
					// 是否是通过微信的扫一扫打开的
//	                weChat: u.indexOf('MicroMessenger') > 0
					weChat: (function (){
					    //var ua = window.navigator.userAgent.toLowerCase();
					    if(u.indexOf('micromessenger')>-1){
					        return true;
					    }else{
					        return false;
					    }
					})()
	            };
	        }(),
	        // 辨别移动终端的语言：zh-cn、en-us、ko-kr、ja-jp...
	        language : (navigator.browserLanguage || navigator.language).toLowerCase()
	    };
		$('#prodDetailCoontent').on('touchstart',function(){
			
			if(getWeiXin()){
	    		$('.wx-tip').show();
	    	}else{	
	    		$('.wx-tip').hide();
	    	}
			if("${fromCode}"=="0"){
				$("#levelTypeImg").attr("src", "images/app-down.png");
			}else{
				$("#levelTypeImg").attr("src", "images/app-down1.png");
			}
			$('.down-wrap').show();
		});

		$('.down-close').on('touchstart',function(){
			$('.down-wrap').hide();
		});
		$('.btn-app').on('touchstart',function(){
			if("${fromCode}"=="0"){
				if(Terminal.platform.iPhone || Terminal.platform.iPad || Terminal.platform.isMac){
	        		$(".btn-app").attr("href","https://itunes.apple.com/cn/app/id1057048929?mt=8&l=cn");
	        	}else if(Terminal.platform.android || Terminal.platform.isWin){
	        		$(".btn-app").attr("href","http://www.gdeng.cn/nsy/nsy.apk");
	        	}
			}else{
				if(Terminal.platform.iPhone || Terminal.platform.iPad || Terminal.platform.isMac){
	        		$(".btn-app").attr("href","https://itunes.apple.com/cn/app/nong-pi-shang/id1074569572?mt=8&l=cn");
	        	}else if(Terminal.platform.android || Terminal.platform.isWin){
	        		$(".btn-app").attr("href","http://www.gdeng.cn/nsy-sell/nsy-nps.apk");
	        	}
			}
			
		})
	}
	//分享参数传递
	function getShareInfoList(){
		var shareData={
		"imageUrl": "${imgHostUrl }${productDto.pictures[0].urlOrg}",
		"productName" : "${productDto.productName}",
		"stockCount" : "${productDto.stockCount}",
		"price" : "${productDto.price}",
		"unitName" :"${productDto.unitName}",
		"description" :"${dealDes}",
		};
		JsBridge.getShareInfoList(JSON.stringify(shareData));
	}
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script src="${CONTEXT}js/jweixin-1.0.0.js"></script>
<script src="${CONTEXT}js/weixin.js"></script>

<script type="text/javascript">
var picUrl = "${imgHostUrl }${productDto.pictures[0].urlOrg}";
var productIdStr = '${productDto.productId}';
var fromCodeStr = '${fromCode}';
var flagStr = "${flag}";
var productNameStr = '${productDto.productName}';
var descStr="库存:${dealStockCount} 单价:${priceSign} 商品介绍:${dealDes}";
var linkStr = CONTEXT + "product/getProductByProdId?productId="+productIdStr+"&fromCode="+fromCodeStr+"&flag="+flagStr;
  wx.ready(function() {
	//获取“分享给朋友”按钮点击状态及自定义分享内容接口
	wx.onMenuShareAppMessage({
		title : productNameStr, // 分享标题
		desc : descStr, // 分享描述
		link : linkStr, // 分享链接
		imgUrl : picUrl, // 分享图标
		type : '', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function(res) {
			
		},
		 cancel: function () { 
		       // 用户取消分享后执行的回调函数
		}
	});
	// 分享给朋友圈
	wx.onMenuShareTimeline({
		title : productNameStr, // 分享标题
		link : linkStr, // 分享链接
		imgUrl : picUrl, // 分享图标
		success : function(res) {
			
		},
		cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
	//分享到QQ
	wx.onMenuShareQQ({
	    title:productNameStr, // 分享标题
	    desc: descStr, // 分享描述
	    link: linkStr, // 分享链接
	    imgUrl : picUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
	//分享到QQ空间
	wx.onMenuShareQZone({
	    title:productNameStr, // 分享标题
	    desc: descStr, // 分享描述
	    link:  linkStr, // 分享链接
	    imgUrl: picUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
});

  </script>
</html>