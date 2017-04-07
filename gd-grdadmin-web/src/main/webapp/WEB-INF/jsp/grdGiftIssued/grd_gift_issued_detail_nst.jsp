<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../pub/constants.inc" %>

<style type="text/css">
	.dialogMainDiv{border-top: thin solid #d3d3d3;text-align: center;min-height: 99%;overflow: visible;}
	.dialogMainDiv table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;}
	.dialogMainDiv th , .dialogMainDiv td{border-right:1px solid #888;border-bottom:1px solid #888;line-height: 25px;font-weight: normal;}
	.dialogMainDiv th{background:#efefef;}
	.dialogMainDiv .impcel{background:#efefef;width: 112px;}
	.multPic-ul {margin:2px auto;width: 156px;height: 80px;}
</style>

<div class="dialogMainDiv">
	<input id="popWinRecordId" type="hidden" value="${requestScope.grdGiftRecord.id }" />

	<div style="width:90%;font-size: 25px;margin:0px auto 5px auto;border-bottom: thin solid #E4E4E4;">
		<span style="margin-right: 5%;">所属市场：${requestScope.grdGiftRecord.marketName }</span>
		<span style="margin-right: 5%;">所属仓库：${requestScope.grdGiftRecord.giftstoreName }</span>
		<span style="margin-right: 5%;">地推：${requestScope.grdGiftRecord.createUser }</span>
		<span style="margin-right: 5%;">礼品发放人：${requestScope.grdGiftRecord.grantUser }</span>
	</div>
	<div style="width:85%;margin:0px auto;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:3px;">
			<tr>
				<td class="impcel">发放的礼品</td>
				<td>
					<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border: none;">
						<tr>
							<th width="10%">序号</th>
							<th width="20%">礼品编号</th>
							<th width="50%">礼品名称</th>
							<th width="20%" style="border-right:none">数量</th>
						</tr>
						<c:forEach items="${requestScope.grdPresentDetils}" var="detail" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td align="left">${detail.giftId}</td>
								<td align="left">${detail.giftName}</td>
								<td data-role="popWinGiftCount">${detail.countNo}</td>
							</tr>
						</c:forEach>
						<tr><td colspan="4" style="border:none;" align="right">合计 ：<span id="popGiftCountSum" style="margin-right: 50px;"></span></td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="impcel">兑换礼品的业务统计</td>
				<td>
					<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border: none;">
						<tr>
							<th  width="10%">序号</th>
							<th  width="20%">业务类型</th>
							<th  width="50%">时间</th>
							<th  width="20%" style="border-right:none">数量</th>
						</tr>
						<c:forEach items="${grdBusinessDetails}" var="detail" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td align="left">${detail.typeName}</td>
								<td align="center"><input type="hidden"  name="code" value="${detail.code}"><span>${detail.codeDetail}</span></td>
								<td align="center"  data-role="giftBusinessCount">${detail.countNo}</td>
							</tr>
						</c:forEach>
						<tr><td colspan="4" style="border:none" align="right">合计 ： <span id="giftBusinessSum" style="margin-right: 30px;"></span></td></tr>
					</table>
				</td>
			</tr>
			
		</table>
	</div>	
</div>

<script type="text/javascript">
	var imgHostUrl = "${imgHostUrl}";
	var carNo="${requestScope.grdGiftRecord.carNo }";
</script>
<script type="text/javascript" src="${CONTEXT}js/grdGiftIssued/grd_gift_issued_detail.js"></script>











