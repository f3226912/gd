<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}
</style>
<form id="addForm" method="post" action="marketprice/save">
	<div>

		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table style="border: none;width: 100%;">
			<tr>
				<td>商品名称<span style="color: red">*</span></td>
				<td><input type="easyui-textbox" id="productName" value="${dto.productName}"
					data-options="required:true" class="easyui-validatebox" maxlength="20" validType="unnormal"
					missingMessage="商品名称必须填写" name="productName" style="width: 150px;"></td>
				<td>采集市场</td>
				<td colspan="3">
					<select name="maketId" id="maketId3" style="width: 90%;">
					</select>
				</td>
			</tr>
			<tr>
				<td>采集时间</td>
				<td><input type="text" id="collectTime" 
					<c:if test="${empty dto.date}">value="true"</c:if>
					<c:if test="${!empty dto.date}">value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${dto.date}' />"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox"  editable="false"
					missingMessage="报价时间必须填写" name="date" style="width: 150px;">
				</td>
				<td>发布时间</td>
				<td><input type="text" id="publishTime" 
					<c:if test="${empty dto.publishTime}">value="true"</c:if>
					<c:if test="${!empty dto.publishTime}">value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${dto.publishTime}' />"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox"  editable="false"
					missingMessage="发布时间必须填写" name="publishTime" style="width: 150px;">
				</td>
			</tr>
			<tr>
				<td>最高报价(元/公斤)<span style="color: red">*</span></td>
				<td><input type="text" id="maxPrice" value="${dto.maxPrice}"
					data-options="required:true,min:0,precision:2,max:9999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="最高报价必须填写" name="maxPrice" style="width: 150px;"></td>
				<td>最低报价(元/公斤)<span style="color: red">*</span></td>
				<td><input type="text" id="minPrice" value="${dto.minPrice}"
					data-options="required:true,min:0,precision:2,max:9999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="最低报价必须填写" name="minPrice" style="width: 150px;"></td>
			</tr>
		</table>
				<div style="padding-left: 12px;margin-top: 10px;">跳转地址(请加上http://)
				<input type="text" id="targetUrl" value="${dto.targetUrl}"
					data-options="validType:['url','length[0,120]']" class="easyui-validatebox"
					missingMessage="" name="targetUrl" style="width: 97%;"
					placeholder="http://www.gdeng.cn">
				</div>

	</div>
</form>
<%-- <script type="text/javascript" src="${CONTEXT}js/marketPrice/add.js"></script> --%>
<script type="text/javascript">
var marketId='${dto.maketId}';
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#maketId3').empty();
				for ( var n = 0; n < markets.length; n++) {
					if(marketId==markets[n].id){
						$('#maketId3').append("<option selected='selected' value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}else{
						$('#maketId3').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}
					
				}
			}
		}
	});
}
initMarket(2);
</script>











