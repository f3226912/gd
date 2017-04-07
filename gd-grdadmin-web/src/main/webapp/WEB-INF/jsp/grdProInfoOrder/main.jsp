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
<title>信息订单统计</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="grdProInfoOrder-datagrid-table" title=""></table>
<div id="grdProInfoOrder-datagrid-tool-bar" style="height:auto;padding:0px !important;margin:0px;">
	<form id="grdProInfoOrder-datagrid-form" method="post">
	<table>
		<tr>
			<td align="right">所属市场：</td>
			<td>
				<select name="marketId" id="marketId" style="width:150px;height:20px;margin-right: 10px;">
					<option value="" selected="selected">全部</option>
				</select>
			</td>
			<!-- <td align="right">团队：</td>
			<td>
				<select name="teamId" id="teamId" style="width:150px;height:20px;margin-right: 10px;">
					<option value="" selected="selected">全部</option>
				</select>
			</td> -->
			<td align="right">地推姓名：</td>
			<td>
			<input type="text" id="grdUserName" name="grdUserName" placeholder="请输入姓名" maxlength="30" class="easyui-validatebox" style="width:160px;height:20px;margin-right: 10px;" >
			</td>
			<td align="right">地推手机：</td>
			<td>
			<input type="text" id="grdMobile" name="grdMobile" placeholder="请输手机号码" maxlength="11" class="easyui-validatebox" style="width:170px;height:20px;margin-right: 10px;" >
			</td>
			<td></td>
			<td align="left">
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			</td>
			
		</tr>
		<tr>
			<td align="right">订单状态：</td>
			<td>
				<select name="orderStatus" id="orderStatus" style="width:150px;height:20px;margin-right: 10px;">
					<option value="" selected="selected">全部</option>
					<option value="1" >待确认</option>
					<option value="2" >已完成</option>
					<option value="3" >已关闭</option>
				
				</select>
			</td>
			<td align="right">支付状态：</td>
			<td>
				<select name="payStatus" id="payStatus" style="width:160px;height:20px;margin-right: 10px;">
					<option value="" selected="selected">全部</option>
					<option value="1" >待支付</option>
					<option value="2" >支付成功</option>

				</select>
			</td>
			<td align="right">接单时间：</td>
			<td>
				<input  type="text"  id="startRecieveTime" name="startRecieveTime"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endRecieveTime\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endRecieveTime\')}'})"    style="width:75px;" />
				—
				<input  type="text"  id="endRecieveTime" name="endRecieveTime"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startRecieveTime\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startRecieveTime\')}'})"   style="width:75px;margin-right: 10px;"/> 
			
			</td>
			<td align="right">确认时间：</td>
			<td>
				<input  type="text"  id="startConfirmTime" name="startConfirmTime"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endConfirmTime\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endConfirmTime\')}'})"    style="width:75px;" />
				—
				<input  type="text"  id="endConfirmTime" name="endConfirmTime"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startConfirmTime\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startConfirmTime\')}'})"   style="width:75px;margin-right: 10px;"/> 
		
			</td>
			
		</tr>
	</table>
	</form>
	<div style="background:#efefef;padding:5px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:5px;">信息订单列表</div>
			<gd:btn btncode='BTNXXDDTJDC01'><div style="float:right;margin-right:57px;"><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></div></gd:btn>
	</div>
</div>
</body>
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

$("#marketId").change(function(){
	var marketId = $(this).val();
	if(marketId){
		$.ajax({
				 type: 'POST',
			     url:CONTEXT+'grdMember/getChildTeamInfo/'+marketId ,
			     dataType:'json',
			     success: function(data) {
			    	 $("#teamId").html("");
			    	 $("#teamId").append("<option value=''>全部</option>");
			    	 $.each(data.rows, function(index, item){
			    		$("#teamId").append("<option value='"+item.giftteamId+"'>"+item.giftteamName+"</option");
			    	 });
			     }
			});
	}else{
		 $("#teamId").html("");
    	 $("#teamId").append("<option value=''>全部</option>");
	}
});


</script>
<script type="text/javascript" src="${CONTEXT}js/grdProInfoOrder/main.js"></script>
</html>