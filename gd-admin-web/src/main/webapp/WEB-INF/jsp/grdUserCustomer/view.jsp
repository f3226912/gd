<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<style>

</style>

	<div id="logView" style="padding:5px;height:auto">
		<input type="hidden" id="viewMemberId" name="memberId" value="${dto.memberId }">
		<table style="width:98%">
			<tr>
				<td align="center" style="width:45%;height:20px;margin-right:10%;">客户姓名：${dto.memberName }</td>
				<td align="center" style="width:45%;height:20px;margin-right:10%;">客户手机：${dto.memberMobile }</td>
			</tr>
			</table>
		<div style="padding:0px;height:25px;background:#efefef;">
			<div style="float: left;font-size:14px;margin-left:0px;">指派记录列表</div>
		</div>

	</div>
		<div style="height: 290px;overflow-x: hidden !important; overflow-y: auto !important;">
			<table id="logDataGrid" title="">
			</table>
		</div>
	</div>
<script type="text/javascript">
var memberId='${dto.memberId }';
</script>
<script type="text/javascript" src="${CONTEXT}js/grdUserCustomer/view.js"></script>