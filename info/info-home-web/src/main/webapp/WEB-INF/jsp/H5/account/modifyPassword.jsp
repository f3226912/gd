<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>我的账户</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		
	
		<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/app.css"/>
		<style>
			.mui-input-group .mui-input-row{
			   margin-top:0;		  
			}
			.mui-input-row label{
				font-family: "Microsoft YaHei";
				font-size: 1.5rem;
				color: #888;
			}
		</style>	
	</head>

	<body>
	<!-- 	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">input（输入框）</h1>
	</header> -->
	<header class="mui-bar mui-bar-nav bgcolor-3aa">
			<a class="mui-icon mui-icon-arrowleft mui-pull-left top-left-right " href="javascript:history.go(-1);">返回</a>
			<a id="finish" class="mui-icon mui-pull-right top-left-right">完成</a>
			<h1 class="mui-title font-col-white top-middle">密码设置</h1>
			<!-- <h1 class="mui-title">修改密码</h1> -->
	</header>
		<div class="mui-content">
			<div class="mui-content-padded" style="margin:0;">
				 
				<form class="mui-input-group" id="pwdEditFrm" style="margin:0;"> 
				<input type="hidden" id="userID" name="userID" value="${systemUserId }">
					<div class="mui-input-row">
						<label>原密码</label>
						<input id="oldPassword" type="password" placeholder="">
					</div>
					<div class="mui-input-row">
						<label>新密码</label>
						<input id="newPassword" type="password" placeholder="">
					</div>
					<div class="mui-input-row">
						<label>确认密码</label>
						<input id="comfirmPassword" type="password" placeholder="">
					</div>
				</form>
			</div>
		</div>
			<nav class="mui-bar mui-bar-tab">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="tab-webview-subpage-chat.html">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge" style="display: none;"></span></span>
				<span class="mui-tab-label">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn" href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label" style="color: #007aff;">我的</span>
			</a>
		</nav>
		<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
		<script src="${CONTEXT }js/common.js"></script>
		<script>
			//去掉空格的方法
			function trim(str){
				return str.replace(/(^\s*)|(\s*$)/g, ""); 
			}

			//获取输入框信息
			function submitPass(){
				var passwordObj={};
				passwordObj.oldPassword=document.getElementById("oldPassword").value;
				passwordObj.newPassword=document.getElementById("newPassword").value;
				passwordObj.confirmPassword=document.getElementById("comfirmPassword").value;
				return passwordObj;				
			}

			//密码验证0未通过验证，1是通过验证
			function verifyValue(){
				var oldPassword=trim(document.getElementById("oldPassword").value);
				var newPassword=trim(document.getElementById("newPassword").value);
				var length=newPassword.length;
				var confirmPassword=trim(document.getElementById("comfirmPassword").value);
				if(oldPassword.length==0){
					return "原密码不能为空";
				}else if(length==0){
					return "新密码不能为空";
				}else if(length<6||length>20){
					return "请输入6~20个字符的密码";
				}else if(length>=6&&/^[0-9]+$/g.test(newPassword)){
					return "密码不能为纯数字";
				}else if(length>=6&&/^[A-Z]+$/g.test(newPassword)){
					return "密码不能为纯大写字母";
				}else if(length>=6&&/^[a-z]+$/g.test(newPassword)){
					return "密码不能为纯小写字母";
				}else if(length>=6&&/^\W+$/g.test(newPassword)){
					return "密码不能为纯符号";
				}else if(length>=6&&/^\s+$/g.test(newPassword)){
					return "密码不能包含空格";
				}else if(confirmPassword.length==0){			
					return "确认密码不能为空";
				}else if (newPassword!=confirmPassword) {	
					return "两次密码输入不一致";	
				}else{
				}
				return 1;
			}
		
			//立即执行注册事件,ajax提交表单
			( function(){
				
				var con=document.getElementById("finish");
				var alertValue=null;
				con.addEventListener("tap",function(){
					alertValue=verifyValue();
					if(alertValue!="1"){ //验证未通过
						mui.alert('', alertValue, function() {
							//返回后动作
						});							
					}else{
						//var passordObj=submitPass();//获取输入框对象；
						var oldPassword=trim(document.getElementById("oldPassword").value);
						var newPassword=trim(document.getElementById("newPassword").value);
						//var userID=trim($S("#userID").val());
						var userID="${systemLoginUser.userID }";
						$("#userId").val(userID);
						var url="${CONTEXT}sysRegisterUser/updateUserPwd";
						//var passordObj=submitPass();//获取输入框对象；
						
				    	$.ajax(url,{
							data:{
								oldPWD:oldPassword,
								newPWD:newPassword,
								userID:userID
							},
							dataType:'text',//服务器返回json格式数据
							type:'post',//HTTP请求类型
							timeout:10000,//超时时间设置为10秒；
							success:function(data){
								if(data=="success"){
									//服务器返回响应，根据响应结果，分析是否登录成功；	
									  	mui.alert('', "密码修改成功,请重新登录!", function() {
									  	//返回后动作
									  	  mui.openWindow({
												url:CONTEXT+"H5/sys/loginOut"
											  });
										return;
									
									});
									
								}else{
									mui.alert('', "操作失败!原始密码输入错误！", function() {
									return;
									});
								}

							},
							error:function(xhr,type,errorThrown){
								//异常处理；
								console.log(type);
							}
						});
						
						
					}	    	
				});
			} )();
			getUnReadData('${systemLoginUser.userID }',$(".mui-badge"));
		</script>
	</body>
</html>