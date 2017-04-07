//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'grdGdGiftteam/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns:[[
            {field:'id',title:'序号',width:50,checkbox:true},
			{field:'marketName',title:'所属市场',width:100,align:'left',halign:'center'},
			{field:'name',title:'团队名称',width:50,align:'left',halign:'center',formatter:formatterName},
			{field:'giftStoreName',title:'所属仓库',width:100,align:'left',halign:'center'},
			{field:'remarks',title:'备注',width:100,align:'left',halign:'center',formatter:formatterRemarks}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
	$(".datagrid-header-rownumber").text("序号");
}

function formatterName(val,row,index) {
	var str ="";
	if (val != "") {
		str += "<a href='javaScript:edit(";
		str += row.id;
		str += ")' style='text-decoration:none;'>" ;
		str += val;
		str += "</a>";
	}
	return str;
}
//查询
function query(){
	
	var marketId = $('#datagrid-form #marketName').val();
	var name = $('#datagrid-form #teamName').val();
	var giftstoreId = $('#datagrid-form #giftstoreName').val();

	var params = {
		"marketId" : marketId,
		"name" : name,
		"giftstoreId" : giftstoreId
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

function refresh(){
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
}

//根据权限判断是否加载数据
function initLoadDataGrid(){
	if($('#btn-search').length > 0) {
		var options = $('#datagrid-table').datagrid('options');
		//直接赋值url即可，无需手动去load数据，datagrid会在页面渲染的时候自动加载。
		options.url = CONTEXT+'grdGdGiftteam/query';
	} else {
		warningMessage("您无此功能查询权限！");
	}
};
$(function(){
	initList();
	//根据权限判断是否加载数据
	//initLoadDataGrid();
	$("#datagrid-form #marketName").change(function(){
		queryGiftstore();
	})
	//查询按钮
	$('#btn-search').click(function(){
		query();
	});
	
	//刷新按钮
	$('#btn-refresh').click(function(){
		refresh();
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
	});
	
	// 新增
	/*$('#btn-add').click(function() {
		$('#saveDialog').dialog({
          id : 'saveDialog',
          title : '新增数据',
          width : 800,
          height : 380,
          closed : false,
          cache : false,
          href : CONTEXT+'grdGdGiftteam/beforeSave',
          modal : true  
      }).dialog("open");
	});*/
	
	
	// 新增按钮  点击事件
	$("#btn-add").click(function(){
		//使用新增div的形式来绑定弹框，以防止jQuery事件的重复绑定问题
		$('<div></div>').dialog({
			id:'saveDialog',
			title:'新增数据',
			href:CONTEXT+'grdGdGiftteam/beforeSave',
			width: 800,
			height: 380,
            cache : false,
			resizable:true,
			modal:true,
			maximizable:true,
			onClose : function() {
	            $(this).dialog('destroy');
	         },
	         buttons : [{
                 text : '新增',
                 iconCls : 'icon-add',
                 handler : function() {
                     //此方法在弹出框页面(grd_member_editor_save)的js中定义。
                	 save();
                 }
             }, {
                 text : '关闭',
                 iconCls : 'icon-cancel',
                 handler : function() {
                     $("#saveDialog").dialog('destroy');
                 }
             }]
		});
	});
	
	// 修改
	$('#btn-edit').click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("id"));
		} else if($(row).length > 1){
			warningMessage("请不要选择多条数据！");
			return false;
		}
	});
	
	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		deleteObj(getSelections("id"));
	});
});

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "grdGdGiftteam/save";
		jQuery.post(url, $('#save-form').serialize(), function(data) {
			
			if (data.msg == "success") {
				
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
			    $("#saveDialog").dialog('destroy');
			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}
}



/**修改操作
 * @param id 当前对象ID
 */
/*function edit(id){
	$('#saveDialog').dialog({
      title : '地推团队管理',
      width : 800,
      height : 380,
      cache : false,
      href : CONTEXT+'grdGdGiftteam/edit/'+id,
      modal : true
  }).dialog('open');
}*/

/**
 * 显示编辑对话框
 * @param id
 */
function edit(id){
	$('<div></div>').dialog({
		id:'saveDialog',
		title:'地推团队管理',
		href:CONTEXT+'grdGdGiftteam/edit/'+id,
		width: 800,
		height: 380,
        cache : false,
		resizable:true,
		modal:true,
		maximizable:true,
		onClose : function() {
            $(this).dialog('destroy');
         }
	});
}
// 删除操作
function deleteObj(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdGdGiftteam/delete", {
				"ids" : id
			}, function(data) {
				if (data.msg == "success") {
					slideMessage("操作成功！");
					$('#datagrid-table').datagrid('reload');
					$('#datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}

//获取关联仓库
function queryGiftstore(){
	var marketId=$("#marketName option:selected").val();
	$.ajax({
		url: CONTEXT+'grdGdGiftteam/queryGiftstore',
		data: {"marketId":marketId,"timestamp":new Date().getMilliseconds()},
		type: 'POST',
		dataType:"json",
		success: function(data) {
			$("#giftstoreName").empty();
			$("#giftstoreName").append("<option value=''>请选择</option>");
			if(data){
				$.each(data.rows,function(index,row){
					$("#giftstoreName").append("<option value='"+row.id+"'>"+row.name+"</option>");
				})
			}
		},
		error: function(data){
			warningMessage(data);
		}
	});
}

//格式化地推人员备注
function formatterRemarks(val,row,index) {
	var resultStr = "";
		if (val) {
			resultStr += "<div style='width:178;overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>";
			resultStr +=  val;
			resultStr += "</div>";
		}
	return resultStr;
}