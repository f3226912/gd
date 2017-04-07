gudeng.goodsHandout = {};

//初始化加载页面列表
gudeng.goodsHandout.initList = function(){
	gudeng.goodsHandout.loadList(null);
	//分页加载
}
//加载列表数据
gudeng.goodsHandout.loadList = function(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'grdProSupplyofgoodHandout/query',
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
	        /*{field:'id',title:'',width:50,checkbox:true},*/
	        {field:'marketName',title:'所属市场',width:100,align:'left',halign:'center'},
			{field:'grdUserName',title:'地推姓名',width:100,align:'left',halign:'center'},
			{field:'grdMobile',title:'地推手机',width:100,align:'center',halign:'center'},		          
			{field:'goodsId',title:'货源ID',width:100,align:'center',halign:'center'},
			{field:'publisher',title:'发布人姓名',width:100,align:'left',halign:'center'},
			{field:'mobile',title:'发布人手机',width:100,align:'center',halign:'center'},
			{field:'publisherTime',title:'发布时间',width:100,align:'center',halign:'center'},
			{field:'status',title:'货源状态',width:100,align:'left',formatter:gudeng.goodsHandout.formatterStatus,halign:'center'}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

gudeng.goodsHandout.formatterStatus = function(val,row,index){
	var result;
	if ((val == "1" || val=="2")&&(row.isDeleted=="0")){
		result="已发布 " ;
	}else if (val == "3"&&(row.isDeleted=="0")){
		result="已接单" ;
	}else if (val == "4"&&(row.isDeleted=="0")){
		result="已过期" ;
	}
	if(row.isDeleted=="1"){
		result="已删除" ;
	}
	return result;
}
gudeng.goodsHandout.query = function(){
	var marketId = $('#datagrid-form #marketId').val();
	var teamId = $('#datagrid-form #teamGroup').val();
	var grdUserName = $('#datagrid-form #grdUserName').val();
	var grdMobile = $('#datagrid-form #grdMobile').val();
	var status = $('#datagrid-form #status').val();
	var isDeleted;
	if(status&&status=="1"){
		isDeleted=status;
		status="";
	};
	var publisher = $('#datagrid-form #publisher').val();
	var mobile = $('#datagrid-form #mobile').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var params = {
		"marketId" : marketId,
		"teamId" : teamId,
		"grdUserName" : grdUserName,
		"grdMobile" : grdMobile,
		"status" : status,
		"publisher" : publisher,
		"mobile" : mobile,
		"startDate" : startDate,
		"endDate" : endDate,
		"isDeleted":isDeleted
	};
	gudeng.goodsHandout.loadList(params);
	//重启导出功能
	disableExport = false ;
}

gudeng.goodsHandout.reset = function(){
	$('#datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
	gudeng.goodsHandout.resetCustom();
}

gudeng.goodsHandout.resetCustom = function(){
	$('#marketId').combobox('select', '');
}

gudeng.goodsHandout.refresh = function(){
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
}

$(function(){
	gudeng.goodsHandout.initList();
	//查询按钮
	$('#btn-search').click(function(){
		gudeng.goodsHandout.query();
	});
	
	//刷新按钮
	$('#btn-refresh').click(function(){
		gudeng.goodsHandout.refresh();
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		gudeng.goodsHandout.reset();
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
          href : CONTEXT+'grdProSupplyofgoodHandout/beforeSave',
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
            	  gudeng.goodsHandout.save();
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
		gudeng.goodsHandout.deleteObj(getSelections("id"));
	});
});

gudeng.goodsHandout.save = function() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "grdProSupplyofgoodHandout/save";
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
gudeng.goodsHandout.edit = function(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '修改数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'grdProSupplyofgoodHandout/edit/'+id,
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
        	  gudeng.goodsHandout.save();
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
gudeng.goodsHandout.view = function(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '查看数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'grdProSupplyofgoodHandout/view/'+id,
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
gudeng.goodsHandout.deleteObj = function(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdProSupplyofgoodHandout/delete", {
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

gudeng.goodsHandout.loadUserTeams = function(marketId){
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
		gudeng.goodsHandout.loadUserTeams(marketId);
		$('#marketId').val(marketId);
	},
	onLoadSuccess : function(){
		var data = $('#marketId').combobox('getData');
		$('#marketId').combobox('select', data[0].keyString);
	}
});*/
