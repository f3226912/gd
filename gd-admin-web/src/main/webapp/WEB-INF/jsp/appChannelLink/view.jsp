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
			<td>downloadLink<span style="color: red">*</span></td>
			<td><input type="text" id="downloadLink" name="downloadLink" value="${dto.downloadLink}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>updateUserId<span style="color: red">*</span></td>
			<td><input type="text" id="updateUserId" name="updateUserId" value="${dto.updateUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createTime<span style="color: red">*</span></td>
			<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>platform<span style="color: red">*</span></td>
			<td><input type="text" id="platform" name="platform" value="${dto.platform}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>channelName<span style="color: red">*</span></td>
			<td><input type="text" id="channelName" name="channelName" value="${dto.channelName}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>updateTime<span style="color: red">*</span></td>
			<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>state<span style="color: red">*</span></td>
			<td><input type="text" id="state" name="state" value="${dto.state}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>pageLink<span style="color: red">*</span></td>
			<td><input type="text" id="pageLink" name="pageLink" value="${dto.pageLink}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>clientChannel<span style="color: red">*</span></td>
			<td><input type="text" id="clientChannel" name="clientChannel" value="${dto.clientChannel}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createUserId<span style="color: red">*</span></td>
			<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
