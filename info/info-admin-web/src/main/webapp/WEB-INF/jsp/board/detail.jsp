<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<input type="hidden" id="id" name="id" value="${dto.id}" />
	<table>
		<tr>
			<td>看板数据名称:</td>
			<td>${dto.name}
				</td>
		</tr>
		<tr>
			<td>所属分类:</td>
			<td>
				${dto.menuId}
			</td>
		</tr>
		<tr>
			<td>${dto.name}:</td>
			<td>
				${dto.values}
			</td>
		</tr>
		<tr>
			<td>状态:</td>
			<td>
				<c:if test="${dto.state==1 }">启用</c:if>
				<c:if test="${dto.state==2 }">禁用</c:if>
			</td>
		</tr>
	</table>
	<div></div>
	<div></div>
</div>











