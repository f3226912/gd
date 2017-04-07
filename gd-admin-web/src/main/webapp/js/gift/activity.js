
function loadActivityData(params){
	params = !params ? {}: params;
	//数据加载
	$('#activitydg').datagrid({
		url:CONTEXT+'gift/queryActivity',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#activitytb',
		pageSize:10,
		queryParams: params,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'id',title:'',width:100,hidden:true},
			{field:'name',title:'活动名称',width:100,align:'center'},
			{field:'startTime',title:'活动开始时间',width:100,align:'center'},
			{field:'endTime',title:'活动结束时间',width:100,align:'center'},
			{field:'opt',title:'操作',width:50,align:'center', formatter : function(val, row, index){
				return "<a href='javascript:;' onClick=\"activitySelectCallback('"+row.id+"','"+row.name+"')\">选择</a>";
			}}
		]]
	}); 
	//分页加载
	$("#activitydg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){
	loadActivityData({});
});


//查询按钮,根据name查询
$('#activity-btn-search').click(function(){ 
	var name = $('#activityForm #name').val();
	var queryStartDate = $('#activityForm #startDate').val();
	var queryEndDate = $('#activityForm #endDate').val();
	var params = {'name':name,'queryStartDate':queryStartDate, 'queryEndDate':queryEndDate};
	loadActivityData(params);
});

$("#activity-btn-refresh").click(function(){
	$('#activityForm')[0].reset();
	$("#activitydg").datagrid('load',{});
});
