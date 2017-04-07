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
getGiftSum(null);

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
		url:CONTEXT+'giftInStorageData/query',
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
		    {field:'purchaseNO',title:'采购单号',width:100,align:'center',halign:'center'},
			{field:'inStorageId',title:'入库单号',width:100,align:'center',halign:'center'},
			{field:'createTime',title:'入库时间',width:120,align:'center',halign:'center',formatter:fmtTime},
			{field:'marketName',title:'所属市场',width:120,align:'left',halign:'center'},
			{field:'giftstoreName',title:'仓库名称',width:100,align:'left',halign:'center'},
			{field:'giftNO',title:'礼品编码',width:100,align:'center',halign:'center'},
			{field:'giftname',title:'礼品名称',width:100,align:'left',halign:'center'},
			{field:'unit',title:'礼品单位',width:100,align:'left',halign:'center'},
			{field:'inCount',title:'入库数量',width:100,align:'right',halign:'center'}
			//{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
}
var disableExport = false ;

function fmtTime(value,row){
	return row.createTime.substring(0,19);

};
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
		"giftName":$("#giftname").val(),
		"inStorageStartDate":$("#inStorageStartDate").val(),
		"inStorageEndDate":$("#inStorageEndDate").val()
	};

getGiftSum(params);
loadList(params);
disableExport = false ;
});
function getGiftSum(data){
	var params=data;
	if(!params){
		params={"type":"1"};
	}else{
		params["type"]=1;
	}
    $.ajax('getGrdGdGiftDataSum/query',
        {
            type: 'post',
            dataType: 'json',
            cache: false,
            data:params,
           
            success: function (data) {
            $("#giftSum").html(data.giftSum);
            }
          
        });

}
//重置按钮
$('#btn-reset').click(function(){
		$('#marketId').val(0);
		$('#giftstoreId').val(0);
		$('#inStorageEndDate').val("");
		$('#inStorageStartDate').val("");
		$('#giftNO').val("");
		$('#giftname').val("");
	});

/*************************************下载***************************************************/

	
	$("#exportData").click(function(){
		var marketId = $('#marketId').val();
		var params = {
				"marketId":$("#marketId").val(),
				"giftstoreId":$("#giftstoreId").val(),
				"giftNo":$("#giftNO").val(),
				"giftName":$("#giftname").val(),
				"inStorageStartDate":$("#inStorageStartDate").val(),
				"inStorageEndDate":$("#inStorageEndDate").val()
			};
		var paramList = gudeng.commons.convertParamsToDelimitedList(params);
		if (!disableExport){
			slideMessage("数据正在导出中, 请耐心等待...");
			disableExport = true ;
			//启动下载
			$.download(CONTEXT+'giftInStorageData/exportData',paramList,'post' );
		}else{
			slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击查询按钮...");
		}


	});
	
	
	




