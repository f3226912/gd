<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<div>
	<form id="detailForm" method="post" action="#">
		<table>
			<tr height="25px">
				<td style="width: 100px;">电话号码:</td>
				<td>
					<label id="mobile"></label>
				</td>
			</tr>
			<tr height="32px">
				<td style="width: 100px;">活动编号:</td>
				<td>
					<label id="activityCode"></label>
				</td>
			</tr>
			
			<tr height="32px">
				<td style="width: 100px;">变更积分:</td>
				<td>
					<label id="integral"></label>
					
				</td>
			</tr>
			<tr height="32px">
				<td class="left">变更类型</td>
				<td>
					<label id="integralType"></label>
				</td>
			</tr>
			<tr height="32px">
				<td class="left">变更时间</td>
				<td>
					<label id="createTimeStr"></label>
				</td>
			</tr>
			<tr height="32px">
				<td class="left">累计积分</td>
				<td>
					<label id="totalIntegral"></label>
				</td>
			</tr>
			<tr height="32px">
				<td class="left">可用积分</td>
				<td>
					<label id="doIntegral"></label>
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var id = "${id}";
</script>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/userIntegral/detail.js"></script>