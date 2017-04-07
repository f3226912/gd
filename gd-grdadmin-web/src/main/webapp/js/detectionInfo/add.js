//function save() {
//	if (!$("#addForm").form('validate')) {
//		return $("#addForm").form('validate');
//	}
//	var url = CONTEXT + "detectinfo/save";
//	jQuery.post(url, $('#addForm').serialize(), function(data) {
//		if (data == "success") {
//			slideMessage("保存成功！");
//			// 刷新父页面列表
//			$("#detectionDatagrid").datagrid('reload');
//			$('#addDialog').dialog('close');
//		} else {
//			warningMessage("保存失败！");
//			return;
//		}
//	});
//}

/**验证采集时间与发布时间，采集时间不能大于发布时间
 * @returns {Boolean}
 */
function timeValidate(){
	if ($("#detectTime").datetimebox("getValue")> $("#publishTime").datetimebox("getValue")) {
		warningMessage("检测日期不能大于发布日期！");
		return false;
	} else {
		return true;
	}
}