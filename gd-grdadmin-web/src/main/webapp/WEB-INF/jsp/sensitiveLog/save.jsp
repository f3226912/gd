<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="sensitiveLog/save">
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
				<td>words<span style="color: red">*</span></td>
				<td><input type="text" id="words" name="words"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="words必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>type<span style="color: red">*</span></td>
				<td><input type="text" id="type" name="type"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="type必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>