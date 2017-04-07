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
		url:CONTEXT+'npsOfferPrice/query',
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
			//{field:'account',title:'用户账号',width:100,align:'center'},
			{field:'id',title:'报价编号',width:100,align:'right',halign:'center'},
			{field:'mobilePhone',title:'手机号码',width:100,align:'center'},
			{field:'realName',title:'姓名',width:100,align:'left',halign:'center'},
			{field:'goodsName',title:'商品名称',width:100,align:'left',halign:'center'},
			{field:'offerPriceStr',title:'报价(上车价)',width:100,align:'right',halign:'center'},
			{field:'purchaseUnit',title:'单位',width:100,align:'left',halign:'center'},
			
			{field:'offerTime',title:'报价时间',width:120,align:'center'},
			//{field:'purchaseStatus',title:'询价状态',width:100,align:'center',formatter:purchaseStatusFormatter},
			{field:'status',title:'报价状态',width:80,align:'center',formatter:statusFormatter},
			//{field:'purchaseEndTime',title:'询价结束时间',width:100,align:'center'},
			{field:'purchaseId',title:'询价信息编号',width:100,align:'right',halign:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

function statusFormatter(value, row, index){
	var html="";
	switch (value){
		case "1":
				html="显示";
			break;
		case "2":
				html="已删除";
			break;
		case "3":
				html="隐藏";
			break;	
	}
	return html;
}

function purchaseStatusFormatter(value, row, index){
	var html="";
	switch (value){
		case "1":
			html="待审核";
			break;
		case "2":
			html="审核通过";
			break;
		case "3":
			html="已驳回";
			break;
		case "4":
			html="用户撤销";
			break;
		//确认没有已关闭状态
		/*case "5":
			html="已关闭";
			*/
		case "6":
			html="已结束";
			break;	
	}
	return html;
}
function query(){
	var mobilePhone = $('#datagrid-form #mobilePhone').val();
	var purchaseId = $('#datagrid-form #purchaseId').val();
	var goodsName = $('#datagrid-form #goodsName').val();
	var purchaseStatus = $('#datagrid-form #purchaseStatus option:selected').val();
	var offerTimeStart = $('#datagrid-form #offerTimeStart').val();
	var offerTimeEnd = $('#datagrid-form #offerTimeEnd').val();
	var status = $('#datagrid-form #status').val();

	
	var params = {
		"mobilePhone" : mobilePhone,
		"purchaseId" : purchaseId,
		"goodsName" : goodsName,
		"purchaseStatus" : purchaseStatus,
		"offerTimeStart" : offerTimeStart,
		"offerTimeEnd" : offerTimeEnd,
		"status" : status
	};
	loadList(params);
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


/**修改操作
 * @param id 当前对象ID
 */
function edit(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '报价信息编辑',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'npsOfferPrice/edit/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
          $('#datagrid-table').datagrid("reload");
      },
      buttons : [ {
          text : '关闭',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
              $('#datagrid-table').datagrid("reload");
          }
      } ],
  });
	$(".window-header").css("border-bottom", "1px solid #d3d3d3");
}

function optformat(value, row, index) {
	var html = "";
	var edit=$("#hdEdit").val();
	if(edit){
		html += "<a class='operate' href='javascript:;' onclick=edit('"
				+ row.id + "');>编辑</a>&nbsp;";
	}
	return html;
}

/***数据导出功能***/
$("#exportData")
		.click(
				function() {
					var mobilePhone = $('#datagrid-form #mobilePhone').val();
					var purchaseId = $('#datagrid-form #purchaseId').val();
					var goodsName = $('#datagrid-form #goodsName').val();
					var purchaseStatus = $('#datagrid-form #purchaseStatus option:selected').val();
					var offerTimeStart = $('#datagrid-form #offerTimeStart').val();
					var offerTimeEnd = $('#datagrid-form #offerTimeEnd').val();
					var status = $('#datagrid-form #status').val();

					
					var params = {
						"mobilePhone" : mobilePhone,
						"purchaseId" : purchaseId,
						"goodsName" : goodsName,
						"purchaseStatus" : purchaseStatus,
						"offerTimeStart" : offerTimeStart,
						"offerTimeEnd" : offerTimeEnd,
						"status" : status
					};
					var paramList = gudeng.commons
							.convertParamsToDelimitedList(params);
					$.ajax({
								url : CONTEXT
										+ 'npsOfferPrice/checkExportParams',
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
																	+ 'npsOfferPrice/exportData',
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
