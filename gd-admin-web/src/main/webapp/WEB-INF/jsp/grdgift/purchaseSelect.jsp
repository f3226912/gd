<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>

</style>

	<div style="height: 300px;overflow-x: hidden !important; overflow-y: auto !important;">
	<table id="purchaseDataGrid" title="">
	</table>
	</div>
	<div id="purchaseGridToolbar" style="padding:5px;height:auto">
		<form id="purchaseGridSearchForm" method="post">
		<div>
			所属市场：
			<select id="purchaseMarketId" name="marketId">
			</select>&nbsp;&nbsp;
			所属仓库：
			<select name="giftstoreId" id="purchaseWarehouseId" style="width:150px;height:20px;margin-right: 10px;">
				<option value="" selected="selected">全部</option>				
			</select>	
			
			<gd:btn btncode='BTNDTLPGLCX01'>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-selectSearch" OnClick ="return $('#purchaseGridSearchForm').form('validate');" >查询</a>
			</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-selectReset">重置</a>
		</div>
		</form>
		

<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#purchaseMarketId').empty();
				$('#purchaseMarketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#purchaseMarketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);

$("#purchaseMarketId").change(function(){
	var marketid = $(this).val();
	if(marketid){
		$.ajax({
				 type: 'POST',
			     url:CONTEXT+'grdGiftIssued/getChildStoreInfo/'+marketid ,
			     dataType:'json',
			     success: function(data) {
			    	 $("#purchaseWarehouseId").html("");
			    	 $("#purchaseWarehouseId").append("<option value=''>全部</option>");
			    	 $.each(data.rows, function(index, item){
			    		$("#purchaseWarehouseId").append("<option value='"+item.giftstoreId+"'>"+item.giftstoreName+"</option");
			    	 });
			     }
			});
	}else{
		 $("#purchaseWarehouseId").html("");
    	 $("#purchaseWarehouseId").append("<option value=''>全部</option>");
	}
});
</script>
<script type="text/javascript" src="${CONTEXT}js/grdgift/purchaseSelect.js"></script>