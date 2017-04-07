
$(document).ready(function(){
	//数据加载
	$('#pushrecdg').datagrid({
		url:CONTEXT+'pushrec/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#pushrectb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#pushrecdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'title',title:'消息描述',width:100,align:'center'},
					{field:'content',title:'内容',width:100,align:'center'},
					{field:'account',title:'发送给',width:100,align:'center',formatter:formatterAccount },
					{field:'createTime',title:'推送时间',width:100,align:'center'},
					{field:'receiveType',title:'发送站点',width:100,align:'center',formatter:formatterReceiveType },
					{field:'redirectUrl',title:'跳转地址',width:100,align:'center',formatter:formatterUrl },
				]]
	}); 
	//分页加载
	$("#pushrecdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});



function formatterAccount(val, row) {
	if (val == null || val == undefined ) return "所有人";
	if (val != null) {
		var  str=val.toString();
		return str;
	 
	} 
}

//接收类型(0:全部,1:谷登农批网,2:农商友,3:农速通)
function formatterReceiveType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "谷登农批";
		}else if(str=="2"){
			return "农商友";
		}else if(str=="3"){
			return "农速通";
		} else if(str=="4"){
			return "产地供应商";
		} 
	} 
}


//农商友APP-推荐页面（NSY_TJ)
//农商友APP_关注页面（NSY_GZ)
//农速通APP_我要找车页面（NST_WYZC)
//农速通APP_我要找货页面（NST_WYZH)
function formatterUrl(val, row) {
	
	if (val != null) {
		var  str=val.toString();
		if(str=="NSY_TJ"){
			return "农商友APP-推荐页面";
		}else if(str=="NSY_GZ"){
			return "农商友APP_关注页面";
		}else if(str=="NST_WYZC"){
			return "农速通APP_我要找车页面";
		} else if(str=="NST_WYZH"){
			return "农速通APP_我要找货页面";
		} 
	} 
}


// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#pushrecdg').datagrid('options').queryParams;
	queryParams.title = $('#pushRecForm #title').val();
	queryParams.startDate =  $('#pushRecForm #startDate').val();
	queryParams.endDate =  $('#pushRecForm #endDate').val();
	
	var queryUrl=CONTEXT+'pushrec/querybysearch';
	
	$('#pushrecdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#pushrectb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'title',title:'消息描述',width:100,align:'center'},
					{field:'content',title:'内容',width:100,align:'center'},
					{field:'account',title:'发送给',width:100,align:'center'},
					{field:'createTime',title:'推送时间',width:100,align:'center'},
					{field:'receiveType',title:'发送站点',width:100,align:'center',formatter:formatterReceiveType },
					{field:'redirectUrl',title:'跳转地址',width:100,align:'center',formatter:formatterUrl },

				]]
	}); 
	//分页加载
	$("#pushrecdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});



$('#icon-add').click(function(){
	$('#addDialog').dialog({'title':'新增记录','href':CONTEXT+'pushrec/addDto', 'width': 600,'height': 600}).dialog('open');
});



function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
    $("#icon-save-btn").linkbutton('disable');
	var url=CONTEXT+"pushrec/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
            $("#icon-save-btn").linkbutton('enable');

			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#pushrecdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else if(data == "type"){
				slideMessage("请选择单播或广播！");
				return;
			}else if(data == "title"){
				slideMessage("请填写标题！");
				return;
			}else if(data == "content"){
				slideMessage("请填写内容！");
				return;
			}else if(data == "receiveType"){
				slideMessage("请选择发送站点！");
				return;
			}else if(data == "memberId"){
				slideMessage("请选择发送人员！");
				return;
			} else {
				warningMessage(data);
				return;
			}
		});
}

$('#btn-reset').click(function(){
	$('#pushRecForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#pushRecForm')[0].reset();
	$("#pushrecdg").datagrid('load', {});
});