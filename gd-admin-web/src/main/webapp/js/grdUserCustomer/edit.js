
$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});

$(document).ready(function() {
	logdataLoad(null);
	$(".datagrid-header-rownumber").text("序号");
});


function logdataLoad(dataParams) {
	// 数据加载
	$('#assignMemberDataGrid').datagrid({
		url : CONTEXT + 'grdUserCustomer/queryGrdMember',
		queryParams : dataParams,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
		toolbar : '#assignMemberGridToolbar',
		pageSize : 20,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		singleSelect:true,
		fit : true,
		onLoadSuccess : function() {
			$(this).datagrid("fixRownumber");
			$("#assignMemberDataGrid").datagrid('clearSelections');
		},
		columns : [ [
		            {field:'id',title:'id',hidden:true,width:120,align:'left'},
		     		{
		     			field : 'name',
		     			title : '地推人员',
		     			width : 100,
		     			align : 'left',
		     			halign:'center'
		     		}, {
		     			field : 'mobile',
		     			title : '地推人员手机',
		     			width : 100,
		     			align : 'left',
		     			halign:'center'
		     		},{
		     			field : 'giftteamName',
		     			title : '所属团队',
		     			width : 180,
		     			align : 'left',
		     			halign:'center'
		     		},{
		     			field : 'market',
		     			title : '所属市场',
		     			width : 150,
		     			align : 'left',
		     			halign:'center'
		     		}, {
		     			field : 'status',
		     			title : '状态',
		     			width : 70,
		     			align : 'left',
		     			halign:'center',
		     			formatter:statusFormatter
		     		}, {
		     			field : 'context',
		     			title : '备注',
		     			width : 200,
		     			align : 'left',
		     			halign:'center',
		     			formatter : contextformat
		     		}] ]
	});
	// 分页加载
	$("#assignMemberDataGrid").datagrid("getPager").pagination({
		pageList : [ 10, 20, 50, 100 ]
	});
	}
	
	function statusFormatter(v,r) {
		if(r.status=='1'){
			return '启用';
		}else if(r.status=='0'){
			return '未启用';
		}else{
			return '未知';
		}
	}
	function contextformat(value, row, index) {
		var context = row.context;
		if (context && context.length > 30) {
			context = context.substring(0, 30) + "...";
		}
		return context;
	}

	// 重置,手动清空
	$('#btn-assignReset').click(function() {
		$('#assignMemberGridSearchForm')[0].reset();
	});
	
	
	// 查询按钮
	$('#icon-assignSearch').click(function() {
		var dataParams = {
			marketId : $('#assignMemberGridSearchForm #assignMemberMarketId').val(),
			grdUserName : $('#assignMemberGridSearchForm #assignMemberGrdUserName').val(),
			grdMobile : $('#assignMemberGridSearchForm #assignMemberGrdMobile').val()
		};
		logdataLoad(dataParams);
	});
