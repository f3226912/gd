<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>短信通道管理</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	
	<div>
			
			<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>
			<table>
				<tr>
					<td class="mleft"  >
						当前使用的通道：
					</td>
					<td class="mright">
						
							<c:if test="${ channel == '1'}">
								北京华兴软通
							</c:if>
<%-- 							<c:if test="${ channel == '2'}"> --%>
<!-- 								深圳朗讯通 -->
<%-- 							</c:if> --%>
							<c:if test="${ channel == '3'}">
								深圳三基时代
							</c:if>
							<c:if test="${ channel == '4'}">
								阿里大鱼
							</c:if>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						请选择需要切换的通道：
					</td>
					<td class="mright">
						<form action="update" id="selectChannelForm">
							<select name="channel" id="selectChannel">
								<option value="1">北京华兴软通</option>
<!-- 								<option value="2">深圳朗讯通</option> -->
								<option value="3">深圳三基时代</option>
								<option value="4">阿里大鱼</option>
							</select>
							<input type="button" value="确定" id="updateBtn">
						</form>
					</td>
				</tr>
		</table>	
		
	<script type="text/javascript">
		$("#selectChannel").find("option[value='${channel}']").attr("selected",true);
		$(document).ready(function(){
			$("#updateBtn").click(function(){
				$("#selectChannelForm").submit();
				return false;
			});
		});
	</script>
</body>


</html>

