<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style>
td {
	padding-left: 10px;
}
.lt{text-align:left;}
.rt{text-align:right;}
.title{
font-weight: bold;font-size:15px;
}
</style>
<form id="save-form" method="post" action="npsPurchase/save">
<br/>
	<div style="width: 90%; margin: 0px auto;">
	<div><span class="title">&nbsp;&nbsp;&nbsp;询价信息</span>
				<span style="float:right;" class="title">编号：${dto.id}</span>
			</div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />

		<table>

			<tr>
				<td class="rt">商品名称：</td>
				<td class="lt">${dto.goodsName}</td>
			</tr>
			<tr>
				<td class="rt">商品图片：</td>
				<td class="lt"><c:if test="${not empty dto.goodsImg}">
				<img alt="商品图片" src="${imgHostUrl}${dto.goodsImg}" width="350" height="200" /></c:if></td>
			</tr>
			<tr>
				<td class="rt">采购量：</td>
				<td class="lt">${dto.purchaseCount}${dto.purchaseUnit}</td>
			</tr>
			<tr>
				<td class="rt">心理价位：</td>
				<td>${dto.minPrice}~${dto.maxPrice}</td>
			</tr>
			<tr>
				<td class="rt">提交时间：</td>
				<td>
				<fmt:formatDate value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<td class="rt">结束时间：</td>
				<td>
				<fmt:formatDate value="${dto.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td class="rt">采购时效：</td>
				<td>${dto.purchaseCycle}天</td>
			</tr>
			<tr>
				<td class="rt">规格要求：</td>
				<td>${dto.specRequire}</td>
			</tr>
			<tr>
				<td class="rt">指定产区：</td>
				<td>${dto.areaName}</td>
			</tr>
			<tr>
				<td class="rt">其他要求：</td>
				<td>${dto.remark}</td>
			</tr>
			<tr>
				<td class="rt">询价状态：</td>
				<td><c:choose>
						<c:when test="${dto.status=='1'}">待审核 </c:when>
						<c:when test="${dto.status=='2'}">审核通过 </c:when>
						<c:when test="${dto.status=='3'}">已驳回 </c:when>
						<c:when test="${dto.status=='4'}">用户撤销 </c:when>
						<c:when test="${dto.status=='6'}">已结束 </c:when>
					</c:choose></td>
			</tr>
			<tr>
				<td class="rt">累计浏览次数：</td>
				<td>${dto.visitCount}</td>
			</tr>
			<tr>
				<td class="rt">驳回原因：</td>
				<td>${dto.rejectReason}</td>
			</tr>
			<tr>
				<td class="rt">发布时间：</td>
				<td>
				<fmt:formatDate value="${dto.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<td class="rt">最近审核时间：</td>
				<td>
				<fmt:formatDate value="${dto.authTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<td class="lt"></td>
				<td calss="rt"><a href="javascript:void(0)" onclick="getOfferPrice(2)">这条询价的全部报价信息</a></td>
			</tr>
				
		</table>
		
	</div>
</form>
<script type="text/javascript" charset="utf-8">
var purchaseId = "${dto.id}";
</script>
<script type="text/javascript" src="${CONTEXT}js/npsPurchase/edit.js"></script>