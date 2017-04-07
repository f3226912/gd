
$(document).ready(function(){
	//数据加载
	$('#giftdg').datagrid({
		url:CONTEXT+'gift/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#gifttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#giftdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'礼品名称',width:100,align:'center'},
					{field:'integral',title:'礼品积分',width:100,align:'center'},
					{field:'type',title:'状态',width:100,align:'center',formatter:formatterType},
					{field:'actName',title:'活动名称',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#giftdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

//查询按钮
$('#icon-search').click(function(){ 
 var queryParams = $('#giftdg').datagrid('options').queryParams;
	queryParams.giftName = $('#giftForm #giftName').val();
	queryParams.actName = $('#giftForm #actName').val();
	
	$('#giftdg').datagrid({
		url:CONTEXT+'gift/querybysearch',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#gifttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#giftdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'礼品名称',width:100,align:'center'},
					{field:'integral',title:'礼品积分',width:100,align:'center'},
					{field:'type',title:'状态',width:100,align:'center',formatter:formatterType},
					{field:'actName',title:'活动名称',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#giftdg").datagrid("getPager").pagination({
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
		else if(str=="3"){
			return "推荐码积分";
		}
		else if(str=="4"){
			return "车辆积分";
		}
	} 
}

$('#icon-add').click(function(){
	$('#addDialog').dialog({'title':'新增记录','href':CONTEXT+'gift/addDto', 'width': 700,'height': 400}).dialog('open');
});

function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	var url=CONTEXT+"gift/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#giftdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else if(data == "error1"){
				slideMessage("同种活动礼品名称不能重复!");
				return;
			}else if(data == "error2"){
				slideMessage("同种活动车辆积分类型不能重复!");
				return;
			}else if(data == "type"){
				slideMessage("请选择状态！");
				return;
			}else if(data == "name"){
				slideMessage("请填写礼品名称！");
				return;
			}else if(data == "integral"){
				slideMessage("请填写礼品积分！");
				return;
			}else {
				warningMessage(data);
				return;
			}
		});
}

//编辑
function editObj(id){
		$('#addDialog').dialog({'title':'礼品信息','href':CONTEXT+'gift/editbyid/'+id,'width': 700,'height': 400}).dialog('open');
}

$('#btn-reset').click(function(){
	$('#giftForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#giftForm')[0].reset();
	$("#giftdg").datagrid('load', {});
});
