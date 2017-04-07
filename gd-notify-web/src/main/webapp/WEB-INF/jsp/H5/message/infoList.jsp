<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>消息中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

			<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css"  href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>	
		<style>
		.mui-bar-nav,.bgcolor-bag{background:#3bb650}	
		.mui-img{ max-width:100%; height:auto; margin:0px 5px 0 0; position: relative; top: 3px;}
		h4.mui-ellipsis{margin-top:3px; line-height:22px; height:22px;}
		span.fontsize-16p{padding-left:5px; }
		.mui-popup-inner{padding: 30px 15px; border-radius: 0px;}
		.mui-popup-title{ display:none;}
		.mui-popup-button{ color:rgb(240,163,0); font-family:'Microsoft YaHei';}
		.mui-popup-title + .mui-popup-text{font-family:'Microsoft YaHei'; font-size: 15px;}
		.mui-popup{border-radius: 5px;}
		.mui-popup-button.mui-popup-button-bold{color:#999; font-weight:normal;}
		.mui-popup-button:first-child{border-radius: 0 0 0 5px;}
		.mui-popup-button:last-child{border-radius: 0 0 5px 0px;}
		
		.goback-txt{font-size:1.65rem;font-weight: normal;}
		</style>
	</head>
<body>
<input type="hidden" id="userID" name="userID" value="${pushNoticeDTO.userID}">
<input type="hidden" id="client" name="client" value="${pushNoticeDTO.client}">
<input type="hidden" id="displayFlag" value="${displayFlag}">
		<header class="mui-bar mui-bar-nav bgcolor-bag header-display" style="display:none">
			<%-- <a class="mui-icon mui-pull-left top-left-right" href="#" onclick="messageListBackClick()"><img class="mui-img" src="${CONTEXT}v1.0/images/icon_common_back.png"/>返回</a> --%>
			<a class="mui-icon mui-icon-left-nav mui-pull-left" onclick="messageListBackClick()"><span class="goback-txt">返回</span></a>
			<a id="info" class="mui-icon mui-pull-right top-left-right">
				<!-- <span class="font-col-orange">
				3
				</span><span>条未读</span>-->
			</a>
			<h1 class="mui-title font-col-white top-middle">消息中心</h1>
		</header>
		<!--<nav class="mui-bar mui-bar-tab">
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
		</nav>-->
		<!--下拉刷新容器-->
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view mui-table-view-chevron msg-list">
				
				</ul>
			</div>
		</div>
		
		<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.js"></script>
		<!-- <script src="${CONTEXT }v1.0/js/jquery.dotdotdot.min.js"></script> -->
		<script src="${CONTEXT }js/common.js"></script>
		<script>
		
		//$('.header-display').css('display','none');
		$('.mui-bar-nav ~ .mui-content').css('padding-top','0');
		var displayFlag = $("#displayFlag").val();
	
		//是否隐藏消息列头的标识,如果有传此参数，且参数值不为空时，需要隐藏此消息列表，如果没有传或者为空，则不需要隐藏消息列头
		if(!displayFlag){
			$('.header-display').css('display','block');
			 $('.mui-bar-nav ~ .mui-content').css('padding-top','4.4rem');
		}
		//从第几条开始
		/* $(function(){
			
		}) */
		var startRow=0;
		//每页面显示 多少条
		var endRow=10;
		var userID= $("#userID").val();
		var client= $("#client").val();
		//消息列表页面加上返回，返回为空方法
		function messageListBackClick(){
			 window.FinishPageFunc.finishCurrentPage();
		}

		
		function getUnReadData(returnSpanData){
			$.ajax({
				type: "POST",
				url: CONTEXT+"sysMessage/queryUnreadTotal",
				data:{"userID":userID,"client":client},
				dataType: "json",
				success: function(data) {
					if(data && data>0){
						returnSpanData.text(data);
						returnSpanData.show();
					}else{
						returnSpanData.hide();
					}
				}
			});
		}

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
				li.setAttribute("isreadFlag",data[i].isread);
				li.className = 'mui-table-view-cell pr-15p mui-transitioning';
			
				var strContent=dealContent(data[i].content);
			
				//if(strContent.length > 39) {
					// strContent = strContent.substring(0,39)+"...";
				 //}
				
				if(data[i].isread =="1"){
					titleSpan='<img src="${CONTEXT}v1.0/module/mui/examples/hello-mui/images/ydot.png" class="yellow-dot">'+
						'<span class="fontsize-16p" >'+data[i].title+'</span>';
					timeSpan='<span class="mui-h5">'+
					data[i].createTime.substring(0,10)+
						'</span>';
					contentSpan='<p class="content-line-2 mui-ellipsis-2"> '+strContent+'</p> ';
				}else{
					titleSpan='<img src="${CONTEXT}v1.0/module/mui/examples/hello-mui/images/ydot.png" class="yellow-dot mui-hidden">'+
						'<span class="viewed-font fontsize-16p" >'+data[i].title+'</span>';
					timeSpan='<span class="mui-h5 viewed-font">'+
					data[i].createTime.substring(0,10)+
						'</span>';
					contentSpan='<p class="content-line-2 viewed-font mui-ellipsis-2">'+strContent+'</p> ';
				}
				li.innerHTML='<div class="mui-slider-right mui-disabled">'+
						'<a class="mui-btn mui-btn-red">删除</a>'+
					'</div><div class="mui-table mui-slider-handle">'+
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
			/*$('.content-style').each(function(){
				$(this).dotdotdot();
			}); */
		}
		//处理content在列表中的显示
		var dealContent =function(str)
        {
            var result;
            //替换所有的HTML标签
            result=str.replace(/<[^>]+>/g,""); 
            //去除行尾空白
            result=result.replace(/[ | ]*\n/g,'\n');
            //替换多余的空行
            result=result.replace(/\n[\s| | ]*\r/g,'\n');
            //去除&nbsp;
            result=result.replace(/&nbsp;/ig, "");
            //去除&lt;
            result=result.replace(/&lt;/ig, "");
          	//去除&gt;
            result=result.replace(/&gt;/ig, "");
            
            //去除前后空格
            result =result.replace(/(^\s+)|(\s+$)/g,"");
            //去除所有空格
            result = result.replace(/\s/g,"");
        
            return result;
	};
		//getUnReadData($("#unReadData"));
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
						data: {"startRow":startRow,"endRow":endRow,"userID":userID,"client":client},
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
			 						$("body").append('<div style="position: absolute;width:100%;height: 100%;z-index:9;"><div style="top:50%;margin-top: -10px;position: relative; "><a id="icon-chatbubble" style="color: #8F8F94;"><span class="mui-icon mui-icon-info" style="position: absolute;top: -50px;left: 50%; margin-left: -23px; font-size: 46px;"></span><span style="width: 100%; text-align: center; position: absolute;font-size: 20px;">暂无消息</span></a></div></div>');
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
				var noticeID= this.getAttribute("dataId");
				var isreadFlag = this.getAttribute("isreadFlag");
				//如果当前记录为未读,跳转到详情页面之前先将此条记录变灰，设置为已读
				 if(isreadFlag=="1"){
				  $(this).find(".yellow-dot").addClass("mui-hidden");
				  $(this).find(".fontsize-16p").addClass("viewed-font");
				  $(this).find(".mui-h5").addClass("viewed-font");
				  $(this).find(".content-line-2").addClass("viewed-font");
				  this.setAttribute("isreadFlag","2");
				 }
			  mui.openWindow({
				/*id:'detail',*/
				url:CONTEXT+'sysMessage/viewMessageDetail?id='+noticeID+'&isread='+isreadFlag+'&userID='+userID+'&client='+client+'&m='+displayFlag
			 });
			});
		}
		liJump();
		
		
		//第一个demo，拖拽后显示操作图标，点击操作图标删除元素；
				$('.msg-list').on('tap', '.mui-btn', function(event) {
					var elem = this;
					var li = elem.parentNode.parentNode;
					var noticeID= li.getAttribute("dataId");
					var isreadFlag = li.getAttribute("isreadFlag");
					
					mui.confirm('确认删除该消息？', '提示', btnArray, function(e) {
						if (e.index == 1) {
							li.parentNode.removeChild(li);
							//确定删除后的动作
							 //执行动作
							 var url=CONTEXT+"sysMessage/deleteMessage";
		        			jQuery.post(url,{"id":noticeID,"userID":userID,"isread":isreadFlag},function(data){
		        				if (data == "success"){
									 var refreshUrl=CONTEXT+"sysMessage/queryMessageList";
				        			jQuery.post(refreshUrl,{"userID":userID,"client":client,"m":displayFlag},function(data){
				        				
				        			});
		        					
		        				}else{
		        					mui.alert('', "操作失败!", function() {
										return;
									});
		        				}
		            		});	
							
						} else {
							setTimeout(function() {
								$.swipeoutClose(li);
							}, 0);
						}
					});
				});
				var btnArray = ['取消', '确认'];
				
				
		</script>
	</body>
</html>
