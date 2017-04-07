var disableExport = false;
$(document).ready(function() {
	// 数据加载
	$('#carsdg').datagrid({
		url : CONTEXT + 'financeCredit/query',
		height : 'auto',
		nowrap : true,
		toolbar : '#carstb',
		pageSize : 10,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess : function() {
			$("#carsdg").datagrid('clearSelections');
		},
		columns : [ [  {
			field : 'memberAccount',
			title : '用户账号',
			width : 100,
			align : 'center'
		}, {
			field : 'marketName',
			title : '所属市场',
			width : 100,
			align : 'center'
		}, {
			field : 'orderAmount',
			title : '订单总交易额',
			width : 100,
			align : 'center'
		}, {
			field : 'userStar',
			title : '用户星级',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "一星";
				} else if (v == 2) {
					return "二星";
				} else if (v == 3) {
						return "三星";
			    } 				 else if (v == 4) {
						return "四星";
				} 				 else if (v == 5) {
						return "五星";
				}}
		}, {
			field : 'creditQuotaRange',
			title : '贷款额度',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "1——5万";
				} else if (v == 2) {
					return "10——50万";
				} else if (v == 3) {
				   return "50——100万";
			    } else if (v == 4) {
				   return "100——300万";
				}}
		}, {
			field : 'createTime',
			title : '申请时间',
			width : 100,
			align : 'center'
		},
		{field:'id',title:'操作',width : 100,align:'center',formatter : function(v, d, i) {
				return "<div style='text-align:center;height:auto;' class='datagrid-cell datagrid-cell-c1-opt'>"+
				"<a class='operate' href='javascript:;' onclick='financeCreditDetail("+v+")'>详情</a>&nbsp;</div>";
			}}
		
		
		] ]
	});
	// 分页加载
	$("#carsdg").datagrid("getPager").pagination({
		pageList : [ 10, 20, 50, 100 ]
	});

});

// 查询按钮,根据条件查询
$('#icon-search').click(function() {
	var queryUrl = CONTEXT + 'financeCredit/query';
	var endDate=$('#carsSearchForm #queryEndDate').val();
	if($('#carsSearchForm #queryEndDate').val().length>0){
		endDate =$('#carsSearchForm #queryEndDate').val()+" 23:59:59";
		}
	$('#carsdg').datagrid({
		url : queryUrl,
		// width: 1000,
		height : 'auto',
		nowrap : true,
		toolbar : '#carstb',
		pageSize : 10,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		queryParams : {
			marketId : $('#carsSearchForm #marketId').val(),
			memberAccount : $('#carsSearchForm #memberAccount').val(),
			startOrderAmount : $('#carsSearchForm #startOrderAmount').val(),
			endOrderAmount : $('#carsSearchForm #endOrderAmount').val(),
			startDate : $('#carsSearchForm #queryStartDate').val(),
			endDate :endDate,
			userStar : $('#carsSearchForm #userStar').val(),
			creditQuotaRange : $('#carsSearchForm #creditQuotaRange').val()
		},
		fit : true,
		columns : [ [  {
			field : 'memberAccount',
			title : '用户账号',
			width : 100,
			align : 'center'
		}, {
			field : 'marketName',
			title : '所属市场',
			width : 100,
			align : 'center'
		}, {
			field : 'orderAmount',
			title : '订单总交易额',
			width : 100,
			align : 'center'
		}, {
			field : 'userStar',
			title : '用户星级',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "一星";
				} else if (v == 2) {
					return "二星";
				} else if (v == 3) {
						return "三星";
			    } 				 else if (v == 4) {
						return "四星";
				} 				 else if (v == 5) {
						return "五星";
				}}
		}, {
			field : 'creditQuotaRange',
			title : '贷款额度',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "1——5万";
				} else if (v == 2) {
					return "10——50万";
				} else if (v == 3) {
				   return "50——100万";
			    } else if (v == 4) {
				   return "100——300万";
				}}
		}, {
			field : 'createTime',
			title : '申请时间',
			width : 100,
			align : 'center'
		},
		{field:'id',title:'操作',width : 100,align:'center',formatter : function(v, d, i) {
			return "<div style='text-align:center;height:auto;' class='datagrid-cell datagrid-cell-c1-opt'>"+
			"<a class='operate' href='javascript:;' onclick='financeCreditDetail("+v+")'>详情</a>&nbsp;</div>";
		}}
		] ]
	});
	// 分页加载
	$("#carsdg").datagrid("getPager").pagination({
		pageList : [ 10, 20, 50, 100 ]
	});
});


function financeCreditDetail(financeCreditId){
	$('#detailDialog').dialog({'title':'查看贷款详情','href':CONTEXT+'financeCredit/financeCreditDetail/'+financeCreditId,'width': 800,'height': 500}).dialog('open');
}

// 数据导出
$("#exportData")
		.click(
				function() {
					var queryParams = {
							marketId : $('#carsSearchForm #marketId').val(),
							memberAccount : $('#carsSearchForm #memberAccount').val(),
							startOrderAmount : $('#carsSearchForm #startOrderAmount').val(),
							endOrderAmount : $('#carsSearchForm #endOrderAmount').val(),
							startDate : $('#carsSearchForm #queryStartDate').val(),
							endDate : $('#carsSearchForm #queryEndDate').val(),
							userStar : $('#carsSearchForm #userStar').val(),
							creditQuotaRange : $('#carsSearchForm #creditQuotaRange').val()
					};
					var paramList = "marketId=" + queryParams.marketId
							+ "&memberAccount=" + queryParams.memberAccount
							+ "&startOrderAmount=" + queryParams.startOrderAmount 
							+ "&endOrderAmount="+ queryParams.endOrderAmount
							+ "&startDate="+ queryParams.startDate
							+ "&endDate="+ queryParams.endDate
							+ "&userStar="+ queryParams.userStar
							+ "&creditQuotaRange="+ queryParams.creditQuotaRange;
							
					$.ajax({
								url : CONTEXT + 'financeCredit/checkExportParams',
								data : queryParams,
								type : 'post',
								success : function(data) {
									// 检测通过
									if (data && data.status == 1) {
										if (!disableExport) {
											slideMessage("数据正在导出中, 请耐心等待...");
											disableExport = true;
											// 启动下载
											$.download(CONTEXT
													+ 'financeCredit/exportData',
													paramList, 'post');
										} else {
											slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
										}
									} else {
										warningMessage(data.message);
									}
								},
								error : function(data) {
									warningMessage(data);
								}
							});
				});

jQuery.download = function(url, data, method) {
	// 获得url和data
	if (url && data) {
		// data 是 string或者 array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		// 把参数组装成 form的  input
		var inputs = '';
		jQuery.each(data.split('&'), function() {
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="'
					+ pair[1] + '" />';
		});
		// request发送请求
		jQuery(
				'<form action="' + url + '" method="' + (method || 'post')
						+ '">' + inputs + '</form>').appendTo('body').submit()
				.remove();
	}
	;
};

//重置
$('#btn-reset').click(function() {
	$('#carsSearchForm')[0].reset();
});