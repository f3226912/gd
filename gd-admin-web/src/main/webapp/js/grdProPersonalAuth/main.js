var disableExport = false;

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
		url:CONTEXT+'grdProPersonalAuth/query',
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
			{field:'account',title:'会员账号',width:100,align:'center',halign:'center'},
			{field:'regetypeName',title:'注册来源',width:100,align:'left',halign:'center'},
			{field:'realName',title:'会员姓名',width:100,align:'left',halign:'center'},
			{field:'memberMobile',title:'会员手机号码',width:100,align:'center',halign:'center'},
			{field:'status',title:'个人认证状态',width:100,align:'left',formatter:formatterStatus,halign:'center'},
			{field:'applyTime',title:'认证申请时间',width:100,align:'center',halign:'center'},
			{field:'auditTime',title:'审核时间',width:100,align:'center',halign:'center'},
			{field:'auditor',title:'审核人',width:100,align:'left',halign:'center'}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

function formatterStatus(val,row,index){
	if (val == "0"){
		return "待认证" ;
	}else if (val == "1"){
		return "已认证" ;
	}else if (val == "2"){
		return "已驳回" ;
	}
}
function query(){
	var marketId = $('#datagrid-form #marketId').val();
	var teamId = $('#datagrid-form #teamGroup').val();
	var grdUserName = $('#datagrid-form #grdUserName').val();
	var grdMobile = $('#datagrid-form #grdMobile').val();
	var memberMobile = $('#datagrid-form #memberMobile').val();
	var status = $('#datagrid-form #status').val();
	var applyStartDate = $('#datagrid-form #applyStartDate').val();
	var applyEndDate = $('#datagrid-form #applyEndDate').val();
	var auditStartDate = $('#datagrid-form #auditStartDate').val();
	var auditEndDate = $('#datagrid-form #auditEndDate').val();
	var params = {
		"marketId" : marketId,
		"teamId" : teamId,
		"grdUserName" : grdUserName,
		"grdMobile" : grdMobile,
		"memberMobile" : memberMobile,
		"status" : status,
		"applyStartDate" : applyStartDate,
		"applyEndDate" : applyEndDate,
		"auditStartDate" : auditStartDate,
		"auditEndDate" : auditEndDate
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
	initMarket(2);
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
	// 数据导出功能
	$("#exportData").click(function() {
		exportData();
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
          href : CONTEXT+'grdProPersonalAuth/beforeSave',
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
		var url = CONTEXT + "grdProPersonalAuth/save";
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
      href : CONTEXT+'grdProPersonalAuth/edit/'+id,
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
      href : CONTEXT+'grdProPersonalAuth/view/'+id,
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
			jQuery.post(CONTEXT + "grdProPersonalAuth/delete", {
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
		gudeng.biStatPersonalAuth.loadUserTeams(marketId);
		$('#marketId').val(marketId);
	},
	onLoadSuccess : function(){
		var data = $('#marketId').combobox('getData');
		$('#marketId').combobox('select', data[0].keyString);
	}
});*/
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url:CONTEXT + "market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId').empty();
				$('#marketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}


function exportData() {

	var marketId = $('#datagrid-form #marketId').val();
	//var teamId = $('#datagrid-form #teamGroup').val();
	var grdUserName = $('#datagrid-form #grdUserName')
			.val();
	var grdMobile = $('#datagrid-form #grdMobile').val();
	var status = $('#datagrid-form #status').val();
	var applyStartDate = $('#datagrid-form #applyStartDate')
			.val();
	var applyEndDate = $('#datagrid-form #applyEndDate')
			.val();
	var auditStartDate = $('#datagrid-form #auditStartDate')
			.val();
	var auditEndDate = $('#datagrid-form #auditEndDate')
			.val();

	var params = {
		"marketId" : marketId,
		//"teamId" : teamId,
		"grdUserName" : grdUserName,
		"grdMobile" : grdMobile,
		"status" : status,
		"applyStartDate" : applyStartDate,
		"applyEndDate" : applyEndDate,
		"auditStartDate" : auditStartDate,
		"auditEndDate" : auditEndDate
	};
	var paramList = 'marketId=' + params.marketId + '&grdUserName=' + params.grdUserName
	+ '&grdMobile=' + params.grdMobile + '&status=' + params.status 
	+ '&applyStartDate=' + params.applyStartDate + '&applyEndDate=' + params.applyEndDate + '&auditStartDate=' + params.auditStartDate 
	+ '&auditEndDate=' + params.auditEndDate
//	+'&teamId=' + params.teamId +
	;
	
	$.ajax({
				url : CONTEXT + 'grdProPersonalAuth/checkExportParams',
				data : params,
				type : 'post',
				success : function(data) {
					// 检测通过
					if (data && data.status == 1) {
						/* $("#Loading2").show(); */
						if (!disableExport) {
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true;
							// 启动下载
							jQuery.download(CONTEXT + 'grdProPersonalAuth/exportData',
									paramList, 'post');
						} else {
							slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
						}
					} else {
						warningMessage(data.message);
					}
				},
				error : function(data) {
					warningMessage("error : " + data);
				}
			});
}

jQuery.download = function(url, data, method) {
	// 获得url和data
	if (url && data) {
		// data 是 string或者 array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		// 把参数组装成 form的 input
		var inputs = '';
		jQuery.each(data.split('&'), function() {
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="'
					+ pair[1] + '" />';
		});
		// request发送请求
		jQuery(
				'<form action="' + url + '" method="' + (method || 'post')
						+ '">' + inputs + '</form>').appendTo('body').submit()
				.remove();
	}
	;
};

function optformat(value, row, index) {
	var html = "";
	html += "<a class='operate' href='javascript:;' onclick=view('"
			+ row.id + "');>查看</a>";
	html += "<a class='operate' href='javascript:;' onclick=edit('"
			+ row.id + "');>修改</a>&nbsp;";
	html += "<a class='operate' href='javascript:;' onclick=delete('"
			+ row.id + "');>删除</a>";
	return html;
}