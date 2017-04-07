$(document).ready(function() {
	logdataLoad(giftId,giftNo,marketId,giftstoreId);
	$(".datagrid-header-rownumber").text("序号");
});

function logdataLoad(id,giftNo,marketId,giftstoreId) {
	// 数据加载
	$('#logDataGrid').datagrid({
		url : CONTEXT + 'grdgift/log/'+id,
		queryParams : null,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
//		toolbar : '#dataGridToolbar',
		pageSize : 999999,
		rownumbers : true,
//		pagination : true,
		fitColumns : true,
		singleSelect:true,
		fit : true,
		onLoadSuccess : function() {
			$("#logDataGrid").datagrid('clearSelections');
		},
		columns : [ [
		{
			field : 'createUserId',
			title : '调整人',
			width : 100,
			align : 'left'
		}, {
			field : 'giftstoreName',
			title : '所属仓库',
			width : 130,
			align : 'left'
		}, {
			field : 'createTime',
			title : '调整时间',
			width : 200,
			align : 'center'
		}, {
			field : 'orignValue',
			title : '原库存',
			width : 100,
			align : 'right'
		}, {
			field : 'realValue',
			title : '调整后库存',
			width : 100,
			align : 'right'
		} , {
			field : 'reason',
			title : '原因',
			width : 500,
			align : 'right'
		} ] ]
	});
	// 数据加载
	/*$('#batchDataGrid').datagrid({
		url : CONTEXT + 'grdPurchase/getPurchaseBatch/'+giftNo+'/'+marketId+'/'+giftstoreId,
		queryParams : null,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
//		toolbar : '#dataGridToolbar',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		singleSelect:true,
		fit : true,
		onLoadSuccess : function() {
			$("#batchDataGrid").datagrid('clearSelections');
		},
		columns : [ [
		     		{
		     			field : 'purchaseNO',
		     			title : '采购单编码',
		     			width : 100,
		     			align : 'center'
		     		}, {
		     			field : 'unitPrice',
		     			title : '单价',
		     			width : 200,
		     			align : 'center',
		     			formatter:priceFormatter
		     		}, {
		     			field : 'createTime',
		     			title : '创建日期',
		     			width : 100,
		     			align : 'center',
		     			formatter:fmtTime
		     		}, {
		     			field : 'remark',
		     			title : '备注',
		     			width : 100,
		     			align : 'center',
		     			formatter : remarkformat
		     		}  ] ]
	});
	// 分页加载
	$("#batchDataGrid").datagrid("getPager").pagination({
		pageList : [ 50, 100, 150, 200 ]
	});
	*/
	

}
function priceFormatter(v,r) {
	return r.unitPrice.toFixed(2);
}
function remarkformat(value, row, index) {
	var remark = row.remark;
	if (remark && remark.length > 30) {
		remark = remark.substring(0, 30) + "...";
	}
	return remark;
}

function fmtTime(value,row){
	return row.createTime.substring(0,10);

};

