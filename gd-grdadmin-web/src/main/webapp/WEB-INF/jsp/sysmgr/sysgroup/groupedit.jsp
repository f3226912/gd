<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	function save() {
		var url=CONTEXT+"sysmgr/sysGroup/${actionUrl}";
		if ($('#myForm').form("validate")) {
			jQuery.post(url, $('#myForm').serialize(), function (data) {
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
<form id="myForm" method="post" >
<input type="hidden" id="resGroupID" name="resGroupID" value="${dto.resGroupID }">
<br/>
<div>
	<table class="grid" align="center">
		<tr>
			<td><em style="color:red">*</em><b>资源组名称</b>：</td>
			<td>
				<input type="text" id="resGroupName" name="resGroupName" class="easyui-validatebox" data-options="required:true,validType:'objectName'" size="38" maxlength="10" value="${dto.resGroupName }"/>
			</td>
		</tr>
		<tr>
			<td><em style="color:red">*</em><b>备注</b>：</td>
			<td>
				<textarea id="remark" name="remark" cols="40" rows="5" class="easyui-validatebox" data-options="required:true,validType:'objectName'" maxlength="400" >${dto.remark }</textarea>
			</td>
		</tr>
	</table>
</div>
</form>