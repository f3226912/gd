<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>源头好货</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	<script type="text/javascript" src="${CONTEXT }js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${CONTEXT }js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="${CONTEXT }js/jquery.touchSlider.js"></script> 
	<script type="text/javascript" src="${CONTEXT }js/swiper.min.js"></script>
	<style type="text/css">
		body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,blockquote,th,td,p{margin:0;padding:0}
		input,button,select,textarea{outline:none}
		img{vertical-align:top;border:none;}
		textarea{overflow: auto;vertical-align: top;resize:none;}
		button,input,select,textarea {font-size:100%;}
		h1,h2,h3,h4,h5,h6 {font-size:100%;font-weight:normal}
		table {border-collapse:collapse;border-spacing:0}
		em,i,b{ font-style:normal; font-weight:normal;}
		body {
			overflow-x:hidden;
			font: normal 100% "Microsoft YaHei",'Helvetica Neue', Helvetica, Arial, sans-serif;  /*为了让电脑上和手机一样字体效果。手机是没有"Microsoft YaHei",'Helvetica Neue'*/
			/***min-width: 300px;设置最小宽HTCG7***/
			-webkit-text-size-adjust:none;/**避免重力感应时文字随分辨率增大而增大**/ 
			/** display:-webkit-box;/**流布局**/
			color:#404040;
			background: #f5f5f5;

		}
		*{box-sizing: border-box;-webkit-box-sizing: border-box;}
		article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary{display:block;}
		ul, li, ol, dl, dd, dt{list-style:none;list-style-position:outside;}
		img	{ border:none;width: 100%; height: 100%;}
		a{color:#565656;}
		a:link, a:hover{text-decoration:none;}
		a:link *, a:hover *{text-decoration:none;}
		@-ms-viewport {width: device-width;}
		.clearfix:after{content:".";display:block;font-size:0;height:0;clear:both;visibility:hidden}
		.clearfix{zoom:1}
		html{ font-family: sans-serif; -webkit-text-size-adjust: 100%;}
		@media screen and (min-width:1200px){html{font-size: 125%;/*10 ÷ 16 × 100% × 2 = 62.5%  pc chrom最小字体12px所以为了调试方便，rem定为20px*/}}
		@media screen and (min-width: 980px) and (max-width: 1199px) {html{font-size: 125%;}}
		@media screen and (min-width: 768px) and (max-width: 979px) {html{font-size: 100%;}}
		@media screen and (max-width: 767px){html{font-size: 75%;}}
		@media screen and (max-width: 480px) {html{font-size: 62.5%;}}
		.one-grid img{width:100%; height:auto;}
		.main-container{ background: #f5f5f5;}
		.swiper-container{ width: 100%; text-align: center; height: 100%;}
		.swiper-img img{ width: 100%; height: 37.5rem;}
		.swiper-pagination-cnet{ text-align: center; display: block; width: 100%; }
		.swiper-pagination-cnet span{ margin: 0 .3rem;}
		.swiper-pagination-bullet{ background: #fff !important;}
		.swiper-pagination-bullet-active{ background: #fff !important; width: 10px !important; height: 10px !important;}

		.mian-introduction{ width: 100%; background: #fff; padding-bottom: 1.5rem;}
		.box-introduction{margin:0 1rem; border-bottom: solid 1px #eee; padding: 1rem 0;}
		.introduction-p{ font-size: 1.8rem; color:#666; width: 84%; float: left;}
		.cooperative{ float: right; background: #ff6c00; border-radius: .5rem; display: inline-block; width: 15%; height: 2rem; line-height: 2rem; text-align: center; font-size: 1.2rem; color:#fff; margin-top: .3rem;}
		.xq-price{padding-top: 1rem; margin:0 1rem;}
		.ul-price{ height: 2rem; line-height: 2rem;}
		.ul-price-dj{ float: left;}
		.dj-price ,.stock-zl{ font-size: 1.4rem; color: #999;}
		.nub-price{ font-size: 2.4rem; color: #ff6c00;}
		.nub-price-1{ font-size: 1.2rem; color: #666;}
		.price-date{ float: right; display: inline-block; font-size: 1.2rem; color: #999;}
		.stock-sj{ width: 100%; float:left; padding-top: .5rem; }
		.stock-mt{ padding-left: .5rem;}
		.product-name{ width: 100%; padding-top:1rem; float: left;}
		.product-name-dp{ float: left; width: 50%;}
		.product-name-map{ width: 50%; float: left;}
		.i-icon{ background: url(../images/com-icon.png) no-repeat -2.4rem -.15rem; background-size: 6rem 11rem; width:1.4rem; height: 1.2rem; display: inline-block;}
		.product-name-sp{ font-size: 1.4rem; color: #999; padding-left: .2rem;}
		.i-icon-t{ background-position: -2.55rem -2.75rem; width: 1.2rem; height: 1.2rem;}

		.commodity-about{ background:#fff; margin-top: 1rem; padding:1rem 1rem 4.95rem 1rem;}
		.commodity-about-green{ font-size: 1.6rem; color: #3cb43d; padding-bottom: .5rem;}
		.commodity-content{ line-height:2rem; font-size: 1.2rem; text-indent: 2rem; color:#999; text-align:justify;}
		.footer{ background: #fefefe; height: 4.9rem; line-height: 4.9rem; width: 100%; border-top: solid 1px #d9d9d9; position: fixed; bottom: 0; z-index: 9;}
		.footer-ul{ height: 100%; width: 100%; display: inline-flex;}
		.footer-list{ flex:1; display:inline-block; text-align: center; }
		.i-icon-sp{ width: 1.6rem; height: 1.6rem; background-position: -2.2rem -5.4rem; position: relative; top: .2rem;}
		.footer-a1{ display: inline-block; width: 100%; background: url(../images/line_03.png) no-repeat right center; height: 3.4rem; line-height: 3.4rem;}
		.i-icon-phone{ width: 1.6rem; height: 2.1rem; background-position: -2.2rem -8.6rem; position: relative; top: .6rem;}
		.footer-a{ font-size: 1.6rem; color: #ff6c00;  -webkit-tap-highlight-color:rgba(0,0,0,0); padding-left: .5rem;}
		/*源头好货*/
		.swiper-banner img{ width: 100%; height: 15rem;}
		.main-wrap{ background: #eee;}
		.area-name{ width: 100%;/* border-top: solid .3rem #eee;*/}
		.pdn-mtg-hf{ width: 100%; height:auto; display: block;}
		.pdn-mtg-hf img{height:auto; width:100%;}
		.production-marketing{ /* margin-top:.5rem; */}
		.area-info{ width: 100%; background: #3bb650; height: 3.05rem; line-height: 3.05rem; position: relative;}
		.area-info:before{content: ""; display: block; position: absolute; bottom:0; left:50%; margin-left: -6.8px; width: 0; height: 0;
        border:6px transparent solid;
        border-bottom-color: #f4e9da;}
		.area-info-h3{ text-align: center; font-size: 1.4rem; word-spacing:8px; letter-spacing: 2px; color: white; position: relative; width: 100%;}
		/*CSS伪类*/
		.area-info-h3:before{content: ""; display: block; position: absolute; top:50%; left:15%; width: 16%; height: .2rem; background: white;}
		.area-info-h3:after{content: ""; display: block; position: absolute; top:50%; right:15%; width: 16%; height: .2rem; background: white;}
		.area-name-gc ul{display:-webkit-box; /* Safari and Chrome */display:box; width:100%;}
		.area-name-gc{ text-align: center; background: #fff; }
		.area-name-gb{ height: 7rem; position: relative; -webkit-box-flex:1.0; /* Safari and Chrome */box-flex:1.0;}
		.area-name-gb img{  width: 100%;}
        .area-source{ width:100%; height:10.5rem; margin-top:.5rem;}
        .area-source img{height:10.5rem;}
		.area-gba{ position: absolute; left: 50%; top: 1rem; margin-left: -2.5rem; background:#fbb23a; -moz-border-radius: 100%; width: 5rem; height: 5rem; line-height: 5rem; border-radius:100%; font-size: 1.3rem; font-weight: 600; color: #fff; font-family: 'Microsoft YaHei';}
		.area-name-gb:nth-child(2) .area-gba {background:#3fa2fe;}
		.area-name-gb:nth-child(3) .area-gba {background:#9231f3;}
		.area-name-gb:nth-child(4) .area-gba {background:#21d699;}
		.area-name-gb:nth-child(5) .area-gba {background:#e84905;}
		.area-name-cp10{ width: 100%; background:#fff; padding-bottom: .2rem; box-shadow: 0px 2px 2px #ccc;}
		.area-name-tp025{ padding-top:.25rem; width: 100%;}
		.one-grid{width: 33%; background: #fff; float: left; margin-left:1%; position: relative;}
		.one-grid .title{ position: absolute; top: .5rem; padding:0 .6rem; font-size: 1.2rem; color: #ff6c00;}
		.fid img{ width: 100%}
		/*.one-grid img{ width: 12.5rem; height: 15rem;}*/
		.one-grid:first-child{ margin-left:-1%; }
		.sub-title{ position: absolute; top: 2.1rem; padding:0 .6rem; font-size: 1rem; color:#999;}
		.seafood{ width: 100%; padding-top: 1rem;}
		.seafood-ap-fish{ background-color:#3bb650; width: 100%; display: table; height:4rem; position: relative; text-align: center; margin-bottom:.1rem; }
		.seafood-ap-fish:before{content: ""; display: block; position: absolute; bottom:0; left:50%; margin-left: -6.8px; width: 0; height: 0; border:6px transparent solid; border-bottom-color: #eee;}
		.seafood-ap-icon{ background:url(../images/advertise/icon-11.png) no-repeat -1.1rem -0.7rem; background-size: 4.1rem 25.95rem; width: 2rem; height: 1.4rem; display: inline-block;}
		.seafood-ap-sp{font-size: 1.6rem; background-color:white; width:12rem; height:2.4rem; line-height: 2.4rem; margin: 0 auto;  color: rgb(0,168,246); margin-top: .8rem; display:inline-block; border-radius: 2rem; }
		.seafood-ap-sp:before{content: ""; display: block; position: absolute; top:50%; left:15%; width: 16%; height: .2rem; background: white;}
		.seafood-ap-sp:after{content: ""; display: block; position: absolute; top:50%; right:15%; width: 16%; height: .2rem; background: white;}
		.seafood-ap-gd{ font-size:1.2rem; color:white; position:absolute; right: .3rem; top:1.3rem;}
		/*水产*/
		.seafood-box-max{ width: 100%; }
		.aquatic{ width: 100%;}
		.aquatic-box{ float: left; width: 50%;}
		.aquatic-img{ background: #fff; position: relative; border-bottom: #d5d5d5 solid 1px; border-right: #e5e5e5 solid 1px; border-left: #e5e5e5 solid 1px;}
		.aquatic-img1{ background: #fff; border-bottom: 1px solid #dbdbdb;}
		.aquatic-img2{ background: #fff;float: left; display: inline-block; width: 50%; position: relative; z-index: 9; border-bottom: #d5d5d5 solid 1px; border-right: #e5e5e5 solid 1px;}
		.seafood-box-min{ position: absolute; top: 0rem; width:100%; padding-left:.9rem; padding-top:.15rem; background:rgba(255,255,255,.5);}
		.seafood-min{ font-size: 1.3rem; width:100%; display: block; padding-top:.2rem; overflow:hidden; text-overflow:ellipsis;white-space:nowrap;}
		.seafood-span-min{ font-size: 1.2rem; display: block; color: #ff6c00; }
		.seafood-box-line{ position: absolute;  top:0rem; padding-left: 1rem; padding-right: 1rem; padding-top:.15rem; width:100%; background:rgba(255,255,255,.5)/* background:linear-gradient(to bottom, rgba(204, 204, 204, 2) 0%, rgba(238, 238, 238, 0) 98%); */}
		/* .seafood-right-line>.seafood-span{ padding-bottom:.1rem;} */
		.seafood-right-line1{ position: absolute; top: 9.5rem;}
		.seafood-st{ display: block; color: #333; font-size: 1.4rem; overflow:hidden;text-overflow:ellipsis;white-space:nowrap;}
		.seafood-span{ display: block; color: #ff6c00; font-size: 1.2rem; padding-bottom:.5rem;}
		.aquatic-3 ,.aquatic-5{ width: 100%;}
		.aquatic-img5 { width: 33.3%; background: #fff; float: left; border-bottom: 1px solid #dbdbdb; border-right: #e5e5e5 solid 1px; position: relative;}
		.seafood-box-lt{ padding-left: .9rem;}
		/*果蔬*/
		/* .seafood-ap-fruits{ background:#eee url(../images/advertise/guo_su_04.jpg) no-repeat center center; background-size:100% 6rem;} */
		.aquatic-1 ,.aquatic-2 { width: 50%; float: left; position: relative;}
		.aquatic-left{ float: left; width: 100%;}
		.aquatic-img6{ float: left; position: relative; border-bottom: 1px solid #dbdbdb; border-right: #e5e5e5 solid 1px; margin-left: -0.35px; background: #fff; left: 0; }
		.aquatic-img7{ float: left; background:#fff; width: 100%; position: absolute; left: 100%;  border-bottom: 1px solid #dbdbdb; top:-1px;}
		.seafood-ap-icon2{ background-position: -1.15rem -3.7rem; width:1.5rem; height: 1.5rem; position: relative; top:.2rem;}
		.seafood-ap-zt{ color: #3cb43d;}
		/* .seafood-st-color2{ color: #3cb43d;} */
		.aquatic-img2-lt{ background: #fff;float: left; display: inline-block; width: 50%; position: relative; z-index: 9; border-bottom: #d5d5d5 solid 1px; border-left: #e5e5e5 solid 1px;}
		.seafood-ap-color ,.seafood-frozen-color{ color: #ffb72c; font-size:1.6rem; display: inline-block;}
		.seafood-ap-icon3{ background-position: -.9rem -5.4rem; width: 2.2rem; height: 1.2rem;}
		/* .seafood-st-color3{ color: #ffb72c} */
		.aquatic-imgs{ float: left; position:relative; margin-left: -.03rem; border-bottom: #d5d5d5 solid 1px; border-left: #e5e5e5 solid 1px;}
		.bg-no ,.fid{ -webkit-tap-highlight-color:rgba(0,0,0,0);}
		/* .seafood-frozen-color{ color: #7a89f3;} */
		.seafood-ap-icon4{ background-position: -.9rem -9.95rem; width: 2.2rem; height: 2rem; position: relative; top:.2rem;}
		.seafood-frozen-color{ color: #00baf6;}
		.seafood-st-2{position: absolute;}
		/* 副食 */
		/* .seafood-on-staple{ background:#eee url(../images/advertise/img_07_08.jpg) no-repeat center center; background-size: 100% 6rem;} */
	   /* 酒水 */
	  /*  .seafood-ap-wine{ background:#eee url(../images/advertise/img_08_04.jpg) no-repeat center center; background-size: 100% 6rem;} */
	   /* 茶 */
	   /* .seafood-ap-tea{background:#eee url(../images/advertise/img_09_04.jpg) no-repeat center center; background-size: 100% 6rem;} */
	   .seafood-frozen-color5{ color:#3cb43d;}
	   .seafood-ap-icon5{ background-position:-.9rem -13.6rem; width:2rem; height:1.9rem; position:relative; top:.3rem;}
	   /* 粮油 */
	  /*  .seafood-ap-oils{background:#eee url(../images/advertise/img_06_08.jpg) no-repeat center center; background-size: 100% 6rem;} */
	   .seafood-frozen-color6{ color:#ff6d2c;}
	   .seafood-ap-icon6{background-position:-.9rem -20.65rem; width:1.8rem; height:1.8rem; position:relative; top:.3rem;}
	   /*干调*/
	   /* .seafood-gan-tiao{background:#eee url(../images/advertise/img_05_06.jpg) no-repeat center center; background-size: 100% 6rem;} */
	   .seafood-frozen-color7{ color:#ffb72c;}
	   .seafood-ap-icon7{background-position:-.9rem -23.4rem; width:1.95rem; height:1.95rem; position:relative; top:.22rem;}
	   /*禽蛋肉食*/
	   /* 	.seafood-ap-eggs{ background:#eee url(../images/advertise/img_03_06.jpg) no-repeat center center; background-size: 100% 6rem;} */
	    .seafood-ap-icon8{ background-position: -.9rem -17.45rem; width: 1.9rem; height: 1.7rem; position:relative; top:.4rem;}
	   .seafood-frozen-color8{ color:#ffb72c;}
	</style>
</head>
	
<body>	
<!--main_visual end-->
	<div class="main-wrap">
		<div class="swiper-container">
		  <div class="swiper-wrapper">
		  	<c:set var="imgcn" value="0"  />
			<c:forEach var="record" items="${lunbocn}">  
				<c:set var="mkey" value="${record}"  />	
				<c:if test="${! empty adMap[mkey] }">	
				<c:set var="imgcn" value="${imgcn+1 }"  />
				    <div id="${mkey}" class="swiper-slide">
				    	<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}');">
				    		<span class="swiper-banner">
				    		<img src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg}"/>
				    		</span>
				    	</a>
				    </div>
				</c:if>
			</c:forEach>
		  </div>
		  <div class="swiper-pagination swiper-pagination-cnet"></div>
		</div>
		<!-- 新增【精准货源】 -->
		<div class="area-source">
			<a href="javascript:aonClick(-3,'marketId=3&provinceId=410000&cityId=410200&title=开封')"><img src="${CONTEXT}images/advertise/new_source.jpg"/></a>
		</div>
		<!-- 产销对接 -->
		<div class="production-marketing">
			<div class="area-info">
				<h3 class="area-info-h3">产  销  对  接</h3>
			</div>
			<a class="pdn-mtg-hf" href="javascript:aonClick(-2,'actId=1&userId=${memberId }')"><img src="${CONTEXT}images/advertise/cx_02.jpg"></a>
		</div>
		<!--地理标志产品大集合-->
		<div class="area-name">
			<div class="area-info">
				<h3 class="area-info-h3">地理标志商品</h3>
			</div>
			<div class="area-name-gc">
			<c:if test="${! empty adMap.B1}">
				<ul>
					<li id="B1" class="area-name-gb">
						<a class="area-name-sub" href="javascript:aonClick(0,'marketId=3&provinceId=410000&cityId=410200&title=开封')"><!-- <span class="area-gba">开封</span> --><img src="${adMap.B1.state eq 1?adMap.B1.adUrl:adMap.B1.replaceImg}"/></a>
					</li>
				<!-- 	<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">徐州</span><img src="${CONTEXT }images/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">台湾</span><img src="${CONTEXT }images/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">玉林</span><img src="${CONTEXT }images/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">洛阳</span><img src="${CONTEXT }images/gc.jpg"/></a>
					</li> -->

				</ul>
				</c:if>
			</div>
			<div class="area-name-cp10">
				<ul class="area-name-tp025 clearfix">
				<c:forEach var="key" begin="1" end="3" step="1" varStatus="cn">  
					<c:set var="mkey" value="C${cn.index}"  />
					<c:if test="${! empty adMap[mkey]}">
						<li id="${mkey}" class="one-grid">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}');"" class="fid">
								<c:if test="${adMap[mkey].jumpType eq 2 }">
									<div class="title">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</div>
									<div class="sub-title">${adMap[mkey].showPrice }</div>
								</c:if>
								<img onerror="javascript:this.src='../images/advertise/def.png';" src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							</a>
						</li>
					</c:if>					
					</c:forEach>	
				</ul>
			</div>
		</div>
		
		<!-- 采购汇 -->
	<%-- 	<div>
			<a href="javascript:aonClick(-2,'actId=1&userId=${memberId }')">
				<img src="${CONTEXT }/images/advertise/purchaseRe1.png" alt="" style="width:100%; height:auto">
			</a>
		</div>	 --%>	
		
		<!--水产-->
		<c:if test="${! empty cateMap[1042] }">
		<div class="seafood" id="floor1">
			<a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1042].categoryId}&title=${cateMap[1042].cateName }')">
			<div class="seafood-ap-fish">	
				<h3 class="seafood-ap-sp"><i class="seafood-ap-icon"></i>${cateMap[1042].cateName }</h3>
				<span class="seafood-ap-gd" >更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="D1"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }""/>
							<div class="seafood-box-line">
								<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-box aquatic-2">
						<div class="aquatic-img1">
						
							<c:set var="mkey" value="D2"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
								</div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<c:set var="mkey" value="D3"  />
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2">
							<c:set var="mkey" value="D4"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-3">
					<c:forEach var="key" begin="5" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="D${cn.index}"  />
					<div class="aquatic-img5">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		</c:if>
		<!--果蔬-->
		<c:if test="${! empty cateMap[1100]}">
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1100].categoryId}&title=${cateMap[1100].cateName }')">
			<div class="seafood-ap-fish seafood-ap-fruits">
				<h3 class="seafood-ap-sp seafood-ap-zt"><i class="seafood-ap-icon seafood-ap-icon2"></i> ${cateMap[1100].cateName }</h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img" style="margin-bottom:-1px;">
						<c:set var="mkey" value="E1"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color2 ">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-2">
						<div class="aquatic-img1">
							<c:set var="mkey" value="E2"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							    <div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
								</div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2">
							<c:set var="mkey" value="E3" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2">
							<c:set var="mkey" value="E4" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-1">
					<div class="aquatic-left aquatic-left-mi">
						<c:forEach var="key" begin="5" end="6" step="1" varStatus="cn">  
						<c:set var="mkey" value="E${cn.index}"  />
						<div class="aquatic-img6">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
						</c:forEach>
						<%-- <div class="aquatic-img6">
						<a href="#" class="bg-no">
							<img src="${CONTEXT }images/good-sources_5.png"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">进口熟冻加拿大甜虾</strong>
								<span class="seafood-span-min">69.00元/公斤</span>
							</div>
						</a>
						</div> --%>
						
					</div>
					<div class="aquatic-img7">
						<div class="img-box">
						<c:set var="mkey" value="E7"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
						    <div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                            </div>
						</a>
						</div>
					</div>
				</div>

			</div>
		</div>
		</c:if>
		<!--副食-->
		<c:if test="${! empty cateMap[1124]}">
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1124].categoryId}&title=${cateMap[1124].cateName }')">
			<div class="seafood-ap-fish seafood-on-staple">
				<h3 class="seafood-ap-sp seafood-ap-color"><i class="seafood-ap-icon seafood-ap-icon3"></i> ${cateMap[1124].cateName }</h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-2">
						<c:set var="mkey" value="F1" />
						<div class="aquatic-img1">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							    <div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
								</div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2-lt">
							<c:set var="mkey" value="F2" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2-lt">
							<c:set var="mkey" value="F3" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="F4" />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color3">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-3">
					<c:forEach var="key" begin="5" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="F${cn.index}"  />
					<div class="aquatic-img5">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
					</c:forEach>
					<%-- <div class="aquatic-img5">
					<a href="#" class="bg-no">
						<img src="${CONTEXT }images/good-sources_5.png"/>
						<div class="seafood-box-min seafood-box-lt">
							<strong class="seafood-min">进口熟冻加拿大甜虾</strong>
							<span class="seafood-span-min">69.00元/公斤</span>
						</div>
					</a>
					</div>
					<div class="aquatic-img5">
					<a href="#" class="bg-no">
						<img src="${CONTEXT }images/good-sources_5.png"/>
						<div class="seafood-box-min seafood-box-lt">
							<strong class="seafood-min">进口熟冻加拿大甜虾</strong>
							<span class="seafood-span-min">69.00元/公斤</span>
						</div>
					</a>
					</div> --%>
				</div>
			</div>
		</div>
		</c:if>
		<!--酒水-->
	    <c:if test="${! empty cateMap[1125]}">	
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1125].categoryId}&title=${cateMap[1125].cateName }')">
			<div class="seafood-ap-fish seafood-ap-wine">
				<h3 class="seafood-ap-sp seafood-frozen-color"><i class="seafood-ap-icon seafood-ap-icon4"></i><span style="position: relative; top:-.2rem;">${cateMap[1125].cateName }</span></h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="G1"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color4">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-2">
						<c:forEach var="key" begin="2" end="3" step="1" varStatus="cn">  
						<c:set var="mkey" value="G${cn.index}"  />
						<div class="aquatic-img1">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							  <img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							  
							  <c:if test="${key eq 2 }">
								  <div class="seafood-box-line seafood-right-line">
							  </c:if>
							   <c:if test="${key eq 3}">
								  <div class="seafood-box-line seafood-right-line seafood-right-line1">
							  </c:if>
                                   <%-- <strong <c:if test="${key eq 2}">class="seafood-st"</c:if> <c:if test="${key eq 3}">class="seafood-st seafood-st-2"</c:if> >${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>  --%>
                                   <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong> 
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                              </div>
							</a>
						</div>
						</c:forEach>
					</div>
				</div>
				<div class="aquatic-box aquatic-5">
					<c:forEach var="key" begin="4" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="G${cn.index}"  />
					<div class="aquatic-imgs">
					<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
						<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
						<div class="seafood-box-min seafood-box-lt ">
							<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
							<c:if test="${adMap[mkey].jumpType eq 2 }">
							<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
							</c:if>
						</div>
					</a>
					</div>
					</c:forEach>

				</div>
			</div>
		</div>
		</c:if>
		
		
		
		
		<!-- 茶  H -->
		<c:if test="${! empty cateMap[1126] }">
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1126].categoryId}&title=${cateMap[1126].cateName }')">
			<div class="seafood-ap-fish seafood-ap-tea">
				<h3 class="seafood-ap-sp seafood-frozen-color5"><i class="seafood-ap-icon seafood-ap-icon5"></i> <span style="position: relative; top:-.1rem;">${cateMap[1126].cateName }</span></h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="H1"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }""/>
							<div class="seafood-box-line">
								<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-box aquatic-2">
						<div class="aquatic-img1">
							<c:set var="mkey" value="H2"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
							<div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                              </div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<c:set var="mkey" value="H3"  />
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2">
							<c:set var="mkey" value="H4"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-3">
					<c:forEach var="key" begin="5" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="H${cn.index}"  />
					<div class="aquatic-img5">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		</c:if>
		
		<!-- 粮油 I -->
		<c:if test="${! empty cateMap[913]}">
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[913].categoryId}&title=${cateMap[913].cateName }')">
			<div class="seafood-ap-fish seafood-ap-oils">	
				<h3 class="seafood-ap-sp seafood-frozen-color6"><i class="seafood-ap-icon seafood-ap-icon6"></i> ${cateMap[913].cateName }</h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img" style="margin-bottom:-1px;">
						<c:set var="mkey" value="I1"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color2 ">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-2">
						<div class="aquatic-img1">
							<c:set var="mkey" value="I2"  />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                                </div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2">
							<c:set var="mkey" value="I3" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2">
							<c:set var="mkey" value="I4" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-1">
					<div class="aquatic-left aquatic-left-mi">
						<c:forEach var="key" begin="5" end="6" step="1" varStatus="cn">  
						<c:set var="mkey" value="I${cn.index}"  />
						<div class="aquatic-img6">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
						</c:forEach>
					</div>
					<div class="aquatic-img7">
						<div class="img-box">
						<c:set var="mkey" value="I7"  />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
						    <div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                            </div>
						</a>
						</div>
					</div>
				</div>

			</div>
		</div>
		</c:if>
		<!--干调 J-->
		<c:if test="${! empty cateMap[957]}">
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[957].categoryId}&title=${cateMap[957].cateName }')">
			<div class="seafood-ap-fish seafood-gan-tiao">	
				<h3 class="seafood-ap-sp seafood-frozen-color7"><i class="seafood-ap-icon seafood-ap-icon7"></i> ${cateMap[957].cateName }</h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-2">
						<c:set var="mkey" value="J1" />
						<div class="aquatic-img1">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							    <div class="seafood-box-line seafood-right-line">
                                  <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
                                  <c:if test="${adMap[mkey].jumpType eq 2 }">
                                  <span class="seafood-span">${adMap[mkey].showPrice }</span>
                                  </c:if>
                                </div>
							</a>
						</div>
						<div class="min-box">
							<div class="aquatic-img2-lt">
							<c:set var="mkey" value="J2" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
							<div class="aquatic-img2-lt">
							<c:set var="mkey" value="J3" />
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
								<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
								<div class="seafood-box-min">
									<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
							</div>
						</div>
					</div>
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="J4" />
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color3">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
				</div>
				<div class="aquatic-box aquatic-3">
					<c:forEach var="key" begin="5" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="J${cn.index}"  />
					<div class="aquatic-img5">
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-min seafood-box-lt">
								<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		</c:if>
		<!-- 禽蛋肉食 K -->
		<c:if test="${! empty cateMap[1127]}">	
		<div class="seafood" id="floor1">
		    <a href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1127].categoryId}&title=${cateMap[1127].cateName }')">
			<div class="seafood-ap-fish seafood-ap-eggs">	
				<h3 class="seafood-ap-sp seafood-frozen-color8"><i class="seafood-ap-icon seafood-ap-icon8"></i> ${cateMap[1127].cateName }</h3>
				<span class="seafood-ap-gd">更多>></span>
			</div>
			</a>
			<div class="seafood-box-max clearfix">
				<div class="aquatic">
					<div class="aquatic-box aquatic-1">
						<div class="aquatic-img">
						<c:set var="mkey" value="K1"/>
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							<img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							<div class="seafood-box-line">
								<strong class="seafood-st seafood-st-color4">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
						</div>
					</div>
					<div class="aquatic-2">
						<c:forEach var="key" begin="2" end="3" step="1" varStatus="cn">  
						<c:set var="mkey" value="K${cn.index}"  />
						<div class="aquatic-img1">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
							  <img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
							  
							  <c:if test="${key eq 2 }">
								  <div class="seafood-box-line seafood-right-line">
							  </c:if>
							   <c:if test="${key eq 3 }">
								  <div class="seafood-box-line seafood-right-line seafood-right-line1">
							  </c:if>
							  
                               <%-- <strong <c:if test="${key eq 2}">class="seafood-st"</c:if> <c:if test="${key eq 3}">class="seafood-st seafood-st-2"</c:if>>${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>  --%>
                                 <strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
                              </div>
							</a>
						</div>
						</c:forEach>
					</div>
				</div>
				<div class="aquatic-box aquatic-5">
					<c:forEach var="key" begin="4" end="7" step="1" varStatus="cn">  
					<c:set var="mkey" value="K${cn.index}"  />
					<div class="aquatic-imgs">
					<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}&promotion=${adMap[mkey].promotion}')" class="bg-no">
						<img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/>
						<div class="seafood-box-min seafood-box-lt">
							<strong class="seafood-min">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
							<c:if test="${adMap[mkey].jumpType eq 2 }">
							<span class="seafood-span-min">${adMap[mkey].showPrice }</span>
							</c:if>
						</div>
					</a>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		</c:if>
		
	</div>

<script type="text/javascript">
	function aonClick(urlType,params){
		
		//var m = "";
		switch(urlType){
		case 0:JsBridge.method0(params);break;
		case 1:JsBridge.method1(params);break;
		case 2:JsBridge.method2(params);break;
		case 5:JsBridge.method5(params);break;
		case 3:JsBridge.method3(params);break;
		case -1:JsBridge.methodCate(params);break;
		case 6:JsBridge.productInfo(params);break;
		case 7:JsBridge.productAction(params);break;
		case -2:JsBridge.cxhDetail(params);break;
		case -3:JsBridge.jzhy(params);break;
		}
		//window.WebViewJavascriptBridge.callHandler(m, {'param': params}, function(responseData) {});
	}
	
	var mySwiper = new Swiper('.swiper-container', {
		loop : true,	
		pagination: '.swiper-pagination',
		autoplay: 3000,//可选选项，自动滑动
		autoplayDisableOnInteraction:false,
	})

	
	$(function(){
	var lis = $('.area-name-gc').find('li').length;
		if(lis==1){
			 $('.area-gba').hide();
		}
		else if(lis==2){
			 $('.area-gba').show();
		}
		else if(lis>=3){
			 $('.area-gba').show().css({'width':'4.5rem','height':'4.5rem', 'line-height':'4.5rem','margin-top':'.3rem'});
			 $('.area-name-sub').find('img').hide();
		}
	//获取当前ID的位置, .seafood-box-min-tp	
	
	
	
	$(".aquatic-1 ,.aquatic-2 ,.min-box ,.aquatic-3 ,.aquatic-left-mi ,.aquatic-img7 ,.aquatic-5").each(function(){
    var $wrap= $(this),
        mainHeight=$wrap.width();
		$(this).find(".aquatic-img").css({'height':mainHeight+'px'}); 
		$(this).find(".aquatic-img1").css({'height':(mainHeight/2)+'px'}); 
 		$(this).find(".aquatic-img2 ,.aquatic-img3 ,.aquatic-img2-lt").css({"height":(mainHeight/2)+'px'});
 		$(this).find('.aquatic-img5').css({'width':(mainHeight/3)+'px' ,'height':(mainHeight/3)+'px'});
 		$(this).find('.aquatic-img6').css({'width':(mainHeight/2)+'px' ,'height':(mainHeight/2)+'px'});
 		$(this).find('.img-box').css({'height':(mainHeight/2)+'px'});
 		$(this).find('.aquatic-imgs').css({'width':(mainHeight/4)+'px' ,'height':(mainHeight/4)+'px'});
 
		})
		
		
		
	});
	
	
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>