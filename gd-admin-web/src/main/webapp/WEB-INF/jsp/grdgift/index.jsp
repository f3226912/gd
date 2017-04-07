<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>地推礼品管理主页</title>
	</head>
<style type="text/css">
.datagrid-header-row .datagrid-cell{
	text-align: center !important;
}
/* .datagrid-header-rownumber,.datagrid-td-rownumber,.datagrid-cell-rownumber{ */
/* 	width: 50px; */
/* 	text-align: center; */
/* } */
</style>	
		<%@ include file="../pub/head.inc" %>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="dataGrid" title="地推礼品管理">
	</table>
	<div id="dataGridToolbar" style="padding:5px;height:auto">
		<form id="dataGridSearchForm" method="post">
		<div>
			所属市场：
			<select id="marketId" name="marketId">
			</select>&nbsp;&nbsp;
			所属仓库：
			<select name="giftstoreId" id="giftstoreId" style="width:150px;height:20px;margin-right: 10px;">
				<option value="" selected="selected">全部</option>				
			</select>	
			礼品编码：
			<input type="hidden" id="id" name="id">
			<input type="text" id="giftNo" name="giftNo" placeholder="礼品编码" data-options="min:0,precision:0" 
			 style="width:150px" >&nbsp;&nbsp;
			礼品名称：
			<input type="text" id="name" placeholder="礼品名称"
			class="easyui-validatebox" name="name" style="width:150px" >&nbsp;&nbsp;
			<gd:btn btncode='BTNDTLPGLCX01'>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-cx">查询</a>
			</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
		<!--  
		OnClick ="return $('#dataGridSearchForm').form('validate');"
			<gd:btn btncode='BTNDTLPGLXZ02'>
			<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a>
			</gd:btn>-->
			<gd:btn btncode='BTNDTLPGLRK01'>
			<a class="easyui-linkbutton" id="icon-instock"  iconCls="icon-reload">入库</a>
			</gd:btn>
			<gd:btn btncode='BTNDTLPGLXZ05'>
			<a class="easyui-linkbutton icon-report-btn" iconCls="icon-save" id="report-btn" onclick="reportData()">导出EXCEL</a>
			</gd:btn>
			<!-- 
			<gd:btn btncode='BTNDTLPGLSC03'>
			<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a>
			</gd:btn> -->
			<gd:btn btncode='BTNDTLPGLSX04'>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			</gd:btn>
		</div>
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:500px;" closed="true" modal="true" >
<!-- 			<div id="dlg-buttonsAdd" style="text-align:center"> -->
<!-- 	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a> -->
<!-- 	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a> -->
<!-- 	        </div> -->
		</div>
		<!-- <div id="purchaseSelectDialog" class="easyui-dialog" style="width:900px;height:500px;" closed="true" modal="true" >
<!-- 			<div id="dlg-buttonsAdd" style="text-align:center"> -->
<!-- 	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a> -->
<!-- 	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a> -->
<!-- 	        </div>
		</div> -->
		<!-- <div id="instockDialog" class="easyui-dialog" style="width:900px;height:500px;" closed="true" modal="true" >
			<div id="dlg-buttonsAdd" style="text-align:center"> -->
<!-- 	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a> -->
<!-- 	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a> -->
<!-- 	        </div> 
		</div>-->
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/grdgift/index.js"></script>
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
initMarket(2);

</script>
</html>

