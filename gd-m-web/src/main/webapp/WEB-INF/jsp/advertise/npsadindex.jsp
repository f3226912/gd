<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>源头好货</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<style>

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
			background: #eee;

		}
		article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary{display:block;}
		ul, li, ol, dl, dd, dt{list-style:none;list-style-position:outside;}
		img	{ border:none;max-width: 100%;height: auto;}
		a{color:#565656;}
		a:link, a:hover{text-decoration:none;color:#666}
		a:link *, a:hover *{text-decoration:none;}
		@-ms-viewport {width: device-width;}
		.clear{clear:both}
		.clearfix:after{content:".";display:block;font-size:0;height:0;clear:both;visibility:hidden}
		.clearfix{zoom:1}

		html
		{
		    font-family: sans-serif;

		    -webkit-text-size-adjust: 100%;
		}
		@media screen and (min-width:1200px){html{font-size: 125%;/*10 ÷ 16 × 100% × 2 = 62.5%  pc chrom最小字体12px所以为了调试方便，rem定为20px*/}}
		@media screen and (min-width: 980px) and (max-width: 1199px) {html{font-size: 125%;}}
		@media screen and (min-width: 768px) and (max-width: 979px) {html{font-size: 100%;}}
		@media screen and (max-width: 767px){html{font-size: 75%;}}
		@media screen and (max-width: 480px) {html{font-size: 62.5%;}}
		.main-wrap{ background: #eee; margin-bottom:49px;}
		.area-name{ width: 100%; border-top: solid .3rem #eee;}
		.area-info{ width: 100%; background: #fff; height: 3.05rem; line-height: 3.05rem; border-bottom: solid #eee 1px;}
		.area-info-h3{ float: left; font-size: 1.4rem; color: #4a5271; background: url(../images/advertise/bg-1.png) no-repeat; background-size: 1.8rem 1.8rem; background-position: 0.8rem; padding-left: 3rem;}
		.area-name-gc ul{display:-moz-box; /* Firefox */display:-webkit-box; /* Safari and Chrome */display:box;width:100%;}
		.area-info-sp{ float: right; font-size: 1.4rem; padding-right: 1rem;}
		.area-name-sub img{ width:100%; height:auto;}
		.area-name-gc{ text-align: center; background: #fff; }
		.area-name-gb{ height: 7rem; position: relative; -moz-box-flex:1.0; /* Firefox */-webkit-box-flex:1.0; /* Safari and Chrome */box-flex:1.0;}
		.area-name-gb img{ height: 7rem;}

		.area-gba{ position: absolute; left: 50%; top: 1rem; margin-left: -2.5rem; background:#fbb23a; -moz-border-radius: 100%; width: 5rem; height: 5rem; line-height: 5rem; border-radius:100%; font-size: 1.3rem; font-weight: 600; color: #fff; font-family: 'Microsoft YaHei';}
		.area-name-gb:nth-child(2) .area-gba {background:#3fa2fe;}
		.area-name-gb:nth-child(3) .area-gba {background:#9231f3;}
		.area-name-gb:nth-child(4) .area-gba {background:#21d699;}
		.area-name-gb:nth-child(5) .area-gba {background:#e84905;}
		.area-name-cp10{ width: 100%; background:#eee;}
		.area-name-tp025{ padding-top:.25rem; width: 100%;}
		.one-grid{width: 33%; background: #fff; float: left; margin-left:0.5%; position: relative;}
		.one-grid .title{ position: absolute; top: .5rem; padding:0 .6rem; font-size: 1.2rem; color: #ff6c00;}
		/*.one-grid img{ width: 12.5rem; height: 15rem;}*/
		.one-grid:first-child{ margin-left:0; }
		.sub-title{ position: absolute; top: 2.1rem; padding:0 .6rem; font-size: 1rem; color:#999;}
		.seafood{ width: 100%; padding-top: 1rem;}
		.seafood-ap{ width: 100%; background-color:#fff; height:3rem; line-height: 3rem; position: relative;}
		.seafood-ap-sp{float: left; font-size: 1.4rem; color: rgb(0,168,246); position: absolute; left: 3rem;}
		.seafood-ap-gd{ float: right; font-size:1.2rem; color: rgb(153,153,153); padding-right: 1.05rem;}
		.seafood-ap-icon{ background:url(../images/advertise/icon-11.png) no-repeat -1.1rem -0.7rem; background-size: 4.1rem 25.95rem; width: 2rem; height: 1.4rem; display: inline-block; position: absolute; top: .8rem; left: .8rem;}
		.seafood-ap-icon-f2{ background-position: -1.1rem -3.7rem; width: 1.9rem; height: 1.5rem; top: .8rem;}
		.seafood-ap-icon-f3{ background-position: -1.1rem -6.55rem; width: 2.4rem; height: 2.15rem; top: .1rem;}
		.seafood-ap-icon-f4{ background-position: -1.05rem -9.55rem; width: 1.9rem; height: 2.5rem; top: .1rem;}
		.seafood-ap-icon-f5{ background-position: -.9rem -13.6rem; width: 2rem; height: 1.9rem; top: .6rem;}
		.seafood-ap-icon-f6{ background-position: -.9rem -17.35rem; width: 2rem; height: 1.7rem; top: .6rem;}
		.seafood-ap-icon-f7{ background-position: -.9rem -20.65rem; width: 1.8rem; height: 1.8rem; top: .6rem;}
		.seafood-ap-icon-f8{ background-position: -.8rem -23.4rem; width: 2rem; height: 2rem; top: .4rem;}
		.seafood-ap-color-b{ color: rgb(60,180,61);}
		.seafood-ap-color-c{ color: rgb(255,183,44); left: 3.5rem;}
		.seafood-ap-color-d{ color: rgb(0,186,246); left: 3rem;}
		.seafood-ap-color-e{ color: rgb(60,180,61); left: 3rem;}
		.seafood-ap-color-f{ color: rgb(255,143,44); left: 3.2rem;}
		.seafood-ap-color-g{ color: rgb(255,109,44); left: 3.2rem;}
		.seafood-ap-color-h{ color: rgb(255,183,44); left: 3.2rem;}
		.seafood-box-max{ width: 100%; padding-top: .25rem;}
		.seafood-box-max-a{ width:49.75%; height:22.75rem; margin-right: .5%; background: rgb(255,255,255); float: left;}
		.seafood-box-max-a img{ width:100%; height:auto; vertical-align: middle;}
		.seafood-st{ display: block; font-size: .9rem; color: rgb(102,102,102); padding: .9rem 0 0 .8rem; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden; font-size: 1.3rem;}
		.seafood-span{ display: block; font-size: 1.2rem; color: rgb(255,108,0); padding: .1rem 0 .5rem .8rem;}
		.seafood-box-max-b{ background: rgb(255,255,255); float: right; margin-right: 0;}
		.seafood-box-line{ width: 100%; height:5rem;}
		.seafood-box-min{ width: 100%;}
		.seafood-box-min-tp{ width: 33%; height:16.6rem; float: left; margin-left: 0.5%; margin-top: 1%; background: rgb(255,255,255);}
		.seafood-box-min-tp:first-child{ margin-left: 0;}
		.seafood-box-min-tp img{ width: 100%; height:auto;}
		.seafood-st-pr{padding: .9rem 0 0 .8rem;}
		
		/*焦点图左右滑动*/
		.main_visual{height:15rem;overflow:hidden;position:relative;}
		.main_image{height:15rem;overflow:hidden;position:relative;}
		.main_image ul{width:9999px;height:15rem;overflow:hidden;position:absolute;top:0;left:0}
		.main_image li{float:left;width:100%;height:15rem; text-align:center}
		.main_image li span.img_3{display:block;width:100%;height:15rem}
		.main_image li a{display:block;width:100%;height:15rem}
		div.flicking_con{position:absolute;bottom:0;left:55%;z-index:999;width:300px;height:21px;margin:0 0 0 -50px;}
		div.flicking_con a{float:left;width:21px;height:21px;margin:0;padding:0;background:url('../images/advertise/btn_main_img.png') 0 0 no-repeat; background-size:display:block;text-indent:-1000px}
		div.flicking_con a.on{background-position:0 -22px}
		.seafood-max-img, .seafood-min-img{ display:block;}
		/*加载更多...*/
		.slide-more{width:100%; text-align: center;border-top:solid 1px #ccc; font-size:1.3rem; color:#666; position: relative; top:2rem;}
		.slide-more-p{ background: #eee; width:13em; margin:0 auto; display: block; position: relative;  top:-1rem;}
	</style>
</head>
<%-- <c:out value="${adModel.d1.spaceSign?adModel.d1.spaceSign:'sd'}"></c:out> 
<c:forEach var="key" begin="1" end="2" step="1" varStatus="vs">  
<c:set var="longKey" value="b${vs.index}"  />
<c:out value="${adMap[longKey].spaceSign}" />   
<br>   
</c:forEach>  --%>
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery.touchSlider.js"></script> 
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
		case -2:JsBridge.cxhDetail(params);break;
		}
		//window.WebViewJavascriptBridge.callHandler(m, {'param': params}, function(responseData) {});
	}

	$(function(){
	//左右滑动
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn()
	},function(){
		$("#btn_prev,#btn_next").fadeOut()
	});
	
	$dragBln = false;
	
	$(".main_image").touchSlider({
		flexible : true,
		speed : 150,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e){
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	
	$(".main_image a").click(function(){
		if($dragBln) {
			return false;
		}
	});
	
	timer = setInterval(function(){
		$("#btn_next").click();
	}, 3000);
	
	$(".main_visual").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		},3000);
	});
	
	$(".main_image").bind("touchstart",function(){
		clearInterval(timer);
	}).bind("touchend", function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		}, 3000);
	});



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
		
	$(".seafood-box-max-a, .seafood-box-min-tp").each(function(){
    var $wrap= $(this),
        mainHeight=$wrap.width(),
        txtHeight = $(this).find(".seafood-box-line").height();
		$(this).find(".seafood-max-img").css({'height':mainHeight,'line-height':mainHeight+'px'}); 
		$(this).find(".seafood-min-img").css({'height':mainHeight,'line-height':mainHeight+'px'}); 
    	$(this).height(mainHeight+txtHeight)
 
		})
		
			
			$('.onefollow').nextAll().hide();
			$(".slide-more").show();
			$(window).scroll(function() {
				var scTop = $(this).scrollTop();
				var scHeight = $(document).height();
				var swHeight = $(this).height()
			
				if(scTop+swHeight==scHeight){
					setTimeout(function() {$('.onefollow').nextAll().show(),$('.slide-more-p').html('没有更多内容加载')},500);
					
				}
			})
			$(window).trigger('scroll');		
		
	});
	
	
	
</script>
<body>	

<div class="main-wrap">
	<div class="main_visual">
	<div class="flicking_con">
	<c:forEach var="record" items="${lunbocn}">  
			<c:set var="mkey" value="${record}"  />	
			<c:if test="${! empty adMap[mkey] }">	
				<a href="#">${cn.index}</a>
			</c:if>
	</c:forEach>	
	</div>
	<div class="main_image">
		<ul>
			<c:set var="imgcn" value="0"  />
			<c:forEach var="record" items="${lunbocn}">  
			<c:set var="mkey" value="${record}"  />	
			<c:if test="${! empty adMap[mkey] }">	
			<c:set var="imgcn" value="${imgcn+1 }"  />
				<li id="${mkey}" ><a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}');"><span class="img_3"><img src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg}"/></span></a></li>
			</c:if>
			</c:forEach>	 			
		</ul>
		<a href="javascript:;" <c:if test="${imgcn > 1 }"> id="btn_prev" </c:if> ></a>
		<a href="javascript:;" <c:if test="${imgcn > 1 }"> id="btn_next" </c:if> ></a>
	</div>
</div>
<!--main_visual end-->
	
		<!--地理标志产品大集合-->
		<div class="area-name">
			<div class="area-info">
				<h3 class="area-info-h3">地理标志产品大集合</h3>
				<span class="area-info-sp"><!-- <a href="#">更多>></a> --></span>
			</div>
			<div class="area-name-gc">
			<c:if test="${! empty adMap.B1}">
				<ul>
					<li id="B1" class="area-name-gb">
						<a class="area-name-sub" href="javascript:aonClick(0,'marketId=3&provinceId=410000&cityId=410200&title=开封')"><!-- <span class="area-gba">开封</span> --><img src="${adMap.B1.state eq 1?adMap.B1.adUrl:adMap.B1.replaceImg}"/></a>
					</li>
				<!-- 	<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">徐州</span><img src="../images/advertise/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">台湾</span><img src="../images/advertise/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">玉林</span><img src="../images/advertise/gc.jpg"/></a>
					</li>
					<li class="area-name-gb">
						<a class="area-name-sub" href="#"><span class="area-gba">洛阳</span><img src="../images/advertise/gc.jpg"/></a>
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
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}');">
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
		<div>
			<a href="javascript:aonClick(-2,'actId=1&userId=${memberId }')">
				<img src="${CONTEXT }/images/advertise/purchaseRe1.png" alt="" style="width:100%; height:auto">
			</a>
		</div>			
		
<!-- 如果关注的分类不止一个或者没有关注，就显示全部或者关注的，且广告位都为5个 -->
<c:if test="${cateCount ne 1 }">		
		<!--海鲜水产 D-->
		<c:if test="${! empty cateMap[1042]}">
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon"></i>
				<h3 class="seafood-ap-sp">${cateMap[1042].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1042].categoryId}&title=${cateMap[1042].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
			<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
			<c:set var="mkey" value="D${cn.index}"  />
					<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
							<span class="seafood-max-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
							<div class="seafood-box-line">
								<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
			</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="D${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		<!--新鲜果蔬E-->
		<c:if test="${! empty cateMap[1100]}">
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f2"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-b">${cateMap[1100].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1100].categoryId}&title=${cateMap[1100].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="E${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="E${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		<!--副食 F-->
		<c:if test="${! empty cateMap[1124]}">		
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f3"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-c">${cateMap[1124].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1124].categoryId}&title=${cateMap[1124].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="F${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="F${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		
		<!--酒水G-->
		<c:if test="${! empty cateMap[1125]}">	
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f4"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-d">${cateMap[1125].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1125].categoryId}&title=${cateMap[1125].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="G${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="G${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		<!--茶  H-->
		<c:if test="${! empty cateMap[1126]}">	
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f5"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-e">${cateMap[1126].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1126].categoryId}&title=${cateMap[1126].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="H${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="H${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>

		<!--粮油 I-->
		<c:if test="${! empty cateMap[913]}">			
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f7"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-g">${cateMap[913].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[913].categoryId}&title=${cateMap[913].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="I${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="I${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>		
		
		<!--干调 J-->
		<c:if test="${! empty cateMap[957]}">			
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f8"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-h">${cateMap[957].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[957].categoryId}&title=${cateMap[957].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="J${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="J${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
</c:if>		
		
		
		<!--禽蛋肉食 K-->
		<c:if test="${! empty cateMap[1127]}">			
		<div class="seafood">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f6"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-f">${cateMap[1127].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1127].categoryId}&title=${cateMap[1127].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="K${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="K${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
</c:if>
<!-- 判断关注多个类别结束 -->	

<!-- *********************当只关联一个分类时，默认展示该分类为第一层的楼层，
除了固定的5个广告位外，后续继续展示该分类的广告，新增展示15个广告位，继续拖动则，查其他分类楼层 -->	
<c:if test="${cateCount eq 1 }">

<c:if test="${! empty oneCateMap[gzCateId]}">			
<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f6"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-f">${oneCateMap[gzCateId].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${oneCateMap[gzCateId].categoryId}&title=${oneCateMap[gzCateId].cateName }')">更多>></a>
			</div>
			
	<c:forEach var="key" begin="1" end="5" step="1" varStatus="cn">  
		<c:set var="mkey" value="${keyMap[gzCateId]}${cn.index}"  />	
		<c:set var="molnum" value="${cn.index%5 }"  />
		<c:if test="${molnum eq 1 or  molnum eq 2}">
			<c:if test="${molnum==1 }">
			<div class="seafood-box-max"></c:if>
						<div id="${mkey}" <c:if test="${molnum==1 }">class="seafood-box-max-a"</c:if><c:if test="${molnum==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
			<c:if test="${molnum==2 }"></div></c:if>
		</c:if>		
		<c:if test="${molnum eq 0 or  molnum eq 3 or  molnum eq 4}">
			<c:if test="${molnum==3 }">
			<div class="seafood-box-min clearfix"></c:if>			
			
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
			<c:if test="${molnum==0 }"></div></c:if>
		</c:if>	
	</c:forEach>
</div>
</c:if>
<!-- *****************显示20个广告位结束**************************** -->	
	
<!-- ***************************************  下滑，展示更多的分类 ********************* -->
		<!--海鲜水产 D-->
		<c:if test="${! empty cateMap[1042] and gzCateId ne 1042}">
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon"></i>
				<h3 class="seafood-ap-sp">${cateMap[1042].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1042].categoryId}&title=${cateMap[1042].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
			<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
			<c:set var="mkey" value="D${cn.index}"  />
					<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
						<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
							<span class="seafood-max-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
							<div class="seafood-box-line">
								<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
								<c:if test="${adMap[mkey].jumpType eq 2 }">
								<span class="seafood-span">${adMap[mkey].showPrice }</span>
								</c:if>
							</div>
						</a>
					</div>
			</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="D${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		<!--新鲜果蔬E-->
		<c:if test="${! empty cateMap[1100] and gzCateId ne 1100}">
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f2"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-b">${cateMap[1100].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1100].categoryId}&title=${cateMap[1100].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="E${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="E${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img  onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		<!--副食 F-->
		<c:if test="${! empty cateMap[1124] and gzCateId ne 1124}">		
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f3"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-c">${cateMap[1124].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1124].categoryId}&title=${cateMap[1124].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="F${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="F${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		
		<!--酒水G-->
		<c:if test="${! empty cateMap[1125] and gzCateId ne 1125}">	
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f4"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-d">${cateMap[1125].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1125].categoryId}&title=${cateMap[1125].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="G${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="G${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
		
		
		<!--茶  H-->
		<c:if test="${! empty cateMap[1126] and gzCateId ne 1126}">	
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f5"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-e">${cateMap[1126].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1126].categoryId}&title=${cateMap[1126].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="H${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="H${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>

		<!--粮油 I-->
		<c:if test="${! empty cateMap[913] and gzCateId ne 913}">			
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f7"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-g">${cateMap[913].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[913].categoryId}&title=${cateMap[913].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="I${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="I${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>		
		
		<!--干调 J-->
		<c:if test="${! empty cateMap[957] and gzCateId ne 957}">			
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f8"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-h">${cateMap[957].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[957].categoryId}&title=${cateMap[957].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="J${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="J${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
</c:if>		
		
		
		<!--禽蛋肉食 K-->
		<c:if test="${! empty cateMap[1127] and gzCateId ne 1127}">			
		<div class="seafood onefollow">
			<div class="seafood-ap">
				<i class="seafood-ap-icon seafood-ap-icon-f6"></i>
				<h3 class="seafood-ap-sp seafood-ap-color-f">${cateMap[1127].cateName }</h3>
				<a class="seafood-ap-gd" href="javascript:aonClick(-1,'marketId=3&cateId=${cateMap[1127].categoryId}&title=${cateMap[1127].cateName }')">更多>></a>
			</div>
			<div class="seafood-box-max">
				<c:forEach var="key" begin="1" end="2" step="1" varStatus="cn">  
				<c:set var="mkey" value="K${cn.index}"  />
						<div id="${mkey}" <c:if test="${cn.index==1 }">class="seafood-box-max-a"</c:if><c:if test="${cn.index==2 }">class="seafood-box-max-a seafood-box-max-b"</c:if>>
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-max-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
									<strong class="seafood-st">${adMap[mkey].state eq 1?adMap[mkey].adName:'' }</strong>
									<c:if test="${adMap[mkey].jumpType eq 2 }">
									<span class="seafood-span">${adMap[mkey].showPrice }</span>
									</c:if>
								</div>
							</a>
						</div>
				</c:forEach>
			</div>
			<div class="seafood-box-min clearfix">
				<c:forEach var="key" begin="3" end="5" step="1" varStatus="cn">  
				<c:set var="mkey" value="K${cn.index}"  />
						<div id="${mkey}" class="seafood-box-min-tp">
							<a href="javascript:aonClick(${adMap[mkey].jumpType},'marketId=3&cateId=${adMap[mkey].categoryId}&productId=${adMap[mkey].productId}&memberId=${memberId }&businessId=${adMap[mkey].businessId}&title=${adMap[mkey].adName }&productSign=${adMap[mkey].productSign}&url=${adMap[mkey].jumpUrl}')">
								<span class="seafood-min-img"><img onerror="javascript:this.src='../images/advertise/def.png';"  src="${adMap[mkey].state eq 1?adMap[mkey].adUrl:adMap[mkey].replaceImg }"/></span>
								<div class="seafood-box-line">
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
		</c:if>
<!-- ***********************************    ************************* -->		
<div class="slide-more"><p class="slide-more-p">继续滑动，查找更多分类</p></div>
</c:if>

	
	</div>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>