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
		url:CONTEXT+'bankInformation/query',
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
		singleSelect:false,
		onLoadSuccess:function(){
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'全选',width:80,checkbox:true},
            {field:'bankName',title:'发卡行',width:100,align:'center'},
            {field:'bankCode',title:'机构代码',width:100,align:'center'},
            {field:'cardName',title:'卡名',width:100,align:'center'},
			{field:'cardType',title:'卡种',width:100,align:'center'},
			{field:'cardLength',title:'长度',width:100,align:'center'},	
			{field:'bankSignId',title:'发卡行标识',width:100,align:'center'},
			{field:'bankShortName',title:'发卡行简称',width:100,align:'center'},		
			{field:'bankLogo',title:'LOGO',width:100,align:'center',formatter:logoFormat},	
			{field:'status',title:'状态',width:100,align:'center',formatter:statusFormat},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
	//$(".datagrid-header-rownumber").text("序号");
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

function logoFormat(value,row,index){
	return value?"<image src="+imgHostUrl+value+" width='35px;' height='25px;' />":"";
}

//function cardTypeFormat(value, row, index) {
//	var html = "";
//	switch(value){
//	case "1":
//		html="借记卡";
//		break;
//	case "2":
//		html="货记卡";
//		break;
//	case "3":
//		html="预付费卡";
//		break;
//	case "4":
//		html="准货记卡";
//		break;
//	default:
//		break;
//	}
//	return html;
//}

function statusFormat(value, row, index) {
	var html = "";
	switch(value){
	case "1":
		html="启用";
		break;
	case"0":
		html="关闭" ;
	    break;
	default:
		break;
	}
	return html;
}

function query(){
	var bankSignId = $('#datagrid-form #bankSignId').val();
	var id = $('#datagrid-form #id').val();
	var cardName = $('#datagrid-form #cardName').val();
	var status = $('#datagrid-form #status').val();
	var bankLogo = $('#datagrid-form #bankLogo').val();
	var bankShortName = $('#datagrid-form #bankShortName').val();
	var bankName = $('#datagrid-form #bankName').val();
	var cardLength = $('#datagrid-form #cardLength').val();
	var cardType = $('#datagrid-form #cardType').val();
	var bankCode = $('#datagrid-form #bankCode').val();
	var params = {
		"bankSignId" : bankSignId,
		"id" : id,
		"cardName" : cardName,
		"status" : status,
		"bankLogo" : bankLogo,
		"bankShortName" : bankShortName,
		"bankName" : bankName,
		"cardLength" : cardLength,
		"cardType" : cardType,
		"bankCode" : bankCode
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
	importBankTip();
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
          title : '新增银行卡信息',
          width : 800,
          height : 450,
          closed : false,
          cache : false,
          href : CONTEXT+'bankInformation/beforeSave',
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
	
	$("#btn-using").click(function(){
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		editObj(getSelections("id"),1);
	})
	
	
	$("#btn-close").click(function(){
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		editObj(getSelections("id"),0);
	})
	
	
	$("#btn-excel").click(function(){
		$("#cardFile").click().bind("change",function(){
			var fileName=$(this).val();
			$.ajax({
				url:CONTEXT+'bankInformation/checkImportParams',
				data:{"fileName":fileName},
				type : 'post',
				success : function(data) {
					if (data && data.status == 1){
						ajaxLoading();
						$("#importForm").submit();
					}else{
						slideMessage(data.message);
					}
				}
			})
		})
		//$('#excelDialog').dialog({'title':'银行信息导入','width': 300,'height': 200}).dialog('open');
	})
	
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

function importExcel(){
	$("#importForm").submit();

}

function ajaxLoading(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "bankInformation/save";
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
      title : '修改银行卡信息',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'bankInformation/edit/'+id,
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
      title : '查看银行卡信息',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'bankInformation/view/'+id,
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
			jQuery.post(CONTEXT + "bankInformation/delete", {
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

//修改状态操作
function editObj(id,status) {
	jQuery.messager.confirm('提示', '您确定要修改所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "bankInformation/updateStatus/"+status, {
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

function importBankTip(){
	var msg=$("#hdMsg").val();
	if(msg&&msg.length>0){
		warningMessage(msg);
		$("#hdMsg").val("");
	}
}
