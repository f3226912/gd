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
			account:$('#subsidySearchForm #account').val(),
			mobile:$('#subsidySearchForm #mobile').val(),
			level:$('#subsidySearchForm #level').val(),
			startDate:$("#startDate").datetimebox("getValue"),
			endDate:$("#endDate").datetimebox("getValue")
	};
	dataLoad(dataParams);
});
function dataLoad(dataParams){
	// 数据加载
	$('#subsidyDatagrid').datagrid({
		url : CONTEXT + 'memberSubsidy/query',
		queryParams: dataParams,
		height : 'auto',
		nowrap : true,
		toolbar : '#toolbar',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess:function(){
			$("#subsidyDatagrid").datagrid('clearSelections');
		},
		columns : [ [
		{
			field : 'account',
			title : '帐号',
			width : 100,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机',
			width : 100,
			align : 'center'
		}, {
			field : 'realName',
			title : '姓名',
			width : 100,
			align : 'center'
		},{
			field : 'createTime',
			title : '注册时间',
			width : 100,
			align : 'center'
		},
//		{
//			field : 'marketId',
//			title : '所属市场',
//			width : 100,
//			align : 'center',
//			formatter: maketIdformat
//		},
		{
			field : 'level',
			title : '用户类型',
			width : 100,
			align : 'center',
			formatter: levelformat
		}, {
			field : 'rejectCount',
			title : '系统驳回次数',
			width : 100,
			align : 'center'
		},{
			field:'orderCount',
			title:'交易订单总数',
			width:100,
			align:'center'
		},{
			field:'productCount',
			title:'交易产品总数',
			width:100,
			align:'center'
		},{
			field:'orderAmount',
			title:'交易总额',
			width:100,
			align:'center'
		},{
			field:'subAmount',
			title:'补贴累计金额',
			width:100,
			align:'center'
		},{
			field:'discountAmount',
			title:'已抵扣金额',
			width:100,
			align:'center'
		},{
			field:'cashAmount',
			title:'已提现金额',
			width:100,
			align:'center'
		},{
			field:'balAvailable',
			title:'钱包现有余额',
			width:100,
			align:'center'
		}
		] ]
	});
	// 分页加载
	$("#subsidyDatagrid").datagrid("getPager").pagination({
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

function levelformat(value,row,index){
	if (row.level==1) {
		return "谷登农批";
	}else if(row.level==2){
		return "农速通";
	}else if(row.level==3){
		return "农商友";
	}else if(row.level==4){
		return "产地供应商";
	}else if(row.level==5){
		return "门岗";
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
	$('#subsidySearchForm')[0].reset();
	$("#startDate").datetimebox("setValue","");
	$("#endDate").datetimebox("setValue","");
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#subsidySearchForm')[0].reset();
	$("#subsidyDatagrid").datagrid('load',{});
	disableExport = false ;
});
