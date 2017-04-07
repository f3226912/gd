<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title></title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="purchase_datagrid-table" title=""></table>
<div id="purchase_datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="purchase_datagrid-form" method="post">
	<div>
		所属市场：
		<select id="marketId" name="marketId" onchange="setWarehouse()">
		</select>&nbsp;&nbsp;
		所属仓库：
		<select id="warehouseId" name="warehouseId">
		<option value=''>全部</option>
		</select>		
		状态：
		<select id="status" name="status">
		<option value=''>全部</option>
		<option value='1'>待入库</option>
		<option value='2'>入库中</option>
		<option value='3'>已关闭</option>
		</select>&nbsp;&nbsp;		
		采购单编号：
		<input type="text" id="purchaseNO" name="purchaseNO" placeholder="请输入采购单编码" class="easyui-validatebox" style="width:150px" >
		<gd:btn btncode='BTNCGGLCX01'>
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		</gd:btn>
	</div>
	</form>
	<div style="margin-bottom:5px">
	<gd:btn btncode='BTNCGGLXZ02'>
		<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="btn-add" >新增</a>
	</gd:btn>	
	<gd:btn btncode='BTNCGGLDCCGD03'>
		<a id="exportSingleData" class="easyui-linkbutton" iconCls="icon-save">导出采购单</a>
	</gd:btn>	
	<gd:btn btncode='BTNCGGLGBCGD01'>	
		<a class="easyui-linkbutton icon-add-btn" iconCls="icon-edit" id="btn-close" >关闭采购单</a>
	</gd:btn>	
	<gd:btn btncode='BTNCGGLSC01'>	
		<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="btn-remove">删除</a>
	</gd:btn>	
	<gd:btn btncode='BTNCGGLDCEXCEL01'>	
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出EXCEL</a>
	</gd:btn>	
	</div>
</div>
</body>
<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
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
function setWarehouse(){
	var marketId = $('#marketId').val();
	if(marketId == null || marketId == ""){
		$('#warehouseId').empty();
		$('#warehouseId').append("<option value=''>全部</option>");
	} else {
		$.ajax({
			type: "GET",
			url: "${CONTEXT }grdGdGiftstore/queryByMarketId?marketId=" + marketId,
			dataType: "json",
			success: function(data) {
				var warehouses=data.rows;
				$('#warehouseId').empty();
				$('#warehouseId').append("<option value=''>全部</option>");
				if (warehouses.length != 0) {
					for ( var n = 0; n < warehouses.length; n++) {
						$('#warehouseId').append("<option value='"+warehouses[n].id+"'>"+warehouses[n].name+"</option>");
					}
				}
			}
		});
	}
}
initMarket(2);
function formatStatus(value,row,index){
	if (row.status == 1){
		return "待入库";
	}else if (row.status == 3){
		return "已关闭";
	}else if (row.status == 2){
		return "入库中";
	}
}
function optformat(value,row,index){
	var html="";
	html+="<a class='operate' href='javascript:;' onclick=view('"+row.id+"');>查看</a>";
	html+="<a class='operate' href='javascript:;' onclick=edit('"+row.id+"');>修改</a>&nbsp;";
	html+="<a class='operate' href='javascript:;' onclick=delete('"+row.id+"');>删除</a>";
	return html;
}
var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var marketId = $('#purchase_datagrid-form #marketId').val();
	var warehouseId = $('#purchase_datagrid-form #warehouseId').val();
	var status = $('#purchase_datagrid-form #status').val();
	var purchaseNO = $('#purchase_datagrid-form #purchaseNO').val();
	var params = {
		"marketId" : marketId,
		"warehouseId" : warehouseId,
		"status" : status,
		"purchaseNO" : purchaseNO
	};
	var paramList = 'marketId=' + marketId + '&warehouseId=' + warehouseId + '&status=' + status + '&purchaseNO=' + purchaseNO;
	$.ajax({
		url: CONTEXT+'grdPurchase/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'grdPurchase/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
		}
	});
});

/***数据导出功能***/
$("#exportSingleData").click(function(){
	var row = $('#purchase_datagrid-table').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	} else if($(row).length > 1){
		warningMessage("请不要选择多条数据！");
		return false;
	} else if($(row).length == 1){
		var id = getSelections("id");
		var params = {
			"marketId" : marketId,
			"warehouseId" : warehouseId,
			"status" : status,
			"purchaseNO" : purchaseNO
		};
		var paramList = 'id=' + id;
		slideMessage("数据正在导出中, 请耐心等待...");
		//启动下载
		$.download(CONTEXT+'grdPurchase/exportSingleData',paramList,'post' );
	}
});

jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};
</script>
<script type="text/javascript" src="${CONTEXT}js/grdPurchase/main.js"></script>
</html>