<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style>
td {
	padding-left: 10px;
}
</style>
<table id="datagrid-table2" title=""></table>
<div id="datagrid-tool-bar2" style="padding: 5px; height: auto">
	<div style="margin-bottom: 5px">
		<a id="exportData2" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
	var purchaseId = "${purchaseId}";
	var type="${type}";
	var disableExport2=false;
</script>
<script type="text/javascript" src="${CONTEXT}js/npsPurchase/offer.js"></script>