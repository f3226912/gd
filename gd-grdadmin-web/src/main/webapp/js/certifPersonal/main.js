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
		url:CONTEXT+'certifPersonal/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns:[[
//			{field:'id',title:'',width:50,checkbox:true},
			{field:'account',title:'用户账号',width:100,align:'center'},
			{field:'realName',title:'姓名',width:100,align:'center'},
			{field:'commitTime',title:'申请时间',width:100,align:'center'},
			{field:'statusStr',title:'认证状态',width:100,align:'center' },
			{field:'optionUser',title:'审核员',width:100,align:'center' },
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

function query(){
	
	var status = $('#datagrid-form #status').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var account = $('#datagrid-form #account').val();
	var realName = $('#datagrid-form #realName').val();
	
	var params = {
		"status" : status,
		"startDate" : startDate,
		"endDate" : endDate,
		"account" : account,
		"realName" : realName
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#datagrid-form')[0].reset();
	var data = $('#status').combobox('getData');
	$('#status').combobox('select', data[0].valueString);
	//重启导出功能
	disableExport = false ;
}

function refresh(){
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	
	//认证状态默认选择全部
	var data = $('#status').combobox('getData');
	$('#status').combobox('select', data[0].valueString);
	
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
          href : CONTEXT+'certifPersonal/beforeSave',
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
		deleteObject(getSelections("id"));
	});
});

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "certifPersonal/save";
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
      href : CONTEXT+'certifPersonal/edit/'+id,
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
      href : CONTEXT+'certifPersonal/view/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '通过',
          iconCls : 'icon-save',
          handler : function() {
        	  updateStatus(id,1,1)
          }
       }, {
           text : '驳回',
           iconCls : 'icon-back',
           handler : function() {
        	   unpassShow(id,1);
           }
        }, {
              text : '关闭',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#saveDialog").dialog('destroy');
                  $("#editAdSpaceDialog").dialog('destroy');
              }
       } ],
  });
}


function updateStatus(id,status,type){
	jQuery.post(CONTEXT+"certifPersonal/updateStatus/"+id+"-"+ 1 +"-"+1,{"id":id},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#datagrid-table').datagrid('reload');
			$('#datagrid-table').datagrid("uncheckAll");
            $("#saveDialog").dialog('destroy');
		}else{
			warningMessage(data);
			return;
		}
	});
	
}


function unpassShow(id,type){
	$('<div></div>').dialog({
      id : 'logDialog',
      title : '请填写驳回原因',
      width : 500,
      height : 300,
      closed : false,
      cache : false,
      href : CONTEXT+'certifPersonal/unpassShow/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '驳回',
          iconCls : 'icon-back',
          handler : function() {
        	  auditUnpass();
          }
       }, {
              text : '关闭',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#logDialog").dialog('destroy');
                  $("#editAdSpaceDialog").dialog('destroy');
              }
       } ],
  });
}



function auditUnpass() {
	if(!$("#auditUnpassForm").form('validate')){
		return;
	}
	var url=CONTEXT+"certifPersonal/auditUnpass";
	jQuery.post(url, $('#auditUnpassForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$('#datagrid-table').datagrid('reload');
			$('#datagrid-table').datagrid("uncheckAll");
            $("#saveDialog").dialog('destroy');
            $("#logDialog").dialog('destroy');
		} else {
			warningMessage(data);
			return;
		}
	});
}

// 删除操作
function deleteObject(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "certifPersonal/delete", {
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

$('#status').combobox({
	valueField:'keyString',
	textField:'valueString',
	url: CONTEXT+'certifCommon/loadStatus',
	editable:false,
	onSelect:function(record){
		$('#status').val(record.keyString);
	},
	onLoadSuccess : function(){
		var data = $('#status').combobox('getData');
		$('#status').combobox('select', data[0].valueString);
	}
});


