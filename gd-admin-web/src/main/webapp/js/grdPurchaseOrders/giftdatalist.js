/*************************************初始化下拉数据***************************************************/
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: CONTEXT +'market/queryByType/'+ marketType,
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
initMarket(2);


$("#marketId").change(function(){
	var marketid = $(this).val();
	if(marketid){
		$.ajax({
				 type: 'POST',
			     url:CONTEXT+'grdGiftIssued/getChildStoreInfo/'+marketid ,
			     dataType:'json',
			     success: function(data) {
			    	 $("#giftstoreId").html("");
			    	 $("#giftstoreId").append("<option value=''>全部</option>");
			    	 $.each(data.rows, function(index, item){
			    		$("#giftstoreId").append("<option value='"+item.giftstoreId+"'>"+item.giftstoreName+"</option");
			    	 });
			     }
			});
	}else{
		 $("#giftstoreId").html("");
    	 $("#giftstoreId").append("<option value=''>全部</option>");
	}
});


/*************************************初始化加载页面列表***************************************************/
initList();
//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#purchase_datagrid-table").datagrid("getPager").pagination({
		pageList: [20,50,100,200,300,500]
	});
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#purchase_datagrid-table').datagrid({
		url:CONTEXT+'grdGdGiftData/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#purchase_datagrid-tool-bar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#purchase_datagrid-table").datagrid('clearSelections');
		},
		columns:[[
		    {field:'giftstoreName',title:'仓库名称',width:100,align:'left',halign:'center'},
			{field:'marketName',title:'所属市场',width:100,align:'left',halign:'center'},
			{field:'giftNO',title:'礼品编码',width:100,align:'center',halign:'center'},
			{field:'giftname',title:'礼品名称',width:100,align:'left',halign:'center'},
			{field:'unit',title:'礼品单位',width:100,align:'left',halign:'center'},
			{field:'inCount',title:'库存数量',width:100,align:'right',halign:'center'},
			{field:'newPriceStr',title:'最新单价',width:100,align:'right',halign:'center'},
			{field:'priceStr',title:'货值',width:100,align:'right',halign:'center'}
			//{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
}
var disableExport = false ;
/*************************************查询***************************************************/
$("#btn-search").click(function(){
		var giftNo = $("#giftNO").val();
		var reg = new RegExp("^[0-9]*$");
		 if(!reg.test(giftNo)){
			 slideMessage("请输入数字。");
		        return false;
		    }
var params = {
		"marketId":$("#marketId").val(),
		"giftstoreId":$("#giftstoreId").val(),
		"giftNo":$("#giftNO").val(),
		"giftName":$("#giftname").val()
	};
loadList(params);
disableExport = false ;
});

$('#btn-reset').click(function(){
	$('#marketId').val(0);
	$('#giftstoreId').val(0);
	$('#giftNO').val("");
	$('#giftname').val("");
});

/*************************************下载***************************************************/

	
	$("#exportData").click(function(){
		var params = {
				"marketId":$("#marketId").val(),
				"giftstoreId":$("#giftstoreId").val(),
				"giftNo":$("#giftNO").val(),
				"giftName":$("#giftname").val()
			};
		var paramList = gudeng.commons.convertParamsToDelimitedList(params);
		if (!disableExport){
			slideMessage("数据正在导出中, 请耐心等待...");
			disableExport = true ;
			//启动下载
			$.download(CONTEXT+'grdGdGiftData/exportData',paramList,'post' );
		}else{
			slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
		}


	});
	
	
	




