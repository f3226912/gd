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
		<tr>
			<td>marketId<span style="color: red">*</span></td>
			<td><input type="text" id="marketId" name="marketId" value="${dto.marketId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>teamName<span style="color: red">*</span></td>
			<td><input type="text" id="teamName" name="teamName" value="${dto.teamName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createTime<span style="color: red">*</span></td>
			<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>auditor<span style="color: red">*</span></td>
			<td><input type="text" id="auditor" name="auditor" value="${dto.auditor}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>updateTime<span style="color: red">*</span></td>
			<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>status<span style="color: red">*</span></td>
			<td><input type="text" id="status" name="status" value="${dto.status}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>grdMobile<span style="color: red">*</span></td>
			<td><input type="text" id="grdMobile" name="grdMobile" value="${dto.grdMobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>applyTime<span style="color: red">*</span></td>
			<td><input type="text" id="applyTime" name="applyTime" value="${dto.applyTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>regetypeName<span style="color: red">*</span></td>
			<td><input type="text" id="regetypeName" name="regetypeName" value="${dto.regetypeName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>grdUserName<span style="color: red">*</span></td>
			<td><input type="text" id="grdUserName" name="grdUserName" value="${dto.grdUserName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>teamId<span style="color: red">*</span></td>
			<td><input type="text" id="teamId" name="teamId" value="${dto.teamId}" readonly="readonly" style="width: 90%;"></td>
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
			<td>auditTime<span style="color: red">*</span></td>
			<td><input type="text" id="auditTime" name="auditTime" value="${dto.auditTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>memberMobile<span style="color: red">*</span></td>
			<td><input type="text" id="memberMobile" name="memberMobile" value="${dto.memberMobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>account<span style="color: red">*</span></td>
			<td><input type="text" id="account" name="account" value="${dto.account}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>memberId<span style="color: red">*</span></td>
			<td><input type="text" id="memberId" name="memberId" value="${dto.memberId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>realName<span style="color: red">*</span></td>
			<td><input type="text" id="realName" name="realName" value="${dto.realName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>regetype<span style="color: red">*</span></td>
			<td><input type="text" id="regetype" name="regetype" value="${dto.regetype}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createUserId<span style="color: red">*</span></td>
			<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
