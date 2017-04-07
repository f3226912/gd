<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="certifShop/save">
	<div>
		<table style="border: none;width: 100%;">
			<tr>
				<td>marketId<span style="color: red">*</span></td>
				<td><input type="text" id="marketId" name="marketId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="marketId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createTime<span style="color: red">*</span></td>
				<td><input type="text" id="createTime" name="createTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createTime必填" style="width: 90%;"></td>
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
				<td>optionUser<span style="color: red">*</span></td>
				<td><input type="text" id="optionUser" name="optionUser"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="optionUser必填" style="width: 90%;"></td>
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
				<td>contractImg<span style="color: red">*</span></td>
				<td><input type="text" id="contractImg" name="contractImg"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="contractImg必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>operatorName<span style="color: red">*</span></td>
				<td><input type="text" id="operatorName" name="operatorName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="operatorName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>address<span style="color: red">*</span></td>
				<td><input type="text" id="address" name="address"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="address必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>shopName<span style="color: red">*</span></td>
				<td><input type="text" id="shopName" name="shopName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="shopName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>commitTime<span style="color: red">*</span></td>
				<td><input type="text" id="commitTime" name="commitTime"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="commitTime必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>realShopName<span style="color: red">*</span></td>
				<td><input type="text" id="realShopName" name="realShopName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="realShopName必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>account<span style="color: red">*</span></td>
				<td><input type="text" id="account" name="account"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="account必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>posNo<span style="color: red">*</span></td>
				<td><input type="text" id="posNo" name="posNo"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="posNo必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>memberId<span style="color: red">*</span></td>
				<td><input type="text" id="memberId" name="memberId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="memberId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>businessId<span style="color: red">*</span></td>
				<td><input type="text" id="businessId" name="businessId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="businessId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>macNo<span style="color: red">*</span></td>
				<td><input type="text" id="macNo" name="macNo"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="macNo必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>cateId<span style="color: red">*</span></td>
				<td><input type="text" id="cateId" name="cateId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="cateId必填" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>createUserId<span style="color: red">*</span></td>
				<td><input type="text" id="createUserId" name="createUserId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="createUserId必填" style="width: 90%;"></td>
			</tr>
		</table>
	</div>
</form>