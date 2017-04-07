gudeng.certif = {};
gudeng.certif.base = {};

//初始化加载页面列表
gudeng.certif.base.initList = function initList(){
	gudeng.certif.base.loadList(null);
	//分页加载
}
//加载列表数据
gudeng.certif.base.loadList = function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'certifBase/query',
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
			{field:'account',title:'用户账号',width:100,align:'center'},
			{field:'mobile',title:'电话号码',width:100,align:'center'},
			{field:'baseName',title:'基地名称',width:100,align:'center'},
			{field:'commitTime',title:'申请时间',width:100,align:'center'},
			{field:'status',title:'认证状态',width:100,align:'center', formatter : gudeng.certif.base.statusFormat},
			{field:'optionUser',title:'审核员',width:100,align:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : gudeng.certif.base.optformat}
		]]
	});
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

gudeng.certif.base.statusFormat = function statusFormat(value,row,index){
	var result ;
	if (value == 0){
		result = "待认证";
	}else if (value == 1){
		result = "已认证";
	}else if (value == 2){
		result = "已驳回";
	}
	return result;
}

gudeng.certif.base.optformat = function optformat(value,row,index){
	var html="";
	html+="<a class='operate' href='javascript:;' onclick=gudeng.certif.base.view('"+row.id+"');>查看</a>";
/* 	html+="<a class='operate' href='javascript:;' onclick=edit('"+row.id+"');>修改</a>&nbsp;";
	html+="<a class='operate' href='javascript:;' onclick=deleteItem('"+row.id+"');>删除</a>"; */
	return html;
}

gudeng.certif.base.query = function query(){
	var params = {
		"startDate" : $("#startDate").val(),
		"endDate" : $("#endDate").val(),
		"account" : $("#account").val(),
		"mobile" : $("#mobile").val(),
		"baseName" : $("#baseName").val(),
		"certifStatus" : $("#certifStatus").val()
	};
	gudeng.certif.base.loadList(params);
	//重启导出功能
	disableExport = false ;
}

gudeng.certif.base.reset = function reset(){
	$('#datagrid-form')[0].reset();
	//认证状态默认选择全部
	var data = $('#certifStatus').combobox('getData');
	$('#certifStatus').combobox('select', data[0].valueString);
	//重启导出功能
	disableExport = false ;
}

gudeng.certif.base.refresh = function refresh(){
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
}

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "certifBase/save";
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
      href : CONTEXT+'certifBase/edit/'+id,
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
gudeng.certif.base.view = function view(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '查看数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'certifBase/view/'+id,
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
        	  updateStatus(id,1,4)
          }
       }, {
           text : '驳回',
           iconCls : 'icon-back',
           handler : function() {
        	   unpassShow(id,4);
           }
        }, {
              text : '关闭',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#saveDialog").dialog('destroy');
              }
       } ]
  });
}

function updateStatus(id,status,type){

	jQuery.post(CONTEXT+"certifBase/updateStatus/"+id+"-"+ status +"-"+ type,{"id":id},function(data){
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
      href : CONTEXT+'certifBase/unpassShow/'+id,
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
              }
       } ],
  });
}



function auditUnpass() {
	if(!$("#auditUnpassForm").form('validate')){
		return;
	}
	var url=CONTEXT+"certifBase/auditUnpass";
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
function deleteItem(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "certifBase/delete", {
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

