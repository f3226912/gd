<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>消息详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	
		<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>
		<style>
		.mui-bar-nav,.bgcolor-bag{background:#3bb650}	
		.mui-img{ max-width:100%; height:auto; margin:0px 5px 0 0; position: relative; top: 3px;}
		img{ max-width:100%; height:auto;}
		.linehei-23p{ word-wrap: break-word; word-break: break-all;}
		.mui-row > [class*='mui-col-']{float:none; }
		.mui-row-list{ border-bottom:solid 1px #ccc;}
		.mui-col-xs-8{ width: 100%; display: block;}
		.mui-col-xs-4{ width: 100%;}
		.mt-3p{ padding-left: 15px;}
		.ptb-1r{padding-bottom:0}
		.mui-popup-inner{padding: 30px 15px; border-radius: 0px;}
		.mui-popup-button{ color:rgb(240,163,0); font-family:'Microsoft YaHei';}
		.mui-popup-title, .mui-popup-title + .mui-popup-text{font-family:'Microsoft YaHei'; font-size: 15px;}
		.mui-popup{border-radius: 5px;}
		.mui-popup-button.mui-popup-button-bold{color:#999; font-weight:normal;}
		.mui-popup-button:first-child{border-radius: 0 0 0 5px;}
		.mui-popup-button:last-child{border-radius: 0 0 5px 0px;}
		
		.goback-txt{font-size:1.65rem;font-weight: normal;}
		</style>
	</head>

	<body>
	
		<header class="mui-bar mui-bar-nav bgcolor-bag header-display" style="display:none">
			<%-- <a class="mui-icon mui-pull-left top-left-right "  href="${CONTEXT}sysMessage/queryMessageList?userID=${sysmessageDTO.userID}&client=${sysmessageDTO.client}&m=${displayFlag}"><img class="mui-img" src="${CONTEXT}v1.0/images/icon_common_back.png"/>返回</a> --%>
			<a class="mui-icon mui-icon-left-nav mui-pull-left"  href="${CONTEXT}sysMessage/queryMessageList?userID=${sysmessageDTO.userID}&client=${sysmessageDTO.client}&m=${displayFlag}"><span class="goback-txt">返回</span></a>
			<a id="delect" class="mui-icon mui-pull-right top-left-right">删除</a>
			<h1 class="mui-title font-col-white top-middle" >消息详情</h1>
			
		</header>
		
		<div class="mui-content" style="margin-bottom:4.95rem;">
			<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed" style="margin:0;">
				<li class="mui-table-view-cell">
					<div class="mui-table">
						<div class="mui-row mui-row-list">
							<div class="mui-table-cell mui-col-xs-8">
								<h4 style="padding-top:3px;padding-bottom:3px; line-height:24px">${sysmessageDTO.title }</h4>
							</div>
							<div class="mui-table-cell mui-col-xs-4 mui-text-right" >
								<span class="mui-h5"><fmt:formatDate value="${sysmessageDTO.createTime }" pattern="yyyy-MM-dd"/></span>
							</div>
						</div>
						<div class="mui-row">
							<div class="mui-table-cell mui-col-xs-12">
								<p class="ptb-1r linehei-23p">
								 ${sysmessageDTO.content }
								 </p>	           				
							</div>					                    	
						</div>
					</div>
				</li>
			   <input type="hidden" id="userID" name="userID" value="${sysmessageDTO.userID}">
			   <input type="hidden" id="client" name="client" value="${sysmessageDTO.client}">
			   <input type="hidden" id="displayFlag" value="${displayFlag}">
			   
			</ul>
		</div>
		<!--<nav class="mui-bar mui-bar-tab">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="tab-webview-subpage-chat.html">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge" style="display: none;" id="unReadData"></span></span>
				<span class="mui-tab-label" style="color: #007aff;">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn" href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label">我的</span>
			</a>
		</nav>-->
	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
	<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
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
		//getUnReadData('${systemLoginUser.userID }',$("#unReadData"));
		var userID= $("#userID").val();
		var client= $("#client").val();
		document.getElementById("delect").addEventListener('tap', function() {
				var btnArray = ['取消', '确认'];
				mui.confirm('', '确认删除该消息？', btnArray, function(e) {
					if (e.index == 1) {
						//确定删除后的动作
						 //执行动作
						 var url=CONTEXT+"sysMessage/deleteMessage";
	        			jQuery.post(url,{"id":"${sysmessageDTO.id}","userID":userID},function(data){
	        				if (data == "success"){
								mui.openWindow({
										/*id:'detail',*/
										url:CONTEXT+'sysMessage/queryMessageList?userID='+userID+'&client='+client+'&m='+displayFlag
									  });
								
	        					
	        				}else{
	        					mui.alert('', "操作失败!", function() {
									return;
								});
	        				}
	            		});	
					} else {
						//
						
					}
				})
			});
		
		function messageGoBack(){
			mui.openWindow({
				/*id:'detail',*/
				url:CONTEXT+'sysMessage/queryMessageList?userID='+userID+'&client='+client+'&m='+displayFlag
			  });
		
			}
	
		</script>
</html>