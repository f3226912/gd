
function loadSellerList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table-sellerList').datagrid({
		url:CONTEXT+'gdActActivity/loadSellerList',
		width: 'auto', 
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar-seller',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table-sellerList").datagrid('clearSelections');
		},
		columns:[[
			{field:'mobile',title:'手机号码',width:80,align:'center'},
			{field:'shopsName',title:'商铺名称',width:80,align:'center'},
			{field:'level',title:'用户类型',width:80,align:'center', formatter : userformat},
			{field:'marketName',title:'所属市场',width:80,align:'center'},
			{field:'cateName',title:'主营分类',width:80,align:'center'},
			{field:'offlineStatus',title:'线下认证',width:80,align:'center', formatter : offlineformat},
			{field:'createTime',title:'创建时间',width:80,align:'center'}
		]]
	}); 
	$("#datagrid-table-sellerList").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}
function loadBuyerList(params){
	params = !params ? {}: params;
	$('#datagrid-table-buyerList').datagrid({
		url:CONTEXT+'gdActActivity/loadBuyerList',
		width: 'auto', 
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar-buyer',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table-buyerList").datagrid('clearSelections');
		},
		columns:[[
			{field:'realName',title:'用户姓名',width:100,align:'center'},
			{field:'mobile',title:'手机号码',width:100,align:'center'},
			{field:'shopsName',title:'商铺名称',width:100,align:'center'},
			{field:'level',title:'用户类型',width:100,align:'center', formatter : userformat},
			{field:'marketName',title:'商铺所属市场',width:100,align:'center'},
			{field:'regetype',title:'注册来源',width:100,align:'center', formatter : regtypeformat},
			{field:'createTime',title:'创建时间',width:100,align:'center'}
		]]
	}); 
	$("#datagrid-table-buyerList").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

//1已认证 2未认证
function offlineformat(value,row,index){
	var result = "";
	if (value == 1){
		result = "认证";
	}else if (value == 2){
		result = "未认证";
	}
	return result;
}
//1谷登农批，2农速通，3农商友，4产地供应商，5门岗
function userformat(value,row,index){
	var result = "";
	if (value == 1){
		result = "谷登农批";
	}else if (value == 2){
		result = "农速通";
	}else if (value == 3){
		result = "农商友";
	}else if (value == 4){
		result = "供应商";
	}else if (value == 5){
		result = "门岗";
	}
	return result;
}

//0管理后台,1谷登农批网,2农速通(旧),3农商友,4农商友-农批商,5农批友,6供应商,7POS刷卡 ,8微信授权,9农速通-货主,10农速通-司机,11农速通-物流公司',
function regtypeformat(value,row,index){
	var result = "";
	if (value == 1){
		result = "谷登农批网";
	}else if (value == 2){
		result = "农速通(旧)";
	}else if (value == 3){
		result = "农商友";
	}else if (value == 4){
		result = "农商友-农批商";
	}else if (value == 5){
		result = "农批友";
	}else if (value == 6){
		result = "供应商";
	}else if (value == 7){
		result = "POS刷卡";
	}else if (value == 8){
		result = "微信授权";
	}else if (value == 9){
		result = "农速通-货主";
	}else if (value == 10){
		result = "农速通-司机";
	}else if (value == 11){
		result = "农速通-物流公司";
	}else if (value == 0){
		result = "管理后台";
	}
	return result;
}

$(function(){
	
	var params = {"activityId" : $("#activityId").val()};
	loadSellerList(params);
	$('#memberList').tabs('select',1);
	loadBuyerList(params);
	
	var type = $("#type").val() ;
	if (type == 1){
		$('#memberList').tabs('select',1);
	}else if(type ==2){
		$('#memberList').tabs('select',0)
	}
	
});