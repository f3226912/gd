<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>账单</title>
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
			background: #f0f0f0;

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
		.main-wrap{ background: #f0f0f0; width:100%;}
		.tit-date{ height: 3.1rem; line-height: 3.1rem; border-top:1px #dedede solid; background: #f0f0f0; font-size: 1.2rem; width: 100%; float: left;}
		.titl-date-lt{ float: left; padding-left: 1.5rem; font-size: 1.2rem; color: #666; letter-spacing:0.5px; }
		.titl-date-lt span{font-size: 1.8rem; color:#ff6c00;}
		.titl-date-rt{ float: right; padding-right: 1rem;}
		.ahref{ color:#ff6c00; font-size: 1.2rem; display:inline-block;}
		.bill-flex{ display:-moz-box; display:-webkit-box; /* Safari and Chrome */width: 100%; border-bottom: 1px #f0f0f0 solid; height: 6.05rem; color: #666;}
		.bill-date-lt{ width: 17.5%; padding-top: 1.5rem; background: #fff; border-right: #f0f0f0 solid 1px; text-align: center; font-size: 1.2rem;}
		.bill-date-content{ width: 82%; background: #f9f9f9; font-size: 1.2rem;  }
		.price{ border-bottom:solid 1px #f0f0f0; height: 3rem; line-height: 3rem; padding-left: 1rem;}
		.price-por{ padding-left: 1rem; height: 3rem; line-height: 3rem; overflow: hidden; white-space: nowrap;text-overflow: ellipsis; padding-right: 1rem;}
		.ico-tp{ background: url(${CONTEXT }v1.0/images/ico-a.png) no-repeat; width: 1.7rem; height: 1.85rem; background-size:1.7rem 1.85rem; display: inline-block; position: relative; top:.3rem;}
		.no-data-tips{text-align:center; display:none; padding-top:50%; font-size: 1.3rem;}
		.layermcont{font-size:1.3rem;}
	</style>
</head>

<body>	
	<div class="main-wrap">

		<div class="tit-date">
			<div class="titl-date-lt">
				<p>
					<span class="dw-i getMonth">6月</span>
					<span class="dw-t getFullY" style="color:#666; font-size:1.2rem;"> 2016年</span><i class="ico-tp"></i>
				</p>
			</div>
			<div class="titl-date-rt"><a class="ahref" href="#">月账单>></a></div>
		</div>
		<!--账单日期天数-->
		<div class="bill-date-day">
			<p class="no-data-tips">暂无数据</p>
			<ul class="bill-date-list">
				
			</ul>
		</div>

	</div>

<script src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
<script src="${CONTEXT }v1.0/js/gdui.m.js"></script>
<script src="${CONTEXT }v1.0/js/jquery.cookie.js"></script>
<script src="${CONTEXT }v1.0/js/plugs/mobiscroll/mobiscroll.custom.min.js"></script>
<script>  

$(document).ready(function(){
	var myDate = new Date();
	var myFull = myDate.getFullYear()
	var myMonth = myDate.getMonth()+1;
	$(".getMonth").html(myMonth+'月');
	$('.getFullY').html(myFull);
})
if(!$.cookie("billsShowdialog")==1){
	layer.open({
	    content: '本页面只显示账单功能上线的账单',
	    btn: ['确定'],
	    yes:function() {
	    	 $.cookie("billsShowdialog",1, { path: '/', expires: 365 });
	    	 layer.closeAll();
	    }
	});
}

	var dateN = new Date();
	function dateNa() {
		function checkTime(i) {
			if(i<10){
				i ="0"+i;
			}
			return i
		};
		var aY = dateN.getFullYear()-1;
		var dM = dateN.getMonth()+1;
		var dd = new Date(aY+'-'+checkTime(dM));
		return dd;
	}
	var startTimeobj = $('.titl-date-lt').mobiscroll().date({
				    theme: 'android-ics light', //皮肤样式
				    display: 'modal', //显示方式 
				    mode: 'scroller', //日期选择模式
				    dateFormat: 'yy-mm', // 日期格式
				    setText: '确定', //确认按钮名称
				    cancelText: '取消',//取消按钮名称
				    dateOrder: 'yymm', //面板中日期排列格式
				    dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
				    showLabel:true,
					minDate: dateNa(), 
					maxDate: new Date(),
					lang: 'zh',
				    preset: 'date',
				    animate: "pop",
				    onBeforeShow: function (inst) { 
				    	inst.settings.wheels[0].length>2?inst.settings.wheels[0].pop():null; 
				    },
				    onSelect:function(valueText,inst){
				    	$('.titl-date-lt').find('.dw-i ,.dw-t').text(valueText);
				    	var arr = valueText.split('-');
				    	var arSplice = arr.join("");
				    	if(arr[1].slice(0,1)=="0"){
				    		arr[1]=arr[1].slice(1,2);
				    	}
				    	$('.titl-date-lt').find('.dw-i').text(arr[1]+'月');
				    	$('.dw-t').text(arr[0]+'年');
				    	pulldownRefresh({account:getUrlmenu.account,payTime:arSplice,channelType:getUrlmenu.channelType},arSplice);  
				   }
				});
		
	
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
	var getUrlmenu = getUrldata();
	if(getUrlmenu.payTime==undefined){	
		var mydate = new Date();
		var myyear = mydate.getFullYear();
		var mymth = mydate.getMonth()+1;
		function checkTime(i) {
			var b = i;
			if(i<10){
				i2 ="0"+i;
			}
			return i2
		};
		var dd = myyear+""+checkTime(mymth);
		getUrlmenu.payTime = dd;
	};
	pulldownRefresh({account:getUrlmenu.account,payTime:getUrlmenu.payTime,channelType:getUrlmenu.channelType});
	function pulldownRefresh(datas,flag) {
		$.ajax({
		   	type: "get",
		   	url: "${CONTEXT}billDetail/queryBillDetailList",
		   	dataType:'json',
		    data: datas,
		   	success: function(data){
		   		var dataList=null;	   	
		   		var dataList = data.datajson;
		   		var arrs = '';
		   		for(var i=0; i<dataList.length; i++) {
		   			var arrLs =  data.datajson[i].payTimeDesc.split(' ');
		   			arrs += '<li class="bill-flex">'+
					'<div class="bill-date-lt">'+
					'<p class="day-t">'+arrLs[0]+'</p>'+
					'<p class="time-t">'+arrLs[1]+'</p>'+
					'</div>'+
					'<div class="bill-date-content">'+
						'<p class="price">'+dataList[i].tradeAmount+'元</p>'+
						'<p class="price-por">'+dataList[i].subject+'</p>'+
					'</div>'+
				'</li>'
		   		}
		   		$('.bill-date-day').find('.bill-date-list').html(arrs);
		   		if(arrs){
		   			$('.titl-date-rt').find('.ahref').show();
		   			$('.bill-date-day').find('.no-data-tips').hide();
		   			
		   		}else{
		   			$('.titl-date-rt').find('.ahref').hide();
		   			$('.bill-date-day').find('.no-data-tips').show();	
		   		}
		   		$(".ahref").attr("href", "${CONTEXT }billDetail/showOrderReport?account="+getUrlmenu.account+'&'+"payTime="+(flag!=undefined?flag:getUrlmenu.payTime)+'&'+'channelType='+getUrlmenu.channelType);
		   	}
			
		})
	}
</script>
</body>
</html>
