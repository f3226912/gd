//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#adinfodg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#adinfodg').datagrid({
		url:CONTEXT+'push/adInfoQuery',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#adinfotb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#adinfodg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:0,checkbox:true},
			{field:'marketId',title:'农贸市场',width:100,align:'center'},
			{field:'adCanal',title:'广告渠道',width:100,align:'center',formatter:formatterCanal },
			{field:'adType',title:'广告类型',width:100,align:'center',formatter:formatterAdType },
			{field:'adName',title:'广告名称',width:100,align:'center'},
			{field:'sort',title:'排序',width:25,align:'center'},
			{field:'startTime',title:'开始时间',width:100,align:'center'},
			{field:'endTime',title:'结束时间',width:100,align:'center'},
			{field:'state',title:'状态',width:25,align:'center',formatter:formatterState },
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
}

//状态(01开始,02截至,03下架)
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="01"){
			return "开始";
		}else if(str=="02"){
			return "截至";
		}else if(str=="03"){
			return "下架";
		} 
	}else{
		return "未知";
	}
}

//发布渠道（01农批web,02谷登物流,03农商友）
function formatterCanal(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="01"){
			return "农批web";
		}else if(str=="02"){
			return "农商友";
		}else if(str=="03"){
			return "农速通";
		}
	}else{
		return "未知";
	}
}

function formatterAdType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="01"){
			return "轮播图";
		}else if(str=="02"){
			return "产品推送";
		}else if(str=="03"){
			return "副图";
		}else if(str=="04"){
			return "全国农贸市场图";
		}
	}else{
		return "未知";
	}
}

function formatterdate(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   str =  str.replace(/-/g,"/");
	   var date = new Date(str);
	   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
		+ date.getDate();
	}
}

function formatPrice(value,row,index){
	if (row.unit == 0){
		return "克";
	}else if (row.unit == 1){
		return "公斤";
	}
}

//删除操作
function delObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"push/adInfoDeleteById",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#adinfodg').datagrid('reload');
					$('#adinfodg').datagrid("uncheckAll");
				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
}

//删除多个操作
function delsObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"push/adInfoDeletesById",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#adinfodg').datagrid('reload');
					$('#adinfodg').datagrid("uncheckAll");
				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
}
//编辑
function editObj(id){
	$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'push/adInfoEditById/'+id,'width': 800,'height': 500}).dialog('open');
}

//查看
function detailObj(id){
	$('#detailDialog').dialog({'title':'查看DTO','href':CONTEXT+'push/adInfoDetailById/'+id,'width': 800,'height': 500}).dialog('open');
}

//选择产品
function showPro(){
	$('#proDialog').dialog({'title':'选择产品','href':CONTEXT+'push/proInitList','width': 800,'height': 500}).dialog('open');
}

//提交新增数据
function saveadd() {
	var url=CONTEXT+"push/adInfoSaveAdd";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#adinfodg").datagrid('reload');
			$('#addDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}

//提交更新数据
function saveedit() {
	var url=CONTEXT+"push/adInfoSaveEdit";
	jQuery.post(url, $('#editForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#adinfodg").datagrid('reload');
			$('#editDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}
