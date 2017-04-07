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
		url:CONTEXT+'npsPurchase/query',
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
		    {field:'id',title:'编号',width:50,align:'right',halign:'center'},
		    {field:'mobilePhone',title:'手机号码',width:100,align:'center'},
		    {field:'realName',title:'姓名',width:80,align:'left',halign:'center'},
		    {field:'marketName',title:'所属市场',width:120,align:'left',halign:'center'},
		    {field:'shopsName',title:'商铺名称',width:100,align:'left',halign:'center'},
		    {field:'goodsName',title:'商品名称',width:100,align:'left',halign:'center'},
		    {field:'createTime',title:'提交时间',width:110,align:'center'},
		    {field:'releaseTime',title:'发布时间',width:110,align:'center'},
		    {field:'status',title:'状态',width:60,align:'left',halign:'center',formatter:statusFormat},
		    {field:'endTime',title:'结束时间',width:110,align:'center'},
		    {field:'visitCount',title:'累计被访问次数',width:100,align:'right',halign:'center'},
		    {field:'priceCount',title:'累计报价数',width:80,align:'right',halign:'center'},
		    //{field:'authUserId',title:'审核员',width:100,align:'left',halign:'center'},
			{field : 'opt',title : '操作',width : 80,align : 'center',formatter : optformat}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}


$("#chooseMarket").click(function(){
	  // 动态创建新增页面(动态销毁)
	  $('<div></div>').dialog({
      id : 'marketSelectDialog',
      title : '选择市场',
      width : 600,
      height : 350,
      closed : false,
      cache : false,
      href : CONTEXT+'npsPurchase/toMarketSelect', 
      modal : true,
      onLoad : function() {
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      onOpen : function() {
      }
  });		
});

function statusFormat(val,row,idx){
	var status="";
	if(val=="1"){
		status="待审核";
	}
	if(val=="2"){
		status="审核通过";
	}
	if(val=="3"){
		status="已驳回";
	}
	if(val=="4"){
		status="用户撤销";
	}
	//注释代码，不会有已关闭的情况
	/*if(val=="5"){
		status="已关闭";
	}*/
	if(val=="6"){
		status="已结束";
	}
	return status;
}

function query(){
	var id = $('#datagrid-form #id').val();
	var mobilePhone = $('#datagrid-form #mobilePhone').val();
	var goodsName = $('#datagrid-form #goodsName').val();
	var status = $('#datagrid-form #status').val();
	var releaseTimeStart = $('#datagrid-form #releaseTimeStart').val();
	var releaseTimeEnd = $('#datagrid-form #releaseTimeEnd').val();
	var createTimeStart = $('#datagrid-form #createTimeStart').val();
	var createTimeEnd = $('#datagrid-form #createTimeEnd').val();
	var marketName = $('#datagrid-form #chooseMarket').val();
	var params = {			
		"status" : status,		
		"createTimeStart" : createTimeStart,		
		"createTimeEnd" : createTimeEnd,		
		"id" : id,	
		"mobilePhone" : mobilePhone,
		"releaseTimeStart" : releaseTimeStart,
		"releaseTimeEnd" : releaseTimeEnd,
		"goodsName" : goodsName,
		"marketName":marketName
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

});

function save3() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "npsPurchase/save";
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

function save(){
	var status=$("input[type='radio']:checked").val();
	var reason=$("#rejectReason");
	if(status=="3"){
		var val=$.trim(reason.val());
		if(val.length==0){
			reason.validatebox({    
			    required: true    
			}); 
			reason.focus();
			return false;
		}
	}
	save3();
}


/**修改操作
 * @param id 当前对象ID
 */
function edit(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '询价信息审核',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'npsPurchase/edit/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '提交',
          iconCls : 'icon-save',
          handler : function() {
        	  save();
              return false; // 阻止表单自动提交事件
          }
      }, {
          text : '关闭',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
	$(".window-header").css("border-bottom", "1px solid #d3d3d3");
}
/**查看操作
 * @param id 当前对象ID
 */
function view(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '查看询价数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'npsPurchase/view/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '关闭',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
	$(".window-header").css("border-bottom", "1px solid #d3d3d3");
}



function optformat(value, row, index) {
	var html = "";
	if(row.status=="1"){
		var check=$("#hdCheck").val();
		if(check){
			html += "<a class='operate' href='javascript:;' onclick=edit('"+ row.id + "');>审核</a>&nbsp;"
		};
	}
	var view=$("#hdView").val();
	if(view){
		html += "<a class='operate' href='javascript:;' onclick=view('"+ row.id + "');>查看</a>";
	}
	return html;
}
var disableExport = false;

/***数据导出功能***/
$("#exportData")
		.click(
				function() {
					var id = $('#datagrid-form #id').val();
					var mobilePhone = $('#datagrid-form #mobilePhone').val();
					var goodsName = $('#datagrid-form #goodsName').val();
					var status = $('#datagrid-form #status').val();
					var releaseTimeStart = $('#datagrid-form #releaseTimeStart').val();
					var releaseTimeEnd = $('#datagrid-form #releaseTimeEnd').val();
					var createTimeStart = $('#datagrid-form #createTimeStart').val();
					var createTimeEnd = $('#datagrid-form #createTimeEnd').val();
					var marketName = $('#datagrid-form #chooseMarket').val();
					var params = {			
						"status" : status,		
						"createTimeStart" : createTimeStart,		
						"createTimeEnd" : createTimeEnd,		
						"id" : id,	
						"mobilePhone" : mobilePhone,
						"releaseTimeStart" : releaseTimeStart,
						"releaseTimeEnd" : releaseTimeEnd,
						"goodsName" : goodsName,
						"marketName":marketName
					};
					var paramList = gudeng.commons
							.convertParamsToDelimitedList(params);
					$
							.ajax({
								url : CONTEXT
										+ 'npsPurchase/checkExportParams',
								data : params,
								type : 'post',
								success : function(data) {
									//检测通过
									if (data && data.status == 1) {
										/* $("#Loading2").show(); */
										if (!disableExport) {
											slideMessage("数据正在导出中, 请耐心等待...");
											disableExport = true;
											//启动下载
											$
													.download(
															CONTEXT
																	+ 'npsPurchase/exportData',
															paramList,
															'post');
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
				});



