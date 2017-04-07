<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<script type="text/javascript">

	function updatePwdInit() {
		var userID=$("#userID").val();
		var url=CONTEXT+'sysRegisterUser/updateUserPWDInit?userID=' + userID;
		window.location.href=url;
		}
	</script>
<html>
	<head>
	<base href="${CONTEXT}" >
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="format-detection" content="telephone=no" />
		<title>我的账户</title>
		<link href="${CONTEXT }v1.0/module/mui/examples/login/css/mui.min.css" rel="stylesheet" type="text/css" />
		<link href="${CONTEXT }v1.0/module/mui/examples/login/css/style.css" rel="stylesheet" type="text/css"  />
		<link href="${CONTEXT }v1.0/css/login.css" rel="stylesheet" type="text/css"/>
		<script src="${CONTEXT }js/common.js"></script>
	</head>

	<body style="background-color:#f5f5f9;">
		<input type="hidden" id="userID" name="userID" value="${systemUserId }">
		<div class="mui-account">
				<div class="mui-account-input-user-name"><i class="mui-input-label-icon-1"></i>
					<span class="mui-sz-user-sp">用户名</span>
					<span class="mui-account-sp">${user.userCode }</span>
				</div>
				<div class="mui-name-box">
					<div class="mui-account-input-user"><i class="mui-input-label-icon-1 mui-input-label-icon-2"></i>
					<span class="mui-sz-user-sp">姓名</span>
						<span class="mui-account-sp">${user.userName }</span>
					</div>
					<div class="bottom-lines"></div>
					<div class="mui-account-input-user"><i class="mui-input-label-icon-1 mui-input-label-icon-3"></i>
					<span class="mui-sz-user-sp">职位</span>
						<span class="mui-account-sp">${user.orgUnitId }</span>
					</div>
					<div class="bottom-lines"></div>
					<div class="mui-account-input-user">
						<i class="mui-input-label-icon-1 mui-input-label-icon-4"></i>
						<span class="mui-sz-user-sp">联系方式</span>
						<span class="mui-account-sp">${user.mobile }</span>
					</div>
				</div>
				<div class="mui-account-input-password">
					<i class="mui-input-label-icon-1 mui-input-label-icon-5"></i>
					<a href="sysRegisterUser/updateUserPWDInit?userID=${systemUserId }" target="_top" class="mui-input-account-clear"/>密码设置</a>		
				</div>

		</div>
	
	</body>

</html>