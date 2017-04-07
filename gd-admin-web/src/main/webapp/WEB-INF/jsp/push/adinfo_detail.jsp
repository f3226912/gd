<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="#">
		<div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table>
			<tr>
				<td>广告轮播名称:</td>
				<td>${dto.adName}
					</td>
			</tr>
			<tr>
				<td>广告渠道:</td>
				<td>
					<c:if test="${dto.adCanal==01 }">农批web</c:if>
					<c:if test="${dto.adCanal==02 }">农商友</c:if>
					<c:if test="${dto.adCanal==03 }">农速通</c:if>
				</td>
			</tr>
			<tr>
				<td>广告类型:</td>
				<td>
					<c:if test="${dto.adType==01 }">轮播图</c:if>
					<c:if test="${dto.adType==02 }">产品推送</c:if>
					<c:if test="${dto.adType==03 }">副图</c:if>
					<c:if test="${dto.adType==04 }">全国农贸市场图</c:if>
				</td>
			</tr>
			<tr>
				<td>开始时间:</td>
				<td>${dto.startTimeStr}</td>
			</tr>
			<tr>
				<td>截至时间:</td>
				<td>${dto.endTimeStr}</td>
			</tr>
			<tr>
				<td>产品id:</td>
				<td>${dto.productId}
					</td>
			</tr>
			<tr>
				<td>排序:</td>
				<td>${dto.sort}
					</td>
			</tr>
			<tr>
				<td>上传图片:</td>
				<td><a href="${imgHostUrl}${dto.adUrl}" target="_blank"><img src="${imgHostUrl}${dto.adUrl}" width="100" height="100"></a></td>
			</tr>
			<tr>
				<td>跳转地址:</td>
				<td>${dto.adLinkUrl}</td>
			</tr>
			<tr>
				<td>状态:</td>
				<td>
					<c:if test="${dto.state==01 }">启用</c:if>
					<c:if test="${dto.state==02 }">停用</c:if>
					<c:if test="${dto.state==03 }">过期</c:if>
				</td>
			</tr>
		</table>
		<div></div>
		<div></div>
	</div>
</form>











