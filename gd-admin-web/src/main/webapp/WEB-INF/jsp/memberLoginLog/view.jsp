<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<input type="hidden" id="id" name="id" value="${dto.id}" />
<div>
	<table style="border: none;width: 100%;">
		<%-- <tr>
			<td>id<span style="color: red">*</span></td>
			<td><input type="text" id="id" name="id" value="${dto.id}" readonly="readonly" style="width: 90%;"></td>
		</tr> --%>
		<tr>
			<td>用户Id<span style="color: red">*</span></td>
			<td><input type="text" id="memberId" name="memberId" value="${dto.memberId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>账号<span style="color: red">*</span></td>
			<td><input type="text" id="account" name="account" value="${dto.account}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>手机号<span style="color: red">*</span></td>
			<td><input type="text" id="mobile" name="mobile" value="${dto.mobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>姓名<span style="color: red">*</span></td>
			<td><input type="text" id="realName" name="realName" value="${dto.realName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>登录时间<span style="color: red">*</span></td>
			<td><input type="text" id="createTime" name="createTime"
			 value='<fmt:formatDate value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />'
			 readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>登录来源<span style="color: red">*</span></td>
			<td><input type="text" id="loginType" name="loginType" value="${dto.loginType}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>ip<span style="color: red">*</span></td>
			<td><input type="text" id="ip" name="ip" value="${dto.ip}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>说明<span style="color: red">*</span></td>
			<td><input type="text" id="description" name="description" value="${dto.description}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
