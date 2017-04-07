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
			<td>goodsId<span style="color: red">*</span></td>
			<td><input type="text" id="goodsId" name="goodsId" value="${dto.goodsId}" readonly="readonly" style="width: 90%;"></td>
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
			<td>publisher<span style="color: red">*</span></td>
			<td><input type="text" id="publisher" name="publisher" value="${dto.publisher}" readonly="readonly" style="width: 90%;"></td>
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
			<td>publisherTime<span style="color: red">*</span></td>
			<td><input type="text" id="publisherTime" name="publisherTime" value="${dto.publisherTime}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>createUserId<span style="color: red">*</span></td>
			<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>mobile<span style="color: red">*</span></td>
			<td><input type="text" id="mobile" name="mobile" value="${dto.mobile}" readonly="readonly" style="width: 90%;"></td>
		</tr>
	</table>
</div>
