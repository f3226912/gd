<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="sensitiveWord/save">
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>敏感词<span style="color: red">*</span></td>
				<td><input type="text" id="name" name="name" value="${dto.name}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>说明<span style="color: red">*</span></td>
				<td>
				<textarea  id="description" name="description"  required="true" class="easyui-validatebox" validType="unnormal" missingMessage="必填" style="width: 90%;">${dto.description}</textarea>
				</td>
			</tr>
		</table>
	</div>
</form>