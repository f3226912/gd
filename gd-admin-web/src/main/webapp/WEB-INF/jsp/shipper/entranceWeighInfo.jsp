<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>

<div style="margin-left: 20px;">
	<h3 style="display: inline;">皮重登记时间:&nbsp;&nbsp;&nbsp;</h3><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.tareCreateTime}" />
	<br/>
	<br/>
	<h2 style="display: inline;">总重登记时间:&nbsp;&nbsp;&nbsp;</h2><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.totalCreateTime}" />
	<br/>
	<br/>
	<h2 style="display: inline;">用户账号:&nbsp;&nbsp;&nbsp;</h2>${dto.account }
	<br/>
	<br/>
	<h2 style="display: inline;">姓名:&nbsp;&nbsp;&nbsp;</h2>${dto.memberName }
	<br/>
	<br/>
	<h2 style="display: inline;">入场车辆车牌:&nbsp;&nbsp;&nbsp;</h2>${dto.carNumber }<br>
	<div style="margin-left: 20px;margin-top: 5px;">
		一天出场次数:&nbsp;&nbsp;${counts.dailySubOutmarket }<br/>
		一周出场次数:&nbsp;&nbsp;${counts.weeklySubOutmarket }<br/>
		一月出场次数:&nbsp;&nbsp;${counts.monthlySubOutmarket }<br/>
	</div>
	<hr>
	<h2 class="bt-order-ct">产品详情：</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable2">
		<tr><th>序号</th><th>产品名称 </th><th>产品重量 </th></tr>
	
		<c:forEach items="${pcDtoList}" var="pcDto" varStatus="status">	
				
				<tr>
					<td>${status.count}</td>
					<td>${pcDto.productName }</td>
					<td>${pcDto.weigh}&nbsp;吨</td>
				</tr>
		</c:forEach>
	
	</table>
	<br/>
	<h2 style="display: inline;">车辆拍照:</h2>
	<br/>
	
	<c:if test="${not empty carNumberImages}">
	<c:forEach items="${carNumberImages}" varStatus="image" var="ci">
		<a href="${imgHostUrl}${ci}" target="_blank"><img src="${imgHostUrl}${ci}" width="182" height="110"></a> <br/>
	</c:forEach><br></c:if>
	<c:if test="${empty carNumberImages}">
		暂时没有图片
	</c:if><br><br>
	
	<h2 style="display: inline;">产地:</h2> 	${dto.place }<br/><br/>
	
	<h2 style="display: inline;">货品质量:</h2>
		<c:if test="${dto.quality=='1'}">
			优
		</c:if>
		<c:if test="${dto.quality=='2'}">
			良
		</c:if>
		<c:if test="${dto.quality=='3'}">
			中
		</c:if>
		<c:if test="${dto.quality=='4'}">
			差
		</c:if>
	<br/><br/>
	
	<h2 style="display: inline;">是否满载:</h2>
	<c:if test="${dto.allWeigh=='1' }">
		是
	</c:if>
	<c:if test="${dto.allWeigh=='2' }">
		否
	</c:if>
	<br/><br/>
	<h2 style="display: inline;">其他物品:</h2>：${dto.others }<br/>
	<h2>其他:</h2>
	<div style="margin-left: 20px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable1">
			<tr>
				<td>
					<b>皮重</b>&nbsp;
					<c:choose>
						<c:when test="${dto.tare!=null && dto.tare!=''}">
							<fmt:formatNumber type="number" value="${dto.tare }" pattern="0.00" maxFractionDigits="2"/>
						</c:when>
						<c:otherwise>
							0.00
						</c:otherwise>
					</c:choose>
					<b>&nbsp;吨</b>
				</td>
				<td>
					<b>总重</b>&nbsp;
					<c:choose>
						<c:when test="${dto.totalWeight!=null && dto.totalWeight!=''}">
							<fmt:formatNumber type="number" value="${dto.totalWeight }" pattern="0.00" maxFractionDigits="2"/>
						</c:when>
						<c:otherwise>
							0.00
						</c:otherwise>
					</c:choose>
					<b>&nbsp;吨</b>
				</td>
				<td>
					<b>净重</b>&nbsp;
					<c:choose>
						<c:when test="${dto.netWeight!=null && dto.netWeight!=''}">
							<fmt:formatNumber type="number" value="${dto.netWeight }" pattern="0.00" maxFractionDigits="2"/>
						</c:when>
						<c:otherwise>
							0.00
						</c:otherwise>
					</c:choose>
					<b>&nbsp;吨</b>
				</td>

			</tr>
		</table><br/>
		<h2 style="display: inline;">记录人员:</h2>${dto.weighMember}<br/><br/>
		
		<h2 style="display: inline;">产品总重:</h2>
			<c:choose>
						<c:when test="${dto.weigh!=null && dto.weigh!=''}">
							<fmt:formatNumber type="number" value="${dto.weigh }" pattern="0.00" maxFractionDigits="2"/>
						</c:when>
						<c:otherwise>
							0.00
						</c:otherwise>
					</c:choose>
					<b>&nbsp;吨</b>		
		<br/><br/> 	
		
		<c:if test="${not empty dto.netWeight }">
			<b>系统允许正负10%的误差:</b><br/>
			<h2 style="display: inline; margin-left: 150px;">误差值:</h2>
				<fmt:formatNumber type="number" value="${dto.netWeight - dto.netWeight*0.1 }" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>
			 ~ 
			 	<fmt:formatNumber type="number" value="${dto.netWeight + dto.netWeight*0.1 }" maxFractionDigits="2" pattern="0.00"></fmt:formatNumber>
			 
		</c:if>
	</div>
	
</div>
