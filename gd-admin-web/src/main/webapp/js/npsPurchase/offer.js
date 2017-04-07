$(function(){
	initList2();
})

//初始化加载页面列表
function initList2(){
	loadList2(null);
}


//加载列表数据
function loadList2(params){
	params = !params ? {}: params;
	params.purchaseId=purchaseId;
	//数据加载
	$('#datagrid-table2').datagrid({
		url:CONTEXT+'npsOfferPrice/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar2',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table2").datagrid('clearSelections');
			disableExport2 = false;
		},
		columns:[[
		    {field:'id',title:'id',align:'center',hidden:true},
			{field:'mobilePhone',title:'手机号码',align:'center'},
			{field:'purchaseId',title:'询价编号',align:'center'},
			{field:'offerPriceStr',title:'上车价',align:'center'},
			{field:'purchaseUnit',title:'单位',align:'center'},
			{field:'offerTime',title:'报价时间',align:'center'},
			{field:'status',title:'报价状态',align:'center',formatter:statusFormatter2},
			{field : 'opt',title : '操作',align : 'center',formatter : optformat2}
		]]
	}); 
	$("#datagrid-table2").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
	initOpt();
}

function initOpt() {
	if (type=="2") {
		$('#datagrid-table2').datagrid("hideColumn", "opt");
	}
}
function statusFormatter2(value,row,index){
	var status="";
	if(value=="1"){
		status="显示";
	}
	if(value=="2"){
		status="已删除";	
	}
	if(value=="3"){
		status="隐藏";
	}
	return status;
}

function optformat2(value, row, index) {
	var str = "<a class='operate' href='javascript:;'";
	html =str;
	if(row.status=="1"){
		html+=" onclick=save2('"+ row.id + "',3,'"+ row.purchaseId + "');>";
	}else{
		html+=" style='color:gray;'>";;
	}
	html+="隐藏</a>&nbsp";
	
	html += str;
	if(row.status=="3"){
		html += " onclick=save2('"+ row.id + "',1,'"+ row.purchaseId + "');>";
	}else{
		html+=" style='color:gray;'>";;
	}
	html+="显示</a>&nbsp";
	return html;
}
/**
 * 此方法用来控制在修改全部报价页面的数据时，如果修改的是编辑页面的报价数据，需要将编辑页面的报价数据状态更新
 * @param id
 * @param status
 * @param purchaseId
 */
function showEditInfo(id,status,purchaseId){
	var showOfferId = $("#showOfferId").html();
	if(showOfferId==id){
		if(status==1){
			$("#linkShow").removeAttr("onclick").css("color","gray");
			$("#linkHide").attr("onclick","updateOfferStatus("+id+",3,"+purchaseId+")").removeAttr("style");
			$("#spStatus").html("显示");
		}
		else if(status==3){
			$("#linkHide").removeAttr("onclick").css("color","gray");
			$("#linkShow").attr("onclick","updateOfferStatus("+id+",1,"+purchaseId+")").removeAttr("style");
			$("#spStatus").html("隐藏");
		}
	}
}
function save2(id,status,purchaseId) {
		var url = CONTEXT + "npsPurchase/updateOffer";
		jQuery.post(url, {"id":id,"status":status,"purchaseId":purchaseId}, function(data) {
			if (data.msg == "success") {
				slideMessage("保存成功！");
				initList2();
				showEditInfo(id,status,purchaseId);
			} else {
				warningMessage(data.msg);
				return;
			}
		});
}


/***数据导出功能***/
$("#exportData2")
		.click(
				function() {

					var params = {			
						"purchaseId" : purchaseId	
					};
					var paramList = gudeng.commons
							.convertParamsToDelimitedList(params);
					$.ajax({
							url : CONTEXT+ 'npsPurchase/checkExportParams2',
							data : params,
							type : 'post',
							success : function(data) {
								//检测通过
								if (data && data.status == 1) {
									if (!disableExport2) {
										slideMessage("数据正在导出中, 请耐心等待...");
										disableExport2 = true;
										//启动下载
										$.download(
														CONTEXT
																+ 'npsPurchase/exportData2',
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


