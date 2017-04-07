function initPromotionTypeList(){
	loadPromotionType(null);
	//分页加载
	$("#promotionTypedg").datagrid("getPager").pagination({
		pageSize: 10,
		pageList: [10,20,50,100]
	});
}

function loadPromotionType(params){
	params = !params ? {}: params;
	//数据加载
	$('#promotionTypedg').datagrid({
		url:CONTEXT+'promotion/queryType',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#promotionTypetb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#promotionTypedg").datagrid('clearSelections');
		},
		columns:[[
		    {field:'id',title:'id',width:50,checkbox:true},
			{field:'name',title:'类型名称',width:100},
			{field:'url',title:'地址',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]],
		onDblClickRow: function(index,row){
		}
		
	}); 
}
//查询按钮,根据name查询
$('#icon-search').click(function(){
	var params = {"name":$('#promotionTypeSearchForm #name').val()};
	loadPromotionType(params);
});
$('#icon-refresh').click(function(){
	window.location.reload(true);
});


//新增
$('#icon-add').click(function(){
	$('#addDialog').dialog({'title':'新增类型','href':CONTEXT+'promotion/typeAdd', 'width': 300,'height': 200}).dialog('open');
});

$("#btn-reset-form").click(function(){
	document.getElementById("promotionTypeSearchForm").reset();
});

function saveTypeInfo() {
	if(!$("#addForm").form('validate')){
		return;
	}
	var name=$('#addForm #name').val();
	if(name.length == 0 || name.match(/^\s+$/g)){
		warningMessage("类型名称不能为空或者全为空格！");
		return;
	}
	var url=CONTEXT+"promotion/saveTypeInfo";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#promotionTypedg").datagrid('reload');
			$('#addDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}



function editObj(id){
	$('#editDialog').dialog({'title':'编辑类型','href':CONTEXT+'promotion/editTypeById/'+id,'width': 400,'height': 300}).dialog('open');
}

function deletebyid(id){
	$.ajax( {  
		url:CONTEXT+'promotion/deleteType/'+id,// 跳转到 action  
		type:'post',  
		dataType:'json',  
		success:function(data) {  
			if(data.status && data.status > 0 ){  
				slideMessage("删除成功！");  
				window.location.reload();  
			}else{  
				warningMessage("删除失败！"); 
			}  
		},  
		error : function() {  
		}  
	});
}
function updateTypeInfo() {
	if(!$("#editForm").form('validate')){
		return;
	}
	var name=$('#editForm #name').val();
	if(name.length == 0 || name.match(/^\s+$/g)){
		warningMessage("类型名称不能为空或者全为空格！");
		return;
	}
	var url=CONTEXT+"promotion/saveTypeModification";
	jQuery.post(url, $('#editForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#promotionTypedg").datagrid('reload');
			$('#editDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}