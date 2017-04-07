$(document).ready(function(){
	var params={"cashRequestId":$("#cashRequestId").val()};
	loadAccountFlow(params,CONTEXT+'cash/cashAccountFlowList');
});


function loadAccountFlow(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#accountFlowdg').datagrid({
		url:loadUrl,
		queryParams: params,
		nowrap:true,
		height: 'auto', 
		//toolbar:'#accountFlowtb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'income',title:'收入',width:100,align:'center',formatter:incomeStatus},
					{field:'flowId',title:'流水号',width:100,align:'center'},
					{field:'expense',title:'支出',width:100,align:'center',formatter:expenseStatus},
					{field:'balTotal',title:'账户余额',width:100,align:'center'},
					{field:'createFlowTime',title:'时间',width:100,align:'center'},
				]]
	}); 
	//分页加载
	$("#accountFlowdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
}
function incomeStatus(val){
	if(val!=null && val!=''){
		return '+'+val;
	}else{
		return '';
	}
}
function expenseStatus(val){
	if(val!=null && val!=''){
		return '-'+val;
	}else{
		return '';
	}
}
