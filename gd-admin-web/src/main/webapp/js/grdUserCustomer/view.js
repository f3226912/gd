
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
	logdataLoad(memberId);
	$(".datagrid-header-rownumber").text("序号");
});

function logdataLoad(memberId) {
	// 数据加载
	$('#logDataGrid').datagrid({
		url : CONTEXT + 'grdUserCustomer/log/'+memberId,
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
			field : 'createUserName',
			title : '调整人姓名',
			width : 100,
			align : 'left',
			halign:'center'
		}, {
			field : 'createUserCode',
			title : '调整人账号',
			width : 130,
			align : 'left',
			halign:'center'
		}, {
			field : 'createTime',
			title : '调整时间',
			width : 170,
			align : 'center',
			halign:'center'
		}, {
			field : 'grdOldUserName',
			title : '原地推',
			width : 100,
			align : 'left',
			halign:'center'
		}, {
			field : 'grdOldUserMobile',
			title : '原地推手机',
			width : 120,
			align : 'center',
			halign:'center'
		} , {
			field : 'grdNewUserName',
			title : '新指派地推',
			width : 100,
			align : 'left',
			halign:'center'
		}, {
			field : 'grdNewUserMobile',
			title : '新指派地推手机',
			width : 120,
			align : 'center',
			halign:'center'
		}  ] ]
	});
	
	// 分页加载
	/*$("#logDataGrid").datagrid("getPager").pagination({
		pageList : [ 50, 100, 150, 200 ]
	});
	*/
}

