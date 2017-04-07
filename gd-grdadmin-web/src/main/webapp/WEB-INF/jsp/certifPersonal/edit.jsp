<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="certifPersonal/save">
<input type="hidden" id="id" name="id" value="${dto.id}" />
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>id<span style="color: red">*</span></td>
				<td><input type="text" id="id" name="id" value="${dto.id}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="id必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateUserId<span style="color: red">*</span></td>
				<td><input type="text" id="updateUserId" name="updateUserId" value="${dto.updateUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateUserId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>icon<span style="color: red">*</span></td>
				<td><input type="text" id="icon" name="icon" value="${dto.icon}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="icon必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>idCard<span style="color: red">*</span></td>
				<td><input type="text" id="idCard" name="idCard" value="${dto.idCard}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="idCard必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateTime<span style="color: red">*</span></td>
				<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>commitTime<span style="color: red">*</span></td>
				<td><input type="text" id="commitTime" name="commitTime" value="${dto.commitTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="commitTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>cardPhotoUrl<span style="color: red">*</span></td>
				<td><input type="text" id="cardPhotoUrl" name="cardPhotoUrl" value="${dto.cardPhotoUrl}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="cardPhotoUrl必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>realName<span style="color: red">*</span></td>
				<td><input type="text" id="realName" name="realName" value="${dto.realName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="realName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>memberId<span style="color: red">*</span></td>
				<td><input type="text" id="memberId" name="memberId" value="${dto.memberId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="memberId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>STATUS<span style="color: red">*</span></td>
				<td><input type="text" id="STATUS" name="STATUS" value="${dto.STATUS}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="STATUS必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>