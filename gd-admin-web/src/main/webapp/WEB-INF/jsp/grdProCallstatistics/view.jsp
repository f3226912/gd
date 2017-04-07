<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<input type="hidden" id="createUserId" name="createUserId" value="${dto.createUserId}" />
<div>
	<table style="border: none;width: 100%;">
		<tr>
			<td>marketId<span style="color: red">*</span></td>
			<td><input type="text" id="marketId" name="marketId" value="${dto.marketId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createTime<span style="color: red">*</span></td>
			<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>updateTime<span style="color: red">*</span></td>
			<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>callTime<span style="color: red">*</span></td>
			<td><input type="text" id="callTime" name="callTime" value="${dto.callTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>grdMobile<span style="color: red">*</span></td>
			<td><input type="text" id="grdMobile" name="grdMobile" value="${dto.grdMobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>grdUserName<span style="color: red">*</span></td>
			<td><input type="text" id="grdUserName" name="grdUserName" value="${dto.grdUserName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>serviceType<span style="color: red">*</span></td>
			<td><input type="text" id="serviceType" name="serviceType" value="${dto.serviceType}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>grdId<span style="color: red">*</span></td>
			<td><input type="text" id="grdId" name="grdId" value="${dto.grdId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>id<span style="color: red">*</span></td>
			<td><input type="text" id="id" name="id" value="${dto.id}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>updateUserId<span style="color: red">*</span></td>
			<td><input type="text" id="updateUserId" name="updateUserId" value="${dto.updateUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>marketName<span style="color: red">*</span></td>
			<td><input type="text" id="marketName" name="marketName" value="${dto.marketName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>source<span style="color: red">*</span></td>
			<td><input type="text" id="source" name="source" value="${dto.source}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>s_Mobile<span style="color: red">*</span></td>
			<td><input type="text" id="s_Mobile" name="s_Mobile" value="${dto.s_Mobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>callRole<span style="color: red">*</span></td>
			<td><input type="text" id="callRole" name="callRole" value="${dto.callRole}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>s_Name<span style="color: red">*</span></td>
			<td><input type="text" id="s_Name" name="s_Name" value="${dto.s_Name}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>e_Mobile<span style="color: red">*</span></td>
			<td><input type="text" id="e_Mobile" name="e_Mobile" value="${dto.e_Mobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>e_Name<span style="color: red">*</span></td>
			<td><input type="text" id="e_Name" name="e_Name" value="${dto.e_Name}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createUserId<span style="color: red">*</span></td>
			<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
