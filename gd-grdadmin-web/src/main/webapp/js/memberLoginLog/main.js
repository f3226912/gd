//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'memberLoginLog/query',
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
			{field:'id',title:'',width:50,checkbox:true},
			{field:'memberId',title:'用户Id',width:100,align:'center'},
			{field:'account',title:'账号',width:100,align:'center'},
			{field:'mobile',title:'手机号',width:100,align:'center'},
			{field:'realName',title:'姓名',width:100,align:'center'},
//			{field:'loginType',title:'登录来源',width:100,align:'center',formatter : formatterType},
			{field:'createTime',title:'登录时间',width:100,align:'center'},
			{field:'ip',title:'ip',width:100,align:'center'},
			{field:'description',title:'说明',width:100,align:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
}
function formatterType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "web主站";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		} else if(str=="4"){
			return "农批商";
		} else if(str=="5"){
			return "供应商";
		}
	}
}

function query(){
	var id = $('#datagrid-form #id').val();
	var createTime = $('#datagrid-form #createTime').val();
	var description = $('#datagrid-form #description').val();
	var account = $('#datagrid-form #account').val();
	var realName = $('#datagrid-form #realName').val();
	var memberId = $('#datagrid-form #memberId').val();
	var loginType = $('#datagrid-form #loginType').val();
	var ip = $('#datagrid-form #ip').val();
	var mobile = $('#datagrid-form #mobile').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var params = {
		"id" : id,
		"createTime" : createTime,
		"description" : description,
		"account" : account,
		"realName" : realName,
		"memberId" : memberId,
		"loginType" : loginType,
		"ip" : ip,
		"startDate" : $("#startDate").val(),
		"endDate" : $("#endDate").val(),
		"mobile" : mobile
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
          href : CONTEXT+'memberLoginLog/beforeSave',
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
		deleteObj(getSelections("id"));
	});
});

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "memberLoginLog/save";
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
      href : CONTEXT+'memberLoginLog/edit/'+id,
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
      href : CONTEXT+'memberLoginLog/view/'+id,
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
			jQuery.post(CONTEXT + "memberLoginLog/delete", {
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
