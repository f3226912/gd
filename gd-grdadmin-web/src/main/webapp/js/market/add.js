
function save() {

	if($("#addForm").form('validate')){
		var marketName= $('#addForm #marketName').val();
		if(marketName==null ||  $.trim(marketName)==""){
			warningMessage("街市名称不能为空!");
			return false;
		}
		var address=  $('#addForm #address').val();
		if(address ==null || $.trim(address)== ""){
			warningMessage("详细地址不能为空!");
			return false;
		}
		var url=CONTEXT+"market/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#marketdg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else if(data == "exists")
			{
				warningMessage("街市名称已存在，请重新输入！");
				return;
			}	
			else {
				warningMessage("系统异常！");
				return;
			}
		});
	}
	
	

}