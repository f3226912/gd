<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="grdProSupplyofgoodHandout/save">
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>marketId<span style="color: red">*</span></td>
				<td><input type="text" id="marketId" name="marketId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="marketId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>teamName<span style="color: red">*</span></td>
				<td><input type="text" id="teamName" name="teamName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="teamName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>goodsId<span style="color: red">*</span></td>
				<td><input type="text" id="goodsId" name="goodsId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="goodsId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateTime<span style="color: red">*</span></td>
				<td><input type="text" id="updateTime" name="updateTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>status<span style="color: red">*</span></td>
				<td><input type="text" id="status" name="status"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="status必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdMobile<span style="color: red">*</span></td>
				<td><input type="text" id="grdMobile" name="grdMobile"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdMobile必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdUserName<span style="color: red">*</span></td>
				<td><input type="text" id="grdUserName" name="grdUserName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdUserName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>teamId<span style="color: red">*</span></td>
				<td><input type="text" id="teamId" name="teamId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="teamId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>grdId<span style="color: red">*</span></td>
				<td><input type="text" id="grdId" name="grdId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="grdId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>publisher<span style="color: red">*</span></td>
				<td><input type="text" id="publisher" name="publisher"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="publisher必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>id<span style="color: red">*</span></td>
				<td><input type="text" id="id" name="id"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="id必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>updateUserId<span style="color: red">*</span></td>
				<td><input type="text" id="updateUserId" name="updateUserId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="updateUserId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>marketName<span style="color: red">*</span></td>
				<td><input type="text" id="marketName" name="marketName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="marketName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>publisherTime<span style="color: red">*</span></td>
				<td><input type="text" id="publisherTime" name="publisherTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="publisherTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>mobile<span style="color: red">*</span></td>
				<td><input type="text" id="mobile" name="mobile"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="mobile必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>