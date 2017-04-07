
$(document).ready(function(){
	//数据加载
	$('#marketdg').datagrid({
		url:CONTEXT+'market/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#markettb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#marketdg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:100,checkbox:true},
			{field:'marketName',title:'街市名称',width:100,align:'center'},
			{field:'provinceName',title:'省份',width:100,align:'center'},
			{field:'cityName',title:'城市',width:100,align:'center'},
			{field:'areaName',title:'区域',width:100,align:'center'},
			{field:'address',title:'详细地址',width:100,align:'center'},
			{field:'marketTypeName',title:'街市类型',width:100,align:'center'},
			{field:'useStatus',title:'状态',width:100,align:'center'},
			{field:'description',title:'备注',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
	//分页加载
	$("#marketdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


function save() {
	var url=CONTEXT+"market/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#marketdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage(data);
				return;
			}
		});
}

// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
    var queryParams = $('#marketdg').datagrid('options').queryParams;
	queryParams.queryName = $('#marketSearchForm #queryName').val();
	queryParams.queryType = $('#marketSearchForm #queryType').val();

	var queryUrl=CONTEXT+'market/queryByName';
	$('#marketdg').datagrid({
		url:queryUrl,
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#markettb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		{field:'id',title:'',width:100,checkbox:true},
		{field:'marketName',title:'街市名称',width:100,align:'center'},
		{field:'provinceName',title:'省份',width:100,align:'center'},
		{field:'cityName',title:'城市',width:100,align:'center'},
		{field:'areaName',title:'区域',width:100,align:'center'},
		{field:'address',title:'详细地址',width:100,align:'center'},
		{field:'marketTypeName',title:'街市类型',width:100,align:'center'},
		{field:'useStatus',title:'状态',width:100,align:'center'},
		{field:'description',title:'备注',width:100,align:'center'},
		{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
			]]
	}); 
	//分页加载
	$("#marketdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
});
	
//删除操作
$("#icon-remove").click(function(){
	var row = $('#marketdg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }
    
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		var deleteStr = getSelections("id");
    		jQuery.post(CONTEXT+"market/deletebyid",{"id":deleteStr},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#marketdg').datagrid('reload');
					$('#marketdg').datagrid("uncheckAll");

				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
});

//编辑
$("#icon-edit").click(function(){
	var row = $('#marketdg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }
    var marketId = getSelections("id");
	if(marketId.indexOf(",")>0){
    	warningMessage("请不要操作多条数据！");
	    return;
	}
	$('#addDialog').dialog({'title':'编辑', 'width': 500,    
	    'height': 380, 'href':CONTEXT+'market/edit?id='+marketId}).dialog('open');
});
	
//删除操作
function delObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"market/deletebyid",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#marketdg').datagrid('reload');
					$('#marketdg').datagrid("uncheckAll");
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
 
//新增
$('#icon-add').click(function(){
	$('#addDialog').dialog({'title':'新增街市','width': 500,    
	    'height': 300, 'href':CONTEXT+'market/addDto'}).dialog('open');
});

//重置
$('#btn-reset').click(function(){
	$('#marketSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#marketSearchForm')[0].reset();
	$("#marketdg").datagrid('load', {'queryName':'', 'queryType':''});
});

function locObj(id){
	var locChange = art.dialog.open(CONTEXT+'market/loc?marketId=' + id,{
		id : 'locMarket',
		title:'定位',
		lock : true,
		ok: function () {
			//获得对话框内document
			var doc = $(this.iframe.contentWindow.document);
	    	//获取修改数据
	    	var lat = doc.find("#lat").val();
	    	var lng = doc.find("#lng").val();
	    	var marketID = doc.find("#marketID").val();
			//发送搜搜
	    	$.ajax({url: CONTEXT+"market/changeLoc?t="+new Date(),
				data:{
						"id":id,
						"lat":lat,
						"lon":lng
					},
				type: 'POST',
				error: function(e){
					jQuery.dialog({
					    content: "操作失败，请联系系统管理员！",
					    time:3,
					    id:"dropInner"
					});
				},
				success: function(result){
					result = eval("("+result+")");
					if(result.code=="1") {
						jQuery.dialog({
						    content: "定位成功",
						    time:2,
						    id:"dropInner"
						});
						locChange.close();
					} else {
						jQuery.dialog({
						    content: "操作失败，请联系系统管理员！",
						    time:3,
						    id:"dropInner"
						});
					}
				}});
	    		return false;
		    },
		    button: [{
		    	name: "回定位点",
				callback: function(e) {
					this.iframe.contentWindow.backPoint();
					return false;
				}	
				}],
		    width:'570px',
		    height:'435px',
		    cancelVal: '关闭',
		    cancel:true
			});
}