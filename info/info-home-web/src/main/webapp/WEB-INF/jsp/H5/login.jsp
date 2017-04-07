<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String BASE_PATH = "/";
    String path = request.getContextPath();
    String CONTEXT = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
	request.setAttribute("CONTEXT",CONTEXT);
%>
<!DOCTYPE html>
<html class="ui-page-login">
<head>
<base href="${CONTEXT}" >
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>智慧农批</title>
<link href="v1.0/module/mui/examples/login/css/mui.min.css" rel="stylesheet" type="text/css" />
<link href="v1.0/module/mui/examples/login/css/style.css" rel="stylesheet" type="text/css"  />
<link href="v1.0/css/login.css" rel="stylesheet" type="text/css"  />
<style>
	.mui-input-group label {
			 width: 15%;}
	.mui-input-row label~input,
	.mui-input-row label~select,
	.mui-input-row label~textarea { 
		width: 85%;}
</style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
			<!-- <h1 class="mui-title">谷 登 智 能 分 析 系 统</h1>
			<p class="mui-title-p">GD-Business Intelligence</p>
			<i class="mui-titie-line"></i> -->
		</header>
		<div class="mui-content mui-content-login">
			<form id='login-form' action="H5/sys/login" method="post" class="mui-input-group">
				<input type="text" style="display: none;"/>
				<input type="password" style="display: none;"/>
				<p id="login-form-true-false">${error }</p>
				<div class="mui-input-row">
					<label class="mui-input-label">账号</label>
					<input id='account' name="userCode" type="text" class="mui-input-clear mui-input" placeholder="请输入用户名">
				</div>
				<div class="mui-input-row ">
					<label class="mui-input-label mui-input-label-b">密码</label>
					<input id='password' name="userPassword" type="password" class="mui-input-clear mui-input" placeholder="请输入密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
				</div>
			</form>
	<!-- 		<form class="mui-input-group">
				<ul class="mui-table-view mui-table-view-chevron">
					<li class="mui-table-view-cell">
						自动登录
						<div id="autoLogin" class="mui-switch">
							<div class="mui-switch-handle"></div>
						</div>
					</li>
				</ul>
			</form> -->
			<div class="mui-content-padded">
				<button id='login' class="mui-btn mui-btn-block mui-btn-primary">登录</button>
				<!-- <div class="link-area"><a id='reg'>注册账号</a> <span class="spliter">|</span> <a id='forgetPassword'>忘记密码</a>
				</div> -->
			</div>
		</div>
<%-- <script type="text/javascript" src="${CONTEXT }js/jquery.min.js"></script>  --%>
<script type="text/javascript" src="${CONTEXT }js/jquery.cookie.js"></script>
<script src="v1.0/js/dialog.js"></script>
<%-- <script src="${CONTEXT}/v1.0/module/mui/examples/login/js/mui.min.js"></script> --%>
<%-- <script src="${CONTEXT}/v1.0/module/mui/examples/login/js/mui.enterfocus.js"></script> --%>
<%-- <script src="${CONTEXT}/v1.0/module/mui/examples/login/js/app.js"></script> --%>
<script type="text/javascript">
//验证

window.onload = function (){
	var aut = document.getElementById('account');
	var pas = document.getElementById('password');
	var lgn = document.getElementById('login');
	var tfs = document.getElementById('login-form-true-false');
	// var re = /[^\w\u4E00-\u9FA5]/g;
	lgn.onclick = function(){
		if(aut.value==null || aut.value==""){
			tfs.style.display="block";
			tfs.innerHTML="请输入正确账号！"
			return false;
		}

		if(pas.value==null || pas.value==""){
			tfs.style.display="block";
			tfs.innerHTML="请输入正确密码！"
			return false;
		}else{
			tfs.style.display="none";
		}
		$.ajax({
			type:"POST", 
			url : CONTEXT+"H5/sys/validate",
			dataType : "json", 
			data:{
				"userCode":$("#account").val(),
				"userPassword":$("#password").val()
			}, 
			success : function(data){ 
				if (data){
					layer.closeAll();
					tfs.style.display="block";
					tfs.innerHTML=data;
					return false;
				}else{
					tfs.style.display="none";
					document.getElementById("login-form").submit();
					layer.open({
					    type: 2,
					    content: '加载中......'
					});
					$.cookie('h5account',$("#account").val(), {expires:365,path:'/'});
					$.cookie('h5pas',$("#password").val(), {expires:365,path:'/'});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				console.info("error:"+XMLHttpRequest);
				console.info("error:"+textStatus);
				console.info("error:"+errorThrown);
			}
		});
		layer.open({
		    type: 2,
		    content: '加载中......'
		});
	}
}
//cookies中保存的帐号密码
var h5account=$.cookie('h5account');
var h5pas=$.cookie('h5pas');
$("#account").val(h5account);
$("#password").val(h5pas);
</script>
</body>
</html>