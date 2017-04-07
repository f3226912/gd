
$(document).ready(function(){
	//数据加载
	$('#activitydg').datagrid({
		url:CONTEXT+'activity/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#activitytb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#activitydg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'活动名称',width:100,align:'center'},
					{field:'limitIntegral',title:'积分上限',width:100,align:'center'},
					{field:'description',title:'活动说明',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#activitydg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

//查询按钮
$('#icon-search').click(function(){ 
 var queryParams = $('#activitydg').datagrid('options').queryParams;
	queryParams.name = $('#activityForm #name').val();
	queryParams.queryStartDate =  $('#activityForm #queryStartDate').val();
	queryParams.queryEndDate =  $('#activityForm #queryEndDate').val();
	
	$('#activitydg').datagrid({
		url:CONTEXT+'activity/querybysearch',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#activitytb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#activitydg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'活动名称',width:100,align:'center'},
					{field:'limitIntegral',title:'积分上限',width:100,align:'center'},
					{field:'description',title:'活动说明',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#activitydg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

//状态(1 有效奖品 2:失效奖品)
function formatterType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "有效奖品";
		}else if(str=="2"){
			return "失效奖品";
		}
	} 
}

$('#icon-add').click(function(){
	$('#addDialog').dialog({'title':'新增记录','href':CONTEXT+'activity/addDto', 'width': 500,'height': 400}).dialog('open');
});

function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	var url=CONTEXT+"activity/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#activitydg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else if(data == "error1"){
				slideMessage("活动起始时间不能小于最后一次活动截止时间!");
				return;
			}else {
				warningMessage(data);
				return;
			}
		});
}

//编辑
function editObj(id){
		$('#addDialog').dialog({'title':'礼品信息','href':CONTEXT+'activity/editbyid/'+id,'width': 500,'height': 400}).dialog('open');
}

$('#btn-reset').click(function(){
	$('#activityForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#activityForm')[0].reset();
	$("#activitydg").datagrid('load', {});
});
