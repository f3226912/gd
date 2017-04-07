<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style type="text/css">


</style>
	<table style="border:0; width:70%;" align="center">
	<tr>
		<td style="margin-left:10%;">采购单编码：${grdPurchaseDTO.purchaseNO}</td>
		<td style="margin-left:20%;">采购申请人：${grdPurchaseDTO.purchaser}</td>
	</tr>
	<tr>
		<td style="margin-left:10%;">所属市场：${grdPurchaseDTO.marketName}</td>
		<td style="margin-left:20%;">所属仓库：${grdPurchaseDTO.warehouseName}</td>
	</tr>
	<tr>
		<td style="margin-left:10%; ">采购单创建时间：<fmt:formatDate value="${grdPurchaseDTO.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td style="margin-left:20%;">状态：<c:choose>
					<c:when test="${grdPurchaseDTO.status == '0' }">已删除</c:when>
					<c:when test="${grdPurchaseDTO.status == '1' }">待入库</c:when>
					<c:when test="${grdPurchaseDTO.status == '2' }">入库中</c:when>
					<c:when test="${grdPurchaseDTO.status == '3' }">已关闭</c:when>
			</c:choose></td>
	</tr>
	
	</table>
	
	<div style="height: 300px;overflow-x: hidden !important; overflow-y: auto !important; margin-top:20px">
	<table id="instockDataGrid" title="">
	</table>
	</div>
	</div>
	<form id="instockForm" method="post" >
	<input type="hidden" id="instockPurchaseNO" value="${grdPurchaseDTO.purchaseNO}">
	<input type="hidden" id="instockMarketId" value="${grdPurchaseDTO.marketId}">
	<input type="hidden" id="instockWarehouseId" value="${grdPurchaseDTO.warehouseId}">
	<div style="margin-left:5%;margin-top:3%">
		<span style="margin-right:2%">备注:</span>
		<textarea id="instockremark" name="remark" maxlength="200" style="width: 650px;height: 100px;" class="easyui-validatebox" ></textarea>
	</div>
	</form>
		
<script type="text/javascript">
$("#confirmInstockBtn").click(function(){
	
});

$(".panel-tool-close").click(function(){
	$('#purchaseSelectDialog').dialog('close');
	$('#dataGrid').datagrid('reload');
	$('#dataGrid').datagrid("uncheckAll");
})

</script>
<script type="text/javascript" src="${CONTEXT}js/grdgift/instock.js"></script>