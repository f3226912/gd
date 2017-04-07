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
			<td> 剩余抽取次数:</td>
			<td>${dto.joinTimesLeft }</td>
		</tr>
		<tr>
			<td> 所获积分:</td>
			<td>${dto.score }</td>
		</tr>
		<tr>
			<td> 积分获取时间:</td>
			<td><fmt:formatDate value='${dto.createTime }' pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td> 创建人:</td>
			<td>${dto.createUserName }</td>
		</tr>
		<tr>
			<td> 修改时间:</td>
			<td><fmt:formatDate value='${dto.updateTime }' pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td> 修改人:</td>
			<td>${dto.updateUserName }</td>
		</tr>
	</table>
</div>