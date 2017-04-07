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
			<td>用户帐号</td>
			<td><span style="width:50%" >${dto.account}</span></td>
		</tr>
		<tr>
			<td>姓名</td>
			<td><span style="width:50%" >${dto.realName}</span></td>
		</tr>
		<tr>
			<td>申请时间</td>
			<td><span style="width:50%" ><fmt:formatDate value="${dto.commitTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
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
			<td>身份证</td>
			<td><span style="width:50%" >${dto.idCard}</span></td>
		</tr>
		<tr>
			<td>身份证图片</td>
			<td>
				<c:choose>
					<c:when test="${dto.cardPhotoUrl != null}">
					<c:forEach items="${fn:split(dto.cardPhotoUrl, ',')}" var="url">
						<img style="width:500px;height:500px;"  src="${imgHostUrl}${url}" onclick="window.open('${imgHostUrl}${url}')" />
					</c:forEach>
					</c:when>
					<c:otherwise>
						图片未上传
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>头像</td>
			<td>
				<c:choose>
					<c:when test="${dto.icon != null}">
						<img style="width:500px;height:500px;" src="${imgHostUrl}${dto.icon}" onclick="window.open('${imgHostUrl}${dto.icon}')" />
					</c:when>
					<c:otherwise>
						图片未上传
					</c:otherwise>
				</c:choose>
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
