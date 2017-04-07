var disableExport = false;
// 初始化加载页面列表
function initList() {
	loadList(null);
	// 分页加载
}
// 加载列表数据
function loadList(params) {
	params = !params ? {} : params;
	// 数据加载
	$('#datagrid-table').datagrid({
		url : CONTEXT + 'grdGdGift/query',
		width : 'auto',
		queryParams : params,
		height : 'auto',
		nowrap : true,
		toolbar : '#datagrid-tool-bar',
		pageSize : 20,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		singleSelect : true,
		onLoadSuccess : function() {
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'id',
			title : '',
			width : 50,
			checkbox : true
		}, {
			field : 'giftNo',
			title : '礼品编号',
			width : 100,
			align : 'left',
			halign:'center',
			formatter : formatterformatterEdit
		}, {
			field : 'name',
			title : '礼品名称',
			width : 150,
			align : 'left',
			halign:'center',
			formatter : formatterformatterEdit
		}, {
			field : 'unit',
			title : '单位',
			width : 100,
			align : 'center'
		}, {
			field : 'spec',
			title : '规格',
			width : 100,
			align : 'left',
			halign:'center',
		}, {
			field : 'riseCount',
			title : '起订量',
			width : 60,
			align : 'right',
			halign:'center',
		}, {
			field : 'supplyCycle',
			title : '供货周期',
			width : 60,
			align : 'left',
			halign:'center',
		}, {
			field : 'rePrice',
			title : '参考价',
			width : 60,
			align : 'right',
			halign:'center',
			formatter : formatterRePrice
		}, {
			field : 'newPrice',
			title : '最新价格',
			width : 60,
			align : 'right',
			halign:'center',
			formatter : formatterNewPrice
		}, ] ]
	});
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList : [ 10, 20, 30, 50 ]
	});
	//$('#datagrid-table').datagrid("hideColumn", "id");
	$(".datagrid-header-rownumber").text("序号");
}
// 根据权限判断是否加载数据
function initLoadDataGrid() {
	if ($('#btn-search').length > 0) {
		var options = $('#datagrid-table').datagrid('options');
		// 直接赋值url即可，无需手动去load数据，datagrid会在页面渲染的时候自动加载。
		options.url = CONTEXT + 'grdGdGift/query';
	} else {
		warningMessage("您无此功能查询权限！");
	}
};

function initNewPrice() {
	if ($('#hdNewPrice').length > 0) {
		$('#datagrid-table').datagrid("showColumn", "newPrice");
	} else {
		$('#datagrid-table').datagrid("hideColumn", "newPrice");
	}
}

function formatterformatterEdit(val, row, index) {
	var str = "";
	if (val != "") {
		str += "<a href='javaScript:edit(";
		str += row.id;
		str += ")' style='text-decoration:none;'>";
		str += val;
		str += "</a>";
	}
	return str;
}

function formatterRePrice(val, row, index) {
	var rePrice = 0.00;
	rePrice = parseFloat(val);
	if (!isNaN(rePrice)) {
		return rePrice.toFixed(2);
	}
}

function formatterNewPrice(val, row, index) {
	var newPrice = 0.00;
	newPrice = parseFloat(val);
	if (!isNaN(newPrice)) {
		return newPrice.toFixed(2);
	}
}

function query() {

	var giftNo = $('#datagrid-form #giftNo').val();
	var name = $('#datagrid-form #name').val();

	var params = {
		"giftNo" : giftNo,
		"name" : name,
	};
	loadList(params);
	// 重启导出功能
	disableExport = false;
}

function reset() {
	$('#datagrid-form')[0].reset();
	// 重启导出功能
	disableExport = false;
}

function refresh() {
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	// 重启导出功能
	disableExport = false;
}

$(function() {

	initList();
	// initLoadDataGrid();
	initNewPrice();
	// 查询按钮
	$('#btn-search').click(function() {
		query();
	});

	// 刷新按钮
	$('#btn-refresh').click(function() {
		refresh();
	});

	// 重置按钮
	$('#btn-reset').click(function() {
		reset();
	});

	// 数据导出功能
	$("#exportData").click(function() {
		exportData();
	});

/*	// 新增
	$('#btn-add').click(function() {
		$('#saveDialog').dialog({
			id : 'saveDialog',
			title : '新增数据',
			width : 800,
			height : 450,
			closed : false,
			cache : false,
			href : CONTEXT + 'grdGdGift/beforeSave',
			modal : true

		}).dialog("open");
	});*/
	
	// 新增按钮  点击事件
	$("#btn-add").click(function(){
		//使用新增div的形式来绑定弹框，以防止jQuery事件的重复绑定问题
		$('<div></div>').dialog({
			id:'saveDialog',
			title:'新增数据',
			href:CONTEXT + 'grdGdGift/beforeSave',
			width: 800,
			height: 450,
            cache : false,
			resizable:true,
			modal:true,
			maximizable:true,
			onClose : function() {
	            $(this).dialog('destroy');
	         },
	         buttons : [{
                 text : '新增',
                 iconCls : 'icon-add',
                 handler : function() {
                     //此方法在弹出框页面(grd_member_editor_save)的js中定义。
                	 save();
                 }
             }, {
                 text : '关闭',
                 iconCls : 'icon-cancel',
                 handler : function() {
                     $("#saveDialog").dialog('destroy');
                 }
             }]
		});
	});

	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}

		var giftNo = "";
		for (var i = 0; i < row.length; i++) {
			if (i == row.length - 1) {
				giftNo += row[i].giftNo;
			} else {
				giftNo += row[i].giftNo+","
			}
		}
		//console.info(giftNo);
		deleteObj(getSelections("id"), giftNo);
	});
});

function save() {
	if ($('#save-form').form('validate')) {
		var url = CONTEXT + "grdGdGift/save";
		jQuery.post(url, $('#save-form').serialize(), function(data) {
			if (data.msg == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
				$('#saveDialog').dialog('close');

			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}
}

/**
 * 修改操作
 * 
 * @param id
 *            当前对象ID
 */
/*function edit(id) {
	$('#saveDialog').dialog({
		title : '礼品管理',
		width : 800,
		height : 450,
		cache : false,
		href : CONTEXT + 'grdGdGift/edit/' + id,
		modal : true
	}).dialog('open');
}*/
/**
 * 显示编辑对话框
 * @param id
 */
function edit(id){
	$('<div></div>').dialog({
		id:'saveDialog',
		title:'礼品管理',
		href:CONTEXT + 'grdGdGift/edit/' + id,
		width: 800,
		height: 450,
        cache : false,
		resizable:true,
		modal:true,
		maximizable:true,
		onClose : function() {
            $(this).dialog('destroy');
         }
	});
}
// 删除操作
function deleteObj(id, giftNo) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdGdGift/delete", {
				"ids" : id,
				"giftNos" : giftNo
			}, function(data) {
				if (data.msg == "success") {
					slideMessage("操作成功！");
					$('#datagrid-table').datagrid('reload');
					$('#datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}

function exportData() {

	var giftNo = $('#datagrid-form #giftNo').val();
	var name = $('#datagrid-form #name').val();

	var params = {
		"giftNo" : giftNo,
		"name" : name,
	};

	$
			.ajax({
				url : CONTEXT + 'grdGdGift/checkExportParams',
				data : params,
				type : 'post',
				success : function(data) {
					// 检测通过
					if (data && data.status == 1) {
						/* $("#Loading2").show(); */
						if (!disableExport) {
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true;
							// 启动下载
							jQuery.download(CONTEXT + 'grdGdGift/exportData',
									params, 'post');
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
}

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