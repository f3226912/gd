var disableExport = false;
$(document).ready(function() {
	// 数据加载
	$('#carsdg').datagrid({
		url : CONTEXT + 'enPostLog/query',
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
			field : 'orderno',
			title : '订单号',
			width : 100,
			align : 'center'
		}, {
			field : 'transype',
			title : '交易类型',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 0) {
					return "订单支付";
				} else if (v == 1) {
					return "刷卡消费";
				} 
			}
		}, {
			field : 'machinenum',
			title : 'POS终端号',
			width : 100,
			align : 'center'
		}, {
			field : 'payfee',
			title : '支付金额',
			width : 100,
			align : 'center'
		}, {
			field : 'paycardno',
			title : '付款卡号',
			width : 100,
			align : 'center'
		}, {
			field : 'transdate',
			title : '交易日期',
			width : 100,
			align : 'center'
		}, {
			field : 'transtime',
			title : '交易时间',
			width : 100,
			align : 'center'
		}, {
			field : 'createtime',
			title : '生成时间',
			width : 100,
			align : 'center'
		}, {
			field : 'transseqno',
			title : '交易流水号',
			width : 100,
			align : 'center'
		}, {
			field : 'state',
			title : '状态',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "请求原文";
				} else if (v == 2) {
					return "请求成功";
				} else if (v == 3) {
					return "请求失败(无对应数据)";
				}
			}
		},
		{field:'iD',title:'操作',width : 100,align:'center',formatter : function(v, d, i) {
				return "<div style='text-align:center;height:auto;' class='datagrid-cell datagrid-cell-c1-opt'>"+
				"<a class='operate' href='javascript:;' onclick='enPostLogDetail("+v+")'>详情</a>&nbsp;</div>";
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
	var queryUrl = CONTEXT + 'enPostLog/query';
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
			transseqno : $('#carsSearchForm #transseqno').val(),
			startDate : $('#carsSearchForm #queryStartDate').val(),
			endDate : $('#carsSearchForm #queryEndDate').val(),
			state : $('#carsSearchForm #state').val()
		},
		fit : true,
		columns : [ [  {
			field : 'orderno',
			title : '订单号',
			width : 100,
			align : 'center'
		}, {
			field : 'transype',
			title : '交易类型',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
					if (v == 0) {
						return "订单支付";
					} else if (v == 1) {
						return "刷卡消费";
					} 
				}	
		}, {
			field : 'machinenum',
			title : 'POS终端号',
			width : 100,
			align : 'center'
		}, {
			field : 'payfee',
			title : '支付金额',
			width : 100,
			align : 'center'
		}, {
			field : 'paycardno',
			title : '付款卡号',
			width : 100,
			align : 'center'
		}, {
			field : 'transdate',
			title : '交易日期',
			width : 100,
			align : 'center'
		}, {
			field : 'transtime',
			title : '交易时间',
			width : 100,
			align : 'center'
		}, {
			field : 'createtime',
			title : '生成时间',
			width : 100,
			align : 'center'
		}, {
			field : 'transseqno',
			title : '交易流水号',
			width : 100,
			align : 'center'
		}, {
			field : 'state',
			title : '状态',
			width : 100,
			align : 'center',
			formatter : function(v, d, i) {
				if (v == 1) {
					return "请求原文";
				} else if (v == 2) {
					return "请求成功";
				} else if (v == 3) {
					return "请求失败(无对应数据)";
				}
			}
		},{field:'iD',title:'操作',width : 100,align:'center',
			formatter : function(v, d, i) {
				return "<div style='text-align:center;height:auto;' class='datagrid-cell datagrid-cell-c1-opt'>"+
				"<a class='operate' href='javascript:;' onclick='enPostLogDetail("+v+")'>详情</a>&nbsp;</div>";
			}
		} ] ]
	});
	// 分页加载
	$("#carsdg").datagrid("getPager").pagination({
		pageList : [ 10, 20, 50, 100 ]
	});
});


function enPostLogDetail(enPostLogId){
	$('#detailDialog').dialog({'title':'查看日志详情','href':CONTEXT+'enPostLog/enPostLogDetail/'+enPostLogId,'width': 800,'height': 500}).dialog('open');
}

// 数据导出
$("#exportData")
		.click(
				function() {
					var queryParams = {
						transseqno : $('#carsSearchForm #transseqno').val() == null
								|| $('#carsSearchForm #transseqno').val() == undefined ? ""
								: $('#carsSearchForm #transseqno').val(),
						startDate : $('#carsSearchForm #queryStartDate').val(),
						endDate : $('#carsSearchForm #queryEndDate').val(),
						state : $('#carsSearchForm #state').val()
					};
					var paramList = "transseqno=" + queryParams.transseqno
							+ "&startDate=" + queryParams.startDate
							+ "&endDate=" + queryParams.endDate + "&state="
							+ queryParams.state;
					$.ajax({
								url : CONTEXT + 'enPostLog/checkExportParams',
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
													+ 'enPostLog/exportData',
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