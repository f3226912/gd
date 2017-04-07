<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	function save() {
		var url=CONTEXT+"sysmgr/sysRole/${actionUrl}";
		if ($('#editForm').form("validate")) {
			jQuery.post(url, $('#editForm').serialize(), function (data) {
				if (data == "success") {
					slideMessage("操作成功！");
					//刷新父页面列表
					$("#dg").datagrid('reload');
					$('#editDialog').dialog('close');
				} else {
					warningMessage(data);
					return;
				}
			});
		}
	}
</script>
<form id="editForm" method="post" >
<input type="hidden" id="roleID" name="roleID" value="${dto.roleID }">
<br/>
<div>
	<table class="grid" align="center">
		<tr>
			<td align="right"><em style="color:red">*</em><b>角色名称：</b></td>
			<td>
				<input type="text" id="roleName" name="roleName" class="easyui-validatebox" size="38" data-options="required:true,validType:'inputName'" maxlength="30" value="${dto.roleName }"/>
			</td>
		</tr>
		<tr>
			<td align="right"><b>备注：</b></td>
			<td>
				<textarea id="remark" name="remark" rows="5" cols="40" maxlength="400" placeholder="最多输入400个字符">${dto.remark }</textarea>
			</td>
		</tr>
	</table>
</div>
</form>