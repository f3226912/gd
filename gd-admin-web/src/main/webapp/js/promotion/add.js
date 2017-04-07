
function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	
	
	var url=CONTEXT+"member/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#memberdg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else  if(data == "exist"){
				warningMessage("账号重复,请修改账号");
				return;
			} else {
				warningMessage(data);
				return;
			}
		});
}