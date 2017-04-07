$(document)
		.ready(
				function() {
					initList();
					// 查询按钮
					$('#icon-search').click(
							function() {

								var params;
								params = {
									"startDate" : $("#startDate").val(),
									"endDate" : $("#endDate").val(),
									"orderNo" : $("#txtOrderNo").val(),
									"orderStatus" : $(
											"#sltOrderStatus option:selected")
											.val(),
									"mobile" : $("#txtMobile").val(),
									"realName" : $("#txtRealName").val()
								};
								loadList(params);
							});

					// 刷新按钮
					$('#icon-refresh').click(function() {
						$('#orderSearchForm')[0].reset();
						initList();
						// 重启导出功能
						disableExport = false;
					});

					// 重置按钮
					$('#icon-reload').click(function() {
						$('#orderSearchForm')[0].reset();
					});

					/** *数据导出功能** */
					$("#exportData")
							.click(
									function() {
										
										var params = {
											"startDate" : $("#startDate").val(),
											"endDate" : $("#endDate").val(),
											"orderNo" : $("#txtOrderNo").val(),
											"orderStatus" : $("#sltOrderStatus option:selected").val(),
											"mobile" : $("#txtMobile").val(),
											"realName" : $("#txtRealName").val()
										};
										var paramList = "startDate="
												+ params.startDate
												+ "&endDate=" + params.endDate
												+ "&orderNo=" + params.orderNo
												+ "&orderStatus=" + params.orderStatus
												+ "&mobile=" + params.mobile
												+ "&realName=" + params.realName;

										$
												.ajax({
													url : CONTEXT
															+ 'sendnoworder/checkExportParams',
													data : params,
													type : 'post',
													success : function(data) {
														// 检测通过
														if (data
																&& data.status == 1) {

															if (!disableExport) {
																slideMessage("数据正在导出中, 请耐心等待...");
																disableExport = true;
																// 启动下载
																$
																		.download(
																				CONTEXT
																						+ 'sendnoworder/exportData',
																				paramList,
																				'post');
															} else {
																slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
															}
														} else {
															if (data.message) {
																warningMessage(data.message);
															} else {
																warningMessage("超时，请重新登录系统");
															}

														}
													},
													error : function(data) {
														warningMessage(data);
													}
												});
									});
				});
jQuery.download = function(url, data, method) {
	// 获得url和data
	if (url && data) {
		// data 是 string或者 array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		// 把参数组装成 form的 input
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
// 初始化加载页面列表
function initList() {
	loadList(null);

	// 分页加载
	$("#orderdg").datagrid("getPager").pagination({
		pageList : [ 10, 20, 50, 100 ]
	});
}

// 加载列表数据
function loadList(params) {
	params = !params ? {} : params;
	// 数据加载
	$('#orderdg').datagrid({
		url : CONTEXT + 'sendnoworder/orderQuery',
		// width: 1000,
		queryParams : params,
		height : 'auto',
		nowrap : true,
		toolbar : '#ordertb',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		singleSelect : true,
		fit : true,
		onLoadSuccess : function() {
			$("#orderdg").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'orderNo',
			title : '订单编号',
			width : fixWidth(0.13),
			align : 'center',
			formatter : orderNoformat
		}, {
			field : 'orderAmount',
			title : '订单金额',
			width : fixWidth(0.1),
			align : 'center'
		},
		// {field:'discountAmount',title:'抵扣金额',width:100,align:'center'},
		// {field:'receAmount',title:'应付金额',width:100,align:'center'},
		{
			field : 'payAmount',
			title : '实付款',
			width : fixWidth(0.1),
			align : 'center'
		}, {
			field : 'orderSourceView',
			title : '订单来源',
			width : fixWidth(0.08),
			align : 'center'
		}, {
			field : 'payTypeView',
			title : '支付方式',
			width : fixWidth(0.08),
			align : 'center'
		}, {
			field : 'account',
			title : '用户账号',
			width : fixWidth(0.08),
			align : 'center'
		},{
			field : 'buyerMobile',
			title : '手机号码',
			width : fixWidth(0.08),
			align : 'center'
		}, {
			field : 'realName',
			title : '买家姓名',
			width : fixWidth(0.08),
			align : 'center'
		}, {
			field : 'shopName',
			title : '商铺名称',
			width : fixWidth(0.08),
			align : 'center'
		}, {
			field : 'orderTime',
			title : '创建时间',
			width : fixWidth(0.1),
			align : 'center'
		}, {
			field : 'finishedTime',
			title : '成交时间',
			width : fixWidth(0.1),
			align : 'center'
		}, {
			field : 'orderStatusView',
			title : '订单状态',
			width : fixWidth(0.08),
			align : 'center'
		},
		// {field:'examineStatus',title:'审核状态',width:100,align:'center',formatter:formatExamineStatus
		// },
		{
			field : 'isFirst',
			title : '活动类型',
			width : fixWidth(0.1),
			align : 'center',
			formatter : formatIsFirst
		}, {
			field : 'opt',
			title : '操作',
			width : fixWidth(0.08),
			align : 'center',
			formatter : optformat
		} ] ]
	});
}

// 计算百分比宽度
function fixWidth(percent) {
	return document.body.clientWidth * percent; // 这里你可以自己做调整
}

function formatIsFirst(val, row) {
	if (val != null) {
		var str = val.toString();
		if (str == "1") {
			return "首单";
		}
	} else {
		return "";
	}
}

// 查看
function editObj(persaleId) {
	$('#editDialog').dialog({
		'title' : '查看订单',
		'href' : CONTEXT + 'sendnoworder/orderDetailById/' + persaleId,
		'width' : 800,
		'height' : 500
	}).dialog('open');
}
