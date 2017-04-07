<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}
</style>
<form id="addForm" method="post" action="marketSale/save">
	<div>

		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table style="border: none;width: 100%;">
			<tr>
				<td>选择交易市场</td>
				<td colspan="3">
					<select id="marketId" name="marketId" style="width: 50%;">
					</select>
				</td>
			</tr>
			<tr>
				<td>今日交易额(元)</td>
				<td><input type="text" id="todaySale" value="${dto.todaySale}"
					data-options="required:true,min:0,precision:2,max:9999999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="今日交易额必须填写" name="todaySale" style="width: 150px;"></td>
			</tr>
			<tr>
				<td>昨日交易额(元)</td>
				<td><input type="text" id="yestodaySale" value="${dto.yestodaySale}"
					data-options="required:true,min:0,precision:2,max:9999999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="昨日交易额必须填写" name="yestodaySale" style="width: 150px;"></td>
			</tr>
		</table>
	</div>
</form>
<%-- <script type="text/javascript" src="${CONTEXT}js/marketPrice/add.js"></script> --%>
<script type="text/javascript">
var marketId='${dto.marketId}';

function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId').empty();
				for ( var n = 0; n < markets.length; n++) {
					if(marketId==markets[n].id){
						$('#marketId').append("<option selected='selected' value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}else{
						$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}
					
				}
			}
		}
	});
}
initMarket(2);
</script>











