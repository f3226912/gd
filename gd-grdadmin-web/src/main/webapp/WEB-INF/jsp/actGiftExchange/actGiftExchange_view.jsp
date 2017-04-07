<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="width:300px; height:200px; margin:10px auto">
	<table>
		<tr>
			<td> 用户账户:</td>
			<td>${dto.account }</td>
		</tr>
		<tr>
			<td> 手机号码:</td>
			<td>${dto.mobile }</td>
		</tr>
		<tr>
			<td> 活动名称:</td>
			<td>${dto.activityName }</td>
		</tr>
		<tr>
			<td> 礼品:</td>
			<td>${dto.giftName }</td>
		</tr>
		<tr>
			<td> 礼品发放状态:</td>
			<td>
				<c:choose>
					<c:when test="${dto.status == '1' }">未发</c:when>
					<c:when test="${dto.status == '2' }">已发</c:when>
					<c:when test="${dto.status == '3' }">不予发放</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td> 礼品发放时间:</td>
			<td><fmt:formatDate value='${dto.send_time }' pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</td>
		<tr>
			<td> 创建时间:</td>
			<td><fmt:formatDate value='${dto.createTime }' pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</td>
		<tr>
			<td> 创建人:</td>
			<td>${dto.createUserName }</td>
		</td>
		<tr>
			<td> 修改时间:</td>
			<td><fmt:formatDate value='${dto.updateTime }' pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</td>
		<tr>
			<td> 修改人:</td>
			<td>${dto.updateUserName }</td>
		</td>
	</table>
</div>
