<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>

</style>
<div style="height: 360px;overflow-x: hidden !important; overflow-y: auto !important;">
	<table id="assignMemberDataGrid" title="">
	</table>
</div>
	<div id="assignMemberGridToolbar" style="padding:5px;height:auto">
		<form id="assignMemberGridSearchForm" method="post">
		<input type="hidden" id="assignMemberId" name="memberId" value="${dto.memberId }">
		<table>
			<tr style="height:40px">
				<td align="right">客户姓名：</td>
				<td style="width:120px;height:20px;margin-right: 10px;" >${dto.memberName }</td>
				<td align="right">客户手机：</td>
				<td style="width:120px;height:20px;margin-right: 10px;">${dto.memberMobile }</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		<tr>
			<td align="right">所属市场：</td>
			<td><select id="assignMemberMarketId" name="marketId" style="width:120px;height:20px;margin-right: 10px;" ></select></td>
			<td align="right">地推人员姓名：</td>
			<td>
			<input type="text" id="assignMemberGrdUserName" name="grdUserName" placeholder="请输入姓名" maxlength="30" class="easyui-validatebox" style="width:120px;height:20px;margin-right: 10px;" >
			</td>
			<td align="right">地推人员手机：</td>
			<td>
			<input type="text" id="assignMemberGrdMobile" name="grdMobile" placeholder="请输手机号码" maxlength="11" class="easyui-validatebox" style="width:120px;height:20px;margin-right: 10px;" >
			</td>
			<td>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-assignSearch" OnClick ="return $('#assignMemberGridSearchForm').form('validate');" >查询</a>&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-assignReset">重置</a>
			</td>
			</table>
		
		</form>
		<div style="padding:0px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:0px;">地推人员列表</div>
		</div>
	</div>

<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#assignMemberMarketId').empty();
				$('#assignMemberMarketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#assignMemberMarketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);

</script>
<script type="text/javascript" src="${CONTEXT}js/grdUserCustomer/edit.js"></script>