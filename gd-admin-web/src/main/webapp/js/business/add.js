
function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	
	
	var url=CONTEXT+"business/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#businessdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage(data);
				return;
			}
		});
}