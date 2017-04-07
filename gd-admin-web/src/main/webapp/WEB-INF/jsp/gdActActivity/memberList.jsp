<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>活动信息</title>
</head>
<body>
<div id="memberList" class="easyui-tabs" style="width:780px;height:360px;overflow-x: hidden !important; overflow-y: hidden !important;">
<div title="活动商铺信息" style="overflow-y: hidden !important;" data-options="closable:true">	
	<table id="datagrid-table-sellerList" title=""></table>
	<div id="datagrid-tool-bar-seller" style="padding:5px;height:auto">
		<div id="sellerList_tb_layout" style="margin-bottom:5px">
		</div>
	</div>
</div>
<div title="活动买家信息" style="overflow-x: hidden !important; overflow-y: auto !important;" data-options="closable:true">
	<table id="datagrid-table-buyerList" title=""></table>
	<div id="datagrid-tool-bar-buyer" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
		</div>
	</div>
</div>
<input type="hidden" id="activityId" value="${activityId}">
<input type="hidden" id="type" value="${type}">
</div>
<script type="text/javascript" >
$('#memberList').tabs({
    border:false,
    onSelect:function(title){
    }
});	

$(document).ready(function() {
	initShopRuleList();
});

function initShopRuleList() {
	$('#sellerList_tb_layout').datagrid({
		width: 'auto',
		queryParams: null,
		height: 'auto', 
		nowrap:true,
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#sellerList_tb_layout").datagrid('clearSelections');
		},
		data: addShopData.slice(0, 10),
		columns:[[
			{field:'id',title:'',width:50 },
			{field:'cardType',title:'手机号码',width:200,align:'center'},
			{field:'shopsName',title:'商铺名称',width : 200,align:'center'},
			{field:'level',title:'用户类型',width:100,align:'center'},
			{field:'marketName',title:'所属市场',width:150,align:'center'},
			{field:'state',title:'主要分类',width:100,align:'center'},
			{field:'state',title:'线下认证',width:100,align:'center'},
			{field:'state',title:'创建时间',width:100,align:'center'},
			{field : 'opt',title :'操作',width : 200,align : 'center',formatter : optformatshoprule}
		]]
	});
	$('#sellerList_tb_layout').datagrid('hideColumn','id');
	$("#sellerList_tb_layout").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
	
	var pager = $("#sellerList_tb_layout").datagrid("getPager");

	pager.pagination({
		total : addShopData.length,
		onSelectPage : function(pageNo, pageSize) {
			debugger;
			var start = (pageNo - 1) * pageSize;
			var end = start + pageSize;
			$("#sellerList_tb_layout").datagrid("loadData", addShopData.slice(start, end));
			pager.pagination('refresh', {
				total : addShopData.length,
				pageNumber : pageNo
			});
		}
	});
}

function optformatshoprule(value,row,index) {
	var options = "<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=delshoprule('"+row.businessId+"')>删除</a></gd:btn>";
	return options;
}

function delshoprule(businessId) {
	removeShopRule(businessId)
	initShopRuleList();
}
</script>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/memberList.js"></script>
</body>

</html>