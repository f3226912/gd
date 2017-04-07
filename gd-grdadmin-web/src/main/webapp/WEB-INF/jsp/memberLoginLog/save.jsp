<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="memberLoginLog/save">
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>id<span style="color: red">*</span></td>
				<td><input type="text" id="id" name="id"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="id必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>description<span style="color: red">*</span></td>
				<td><input type="text" id="description" name="description"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="description必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>account<span style="color: red">*</span></td>
				<td><input type="text" id="account" name="account"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="account必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>realName<span style="color: red">*</span></td>
				<td><input type="text" id="realName" name="realName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="realName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>memberId<span style="color: red">*</span></td>
				<td><input type="text" id="memberId" name="memberId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="memberId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>loginType<span style="color: red">*</span></td>
				<td><input type="text" id="loginType" name="loginType"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="loginType必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>ip<span style="color: red">*</span></td>
				<td><input type="text" id="ip" name="ip"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="ip必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>mobile<span style="color: red">*</span></td>
				<td><input type="text" id="mobile" name="mobile"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="mobile必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>