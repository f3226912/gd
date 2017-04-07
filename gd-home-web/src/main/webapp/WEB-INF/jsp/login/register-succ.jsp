<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>		
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css"/>
		<title>注册成功</title>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">欢迎注册</div>
		</div>
	</div>
	<!--head end-->


	<div class="login-wrap clearfix">
		<div class="wrap-1170 login-bbox">	
			<div class="reg-box i-box-shadow clearfix">
				<div class="reg-result-box">
					<input type="hidden" id="BackUrl" name="BackUrl" value="${BackUrl }">
					<h3 class="com-result-t"><span class="result-ico-succ"></span>恭喜您，注册成功！</h3>
					<p>尊敬的“<span>${sessionScope.loginMember.account}</span>” 感谢您注册成为谷登农批网“农批商”会员！<span id="jumpTo">5</span>秒后自动跳转</p>
					<p>您现在可以<a href="#" class="reg-r-link">发布产品</a>或进入<a href="#" class="reg-r-link">会员中心后台</a>，祝您生意兴隆！</p>
				</div>
			</div>

		</div>
		
	</div>

	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
	<script type="text/javascript">
		var BackUrl = $("#BackUrl").val();
		$(document).ready(function(){
			if (BackUrl != "") {
				countDown(5,BackUrl);
			} else {
				countDown(5,CONTEXT+"index");
			}
		});
		function countDown(secs, surl) {
			var jumpTo = document.getElementById('jumpTo');
			jumpTo.innerHTML = secs;
			if (--secs > 0) {
				setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
			} else {
				location.href = surl;
			}
		}
	</script>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>
