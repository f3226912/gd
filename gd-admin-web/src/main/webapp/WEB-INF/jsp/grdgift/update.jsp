<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}

/* #tb-edit *{ */
/*  overflow-x: hidden !important; overflow-y: hidden !important; */
/* } */

/* .datagrid-header-rownumber,.datagrid-td-rownumber,.datagrid-cell-rownumber{ */
/* 	width: 50px; */
/* 	text-align: center; */
/* } */
</style>
<div id="tb-edit" class="easyui-tabs" style="width:780px;height:360px;overflow-x: hidden !important; overflow-y: hidden !important;">
<div title="礼品信息" style="overflow-y: hidden !important;">	
	<form id="addForm" method="post" >
	<input type="hidden" name="id" value="${dto.id }">
	<input type="hidden" name="giftNo" value="${dto.giftNo }">
	<input type="hidden" name="oldName" value="${dto.name }">
	<input type="hidden" name="stockTotalOld" value="${dto.stockTotal }">
	<input type="hidden" name="stockAvailable" value="${dto.stockAvailable }">
	<div style="height: 290px;">
		<table style="border: none;width: 100%;">
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>所属仓库：</td>
				<td colspan="3">
				<labe>${dto.giftstoreName }</labe>
					<!-- <select name="giftstoreId" readonly="readonly" id="updateGiftstoreId" style="width: 650px;" >
					</select> -->
				</td>
			</tr>
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>礼品编码：</td>
				<td><input type="text" readonly="readonly" value="${dto.giftNo }" style="width: 650px;"></td>
			</tr>
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>礼品名称：</td>
				<td><input type="easyui-textbox" id="name"
					data-options="required:true"  readonly="readonly" class="easyui-validatebox" maxlength="30" 
					missingMessage="礼品名称必须填写" name="name" style="width: 650px;" value='${dto.name }'></td>
			</tr>
			<!-- <tr>
				<td><span style="color: red">*</span>所属市场</td>
				<td colspan="3">
					<select name="marketId" id="maketId3" style="width: 650px;">
					</select>
				</td>
			</tr>
			 -->
			
			<tr>
				<td style="text-align:right;"><span style="color: red"></span>待发放数量：</td>
				<td> 
				<input type="text" readonly="readonly" style="width: 650px;" value="${dto.noCount}">
				</td>
			</tr>
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>库存：</td>
				<td><input type="text" id="stockTotal" 
					data-options="required:true,min:0,precision:0,max:9999999" class="easyui-numberbox easyui-validatebox"
					missingMessage="库存必须填写" name="stockTotal" style="width: 650px;" value="${dto.stockTotal }"></td>
			</tr>
				<tr>
				<td style="text-align:right;"><span style="color: red"></span>单位：</td>
				<td><input type="text" id="unit"
					data-options="required:true" maxlength="11"class="easyui-validatebox"
					missingMessage="请输入发放礼品的单位" name="unit" style="width: 650px;"  value="${dto.unit}"></td>
			</tr>
			<tr>
				<td style="text-align:right;">备注：<span style="color: red"></span></td>
				<td><textarea id="remark" name="remark" maxlength="200" style="width: 650px;height: 100px;">${dto.remark }</textarea></td>
			</tr>
		</table>
	</div>
	<div align="center">
		<gd:btn btncode='BTNDTLPGLXG04'>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()"> 保存 </a>
		</gd:btn>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
	</form>
</div>

<div title="库存调整记录" style="overflow-x: hidden !important; overflow-y: auto !important;" >
<div style="height: 290px;overflow-x: hidden !important; overflow-y: auto !important;">
	<table id="logDataGrid" title="" >
	</table>
</div>
	<div align="center" >
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
</div>
<!-- 
<div title="批次价格" style="overflow-x: hidden !important; overflow-y: auto !important;" >
<div style="height: 290px;overflow-x: hidden !important; overflow-y: auto !important;">
	<table id="batchDataGrid" title="" >
	</table>
</div>
	<div align="center" >
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	</div>
</div>
 -->
</div>
<script type="text/javascript">
var marketId='${dto.marketId}';
var giftId='${dto.id }';
var giftNo='${dto.giftNo }';
var giftstoreId='${dto.giftstoreId }';
if(!giftNo || !giftstoreId){
	$("#icon-save-btn").hide();
}
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
function initGiftstore(){
	$.ajax({
		type: "GET",
		url:CONTEXT+'grdGiftIssued/getChildStoreInfo/'+marketId ,
		dataType: "json",
		success: function(data) {
			var giftstores=data.rows;
			if (giftstores.length != 0) {
				$('#updateGiftstoreId').empty();
				for ( var n = 0; n < giftstores.length; n++) {
					if(giftstoreId==giftstores[n].id){
						$('#updateGiftstoreId').append("<option selected='selected' value='"+giftstores[n].giftstoreId+"'>"+giftstores[n].giftstoreName+"</option>");
					}else{
						$('#updateGiftstoreId').append("<option value='"+giftstores[n].giftstoreId+"'>"+giftstores[n].giftstoreName+"</option>");
					}
					
				}
			}else{
				$("#updateGiftstoreId").append("<option value=''>--</option>");
			}
		}
	});
}
initMarket(2);
initGiftstore();

$("#maketId3").change(function(){
	var marketid = $(this).val();
	if(marketid){
		$.ajax({
				 type: 'POST',
			     url:CONTEXT+'grdGiftIssued/getChildStoreInfo/'+marketid ,
			     dataType:'json',
			     success: function(data) {
			    	 $("#updateGiftstoreId").html("");
			    	 if(data.rows.length < 1){
			    		 $("#updateGiftstoreId").append("<option value=''>--</option>");
			    	 }
			    	 $.each(data.rows, function(index, item){
			    		$("#updateGiftstoreId").append("<option value='"+item.giftstoreId+"'>"+item.giftstoreName+"</option");
			    	 });
			     }
			});
	}else{
		 $("#updateGiftstoreId").html("");
    	 $("#updateGiftstoreId").append("<option value=''>--</option>");
	}
});
</script>
<script type="text/javascript" src="${CONTEXT}js/grdgift/update.js"></script>