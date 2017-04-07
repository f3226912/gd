<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>金融服务</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }v1.0/js/plugs/mobiscroll/mobiscroll.custom.css">
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
			background: #f7f7f7;

		}
		article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary{display:block;}
		ul, li, ol, dl, dd, dt{list-style:none;list-style-position:outside;}
		img	{ border:none;max-width: 100%;height: auto;}
		a{color:#565656;}
		a:link, a:hover{text-decoration:none;}
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
		.main-wrap{ background: #f7f7f7; width:100%;}
		.banner{ text-align: center; position: relative; background-color: #fff; border-bottom: solid 1px #e5e5e5;}
		.banner-img{ width: 100%; text-align: center; position: relative;}
		.transaction-sum{ position: absolute; top: 0; color: #212121; left: 50%; margin-left: -2.15rem; margin-top: 2.35rem;}
		.transaction-1{ font-size: 1.2rem;}
		.transaction-2{ font-size: 1.6rem; padding-top: .8rem; color:#a98e67;}
		.transaction-3{ font-size: 1.2rem; padding-top: 1rem;}
		.grade{ padding: .8rem 0 0 .6rem;}
		.noStar{ background: url(${CONTEXT }v1.0/images/icon-a.png) no-repeat; background-position: -2.55rem -0.4rem; background-size:9.5rem 12.1rem; width:2rem; height: 1.6rem; float: left;}
		.hasStar{ background-position: -0.6rem -0.4rem;}
		.content-left{ text-align: left; padding:1.3rem 0 1.3rem 1rem; color: #000;min-height:3.6rem}
		.transaction-zt ,.transaction-zb{ padding-top: .6rem; font-size: 1.2rem;}
		.process{ background:#fff; margin-top: 1.5rem; border-bottom: solid 1px #eaeaea;}
		.process-ctn{ width:100%; height: 3.9rem; line-height: 3.9rem; border-top: solid 1px #eaeaea; position: relative;}
		.speed{ color: #a98e67; font-size: 1.3rem; display: inline-block; width: 5em; padding-left: 5.5rem;}
		.speed-black{ color: #000; font-size: 1.2rem; display: inline-block;}
		.process-icon-1{ background: url(${CONTEXT }v1.0/images/icon-a.png) no-repeat; background-position: -5.85rem -.4rem; background-size:9.5rem 12.1rem; width:3rem; height: 2.6rem; display: inline-block; position: absolute; top: .75rem; left: 1.6rem;}
		.process-icon-2{  background-position: -5.85rem -4.4rem;}
		.process-icon-3{  background-position: -5.85rem -8.45rem;}
		.select-credit{ text-align: center; background: #fff; margin-top: 1.5rem; border-top: solid 1px #e5e5e5;}
		.demo-label-li{ float: left; width: 50%; height: 3.9rem; line-height: 3.9rem; display:inline-block; border-top:solid #eaeaea 1px; -webkit-tap-highlight-color:rgba(0,0,0,0);}
		.select-credit-tit{ height: 4rem; line-height: 4rem; font-size: 1.3rem; color: #000;}
		.demo-label-li:nth-child(1){border-right:solid #eaeaea 1px; margin-left: -1px;}
		.demo-label-li:nth-child(3){border-right:solid #eaeaea 1px; margin-left: -1px;}
		.extend-credit{ background: #a98e67; height: 5.2rem; line-height: 5.2rem; text-align: center; font-size: 1.8rem; color: #fff; -webkit-tap-highlight-color:rgba(0,0,0,0);}
		.extend-credit-none{ background: #dbd6d0;}
		.demo-label{ position: relative; width: 100%;}
		.demo-radio-none { width: 1.3rem; height: 1.3rem; background: #fff; border: solid 1px #bfbfbf; display:inline-block; position: absolute; left:5.8rem; top: 1.3rem;}
		.demo-radioInput{ text-align: left; display: inline-block;position: absolute; left:8rem; font-size:1.2rem;}
		.demo-radio { background: #a98e67; border: solid 1px #a98e67;}
	</style>
</head>

<body>	
	<div class="main-wrap">
		<div class="banner">
			<div class="banner-img">
				<img src="${CONTEXT }v1.0/images/ban-an.png"/>
			</div>
			<div class="transaction-sum">
				<p class="transaction-1">我的交易总金额</p>
				<p class="transaction-2"></p>
				<p class="transaction-3">我现在的星级</p>
				<div class="grade">
					<ul class="grade-st">
						<li class="noStar"></li>
						<li class="noStar"></li>
						<li class="noStar"></li>
						<li class="noStar"></li>
						<li class="noStar"></li>
					</ul>
				</div>
			</div>
			<div class="content-left">
				<p class="transaction-zt"></p>
				<p class="transaction-zb"></p>
			</div>
		</div>
		<div class="process">
			<div class="process-ctn">
				<i class="process-icon-1"></i>
				<span class="speed">速度快</span>
				<span class="speed-black">1分钟申请</span>
			</div>
			<div class="process-ctn">
				<i class="process-icon-1 process-icon-2"></i>
				<span class="speed">额度高</span>
				<span class="speed-black">最高可贷款300万</span>
			</div>
			<div class="process-ctn">
				<i class="process-icon-1 process-icon-3"></i>
				<span class="speed">流程简单</span>
				<span class="speed-black">一键下单，方便快捷</span>
			</div>
		</div>

		<div class="select-credit clearfix">
			<div class="select-credit-tit">请选择您需要贷款的额度</div>
			<ul class="credit-cek">
				<li class="demo-label-li">
				<div class="demo-label">
			        <div class="demo-radio demo-radio-none"></div>
			        <span class="demo-radioInput">5-10万</span>
			    </div>
			    </li>
				<li class="demo-label-li">
				<div class="demo-label">
			        <div class="demo-radio-none"></div>
			        <span class="demo-radioInput">10-50万</span>
			    </div>
			    </li>
				<li class="demo-label-li">
				<div class="demo-label">
			        <div class="demo-radio-none"></div>
			        <span class="demo-radioInput">50-100万</span>
			    </div>
			    </li>
				<li class="demo-label-li">
				<div class="demo-label">
			        <div class="demo-radio-none"></div>
			        <span class="demo-radioInput">100-300万</span>
			   </div>
			  	</li>
			</ul>

		</div>
		<div class="extend-credit">我要贷款</div>
	</div>

<script src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
<script src="${CONTEXT }v1.0/js/jquery.mobile.custom.min.js"></script>
<script>
/**取参数**/
	function getUrldata(){
		var searchStr = window.location.search.substr(1),
            arr = searchStr.split("&"),
            regObj = {};
        for(var i = 0 ;i<arr.length;i++){
            regObj[arr[i].split("=")[0]] = arr[i].split("=")[1]
        }
        return regObj;
	}
	$(function(){
		var winH = $(window).height();
		var docH = $(".main-wrap").height();
		if(docH<winH){
			$(".extend-credit").css({"margin-top":winH-docH+"px"})
		}
	});
	var getUrlmenu = getUrldata();
	financialData({memberId:getUrlmenu.memberId});
	function financialData(datas) {
		$.ajax({
		   	type: "get",
		   	url: '${CONTEXT}credit/getInfo',
		   	dataType:'json',
		    data: datas,
		   	success: function(data){
		   		
				
		   		var datajson = data.datajson;
		   		var maxDt = $('.transaction-2').text(datajson.tradeAmount+'元');
		   		if(datajson.tradeAmount<10000000){
		   			$('.content-left').find('.transaction-zt').text('您在农商友的交易额还未达到星级标准，加油哦！').css({'color':'#d52300'});
		   			$('.content-left').find('.transaction-zb').text('请使用农商友智能POS机收款，优先享受农商友各项优质服务！');
		   			$('.demo-label').find('.demo-radio-none').css({'background':'#fff','border':'1px solid #dcdcdc'});
		   			$('.demo-label').find('.demo-radioInput').css({'color':'#b7b7b7'});
		   			$('.extend-credit').addClass('extend-credit-none');
		   		}else{
		   			$('.content-left').find('.transaction-zt').text('您在农商友平台的交易额已达到星级标准！');
		   			$('.content-left').find('.transaction-zb').text('您将可以获得优质、便捷的金融服务！为您的经营快速发展助力！');
		   			
		   		}
		   		
		   		var noStar = $('.grade-st').find('li');
		   		for(var i=0; i<noStar.length; i++) {
		   			if($('.grade-st').find('li').eq(i).index(".noStar")<datajson.level && datajson.level>0){
		   				noStar.eq(i).addClass('hasStar');
		   			}
		   			
		   		}
		   	}
		   	
		})
		
	}
	var $demoLabel = $('.demo-label-li').find('.demo-label');
		$demoLabel.on('tap',function() {
			$demoLabel.find('.demo-radio-none').removeClass('demo-radio');
			$(this).find('.demo-radio-none').addClass('demo-radio');
			
		});
	
	
	$('.extend-credit').on('tap',function() {
		var dataSum = $('.demo-radio').parent().parent('li').index()+1;
		if(!$(this).hasClass('extend-credit-none')){
			$.ajax({
			   		type: "get",
			   		url: "${CONTEXT}credit/applyCredit",
			   		data: {memberId:getUrlmenu.memberId,creditQuotaRange:dataSum},
			   		success: function(data){
					$('.extend-credit').text('贷款申请已受理，稍后会有客户经理跟您联系。').css('font-size','1.3rem');		
			   	} 	
			})	
		}
			

})
</script>
</body>
</html>
