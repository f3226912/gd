<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="certifCompany/save">
<input type="hidden" id="id" name="id" value="${dto.id}" />
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>status<span style="color: red">*</span></td>
				<td><input type="text" id="status" name="status" value="${dto.status}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="status必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateTime<span style="color: red">*</span></td>
				<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>companyName<span style="color: red">*</span></td>
				<td><input type="text" id="companyName" name="companyName" value="${dto.companyName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="companyName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>optionUser<span style="color: red">*</span></td>
				<td><input type="text" id="optionUser" name="optionUser" value="${dto.optionUser}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="optionUser必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>bzl<span style="color: red">*</span></td>
				<td><input type="text" id="bzl" name="bzl" value="${dto.bzl}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="bzl必填" style="width: 90%;"></td>
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
				<td>commitTime<span style="color: red">*</span></td>
				<td><input type="text" id="commitTime" name="commitTime" value="${dto.commitTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="commitTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>account<span style="color: red">*</span></td>
				<td><input type="text" id="account" name="account" value="${dto.account}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="account必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>memberId<span style="color: red">*</span></td>
				<td><input type="text" id="memberId" name="memberId" value="${dto.memberId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="memberId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>appType<span style="color: red">*</span></td>
				<td><input type="text" id="appType" name="appType" value="${dto.appType}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="appType必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>bzlPhotoUrl<span style="color: red">*</span></td>
				<td><input type="text" id="bzlPhotoUrl" name="bzlPhotoUrl" value="${dto.bzlPhotoUrl}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="bzlPhotoUrl必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>