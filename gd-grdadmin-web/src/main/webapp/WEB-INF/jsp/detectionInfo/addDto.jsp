<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}
</style>
<form id="addForm" method="post" action="detectinfo/save">
	<div>

		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table style="border: none;width: 100%;">
			<tr>
				<td>商品名称<span style="color: red">*</span></td>
				<td><input type="text" id="productName" value="${dto.productName}"
					required="true" class="easyui-validatebox" maxlength="20" validType="unnormal"
					missingMessage="商品名称必须填写" name="productName" style="width: 90%;"></td>
				<td>出产地<span style="color: red">*</span></td>
				<td colspan="2"><input type="text" id="origin" maxlength="20" validType="unnormal"
					value="${dto.origin}" required="true" class="easyui-validatebox"
					missingMessage="出产地必须填写" name="origin" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>被检单位或姓名<span style="color: red">*</span></td>
				<td><input type="text" id="unitName" value="${dto.unitName}"
					required="true" class="easyui-validatebox" maxlength="20" validType="unnormal"
					missingMessage="被检单位必须填写" name="unitName" style="width: 90%;"></td>
				<td>检测项目</td>
				<td>
					<select name="inspection" style="width: 90%;">
						<option value="0" <c:if test="${dto.inspection eq '0'}">selected="selected"</c:if>>蔬菜农药残留检测</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>抑制率<span style="color: red">*</span></td>
				<td><input type="text" id="rate" value="${dto.rate}"
					data-options="required:true,min:0,precision:2,max:199.99" class="easyui-numberbox easyui-validatebox"
					missingMessage="抑制率必须填写" name="rate" style="width: 60%;">&nbsp;%</td>
				<td>是否合格</td>
				<td >
					<select name="pass" style="width: 30%;">
						<option value="1" <c:if test="${dto.pass==1}"> selected="selected"</c:if>>是</option>
						<option value="0" <c:if test="${dto.pass==0}"> selected="selected"</c:if>>否</option>
					</select>		
				</td>
			</tr>
			<tr>
				<td>检测日期</td>
				<td><input type="text" id="detectTime" 
				<c:if test="${empty dto.detectTime}">value="true"</c:if>
				<c:if test="${!empty dto.detectTime}">value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${dto.detectTime}' />"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox" editable="false" 
					missingMessage="检测日期必须填写" name="detectTime" style="width: 150px;">
				</td>
				<td>发布日期</td>
				<td><input type="text" id="publishTime" 
				<c:if test="${empty dto.publishTime}">value="true"</c:if>
				<c:if test="${!empty dto.publishTime}">value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${dto.publishTime}' />"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox"  editable="false"
					missingMessage="发布日期必须填写" name="publishTime" style="width: 150px;">
				</td>
			</tr>
			<tr>
				<td>选择交易市场</td>
				<td colspan="3">
					<select id="maketId3" name="maketId" style="width: 50%;">
					</select>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/detectionInfo/add.js"></script>
<script type="text/javascript">
var marketId='${dto.maketId}';

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











