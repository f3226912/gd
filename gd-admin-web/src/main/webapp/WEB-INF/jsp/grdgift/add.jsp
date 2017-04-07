<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}

#tb-add *{
 overflow-x: hidden !important; overflow-y: hidden !important;
}
/* .datagrid-header-rownumber,.datagrid-td-rownumber,.datagrid-cell-rownumber{ */
/* 	width: 50px; */
/* 	text-align: center; */
/* } */
</style>
<div id="tb-add" class="easyui-tabs" style="width:780px;height:360px;overflow-x: hidden !important; overflow-y: hidden !important;"> 
<div title="礼品信息">	
	<form id="addForm" method="post" >
	<div style="height: 290px;">
		<table style="border: none;width: 100%;">
			<tr>
				<td><span style="color: red">*</span>礼品编码</td>
				<td><input type="text" readonly="readonly" style="width: 650px;" placeholder="系统自增长"></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>礼品名称</td>
				<td><input type="easyui-textbox" id="name"
					data-options="required:true" class="easyui-validatebox" maxlength="30" validType="unnormal"
					missingMessage="礼品名称必须填写" name="name" style="width: 650px;" placeholder="请输入礼品名称"></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>所属市场</td>
				<td colspan="3">
					<select name="marketId" id="maketId3" style="width: 650px;">
					</select>
				</td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>库存</td>
				<td><input type="text" id="stockTotal" value=""
					data-options="required:true,min:0,precision:0,max:9999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="库存必须填写" name="stockTotal" style="width: 650px;" placeholder="请输入实际库存量"></td>
			</tr>
			
			<tr>
				<td><span style="color: red"></span>单位</td>
				<td><input type="text" id="unit" value=""
					data-options="required:true" maxlength="11" class="easyui-validatebox"
					missingMessage="请输入发放礼品的单位" name="unit" style="width: 650px;" placeholder="请输入发放礼品的单位"></td>
			</tr>
			<tr>
				<td>备注<span style="color: red"></span></td>
				<td><textarea id="remark" name="remark" maxlength="200" style="width: 650px;height: 100px;" placeholder="富文本备注信息"></textarea></td>
			</tr>
		</table>
	</div>
	<div align="center">
	<gd:btn btncode='BTNDTLPGLXZ02'>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()"> 保存 </a>
	</gd:btn>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
	</form>
</div>

<div title="库存调整记录" >
	<div style="height: 290px;">
		<table id="logDataGrid" title="" >
		</table>
	</div>
	<div align="center" style="margin-bottom: 5px;">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
</div>
</div>
<script type="text/javascript">
var marketId='${dto.marketId}';
var giftId=null;
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
<script type="text/javascript" src="${CONTEXT}js/grdgift/update.js"></script>