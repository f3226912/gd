//function save() {
//	if (!$("#addForm").form('validate')) {
//		return $("#addForm").form('validate');
//	}
//	var url = CONTEXT + "marketprice/save";
//	jQuery.post(url, $('#addForm').serialize(), function(data) {
//		if (data == "success") {
//			slideMessage("保存成功！");
//			// 刷新父页面列表
//			$("#pricesDatagrid").datagrid('reload');
//			$('#addDialog').dialog('close');
//		} else {
//			warningMessage("保存失败！");
//			return;
//		}
//	});
//}

function priceValidate(){
	if (($('#minPrice').numberbox('getValue')-$('#maxPrice').numberbox('getValue'))>0) {
		warningMessage("最高价不能小于最低价！");
		return false;
	}
	return true;
}

/**验证采集时间与发布时间，采集时间不能大于发布时间
 * @returns {Boolean}
 */
function timeValidate(){
	if ($("#collectTime").datetimebox("getValue") > $("#publishTime").datetimebox("getValue")) {
		warningMessage("采集时间不能大于发布时间！");
		return false;
	} else {
		return true;
	}
}

/**将时间格式字符串转化为时间戳
 * @param dateStr
 * @returns
 */
function getTimeStamp(dateStr){
    var newstr = dateStr.replace(/-/g,'/'); 
    var date =  new Date(newstr); 
    var time_str = date.getTime().toString();
    return time_str.substr(0, 10);
}