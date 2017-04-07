
$(document).ready(function(){
	//数据加载
	$('#logitelstatdg').datagrid({
		url:CONTEXT+'logitelstat/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#logitelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#logitelstatdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center'},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTime',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#logitelstatdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#logitelstatdg').datagrid('options').queryParams;
	queryParams.e_Mobile = $('#logiTelStatForm #e_Mobile').val();
	queryParams.shopName = $('#logiTelStatForm #shopName').val();
	queryParams.startDate =  $('#logiTelStatForm #startDate').val();
	queryParams.endDate =  $('#logiTelStatForm #endDate').val();
	
	var queryUrl=CONTEXT+'logitelstat/querybysearch';
	
	$('#logitelstatdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#logitelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center'},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTime',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#logitelstatdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

