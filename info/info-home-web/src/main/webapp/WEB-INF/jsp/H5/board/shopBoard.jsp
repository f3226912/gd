<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>工作台</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>
		
		<style type="text/css">
		.b-name{
		font-family: "Microsoft YaHei";
		font-size: 1.5rem;
		color: #888;
		}
		</style>
	</head>

	<body>
		<header class="mui-bar mui-bar-nav bgcolor-3aa z-index">
			<a class="mui-icon mui-icon-arrowleft mui-pull-left top-left-right "  href="${CONTEXT}H5/workbench/main">返回</a>
			<!-- <a id="finish" class="mui-icon mui-pull-right top-left-right">完成</a> -->
			<h1 class="mui-title font-col-white top-middle">
			<c:if test="${type eq '1'}">
				综合看板
			</c:if>
			<c:if test="${type eq '2'}">
				${menuName }
			</c:if>
			</h1>
		</header>
		<div class="mui-content">
			<ul class="mui-table-view" style="margin-top:0;margin-bottom:4.95rem;">

				<li class="mui-table-view-cell" style="background-color:#f5f5f9;">
					<span class="mui-h5 ml-8">可显示项</span>
					<div class="mui-h5 whe-right right-8">
						<p>是否显示</p>					
					</div>
				</li>
				
				<c:choose>
						<c:when test="${ empty result}">
						<c:forEach var="board" items="${resultMap}" >
						<li class="mui-table-view-cell">
							<span class="ml-8 b-name">${board.value.name }</span>
							<c:if test="${type eq '2' }">
							 <c:if test="${board.value.status eq '1'}">
								<div class="mui-switch mui-switch-mini right-8" id="${board.key}_${type}">
									<div class="mui-switch-handle"></div>
								</div>
							  </c:if>
							  <c:if test="${board.value.status eq '0'}">
								<div class="mui-switch mui-switch-mini mui-active right-8" id="${board.key}_${type}">
									<div class="mui-switch-handle"></div>
								</div>
							  </c:if>
							 </c:if>
							 <c:if test="${type eq '1'}">
								 <c:if test="${board.value.status eq '1'}">
									<div class="mui-switch mui-switch-mini right-8" id="${board.key}_${type}">
										<div class="mui-switch-handle"></div>
									</div>
								  </c:if>
								  <c:if test="${board.value.status eq '0'}">
									<div class="mui-switch mui-switch-mini mui-active right-8" id="${board.key}_${type}">
										<div class="mui-switch-handle"></div>
									</div>
								  </c:if>
							 </c:if>
						</li>
						</c:forEach>
						</c:when>
						
						<%-- <c:otherwise>
							${result }
						</c:otherwise> --%>
					</c:choose>
	
			</ul>
		</div>
		<nav class="mui-bar mui-bar-tab z-index">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label" style="color: #007aff;">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="tab-webview-subpage-chat.html">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge" style="display: none;" id="unReadData" ></span></span>
				<span class="mui-tab-label">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn" href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label">我的</span>
			</a>
		</nav>
		<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
		<script src="${CONTEXT }js/common.js"></script>
		<script>
		//
			mui.init({
				swipeBack:true //启用右滑关闭功能
			});
			mui('.mui-content .mui-switch').each(function() { //循环所有toggle
			
				 /*var value=this.classList.contains('mui-active') ? 'true' : 'false';
				 alert(value);*/
				 //改变开关要做的动作在下面
		
				this.addEventListener('toggle', function(event) {
					var boardIDAndType = $(this).attr("id").split('_');
					var boardID = boardIDAndType[0];
					var type = boardIDAndType[1];
					var value=this.classList.contains('mui-active') ? 'true' : 'false';
					var url = "";
					if("1" == type){
						url = (value=='true')?"${CONTEXT}/sysUserBoard/insertUserBoard":"${CONTEXT}/sysUserBoard/deleteUserBoard";		
					}else{
						url = (value=='true')?"${CONTEXT}/sysUserBoard/deleteUserBoard":"${CONTEXT}/sysUserBoard/insertUserBoard";		
					}
				 	$.post(url,{"boardID":boardID,"type":type},function(data){
						 if(data=="success"){
							
						 }else{
							
						 }
					 });
				});
			});
			getUnReadData('${systemLoginUser.userID }',$("#unReadData"));
			$("document").ready(function(){
				if("${result}"){
					$(".mui-content").remove();
					$("body").append('<div style="position: absolute;width:100%;height: 100%;z-index: 9;"><div style="top:50%;margin-top: -10px;position: relative; "><a id="icon-chatbubble" style="color: #8F8F94;position: relative;left: 50%;margin-left: -63px; "><span class="mui-icon mui-icon-info"></span><span>暂无看板数据</span></a></div></div>');					
				}
			})
		</script>
	</body>
</html>