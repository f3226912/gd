$(function() {
	// 数据加载
	loadData(null);
});


//查询按钮,根据name查询
$('#icon-search').click(function() {
	if (!($('#detectionSearchForm').form('validate'))) {
		return false;
	}
	if (!dateCompare()) {
		return false;
	}
	var dataParams={
		productName : $('#detectionSearchForm #productName').val(),
		startDate : $("#startDate").datetimebox("getValue"),
		endDate : $("#endDate").datetimebox("getValue"),
		maketId : $('#detectionSearchForm #maketId').val()
	};
	loadData(dataParams);
});


function loadData(dataParams){
	$('#detectionDatagrid').datagrid({
		url : CONTEXT + 'detectinfo/query',
		queryParams : dataParams,
		// width: 1000,
		height : 'auto',
		nowrap : true,
		toolbar : '#detectiontb',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		columns : [ [
			{
				field : 'id',
				title : '',
				width : 50,
				checkbox : true
			},{
				field : 'productName',
				title : '商品名称',
				width : 100,
				align : 'center'
			},{
				field : 'marketName',
				title : '检测市场',
				width : 100,
				align : 'center'
			},{
				field : 'origin',
				title : '出产地',
				width : 100,
				align : 'center'
			},{
				field : 'unitName',
				title : '被检单位或姓名',
				width : 100,
				align : 'center'
			},{
				field : 'inspection',
				title : '检测项目',
				width : 100,
				align : 'center',
				formatter : inspectionFormat
			},{
				field : 'rate',
				title : '抑制率',
				width : 50,
				align : 'center',
				formatter : rateFormat
			},{
				field : 'pass',
				title : '是否合格',
				width : 50,
				align : 'center',
				formatter :passFormat
			},{
				field : 'detectTime',
				title : '检测日期',
				width : 100,
				align : 'center'
			},{
				field : 'publishTime',
				title : '发布日期',
				width : 100,
				align : 'center'
			},{
				field : 'opt',
				title : '操作',
				width : 100,
				align : 'center',
				formatter : optformat
			}
		] ]
	});
	// 分页加载
	$("#detectionDatagrid").datagrid("getPager").pagination({
		pageList : [ 50,100, 150,200 ]
	});
}


function passFormat(value,row, index){
	return row.pass > 0 ? "是": "否";
}

function rateFormat(value,row, index){
	return "" + row.rate+ "%";
}

function inspectionFormat(value,row, index){
	switch (row.inspection) {
		case 0:
			return "蔬菜农药残留检测";
			break;
		default:
			return "蔬菜农药残留检测";
			break;
	}
}

function formatterdate(val, row) {
	if (val != null) {
		var str = val.toString();
		str = str.replace(/-/g, "/");
		var date = new Date(str);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
	}
}

function save() {
	if ($('#addForm').form('validate')) {
		if (!timeValidate()) {
			return false;
		}
		var url = CONTEXT + "detectinfo/save";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#detectionDatagrid").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage("保存失败！");
				return false;
			}
		});
	}
}

// 删除操作
$("#icon-remove").click(function() {
	var row = $('#detectionDatagrid').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	}
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			var deleteStr = getSelections("id");
			jQuery.post(CONTEXT + "detectinfo/delete", {
				"ids" : deleteStr
			}, function(data) {
				if (data == "success") {
					slideMessage("删除成功！");
					$('#detectionDatagrid').datagrid('reload');
					$('#detectionDatagrid').datagrid("uncheckAll");
				} else {
					warningMessage("删除失败！");
					return false;
				}
			});
		} else {
			return false;
		}
	});
});

// 编辑
$("#icon-edit").click(function() {
	var row = $('#detectionDatagrid').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	}
	var seleteStr = getSelections("id");
	if (seleteStr.indexOf(",") > 0) {
		warningMessage("请不要操作多条数据！");
		return false;
	}
	$('#addDialog').dialog({
		'title' : '编辑检测信息',
		'href' : CONTEXT + 'detectinfo/edit/' + seleteStr
	}).dialog('open');
});

/**
 * 查看操作
 *
 * @param id
 *            对象ID
 */
function viewObj(id) {

}

/**
 * 修改操作
 *
 * @param id
 *            当前对象ID
 */
function editObj(id) {
	$('#addDialog').dialog({
		'title' : '编辑检测信息',
		'href' : CONTEXT + 'detectinfo/edit/' + id
	}).dialog('open');
}

/**
 * 删除操作
 *
 * @param id
 *            当前对象ID
 */
function delObj(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "detectinfo/delete", {
				"ids" : id
			}, function(data) {
				if (data == "success") {
					slideMessage("删除成功！");
					$('#detectionDatagrid').datagrid('reload');
					$('#detectionDatagrid').datagrid("uncheckAll");
				} else {
					warningMessage("删除失败！");
					return;
				}
			});
		} else {
			return;
		}
	});
}

function dateCompare() {
	if ($("#endDate").datetimebox("getValue")) {
		if ($("#startDate").datetimebox("getValue")>= $("#endDate").datetimebox("getValue")) {
			warningMessage("开始时间必须小于结束时间！");
			return false;
		} else {
			return true;
		}
	}
	return true;
}

// 新增
$('#icon-add').click(function() {
	$('#addDialog').dialog({
		'title' : '新增检测信息',
		'href' : CONTEXT + 'detectinfo/addDto'
	}).dialog('open');
});


// 重置
$('#btn-reset').click(function(){
	$('#detectionSearchForm')[0].reset();
	$("#startDate").datetimebox("setValue","");
	$("#endDate").datetimebox("setValue","");
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#detectionSearchForm')[0].reset();
	$("#detectionDatagrid").datagrid('load',{});
	//重启导出功能
	disableExport = false ;
});
