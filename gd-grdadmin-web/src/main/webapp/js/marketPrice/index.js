$(document).ready(function() {
	dataLoad(null);
});
//查询按钮,根据name查询
$('#icon-search').click(function() {
	if(!dateCompare()){
		return false;
	}
	var dataParams={
			productName:$('#pricesSearchForm #productName').val(),
	   		maketId:$('#pricesSearchForm #maketId').val(),
	   		startDate:$("#startDate").datetimebox("getValue"),
	   		endDate:$("#endDate").datetimebox("getValue")
	};
	dataLoad(dataParams);
});

function dataLoad(dataParams){
	// 数据加载
	$('#pricesDatagrid').datagrid({
		url : CONTEXT + 'marketprice/query',
		queryParams: dataParams,
		// width: 1000,
		height : 'auto',
		nowrap : true,
		toolbar : '#pricetb',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess:function(){
			$("#pricesDatagrid").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'id',
			title : '',
			width : 50,
			checkbox : true
		},
		{
			field : 'productName',
			title : '商品名称',
			width : 100,
			align : 'center',
			formatter:productNameformat
		}, {
			field : 'date',
			title : '采集时间',
			width : 100,
			align : 'center'
		}, {
			field : 'marketName',
			title : '采集市场',
			width : 100,
			align : 'center'
		},{
			field : 'maxPrice',
			title : '最高报价(元/公斤)',
			width : 100,
			align : 'center'
		}, {
			field : 'minPrice',
			title : '最低报价(元/公斤)',
			width : 100,
			align : 'center'
		}, {
			field : 'averagePrice',
			title : '平均报价(元/公斤)',
			width : 100,
			align : 'center'
		}, {
			field : 'publishTime',
			title : '发布时间',
			width : 100,
			align : 'center'
		},{
			field:'opt',
			title:'操作',
			width:100,
			align:'center',
			formatter:optformat
		}
		] ]
	});
	// 分页加载
	$("#pricesDatagrid").datagrid("getPager").pagination({
		pageList : [ 50,100, 150,200 ]
	});
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


function productNameformat(value,row,index){
	if ($.trim(row.targetUrl)) {
		if (row.targetUrl.indexOf("http://")!=0 && row.targetUrl.indexOf("https://")!=0) {
			row.targetUrl="http://"+row.targetUrl;
		}
		return "<a class='operate' href='"+row.targetUrl+"' target='_blank'>"+row.productName+"</a>";
	}else{
		return row.productName;
	}
}

function save() {
	if($('#addForm').form('validate')){
		if (!priceValidate()) {
			return false;
		}
		if (!timeValidate()) {
			return false;
		}
		var url = CONTEXT + "marketprice/save";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#pricesDatagrid").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage("保存失败！");
				return;
			}
		});
	}
}

// 删除操作
$("#icon-remove").click(function() {
	var row = $('#pricesDatagrid').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	}
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			var deleteStr = getSelections("id");
			jQuery.post(CONTEXT + "marketprice/delete", {
				"ids" : deleteStr
			}, function(data) {
				if (data == "success") {
					slideMessage("删除成功！");
					$('#pricesDatagrid').datagrid('reload');
					$('#pricesDatagrid').datagrid("uncheckAll");
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
	var row = $('#pricesDatagrid').datagrid("getSelections");
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
		'title' : '编辑市场价格信息',
		'href' : CONTEXT + 'marketprice/edit/' + seleteStr
	}).dialog('open');
});


/**
 * 查看操作
 * @param id 对象ID
 */
function viewObj(id){


}

/**修改操作
 * @param id 当前对象ID
 */
function editObj(id){
	$('#addDialog').dialog({
		'title' : '编辑市场价格信息',
		'href' : CONTEXT + 'marketprice/edit/' + id
	}).dialog('open');
}

// 删除操作
function delObj(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "marketprice/delete", {
				"ids" : id
			}, function(data) {
				if (data == "success") {
					slideMessage("操作成功！");
					$('#pricesDatagrid').datagrid('reload');
					$('#pricesDatagrid').datagrid("uncheckAll");
				} else {
					warningMessage(data);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}

// 新增
$('#icon-add').click(function() {
	$('#addDialog').dialog({
		'title' : '新增商品市场价格信息',
		'href' : CONTEXT + 'marketprice/addDto'
	}).dialog('open');
});

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

//重置
$('#btn-reset').click(function(){
	$('#pricesSearchForm')[0].reset();
	$("#startDate").datetimebox("setValue","");
	$("#endDate").datetimebox("setValue","");
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#pricesSearchForm')[0].reset();
	$("#pricesDatagrid").datagrid('load',{});
	//重启导出功能
	disableExport = false ;
});
