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
			<td>id<span style="color: red">*</span></td>
			<td><input type="text" id="id" name="id" value="${dto.id}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createTime<span style="color: red">*</span></td>
			<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>words<span style="color: red">*</span></td>
			<td><input type="text" id="words" name="words" value="${dto.words}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>type<span style="color: red">*</span></td>
			<td><input type="text" id="type" name="type" value="${dto.type}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
