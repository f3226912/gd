<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="grdProCallstatistics/save">
<input type="hidden" id="createUserId" name="createUserId" value="${dto.createUserId}" />
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>marketId<span style="color: red">*</span></td>
				<td><input type="text" id="marketId" name="marketId" value="${dto.marketId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="marketId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateTime<span style="color: red">*</span></td>
				<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>callTime<span style="color: red">*</span></td>
				<td><input type="text" id="callTime" name="callTime" value="${dto.callTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="callTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdMobile<span style="color: red">*</span></td>
				<td><input type="text" id="grdMobile" name="grdMobile" value="${dto.grdMobile}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdMobile必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdUserName<span style="color: red">*</span></td>
				<td><input type="text" id="grdUserName" name="grdUserName" value="${dto.grdUserName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdUserName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>serviceType<span style="color: red">*</span></td>
				<td><input type="text" id="serviceType" name="serviceType" value="${dto.serviceType}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="serviceType必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdId<span style="color: red">*</span></td>
				<td><input type="text" id="grdId" name="grdId" value="${dto.grdId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>id<span style="color: red">*</span></td>
				<td><input type="text" id="id" name="id" value="${dto.id}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="id必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateUserId<span style="color: red">*</span></td>
				<td><input type="text" id="updateUserId" name="updateUserId" value="${dto.updateUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateUserId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>marketName<span style="color: red">*</span></td>
				<td><input type="text" id="marketName" name="marketName" value="${dto.marketName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="marketName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>source<span style="color: red">*</span></td>
				<td><input type="text" id="source" name="source" value="${dto.source}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="source必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>s_Mobile<span style="color: red">*</span></td>
				<td><input type="text" id="s_Mobile" name="s_Mobile" value="${dto.s_Mobile}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="s_Mobile必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>callRole<span style="color: red">*</span></td>
				<td><input type="text" id="callRole" name="callRole" value="${dto.callRole}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="callRole必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>s_Name<span style="color: red">*</span></td>
				<td><input type="text" id="s_Name" name="s_Name" value="${dto.s_Name}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="s_Name必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>e_Mobile<span style="color: red">*</span></td>
				<td><input type="text" id="e_Mobile" name="e_Mobile" value="${dto.e_Mobile}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="e_Mobile必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>e_Name<span style="color: red">*</span></td>
				<td><input type="text" id="e_Name" name="e_Name" value="${dto.e_Name}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="e_Name必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>