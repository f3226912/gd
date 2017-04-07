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
<title>接单记录统计</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="proOrderRecieved-datagrid-table" title=""></table>
<div id="proOrderRecieved-datagrid-tool-bar" style="height:auto;padding:0px !important;margin:0px;">
	<form id="proOrderRecieved-datagrid-form" method="post">
	<table>
		<tr>
			<td align="right">所属市场：</td>
			<td>
				<select name="marketId" id="marketId" style="width:140px;height:20px;margin-right: 5px;">
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
			<input type="text" id="grdUserName" name="grdUserName" placeholder="请输入姓名" maxlength="30" class="easyui-validatebox" style="width:230px;height:20px;margin-right: 5px;" >
			</td>
			<td align="right">地推手机：</td>
			<td>
			<input type="text" id="grdMobile" name="grdMobile" placeholder="请输手机号码" maxlength="11" class="easyui-validatebox" style="width:230px;height:20px;margin-right: 5px;" >
			</td>
			<td></td>
			<td align="left">
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			</td>
			
		</tr>
		<tr>
			<td align="right">确认状态：</td>
			<td>
				<select name="confirmStatus" id="confirmStatus" style="width:140px;height:20px;margin-right: 5px;">
					<option value="" selected="selected">全部</option>
					<option value="2" >接受</option>
					<option value="4" >拒绝</option>
					<option value="5" >超时</option>
					<option value="6" >取消</option>
					
				</select>
			</td>
			<td align="right">发布时间：</td>
			<td>
				<input  type="text"  id="startPublisherTime" name="startPublisherTime"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endPublisherTime\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endPublisherTime\')}'})"    style="width:105px;" />
				—
				<input  type="text"  id="endPublisherTime" name="endPublisherTime"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startPublisherTime\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startPublisherTime\')}'})"   style="width:105px;margin-right: 5px;"/> 
				
			</td>
			<td align="right">接单时间：</td>
			<td>
				<input  type="text"  id="startRecieveTime" name="startRecieveTime"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endRecieveTime\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endRecieveTime\')}'})"    style="width:105px;" />
				—
				<input  type="text"  id="endRecieveTime" name="endRecieveTime"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startRecieveTime\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startRecieveTime\')}'})"   style="width:105px;margin-right: 5px;"/> 
			
			</td>
			<td align="right">确认时间：</td>
			<td>
				<input  type="text"  id="startConfirmTime" name="startConfirmTime"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endConfirmTime\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endConfirmTime\')}'})"    style="width:75px;" />
				—
				<input  type="text"  id="endConfirmTime" name="endConfirmTime"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startConfirmTime\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startConfirmTime\')}'})"   style="width:75px;margin-right: 5px;"/> 
		
			</td>
			
		</tr>
			<tr>
		<td align="right">线路类型：</td>
			<td>
				<select name="sourceType" id="sourceType" style="width:140px;height:20px;margin-right: 5px;">
					<option value="" selected="selected">全部</option>
					<option value="1" >干线</option>
					<option value="2" >同城</option>
				</select>
			</td>
		<td align="right">发货地：</td>
		<td>
		  <select id="s_provinceId" name="s_provinceId" class="text_sketch" style="width:73px;height:20px;margin-right: 2px;">
		 					<option selected="selected" value="">选择省份</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	<select id="s_cityId" name="s_cityId" class="text_sketch" style="width:73px;height:20px;margin-right: 2px;">
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	<select id="s_areaId" name="s_areaId" class="text_sketch" style="width:73px;height:20px;margin-right: 5px;">
		 					<option selected="selected" value="">选择区/县</option>
		 				</select>
		</td>
		<td align="right">目的地：</td>
		<td>
		<select id=e_provinceId name="e_provinceId" class="text_sketch"  style="width:73px;height:20px;margin-right: 2px;">
		 					<option selected="selected" value="">选择省份</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	<select id="e_cityId" name="e_cityId" class="text_sketch" style="width:73px;height:20px;margin-right: 2px;">
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	<select id="e_areaId" name="e_areaId" class="text_sketch" style="width:73px;height:20px;margin-right: 5px;">
		 					<option selected="selected" value="">选择区/县</option>
		 				</select>
		</td>
		<td></td>
		<td></td>
		</tr>
	</table>

	</form>
	<div style="background:#efefef;padding:5px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:5px;">接单信息列表</div>
			<gd:btn btncode='BTNJDJLTJDC01'><div style="float:right;margin-right:57px;"><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></div></gd:btn>
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
<script type="text/javascript" src="${CONTEXT}js/grdProOrderRecieved/main.js"></script>
</html>