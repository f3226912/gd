<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<div>
	<form id="addForm" method="post" action="userIntegralChange/save">
		<table>
			<tr height="25px">
				<td style="width: 100px;"><span style="color: red;">*</span>用户:</td>
				<td>
					<input type="text" id="account1" style="width: 150px" readonly="readonly"/>
					<input type="hidden" id="memberId" />
					<input type="hidden" id="mobile" />
					<input type="button" value="选择用户" id="selectMember"/>
				</td>
			</tr>
			<tr height="32px">
				<td style="width: 100px;"><span style="color: red;">*</span>活动编号:</td>
				<td>
					<input type="text" id="code" name="code" maxlength="50" required="true" class="easyui-validatebox"
					missingMessage="活动编号必须填写" style="width: 150px">
				</td>
			</tr>
			<tr height="32px">
				<td style="width: 100px;"><span style="color: red;">*</span>变更类型:</td>
				<td>
					<select id="integralType" name="integralType" style="width: 150px">
						<option value="1">增加积分</option>
						<option value="2">扣减积分</option>
					</select>
				</td>
			</tr>
			<tr height="32px">
				<td class="left"><span style="color: red;">*</span>积分值:</td>
				<td>
					<input name="integral" id="integral" required="true" class="easyui-validatebox" data-options="required:true,validType:['num','range[1,99999]']"
					style="width: 174px;">
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/userIntegral/add.js"></script>