<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc"%>
<style type="text/css">
.mleft {
	font-size: 16px;
	text-align: right;
	valign: middle;
	width: 150px;
}

.mright {
	font-size: 16px;
	align: left;
	valign: middle;
}
</style>

<form id="addForm" method="post" action="activity/save">
	<div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动名称:</td>
				<td class="mright"><input type="text" id="name" name="name"
					value="${dto.name}" required="true" maxlength="20"
					class="easyui-validatebox" style="width: 150px"
					missingMessage="必须填写"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>积分上限:</td>
				<td class="mright"><input type="text" id="limitIntegral"
					name="limitIntegral" value="${dto.limitIntegral}" required="true"
					maxlength="20" class="easyui-validatebox" style="width: 150px"
					missingMessage="必须填写"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动说明:</td>
				<td class="mright"><input type="text" id="description"
					name="description" value="${dto.description}" required="true"
					maxlength="20" class="easyui-validatebox" style="width: 150px"
					missingMessage="必须填写"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动起始时间:</td>
				<td><input type="text" id="startTime"
					value="${dto.strStartTime}"
					onFocus="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					onClick="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					name="startTimeStr" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动截止时间:</td>
				<td><input type="text" id="endTime" value="${dto.strEndTime}"
					onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					name="endTimeStr" style="width: 150px;" /></td>
			</tr>
			<%-- 
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动起始时间:</td>
				<td><input type="text" id="startTime"
					<c:if test="${empty dto.startTime}">value="true"</c:if>
					<c:if test="${!empty dto.startTime}">value="${dto.startTime}"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox"
					editable="false" missingMessage="检测日期必须填写" name="startTime"
					style="width: 150px;"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>活动截止时间:</td>
				<td><input type="text" id="endTime"
					<c:if test="${empty dto.endTime}">value="true"</c:if>
					<c:if test="${!empty dto.endTime}">value="${dto.endTime}"</c:if>
					required="true" class="easyui-validatebox easyui-datetimebox"
					editable="false" missingMessage="发布日期必须填写" name="endTime"
					style="width: 150px;"></td>
			</tr>
			--%>
		</table>

		<c:if test="${!empty dto.id}">
			<table>
				<tr>
					<td class="mleft">关联礼品:</td>
				</tr>
			</table>

			<table width="100%" height="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td align="center" valign="middle">
						<table width="50%" border="1" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<th>礼品名称</th>
								<th>礼品积分</th>
								<th>状态</th>
							</tr>
							<c:forEach items="${dto2}" var="gift">
								<tr>
									<td>${gift.name}</td>
									<td>${gift.integral}</td>
									<td>${gift.type}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</c:if>
	</div>
</form>
