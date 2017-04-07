$(document).ready(function(){
	var params={"memberId":$("#memberId").val()};
	changeLog(params,CONTEXT+'member/getChangeLogList');
});


function changeLog(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#changeLogdg').datagrid({
		url:loadUrl,
		queryParams: params,
		nowrap:false,
		height: 'auto', 
		//toolbar:'#accountFlowtb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		columns:[[
					{field:'type',title:'变更操作',width:100,align:'center',formatter:typeStatus},
					{field:'description',title:'变更说明',width:100,align:'center'},
					{field:'createUserName',title:'操作人员',width:100,align:'center'},
					{field:'createTime',title:'变更时间',width:100,align:'center'},
				]]
	}); 
	//分页加载
	$("#changeLogdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
}
function typeStatus(val){
	if(val == 1){
		return '变更为金牌供应商';
	}else{
		return '变更为普通会员';
	}
}
