<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="certifCorp/save">
<input type="hidden" id="id" name="id" value="${dto.id}" />
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime" value="${dto.createTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateTime<span style="color: red">*</span></td>
				<td><input type="text" id="updateTime" name="updateTime" value="${dto.updateTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>markets<span style="color: red">*</span></td>
				<td><input type="text" id="markets" name="markets" value="${dto.markets}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="markets必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>stockCount<span style="color: red">*</span></td>
				<td><input type="text" id="stockCount" name="stockCount" value="${dto.stockCount}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="stockCount必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>STATUS<span style="color: red">*</span></td>
				<td><input type="text" id="STATUS" name="STATUS" value="${dto.STATUS}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="STATUS必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>bzl<span style="color: red">*</span></td>
				<td><input type="text" id="bzl" name="bzl" value="${dto.bzl}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="bzl必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>units<span style="color: red">*</span></td>
				<td><input type="text" id="units" name="units" value="${dto.units}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="units必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>city<span style="color: red">*</span></td>
				<td><input type="text" id="city" name="city" value="${dto.city}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="city必填" style="width: 90%;"></td>
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
				<td>corpName<span style="color: red">*</span></td>
				<td><input type="text" id="corpName" name="corpName" value="${dto.corpName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="corpName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>area<span style="color: red">*</span></td>
				<td><input type="text" id="area" name="area" value="${dto.area}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="area必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>address<span style="color: red">*</span></td>
				<td><input type="text" id="address" name="address" value="${dto.address}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="address必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>shopName<span style="color: red">*</span></td>
				<td><input type="text" id="shopName" name="shopName" value="${dto.shopName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="shopName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>commitTime<span style="color: red">*</span></td>
				<td><input type="text" id="commitTime" name="commitTime" value="${dto.commitTime}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="commitTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>province<span style="color: red">*</span></td>
				<td><input type="text" id="province" name="province" value="${dto.province}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="province必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>memberId<span style="color: red">*</span></td>
				<td><input type="text" id="memberId" name="memberId" value="${dto.memberId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="memberId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>businessId<span style="color: red">*</span></td>
				<td><input type="text" id="businessId" name="businessId" value="${dto.businessId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="businessId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>cateId<span style="color: red">*</span></td>
				<td><input type="text" id="cateId" name="cateId" value="${dto.cateId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="cateId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId" value="${dto.createUserId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>bzlPhotoUrl<span style="color: red">*</span></td>
				<td><input type="text" id="bzlPhotoUrl" name="bzlPhotoUrl" value="${dto.bzlPhotoUrl}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="bzlPhotoUrl必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>