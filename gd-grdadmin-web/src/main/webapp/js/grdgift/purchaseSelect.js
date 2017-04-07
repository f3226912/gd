$(document).ready(function() {
	logdataLoad(null);
	$(".datagrid-header-rownumber").text("序号");
});


function logdataLoad(dataParams) {
	// 数据加载
	$('#purchaseDataGrid').datagrid({
		url : CONTEXT + 'grdPurchase/queryPurchaseSelect',
		queryParams : dataParams,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
		toolbar : '#purchaseGridToolbar',
		pageSize : 999999,
		rownumbers : true,
		//pagination : true,
		fitColumns : true,
		singleSelect:true,
		fit : true,
		onLoadSuccess : function() {
			$("#purchaseDataGrid").datagrid('clearSelections');
		},
		columns : [ [
		     		{
		     			field : 'purchaseNO',
		     			title : '采购单编号',
		     			width : 150,
		     			align : 'left'
		     		}, {
		     			field : 'marketName',
		     			title : '所属市场',
		     			width : 200,
		     			align : 'left'
		     		}, {
		     			field : 'warehouseName',
		     			title : '所属仓库',
		     			width : 200,
		     			align : 'left'
		     		}, {
		     			field : 'totalPrice',
		     			title : '订单总金额',
		     			width : 150,
		     			align : 'right',
		     			formatter:priceFormatter
		     		}, {
		     			field : 'createTime',
		     			title : '创建日期',
		     			width : 200,
		     			align : 'center'
		     		}, {
		     			field : 'status',
		     			title : '状态',
		     			width : 100,
		     			align : 'center',
		     			formatter:statusFormatter
		     		}, {
		     			field : 'purchaser',
		     			title : '采购申请人',
		     			width : 150,
		     			align : 'left'
		     		}, {
		     			field : 'remark',
		     			title : '备注',
		     			width : 300,
		     			align : 'left',
		     			formatter : remarkformat
		     		}] ]
	});
	// 分页加载
	//$("#batchDataGrid").datagrid("getPager").pagination({
		//pageList : [ 50, 100, 150, 200 ]
	//});
	}
	function priceFormatter(v,r) {
		return r.totalPrice.toFixed(2);
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
	function remarkformat(value, row, index) {
		var remark = row.remark;
		if (remark && remark.length > 30) {
			remark = remark.substring(0, 30) + "...";
		}
		return remark;
	}

	// 重置,手动清空
	$('#btn-selectReset').click(function() {
		 $("#purchaseWarehouseId").val("");
		 $("#purchaseMarketId").val("");
	});
	
	/*
	function gotoInstock(purchaseNO) {
		$('#purchaseSelectDialog').dialog('destroy');
		$('#purchaseSelectDialog').dialog({
			'title' : '礼品入库',
			'href' : CONTEXT + 'grdPurchase/initInstock/'+purchaseNO,
			"width" : 900,
			"height" :500
		}).dialog('open');
	}

	// 下一步按钮
	$('#nextPurchaseBtn').click(function() {
		
	});*/
	
	// 查询按钮
	$('#icon-selectSearch').click(function() {
		var dataParams = {
			marketId : $('#purchaseGridSearchForm #purchaseMarketId').val(),
			warehouseId : $('#purchaseGridSearchForm #purchaseWarehouseId').val()
		};
		logdataLoad(dataParams);
	});
