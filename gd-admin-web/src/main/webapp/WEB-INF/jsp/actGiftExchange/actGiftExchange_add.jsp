<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="width:300px; height:200px; margin:10px auto">
<form id="addActGiftExchangeForm" method="post" action="actGiftExchange/saveAdd">
	<input type="hidden" id="id" name="id" value="${dto.id}"/>
	<div>
		<table>
			<tr>
				<td> 用户账户:</td>
				<td width="200" align="left">
					<input type="button" id="showMemberDailog" required="true" onclick="openSelectMember()" value="${(empty dto.account) ? '搜索' : dto.account }" ${(!empty dto.userid) ? "disabled" : ""}>
					<input type="hidden" id="memberId" name="userid"  value="${dto.userid }" >
				</td>
			</tr>
			<tr>
				<td> 活动编号:</td>
				<td>
					<input type="text" id="activityId" value="${dto.activity_id }" maxlength="20" name="activity_id" required="true" class="easyui-validatebox" style="width:150px" >
				</td>
			</tr>
			<tr>
				<td> 选择礼品:</td>
				<td>
					<input type="button" id="showGiftDailog" required="true" onclick="openSelectGift()" value="${(empty dto.giftName) ? '选择' : dto.giftName }">
					<input type="hidden" id="giftId" name="gift_id"  value="${dto.gift_id }" >
				</td>
			</tr>
		</table>
	</div>
</form>
<div id="memberDailog" class="easyui-dialog"  style="width:600px;height:220px;"  closed="true" modal="true" buttons="#dlg-buttonsMember">
	<div id="#dlg-buttonsMember" style="text-align:center">
	      <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#memberDailog').dialog('close')">关闭</a>
	</div>
</div>
<div id="giftDialog" class="easyui-dialog"  style="width:600px;height:220px;"  closed="true" modal="true" buttons="#dlg-buttonsGift">
	<div id="#dlg-buttonsGift" style="text-align:center">
	      <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#giftDialog').dialog('close')">关闭</a>
	</div>
</div>
<script type="text/javascript" >
function openSelectMember(){
	$('#memberDailog').dialog({
		'title' : '选择用户',
		'width' : 700,
		'height' : 300,
		'href' : CONTEXT + 'actGiftExchange/memberSelect'
	}).dialog('open');
}

function openSelectGift(){
	var activityId = $("#activityId").val();
	if(activityId == ''){
		warningMessage("请先输入活动编号！");
		return false;
	}
	$('#giftDialog').dialog({
		'title' : '选择礼品',
		'width' : 700,
		'height' : 300,
		'href' : CONTEXT + 'actGiftExchange/giftSelect?activityId='+activityId
	}).dialog('open');
}	

$(function(){
	$("#activityId").change(function(){
		$("#showGiftDailog").val("搜索");
		$("#giftId").val("");
	});
});
</script>
