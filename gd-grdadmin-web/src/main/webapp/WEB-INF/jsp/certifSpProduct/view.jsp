<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<input type="hidden" id="id" name="id" value="${dto.id}" />
<div>
	<table style="border: none;width: 100%;">
		<tr>
			<td>用户账号<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.account}</span></td>
		</tr>
		<tr>
			<td>商品名称<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.productName}</span></td>
		</tr>
		<tr>
			<td>认证机构<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.certifOrg}</span></td>
		</tr>
		<tr>
			<td>产品标识名称<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.signs}</span></td>
		</tr>
		<tr>
			<td>企业名称<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.companyName}</span></td>
		</tr>
		<tr>
			<td>商标<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.brand}</span></td>
		</tr>
		<tr>
			<td>申请时间<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.commitTimeString}</span></td>
		</tr>
		<tr>
			<td>账号状态<span style="color: red"></span></td>
			<td>
				<span style="width:50%" >
					<c:choose>
					   <c:when test="${dto.userStatus == 0}">
					    	未启用
					   </c:when>
					   <c:when test="${dto.userStatus == 1}">
					   		启用
					   </c:when>
					   <c:otherwise>
					   </c:otherwise>
					</c:choose>
				</span>
			</td>
		</tr>
		<tr>
			<td>商铺名称<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.shopsName}</span></td>
		</tr>
		<tr>
			<td>供应量<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.stockCount}${dto.units}</span></td>
		</tr>
		<tr>
			<td>证书编号<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.certifNo}</span></td>
		</tr>
		<tr>
			<td>专用标志图片<span style="color: red"></span></td>
			<td>
				<c:forEach var="url" items ="${pictureList}" varStatus="status">
			    	<span><img alt="" src="${imgHostUrl}${url}" onclick="window.open('${imgHostUrl}${url}')" ></span>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>商品图片<span style="color: red"></span></td>
			<td><span style="width:50%" ><img alt="" src="${imgHostUrl}${productImg}" onclick="window.open('${imgHostUrl}${productImg}')" ></span></td>
		</tr>
		<tr>
			<td>所在地址<span style="color: red"></span></td>
			<td><span style="width:50%" >${dto.provinceStr}${dto.cityStr}  详细地址:${dto.address}</span></td>
		</tr>
	</table>
	<div><p></p></div>
	<table style="border: none;width: 100%;">
	<caption  style="margin: 0 10;text-align:left;">认证审核记录 : </caption>
		<thead>
			<tr>
				<td>NO.</td>
				<td>审核操作</td>
				<td>驳回原因</td>
				<td>审核员</td>
				<td>操作时间</td>
			</tr>
		</thead>
		<c:forEach var="item" items ="${certifLogList}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${item.statusStr}</td>
				<td>${item.reason}</td>
				<td>${item.optionUser}</td>
				<td>
				<fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
	</table>
</div>
