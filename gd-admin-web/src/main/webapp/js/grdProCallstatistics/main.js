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
		url:CONTEXT+'grdProCallstatistics/query',
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
			{field:'source',title:'拨打来源',width:100,align:'left',halign:'center'},
			{field:'callRole',title:'APP来源',width:100,align:'left',halign:'center'},
			{field:'e_Name',title:'主叫姓名',width:100,align:'left',halign:'center'},
			{field:'e_Mobile',title:'主叫手机',width:100,align:'center',halign:'center'},
			{field:'grdUserName',title:'地推姓名',width:100,align:'left',halign:'center'},
			{field:'marketName',title:'主叫人所属市场',width:100,align:'left',halign:'center'},
			{field:'serviceType',title:'主叫业务范围',width:100,align:'center',halign:'center'},
			{field:'s_Name',title:'被叫姓名',width:100,align:'left',halign:'center'},
			{field:'s_Mobile',title:'被叫手机',width:100,align:'center',halign:'center'},
			{field:'callTime',title:'拨打时间',width:130,align:'center',halign:'center'}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function query(){
	var marketId = $('#datagrid-form #marketId').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var grdUserName = $('#datagrid-form #grdUserName').val();
	var serviceType = $('#datagrid-form #serviceType').val();
	var source = $('#datagrid-form #source').val();
	var s_Mobile = $('#datagrid-form #s_Mobile').val();
	var callRole = $('#datagrid-form #callRole').val();
	var s_Name = $('#datagrid-form #s_Name').val();
	var e_Mobile = $('#datagrid-form #e_Mobile').val();
	var e_Name = $('#datagrid-form #e_Name').val();
	var params = {
		"marketId" : marketId,
		"startDate" : startDate,
		"endDate" : endDate,
		"grdUserName" : grdUserName,
		"serviceType" : serviceType,
		"source" : source,
		"s_Mobile" : s_Mobile,
		"callRole" : callRole,
		"s_Name" : s_Name,
		"e_Mobile" : e_Mobile,
		"e_Name" : e_Name
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

$(function(){
	initList();
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
	$('#btn-add').click(function() {
		$('<div></div>').dialog({
          id : 'saveDialog',
          title : '新增数据',
          width : 800,
          height : 450,
          closed : false,
          cache : false,
          href : CONTEXT+'grdProCallstatistics/beforeSave',
          modal : true,
          onLoad : function() {
              //初始化表单数据
          },
          onClose : function() {
              $(this).dialog('destroy');
          },
          buttons : [ {
              text : '保存',
              iconCls : 'icon-save',
              handler : function() {
            	  save();
                  return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#saveDialog").dialog('destroy');
              }
          } ],
      });
	});
	
	// 修改
	$('#btn-edit').click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("createUserId"));
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
		deleteObj(getSelections("createUserId"));
	});
});

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "grdProCallstatistics/save";
		jQuery.post(url, $('#save-form').serialize(), function(data) {
			if (data.msg == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
				$('#saveDialog').dialog('close');
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
function edit(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '修改数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'grdProCallstatistics/edit/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '保存',
          iconCls : 'icon-save',
          handler : function() {
        	  save();
              return false; // 阻止表单自动提交事件
          }
      }, {
          text : '取消',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
}
/**查看操作
 * @param id 当前对象ID
 */
function view(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '查看数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'grdProCallstatistics/view/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确定',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
}

// 删除操作
function deleteObj(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdProCallstatistics/delete", {
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
