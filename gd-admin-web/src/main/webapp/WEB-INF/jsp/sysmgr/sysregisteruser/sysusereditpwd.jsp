<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	function saveEditPwd() {
		var url="${CONTEXT}sysmgr/sysRegisterUser/updateUserPwd";
		if ($('#pwdForm').form("validate")) {
			jQuery.post(url, $('#pwdForm').serialize(), function (data) {
				if (data == "success") {
					slideMessage("操作成功！");
					$('#headerEditDialog').dialog('close');
				} else {
					warningMessage('操作失败!原始密码输入错误！');
					return;
				}
			});
		}
	}
</script>
<form id="pwdForm" method="post" >
<input type="hidden" id="userID" name="userID" value="${systemUserId }">
<br/>
<div>
	<table style="width:100%;height:100%; border-collapse: separate;border-spacing: 4px;" align="center">
		<tr>
			<td align="right"><em style="color:red">*</em><b>原密码：</b></td>
			<td>
				<input type="password" id="oldPWD" name="oldPWD" class="easyui-validatebox" data-options="required:true,validType:'password'" maxlength="16" />
			</td>
		</tr>
		<tr>
			<td align="right"><em style="color:red">*</em><b>新密码：</b></td>
			<td>
				<input type="password" id="newPWD" name="newPWD" class="easyui-validatebox" data-options="required:true,validType:'password'" maxlength="16" />
			</td>
		</tr>
		<tr>
			<td align="right"><em style="color:red">*</em><b>确认密码：</b></td>
			<td>
				<input type="password" id="rnewPWD" name="rnewPWD" class="easyui-validatebox" required="required" validType="equalTo['#newPWD']" maxlength="16" />
			</td>
		</tr>
	</table>
</div>
</form>