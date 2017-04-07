<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="margin-top: 20px; font-size: 15px;">
	所属区域省份：${nstDTO.provinceName }<br>
	所属区域城市：${nstDTO.cityName }<br>
	信息部名称：${nstDTO.companyName }<br>
	信息部账号：${nstDTO.account}<br>
	当日分配上限：${nstDTO.dayAssignMax }<c:if test="${not empty nstDTO.dayAssignMax}"> 条</c:if><br>
	当月分配上限：${nstDTO.monthAssignMax }<c:if test="${not empty nstDTO.dayAssignMax}"> 条</c:if><br>
	分配有效开始时间：${nstDTO.assignStartTime }<br>
	分配有效结束时间：${nstDTO.assignEndTime }<br>
	已分配信息数：<c:if test="${not empty nstDTO.dayAssignMax }">${nstDTO.assignedNum }条</c:if><br>
	规则状态：<c:choose>
				<c:when test="${nstDTO.rule==0 }">
					无效
				</c:when>
				<c:when test="${nstDTO.isEffective==0 and (not empty nstDTO.dayAssignMax)}">
					有效
				</c:when>
				<c:otherwise>
					无效
				</c:otherwise>
			</c:choose>
</div>
