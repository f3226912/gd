<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商活动详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }/v1.0/css/marketing/gys-css.css">
	<link rel="stylesheet" href="${CONTEXT }/v1.0/css/marketing/iconfont.css">
	<script type="text/javascript">
		var apiUrl='${apiUrl }';
		var CONTEXT = '${CONTEXT }';
	</script>
	

</head>

<body>	
	<div class="main-wrap">
		<div class="start-time-box">
			<div class="start-time">
				<i class="iconfont icon-time icon-color"></i>
				<span class="st-time-cb">距开始时间：</span>
				<span class="st-time-cl">28:32:45</span>
			</div>
			<span class="sign-up signMark">已报名</span>
		</div>
		<div class="detail-content clearfix">
			<div class="detail-img"><img src="images/sp-img_03.png"/></div>
			<div class="activity-name">
				<h3 class="activity-name-cp">活动名称：草莓产销汇</h3>
				<p class="activity-name-strat">开始时间：<span class="activity-strat-tm">2016-05-06  10:00:00</span></p>
				<p class="activity-name-strat activity-name-end">结束时间：<span class="activity-strat-ed">2016-05-10  10:00:00</span></p>
			</div>

		</div>
		<div class="activity-rules-click">
			<div class="activity-rules-si clearfix">
			    <span class="activity-rules-gz">活动规则</span>
			    <i class="iconfont icon-icon_arrow_bottom bot-color"></i>
			</div>
			 <p class="activity-rules-content">凡参加活动者，即视为同意接受本活动规则及奖品领取规则的所有条款，如有任何舞弊、欺诈、用户信息不真实等情形，主办方有权取消参与者参加活动的资格，并保留不另行通 知或采取其他相应法律措施的权利。 
			</p>
		</div>
		<div class="product-list-name">
			<ul class="product-list-ul clearfix">
				
			</ul>
		</div>
		
		
	<footer class="box-footer clearfix">
		<ul class="footer-ul">
			<li id="cancelBu"><a class="footer-li-a footerCancel" href="#">取消</a></li>
			<li id="addBu"><a class="footer-li-b" href="#">添加商品</a></li>
		</ul>
	</footer>
	</div>
	
	<script type="text/javascript" src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${CONTEXT }v1.0/js/jquery.mobile.custom.min.js"></script>
	<script type="text/javascript" src="${CONTEXT }v1.0/js/marketing/global.js"></script>
	<script type="text/javascript" src="${CONTEXT }v1.0/js/layer.m.js"></script>
	<script>
		 //var actId = "${actId}";
		/*获取url*/
          //ly added
          var state=null;//2代表位开始
          Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		
        /* $(function() { */
        	function getQueryString(name) { 
        		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
        		var r = window.location.search.substr(1).match(reg); 
        		if (r != null) return unescape(r[2]); return null; 
        		}  
        		
        	$('.bot-color').on('tap',function() {

        		if($('.activity-rules-content').is(':visible')){

        			$('.activity-rules-content').slideUp();
        		}else{

        			$('.activity-rules-content').slideDown();
        		}
        	});
        	
        	function addShow(){
				//window.location.href='/PromotionAcitivity/promotionDetail?actId=1&type=0';
				var type=getQueryString('type');
				if(type==0){
					$(".box-footer").hide();
				}
			}addShow();
        	
        	
              //添加商品
            $('.footer-li-b').on('tap',addAct);
            function addAct(){
            	 var url = location.search;	         				
 	 			var value = url.match(/actId=\d+/g);
 		        var actId=value[0].split("=");
 	 			var ua = navigator.userAgent.toLowerCase();	
 	 			if (/iphone|ipad|ipod/.test(ua)) {
 	 				addAction(actId[1]);
 	 			} else if (/android/.test(ua)) {
 	 				window.manageI.addingGoods(actId[1]);	
 	 			}
	 		}
         
        	 
			var typeId=getQueryString("actId");
            var userId=getQueryString("userId");

            financialData(typeId,userId);
			function financialData(id,userId) {			
				$.ajax({
					type: "get",
				   	url: CONTEXT + "activityPromotion/activity/detail",
				   	dataType:'json',
				    data: {
				    	actId:id
				    },
				   	success: function(data){
				   		state=data.object.rstatus;
				   		if(state==1){
				   			$(".signMark").html("已结束");
				   			$("#cancelBu").show();
				   			$("#addBu").css("width","50%");	
				   		}else if(state==2){
				   			$(".signMark").html("未开始");
				   			$("#cancelBu").hide();
				   			$("#addBu").css("width","100%");

				   		}else if(state==3){
				   			$(".signMark").html("进行中");
				   			$("#cancelBu").show();
				   			$("#addBu").css("width","50%");
				   		}
				   		var introduction = $('.activity-rules-click').find('.activity-rules-content').html(data.object.introduction)
				   		var nameStr = '';
				   		//var imTp = $(".imgTemp").prop("src",data.object.pictureRefList[0].url120);
				   		    nameStr +="<div class='detail-img'>"+'<img'+' src='+data.object.pictureRefList[0].url120+'>'+"</div>"+
						"<div class='activity-name'>"+
							"<h3 class='activity-name-cp'>活动名称："+data.object.name+"</h3>"+
							"<p class='activity-name-strat'>开始时间："+"<span class='activity-strat-tm'>"+data.object.startTime+"</span>"+"</p>"+
							"<p class='activity-name-strat activity-name-end'>结束时间："+"<span class='activity-strat-ed'>"+data.object.endTime+"</span>"+
							"</p>"+
						"</div>"
                        $('.main-wrap').find('.detail-content').html(nameStr);
				   		//倒计时
				   		 var endTime=data.object.endTime;
						 	 endTime=endTime.replace(/-/g,"/");
				   		var date2=Date.parse(endTime);
				   		console.log(data.object.endTime);
				   		var date1=new Date().getTime();
				   		countdown((date2-date1)/1000);
				   		
				   	},
				   	error:function(jqXHR) {
				   		alert("发生错误："+jqXHR.status);
				   	}
				   	
				})
				//活动商品查询接口
				$.ajax({
				   	type: "get",
				   	url: CONTEXT + "activityPromotion/product/list",
				   	dataType:'json',
				    data: {
				    	userId:userId,
				    	productType:1
				    	
				    },
				   	success: function(data){	
				   		//console.log(data);
				   		if(data.statusCode==0){
				   			var queryInterface = '';
					   		var listS = data.object.recordList;
					   		for(var i = 0; i<listS.length; i++) {
					   			queryInterface +="<li class='product-list-ul-li'>"+
						   "<div class='product-list-li-box'>"+
							"<div class='product-img'>"+"<img"+ ' src='+data.object.recordList[i].imgUrl+">"+"</div>"+
							  "<div class='product-list-activity'>"+
								 "<h3 class='product-activity-h3'>"+data.object.recordList[i].productName+"</h3>"+
								 "<p class='product-activity-stock'>库存："+"<span class='activity-strat-tm'>"+data.object.recordList[i].stockCount+"</span> 吨"+"</p>"+
								 "<p class='product-activity-hdj'>活动价："+"<span class='activity-strat-ed'>"+data.object.recordList[i].price+"</span> 元/吨"+"</p>"+
							  "</div>"+
						   "</div>"+
//					    	<p class="product-description">未通过商品上架规则，无法参加活动，详情查看商品库</p>
					     "</li>";
					     
					   		}
					   	//判断没有产品
						   		if(listS.length==0){
						   			var foots = '<div class="footer-application"><a class="footer-li-a" href="${CONTEXT }activityPromotion/activity/detail?add=1">立刻申请</a></div>'
						   			$('.box-footer').html(foots);
						   		 }	
					   		 
	                        $('.product-list-name').find('.product-list-ul').html(queryInterface);
	                       }else{
	                    	   var foots = '<div class="footer-application"><a class="footer-li-a footHre" href="#">立刻申请</a></div>'
						   			$('.box-footer').html(foots);
	                    	   //${CONTEXT }activityPromotion/activity/detail?add=1
	                       }
				   		
				   		
				   	},
				   	error:function(jqXHR) {
				   		alert("发生错误："+jqXHR.status);
				   	}
				   	
				})
			}
			//取消

			$(".footerCancel").on('tap',footerCancle);
			function footerCancle(){
					/*financialData(typeId,userId);
					if(state==1){
				   			$(".signMark").html("已结束");
				   			$("#cancelBu").show();
				   			$("#addBu").css("width","50%");	
				   		}else if(state==2){
				   			$(".signMark").html("未开始");
				   			$("#cancelBu").hide();
				   			$("#addBu").css("width","100%");

				   		}else if(state==3){
				   			$(".signMark").html("进行中");
				   			$("#cancelBu").show();
				   			$("#addBu").css("width","50%");
				   		}*/		
					var r=confirm("是否取消参加活动？");
				  	if (r==true){
					  	$.ajax({
							   	type: "get",
							   	url: CONTEXT + "activityPromotion /activity/cancel",
							   	dataType:'json',
							    data: {
							    	actId:typeId,
							    	userId:userId				    	
							    },
							   	success: function(data){				   		
							   		var url = location.search;
							  		var value = url.match(/actId=\d+/g);
			 		        		var actId=value[0].split("=");	 	 			
			 	 					var ua = navigator.userAgent.toLowerCase();
							   		if(state==1){	
							   				$("#cancelBu").show();
					   						$("#addBu").css("width","50%");			   			
							   				$('.product-list-ul-li').hide();
							   				alert('活动已结束');
							   				location.reload(CONTEXT + "activityPromotion /activity/cancel");
							   		}else if(state==2){//data.statusCode
							   			$("#cancelBu").hide();
					   					$("#addBu").css("width","100%");	
							   			$('.product-list-ul-li').hide();
			 	 								if (/iphone|ipad|ipod/.test(ua)) {
								 	 				cancelAction();
								 	 			} else if (/android/.test(ua)) {
								 	 				window.manageI.cancelAction();	
								 	 			}
							   			window.location.href=CONTEXT + "PromotionAcitivity/showIndex";
							   		}
							   		else if(state==3){
							   			$("#cancelBu").show();
					   						$("#addBu").css("width","50%");	
							   			$('.product-list-ul-li').show();
							   			alert('活动已开始，无法取消');
							   			location.reload(CONTEXT + "activityPromotion /activity/cancel"); 
							   		}	
							   	},
							   	error:function(jqXHR) {
							   		alert("发生错误："+jqXHR.status);
							   	}
							   	
							 })
				    }else{
				  		 return false;

				    }
				//取消接口
				$('.footer-li-a').on('tap',function() {
				$.ajax({
				   	type: "get",
				   	url: CONTEXT + "activityPromotion /activity/cancel",
				   	dataType:'json',
				    data: {
				    	actId:id,
				    	userId:userId
				    	
				    },
				   	success: function(data){	
				   		console.log(data.statusCode.length);
				   		if(data.statusCode==0){				   			
				   				$('.product-list-ul-li').hide();
				   				alert('活动已结束')
				   				location.reload(CONTEXT + "activityPromotion /activity/cancel");
				   		}else if(data.statusCode==2){
				   			$('.product-list-ul-li').hide();
				   			alert('是否取消参加活动')
				   			window.location.href=CONTEXT + "PromotionAcitivity/showIndex";
				   		}
				   		else{
				   			$('.product-list-ul-li').show();
				   			alert('活动已开始，无法取消')
				   			location.reload(CONTEXT + "activityPromotion /activity/cancel"); 
				   		}	
				   	},
				   	error:function(jqXHR) {
				   		alert("发生错误："+jqXHR.status);
				   	}
				   	
				  })
				});
			}
			//倒计时
			
			function countdown(s){
				var h=Math.floor(s/60/60),
						m=Math.floor((s-(h*60*60))/60),
						newS=Math.floor(s-(h*60*60)-(m*60));
				$(".st-time-cl").html(h+':'+m+':'+newS);
				var timer=setInterval(function(){//这是倒计时循环部分
					newS--;
					if(newS<0){
						newS=59;
						m--;
					}
					if(m<0){
						m=59;
						h--;
					}
					h=fix(h,2);
					m=fix(m,2);
					newS=fix(newS,2);
					$(".st-time-cl").html(h+':'+m+':'+newS);
				},1000);
			}
			
			$("body").on("tap",".footHre",footLink);
			
			function footLink(){				
 	 		 var url = location.search;	         	 			
 	 			var value = url.match(/actId=\d+/g);
 		        var actId=value[0].split("=");
 	 			
 	 			var ua = navigator.userAgent.toLowerCase();	
 	 			if (/iphone|ipad|ipod/.test(ua)) {
 	 				addAction(actId[1]);
 	 			} else if (/android/.test(ua)) {
 	 				window.manageI.addingGoods(actId[1]);	
 	 			}
			}
			
       /*  }) */
	</script>


<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>