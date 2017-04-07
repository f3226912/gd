<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>消息</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

			<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css"  href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>	
	</head>
<body>
<input type="hidden" id="userID" name="userID" value="${systemUserId }">
		<header class="mui-bar mui-bar-nav bgcolor-3aa">
			<!--  <a class="mui-icon mui-icon-arrowleft mui-pull-left top-left-right" href="javascript:history.go(-1);">返回</a>-->
			<a id="info" class="mui-icon mui-pull-right top-left-right">
				<!-- <span class="font-col-orange">
				3
				</span><span>条未读</span>-->
			</a>
			<h1 class="mui-title font-col-white top-middle">消息列表</h1>
		</header>
		<nav class="mui-bar mui-bar-tab">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge" id="unReadData" style="display: none;">
				</span></span>
				<span class="mui-tab-label" style="color: #007aff;">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn" href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label">我的</span>
			</a>
		</nav>
		<!--下拉刷新容器-->
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view mui-table-view-chevron">
				
				</ul>
			</div>
		</div>
		
		<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.js"></script>
		<script src="${CONTEXT }js/common.js"></script>
		<script>
		//从第几条开始
		var startRow=0;
		//每页面显示 多少条
		var endRow=10;
		
		function getMessageData(data){
			var titleSpan="";
			var timeSpan="";
			var contentSpan="";
			var table = document.body.querySelector('.mui-table-view');
			//var cells = document.body.querySelectorAll('.mui-table-view-cell');
			for (var i = 0, len = i + endRow; i < len; i++) {
				if(data[i]==null){
					break;
				}
				var li = document.createElement('li');
				li.setAttribute("dataId",data[i].id);
				li.className = 'mui-table-view-cell pr-15p';
				if(data[i].isread=="1"){
					titleSpan='<img src="${CONTEXT}v1.0/module/mui/examples/hello-mui/images/ydot.png" class="yellow-dot">'+
						'<span class="fontsize-16p" >'+data[i].title+'</span>';
					timeSpan='<span class="mui-h5">'+
					data[i].createTime.substring(0,10)+
						'</span>';
					contentSpan='<p class="content-line-2">'+data[i].content+'</p> ';
				}else{
					titleSpan='<img src="${CONTEXT}v1.0/module/mui/examples/hello-mui/images/ydot.png" class="yellow-dot mui-hidden">'+
						'<span class="viewed-font fontsize-16p" >'+data[i].title+'</span>';
					timeSpan='<span class="mui-h5 viewed-font">'+
					data[i].createTime.substring(0,10)+
						'</span>';
					contentSpan='<p class="content-line-2 viewed-font">'+data[i].content+'</p> ';
				}
				li.innerHTML='<div class="mui-table">'+
				'<div class="mui-row">'+
					'<div class="mui-table-cell mui-col-xs-8">'+
						'<h4 class="mui-ellipsis">'+
						titleSpan+
						'</h4>'+
					'</div>'+
					'<div class="mui-table-cell mui-col-xs-4 mui-text-right mt-3p" >'+
					timeSpan+
					'</div>'+
				'</div>'+
				'<div class="mui-row">'+
					'<div class="mui-table-cell mui-col-xs-12 mtb-05r">'+
					contentSpan +         				
					'</div>'+				                    	
				'</div>'+
			'</div>';
			table.appendChild(li);
			}
		}
		
		getUnReadData('${systemLoginUser.userID }',$("#unReadData"));
		//上拉加载下拉刷新的初始化
		var pullinit =	mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					/*down: {
						callback: pulldownRefresh
					}*/
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			/**
			 * 下拉刷新具体业务实现
			 */
		/*	function pulldownRefresh() {
				setTimeout(function() {
					var table = document.body.querySelector('.mui-table-view');
					var cells = document.body.querySelectorAll('.mui-table-view-cell');
					for (var i = cells.length, len = i + 3; i < len; i++) {
						var li = document.createElement('li');
						li.className = 'mui-table-view-cell ';
						li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';
						//下拉刷新，新纪录插到最前面；
						table.insertBefore(li, table.firstChild);
					}
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
				}, 1500);
			}*/

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

			/**
			 * 上拉加载具体业务实现
			  	刚进入这个页面的时候肯定会从后台传进来一个标志信息的mark类的字段。
			 */
			var firstreq = 0;
			 function pullupRefresh() {
				 $.ajax({
						type:'POST',
						url:CONTEXT+'sysMessage/queryMessageListTotal',
						data: {"startRow":startRow,"endRow":endRow},
						dataType: 'json',
						error:function(data){
							
						},
						success:function(data){
		     				if (data && data.length>0){
		     					if(firstreq ==0){
		     						//如果是第一次加载，不需要再等1。5秒显示
		     						mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
		     						getMessageData(data);
			     					
		     					}else{
		     						setTimeout(function() {
			     						mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
			     						getMessageData(data);
			     						
			     					}, 1500);
		     					}
								firstreq+=1;
		     					startRow=startRow+endRow;
		     				}else{
		     					if(firstreq==0){
			 						$("body").append('<div style="position: absolute;width:100%;height: 100%;z-index:9;"><div style="top:50%;margin-top: -10px;position: relative; "><a id="icon-chatbubble" style="color: #8F8F94;position: relative;left: 50%;margin-left: -46px; "><span class="mui-icon mui-icon-info"></span><span>暂无消息</span></a></div></div>');
			 						$("#pullrefresh").remove();//不知道为什么不加这句，加载的还会转
		     						return;
		     					}else{
		     						if(data.length<=0){
		     							mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
		     						}
		     					}
		     					
		     				}
		     				
						}
						 
						 
				});	
			
			}
	
			//document.getElementById('body').innderHTML = str;
		/*	var str='<li class="mui-table-view-cell pr-15p">
						<div class="mui-table">
							<div class="mui-row">
								<div class="mui-table-cell mui-col-xs-8">
									<h4 class="mui-ellipsis">
										<img src="images/ydot.png" class="yellow-dot mui-hidden">
										<span class="viewed-font fontsize-16p" >2016春节放假安排</span>
									</h4>
								</div>
								<div class="mui-table-cell mui-col-xs-4 mui-text-right mt-3p" >
									<span class="mui-h5 viewed-font">2016-06-12</span>
								</div>
							</div>
							<div class="mui-row">
								<div class="mui-table-cell mui-col-xs-12 mtb-05r">
									<p class="content-line-2 viewed-font">
										2016年2月7日至2016年2月13日调休3333，放假7日。2月7日星期日，农历除夕)、2月8日(星期一，农历正月……  2）关于
									</p>           				
								</div>					                    	
							</div>
						</div>
					</li>';*/

		//注册li的跳转点击事件：
		function liJump(){
			

			mui(".mui-table-view").on('tap','.mui-table-view-cell',function(){
				var messageID= this.getAttribute("dataId");
			  mui.openWindow({
				/*id:'detail',*/
				url:CONTEXT+'sysMessage/viewMessageDetail?messageID='+messageID
			  });
			})
		}
		liJump();

		</script>
	</body>
</html>