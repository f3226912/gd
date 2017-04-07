<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>

<form id="auditForm" method="post" action="certifi/audit">
		<div>
			<input type="hidden" id="certifiId" name="certifiId"    value="${dto.certifiId}"/>
			<input type="hidden" id="status" name="status"    value="1"/>
			<table>
				<tr>
					<td class="mleft"  >
						账号:
					</td>
					<td class="mright">
						${dto.account}
					</td>
				</tr>
			<%-- 	<tr>
					<td class="mleft" >
						昵称:
					</td>
					<td class="mright">
					${dto.nickName}
					</td>
				</tr> --%>
				<tr>
					<td class="mleft" >
						注册来源:
					</td>
<!-- 					//会员类别（1谷登农批，2农速通，3农商友，4产地供应商 ，其余待补）
 -->					
					<td class="mright">
						<c:if test="${dto.level==1 }">谷登农批</c:if>
 						<c:if test="${dto.level==2 }">农速通</c:if>
						<c:if test="${dto.level==3 }">农商友</c:if>
						<c:if test="${dto.level==4 }">产地供应商</c:if>
					</td>
				</tr>
				
				
			 
				
				<!-- 根据认证类型，显示个人姓名，或者企业名称 -->
				<c:choose>
				<c:when test="${dto.type==1 }">
				<tr>
					<td class="mleft" >
					姓名:</td>
					<td class="mright">
					${dto.linkMan}
					</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
					<td class="mleft" >
					企业名称:</td>
					<td class="mright">
					${dto.companyName}
					</td>
				</tr>
				<tr>
					<td class="mleft" >
					法人代表:</td>
					<td class="mright">
					${dto.linkMan}
					</td>
				</tr>
				</c:otherwise>
				</c:choose>
				
				<tr>
				<td class="mleft" >
					提交时间:</td>
					<td class="mright">
					${dto.commitTime_string}
 					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
					激活状态:
					</td>
					<td class="mright">
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_baseinfo 表得到）
							 -->
							<!-- 0未启用，1已启用  -->	
							<c:if test="${dto.isActivi==0 }">未启用</c:if>
 						 	<c:if test="${dto.isActivi==1 }">启用</c:if>
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						商家认证:
					</td>
					<td class="mright">
							<c:if test="${dto.status==0 }">待认证</c:if>
 						 	<c:if test="${dto.status==1 }">已认证</c:if>
					</td>
				</tr>
				
				
				
				<c:choose>
				<c:when test="${dto.type==1 }">
			<!-- 	<tr>
					<td colspan="2">个人认证信息</td>
				</tr> -->
				<tr>
					<td class="mleft" >
					身份证号：
					</td>
					<td class="mright">
					${dto.idCard}
					</td>
				</tr> 
				<tr>
					<td style="font-size:16px;" align="center" colspan="2">
					身份证</td>
				</tr>
				<tr>
					<td  colspan="2" style="font-size:16px;">
					<c:choose>
						<c:when test="${dto.cardPhotoUrl != null}">
						<c:forEach items="${fn:split(dto.cardPhotoUrl, ',')}" var="url">
							<img src="${imgHostUrl}${url}" />
						</c:forEach>
						</c:when>
						<c:otherwise>
							图片未上传
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
			<!-- 	<tr>
					<td colspan="2">企业认证信息</td>
				</tr> -->
				<tr>
					<td class="mleft">
					营业执照号：</td>
					<td >${dto.bzl}</td>
				</tr>
				<tr>
					<td  style="font-size:16px;" align="center" colspan="2">
					营业执照</td>
				</tr>
				<tr>
					<td class="mleft" colspan="2">
					<c:choose>
						<c:when test="${dto.bzlPhotoUrl != null}">
						<img src="${imgHostUrl}${dto.bzlPhotoUrl}" />
						</c:when>
						<c:otherwise>
						图片未上传
						</c:otherwise>
					</c:choose>
					
					</td>
				</tr>
					<tr>
					<td class="mleft">组织机构代码证号：</td>
					<td class="mright">${dto.orgCode}</td>
				</tr>
				<tr>
					<td style="font-size:16px;" align="center"colspan="2">
					组织机构代码证</td>
				</tr>
				<tr>
					<td class="mleft" colspan="2">
					<c:choose>
						<c:when test="${dto.orgCodePhotoUrl != null}">
						<img src="${imgHostUrl}${dto.orgCodePhotoUrl}" />
						</c:when>
						<c:otherwise>
						图片未上传
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				
				</c:otherwise>
				</c:choose>
				
				
			</table>
			<div>
			 
			</div>
			<div>
			</div>
 			
			
		</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/certifi/add.js"></script>











