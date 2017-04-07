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
<title>退款记录</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="datagrid-table" title=""></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<table>
					<tr>
						<td>退款编号 :</td>
						<td><input type="text" id="refundNo"
							class="easyui-validatebox" name="refundNo" style="width: 120px"
							maxlength="20"></td>
						<td>订单编号 :</td>
						<td><input type="text" id="orderNo"
							class="easyui-validatebox" name="orderNo" style="width: 120px"
							maxlength="20"></td>

						<td>所属市场:</td>
						<td><select style="width: 120px" id="marketId" name="marketId"><option value="">请选择</option>
						<c:forEach items="${requestScope.marketDTOs }" var="market">
						<option value="${market.id }">${market.marketName }</option>
					</c:forEach>
						</select></td>
						<td>退款状态 :</td>
						<td><select name="status" id="status"
							style="width: 120px;">
								<option value="">---请选择---</option>
								<option value="1">待退款</option>
								<option value="3">已退款</option>
						</select></td>
					<tr>
					<td>用户账号 :</td>
						<td><input type="text" id="account"
							class="easyui-validatebox" name="account" style="width: 120px"
							maxlength="50"></td>
							
							<td>手机号码 :</td>
						<td><input type="text" id="mobile"
							class="easyui-validatebox" name="mobile" style="width: 120px"
							maxlength="50"></td>
							
							<td>退款金额 :</td>
						<td><input type="text" id="amount"
							class="easyui-validatebox" name="amount" style="width: 120px"
							maxlength="10"></td>
							
						<td>创建时间 :</td>
						<td ><input type="text" id="startDate"
							name="startDate"
							onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px">~ <input type="text" id="endDate"
							name="endDate"
							onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px"></td>
					</tr>
				</table>
	</form>
	<div style="margin-bottom:5px">
	    <a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>
</body>
<script type="text/javascript">
var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var amount = $('#datagrid-form #amount').val();
	var createTime = $('#datagrid-form #createTime').val();
	var orderNo = $('#datagrid-form #orderNo').val();
	var status = $('#datagrid-form #status').val();


	var params = {
		"amount" : amount,
		"createTime" : createTime,
		"orderNo" : orderNo,
		"status" : status,
	};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url: CONTEXT+'orderRefundRecord/checkExportParams',
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
					$.download(CONTEXT+'orderRefundRecord/exportData',paramList,'post' );
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
</script>
<script type="text/javascript" src="${CONTEXT}js/order/refund_order.js"></script>
</html>