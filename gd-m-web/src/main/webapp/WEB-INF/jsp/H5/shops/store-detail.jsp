<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商铺详情</title>
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
		em,i,b,strong{ font-style:normal; font-weight:normal;}
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
		
		.ui-box{display: -webkit-box; display: box;}
		.ui-f1{-webkit-box-flex:1;box-flex:1;}
		.ui-f2{-webkit-box-flex:2;box-flex:2;}
		.ui-hor{-webkit-box-orient:horizontal;box-orient:horizontal;}
		.ui-ver{-webkit-box-orient:vertical;box-orient:vertical;}
		.ui-ht{-webkit-box-align:start;box-align:start;}
		.ui-hc{-webkit-box-align:center;box-align:center;}
		.ui-he{-webkit-box-align:end;box-align:end;}
		.ui-hs{-webkit-box-align:stretch;box-align:stretch;}
		.ui-vt{-webkit-box-pack:start;box-pack:start;}
		.ui-vc{-webkit-box-pack:center;box-pack:center;}
		.ui-ve{-webkit-box-pack:end;box-pack:end;}
		.ui-vj{-webkit-box-pack:justify;box-pack:justify;}
		
		*{box-sizing: border-box;-webkit-box-sizing: border-box;}
		.fl{float: left;}
		.fr{float: right;}
		.ui-w-100{width: 100%;}
		.ui-h-100{height: 100%;}
		.ui-fs-12{font-size: 1.2rem;}
		.ui-fs-14{font-size: 1.4rem;}
		.ui-fs-16{font-size: 1.6rem;}
		.ui-fs-20{font-size: 2rem;}
		.ui-bgs-100per{background-size: 80% auto !important;}
		.ui-over-elli{white-space:nowrap; overflow:hidden; text-overflow:ellipsis;}
		.ui-over-elli2{
			display: -webkit-box;
		    overflow: hidden;
		    text-overflow: ellipsis;
		    -webkit-line-clamp: 2;
		    -webkit-box-orient: vertical;
		}
		
		
		
		body{background:#eeeeee;}
		.head-store{background: #fff; color: #666666;margin-top: 5rem;}
		.w-80{width: 8rem;}
		.h-90{height: 9rem;}
		.head-title{display: block; text-align: center; height: 5rem; font-size: 2rem; background: #3bb650; color: #fff;line-height: 5rem; position: fixed; width: 100%;top: 0;left: 0;}
		.head-title a{position:absolute;font-size: 1.6rem;color: #fff; margin-left:1rem ;left: 0;background: url(images/icon-1.png) no-repeat 0 1.6rem; background-size:1.2rem auto ; padding-left: 1.5rem;}
		.store-ico{ background: url(images/icon-store.png) no-repeat center center;}
		.call-numb{color: #ff9902;font-size: 1.2rem;}
		.call-numb p{ text-align: center;}
		.store-name{ font-size: 1.6rem; line-height: 9rem;}
		.icon-phone{background: url(images/com-icon-store.png) no-repeat center 1rem;height: 6rem;}
		.down-wrap{vertical-align: middle;z-index:110;display: none;text-align: center;height: 100%;width: 100%; background: rgba(27,27,27,0.8); position: fixed;bottom: 0;}
		.btn-app{ display: block; width: 15rem; height: 3rem; border-radius: 1rem; font-size: 1.6rem; color: #000000; font-weight: bold; text-align: center; line-height: 3rem; left: 50%; position: absolute; bottom: 3rem; margin-left: -7rem;}
		.down-wrap .img{ margin:0 auto; height: 21.5rem; width: 30rem; top: 50%; left: 50%; margin-top: -10.5rem; margin-left: -15rem; position: absolute;}
		.down-wrap .wx-tip{position: absolute; right: 0; width: 60%; top:2rem;}
		.down-close{width: 3rem; height: 3rem; display: block; position: absolute; right: 0.5rem;top: 0.5rem; background-size:6rem auto ;}
		.item{margin-top: 1rem;background:#fff; color: #999999; display: block;}
		.group{padding:1.2rem 0.8rem;}
		.group-ds{padding:0rem 0.8rem 0.8rem;}
		.group label{ color: #444444; font-weight: bold ;}
		.group .place em{margin-left: 1rem;}
		.goods li{float: left; width: 33.3%; text-align: center;margin-bottom: 1rem;}
		.goods li span{width: 80%;text-align: center;border: 1px solid #eee; display: inline-block;height: 2.8rem; line-height: 2.8rem; border-radius: 0.5rem; font-size: 1.2rem;color: #494949;}
	</style>
</head>

<body>	
	<div class="main-wrap">
		<div class="head-title">
			<a href="javascript:history.go(-1)">返回</a>
			商铺详情
		</div>
		<div class="head-store ui-box ui-hor ui-w-100 ui-vj">
			<div class="store-ico w-80 h-90 ui-bgs-100per"></div>
			<div class="ui-f1 ui-box ui-ver ui-vj">
				<h2 class="store-name ui-over-elli">${dto.shopsName }</h2>
			</div>
			<div class="w-80 h-90 call-numb">
				<div class="icon-phone ui-w-100 ui-bgs-100per"></div>
				<p class="show-down">拨打电话</p>
			</div>
		</div>
		<div class="ui-box ui-ver">
			<div class="item ui-w-100">
				<div class="group ui-w-100 ui-box ui-vj" style="border-bottom: 1px solid #eee;">
					<label class="ui-fs-14">商铺所在地</label>
					<div class="place ui-fs-14"><em>${dto.province}</em><em>${dto.city}</em><em>${dto.area}</em></div>
				</div>
				<div class="group ui-w-100 ui-box ui-vj">
					<label class="ui-fs-14">地址</label>
					<div class="ui-fs-14">${dto.address}</div>
				</div>
			</div>
			
			<div class="item ui-w-100">
				<div class="group ui-w-100">
					<label class="ui-fs-14">主营产品</label>
				</div>
				<div class="group-ds ui-w-100">
					<p>${dto.mainProduct}</p>
				</div>
			</div>
			
			<div class="item ui-w-100">
				<div class="group ui-w-100 ui-box ui-vj" style="border-bottom: 1px solid #eee;">
					<label class="ui-fs-14">经营模式</label>
					<div class="ui-fs-14">${dto.businessModelStr}</div>
				</div>
				<c:if test="${dto.level eq '4' }">
					<div class="group ui-w-100 ui-box ui-vj">
						<label class="ui-fs-14">经营类型</label>
						<div class="ui-fs-14">
							<c:if test="${dto.managementType eq '1' }">
								种养大户
							</c:if>
							<c:if test="${dto.managementType eq '2' }">
								合作社
							</c:if>
							<c:if test="${dto.managementType eq '3' }">
								基地
							</c:if>
						</div>
					</div>
				</c:if>
			</div>
			<div class="item ui-w-100">
				<div class="group ui-w-100 ui-box ui-vj">
					<label class="ui-fs-14">经营范围</label>
				</div>
				<div class="group-ds ui-w-100">
					<ul class="goods">
					<c:forEach var="rbc" items="${listRBC}">
						<li style="width: 33.3%;"><span>${rbc.cateName }</span></li>
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="down-wrap">
		<div class="img">
			<img src="images/app-down.png" alt=""/>
			<a href="javascript:;" class="btn-app"></a>
			<a href="javascript:;" class="down-close"></a>
		</div>
		<img src="images/app-wx-tip.png" alt="" class="wx-tip"/>
	</div>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script>
	(function(){
		function isWeiXin(){
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
//                    weChat: u.indexOf('MicroMessenger') > 0
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
        
		$('.call-numb').on('touchstart',function(){
			if(isWeiXin()){
        		$('.wx-tip').show();
        	}else{
        		$('.wx-tip').hide();
        	}
			$('.down-wrap').show();
		});
		$('.down-close').on('touchstart',function(){
			$('.down-wrap').hide();
		});
		$('.btn-app').on('touchstart',function(){
			if(Terminal.platform.iPhone || Terminal.platform.iPad || Terminal.platform.isMac){
        		$(".btn-app").attr("href","https://itunes.apple.com/cn/app/id1057048929?mt=8&l=cn");
        	}else if(Terminal.platform.android || Terminal.platform.isWin){
        		$(".btn-app").attr("href","http://www.gdeng.cn/nsy/nsy.apk");
        	}
		})
	})()
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>