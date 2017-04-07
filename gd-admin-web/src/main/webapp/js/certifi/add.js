


function save() {
	
	if(!$("#auditForm").form('validate')){
		return $("#auditForm").form('validate');
	}
 	
 	
	var url=CONTEXT+"certifi/audit";
		jQuery.post(url, $('#auditForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#certifidg").datagrid('reload');
				$('#showDialog').dialog('close');
			} else {
				warningMessage(data);
				return;
			}
		});
}

function auditUnpass() {
	
	if(!$("#auditUnpassForm").form('validate')){
		return;
	}
	var url=CONTEXT+"certifi/auditUnpass";
	jQuery.post(url, $('#auditUnpassForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#certifidg").datagrid('reload');
			$('#showDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}