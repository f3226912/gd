<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding:10px">
	<table>
		<tr>
			<td>广告渠道：&nbsp;</td>
			<td>${adSpaceDTO.menuName }</td>
		</tr>
		<tr>
			<td>广告类型：&nbsp;</td>
			<td>
				<c:choose>
					<c:when test="${adSpaceDTO.adType == '1'}">图片</c:when>
					<c:when test="${adSpaceDTO.adType == '2'}">文字</c:when>
					<c:when test="${adSpaceDTO.adType == '3'}">app启动页</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>广告位：&nbsp;</td>
			<td>${adSpaceDTO.spaceSign }</td>
		</tr>
		<tr>
			<td>广告价格：&nbsp;</td>
			<td>${adSpaceDTO.adPrice }<c:if test="${adSpaceDTO.adPrice != ''}">&nbsp;/&nbsp;天</c:if></td>
		</tr>
		
		<c:if test="${adSpaceDTO.adType == '1' or  adSpaceDTO.adType == '3'}">
		<tr>
		<td>广告图规格：&nbsp;</td>
			<td>${adSpaceDTO.adSize}</td>
		</tr>
		</c:if>
		<tr>
			<td>广告位展示图：</td>
			<td style="width:150px;height:150px">
			<c:if test="${adSpaceDTO.showImg !=''}"><a href="${imgHostUrl}${adSpaceDTO.showImg }" target="_blank"><img alt="" src="${imgHostUrl}${adSpaceDTO.showImg }" width="150px" height="150px"></a></c:if>
			</td>
		</tr>
		<tr>
			<td>广告位说明：</td>
			<td>${adSpaceDTO.adName }</td>
		</tr>
		<tr>
			<td>过期广告默认替换图:</td>
			<td style="width:150px;height:150px">
				<c:if test="${adSpaceDTO.replaceImg !=''}"><a href="${imgHostUrl}${adSpaceDTO.replaceImg }" target="_blank"><img alt="" src="${imgHostUrl}${adSpaceDTO.replaceImg }" width="150px" height="150px"></a></c:if>
			</td>
		</tr>
	</table>
</div>