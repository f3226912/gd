<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<style>
td{
    padding-left: 10px;
}
</style>
<div id="tb-rules" class="easyui-tabs" style="width:780px;height:360px;overflow-x: hidden !important; overflow-y: hidden !important;">
<div title="基本规则" style="overflow-y: hidden !important;" data-options="closable:true">	
	<div style="height: 290px;">
		<table style="border: none;width: 100%;">
			<tr>
				<td style="text-align:right;width: 100px;"><span style="color: red">*</span>活动名称：</td>
				<td><input type="text" readonly="readonly" value="${activity.name}" style="width: 250px;"></td>
			</tr>
			<tr>
				<td style="text-align:right;"><span style="color: red"></span>活动类型：</td>
				<td> 
					<select id="activityType" name="activityType"  style="width:150px" disabled>
						<c:if test="${activity.activityType eq 1}">
							<option value="1">6+1促销</option>
						</c:if>
					</select>	
				</td>
			</tr>
			<tr>
				<td style="text-align:right;width: 100px;">活动时间：<span style="color: red"></span></td>
				<td><input type="text" readonly="readonly" value="${activity.startTimeString}" style="width: 250px;">
					&nbsp;&nbsp;~&nbsp;&nbsp;
					<input type="text" readonly="readonly" value="${activity.endTimeString}" style="width: 250px;">
				</td>
			</tr>
		</table>
	</div>
</div>
<div title="活动规则" style="overflow-x: hidden !important; overflow-y: auto !important;" data-options="closable:true">
	<div style="margin-top: 80px;">
		<div style="padding-left: 27%;font-size: 18px;color: #000000;">用户预付款款设置</div>
		<div style="padding-left: 30%;margin-top: 15px;">
			<div style="font-size: 16px;color: #000000;">买家预付款设置</div>
			<div style="font-size: 13px;color: #333333;margin-top: 15px;">按订单金额:<input type="text" readonly="readonly" value="${activity.cost}" style="width: 40px;" disabled>%收取</div>
		</div>
	</div>
</div>
<div title="用户规则" style="overflow-x: hidden !important; overflow-y: auto !important;" data-options="closable:true">
	<div style="height: 290px;">
		<table style="border: none;width: 100%;">
			<tr style="height: 10px;">
				<td></td>
				<td></td>
			</tr>			
			<tr>
				<td style="width: 100px;"><span style="color: red">*</span>卖家设置：</td>
				<td><a href="javascript:void(0);" onclick="showList(2)" >活动商铺信息</a></td>
			</tr>
			<tr>
				<td ><span style="color: red"></span>所属市场：</td>
				<td> 
					<select i style="width:150px" disabled>
						<c:forEach items="${markets}" var="item" varStatus="status">
							<c:if test="${activity.marketId eq item.id}">
								<option value="${activity.marketId}">${item.marketName}</option>
							</c:if>
						</c:forEach>
					</select>	
				</td>
			</tr>
			<tr>
				<td style="width: 100px;">添加活动商铺：<span style="color: red"></span></td>
				<td><input type="button"  value="添加商铺" style="width: 250px;" disabled>
				</td>
			</tr>
			<tr style="height: 10px;">
				<td></td>
				<td></td>
			</tr>			
			<tr>
				<td style="width: 100px;"><span style="color: red">*</span>买家设置：</td>
				<td><a href="javascript:void(0);" onclick="showList(1)">活动买家信息</a></td>
			</tr>
			<tr>
				<td style="width: 120px;" colspan="2">订单金额:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" readonly="readonly" value="${activity.startTimeString}" style="width: 60px;">
					&nbsp;&nbsp;元~&nbsp;&nbsp;
					<input type="text" readonly="readonly" value="${activity.endTimeString}" style="width: 60px;">
					元,参加本次活动
				</td>
			</tr>
			<tr>
				<td style="width: 100px;">指定买家：<span style="color: red"></span></td>
				<td><input type="button"  value="添加买家" style="width: 250px;" disabled>
				</td>
			</tr>			
		</table>
	</div>
	
</div>
<div title="物流配送规则" style="overflow-x: hidden !important; overflow-y: auto !important;" data-options="closable:true">
	<div style="margin-top: 80px;">
		<div style="padding-left: 27%;font-size: 18px;color: #000000;">物流配送规则设置</div>
		<div style="padding-left: 30%;margin-top: 15px;">
			<div style="font-size: 16px;color: #000000;">物流配送方式</div>
			<div style="font-size: 13px;color: #333333;margin-top: 15px;">
				<input type="checkbox" readonly="readonly" value="0" style="width: 10px;" disabled
				<c:forEach items="${types}" var="ctype" varStatus="status">
					<c:if test="${ctype.type eq 0}">checked</c:if>
				</c:forEach>
				>自提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="checkbox" readonly="readonly" value="0" style="width: 10px;" disabled
				<c:forEach items="${types}" var="ctype" varStatus="status">
					<c:if test="${ctype.type eq 1}">checked</c:if>
				</c:forEach>
				>平台配送
			</div>
		</div>
	</div>
	
</div>
<input type="hidden" id="activityId" value="${activity.id}">
</div>
<script type="text/javascript">
$('#tb-rules').tabs({
    border:false,
    onSelect:function(title){
        //alert(title+' is selected');
    }
});
var pp = $('#tb-edit').tabs('select',1);
function showList(type){
	$('<div></div>').dialog({
		'title' : '活动信息',
		'href' : CONTEXT + 'gdActActivity/toMemberList/' + $("#activityId").val() + "/" + type,
		"width" : 800,
		"height" :400,
        onClose : function() {
            $(this).dialog('destroy');
        }
	});
}
</script>