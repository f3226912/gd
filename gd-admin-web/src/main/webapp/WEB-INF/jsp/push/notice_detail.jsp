<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<input type="hidden" id="id" name="id" value="${dto.id}" />
	<table>
		<tr>
			<td>消息名称:</td>
			<td>${dto.title}
				</td>
		</tr>
		<tr>
			<td>消息发送对象:</td>
			<td>
			<span type="text" id="detailClient"/>
			
			
		</tr>
		<tr>
			<td>信息内容:</td>
			<td style="width:700px;">
				${dto.content}
			</td>
		</tr>
		<tr>
			<td>发布时间:</td>
			<td>
				${dto.createTimeStr}
			</td>
		</tr>
		<!-- <tr>
			<td>链接地址:</td>
			<td>
				${dto.linkUrl}
			</td>
		</tr> -->
		<tr>
			<td>发送状态:</td>
			<td>
				<c:if test="${dto.state==2 }">待发送</c:if>
				<c:if test="${dto.state==1 }">已发送</c:if>
			</td>
		</tr>
		
	</table>
	<div></div>
	<div></div>
</div>

<script type="text/javascript" >
$(document).ready(function(){
var client = '${dto.client}';

var returnStr ="";
if(client){
	var clients = client.split(",");
	for(var i = 0; i < clients.length; i ++){
		if(clients[i]=="1"){
			returnStr+= ",农批商";
		}
		if(clients[i]=="2"){
			returnStr+= ",供应商";
		}
		if(clients[i]=="3"){
			returnStr+= ",农速通";
		}
		if(clients[i]=="4"){
			returnStr+= ",农商友";
		}
		if(clients[i]=="5"){
			returnStr+= ",谷登农批网";
		}
	
	}
	$("#detailClient").html(returnStr.substring(1, returnStr.length));
}
})
</script>







