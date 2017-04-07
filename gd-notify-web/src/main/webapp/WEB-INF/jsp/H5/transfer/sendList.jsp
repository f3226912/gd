<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		
		<title>物流管理</title>
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title></title>
		<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.min.css">
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/logisticsOrder.css"/>
		<style type="text/css">
			.mui-fs-18p{
				font-size: 18px;
			}
			.pic_phone > .mui-icon-phone{
				font-size: 45px;
			}
			a{
				color: #ff6c00;
			}
			.mui-scroll-wrapper{
			top:40px;
			} 
			.mui-bar{
				padding-right: 0px;
				padding-left: 0px;
			}
			*{
				font-size:13px;
			}
		</style>
		<script type="text/javascript" src="${CONTEXT }js/transfer/sendList.js"></script>
		<script type="text/javascript" src="${CONTEXT }js/transfer/nstList.js"></script>
	</head>
<body>
<input type="hidden" id="userID" name="userId" value="${userId}">
<input type="hidden" id="client" name="fromCode" value="${fromCode}">
<jsp:include page="/WEB-INF/jsp/pub/transfer/head.jsp"></jsp:include>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
			
			
			<jsp:include page="/WEB-INF/jsp/pub/transfer/template.jsp"></jsp:include>
			
			<!-- 已发布模板开始处 -->
			<div id="published" class="outline">
				
			</div>
			<!-- 已发布模板结束处 -->
			
			<!-- 待成交模板开始处以这个模板为基础 -->
			<div id="pendFinished" class="outline  mui-hidden">
				
			</div>
			
			<!-- 已成交模板开始处-->
			<div id="dealed" class="outline mui-hidden">
				
			</div>
			<!-- 已成交模板结束处 -->
			
			<!-- 已完成模板开始处-->
			<div id="finished" class="outline mui-hidden">
			
			</div>	 
			<!-- 已完成模板结束处 -->
			
			<!-- 未完成模板开始处-->
			<div id="unfinished" class="outline mui-hidden">
			</div>
			
			</div>
		</div>

		
		<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
		<script src="${CONTEXT }v1.0/js/jquery.mobile.custom.min.js"></script>
		<script>
			var page = 1;
			var type = 0;
			var fromCode = ${fromCode};
			var pageTotal = 1;
			var showAll = false;
			var changeMark=0;
		/**
		 * 上拉加载，下拉刷新的js
		 */
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
						callback: pullupRefresh
					}
				}
			});

		if(!sessionStorage.countNum){
				sessionStorage.setItem("countNum", "0");
			}else {
				changeMark=sessionStorage.getItem("countNum");
			}
		
			function pulldownRefresh() {
				page = 1;
				if (type==0) {
					getSendList(${userId},page++);
				} else {
					getnstList(type,${userId},page++);
				}
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
			}
			difSessionNum(changeMark) ;
			type = parseInt(changeMark);

			function pullupRefresh() {
				if (type==0) {
					getSendList(${userId},page++);
				} else {
					getnstList(type,${userId},page++);
				}
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //
			}
			if (mui.os.plus) {
				mui.plusReady(function() {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().pullupLoading();
					}, 1000);

				});
			} else {
				mui.ready(function() {
					mui('#pullrefresh').pullRefresh().pullupLoading();
				});
			}

	/*	页面切换的js*/
	
		$(document).ready(function(){
			//左滑
			$("body").swipeleft(function(){
				changeHead("left");	
			});
			//右滑
			$("body").swiperight(function(){
				changeHead("right");

			});
			//点击已发布
			$("#aPublished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				//$(".outline").eq(0).addClass("mui-hidden").end().eq(1).removeClass("mui-hidden");
				var ot=$(".outline").eq(0);
				$(".outline").each(function(){
					$(this).addClass("mui-hidden");
				});
				ot.removeClass("mui-hidden");
				
				$(this).addClass('bottom_red');
				changeMark=0;
				sessionStorage.setItem("countNum", "0");
				type=0;
				pulldownRefresh();
			});
			//点击待成交
			$("#aDeal").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				var ot=$(".outline").eq(1);
				$(".outline").each(function(){
					$(this).addClass("mui-hidden");
				});
				ot.removeClass("mui-hidden");
				$(this).addClass('bottom_red');
				changeMark=1;
				sessionStorage.setItem("countNum", "1");
				type=1;
				pulldownRefresh();
			});
			//点击已成交
			$("#aDealed").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				var ot=$(".outline").eq(2);
				$(".outline").each(function(){
					$(this).addClass("mui-hidden");
				});
				ot.removeClass("mui-hidden");
				$(this).addClass('bottom_red');
				changeMark=2;
				sessionStorage.setItem("countNum", "2");
				type=2;
				pulldownRefresh();
			});
			//点击已完成
			$("#aFinished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				var ot=$(".outline").eq(3);
				$(".outline").each(function(){
					$(this).addClass("mui-hidden");
				});
				ot.removeClass("mui-hidden");
				$(this).addClass('bottom_red');
				changeMark=3;
				sessionStorage.setItem("countNum", "3");
				type=3;
				pulldownRefresh();
			});
			//点击未完成
			$("#aUnfinished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				var ot=$(".outline").eq(4);
				$(".outline").each(function(){
					$(this).addClass("mui-hidden");
				});
				ot.removeClass("mui-hidden");
				$(this).addClass('bottom_red');
				changeMark=4;
				sessionStorage.setItem("countNum", "4");
				type=4;
				pulldownRefresh();
			})

		})

		function changeHead(direction){
			if(changeMark==0){	//已发布
				if(direction=="left"){
					$("#topHead a").eq(0).removeClass('bottom_red').end().eq(1).addClass('bottom_red');
					$(".outline").eq(0).addClass("mui-hidden").end().eq(1).removeClass("mui-hidden");
					type=1;
					pulldownRefresh();
					changeMark=1;
					sessionStorage.setItem("countNum", "1");
				}else if(direction=="right"){

				}			

			}else if(changeMark==1){ //待成交
				if(direction=="left"){
					$("#topHead a").eq(1).removeClass('bottom_red').end().eq(2).addClass('bottom_red');
					$(".outline").eq(1).addClass("mui-hidden").end().eq(2).removeClass("mui-hidden");
					type=2;
					pulldownRefresh();
					changeMark=2;
					sessionStorage.setItem("countNum", "2");
				}else if(direction=="right"){
					$("#topHead a").eq(1).removeClass('bottom_red').end().eq(0).addClass('bottom_red');
					$(".outline").eq(1).addClass("mui-hidden").end().eq(0).removeClass("mui-hidden");
					type=0;
					pulldownRefresh();
					changeMark=0;
					sessionStorage.setItem("countNum", "0");
				}	
				
			}else if(changeMark==2){ //已成交
				if(direction=="left"){
					$("#topHead a").eq(2).removeClass('bottom_red').end().eq(3).addClass('bottom_red');
					$(".outline").eq(2).addClass("mui-hidden").end().eq(3).removeClass("mui-hidden");
					type=3;
					pulldownRefresh();
					changeMark=3;
					sessionStorage.setItem("countNum", "3");
				}else if(direction=="right"){
					$("#topHead a").eq(2).removeClass('bottom_red').end().eq(1).addClass('bottom_red');
					$(".outline").eq(2).addClass("mui-hidden").end().eq(1).removeClass("mui-hidden");
					type=1;
					pulldownRefresh();
					changeMark=1;	
					sessionStorage.setItem("countNum", "1");
				}

			}else if(changeMark==3){ //已完成
				if(direction=="left"){
					$("#topHead a").eq(3).removeClass('bottom_red').end().eq(4).addClass('bottom_red');
					$(".outline").eq(3).addClass("mui-hidden").end().eq(4).removeClass("mui-hidden");
					type=4;
					pulldownRefresh();
					changeMark=4;
					sessionStorage.setItem("countNum", "4");

				}else if(direction=="right"){
					$("#topHead a").eq(3).removeClass('bottom_red').end().eq(2).addClass('bottom_red');
					$(".outline").eq(3).addClass("mui-hidden").end().eq(2).removeClass("mui-hidden");
					type=2;
					pulldownRefresh();
					changeMark=2;
					sessionStorage.setItem("countNum", "2");

				}

			}else if(changeMark==4){ //未完成
				if(direction=="left"){				
				}else if(direction=="right"){
					$("#topHead a").eq(4).removeClass('bottom_red').end().eq(3).addClass('bottom_red');
					$(".outline").eq(4).addClass("mui-hidden").end().eq(3).removeClass("mui-hidden");
					type=3;
					pulldownRefresh();
					sessionStorage.setItem("countNum", "3");
					changeMark=3;	
				}

			}else{
				console.log('changeHead方法有误');
			}
		}

		mui.ready(function() {
			pulldownRefresh(); 
		});

		//封装changeMark方法
		function difSessionNum(num){
			num=parseInt(num);
			$("#topHead a").each(function() {
				$(this).removeClass('bottom_red');
			});
			$("#topHead a").eq(num).addClass('bottom_red');

			$(".outline").each(function(){
				$(this).addClass("mui-hidden");
			});
			$(".outline").eq(num).removeClass("mui-hidden");
			type=0;			
		}
		
		/*$(".ahref").on("tap",function(){
		location.href=$(this).data("ahref");
	})*/	
		$(document).ready(function(){
			$("body").on("tap",".ahref",function(){
				location.href=$(this).data("ahref");
			})		
		})
	
		
	</script>
<div id="showLayout">
	
</div>
</body>
</html>
