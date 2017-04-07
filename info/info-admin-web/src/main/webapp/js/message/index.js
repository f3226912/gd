$(document).ready(function() {
	dataLoad(null);
});
//查询按钮,根据name查询
$('#icon-search').click(function() {
	var dataParams={
		title:$('#pricesSearchForm #title').val()
	};
	dataLoad(dataParams);
});

function dataLoad(dataParams){
	// 数据加载
	$('#pricesDatagrid').datagrid({
		url : CONTEXT + 'message/query',
		queryParams: dataParams,
		checkbox:false,
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
		columns : [ [
		{
			field : 'title',
			title : '消息名称',
			width : 100,
			align : 'center'
		}, {
			field : 'createTime',
			title : '消息发布时间',
			width : 100,
			align : 'center'
		}, {
			field : 'createUserName',
			title : '发布人',
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

function save() {
	if($('#addForm').form('validate')){
		var url = CONTEXT + "message/save";
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

// 新增
$('#icon-add').click(function() {
	$('#addDialog').dialog({
		'title' : '新增',
		'href' : CONTEXT + 'message/addInit'
	}).dialog('open');
});

function detail(id){
	$('#addDialog').dialog({
		'title' : '详情',
		'href' : CONTEXT + 'message/detail?id='+id
	}).dialog('open');
}


//重置
$('#btn-reset').click(function(){
	$('#pricesSearchForm')[0].reset();
	$("#title").val("");
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#pricesSearchForm')[0].reset();
	$("#pricesDatagrid").datagrid('load',{});
});
