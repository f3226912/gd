$(document).ready(function() {
	var dataParams = {
			purchaseNO : $('#instockForm #instockPurchaseNO').val()
		};
	logdataLoad(dataParams);
	$(".datagrid-header-rownumber").text("序号");
	
});


function logdataLoad(dataParams) {
	// 数据加载
	$('#instockDataGrid').datagrid({
		url : CONTEXT + 'grdPurchase/queryPurchasegiftList',
		queryParams : dataParams,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
		pageSize : 999999,
		rownumbers : true,
		//pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess : function() {
			$("#instockDataGrid").datagrid('clearSelections');
			$("#instockDataGrid").parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
			$("#instockDataGrid").parent().find("div .datagrid-header-check").append("<span>选择</span>");
			//以下代码是为了给点击行去掉点击事件，点击checkbox才触发点击事件
			var s= $("#instockDataGrid").datagrid('getPanel');
			var rows = s.find('tr.datagrid-row');
			var rows1 = s.find('tr.datagrid-row td[field!=purchaseGiftId]');
			rows1.unbind('click').bind('click',function(e){
				return false;
			 });
		},
		onSelect: function(index,row){
			if($("#stock"+row.purchaseGiftId).val() == 0){
				$("#stock"+row.purchaseGiftId).val(row.count);
			}
		},
		onUnselect: function(index,row){
			$("#stock"+row.purchaseGiftId).val(0);
		},
		columns : [ [
		     		{
		     			field : 'giftNO',
		     			title : '礼品编码',
		     			width : 150,
		     			align : 'left'
		     		}, {
		     			field : 'giftName',
		     			title : '礼品名称',
		     			width : 200,
		     			align : 'left'
		     		}, {
		     			field : 'unit',
		     			title : '单位',
		     			width : 200,
		     			align : 'center'
		     		}, {
		     			field : 'purchaseCount',
		     			title : '采购数量',
		     			width : 150,
		     			align : 'right'
		     		}, {
		     			field : 'count',
		     			title : '待入库数量',
		     			width : 150,
		     			align : 'right'
		     		}, {
		     			field : 'instockCount',
		     			title : '入库数量',
		     			width : 200,
		     			align : 'center',
		     			formatter : optformatinstockCount
		     		}, 
		     		{field:'purchaseGiftId',title:'',width:100,checkbox:true}
		     		] ]
	});
	// 分页加载
	//$("#instockDataGrid").datagrid("getPager").pagination({
		//pageList : [100, 150, 200 ]
	//});
}

function optformatinstockCount(value,row,index){
	var html='';
	html+='<input type="text" id="stock'+row.purchaseGiftId+'" class="formatstock" value="0" style="width:70px" >';
	return html;
}
function statusFormatter(v,r) {
	if(r.status=='0'){
		return '已删除';
	}else if(r.status=='1'){
		return '待入库';
	}else if(r.status=='2'){
		return '入库中';
	}else if(r.status=='3'){
		return '已关闭';
	}else{
		return '未知';
	}
}