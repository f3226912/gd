//初始化加载页面列表
function initList(){
	loadList(null);
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'grdProMemberInvitedRegister/query',
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
		  /*  {field:'id',title:'id',width:50,checkbox:true},*/
			{field:'marketName',title:'所属市场',width:100,align:'left',halign:'center'},
			{field:'grdUserName',title:'地推姓名',width:100,align:'left',halign:'center'},
			{field:'grdMobile',title:'地推手机',width:100,align:'center',halign:'center'},
			{field:'type',title:'业务类型',width:100,align:'center',halign:'center',formatter:function(v,d,i){
				if(v == 1){
					return "农批";
				}else if(v == 2){
					return "农速通";
				}else{
					return "--";
				}
			}},
			{field:'account',title:'会员账号',width:100,align:'center',halign:'center'},
			{field:'regetypeName',title:'注册来源',width:100,align:'left',halign:'center'},
			{field:'realName',title:'会员姓名',width:100,align:'left',halign:'center'},
			{field:'memberMobile',title:'会员手机号码',width:100,align:'center',halign:'center'},
			{field:'registerTime',title:'注册时间',width:120,align:'center',halign:'center'}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

function query(){
	var marketId = $('#datagrid-form #marketId').val();
	var teamGroup = $('#datagrid-form #teamGroup').val();
	var grdMobile = $('#datagrid-form #grdMobile').val();
	var memberMobile = $('#datagrid-form #memberMobile').val();
	var grdUserName = $('#datagrid-form #grdUserName').val();
	var regetype = $('#datagrid-form #regetype').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var type = $('#datagrid-form #type').val();
	var params = {
		"marketId" : marketId,
		"teamId" : teamGroup,
		"grdMobile" : grdMobile,
		"memberMobile" : memberMobile,
		"grdUserName" : grdUserName,
		"regetype" : regetype,
		"startDate" : startDate,
		"endDate" : endDate,
		"type" : type
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
	resetCustom() ;
}
function resetCustom(){
	$('#marketId').combobox('select', "");
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
          href : CONTEXT+'grdProMemberInvitedRegister/beforeSave',
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
		var url = CONTEXT + "grdProMemberInvitedRegister/save";
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
      href : CONTEXT+'grdProMemberInvitedRegister/edit/'+id,
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
      href : CONTEXT+'grdProMemberInvitedRegister/view/'+id,
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
			jQuery.post(CONTEXT + "grdProMemberInvitedRegister/delete", {
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

function loadUserTeams(marketId){
	$.ajax( {
	    url:CONTEXT+'commons/userteams',
	    data : {"marketId" : marketId},
	    type:'post',
	    dataType:'json',
	    success:function(data) {
	    	var option = "";
	    	var select =  $("#teamGroup");
	    	select.children("option").remove();
	    	for(item in data){
    			option = $("<option value=''></option>");
    			option.val(data[item].keyString);
    			option.text(data[item].valueString);
    			option.appendTo(select);
    		}
	     },
	     error : function(data) {
	    	 warningMessage(data);
	     }
	});
}
/*
$('#marketId').combobox({
	valueField:'keyString',
	textField:'valueString',
	url: CONTEXT+'commons/marketPairs',
	editable:false,
	onSelect:function(record){
		var marketId = record.keyString ;
		loadUserTeams(marketId);
		$('#marketId').val(marketId);
	},
	onLoadSuccess : function(){
		var data = $('#marketId').combobox('getData');
		$('#marketId').combobox('select', data[0].keyString);
	}
});*/
