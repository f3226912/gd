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
		<link rel="stylesheet" type="text/css" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>
	</head>

	<body>
	
		<header class="mui-bar mui-bar-nav bgcolor-3aa">
			<a class="mui-icon mui-icon-arrowleft mui-pull-left top-left-right "  href="${CONTEXT}sysMessage/queryMessageList">返回</a>
			<a id="delect" class="mui-icon mui-pull-right top-left-right">删除</a>
			<h1 class="mui-title font-col-white top-middle" >消息详情</h1>
		</header>
		
		<div class="mui-content" style="margin-bottom:4.95rem;">
			<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed" style="margin:0;">
				<li class="mui-table-view-cell">
					<div class="mui-table">
						<div class="mui-row">
							<div class="mui-table-cell mui-col-xs-8">
								<h4 class="mui-ellipsis" style="padding-top:3px;padding-bottom:3px;">${sysmessageDTO.title }</h4>
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
			   
			</ul>
		</div>
		<nav class="mui-bar mui-bar-tab">
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
		</nav>
	</body>
	<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
	<script src="${CONTEXT }js/common.js"></script>
	<script>
		getUnReadData('${systemLoginUser.userID }',$("#unReadData"));
		document.getElementById("delect").addEventListener('tap', function() {
				var btnArray = ['否', '是'];
				mui.confirm('', '确定要删除消息吗？', btnArray, function(e) {
					if (e.index == 1) {
						//确定删除后的动作
						 //执行动作
						 var url=CONTEXT+"sysMessage/deleteMessage";
	        			jQuery.post(url,{"messageID":"${sysmessageDTO.id}"},function(data){
	        				if (data == "success"){
								mui.openWindow({
										/*id:'detail',*/
										url:CONTEXT+'sysMessage/queryMessageList'
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
	
		</script>
</html>