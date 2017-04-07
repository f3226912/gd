var disableExport = false ;
$(function(){
	$("#datagrid").datagrid({
		url : CONTEXT+'grdProMemberInvitedOrder/query',
		title:"",						//表格标题
		iconCls:"icon-search",			//表格图标
		nowrap: false,					//是否只显示一行，即文本过多是否省略部分。
		striped: true,					//显示斑马线效果(默认false)
		nowrap: true, 					//设置信息在同一行显示(默认true)
		showHeader: true, 				//是否显示行头(默认true)
        showFooter: true, 				//是否显示行尾(默认false)
		loadMsg:"拼命加载中 请稍后...",
		fitColumns:true,				//自动展开/收缩列(默认false,在列比较少的时候设置此属性) 
		fit:true,						//datagrid的宽高自适应
		pagination:true, 				//包含分页
		pageSize: 20,
		pageList: [20,30,50,100],		//可以设置每页记录条数的列表  
		rownumbers:true,
		singleSelect:true,
		toolbar : "#tab-tools",			//工具栏
		queryParams:{"market" : null },
		frozenColumns : [[
  			//{checkbox:true},
  			{field : "market",title : "所属市场",width : 150,align : "left",halign:'center'},
  			{field : "name",title : "地推姓名",width : 100, sortable:true, align : "left",halign:'center'},
  			{field : "mobile", title : "地推手机", width : 100, align : "center",halign:'center'}
  		]],
  		columns : [[
  			{field : "orderNo", title : "订单号", width : 100, align : "center",halign:'center'},
  			{field : "orderTime", title : "交易时间", width : 120, align : "center",halign:'center'},
  			{field : "buyerMobile", title : "买家手机", width : 100, align : "center",halign:'center'},
  			{field : "buyerName", title : "买家姓名", width : 100, align : "left",halign:'center'},
  			{field : "shopsName", title : "商铺", width : 100, align : "left",halign:'center'},
  			{field : "orderPrice", title : "实付金额", width : 100, align : "right",halign:'center',formatter:formatNumber }
  			
  		]],//单选
		toolbar:[],
		onLoadSuccess:function(data){
			/*if(data.result == "error"){
				$.messager.show({title:"提示信息",msg:data.msg,timeout:3000});
			}*/
		},onLoadError:function(data){
			$.messager.alert("提示信息","请求服务器失败!");
		}
	});	
	
	
	$(".datagrid-toolbar").append($("#searchbox"));
	$(".datagrid-toolbar").append($("#exp_btn"));
	
	//加载市场
	/*$('#market').combobox({
		url:CONTEXT+'marketberth/marketlist/0',
	    valueField:'id',
	    textField:'marketName',
	    onLoadSuccess:function(){
	    	var data = $('#market').combobox('getData');
	    	$("#market ").combobox('select',data[0].id);
    	}
	});*/
	
	
});

function formatNumber(val, row) {
	if (val != null) {
		return val.toFixed(2);
	}
}

function searchMarket(){
	
	$('#datagrid').datagrid('load',{
		marketId: $('#market').val(),
		name:$("#name").val(),
		mobile:$("#mobile").val(),
		orderNo:$("#orderNo").val(),
		buyerMobile:$("#buyerMobile").val(),
		shopsName:$("#shopsName").val(),
		startDate:$("#startDate").val(),
		endDate:$("#endDate").val()
	});
	disableExport=false;
}

function reloadMarket(){
	$('#search_fm')[0].reset();
	$('#datagrid').datagrid('load',{});
	//var data = $('#market').combobox('getData');
	//$("#market").combobox('select',data[0].id);
	disableExport=false;
}



/***数据导出功能***/
function exportData(){
	var maketId = $('#editMarketId').val();
	var params = {
		"marketId": $('#market').val(),
		"name":$("#name").val(),
		"mobile":$("#mobile").val(),
		"orderNo":$("#orderNo").val(),
		"buyerMobile":$("#buyerMobile").val(),
		"shopsName":$("#shopsName").val(),
		"startDate":$("#startDate").val(),
		"endDate":$("#endDate").val()
	};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url : CONTEXT + 'grdProMemberInvitedOrder/checkExportParams',
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
					$.download(CONTEXT+'grdProMemberInvitedOrder/exportData',paramList,'post' );
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
};
