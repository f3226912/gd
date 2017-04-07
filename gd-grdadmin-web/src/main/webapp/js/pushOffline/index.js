$(document).ready(function() {
	// 数据加载
	dataLoad(null);
});
//查询按钮,根据name查询
$('#icon-search').click(function() {
	if(!dateCompare()){
		return false;
	}
	var dataParams={
			industry:$('#industry').val(),
			source:$('#source').val(),
			pushName:$('#pushName').val(),
			pushMobile:$('#pushMobile').val(),
			memberMobile:$('#memberMobile').val(),
			startDate:$("#startDate").datetimebox("getValue"),
			endDate:$("#endDate").datetimebox("getValue")
	};
	dataLoad(dataParams);
});
function dataLoad(dataParams){
	// 数据加载
	$('#pushOfflineDatagrid').datagrid({
		url : CONTEXT + 'pushOffline/query',
		queryParams: dataParams,
		height : 'auto',
		nowrap : true,
		toolbar : '#toolbar',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess:function(data){
			console.log(data);
			$("#total").text(data.total);
			$("#pushOfflineDatagrid").datagrid('clearSelections');
		},
		columns : [ [
		{
			field : 'industry',
			title : '所属行业',
			width : 100,
			align : 'center'
		}, {
			field : 'source',
			title : '推广来源',
			width : 100,
			align : 'center'
		}, {
			field : 'pushName',
			title : '推广人',
			width : 100,
			align : 'center'
		},{
			field : 'pushMobile',
			title : '推广人手机号',
			width : 100,
			align : 'center'
		},{
			field : 'memberMobile',
			title : '会员手机号',
			width : 100,
			align : 'center'
		}, {
			field : 'createTime',
			title : '推广时间',
			width : 100,
			align : 'center'
		}
		] ]
	});
	// 分页加载
	$("#pushOfflineDatagrid").datagrid("getPager").pagination({
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
	$('#pushOfflineSearchForm')[0].reset();
	$("#startDate").datetimebox("setValue","");
	$("#endDate").datetimebox("setValue","");
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#pushOfflineSearchForm')[0].reset();
	$("#pushOfflineDatagrid").datagrid('load',{});
	//重启导出功能
	disableExport = false ;
});
